package ru.vavtech.model;

import ru.vavtech.model.enums.Currency;

import java.math.BigDecimal;

public class InputNumber {
    /** Целая часть числа */
    private final long integerPart;
    /** Дробная часть */
    private final BigDecimal decimalPart;
    /** Признак положительного числа */
    private final boolean isPositiveNumber;
    /** Признак наличия дробной части */
    private final boolean isHavingDecimalPart;

    /**
     * Конструктор в котором происходит инициализация служебных полей
     * @param inputNumber входное значение
     */
    public InputNumber(double inputNumber) {
        var number = new BigDecimal(String.valueOf(inputNumber));
        isPositiveNumber = number.signum() > 0;
        integerPart = number.longValue();
        decimalPart = number.subtract(new BigDecimal(integerPart));
        isHavingDecimalPart = decimalPart.doubleValue() != 0;
    }

    public long getIntegerPart() {
        return integerPart;
    }

    public boolean isPositiveNumber() {
        return isPositiveNumber;
    }

    /**
     * Преобразует дробную часть числа в целое число
     * @return преобразованную дробную часть числа (long)
     */
    public long getConvertedDecimalPart() {
        return (long) (decimalPart.doubleValue() * 100);
    }

    /**
     * Метод вычисляет индекс по которому будет строиться окончание валюты
     * {@link Currency#RUBLES}
     * @return индекс элемента в массиве
     */
    public int getCurrencyIndex() {
        return calculateIndex(integerPart);
    }

    /**
     * @return индекс с нужным склонением копеек
     * {@link Currency#KOPEKS}
     */
    public int getPennyIndex() {
        return calculateIndex(getConvertedDecimalPart());
    }

    /**
     * Метод для определения длинны целой части числа
     * @return длину целой части числа
     */
    public int getNumberLength() {
        return String.valueOf(integerPart).length();
    }

    /**
     * Метод для определения длины дробной части
     * @return длину дробной части числа. Если 0, значит передано целове число
     */
    public int getDecimalPartLength() {
        return isHavingDecimalPart ? decimalPart.toPlainString().split("\\.")[1].length() : 0;
    }

    /**
     * Метод вычисляет индекс, необходимый для получения окончания валюты
     * @param number число, для которого высчитывается индекс
     * @return индекс в списке {@link Currency}
     */
    private int calculateIndex(long number) {
        var decimalPlaceOfNumber = (int) (number % 100);
        if (decimalPlaceOfNumber > 10 && decimalPlaceOfNumber < 20) {
            return 2;
        }
        var lastDigitOfNumber = (int) (number % 10);
        return switch (lastDigitOfNumber) {
            case 1 -> 0;
            case 2, 3, 4 -> 1;
            default -> 2;
        };
    }
}
