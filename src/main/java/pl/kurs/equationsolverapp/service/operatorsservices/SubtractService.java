package pl.kurs.equationsolverapp.service.operatorsservices;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SubtractService implements OperatorService {

    @Override
    public BigDecimal compute(BigDecimal firstValue, BigDecimal secondValue) {
        return firstValue.subtract(secondValue);
    }
}
