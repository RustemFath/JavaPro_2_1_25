package ru.mystudy;

import ru.mystudy.employee.PositionEnum;
import ru.mystudy.employee.Employee;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainApp {
    public static void main(String[] args) {
        /**
         *  Найдите в списке целых чисел 3-е наибольшее число (пример: 5 2 10 9 4 3 10 1 13 => 10)
         */
        System.out.println("********* Task_01 **********");
        List<Integer> intList = Arrays.asList(5, 2, 10, 9, 4, 3, 10, 1, 13);
        System.out.println(intList);
        Integer maxIntTask01 = intList.stream()
                .sorted(Comparator.reverseOrder())
                .skip(2)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Not found 3 max int"));
        System.out.println("3-е наибольшее число = " + maxIntTask01);

        /**
         * Найдите в списке целых чисел 3-е наибольшее «уникальное» число (пример: 5 2 10 9 4 3 10 1 13 => 9,
         * в отличие от прошлой задачи здесь разные 10 считает за одно число)
         */
        System.out.println("********* Task_02 **********");
        System.out.println(intList);
        Integer maxIntTask02 = intList.stream()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(2)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Not found 3 max unique int"));
        System.out.println("3-е наибольшее «уникальное» число = " + maxIntTask02);

         /**
          * Имеется список объектов типа Сотрудник (имя, возраст, должность),
          * необходимо получить список имен 3 самых старших сотрудников с должностью «Инженер»,
          * в порядке убывания возраста
          */
        System.out.println("********* Task_03 **********");
        List<Employee> employeeList = Arrays.asList(
                new Employee("Ivan", 26, PositionEnum.BOOKER),
                new Employee("Alex", 42, PositionEnum.ENGINEER),
                new Employee("Victor", 34, PositionEnum.WORKER),
                new Employee("German", 56, PositionEnum.ENGINEER),
                new Employee("Petr", 38, PositionEnum.ENGINEER),
                new Employee("Dmitry", 22, PositionEnum.WORKER),
                new Employee("Vladimir", 39, PositionEnum.ENGINEER),
                new Employee("Roman", 27, PositionEnum.WORKER)
        );
        List<String> nameEngineerList = employeeList.stream()
                .filter(employee -> employee.getPosition() == PositionEnum.ENGINEER)
                .sorted(Comparator.comparing(Employee::getAge, Comparator.reverseOrder()))
                .limit(3)
                .map(Employee::getName)
                .toList();
        System.out.println("3 самых старших сотрудников с должностью «Инженер»: " + nameEngineerList);

         /**
         * Имеется список объектов типа Сотрудник (имя, возраст, должность),
         * посчитайте средний возраст сотрудников с должностью «Инженер»
         */
        System.out.println("********* Task_04 **********");
        OptionalDouble average = employeeList.stream()
                .filter(employee -> employee.getPosition() == PositionEnum.ENGINEER)
                .mapToInt(Employee::getAge)
                .average();
        if (average.isPresent()) {
            System.out.println("Средний возраст сотрудников с должностью «Инженер»: " + average.getAsDouble());
        } else {
            System.out.println("Not found employees with position ENGINEER");
        }

         /**
         * Найдите в списке слов самое длинное
         */
        System.out.println("********* Task_05 **********");
        List<String> wordList = Arrays.asList("list", "array", "collection", "abstractList", "hashmap", "hashset", "linkedArray", "set", "say");
        Optional<String> word05 = wordList.stream()
                .max(Comparator.comparing(String::length));
        if (word05.isPresent()) {
            System.out.println("Самое длинное слово: " + word05.get());
        } else {
            System.out.println("list of words is empty");
        }

         /**
         * Имеется строка с набором слов в нижнем регистре, разделенных пробелом. Постройте хеш-мапы,
         * в которой будут хранится пары: слово - сколько раз оно встречается во входной строке
         */
        System.out.println("********* Task_06 **********");
        String str06 = "returns true if this list contains the specified element more formally returns true if and only if this list contains at least one element such that";
        Map<String, Long> map06 = Stream.of(str06.split(" "))
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));
        System.out.println("Map: " + map06);

         /**
         * Отпечатайте в консоль строки из списка в порядке увеличения длины слова,
         * если слова имеют одинаковую длины, то должен быть сохранен алфавитный порядок
         */
        System.out.println("********* Task_07 **********");
        wordList.stream()
                .sorted(Comparator.comparing(String::length).thenComparing(String::toString))
                .forEach(System.out::println);

         /**
         * Имеется массив строк, в каждой из которых лежит набор из 5 слов, разделенных пробелом,
         * найдите среди всех слов самое длинное, если таких слов несколько, получите любое из них
         */
        System.out.println("********* Task_08 **********");
        List<String> strArray = Arrays.asList(
                "returns true if this list",
                "contains the 08specified08 element more",
                "formally returns true if and",
                "only if this list contains",
                "at least 09specified09 element such");
        Optional<String> word08 = strArray.stream()
                .map(s -> s.split(" "))
                .flatMap(Stream::of)
                .max(Comparator.comparing(String::length));
        if (word08.isPresent()) {
            System.out.println("Самое длинное слово: " + word05.get());
        } else {
            System.out.println("list of string is empty");
        }
    }
}
