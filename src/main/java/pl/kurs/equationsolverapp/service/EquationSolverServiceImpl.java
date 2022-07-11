package pl.kurs.equationsolverapp.service;

import org.springframework.stereotype.Service;
import pl.kurs.equationsolverapp.exceptions.InvalidEquationFormatException;
import pl.kurs.equationsolverapp.exceptions.UnknownOperatorException;
import pl.kurs.equationsolverapp.model.EquationEvent;
import pl.kurs.equationsolverapp.service.eventservices.EquationEventService;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

@Service
public class EquationSolverServiceImpl implements EquationSolverService {

    private ValidationService validationService;
    private ProcessOperatorService processOperatorService;
    private EquationEventService equationEventService;


    public EquationSolverServiceImpl(ValidationService validationService, ProcessOperatorService processOperatorService, EquationEventService equationEventService) {
        this.validationService = validationService;
        this.processOperatorService = processOperatorService;
        this.equationEventService = equationEventService;
    }

    @Override
    public BigDecimal calculateEquation(String expression) throws InvalidEquationFormatException, UnknownOperatorException {

        String validatedExpression = validationService.validExpression(expression);
        Stack<Character> operatorStack = new Stack<Character>();
        Stack<BigDecimal> valueStack =  new Stack<BigDecimal>();
        String[] tokens = validatedExpression.split(" ");

        for (int n = 0; n < tokens.length; n++) {
            String nextToken = tokens[n];
            char ch = nextToken.charAt(0);
            if (ch >= '0' && ch <= '9') {
                valueStack.push(BigDecimal.valueOf(Long.parseLong(nextToken)));
            } else if (isOperator(ch)) {
                if (operatorStack.empty() || getPrecedence(ch) > getPrecedence(operatorStack.peek())) {
                    operatorStack.push(ch);
                } else {
                    while (!operatorStack.empty() && getPrecedence(ch) <= getPrecedence(operatorStack.peek())) {
                        char toProcess = operatorStack.peek();
                        operatorStack.pop();
                        valueStack.push(processOperatorService.processOperator(toProcess, valueStack));
                    }
                    operatorStack.push(ch);
                }
            }
        }

        while (!operatorStack.empty() && isOperator(operatorStack.peek())) {
            char toProcess = operatorStack.peek();
            operatorStack.pop();
            valueStack.push(processOperatorService.processOperator(toProcess, valueStack));
        }

        equationEventService.saveEvent(
                new EquationEvent(
                        Timestamp.from(Instant.now()),
                        validatedExpression
                )
        );

        return valueStack.peek();
    }

    private int getPrecedence(char operator) {
        return (operator == '+' || operator == '-') ? 1 : 2;
    }

    private boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

}
