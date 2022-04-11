package Section_9.src.practices;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Employee implements IEmployee {

    protected final Matcher mat;
    protected String firstName;
    protected String lastName;
    protected LocalDate dob;

    protected static final String REGEX = "(?<lastName>\\w+),\\s*(?<firstName>\\w+),\\s*(?<dob>\\d{1,2}/\\d{1,2}/\\d{4}),\\s*(?<role>\\w+)(?:,\\s*\\{(?<details>.*)\\})\\n";
    public static final Pattern PAT = Pattern.compile(REGEX);

    DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
    public final static NumberFormat moneyFormat = NumberFormat.getCurrencyInstance();

    protected Employee() {
        mat = null;
        lastName = "N/A";
        firstName = "N/A";
        dob = null;
    }

    public Employee(String peopleText) {

        mat = Employee.PAT.matcher(peopleText);
        if (mat.find()) {

            lastName = mat.group("lastName");
            firstName = mat.group("firstName");
            dob = LocalDate.from(dtFormatter.parse(mat.group("dob")));
        }
    }

    public static final IEmployee createEmployee(String employeeText) {
        Matcher peopleMat = Employee.PAT.matcher(employeeText);
        if (peopleMat.find()) {
            return switch (peopleMat.group("role")) {
                case "Programmer" -> new Programmer(employeeText);
                case "Manager" -> new Manager(employeeText);
                case "Analyst" -> new Analyst(employeeText);
                case "CEO" -> new CEO(employeeText);
                default -> () -> 0 ;
            };
        } else return () -> 0;
    }

    @Override
    public int getSalary() {
        return 0;
    }

    public double getBonus() {
        return getSalary() * 1.10;
    }

    @Override
    public String toString() {
        return String.format("{%s %s %s - %s}", lastName, firstName, moneyFormat.format(getSalary()), moneyFormat.format(getBonus()));
    }

    private static final class DummyEmployee extends Employee {
        @Override
        public int getSalary() {
            return super.getSalary();
        }
    }
}
