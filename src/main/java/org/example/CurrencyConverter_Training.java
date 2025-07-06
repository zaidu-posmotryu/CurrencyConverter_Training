package org.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.Scanner;

public class CurrencyConverter_Training {

    static double getExchangeRate(String fromCurrency, String toCurrency) {
        String url = "https://open.er-api.com/v6/latest/" + fromCurrency;
        System.out.println("Выполняется запрос к: " + url);

        Response response = RestAssured
                .given()
                .header("User-Agent", "Mozilla/5.0")
                .get(url);

        String responseBody = response.getBody().asString();
        System.out.println("Ответ от сервера: " + responseBody);

        String result = response.jsonPath().getString("result");
        if (!"success".equalsIgnoreCase(result)) {
            throw new RuntimeException("Ошибка при получении курса валют: " + responseBody);
        }

        Double rate = response.jsonPath().getDouble("rates." + toCurrency);
        if (rate == null) {
            throw new RuntimeException("Не удалось получить курс валюты для " + toCurrency);
        }
        return rate;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("⚠️ Доступны только валюты: EUR, USD, GBP");

        System.out.print("Введите сумму: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Введите исходную валюту (EUR, USD, GBP): ");
        String fromCurrency = scanner.nextLine().trim().toUpperCase();

        if (!fromCurrency.equals("EUR") && !fromCurrency.equals("USD") && !fromCurrency.equals("GBP")) {
            System.out.println("❌ Ошибка: недопустимая исходная валюта. Допустимы только EUR, USD, GBP.");
            return;
        }

        System.out.print("Введите целевую валюту (EUR, USD, GBP): ");
        String toCurrency = scanner.nextLine().trim().toUpperCase();

        if (!toCurrency.equals("EUR") && !toCurrency.equals("USD") && !toCurrency.equals("GBP")) {
            System.out.println("❌ Ошибка: недопустимая целевая валюта. Допустимы только EUR, USD, GBP.");
            return;
        }

        if (fromCurrency.equals(toCurrency)) {
            System.out.printf("Сумма в целевой валюте: %.2f %s%n", amount, toCurrency);
            return;
        }

        double rate = getExchangeRate(fromCurrency, toCurrency);
        double converted = amount * rate;
        System.out.printf("%.2f %s = %.2f %s%n", amount, fromCurrency, converted, toCurrency);
    }
}
