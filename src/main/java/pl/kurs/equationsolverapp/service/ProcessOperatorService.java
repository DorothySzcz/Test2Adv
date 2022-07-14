package pl.kurs.equationsolverapp.service;

import java.math.BigDecimal;

public interface ProcessOperatorService {
    BigDecimal processOperator(char operator, BigDecimal val1, BigDecimal val2);
}
