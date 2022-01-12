package ru.vavtech.service;

import ru.vavtech.model.InputNumber;

import java.math.BigDecimal;

public class InputNumberServiceImpl implements InputNumberService {

    @Override
    public InputNumber convertToInputNumber(BigDecimal number) {
        InputNumber result = new InputNumber();
        result.setPositiveNumber(number.signum() > 0);
        result.setIntegerPart(number.longValue());
        result.setDecimalPart(number.subtract(new BigDecimal(result.getIntegerPart())));
        result.setHavingDecimalPart(result.getDecimalPart().doubleValue() != 0);
        return result;
    }

    @Override
    public Long getConvertedDecimalPart(InputNumber number) {
        return (long) (number.getDecimalPart().doubleValue() * 100);
    }

    @Override
    public Integer getCurrencyIndex(InputNumber number) {
        return calculateIndex(number.getIntegerPart());
    }

    @Override
    public Integer getPennyIndex(InputNumber number) {
        return calculateIndex(getConvertedDecimalPart(number));
    }

    @Override
    public Integer getNumberLength(InputNumber number) {
        return String.valueOf(number.getIntegerPart()).length();
    }

    @Override
    public Integer getDecimalPartLength(InputNumber number) {
        return number.isHavingDecimalPart() ? number.getDecimalPart().toPlainString().split("\\.")[1].length() : 0;
    }

    @Override
    public Integer calculateIndex(Long number) {
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
