package pl.kurs.equationsolverapp.service;

import org.springframework.stereotype.Service;
import pl.kurs.equationsolverapp.service.operatorsservices.*;

import java.math.BigDecimal;

@Service
public class ProcessOperatorServiceImpl implements ProcessOperatorService {

    private AddService addService;
    private SubtractService subtractService;
    private MultiplyService multiplyService;
    private DivideService divideService;

    public ProcessOperatorServiceImpl(AddService addService, SubtractService subtractService, MultiplyService multiplyService, DivideService divideService) {
        this.addService = addService;
        this.subtractService = subtractService;
        this.multiplyService = multiplyService;
        this.divideService = divideService;
    }

    @Override
    public BigDecimal processOperator(char op, Stack<BigDecimal> valueStack) {

        BigDecimal b = valueStack.peek();
        valueStack.pop();
        BigDecimal a = valueStack.peek();
        valueStack.pop();

        return switch (op) {
            case '+' -> addService.compute(a, b);
            case '-' -> subtractService.compute(a, b);
            case '*' -> multiplyService.compute(a, b);
            case '/' -> divideService.compute(a, b);
            default -> throw new IllegalStateException("Unexpected value: " + op);
        };
    }

}
