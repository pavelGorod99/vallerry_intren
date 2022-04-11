package Section_9.src.practices;

import java.text.NumberFormat;
import java.util.regex.Matcher;

public class Test {

    private final static String people = """
            Flinstone1, Fred, 1/1/1900, Programmer, {locpd=2000,yoe=10,iq=140}
            Flinstone1, Fred, 1/1/1900, Programmerzz, {locpd=2000,yoe=10,iq=140}
            Rubble2, Barney, 2/2/1905, Programmer, {locpd=2000,yoe=10,iq=141}
            Flinstone3, Wilma, 3/3/1910, Programmer, {locpd=1000,yoe=10,iq=142}
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
    Flyer flyer = new CEO("");

    public final static NumberFormat moneyFormat = NumberFormat.getCurrencyInstance();

    public static void main(String[] args) {

        int totalSalaries = 0;
        IEmployee employee = null;
        while (peopleMat.find()) {
            employee = Employee.createEmployee(peopleMat.group());
            System.out.println(employee.toString());
            totalSalaries += employee.getSalary();
        }


//        List<IEmployee> employees = new ArrayList<>();
//        employees.add(new IEmployee("Irina", "Gorod", 40));
//        employees.add(new IEmployee("Vasile", "Coada", 35));
//
//        for (IEmployee emp: employees)
//            System.out.println(emp.toString());
    }
}
