import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Клас BasicDataOperationUsingList надає методи для виконання основних операцiй з даними типу LocalDateTime.
 * 
 * <p>Цей клас зчитує данi з файлу "list/LocalDateTime.data", сортує їх та виконує пошук значення в масивi та списку.</p>
 * 
 * <p>Основнi методи:</p>
 * <ul>
 *   <li>{@link #main(String[])} - Точка входу в програму.</li>
 *   <li>{@link #doDataOperationWithLambdas()} - Виконує основнi операцiї з даними використовуючі функціональне програмування.</li>
 *   <li>{@link #sortArray()} - Сортує масив LocalDateTime.</li>
 *   <li>{@link #searchArray()} - Виконує пошук значення в масивi LocalDateTime.</li>
 *   <li>{@link #findMinAndMaxInArray()} - Знаходить мiнiмальне та максимальне значення в масивi LocalDateTime.</li>
 *   <li>{@link #sortList()} - Сортує список LocalDateTime.</li>
 *   <li>{@link #searchList()} - Виконує пошук значення в списку LocalDateTime.</li>
 *   <li>{@link #findMinAndMaxInList()} - Знаходить мiнiмальне та максимальне значення в списку LocalDateTime.</li>
 * </ul>
 * 
 * <p>Конструктор:</p>
 * <ul>
 *   <li>{@link #BasicDataOperationUsingList(String[])} - iнiцiалiзує об'єкт з значенням для пошуку.</li>
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
 *   <li>{@link #dateTimeList} - Список LocalDateTime.</li>
 * </ul>
 * 
 * <p>Приклад використання:</p>
 * <pre>
 * {@code
 * java BasicDataOperationUsingList "2024-03-16T00:12:38Z"
 * }
 * </pre>
 */
public class BasicDataOperationUsingList {
    static final String PATH_TO_DATA_FILE = "list/LocalDateTime.data";

    LocalDateTime dateTimeValueToSearch;
    LocalDateTime[] dateTimeArray;
    List<LocalDateTime> dateTimeList;

    public static void main(String[] args) {  
        BasicDataOperationUsingList basicDataOperationUsingList = new BasicDataOperationUsingList(args);
        basicDataOperationUsingList.doDataOperationWithLambdas();
    }

    /**
     * Конструктор, який iнiцiалiзує об'єкт з значенням для пошуку.
     * 
     * @param args Аргументи командного рядка, де перший аргумент - значення для пошуку.
     */
    BasicDataOperationUsingList(String[] args) {
        if (args.length == 0) {
            throw new RuntimeException("Вiдсутнє значення для пошуку");
        }

        String searchValue = args[0];
        dateTimeValueToSearch = LocalDateTime.parse(searchValue, DateTimeFormatter.ISO_DATE_TIME);

        dateTimeArray = Utils.readArrayFromFile(PATH_TO_DATA_FILE);
        dateTimeList = new ArrayList<>(Arrays.asList(dateTimeArray));
    }

    /**
     * Виконує основнi операцiї з даними використовуючі функціональне програмування.
     * 
     * Метод зчитує масив та список об'єктiв LocalDateTime з файлу, сортує їх та виконує пошук значення.
     */
    private void doDataOperationWithLambdas() {
        // операцiї з масивом дати та часу
        searchArray();
        findMinAndMaxInArray();

        sortArray();
        
        searchArray();
        findMinAndMaxInArray();

        // операцiї з ArrayList
        searchList();
        findMinAndMaxInList();

        sortList();

        searchList();
        findMinAndMaxInList();

        // записати вiдсортований масив в окремий файл
        Utils.writeArrayToFile(dateTimeArray, PATH_TO_DATA_FILE + ".sorted");
    }

    /**
     * Сортує масив об'єктiв LocalDateTime та виводить початковий i вiдсортований масиви використовуючі функціональне програмування.
     * Вимiрює та виводить час, витрачений на сортування масиву в наносекундах.
     */
    private void sortArray() {
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
     * Шукає задане значення дати i часу в ArrayList дати i часу використовуючі функціональне програмування.
     */
    void searchList() {
        long startTime = System.nanoTime();

        int index = dateTimeList.stream()
                    .filter(dateTime -> dateTime.equals(dateTimeValueToSearch))
                    .findFirst()
                    .map(dateTimeList::indexOf)
                    .orElse(-1);

        Utils.printOperationDuration(startTime, "пошук в ArrayList дати i часу");        

        if (index >= 0) {
            System.out.println("Значення '" + dateTimeValueToSearch + "' знайдено в ArrayList за iндексом: " + index);
        } else {
            System.out.println("Значення '" + dateTimeValueToSearch + "' в ArrayList не знайдено.");
        }
    }

    /**
     * Знаходить мiнiмальне та максимальне значення в ArrayList дати i часу.
     */
    void findMinAndMaxInList() {
        if (dateTimeList == null || dateTimeList.isEmpty()) {
            System.out.println("ArrayList порожнiй або не iнiцiалiзований.");
            return;
        }

        long startTime = System.nanoTime();

        LocalDateTime min = Collections.min(dateTimeList);
        LocalDateTime max = Collections.max(dateTimeList);

        Utils.printOperationDuration(startTime, "пошук мiнiмальної i максимальної дати i часу в ArrayList");

        System.out.println("Мiнiмальне значення в ArrayList: " + min);
        System.out.println("Максимальне значення в ArrayList: " + max);
    }

    /**
     * Сортує ArrayList об'єктiв LocalDateTime та виводить початковий i вiдсортований списки  використовуючі функціональне програмування.
     */
    void sortList() {
        long startTime = System.nanoTime();

        dateTimeList = dateTimeList.stream()
                       .sorted()
                       .toList();

        Utils.printOperationDuration(startTime, "сортування ArrayList дати i часу");
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