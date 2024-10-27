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
 * Клас BasicDataOperationUsingSet надає методи для виконання основних операцій з даними типу LocalDateTime.
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
 *   <li>{@link #readSetFromFile()} - Зчитує список LocalDateTime з файлу.</li>
 *   <li>{@link #sortSet()} - Сортує список LocalDateTime.</li>
 *   <li>{@link #searchSet(LocalDateTime)} - Виконує пошук значення в списку LocalDateTime.</li>
 * </ul>
 * 
 * <p>Конструктор:</p>
 * <ul>
 *   <li>{@link #BasicDataOperationUsingSet(String[])} - Ініціалізує об'єкт з значенням для пошуку.</li>
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
 *   <li>{@link #dataTimeSet} - ArrayList LocalDateTime.</li>
 * </ul>
 * 
 * <p>Приклад використання:</p>
 * <pre>
 * {@code
 * java BasicDataOperationUsingSet "2024-03-16T00:12:38Z" > log.txt
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

    BasicDataOperationUsingSet(String[] args) {
        if (args.length == 0) {
            throw new RuntimeException("Відсутнє значення для пошуку");
        }

        String valueToSearch = args[0];
        this.dataTimeValueToSearch = LocalDateTime.parse(valueToSearch, DateTimeFormatter.ISO_DATE_TIME);

        dataTimeArray = Utils.readArrayFromFile(PATH_TO_DATA_FILE);

        dataTimeSet = new HashSet<>(Arrays.asList(dataTimeArray));
    }

    /**
     * Виконує основні операції з даними.
     * 
     * Метод зчитує масив та список об'єктів LocalDateTime з файлу, сортує їх та виконує пошук значення.
     */
    private void doDataOperation() {
        // операції з масивом дати та часу
        searchArray();
        findMinAndMaxInArray();

        sortArray();

        // після сортування масиву
        searchArray();
        findMinAndMaxInArray();

        // операції з HashSet дати та часу
        searchSet();
        findMinAndMaxInSet();

        compareArrayAndSet();
        
        // записати відсортований масив в окремий файл
        Utils.writeArrayToFile(dataTimeArray, PATH_TO_DATA_FILE + ".sorted");
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
    private void sortArray() {
        // вимірюємо час, витрачений на сортування масиву дати і часу 
        long startTime = System.nanoTime();

        Arrays.sort(dataTimeArray);

        Utils.printOperationDuration(startTime, "сортування масиву дати і часу");
    }

    /**
     * Метод для пошуку значення в масиві дати і часу.
     * 
     * @param dataTimeValueToSearch Значення типу LocalDateTime, яке потрібно знайти в масиві.
     * 
     * Метод вимірює час, витрачений на пошук значення в масиві, і виводить його в консоль.
     * Якщо значення знайдено, виводиться індекс знайденого значення в масиві.
     * Якщо значення не знайдено, виводиться відповідне повідомлення.
     */
    private void searchArray() {
        // вимірюємо час, витрачений на пошук в масиві дати і часу
        long startTime = System.nanoTime();
        
        int index = Arrays.binarySearch(this.dataTimeArray, dataTimeValueToSearch);
        
        Utils.printOperationDuration(startTime, "пошук в масиві дати і часу");

        if (index >= 0) {
            System.out.println("Значення '" + dataTimeValueToSearch + "' знайдено в масиві за індексом: " + index);
        } else {
            System.out.println("Значення '" + dataTimeValueToSearch + "' в масиві не знайдено.");
        }
    }

    private void findMinAndMaxInArray() {
        if (dataTimeArray == null || dataTimeArray.length == 0) {
            System.out.println("Масив порожній або не ініціалізований.");
            return;
        }

        // вимірюємо час, витрачений на пошук мінімальної і максимальної дати і часу
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
     * Шукає задане значення дати і часу в списку dataTimeList за допомогою алгоритму бінарного пошуку.
     * Вимірює час, витрачений на пошук, і виводить його в наносекундах.
     * 
     * @param dataTimeValueToSearch значення дати і часу, яке потрібно знайти в списку
     */
    private void searchSet() {
        // вимірюємо час, витрачений на пошук в списку дати і часу
        long startTime = System.nanoTime();

        boolean isFound = this.dataTimeSet.contains(dataTimeValueToSearch);

        Utils.printOperationDuration(startTime, "пошук в HashSet дати і часу");

        if (isFound) {
            System.out.println("Значення '" + dataTimeValueToSearch + "' знайдено в HashSet");
        } else {
            System.out.println("Значення '" + dataTimeValueToSearch + "' в HashSet не знайдено.");
        }
    }


    private void findMinAndMaxInSet() {
        if (dataTimeSet == null || dataTimeSet.isEmpty()) {
            System.out.println("HashSet порожній або не ініціалізований.");
            return;
        }

        // вимірюємо час, витрачений на пошук мінімальної і максимальної дати і часу
        long startTime = System.nanoTime();

        LocalDateTime min = Collections.min(dataTimeSet);
        LocalDateTime max = Collections.max(dataTimeSet);

        System.out.println("Мінімальне значення в HashSet: " + min);
        System.out.println("Максимальне значення в HashSet: " + max);

        Utils.printOperationDuration(startTime, "пошук мінімальної і максимальної дати і часу в HashSet");
    }

    /**
     * Порівнює масив і HashSet об'єктів LocalDateTime та виводить результати порівняння.
     * Метод виводить кількість елементів в масиві та HashSet, а також перевіряє, чи всі елементи масиву присутні в HashSet.
     */
    private void compareArrayAndSet() {
        System.out.println("Кількість елементів в масиві: " + dataTimeArray.length);
        System.out.println("Кількість елементів в HashSet: " + dataTimeSet.size());

        boolean allElementsMatch = true;
        for (LocalDateTime dateTime : dataTimeArray) {
            if (!dataTimeSet.contains(dateTime)) {
                allElementsMatch = false;
                break;
            }
        }

        if (allElementsMatch) {
            System.out.println("Всі елементи масиву присутні в HashSet.");
        } else {
            System.out.println("Не всі елементи масиву присутні в HashSet.");
        }
    }
}

class Utils {
    static void printOperationDuration(long startTime, String operationName) {
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println(">>>>>>>>>> Час виконання операції '" + operationName + "'': " + duration + " наносекунд");
    }

    /**
     * Зчитує масив об'єктів LocalDateTime з файлу.
     *
     * @return Масив об'єктів LocalDateTime, зчитаних з файлу.
     * @throws IOException Якщо виникає помилка під час читання файлу.
     */
    static LocalDateTime[] readArrayFromFile(String pathToFile) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        // Створюємо тимчасовий масив для зберігання даних
        LocalDateTime[] tempArray = new LocalDateTime[1000]; // Initial size
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

        // Створюємо новий масив з точним розміром (без порожніх елементів)
        LocalDateTime[] finalArray = new LocalDateTime[index];
        System.arraycopy(tempArray, 0, finalArray, 0, index);

        // вивести список дати і часу
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