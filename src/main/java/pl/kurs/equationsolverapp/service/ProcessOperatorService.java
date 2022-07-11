package pl.kurs.equationsolverapp.service;

import java.math.BigDecimal;

public interface ProcessOperatorService {
    BigDecimal processOperator(char op, Stack<BigDecimal> valueStack);
}
