package Section_9.src.practices;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Programmer extends Employee {

    private int linesOfCode = 0;
    private int yearsOfExp = 0;
    private int iq = 0;

    private final String progReex = "\\w+\\=(?<locpd>\\w+)\\,\\w+\\=(?<yoe>\\w+)\\,\\w+\\=(?<iq>\\w+)";
    private final Pattern progPat = Pattern.compile(progReex);

    public Programmer(String peopleText) {
        super(peopleText);
        Matcher progMat = progPat.matcher(mat.group("details"));

        if (progMat.find()) {
            linesOfCode = Integer.parseInt(progMat.group("locpd"));
            yearsOfExp = Integer.parseInt(progMat.group("yoe"));
            iq = Integer.parseInt(progMat.group("iq"));
        }
    }

    public int getSalary() {
        return 3000 + linesOfCode * yearsOfExp * iq;
    }
}
