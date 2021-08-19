package com.epam.izh.rd.online.repository;

import java.io.*;


public class SimpleFileRepository implements FileRepository {
    public long countFiles = 0;
    public long countDirs = 0;

    /**
     * Метод рекурсивно подсчитывает количество файлов в директории
     *
     * @param path путь до директори
     * @return файлов, в том числе скрытых
     */
    @Override
    public long countFilesInDirectory(String path) {
        File dir = new File("src\\main\\resources\\" + path);
        for (File fileCheck : dir.listFiles()) {
            if (fileCheck.isFile()) {
                countFiles++;
            } else {
                countFilesInDirectory(path + "\\" + fileCheck.getName());
            }
        }
        return countFiles;

    }

    /**
     * Метод рекурсивно подсчитывает количество папок в директории, считая корень
     *
     * @param path путь до директории
     * @return число папок
     */
    @Override
    public long countDirsInDirectory(String path) {
        File dir = new File("src\\main\\resources\\" + path);
        for (File fileCheck : dir.listFiles()) {
            if (fileCheck.isDirectory()) {
                countDirs++;
                countDirsInDirectory(path + "\\" + fileCheck.getName());
            }
        }
        return countDirs + 1;

    }

    /**
     * Метод копирует все файлы с расширением .txt
     *
     * @param from путь откуда
     * @param to   путь куда
     */
    @Override
    public void copyTXTFiles(String from, String to) {
        File fileFrom = new File(from);
        File fileTo = new File(to);
        File dirTo = new File(fileTo.getParent());
        dirTo.mkdirs();
        try (FileReader fileReader = new FileReader(fileFrom); FileWriter fileWriter = new FileWriter(fileTo, true)) {
            fileTo.createNewFile();
            int c;
            while ((c = fileReader.read()) != -1) {
                fileWriter.write((char) c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод создает файл на диске с расширением txt
     *
     * @param path путь до нового файла
     * @param name имя файла
     * @return был ли создан файл
     */
    // файл создаётся, но тест не проходит
    @Override
    public boolean createFile(String path, String name) {
        File newFile = new File(path + "/" + name);
        File dir = new File(path);
        System.out.println(newFile);
        System.out.println(dir);
        try {
            dir.mkdirs();
            return newFile.createNewFile();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * Метод считывает тело файла .txt из папки src/main/resources
     *
     * @param fileName имя файла
     * @return контент
     */
    @Override
    public String readFileFromResources(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try (FileReader fileReader = new FileReader("src/main/resources/" + fileName)) {
            int i;
            while ((i = fileReader.read()) != -1) {
                stringBuilder.append((char) i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }
}
