package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CurrencyConverter_TrainingTest {

    @Test
    void testApiRateIsPositive() {

        CurrencyConverter_Training converter = new CurrencyConverter_Training();
        double rate = converter.getExchangeRate("EUR", "USD");
        assertTrue(rate > 0, "Курс должен быть положительным");
    }
}
