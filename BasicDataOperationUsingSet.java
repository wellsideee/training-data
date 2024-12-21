import java.io.*;
import java.util.*;

/**
 * Клас BasicDataOperationUsingSet надає методи для виконання основних операцій з даними типу Double.
 * 
 * Цей клас зчитує дані з файлу "list/double.data", сортує їх та виконує пошук значення в масиві та множині.
 */
public class BasicDataOperationUsingSet {
    static final String PATH_TO_DATA_FILE = "list/double.data";

    private final double valueToSearch;
    private Double[] valueArray;
    private Set<Double> valueSet;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Будь ласка, вкажіть значення для пошуку.");
            return;
        }

        try {
            double valueToSearch = Double.parseDouble(args[0]);
            BasicDataOperationUsingSet operation = new BasicDataOperationUsingSet(valueToSearch);
            operation.doDataOperation();
        } catch (NumberFormatException e) {
            System.err.println("Неправильний формат числа: " + args[0]);
        }
    }

    public BasicDataOperationUsingSet(double valueToSearch) {
        this.valueToSearch = valueToSearch;
        this.valueArray = readArrayFromFile(PATH_TO_DATA_FILE);
        this.valueSet = new HashSet<>(Arrays.asList(valueArray));
    }

    private void doDataOperation() {
        searchArray();
        findMinAndMaxInArray();

        sortArray();

        searchArray();
        findMinAndMaxInArray();

        searchSet();
        findMinAndMaxInSet();
        compareArrayAndSet();

        writeArrayToFile(valueArray, PATH_TO_DATA_FILE + ".sorted");
    }

    private void sortArray() {
        long startTime = System.nanoTime();
        Arrays.sort(valueArray);
        printOperationDuration(startTime, "сортування масиву чисел");
    }

    private void searchArray() {
        long startTime = System.nanoTime();
        int index = Arrays.binarySearch(valueArray, valueToSearch);
        printOperationDuration(startTime, "пошук у масиві чисел");

        if (index >= 0) {
            System.out.println("Значення '" + valueToSearch + "' знайдено в масиві за індексом: " + index);
        } else {
            System.out.println("Значення '" + valueToSearch + "' у масиві не знайдено.");
        }
    }

    private void findMinAndMaxInArray() {
        if (valueArray == null || valueArray.length == 0) {
            System.out.println("Масив порожній або не ініціалізований.");
            return;
        }

        long startTime = System.nanoTime();
        double min = Collections.min(Arrays.asList(valueArray));
        double max = Collections.max(Arrays.asList(valueArray));
        printOperationDuration(startTime, "пошук мінімального та максимального значень у масиві");

        System.out.println("Мінімальне значення в масиві: " + min);
        System.out.println("Максимальне значення в масиві: " + max);
    }

    private void searchSet() {
        long startTime = System.nanoTime();
        boolean isFound = valueSet.contains(valueToSearch);
        printOperationDuration(startTime, "пошук у множині чисел");

        if (isFound) {
            System.out.println("Значення '" + valueToSearch + "' знайдено у множині.");
        } else {
            System.out.println("Значення '" + valueToSearch + "' у множині не знайдено.");
        }
    }

    private void findMinAndMaxInSet() {
        if (valueSet == null || valueSet.isEmpty()) {
            System.out.println("Множина порожня або не ініціалізована.");
            return;
        }

        long startTime = System.nanoTime();
        double min = Collections.min(valueSet);
        double max = Collections.max(valueSet);
        printOperationDuration(startTime, "пошук мінімального та максимального значень у множині");

        System.out.println("Мінімальне значення у множині: " + min);
        System.out.println("Максимальне значення у множині: " + max);
    }

    private void compareArrayAndSet() {
        System.out.println("Кількість елементів у масиві: " + valueArray.length);
        System.out.println("Кількість елементів у множині: " + valueSet.size());

        boolean allElementsMatch = valueSet.containsAll(Arrays.asList(valueArray));

        if (allElementsMatch) {
            System.out.println("Усі елементи масиву присутні у множині.");
        } else {
            System.out.println("Не всі елементи масиву присутні у множині.");
        }
    }

    private static Double[] readArrayFromFile(String pathToFile) {
        List<Double> tempList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                tempList.add(Double.parseDouble(line));
            }
        } catch (IOException e) {
            System.err.println("Помилка читання файлу: " + e.getMessage());
        }

        return tempList.toArray(new Double[0]);
    }

    private static void writeArrayToFile(Double[] array, String pathToFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathToFile))) {
            for (Double value : array) {
                writer.write(value.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Помилка запису у файл: " + e.getMessage());
        }
    }

    private static void printOperationDuration(long startTime, String operationName) {
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("Час виконання операції '" + operationName + "': " + duration + " наносекунд");
    }
}

