package Section_9.src.practices;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Programmer {

    private String firstName;
    private String lastName;
    private LocalDate dob;

    String regex = "(?<lastName>\\w+),\\s*(?<firstName>\\w+),\\s*(?<dob>\\d{1,2}/\\d{1,2}/\\d{4})\\n";
    Pattern pat = Pattern.compile(regex);
//    Matcher mat = pat.matcher(people);
}
