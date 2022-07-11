package pl.kurs.equationsolverapp.service.operatorsservices;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class MultiplyServiceTest {

    MultiplyService multiplyService;

    @Before
    public void init() {
        multiplyService = new MultiplyService();
    }

    @Test
    public void shouldReturn15ForMultiplyMethod() {

        BigDecimal val1 = BigDecimal.valueOf(5);
        BigDecimal val2 = BigDecimal.valueOf(3);
        BigDecimal expectedValue = BigDecimal.valueOf(15);

        BigDecimal result = multiplyService.compute(val1, val2);

        Assert.assertEquals(expectedValue, result);

    }

}