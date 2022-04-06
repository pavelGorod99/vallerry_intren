package Section_9.src.practices;

import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) {

        List<IEmployee> employees = new ArrayList<>();
        employees.add(new IEmployee("Irina", "Gorod", 40));
        employees.add(new IEmployee("Vasile", "Coada", 35));

        for (IEmployee emp: employees)
            System.out.println(emp.toString());
    }
}
