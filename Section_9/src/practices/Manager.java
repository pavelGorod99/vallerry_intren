package Section_9.src.practices;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Manager extends Employee {

    private int orgSize;
    private int dr;

    private final String mgrRegex = "\\w+=(?<orgSize>\\w+),\\w+=(?<dr>\\w+)";
    private final Pattern mgrPat = Pattern.compile(mgrRegex);

    public Manager(String peopleText) {

        super(peopleText);
        Matcher progMat = mgrPat.matcher(mat.group("details"));

        if (progMat.find()) {
            orgSize = Integer.parseInt(progMat.group("orgSize"));
            dr = Integer.parseInt(progMat.group("dr"));
        }
    }

    public int getSalary() {
        return 3000 + orgSize * dr;
    }
}
