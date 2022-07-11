package pl.kurs.equationsolverapp.app;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import pl.kurs.equationsolverapp.exceptions.InvalidEquationFormatException;
import pl.kurs.equationsolverapp.exceptions.UnknownOperatorException;
import pl.kurs.equationsolverapp.service.EquationSolverService;

@ComponentScan(basePackages = "pl.kurs")
public class Application {

    public static void main(String[] args) throws InvalidEquationFormatException, UnknownOperatorException {

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Application.class);
        EquationSolverService equationSolverService = ctx.getBean(EquationSolverService.class);

        String input = args[0];
        System.out.println("Wynik: " + equationSolverService.calculateEquation(input));

        ctx.close();
    }
}
