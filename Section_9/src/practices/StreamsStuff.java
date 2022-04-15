package Section_9.src.practices;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;

public class StreamsStuff {

    private final static String peopleText = """
            Flinstone1, Fred, 1/1/1900, Programmer, {locpd=2000,yoe=10,iq=140}
            Flinstone1, Fred, 1/1/1900, Programmer, {locpd=2000,yoe=10,iq=140}
            Flinstone1, Fred, 1/1/1900, Programmer, {locpd=2000,yoe=10,iq=140}
            Flinstone1, Fred, 1/1/1900, Programmer, {locpd=2000,yoe=10,iq=140}
            Flinstone1, Fred, 1/1/1900, Programmer, {locpd=2000,yoe=10,iq=140}
            Flinstone1, Fred, 1/1/1900, Programmer, {locpd=2000,yoe=10,iq=140}
            Flinstone1, Fred, 1/1/1900, Programmerzz, {locpd=2000,yoe=10,iq=140}
            Rubble2, Barney, 2/2/1905, Programmer, {locpd=2000,yoe=10,iq=141}
            Flinstone113, Wilmama, 3/3/1910, Programmer, {locpd=1000,yoe=10,iq=142}
            Rubble4, Betty, 4/4/1915, Programmer, {locpd=4000,yoe=10,iq=143}
            Flinstone5, Fred, 1/1/1900, Programmer, {locpd=3000,yoe=10,iq=144}
            Rubble6, Barney, 2/2/1905, Manager, {orgSize=200,dr=10}
            Flinstone7, Wilma, 3/3/1910, Manager, {orgSize=100,dr=10}
            Rubble8, Betty, 4/4/1915, Manager, {orgSize=300,dr=10}
            Flinstone9, Fred, 1/1/1900, Manager, {orgSize=400,dr=10}
            Rubble10, Barney, 2/2/1905, Manager, {orgSize=200,dr=10}
            Flinstone11, Wilma, 3/3/1910, Analyst, {projectCount=3}
            Rubble12, Betty, 4/4/1915, Analyst, {projectCount=3}
            Flinstone13, Wilma, 3/3/1910, Analyst, {projectCount=4}
            Rubble14, Betty, 4/4/1915, Analyst, {projectCount=4}
            Flinstone15, Wilma, 3/3/1910, Analyst, {projectCount=3}
            Rubble16, Betty, 4/4/1915, CEO, {avgStockPrice=300}
            """;

    public static void main(String[] args) throws IOException {

        peopleText
                .lines()
                .map(Employee::createEmployee)
                .forEach(System.out::println);

        System.out.println("\n");

        Stream.of("one","two","three","four")
                .map(String::hashCode)
                .forEach(h -> System.out.printf("%h%n", h));

        System.out.println();

        record Car(String name, String model) {}

        Stream.of(new Car("Ford", "Bronco"), new Car("Tesla", "X"), new Car("Tesla", "3"))
                .filter(c -> "Tesla".equals(c.name))
                .forEach(System.out::println);

        System.out.println();

        Stream.of("one", null, "three")
                .filter(s -> s != null)
                .forEach(System.out::println);

        System.out.println();

        IntStream.range(1, 6)
                .mapToObj(String::valueOf)
                .map(n -> n.equals("5") ? n.concat("") : n.concat("-"))
                .forEach(System.out::print);

        System.out.println("\n");

        Arrays.stream(new String[]{"jake", "sam", "john"})
                .map(String::toUpperCase)
                .forEach(System.out::println);

        System.out.println("\n");

//        int sum = Files.lines(Path.of("C:\\Users\\pavel\\IdeaProjects\\vallerry_intren\\Section_9\\src\\practices\\employee.txt"))
//                .map(Employee::createEmployee)
//                .sorted((o1, o2) -> Integer.compare(o1.getSalary(), o2.getSalary()))
//                .mapToInt(StreamsStuff::showEmployeeAndGetSalary)
//                .sum();

        int sum = Files.lines(Path.of("C:\\Users\\pavel\\IdeaProjects\\vallerry_intren\\Section_9\\src\\practices\\employee.txt"))
                .map(Employee::createEmployee)
                .map(e -> (Employee)e)
                .sorted(comparing(Employee::getLastName).thenComparing(Employee::getFirstName)
                                                        .thenComparing(Employee::getSalary).reversed())
                .mapToInt(StreamsStuff::showEmployeeAndGetSalary)
                .sum();

        System.out.println("\n" + Employee.moneyFormat.format(sum));
    }

    public static int showEmployeeAndGetSalary(@NotNull IEmployee e) {
        System.out.println(e.toString());
        return e.getSalary();
    }
}
