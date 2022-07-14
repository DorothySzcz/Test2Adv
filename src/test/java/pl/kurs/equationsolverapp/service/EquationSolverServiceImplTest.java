package pl.kurs.equationsolverapp.service;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pl.kurs.equationsolverapp.exceptions.InvalidEquationFormatException;
import pl.kurs.equationsolverapp.exceptions.UnknownOperatorException;
import pl.kurs.equationsolverapp.model.EquationEvent;
import pl.kurs.equationsolverapp.service.eventservices.EquationEventService;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

public class EquationSolverServiceImplTest {

    @Mock private ValidationService validationServiceMock;
    @Mock private ProcessOperatorService processOperatorServiceMock;
    @Mock private EquationEventService equationEventServiceMock;
    private EquationSolverService equationSolverService;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
        equationSolverService = new EquationSolverServiceImpl(validationServiceMock, processOperatorServiceMock,equationEventServiceMock);
    }

    @Test
    public void shouldReturn7FromInput() throws InvalidEquationFormatException, UnknownOperatorException {

        String input = "8+2*2-4/2";
        char operator1 = '+';
        char operator2 = '*';
        char operator3 = '-';
        char operator4 = '/';
        BigDecimal a = BigDecimal.valueOf(8);
        BigDecimal b = BigDecimal.valueOf(2);
        BigDecimal c = BigDecimal.valueOf(2);
        BigDecimal d = BigDecimal.valueOf(4);
        BigDecimal e = BigDecimal.valueOf(2);

        Mockito.when(validationServiceMock.validExpression(input)).thenReturn("8 + 2 * 2 - 4 / 2");
        String validatedExpression = validationServiceMock.validExpression(input);

        Mockito.when(processOperatorServiceMock.processOperator(operator2, b, c)).thenReturn(BigDecimal.valueOf(4));
        BigDecimal multiply = processOperatorServiceMock.processOperator(operator2, b, c);

        Mockito.when(processOperatorServiceMock.processOperator(operator4, d, e)).thenReturn(BigDecimal.valueOf(2));
        BigDecimal divide = processOperatorServiceMock.processOperator(operator4, d, e);

        Mockito.when(processOperatorServiceMock.processOperator(operator1, a, multiply)).thenReturn(BigDecimal.valueOf(12));
        BigDecimal add = processOperatorServiceMock.processOperator(operator1, a, multiply);

        Mockito.when(processOperatorServiceMock.processOperator(operator3, add, divide)).thenReturn(BigDecimal.valueOf(10));

        EquationEvent equationEvent = new EquationEvent(Timestamp.from(Instant.now()), validatedExpression);
        Mockito.lenient().doNothing().when(equationEventServiceMock).saveEvent(equationEvent);

        BigDecimal result = equationSolverService.calculateEquation(input);
        BigDecimal expectedValue = BigDecimal.valueOf(10);

        Assertions.assertThat(result).isEqualTo(expectedValue);

    }

}