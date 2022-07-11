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
    public void shouldReturn5FromString() throws InvalidEquationFormatException, UnknownOperatorException {

        String input = "2 + 3";

        Stack<BigDecimal> valueStack = new Stack<>();
        valueStack.push(BigDecimal.valueOf(2));
        valueStack.push(BigDecimal.valueOf(3));

        Stack<Character> operatorStack = new Stack<>();
        operatorStack.push('+');

        Mockito.when(validationServiceMock.validExpression(input)).thenReturn("2 + 3");
        String validatedExpression = validationServiceMock.validExpression(input);

        Mockito.when(processOperatorServiceMock.processOperator(operatorStack.peek(), valueStack)).thenReturn(BigDecimal.valueOf(5));

        EquationEvent equationEvent = new EquationEvent(Timestamp.from(Instant.now()), validatedExpression);
        Mockito.lenient().doNothing().when(equationEventServiceMock).saveEvent(equationEvent);

        BigDecimal result = equationSolverService.calculateEquation(input);
        BigDecimal expectedValue = BigDecimal.valueOf(5);

        Assertions.assertThat(result).isEqualTo(expectedValue);

    }

}