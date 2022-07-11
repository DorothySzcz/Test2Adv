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
        Stack<BigDecimal> valueStack = new Stack<>();
        valueStack.push(BigDecimal.valueOf(2));
        valueStack.push(BigDecimal.valueOf(3));

        BigDecimal firstValue = BigDecimal.valueOf(2);
        BigDecimal secondValue = BigDecimal.valueOf(3);

        Mockito.when(addService.compute(firstValue, secondValue)).thenReturn(BigDecimal.valueOf(6));
        BigDecimal expectedValue = BigDecimal.valueOf(6);

        BigDecimal result = processOperatorService.processOperator(op, valueStack);

        Assertions.assertThat(result).isEqualTo(expectedValue);
    }

    @Test
    public void shouldReturn2WhenOperatorIsMinus() {

        char op = '-';
        Stack<BigDecimal> valueStack = new Stack<>();
        valueStack.push(BigDecimal.valueOf(5));
        valueStack.push(BigDecimal.valueOf(3));

        BigDecimal firstValue = BigDecimal.valueOf(5);
        BigDecimal secondValue = BigDecimal.valueOf(3);

        Mockito.when(subtractService.compute(firstValue, secondValue)).thenReturn(BigDecimal.valueOf(2));
        BigDecimal expectedValue = BigDecimal.valueOf(2);

        BigDecimal result = processOperatorService.processOperator(op, valueStack);

        Assertions.assertThat(result).isEqualTo(expectedValue);
    }

    @Test
    public void shouldReturn15WhenOperatorIsMultiplier() {

        char op = '*';
        Stack<BigDecimal> valueStack = new Stack<>();
        valueStack.push(BigDecimal.valueOf(5));
        valueStack.push(BigDecimal.valueOf(3));

        BigDecimal firstValue = BigDecimal.valueOf(5);
        BigDecimal secondValue = BigDecimal.valueOf(3);

        Mockito.when(multiplyService.compute(firstValue, secondValue)).thenReturn(BigDecimal.valueOf(15));
        BigDecimal expectedValue = BigDecimal.valueOf(15);

        BigDecimal result = processOperatorService.processOperator(op, valueStack);

        Assertions.assertThat(result).isEqualTo(expectedValue);
    }

    @Test
    public void shouldReturn3WhenOperatorIsDivider() {

        char op = '/';
        Stack<BigDecimal> valueStack = new Stack<>();
        valueStack.push(BigDecimal.valueOf(6));
        valueStack.push(BigDecimal.valueOf(2));

        BigDecimal firstValue = BigDecimal.valueOf(6);
        BigDecimal secondValue = BigDecimal.valueOf(2);

        Mockito.when(divideService.compute(firstValue, secondValue)).thenReturn(BigDecimal.valueOf(3));
        BigDecimal expectedValue = BigDecimal.valueOf(3);

        BigDecimal result = processOperatorService.processOperator(op, valueStack);

        Assertions.assertThat(result).isEqualTo(expectedValue);
    }

    @Test
    public void shouldThrowIllegalStateExceptionWhenOperatorIsUnknown() {

        char op = '^';
        Stack<BigDecimal> valueStack = new Stack<>();
        valueStack.push(BigDecimal.valueOf(6));
        valueStack.push(BigDecimal.valueOf(2));

        Throwable e = assertThrows(IllegalStateException.class, () -> processOperatorService.processOperator(op, valueStack));

        SoftAssertions sa = new SoftAssertions();
        sa.assertThat(e).isExactlyInstanceOf(IllegalStateException.class);
        sa.assertThat(e).hasMessage("Unexpected value: " + op);
        sa.assertAll();

    }

}