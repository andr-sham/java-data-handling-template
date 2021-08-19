package com.epam.izh.rd.online.service;

import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleRegExpService implements RegExpService {

    /**
     * Метод должен читать файл sensitive_data.txt (из директории resources) и маскировать в нем конфиденциальную информацию.
     * Номер счета должен содержать только первые 4 и последние 4 цифры (1234 **** **** 5678). Метод должен содержать регулярное
     * выражение для поиска счета.
     *
     * @return обработанный текст
     */
    @Override
    public String maskSensitiveData() {
        StringBuilder unMaskData = new StringBuilder();
        try (FileReader fileReader = new FileReader("src/main/resources/sensitive_data.txt")) {
            int i;
            while ((i = fileReader.read()) != -1) {
                unMaskData.append((char) i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Pattern pattern = Pattern.compile("(\\d{4}\\s){4}");
        Matcher matcher = pattern.matcher(unMaskData);
        while (matcher.find()) {
            unMaskData.replace(matcher.start() + 5, matcher.end() - 5, "**** **** ");
        }
        return unMaskData.toString().trim();
    }

    /**
     * Метод должен считыввать файл sensitive_data.txt (из директории resources) и заменять плейсхолдер ${payment_amount} и ${balance} на заданные числа. Метод должен
     * содержать регулярное выражение для поиска плейсхолдеров
     *
     * @return обработанный текст
     */
    @Override
    public String replacePlaceholders(double paymentAmount, double balance) {
        StringBuilder stringBuilder = new StringBuilder();
        try (FileReader fileReader = new FileReader("src/main/resources/sensitive_data.txt")) {
            int i;
            while ((i = fileReader.read()) != -1) {
                stringBuilder.append((char) i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Pattern pattern = Pattern.compile("[$][{]\\w+[}]");
        Matcher matcher = pattern.matcher(stringBuilder);
        while (matcher.find()) {
            if (matcher.group().equals("${payment_amount}")) {
                stringBuilder.replace(matcher.start(), matcher.end(), String.valueOf((int) paymentAmount));
                stringBuilder.setLength(stringBuilder.length() + "${payment_amount}".length() - 1);
            } else {
                stringBuilder.replace(matcher.start(), matcher.end(), String.valueOf((int) balance));
                stringBuilder.setLength(stringBuilder.length() + "${balance}".length() - 1);
            }
        }
        return stringBuilder.toString().trim();
    }
}
