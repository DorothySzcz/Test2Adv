package pl.kurs.equationsolverapp.service.operatorsservices;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DivideService implements OperatorService {

    @Override
    public BigDecimal compute(BigDecimal firstValue, BigDecimal secondValue) {
        if(secondValue.equals(BigDecimal.valueOf(0)))
            throw new ArithmeticException("Division by zero");
        return firstValue.divide(secondValue);
    }
}
