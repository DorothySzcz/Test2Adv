package pl.kurs.equationsolverapp.service;

import pl.kurs.equationsolverapp.exceptions.InvalidEquationFormatException;
import pl.kurs.equationsolverapp.exceptions.UnknownOperatorException;

public interface ValidationService {

    String validExpression(String expressionToValid) throws InvalidEquationFormatException, UnknownOperatorException;
}
