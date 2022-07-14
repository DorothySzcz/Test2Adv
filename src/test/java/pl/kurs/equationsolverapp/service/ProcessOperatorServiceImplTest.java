package pl.kurs.equationsolverapp.service;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pl.kurs.equationsolverapp.service.operatorsservices.*;

import java.math.BigDecimal;

import static org.junit.Assert.assertThrows;

public class ProcessOperatorServiceImplTest {

    @Mock private AddService addService;
    @Mock private SubtractService subtractService;
    @Mock private MultiplyService multiplyService;
    @Mock private DivideService divideService;
    private ProcessOperatorService processOperatorService;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
        processOperatorService = new ProcessOperatorServiceImpl(addService, subtractService, multiplyService, divideService);
    }

    @Test
    public void shouldReturn6WhenOperatorIsPlus() {

        char op = '+';
        BigDecimal val1 = BigDecimal.valueOf(2);
        BigDecimal val2 = BigDecimal.valueOf(3);

        Mockito.when(addService.compute(val1, val2)).thenReturn(BigDecimal.valueOf(6));
        BigDecimal expectedValue = BigDecimal.valueOf(6);

        BigDecimal result = processOperatorService.processOperator(op, val1, val2);

        Assertions.assertThat(result).isEqualTo(expectedValue);
    }

    @Test
    public void shouldReturn2WhenOperatorIsMinus() {

        char op = '-';
        BigDecimal val1 = BigDecimal.valueOf(5);
        BigDecimal val2 =  BigDecimal.valueOf(3);

        Mockito.when(subtractService.compute(val1, val2)).thenReturn(BigDecimal.valueOf(2));
        BigDecimal expectedValue = BigDecimal.valueOf(2);

        BigDecimal result = processOperatorService.processOperator(op, val1, val2);

        Assertions.assertThat(result).isEqualTo(expectedValue);
    }

    @Test
    public void shouldReturn15WhenOperatorIsMultiplier() {

        char op = '*';

        BigDecimal val1 =  BigDecimal.valueOf(5);
        BigDecimal val2 =  BigDecimal.valueOf(3);

        Mockito.when(multiplyService.compute(val1, val2)).thenReturn(BigDecimal.valueOf(15));
        BigDecimal expectedValue = BigDecimal.valueOf(15);

        BigDecimal result = processOperatorService.processOperator(op, val1, val2);

        Assertions.assertThat(result).isEqualTo(expectedValue);
    }

    @Test
    public void shouldReturn3WhenOperatorIsDivider() {

        char op = '/';
        BigDecimal val1 =  BigDecimal.valueOf(6);
        BigDecimal val2 =  BigDecimal.valueOf(2);

        Mockito.when(divideService.compute(val1, val2)).thenReturn(BigDecimal.valueOf(3));
        BigDecimal expectedValue = BigDecimal.valueOf(3);

        BigDecimal result = processOperatorService.processOperator(op, val1, val2);

        Assertions.assertThat(result).isEqualTo(expectedValue);
    }

    @Test
    public void shouldThrowIllegalStateExceptionWhenOperatorIsUnknown() {

        char op = '^';
        Stack<BigDecimal> valueStack = new Stack<>();
        BigDecimal val1 = BigDecimal.valueOf(6);
        BigDecimal val2 = BigDecimal.valueOf(2);

        Throwable e = assertThrows(IllegalStateException.class, () -> processOperatorService.processOperator(op, val1, val2));

        SoftAssertions sa = new SoftAssertions();
        sa.assertThat(e).isExactlyInstanceOf(IllegalStateException.class);
        sa.assertThat(e).hasMessage("Unexpected value: " + op);
        sa.assertAll();

    }

}