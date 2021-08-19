package com.epam.izh.rd.online.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

public class SimpleBigNumbersService implements BigNumbersService {

    /**
     * Метод делит первое число на второе с заданной точностью
     * Например 1/3 с точностью 2 = 0.33
     *
     * @param range точность
     * @return результат
     */
    @Override
    public BigDecimal getPrecisionNumber(int a, int b, int range) {
        MathContext mathContext = new MathContext(range, RoundingMode.HALF_UP);
        return new BigDecimal(a).divide(new BigDecimal(b), mathContext);
    }

    /**
     * Метод находит простое число по номеру
     *
     * @param range номер числа, считая с числа 2
     * @return простое число
     */
    @Override
    public BigInteger getPrimaryNumber(int range) {
        if (range == 1) {
            return new BigInteger(String.valueOf(2));
        } else {
            int primeCheck = 2;
            for (int i = 2; i <= range + 1; i++) {
                primeCheck++;
                boolean primeFound = false;
                while (!primeFound) {
                    for (int divider = 2; divider <= Math.round(Math.sqrt(primeCheck)); divider++) {
                        if (primeCheck % divider == 0) {
                            primeFound = false;
                            primeCheck++;
                            break;
                        } else {
                            primeFound = true;
                        }
                    }

                }

            }
            return new BigInteger(String.valueOf(primeCheck));
        }

    }
}
