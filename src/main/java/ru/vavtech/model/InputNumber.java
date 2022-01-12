package ru.vavtech.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InputNumber {
    /** Целая часть числа */
    private long integerPart;
    /** Дробная часть */
    private BigDecimal decimalPart;
    /** Признак положительного числа */
    private boolean isPositiveNumber;
    /** Признак наличия дробной части */
    private boolean isHavingDecimalPart;
}
