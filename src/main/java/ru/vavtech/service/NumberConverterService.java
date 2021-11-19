package ru.vavtech.service;

import ru.vavtech.model.InputNumber;
import ru.vavtech.model.enums.Currency;

import java.util.ArrayList;
import java.util.List;

import static ru.vavtech.model.enums.NumberConstants.*;

/**
 * Класс обрабатывает полученное число и приводит его к строке
 */
public class NumberConverterService {
    /** Счетчик разрядов числа */
    private int rankCounter;
    /** Индекс окончания числа в зависимости от разряда */
    private int numbersRanksEndingsIndex;

    /**
     * Конвертирует число в строку (финальный метод, собирает в одну строку результаты преобразований остальных методов)
     * @param inputNumber - класс хранящий полученное число
     * @return - строку с результатом преобразования
     */
    public String convertNumberToString(InputNumber inputNumber) {
        if (!isInputNumberInAcceptableRange(inputNumber))
            return "Введенное число выходит за допустимый диапазон";

        StringBuilder numberToString = formStringBuilderOfCurrencyPart(inputNumber);

        if (inputNumber.getDecimalPartLength() > 0) {
            numberToString.append("и ");
            numberToString.append(formStringBuilderOfPennyPart(inputNumber.getConvertedDecimalPart(),
                    inputNumber.getPennyIndex()));
        }
        if (!inputNumber.isPositiveNumber()) {
            numberToString.insert(0, "минус ");
        }
        numberToString.trimToSize();
        return numberToString.toString();
    }

    /**
     * Формирует строковое представление целой части числа (рубли)
     * @param inputNumber - класс хранящий полученное число
     * @return - StringBuilder с результатом
     */
    private StringBuilder formStringBuilderOfCurrencyPart(InputNumber inputNumber) {
        var stringRepresentationOfNumber = new StringBuilder();

        if (inputNumber.getIntegerPart() == 0) {
            stringRepresentationOfNumber.append(ZERO);
        } else {
            stringRepresentationOfNumber.append(convertNumberToString(inputNumber.getIntegerPart(), Currency.RUBLES));
        }
        stringRepresentationOfNumber.append(Currency.RUBLES.getCurrencyValuesArray().get(inputNumber.getCurrencyIndex()));

        return stringRepresentationOfNumber;
    }

    /**
     * Формирует строковое представление дробной части числа (копейки)
     * @param decimalPart - преобразованная к целому числу десятичная часть
     * @param pennyIndex  - индекс десятичной части для правильного формирования строки
     * @return - StringBuilder с результатом
     */
    private StringBuilder formStringBuilderOfPennyPart(long decimalPart, int pennyIndex) {
        var pennyToString = convertNumberToString(decimalPart, Currency.KOPEKS);
        pennyToString.append(Currency.KOPEKS.getCurrencyValuesArray().get(pennyIndex));
        return pennyToString;
    }

    /**
     * Определяет допустимую длину числа для преобразования
     */
    private boolean isInputNumberInAcceptableRange(InputNumber inputNumber) {
        return inputNumber.getNumberLength() <= 15 && inputNumber.getDecimalPartLength() <= 2;
    }

    /**
     * Конвертирует число в строку (добавляет окончания, считает индекс разрядов числа)
     * @param number   - число которое необходимо конвертировать в строку
     * @param currency - валюта с которой нужно выполнять конвертацию
     */
    private StringBuilder convertNumberToString(long number, Currency currency) {
        StringBuilder sb = new StringBuilder();
        while (number > 0) {
            var partOfTheNumber = (int) (number % 1000);
            var result = convertPartNumberToString(partOfTheNumber, currency);

            if (rankCounter > 0)
                sb.insert(0,
                        result + RANKS_ENDINGS.get(Math.min(rankCounter, 2)).get(numbersRanksEndingsIndex));
            else
                sb.insert(0, result);

            number /= 1000;
            rankCounter++;
        }
        rankCounter = 0;
        return sb;
    }

