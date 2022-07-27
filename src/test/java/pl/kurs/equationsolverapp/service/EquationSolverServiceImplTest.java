package pl.kurs.equationsolverapp.service;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import pl.kurs.equationsolverapp.exceptions.InvalidEquationFormatException;
import pl.kurs.equationsolverapp.exceptions.UnknownOperatorException;
import pl.kurs.equationsolverapp.model.EquationEvent;
import pl.kurs.equationsolverapp.service.eventservices.EquationEventService;
import pl.kurs.equationsolverapp.service.operatorsservices.AddService;
import pl.kurs.equationsolverapp.service.operatorsservices.DivideService;
import pl.kurs.equationsolverapp.service.operatorsservices.MultiplyService;
import pl.kurs.equationsolverapp.service.operatorsservices.SubtractService;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

import static org.mockito.Mockito.*;

public class EquationSolverServiceImplTest {

    @Mock private ValidationService validationServiceMock;
    @Mock private EquationEventService equationEventServiceMock;
    @Spy private AddService addService;
    @Spy private SubtractService subtractService;
    @Spy private MultiplyService multiplyService;
    @Spy private DivideService divideService;
    private ProcessOperatorService processOperatorServiceSpy;
    private EquationSolverService equationSolverService;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
        ProcessOperatorServiceImpl processOperatorServiceImpl = new ProcessOperatorServiceImpl(addService, subtractService, multiplyService, divideService);
        processOperatorServiceSpy = spy(processOperatorServiceImpl);
        equationSolverService = new EquationSolverServiceImpl(validationServiceMock, processOperatorServiceSpy,equationEventServiceMock);
    }

    @Test
    public void shouldReturn10FromInput() throws InvalidEquationFormatException, UnknownOperatorException {

        String input = "8+2*2-4/2-1+2";

        Mockito.when(validationServiceMock.validExpression(input)).thenReturn("8 + 2 * 2 - 4 / 2 - 1 + 2");
        String validatedExpression = validationServiceMock.validExpression(input);

        BigDecimal val1 = processOperatorServiceSpy.processOperator('/', BigDecimal.valueOf(4), BigDecimal.valueOf(2));
        BigDecimal val2 = processOperatorServiceSpy.processOperator('*', BigDecimal.valueOf(2), BigDecimal.valueOf(2));
        BigDecimal val3 = processOperatorServiceSpy.processOperator('+', BigDecimal.valueOf(8), val2);
        BigDecimal val4 = processOperatorServiceSpy.processOperator('-', val3, val1);
        BigDecimal val5 = processOperatorServiceSpy.processOperator('-', val4, BigDecimal.valueOf(1));
        BigDecimal resultOfProcessOperatorMethod = processOperatorServiceSpy.processOperator('+', val5, BigDecimal.valueOf(2));

        verify(addService, times(2)).compute(any(BigDecimal.class), any(BigDecimal.class));
        verify(subtractService, times(2)).compute(any(BigDecimal.class), any(BigDecimal.class));
        verify(multiplyService, times(1)).compute(any(BigDecimal.class), any(BigDecimal.class));
        verify(divideService, times(1)).compute(any(BigDecimal.class), any(BigDecimal.class));

        Mockito.lenient().doNothing().when(equationEventServiceMock).saveEvent(new EquationEvent(Timestamp.from(Instant.now()), validatedExpression));

        BigDecimal resultOfCalculateEquationMethod = equationSolverService.calculateEquation(input);

        Assertions.assertThat(resultOfCalculateEquationMethod)
                .isEqualTo(BigDecimal.valueOf(11))
                .isEqualTo(resultOfProcessOperatorMethod);
    }

}