package ru.vavtech.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.vavtech.model.enums.Currency;
import ru.vavtech.service.InputNumberService;
import ru.vavtech.service.InputNumberServiceImpl;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class InputNumberTest {

    InputNumberService inputNumberService = new InputNumberServiceImpl();

    @ParameterizedTest
    @MethodSource("ru.vavtech.model.InputNumberTest#getArgumentsForGetCurrencyIndexForIntegerPart")
    @DisplayName("Тестирование правильного получения индекса окончаний и строки для рублей")
    void getCurrencyIndexForIntegerPart(double initialValue, int resultIndex, String resultString) {
        var number1 = inputNumberService.convertToInputNumber(BigDecimal.valueOf(initialValue));
        var currencyIndex = inputNumberService.getCurrencyIndex(number1);
        assertThat(currencyIndex).isEqualTo(resultIndex);
        assertThat(Currency.RUBLES.getCurrencyValuesArray().get(currencyIndex)).isEqualTo(resultString);
    }

    static Stream<Arguments> getArgumentsForGetCurrencyIndexForIntegerPart() {
        return Stream.of(
                Arguments.of(51, 0, "рубль "),
                Arguments.of(54, 1, "рубля "),
                Arguments.of(58, 2, "рублей ")
        );
    }

    @ParameterizedTest
    @MethodSource("ru.vavtech.model.InputNumberTest#getArgumentsForGetCurrencyIndexForDecimalPart")
    @DisplayName("Тестирование правильного получения индекса окончаний и строки для копеек")
    void getCurrencyIndexForDecimalPart(double initialValue, int resultIndex, String resultString) {
        var number1 = inputNumberService.convertToInputNumber(BigDecimal.valueOf(initialValue));
        var currencyIndex = inputNumberService.getPennyIndex(number1);
        assertThat(currencyIndex).isEqualTo(resultIndex);
        assertThat(Currency.KOPEKS.getCurrencyValuesArray().get(currencyIndex)).isEqualTo(resultString);
    }

    static Stream<Arguments> getArgumentsForGetCurrencyIndexForDecimalPart() {
        return Stream.of(
                Arguments.of(0.91, 0, "копейка"),
                Arguments.of(0.92, 1, "копейки"),
                Arguments.of(0.97, 2, "копеек")
        );
    }
}