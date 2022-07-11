package pl.kurs.equationsolverapp.service.operatorsservices;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class AddServiceTest {

    private AddService addService;

    @Before
    public void init() {
        addService = new AddService();
    }

    @Test
    public void shouldReturn5ForAddMethod() {

        BigDecimal val1 = BigDecimal.valueOf(5);
        BigDecimal val2 = BigDecimal.valueOf(3);
        BigDecimal expectedValue = BigDecimal.valueOf(8);

        BigDecimal result = addService.compute(val1, val2);

        Assert.assertEquals(expectedValue, result);

    }

}