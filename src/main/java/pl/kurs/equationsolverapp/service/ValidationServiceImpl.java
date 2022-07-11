package pl.kurs.equationsolverapp.service;

import org.springframework.stereotype.Service;
import pl.kurs.equationsolverapp.exceptions.InvalidEquationFormatException;
import pl.kurs.equationsolverapp.exceptions.UnknownOperatorException;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class ValidationServiceImpl implements ValidationService {
    public String validExpression(String expressionToValid) throws InvalidEquationFormatException, UnknownOperatorException {
        return Optional.of(
                Optional.ofNullable(expressionToValid)
                        .filter(Objects::nonNull)
                        .filter(e -> !e.isEmpty())
                        .filter(e -> !Pattern.matches(".*[a-zA-Z]+.*", e))
                        .orElseThrow(() -> new InvalidEquationFormatException("Invalid expression!"))
                ).map(e -> e.replaceAll("\\s", ""))
                .filter(e -> e.matches("([-+]?[0-9]*\\.?[0-9]+[\\/\\+\\-\\*])+([-+]?[0-9]*\\.?[0-9]+)"))
                .map(e -> e.replaceAll("(?<=[*+\\/-])(?=[0-9])|(?<=[0-9])(?=[*+\\/-])", " "))
                .orElseThrow(() -> new UnknownOperatorException("Invalid operator!"));
    }
}