    /**
     * Определяет с помощью какого из методов конвертировать часть числа (до 3-х цифр) в строку
     * @param number   - число которое необходимо конвертировать в строку
     * @param currency - валюта с которой нужно выполнять конвертацию
     * @return - результат в виде строки
     */
    private String convertPartNumberToString(int number, Currency currency) {
        var stringBuffer = new StringBuilder();
        stringBuffer.append(RANKS.get(rankCounter));
        var listOfNumbers = convertNumberToList(number);
        var extraNumber = number % 100;
        if (extraNumber > 10 && extraNumber < 20) {
            convertPartNumberToStringWithExtraNumber(listOfNumbers, stringBuffer);
        } else {
            convertPartNumberToStringWithStandardNumber(currency, listOfNumbers, stringBuffer);
        }
        return stringBuffer.toString();
    }

    /**
     * Совершает преобразование для "исключительного" случая (число кончается на цифры от 11 до 19)
     * @param listOfNumbers - List цифр (не более 3-х)
     * @param stringBuffer  - буфер для записи результата
     */
    private void convertPartNumberToStringWithExtraNumber(List<Integer> listOfNumbers, StringBuilder stringBuffer) {
        var index = listOfNumbers.get(0);
        stringBuffer.insert(0, PRIME_FROM_11_TO_19.get(index));
        if (listOfNumbers.size() > 2) {
            var hundred = listOfNumbers.get(2);
            stringBuffer.insert(0, PRIME_NUMBERS_DOZENS_AND_HUNDREDS.get(2).get(hundred));
        }
        numbersRanksEndingsIndex = 2;
    }

    /**
     * Преобразует поочередно каждую цифру из листа в строку с учетом окончаний для переданной валюты
     * @param currency     - валюта, исходя из которой формируются окончания слов
     * @param listONumbers - List с цифрами (не более 3-х)
     * @param stringBuffer - буфер для сохранения результата
     */
    private void convertPartNumberToStringWithStandardNumber(Currency currency, List<Integer> listONumbers, StringBuilder stringBuffer) {
        for (var i = 0; i < listONumbers.size(); i++) {
            var index = listONumbers.get(i);
            if (i == 0) {
                switch (index) {
                    case 1 -> {
                        stringBuffer.insert(0, PRIME_NUMBERS_DOZENS_AND_HUNDREDS.get(i).get(index));
                        stringBuffer.insert(2, rankCounter == 1 ? RANKS_ENDINGS.get(i).get(0) : currency.getPrimeNumbersEndings().get(0));
                        numbersRanksEndingsIndex = 0;
                    }
                    case 2 -> {
                        stringBuffer.insert(0, PRIME_NUMBERS_DOZENS_AND_HUNDREDS.get(i).get(index));
                        stringBuffer.insert(2, rankCounter == 1 ? RANKS_ENDINGS.get(i).get(1) : currency.getPrimeNumbersEndings().get(1));
                        numbersRanksEndingsIndex = 1;
                    }
                    case 3, 4 -> {
                        stringBuffer.insert(0, PRIME_NUMBERS_DOZENS_AND_HUNDREDS.get(i).get(index));
                        numbersRanksEndingsIndex = 1;
                    }
                    default -> {
                        stringBuffer.insert(0, PRIME_NUMBERS_DOZENS_AND_HUNDREDS.get(i).get(index));
                        numbersRanksEndingsIndex = 2;
                    }
                }
            } else {
                stringBuffer.insert(0, PRIME_NUMBERS_DOZENS_AND_HUNDREDS.get(i).get(index));
            }
        }
    }

    /**
     * Преобразует число в List состоящий из его цифр
     * @param number - число для преобразования
     * @return - List из числа
     */
    private List<Integer> convertNumberToList(int number) {
        var listOfNumbers = new ArrayList<Integer>(3);
        while (number != 0) {
            listOfNumbers.add(number % 10);
            number /= 10;
        }
        return listOfNumbers;
    }
}
