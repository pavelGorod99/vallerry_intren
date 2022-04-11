package Section_9.src.practices;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Analyst extends Employee {

    private int projectCount;

    private final String analystRegex = "\\w+=(?<projectCount>\\w+)";
    private final Pattern analystPat = Pattern.compile(analystRegex);

    public Analyst(String peopleText) {
        super(peopleText);
        Matcher analystMat = analystPat.matcher(mat.group("details"));

        if (analystMat.find())
            projectCount = Integer.parseInt(analystMat.group("projectCount"));
    }

    public int getSalary() {
        return 3500 * projectCount;
    }
}
