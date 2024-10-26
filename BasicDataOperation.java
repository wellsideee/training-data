import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Клас BasicDataOperation надає методи для виконання основних операцій з даними типу LocalDateTime.
 * 
 * <p>Цей клас зчитує дані з файлу "list/date.data", сортує їх та виконує пошук значення в масиві та списку.</p>
 * 
 * <p>Основні методи:</p>
 * <ul>
 *   <li>{@link #main(String[])} - Точка входу в програму.</li>
 *   <li>{@link #doDataOperation()} - Виконує основні операції з даними.</li>
 *   <li>{@link #readArrayFromFile()} - Зчитує масив LocalDateTime з файлу.</li>
 *   <li>{@link #sortArray()} - Сортує масив LocalDateTime.</li>
 *   <li>{@link #searchArray(LocalDateTime)} - Виконує пошук значення в масиві LocalDateTime.</li>
 *   <li>{@link #readListFromFile()} - Зчитує список LocalDateTime з файлу.</li>
 *   <li>{@link #sortList()} - Сортує список LocalDateTime.</li>
 *   <li>{@link #searchList(LocalDateTime)} - Виконує пошук значення в списку LocalDateTime.</li>
 * </ul>
 * 
 * <p>Конструктор:</p>
 * <ul>
 *   <li>{@link #BasicDataOperation(String[])} - Ініціалізує об'єкт з значенням для пошуку.</li>
 * </ul>
 * 
 * <p>Константи:</p>
 * <ul>
 *   <li>{@link #PATH_TO_DATA_FILE} - Шлях до файлу з даними.</li>
 * </ul>
 * 
 * <p>Змінні екземпляра:</p>
 * <ul>
 *   <li>{@link #dataTimeValueToSearch} - Значення LocalDateTime для пошуку.</li>
 *   <li>{@link #dataTimeArray} - Масив LocalDateTime.</li>
 *   <li>{@link #dataTimeList} - ArrayList LocalDateTime.</li>
 * </ul>
 * 
 * <p>Приклад використання:</p>
 * <pre>
 * {@code
 * java BasicDataOperation "2024-03-16T00:12:38Z" > log.txt
 * }
 * </pre>
 */
 public class BasicDataOperation {
    static final String PATH_TO_DATA_FILE = "list/date.data";

    LocalDateTime dataTimeValueToSearch;
    LocalDateTime[] dataTimeArray;
    List<LocalDateTime> dataTimeList = new ArrayList<>();

    public static void main(String[] args) {  
        BasicDataOperation basicDataOperation = new BasicDataOperation(args);
        basicDataOperation.doDataOperation();
    }

    BasicDataOperation(String[] args) {
        if (args.length == 0) {
            throw new RuntimeException("Відсутнє значення для пошуку");
        }
        String searchValue = args[0];
        
        dataTimeValueToSearch = LocalDateTime.parse(searchValue, DateTimeFormatter.ISO_DATE_TIME);
    }

    /**
     * Виконує основні операції з даними.
     * 
     * Метод зчитує масив та список об'єктів LocalDateTime з файлу, сортує їх та виконує пошук значення.
     */
    void doDataOperation() {
        // операції з масивом дати та часу
        dataTimeArray = readArrayFromFile();
        sortArray();
        searchArray(dataTimeValueToSearch);

        // операції з ArrayList
        dataTimeList = readListFromFile();
        sortList();
        searchList(dataTimeValueToSearch);
    }

    /**
     * Зчитує масив об'єктів LocalDateTime з файлу.
     *
     * @return Масив об'єктів LocalDateTime, зчитаних з файлу.
     * @throws IOException Якщо виникає помилка під час читання файлу.
     */
    LocalDateTime[] readArrayFromFile() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        // Створюємо тимчасовий масив для зберігання даних
        LocalDateTime[] tempArray = new LocalDateTime[1000]; // Initial size
        int index = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(PATH_TO_DATA_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                LocalDateTime dateTime = LocalDateTime.parse(line, formatter);
                tempArray[index++] = dateTime;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Створюємо новий масив з точним розміром (без порожніх елементів)
        LocalDateTime[] finalArray = new LocalDateTime[index];
        System.arraycopy(tempArray, 0, finalArray, 0, index);
        return finalArray;
    }

    /**
     * Сортує масив об'єктів LocalDateTime та виводить початковий і відсортований масиви.
     * Вимірює та виводить час, витрачений на сортування масиву в наносекундах.
     * 
     * Метод виконує наступні кроки:
     * <li>1. Виводить початковий масив об'єктів LocalDateTime.</li>
     * <li>2. Вимірює час, витрачений на сортування масиву за допомогою Arrays.sort().</li>
     * <li>3. Виводить час, витрачений на сортування масиву в наносекундах.</li>
     * <li>4. Виводить відсортований масив об'єктів LocalDateTime.</li>
     */
    void sortArray() {
        // вивести масив дати і часу
        System.out.println("Оригінальний масив:");
        for (LocalDateTime dateTime : this.dataTimeArray) {
            System.out.println(dateTime);
        }

        // вимірюємо час, витрачений на сортування масиву дати і часу 
        long startTime = System.nanoTime();
        Arrays.sort(dataTimeArray);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        
        System.out.println("--------------------------------");        
        System.out.println(">>>>>>>>>> Час, витрачений на сортування масиву дати і часу: " + duration + " наносекунд");
        System.out.println("--------------------------------");

        // друкуємо відсортований масив дати і часу
        System.out.println("Відсортований масив дати і часу:");
        for (LocalDateTime dateTime : this.dataTimeArray) {
            System.out.println(dateTime);
        }
    }

    /**
     * Метод для пошуку значення в масиві дати і часу.
     * 
     * @param searchValue Значення типу LocalDateTime, яке потрібно знайти в масиві.
     * 
     * Метод вимірює час, витрачений на пошук значення в масиві, і виводить його в консоль.
     * Якщо значення знайдено, виводиться індекс знайденого значення в масиві.
     * Якщо значення не знайдено, виводиться відповідне повідомлення.
     */
    void searchArray(LocalDateTime searchValue) {
        // вимірюємо час, витрачений на пошук в масиві дати і часу
        long startTime = System.nanoTime();
        
        int index = Arrays.binarySearch(this.dataTimeArray, searchValue);
        
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);

        System.out.println("--------------------------------");
        System.out.println(">>>>>>>>>> Час, витрачений на пошук в масиві дати і часу: " + duration + " наносекунд");
        System.out.println("--------------------------------");

        if (index >= 0) {
            System.out.println("Значення '" + searchValue + "' знайдено в масиві за індексом: " + index);
        } else {
            System.out.println("Значення '" + searchValue + "' в масиві не знайдено.");
        }
    }

    /**
     * Зчитує список об'єктів LocalDateTime з файлу.
     * 
     * @return список об'єктів LocalDateTime, зчитаних з файлу
     * @throws IOException якщо виникає помилка під час читання файлу
     */
    List<LocalDateTime> readListFromFile() {
        List<LocalDateTime> list = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        try (BufferedReader br = new BufferedReader(new FileReader(PATH_TO_DATA_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                LocalDateTime dateTime = LocalDateTime.parse(line, formatter);
                list.add(dateTime);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Метод сортує список об'єктів LocalDateTime, що зберігаються в dataTimeList.
     * 
     * Послідовність виконання методу:
     * <li>1. Виводить початковий список дати і часу.</li>
     * <li>2. Потім вимірюється час, витрачений на сортування списку.</li>
     * <li>3. Вивести цей час, витрачений на сортування списку, на екран.</li>
     * <li>4. Вивести відсортований список дати і часу.</li>
     * 
     * <p>Час сортування вимірюється в наносекундах.</p>
     */
    void sortList() {
        // вивести список дати і часу
        System.out.println("Початковий список дати і часу:");
        for (LocalDateTime dateTime : this.dataTimeList) {
            System.out.println(dateTime);
        }

        // вимірюємо час, витрачений на сортування списку дати і часу
        long startTime = System.nanoTime();

        Collections.sort(this.dataTimeList);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        
        System.out.println("--------------------------------");        
        System.out.println(">>>>>>>>>> Час, витрачений на сортування ArrayList дати і часу: " + duration + " наносекунд");
        System.out.println("--------------------------------");

        // друкуємо відсортований список дати і часу
        System.out.println("Відсортований список дати і часу:");
        for (LocalDateTime dateTime : this.dataTimeList) {
            System.out.println(dateTime);
        }
    }

    /**
     * Шукає задане значення дати і часу в списку dataTimeList за допомогою алгоритму бінарного пошуку.
     * Вимірює час, витрачений на пошук, і виводить його в наносекундах.
     * 
     * @param searchValue значення дати і часу, яке потрібно знайти в списку
     */
    void searchList(LocalDateTime searchValue) {
        // вимірюємо час, витрачений на пошук в списку дати і часу

        long startTime = System.nanoTime();
        int index = Collections.binarySearch(this.dataTimeList, searchValue);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("--------------------------------");
        System.out.println(">>>>>>>>>> Час, витрачений на пошук в ArrayList: " + duration + " наносекунд");        System.out.println("--------------------------------");

        if (index >= 0) {
            System.out.println("Значення '" + searchValue + "' знайдено в ArrayList за індексом: " + index);
        } else {
            System.out.println("Значення '" + searchValue + "' в ArrayList не знайдено.");
        }
    }
}