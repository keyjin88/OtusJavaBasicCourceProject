package ru.vavtech.service;

import ru.vavtech.model.InputNumber;
import ru.vavtech.model.enums.Currency;

import java.math.BigDecimal;

public interface InputNumberService {

    InputNumber convertToInputNumber(BigDecimal number);

    /**
     * Преобразует дробную часть числа в целое число
     * @param number число пользователя
     * @return преобразованную дробную часть числа (long)
     */
    Long getConvertedDecimalPart(InputNumber number);

    /**
     * Метод вычисляет индекс по которому будет строиться окончание валюты
     * {@link Currency#RUBLES}
     * @param number число пользователя
     * @return индекс элемента в массиве
     */
    Integer getCurrencyIndex(InputNumber number);

    /**
     * Метод для вычисления индекса по которому будут строиться окончания копеек
     * {@link Currency#KOPEKS}
     * @param number число пользователя
     * @return индекс с нужным склонением копеек
     */
    Integer getPennyIndex(InputNumber number);

    /**
     * Метод для определения длинны целой части числа
     * @param number число пользователя
     * @return длину целой части числа
     */
    Integer getNumberLength(InputNumber number);

    /**
     * Метод для определения длины дробной части
     * @param number число пользователя
     * @return Длину дробной части числа. Если 0, значит передано целое число
     */
    Integer getDecimalPartLength(InputNumber number);

    /**
     * Метод вычисляет индекс, необходимый для получения окончания валюты
     * @param number число, для которого высчитывается индекс
     * @return индекс в списке {@link Currency}
     */
    Integer calculateIndex(Long number);
}
