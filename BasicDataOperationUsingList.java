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
 * Клас BasicDataOperationUsingList надає методи для виконання основних операцій з даними типу LocalDateTime.
 * 
 * <p>Цей клас зчитує дані з файлу "list/LocalDateTime.data", сортує їх та виконує пошук значення в масиві та списку.</p>
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
 *   <li>{@link #BasicDataOperationUsingList(String[])} - Ініціалізує об'єкт з значенням для пошуку.</li>
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
 * java BasicDataOperationUsingList "2024-03-16T00:12:38Z" > log.txt
 * }
 * </pre>
 */
public class BasicDataOperationUsingList {
    static final String PATH_TO_DATA_FILE = "list/LocalDateTime.data";

    LocalDateTime dataTimeValueToSearch;
    LocalDateTime[] dataTimeArray;
    List<LocalDateTime> dataTimeList;

    public static void main(String[] args) {  
        BasicDataOperationUsingList basicDataOperationUsingList = new BasicDataOperationUsingList(args);
        basicDataOperationUsingList.doDataOperation();
    }

    BasicDataOperationUsingList(String[] args) {
        if (args.length == 0) {
            throw new RuntimeException("Відсутнє значення для пошуку");
        }
        String searchValue = args[0];
        dataTimeValueToSearch = LocalDateTime.parse(searchValue, DateTimeFormatter.ISO_DATE_TIME);

        dataTimeArray = Utils.readArrayFromFile(PATH_TO_DATA_FILE);
        dataTimeList = new ArrayList<>(Arrays.asList(dataTimeArray));
    }

    void doDataOperation() {
        // операції з масивом дати та часу
        searchArray();
        findMinAndMaxInArray();

        sortArray();

        searchArray();
        findMinAndMaxInArray();

        // операції з ArrayList
        searchList();
        findMinAndMaxInList();
        
        sortList();

        searchList();
        findMinAndMaxInList();

        // записати відсортований масив в окремий файл
        Utils.writeArrayToFile(dataTimeArray, PATH_TO_DATA_FILE + ".sorted");
    }

    /**
     * Сортує масив об'єктів LocalDateTime та виводить початковий і відсортований масиви.
     * Вимірює та виводить час, витрачений на сортування масиву в наносекундах.
     */
    void sortArray() {
        long startTime = System.nanoTime();

        Arrays.sort(dataTimeArray);

        Utils.printOperationDuration(startTime, "сортування масиву дати і часу");
    }

    /**
     * Метод для пошуку значення в масиві дати і часу.
     */
    void searchArray() {
        long startTime = System.nanoTime();

        int index = Arrays.binarySearch(this.dataTimeArray, dataTimeValueToSearch);

        Utils.printOperationDuration(startTime, "пошук в масиві дати і часу");

        if (index >= 0) {
            System.out.println("Значення '" + dataTimeValueToSearch + "' знайдено в масиві за індексом: " + index);
        } else {
            System.out.println("Значення '" + dataTimeValueToSearch + "' в масиві не знайдено.");
        }
    }

    void findMinAndMaxInArray() {
        if (dataTimeArray == null || dataTimeArray.length == 0) {
            System.out.println("Масив порожній або не ініціалізований.");
            return;
        }

        long startTime = System.nanoTime();

        LocalDateTime min = dataTimeArray[0];
        LocalDateTime max = dataTimeArray[0];

        for (LocalDateTime dateTime : dataTimeArray) {
            if (dateTime.isBefore(min)) {
                min = dateTime;
            }
            if (dateTime.isAfter(max)) {
                max = dateTime;
            }
        }

        System.out.println("Мінімальне значення в масиві: " + min);
        System.out.println("Максимальне значення в масиві: " + max);

        Utils.printOperationDuration(startTime, "пошук мінімальної і максимальної дати і часу в масиві");
    }

    /**
     * Шукає задане значення дати і часу в списку dataTimeList.
     */
    void searchList() {
        long startTime = System.nanoTime();

        int index = Collections.binarySearch(this.dataTimeList, dataTimeValueToSearch);

        Utils.printOperationDuration(startTime, "пошук мінімальної і максимальної дати і часу в ArrayList");

        if (index >= 0) {
            System.out.println("Значення '" + dataTimeValueToSearch + "' знайдено в ArrayList за індексом: " + index);
        } else {
            System.out.println("Значення '" + dataTimeValueToSearch + "' в ArrayList не знайдено.");
        }
    }

    void findMinAndMaxInList() {
        if (dataTimeList == null || dataTimeList.isEmpty()) {
            System.out.println("ArrayList порожній або не ініціалізований.");
            return;
        }

        long startTime = System.nanoTime();

        LocalDateTime min = Collections.min(dataTimeList);
        LocalDateTime max = Collections.max(dataTimeList);

        System.out.println("Мінімальне значення в ArrayList: " + min);
        System.out.println("Максимальне значення в ArrayList: " + max);

        Utils.printOperationDuration(startTime, "пошук мінімальної і максимальної дати і часу в ArrayList");
    }

    void sortList() {
        long startTime = System.nanoTime();

        Collections.sort(dataTimeList);

        Utils.printOperationDuration(startTime, "сортування ArrayList дати і часу");
    }
}

class Utils {
    static void printOperationDuration(long startTime, String operationName) {
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println(">>>>>>>>>> Час виконання операції '" + operationName + "'': " + duration + " наносекунд");
    }

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

        System.out.println("Початковий список дати і часу:");
        for (LocalDateTime dateTime : finalArray) {
            System.out.println(dateTime);
        }

        return finalArray;
    }

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