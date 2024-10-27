import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Клас BasicDataOperationUsingSet надає методи для виконання основних операцiй з даними типу LocalDateTime.
 * 
 * <p>Цей клас зчитує данi з файлу "list/LocalDateTime.data", сортує їх та виконує пошук значення в масивi та множинi.</p>
 * 
 * <p>Основнi методи:</p>
 * <ul>
 *   <li>{@link #main(String[])} - Точка входу в програму.</li>
 *   <li>{@link #doDataOperation()} - Виконує основнi операцiї з даними.</li>
 *   <li>{@link #sortArray()} - Сортує масив LocalDateTime.</li>
 *   <li>{@link #searchArray()} - Виконує пошук значення в масивi LocalDateTime.</li>
 *   <li>{@link #findMinAndMaxInArray()} - Знаходить мiнiмальне та максимальне значення в масивi LocalDateTime.</li>
 *   <li>{@link #searchSet()} - Виконує пошук значення в множинi LocalDateTime.</li>
 *   <li>{@link #findMinAndMaxInSet()} - Знаходить мiнiмальне та максимальне значення в множинi LocalDateTime.</li>
 *   <li>{@link #compareArrayAndSet()} - Порiвнює елементи масиву та множини.</li>
 * </ul>
 * 
 * <p>Конструктор:</p>
 * <ul>
 *   <li>{@link #BasicDataOperationUsingSet(String[])} - iнiцiалiзує об'єкт з значенням для пошуку.</li>
 * </ul>
 * 
 * <p>Константи:</p>
 * <ul>
 *   <li>{@link #PATH_TO_DATA_FILE} - Шлях до файлу з даними.</li>
 * </ul>
 * 
 * <p>Змiннi екземпляра:</p>
 * <ul>
 *   <li>{@link #dataTimeValueToSearch} - Значення LocalDateTime для пошуку.</li>
 *   <li>{@link #dataTimeArray} - Масив LocalDateTime.</li>
 *   <li>{@link #dataTimeSet} - Множина LocalDateTime.</li>
 * </ul>
 * 
 * <p>Приклад використання:</p>
 * <pre>
 * {@code
 * java BasicDataOperationUsingSet "2024-03-16T00:12:38Z"
 * }
 * </pre>
 */
public class BasicDataOperationUsingSet {
    static final String PATH_TO_DATA_FILE = "list/LocalDateTime.data";

    LocalDateTime dataTimeValueToSearch;
    LocalDateTime[] dataTimeArray;
    Set<LocalDateTime> dataTimeSet = new HashSet<>();

    public static void main(String[] args) {  
        BasicDataOperationUsingSet basicDataOperationUsingSet = new BasicDataOperationUsingSet(args);
        basicDataOperationUsingSet.doDataOperation();
    }

    /**
     * Конструктор, який iнiцiалiзує об'єкт з значенням для пошуку.
     * 
     * @param args Аргументи командного рядка, де перший аргумент - значення для пошуку.
     */
    BasicDataOperationUsingSet(String[] args) {
        if (args.length == 0) {
            throw new RuntimeException("Вiдсутнє значення для пошуку");
        }

        String valueToSearch = args[0];
        this.dataTimeValueToSearch = LocalDateTime.parse(valueToSearch, DateTimeFormatter.ISO_DATE_TIME);

        dataTimeArray = Utils.readArrayFromFile(PATH_TO_DATA_FILE);
        dataTimeSet = new HashSet<>(Arrays.asList(dataTimeArray));
    }

    /**
     * Виконує основнi операцiї з даними.
     * 
     * Метод зчитує масив та множину об'єктiв LocalDateTime з файлу, сортує їх та виконує пошук значення.
     */
    private void doDataOperation() {
        // операцiї з масивом дати та часу
        searchArray();
        findMinAndMaxInArray();

        sortArray();

        searchArray();
        findMinAndMaxInArray();

        // операцiї з HashSet дати та часу
        searchSet();
        findMinAndMaxInSet();
        compareArrayAndSet();

        // записати вiдсортований масив в окремий файл
        Utils.writeArrayToFile(dataTimeArray, PATH_TO_DATA_FILE + ".sorted");
    }

    /**
     * Сортує масив об'єктiв LocalDateTime та виводить початковий i вiдсортований масиви.
     * Вимiрює та виводить час, витрачений на сортування масиву в наносекундах.
     */
    private void sortArray() {
        long startTime = System.nanoTime();

        Arrays.sort(dataTimeArray);

        Utils.printOperationDuration(startTime, "сортування масиву дати i часу");
    }

    /**
     * Метод для пошуку значення в масивi дати i часу.
     */
    private void searchArray() {
        long startTime = System.nanoTime();

        int index = Arrays.binarySearch(this.dataTimeArray, dataTimeValueToSearch);

        Utils.printOperationDuration(startTime, "пошук в масивi дати i часу");

        if (index >= 0) {
            System.out.println("Значення '" + dataTimeValueToSearch + "' знайдено в масивi за iндексом: " + index);
        } else {
            System.out.println("Значення '" + dataTimeValueToSearch + "' в масивi не знайдено.");
        }
    }

    /**
     * Знаходить мiнiмальне та максимальне значення в масивi LocalDateTime.
     */
    private void findMinAndMaxInArray() {
        if (dataTimeArray == null || dataTimeArray.length == 0) {
            System.out.println("Масив порожнiй або не iнiцiалiзований.");
            return;
        }

        long startTime = System.nanoTime();

        LocalDateTime min = dataTimeArray[0];
        LocalDateTime max = dataTimeArray[0];

        Utils.printOperationDuration(startTime, "пошук мiнiмальної i максимальної дати i часу в масивi");

        for (LocalDateTime dateTime : dataTimeArray) {
            if (dateTime.isBefore(min)) {
                min = dateTime;
            }
            if (dateTime.isAfter(max)) {
                max = dateTime;
            }
        }

        System.out.println("Мiнiмальне значення в масивi: " + min);
        System.out.println("Максимальне значення в масивi: " + max);
    }

    /**
     * Метод для пошуку значення в множинi дати i часу.
     */
    private void searchSet() {
        long startTime = System.nanoTime();

        boolean isFound = this.dataTimeSet.contains(dataTimeValueToSearch);

        Utils.printOperationDuration(startTime, "пошук в HashSet дати i часу");

        if (isFound) {
            System.out.println("Значення '" + dataTimeValueToSearch + "' знайдено в HashSet");
        } else {
            System.out.println("Значення '" + dataTimeValueToSearch + "' в HashSet не знайдено.");
        }
    }

    /**
     * Знаходить мiнiмальне та максимальне значення в множинi LocalDateTime.
     */
    private void findMinAndMaxInSet() {
        if (dataTimeSet == null || dataTimeSet.isEmpty()) {
            System.out.println("HashSet порожнiй або не iнiцiалiзований.");
            return;
        }

        long startTime = System.nanoTime();

        LocalDateTime min = Collections.min(dataTimeSet);
        LocalDateTime max = Collections.max(dataTimeSet);

        Utils.printOperationDuration(startTime, "пошук мiнiмальної i максимальної дати i часу в HashSet");

        System.out.println("Мiнiмальне значення в HashSet: " + min);
        System.out.println("Максимальне значення в HashSet: " + max);
    }

    /**
     * Порiвнює елементи масиву та множини.
     */
    private void compareArrayAndSet() {
        System.out.println("Кiлькiсть елементiв в масивi: " + dataTimeArray.length);
        System.out.println("Кiлькiсть елементiв в HashSet: " + dataTimeSet.size());

        boolean allElementsMatch = true;
        for (LocalDateTime dateTime : dataTimeArray) {
            if (!dataTimeSet.contains(dateTime)) {
                allElementsMatch = false;
                break;
            }
        }

        if (allElementsMatch) {
            System.out.println("Всi елементи масиву присутнi в HashSet.");
        } else {
            System.out.println("Не всi елементи масиву присутнi в HashSet.");
        }
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
        System.out.println("\n>>>>>>>>>> Час виконання операцiї '" + operationName + "': " + duration + " наносекунд");
    }

    /**
     * Зчитує масив об'єктiв LocalDateTime з файлу.
     * 
     * @param pathToFile Шлях до файлу з даними.
     * @return Масив об'єктiв LocalDateTime.
     */
    static LocalDateTime[] readArrayFromFile(String pathToFile) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime[] tempArray = new LocalDateTime[1000];
        int index = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                LocalDateTime dateTime = LocalDateTime.parse(line, formatter);
                tempArray[index++] = dateTime;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        LocalDateTime[] finalArray = new LocalDateTime[index];
        System.arraycopy(tempArray, 0, finalArray, 0, index);

        return finalArray;
    }

    /**
     * Записує масив об'єктiв LocalDateTime у файл.
     * 
     * @param dataTimeArray Масив об'єктiв LocalDateTime.
     * @param pathToFile Шлях до файлу для запису.
     */
    static void writeArrayToFile(LocalDateTime[] dataTimeArray, String pathToFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathToFile))) {
            for (LocalDateTime dateTime : dataTimeArray) {
                writer.write(dateTime.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}