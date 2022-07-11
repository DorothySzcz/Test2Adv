package pl.kurs.equationsolverapp.service.operatorsservices;

import java.math.BigDecimal;

public interface OperatorService {

    BigDecimal compute(BigDecimal firstValue, BigDecimal secondValue);

}
