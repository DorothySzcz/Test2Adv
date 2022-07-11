package pl.kurs.equationsolverapp.service.operatorsservices;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class SubtractServiceTest {

    private SubtractService subtractService;

    @Before
    public void init() {
        subtractService = new SubtractService();
    }

    @Test
    public void shouldReturn2ForSubtractMethod() {

        BigDecimal val1 = BigDecimal.valueOf(5);
        BigDecimal val2 = BigDecimal.valueOf(3);
        BigDecimal expectedValue = BigDecimal.valueOf(2);

        BigDecimal result = subtractService.compute(val1, val2);

        Assert.assertEquals(expectedValue, result);

    }

}