package pl.kurs.equationsolverapp.service;


import pl.kurs.equationsolverapp.exceptions.InvalidEquationFormatException;
import pl.kurs.equationsolverapp.exceptions.UnknownOperatorException;

import java.math.BigDecimal;

public interface EquationSolverService {

    BigDecimal calculateEquation(String input) throws InvalidEquationFormatException, UnknownOperatorException;
}
