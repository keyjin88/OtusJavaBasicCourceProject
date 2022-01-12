package ru.vavtech;

import ru.vavtech.service.NumberConverterService;

import java.math.BigDecimal;
import java.util.Scanner;

public class PriceInWordsApp {

    public static void main(String[] args) {
        var input = new Scanner(System.in);
        System.out.println("Циферно-словестный преобразователь готов к действию!\nВведите число");
        var result = new NumberConverterService().convertNumberToString(BigDecimal.valueOf(input.nextDouble()));
        System.out.println("Результат: " + result);
        // TODO: 12.11.2021 Реализовать функционал выхода из приложения.
    }
}
