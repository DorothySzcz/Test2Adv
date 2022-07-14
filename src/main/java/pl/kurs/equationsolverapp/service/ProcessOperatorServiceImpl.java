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
    public BigDecimal processOperator(char operator, BigDecimal val1, BigDecimal val2) {
        return switch (operator) {
            case '+' -> addService.compute(val1, val2);
            case '-' -> subtractService.compute(val1, val2);
            case '*' -> multiplyService.compute(val1, val2);
            case '/' -> divideService.compute(val1, val2);
            default -> throw new IllegalStateException("Unexpected value: " + operator);
        };
    }

}
