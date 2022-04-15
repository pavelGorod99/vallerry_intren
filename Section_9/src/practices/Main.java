package Section_9.src.practices;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.stream.IntStream;

public class Main {

    private final static String people = """
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

//    private final static String peopleRegex = "(?<lastName>\\w+),\\s*(?<firstName>\\w+),\\s*(?<dob>\\d{1,2}/\\d{1,2}/\\d{4}),\\s*(?<role>\\w+)(?:,\\s*\\{(?<details>.*)\\})?\\n";
//    final static Pattern peoplePat = Pattern.compile(peopleRegex);
    final static Matcher peopleMat = Employee.PAT.matcher(people);
//    Flyer flyer = new CEO("");

    public final static NumberFormat moneyFormat = NumberFormat.getCurrencyInstance();

    static int totalSalaries = 0;
    private static Set<IEmployee> employees;
    private static Map<String, Integer> mapList;

    public Main() {}

    public static void main(String[] args) {

//        arrayList();

//        linkedList();

//        hashSet();

//        linkedHashSet();

//        treeSet();

//        fillHashMap();
//
//        System.out.println(mapList.entrySet());
//        System.out.println(mapList.size());
//
//        for (Map.Entry<String, Integer> map: mapList.entrySet())
//            System.out.printf("Key = %s Value = %s\n", map.getKey(), map.getValue());

//        List<String> removalNames = createRemovalNamesList();
//        removalNames.sort(Comparator.naturalOrder());
//        System.out.println(removalNames);


//        ################################################################################

//        removeUndesirables(employeeList, removalNames);

//        IEmployee myE =  employeeList.get(5);
//        System.out.println(employeeList.contains(myE));

//        IEmployee NE = Employee.createEmployee("Flinstone5, Fred, 1/1/1900, Programmer, {locpd=3000,yoe=10,iq=144}\n");
//        System.out.println(employeeList.contains(NE));
//
//        System.out.println("EQUAL? " + myE.equals(NE));

//        showEmployeeList(employeeList);

//        for (IEmployee emp: employeeList) {
//            if (emp instanceof Employee) {
//                Employee emp1 = (Employee) emp;
//                if (removalNames.contains(emp1.firstName)) {
//                    System.out.println("Contain: " + emp1.firstName);
//                    employeeList.remove(emp1);
//                }
//            }
//        }
//
//        for (IEmployee emp: employeeList)
//            System.out.println(emp.toString());
//        List<IEmployee> employees = new ArrayList<>();
//        employees.add(new IEmployee("Irina", "Gorod", 40));
//        employees.add(new IEmployee("Vasile", "Coada", 35));
//
//        for (IEmployee emp: employees)
//            System.out.println(emp.toString());
    }

    private static void fillHashMap() {
        mapList = new HashMap<>();
        while (peopleMat.find()) {
            IEmployee e = Employee.createEmployee(peopleMat.group());
            mapList.put(((Employee) e).lastName, e.getSalary());
        }
    }

    static void arrayList() {

        List<IEmployee> employeeList = fillEmployeeList();

        employeeList.sort((o1, o2) -> {
                if (o1 instanceof Employee && o2 instanceof Employee) {
                    int result = ((Employee) o1).lastName.compareTo(((Employee) o2).lastName);
                    return result != 0 ? result : Integer.compare(((Employee) o1).getSalary(), ((Employee) o2).getSalary());
                }
                return 0;
            });

        Collections.sort(employeeList, Comparator.reverseOrder());

        for (IEmployee emp: employeeList)
            System.out.println(emp.toString());

        System.out.printf("\nThe total payout should be %s%n", moneyFormat.format(totalSalaries));
        System.out.println("List size: " + employeeList.size());
    }

    static void linkedList() {

        List<IEmployee> employeeList = fillLinkedList();

        showEmployeeList(employeeList);
    }

    static void hashSet() {
        Set<IEmployee> employeeSetList = fillEmployeeSetList();

        for(IEmployee emp: employeeSetList)
            System.out.println(emp.toString());

        System.out.println("New list size: " + employeeSetList.size());
    }

    static void linkedHashSet() {
        Set<IEmployee> employeeSetList = fillEmployeeLinkedHashSetList();

        for(IEmployee emp: employeeSetList)
            System.out.println(emp.toString());

        System.out.println("New list size: " + employeeSetList.size());
    }

    static void treeSet() {
        Set<IEmployee> employeeSetList = fillEmployeeTreeSetList();

        for(IEmployee emp: employeeSetList)
            System.out.println(emp.toString());

        System.out.println("New list size: " + employeeSetList.size());
    }

    @NotNull
    private static List<String> createRemovalNamesList() {
        return new ArrayList<>(List.of("Flinstone3","Rubble6","Flinstone9"));
    }

    @NotNull
    private static List<IEmployee> fillEmployeeList() {
        IEmployee employee;
        List<IEmployee> employeeList = new ArrayList<>(10);
        while (peopleMat.find()) {
            employee = Employee.createEmployee(peopleMat.group());
            employeeList.add(employee);
            totalSalaries += employee.getSalary();
        }
        return employeeList;
    }

    private static List<IEmployee> fillLinkedList() {
        IEmployee employee;
        List<IEmployee> employeeList = new LinkedList<>();
        while (peopleMat.find()) {
            employee = Employee.createEmployee(peopleMat.group());
            employeeList.add(employee);
            totalSalaries += employee.getSalary();
        }
        return employeeList;
    }

    private static Set<IEmployee> fillEmployeeSetList() {
        Set<IEmployee> employees = new HashSet<>();
        while (peopleMat.find())
            employees.add(Employee.createEmployee(peopleMat.group()));

        return employees;
    }

    private static Set<IEmployee> fillEmployeeLinkedHashSetList() {
        Set<IEmployee> employees = new LinkedHashSet<>();
        while (peopleMat.find())
            employees.add(Employee.createEmployee(peopleMat.group()));

        return employees;
    }

    private static Set<IEmployee> fillEmployeeTreeSetList() {
        employees = new TreeSet<>((o1, o2) -> ((Employee) o1).lastName.compareTo(((Employee) o2).lastName));
        while (peopleMat.find())
            employees.add(Employee.createEmployee(peopleMat.group()));

        return employees;
    }


    private static void showEmployeeList(List<IEmployee> employeeList) {
        for (IEmployee emp: employeeList)
            System.out.println(emp.toString());
    }

    private static void removeUndesirables(List<IEmployee> employeeList, List<String> removalNames) {

        for (Iterator<IEmployee> it = employeeList.iterator(); it.hasNext();) {
            IEmployee worker = it.next();

            if (worker instanceof Employee) {
                Employee tmpWorker = (Employee) worker;
                if (removalNames.contains(tmpWorker.lastName))
                    it.remove();
            }
        }
    }

    //        for (IEmployee iEmployee : employeeList) {
//
//        }
//        employeeList.forEach((iEmployee) -> {
//
//        });
//
//        IntStream.range(0, employeeList.size())
//                .forEach(idx ->
//                        {
//
//                        }
//                )
//        ;

    public int getSalary(String s) {
        return mapList.getOrDefault(s, -1);
    }
}
