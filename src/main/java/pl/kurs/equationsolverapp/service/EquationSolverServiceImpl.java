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

    private final ValidationService validationService;
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
        Stack<Character> operatorStack = new Stack<>();
        Stack<BigDecimal> valueStack =  new Stack<>();
        String[] tokens = validatedExpression.split(" ");

        for (String nextToken : tokens) {
            char ch = nextToken.charAt(0);
            if (ch >= '0' && ch <= '9') {
                valueStack.push(BigDecimal.valueOf(Long.parseLong(nextToken)));
            } else if (isOperator(ch)) {
                if (operatorStack.empty() && getPrecedence(ch) <= getPrecedence(operatorStack.peek())) {
                    while (operatorStack.empty() && getPrecedence(ch) <= getPrecedence(operatorStack.peek())) {
                        BigDecimal val2 = valueStack.peek();
                        valueStack.pop();
                        BigDecimal val1 = valueStack.peek();
                        valueStack.pop();
                        valueStack.push(processOperatorService.processOperator(operatorStack.peek(), val1, val2));
                        operatorStack.pop();
                    }
                }
                operatorStack.push(ch);
            }
        }

        while (operatorStack.empty() && isOperator(operatorStack.peek())) {
            BigDecimal val2 = valueStack.peek();
            valueStack.pop();
            BigDecimal val1 = valueStack.peek();
            valueStack.pop();
            valueStack.push(processOperatorService.processOperator(operatorStack.peek(), val1, val2));
            operatorStack.pop();
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
