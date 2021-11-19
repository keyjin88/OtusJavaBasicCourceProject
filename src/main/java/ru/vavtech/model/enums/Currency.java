package ru.vavtech.model.enums;


import java.util.List;

/**
 * Хранит валюту и окончания простых чисел перед ней (од-ин и дв-а)
 */
public enum Currency {
    CURRENCY(List.of("рубль ", "рубля ", "рублей "),
            List.of("ин ", "а ")),
    PENNY(List.of("копейка", "копейки", "копеек"),
            List.of("на ", "е "));

    private final List<String> currencyValuesArray;
    private final List<String> primeNumbersEndings;

    Currency(List<String> currencyValuesArray, List<String> primeNumbersEndings) {
        this.currencyValuesArray = currencyValuesArray;
        this.primeNumbersEndings = primeNumbersEndings;
    }

    public List<String> getCurrencyValuesArray() {
        return currencyValuesArray;
    }

    public List<String> getPrimeNumbersEndings() {
        return primeNumbersEndings;
    }
}
