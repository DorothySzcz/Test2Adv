package pl.kurs.equationsolverapp.service.operatorsservices;

import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class DivideServiceTest {

    DivideService divideService;

    @Before
    public void init() {
        divideService = new DivideService();
    }

    @Test
    public void shouldReturn5ForDivideMethod() {

        BigDecimal val1 = BigDecimal.valueOf(10);
        BigDecimal val2 = BigDecimal.valueOf(2);
        BigDecimal expectedValue = BigDecimal.valueOf(5);

        BigDecimal result = divideService.compute(val1, val2);

        Assert.assertEquals(expectedValue, result);

    }

    @Test
    public void shouldThrowArithmeticExceptionWhenSecondValueIs0ForDividerOperation() {

        BigDecimal val1 = BigDecimal.valueOf(6);
        BigDecimal val2 = BigDecimal.valueOf(0);

        Throwable e = assertThrows(ArithmeticException.class, () -> divideService.compute(val1, val2));

        SoftAssertions sa = new SoftAssertions();
        sa.assertThat(e).isExactlyInstanceOf(ArithmeticException.class);
        sa.assertThat(e).hasMessage("Division by zero");
        sa.assertAll();

    }

}