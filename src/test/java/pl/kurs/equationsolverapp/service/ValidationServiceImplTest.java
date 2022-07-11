package pl.kurs.equationsolverapp.service;

import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;
import pl.kurs.equationsolverapp.exceptions.InvalidEquationFormatException;
import pl.kurs.equationsolverapp.exceptions.UnknownOperatorException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class ValidationServiceImplTest {

    private ValidationService validationService;

    @Before
    public void init() {
        validationService = new ValidationServiceImpl();
    }

    @Test
    public void shouldReturnValidatedExpressionWhenInputIsCorrect() throws InvalidEquationFormatException, UnknownOperatorException {

        String input = "2+2*3";
        String expectedExpression = "2 + 2 * 3";

        String validatedExpression = validationService.validExpression(input);

        assertEquals(expectedExpression, validatedExpression);

    }

    @Test
    public void shouldThrowInvalidEquationFormatExceptionWhenInputIsNull() throws InvalidEquationFormatException, UnknownOperatorException {

        String input = null;

        Throwable e = assertThrows(InvalidEquationFormatException.class, () -> validationService.validExpression(input));

        SoftAssertions sa = new SoftAssertions();
        sa.assertThat(e).isExactlyInstanceOf(InvalidEquationFormatException.class);
        sa.assertThat(e).hasMessage("Invalid expression!");
        sa.assertAll();

    }

    @Test
    public void shouldThrowInvalidEquationFormatExceptionWhenInputIsEmpty() {

        String input = "";

        Throwable e = assertThrows(InvalidEquationFormatException.class, () -> validationService.validExpression(input));

        SoftAssertions sa = new SoftAssertions();
        sa.assertThat(e).isExactlyInstanceOf(InvalidEquationFormatException.class);
        sa.assertThat(e).hasMessage("Invalid expression!");
        sa.assertAll();

    }

    @Test
    public void shouldThrowInvalidEquationFormatExceptionWhenInputContainsLiterals() {

        String input = "2a+3";

        Throwable e = assertThrows(InvalidEquationFormatException.class, () -> validationService.validExpression(input));

        SoftAssertions sa = new SoftAssertions();
        sa.assertThat(e).isExactlyInstanceOf(InvalidEquationFormatException.class);
        sa.assertThat(e).hasMessage("Invalid expression!");
        sa.assertAll();

    }

    @Test
    public void shouldThrowUnknownOperatorExceptionWhenInputHasUnsupportedOperators() {

        String input = "(2+2)*3";

        Throwable e = assertThrows(UnknownOperatorException.class, () -> validationService.validExpression(input));

        SoftAssertions sa = new SoftAssertions();
        sa.assertThat(e).isExactlyInstanceOf(UnknownOperatorException.class);
        sa.assertThat(e).hasMessage("Invalid operator!");
        sa.assertAll();

    }

}