import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Queue;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 * Клас BasicDataOperationUsingQueue надає методи для виконання основних операцiй з даними типу LocalDateTime.
 * 
 * <p>Цей клас зчитує данi з файлу "list/LocalDateTime.data", сортує їх та виконує пошук значення в масивi та черзi.</p>
 * 
 * <p>Основнi методи:</p>
 * <ul>
 *   <li>{@link #main(String[])} - Точка входу в програму.</li>
 *   <li>{@link #doDataOperationWithLambdas()} - Виконує основнi операцiї з даними використовуючі функціональне програмування.</li>
 *   <li>{@link #sortArray()} - Сортує масив LocalDateTime.</li>
 *   <li>{@link #searchArray()} - Виконує пошук значення в масивi LocalDateTime.</li>
 *   <li>{@link #findMinAndMaxInArray()} - Знаходить мiнiмальне та максимальне значення в масивi LocalDateTime.</li>
 *   <li>{@link #searchQueue()} - Виконує пошук значення в черзi LocalDateTime.</li>
 *   <li>{@link #findMinAndMaxInQueue()} - Знаходить мiнiмальне та максимальне значення в черзi LocalDateTime.</li>
 *   <li>{@link #peekAndPollQueue()} - Виконує операцiї peek та poll з чергою LocalDateTime.</li>
 * </ul>
 * 
 * <p>Конструктор:</p>
 * <ul>
 *   <li>{@link #BasicDataOperationUsingQueue(String[])} - iнiцiалiзує об'єкт з значенням для пошуку.</li>
 * </ul>
 * 
 * <p>Константи:</p>
 * <ul>
 *   <li>{@link #PATH_TO_DATA_FILE} - Шлях до файлу з даними.</li>
 * </ul>
 * 
 * <p>Змiннi екземпляра:</p>
 * <ul>
 *   <li>{@link #dateTimeValueToSearch} - Значення LocalDateTime для пошуку.</li>
 *   <li>{@link #dateTimeArray} - Масив LocalDateTime.</li>
 *   <li>{@link #dateTimeQueue} - Черга LocalDateTime.</li>
 * </ul>
 * 
 * <p>Приклад використання:</p>
 * <pre>
 * {@code
 * java BasicDataOperationUsingQueue "2024-03-16T00:12:38Z"
 * }
 * </pre>
 */
public class BasicDataOperationUsingQueue {
    static final String PATH_TO_DATA_FILE = "list/LocalDateTime.data";

    LocalDateTime dateTimeValueToSearch;
    LocalDateTime[] dateTimeArray;
    Queue<LocalDateTime> dateTimeQueue;

    public static void main(String[] args) {  
        BasicDataOperationUsingQueue basicDataOperationUsingQueue = new BasicDataOperationUsingQueue(args);
        basicDataOperationUsingQueue.doDataOperationWithLambdas();
    }

    /**
     * Конструктор, який iнiцiалiзує об'єкт з значенням для пошуку.
     * 
     * @param args Аргументи командного рядка, де перший аргумент - значення для пошуку.
     */
    BasicDataOperationUsingQueue(String[] args) {
        if (args.length == 0) {
            throw new RuntimeException("Вiдсутнє значення для пошуку");
        }

        String valueToSearch = args[0];
        this.dateTimeValueToSearch = LocalDateTime.parse(valueToSearch, DateTimeFormatter.ISO_DATE_TIME);

        dateTimeArray = Utils.readArrayFromFile(PATH_TO_DATA_FILE);

        dateTimeQueue = new PriorityQueue<>(Arrays.asList(dateTimeArray));
    }

    /**
     * Виконує основнi операцiї з даними використовуючі функціональне програмування.
     * 
     * Метод зчитує масив та чергу об'єктiв LocalDateTime з файлу, сортує їх та виконує пошук значення.
     */
    private void doDataOperationWithLambdas() {
        // операцiї з масивом дати та часу
        searchArray();
        findMinAndMaxInArray();

        sortArray();

        searchArray();
        findMinAndMaxInArray();

        // операцiї з Queue дати та часу
        searchQueue();
        findMinAndMaxInQueue();
        peekAndPollQueue();

        // записати вiдсортований масив в окремий файл
        Utils.writeArrayToFile(dateTimeArray, PATH_TO_DATA_FILE + ".sorted");
    }

    /**
     * Сортує масив об'єктiв LocalDateTime та виводить початковий i вiдсортований масиви.
     * Вимiрює та виводить час, витрачений на сортування масиву в наносекундах.
     * 
     * Метод виконує наступнi кроки:
     * <li>1. Виводить початковий масив об'єктiв LocalDateTime.</li>
     * <li>2. Вимiрює час, витрачений на сортування масиву за допомогою Arrays.sort().</li>
     * <li>3. Виводить час, витрачений на сортування масиву в наносекундах.</li>
     * <li>4. Виводить вiдсортований масив об'єктiв LocalDateTime.</li>
     */
    private void sortArray() {
        // вимiрюємо час, витрачений на сортування масиву дати i часу 
        long startTime = System.nanoTime();

        dateTimeArray = Arrays.stream(dateTimeArray)
                              .sorted()
                              .toArray(LocalDateTime[]::new);

        Utils.printOperationDuration(startTime, "сортування масиву дати i часу");
    }

    /**
     * Метод для пошуку значення в масивi дати i часу використовуючі функціональне програмування.
     */
    private void searchArray() {
        // вимiрюємо час, витрачений на пошук в масивi дати i часу
        long startTime = System.nanoTime();

        boolean found = Arrays.stream(dateTimeArray)
                              .anyMatch(dateTime -> dateTime.equals(dateTimeValueToSearch));

        Utils.printOperationDuration(startTime, "пошук в масивi дати i часу");

        System.out.println("Значення " + dateTimeValueToSearch + (found ? " знайдено" : " не знайдено") + " в масиві.");
    }

    /**
     * Знаходить мiнiмальне та максимальне значення в масивi LocalDateTime використовуючі функціональне програмування.
     */
    private void findMinAndMaxInArray() {
        if (dateTimeArray == null || dateTimeArray.length == 0) {
            System.out.println("Масив порожнiй або не iнiцiалiзований.");
            return;
        }

        // вимiрюємо час, витрачений на пошук мiнiмальної i максимальної дати i часу
        long startTime = System.nanoTime();

        LocalDateTime min = Arrays.stream(dateTimeArray)
                                  .min(LocalDateTime::compareTo)
                                  .orElse(null);

        LocalDateTime max = Arrays.stream(dateTimeArray)
                                  .max(LocalDateTime::compareTo)
                                  .orElse(null);

        Utils.printOperationDuration(startTime, "пошук мiнiмальної i максимальної дати i часу в масивi");

        System.out.println("Мiнiмальне значення в масивi: " + min);
        System.out.println("Максимальне значення в масивi: " + max);
    }

    /**
     * Метод для пошуку значення в черзi дати i часу використовуючі функціональне програмування.
     */
    private void searchQueue() {
        // вимiрюємо час, витрачений на пошук в черзi дати i часу
        long startTime = System.nanoTime();

        boolean isFound = dateTimeQueue.stream()
                           .anyMatch(dateTime -> dateTime.equals(dateTimeValueToSearch));

        Utils.printOperationDuration(startTime, "пошук в Queue дати i часу");

        if (isFound) {
            System.out.println("Значення '" + dateTimeValueToSearch + "' знайдено в Queue");
        } else {
            System.out.println("Значення '" + dateTimeValueToSearch + "' в Queue не знайдено.");
        }
    }

    /**
     * Знаходить мiнiмальне та максимальне значення в черзi LocalDateTime використовуючі функціональне програмування.
     */
    private void findMinAndMaxInQueue() {
        if (dateTimeQueue == null || dateTimeQueue.isEmpty()) {
            System.out.println("Queue порожнiй або не iнiцiалiзований.");
            return;
        }

        // вимiрюємо час, витрачений на пошук мiнiмальної i максимальної дати i часу
        long startTime = System.nanoTime();

        LocalDateTime min = dateTimeQueue.stream()
                         .min(LocalDateTime::compareTo)
                         .orElse(null);

        LocalDateTime max = dateTimeQueue.stream()
                         .max(LocalDateTime::compareTo)
                         .orElse(null);

        Utils.printOperationDuration(startTime, "пошук мiнiмальної i максимальної дати i часу в Queue");

        System.out.println("Мiнiмальне значення в Queue: " + min);
        System.out.println("Максимальне значення в Queue: " + max);
    }

    /**
     * Виконує операцiї peek та poll з чергою LocalDateTime.
     */
    private void peekAndPollQueue() {
        if (dateTimeQueue == null || dateTimeQueue.isEmpty()) {
            System.out.println("Queue порожнiй або не iнiцiалiзований.");
            return;
        }

        LocalDateTime firstElement = dateTimeQueue.peek();
        System.out.println("Перший елемент у черзi: " + firstElement);

        firstElement = dateTimeQueue.poll();
        System.out.println("Забрати перший елемент у черзi: " + firstElement);

        firstElement = dateTimeQueue.peek();
        System.out.println("Перший елемент у черзi: " + firstElement);
    }
}

/**
 * Клас Utils мiститить допомiжнi методи для роботи з даними типу LocalDateTime.
 */
class Utils {
    /**
     * Виводить час виконання операцiї в наносекундах.
     * 
     * @param startTime Час початку операцiї в наносекундах.
     * @param operationName Назва операцiї.
     */
    static void printOperationDuration(long startTime, String operationName) {
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("\n>>>>>>>>> Час виконання операцiї '" + operationName + "': " + duration + " наносекунд");
    }

    /**
     * Зчитує масив об'єктiв LocalDateTime з файлу використовуючі функціональне програмування.
     * 
     * @param pathToFile Шлях до файлу з даними.
     * @return Масив об'єктiв LocalDateTime.
     */
    static LocalDateTime[] readArrayFromFile(String pathToFile) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            return br.lines()
                          .map(dataLine -> LocalDateTime.parse(dataLine, formatter))
                          .toArray(LocalDateTime[]::new);
        } catch (IOException e) {
            throw new RuntimeException("Помилка читання даних з файлу: " + pathToFile, e);
        }
    }

    /**
     * Записує масив об'єктiв LocalDateTime у файл.
     * 
     * @param dateTimeArray Масив об'єктiв LocalDateTime.
     * @param pathToFile Шлях до файлу для запису.
     */
    static void writeArrayToFile(LocalDateTime[] dateTimeArray, String pathToFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathToFile))) {
            for (LocalDateTime dateTime : dateTimeArray) {
                writer.write(dateTime.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Помилка запису даних з файлу: " + pathToFile, e);
        }
    }
}