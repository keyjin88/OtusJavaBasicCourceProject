package ru.vavtech.model.enums;

import java.util.List;

/**
 * Хранит строковое представление простых чисел, их разрядов и окончания
 */
public enum Number {
    RANKS(List.of("", "тысяч", "миллион", "миллиард", "триллион")),
    RANKS_ENDINGS(new String[][]{{"на ", "е ", ""},     //тысача в ед. числе
            {"а ", "и ", " "},      //тысача во множ. числе
            {" ", "а ", "ов "}}),   //миллион и выше
    PRIME_NUMBERS_DOZENS_AND_HUNDREDS(new String[][]{{"", "од", "дв", "три ", "четыре ", "пять ", "шесть ", "семь ", "восемь ", "девять "},
            {"", "десять ", "двадцать ", "тридцать ", "сорок ", "пятьдесят ", "шестьдесят ", "семьдесят ", "восемьдесят ", "девяносто "},
            {"", "сто ", "двести ", "триста ", "четыреста ", "пятьсот ", "шестьсот ", "семьсот ", "восемьсот ", "девятьсот "}}),
    PRIME_FROM_11_TO_19(List.of("", "одиннадцать ", "двенадцать ", "тринадцать ",
            "четырнадцать ", "пятнадцать ", "шестнадцать ", "семнадцать ", "восемнадцать ", "девятнадцать ")),
    ZERO("ноль ");

    private List<String> valuesArray;
    private String[][] twoDimensionalValuesArray;
    private String singleValue;

    Number(List<String> numbers) {
        this.valuesArray = numbers;
    }

    Number(String[][] strings) {
        this.twoDimensionalValuesArray = strings;
    }

    Number(String singleValue) {
        this.singleValue = singleValue;
    }

    public List<String> getValuesArray() {
        return valuesArray;
    }

    public String[][] getTwoDimensionalValuesArray() {
        return twoDimensionalValuesArray;
    }

    public String getSingleValue() {
        return singleValue;
    }
}
