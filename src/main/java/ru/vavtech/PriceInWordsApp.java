package ru.vavtech;

import ru.vavtech.model.InputNumber;
import ru.vavtech.service.NumberConverterService;

import java.util.Scanner;

public class PriceInWordsApp {
    public static void main(String[] args) {
        var input = new Scanner(System.in);
        System.out.println("Циферно-словестный преобразователь готов к действию!\nВведите число");
        var number = new InputNumber(input.nextDouble());
        var result = new NumberConverterService().convertNumberToString(number);
        System.out.println("Результат: " + result);
        // TODO: 12.11.2021 Реализовать функционал выхода из приложения.
    }
}
