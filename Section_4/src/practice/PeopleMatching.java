package Section_4.src.practice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PeopleMatching {

    public static void main(String[] args) {

        String people = """
                Jhon, Rembo, 1/1/1900
                Arnold, Schwarznegger, 2/2/1905
                Mam, Tereza, 3/3/1900
                Mihai, Eminescu, 5/5/1900
                """;

        String regex = "(?<lastName>\\w+),\\s*(?<firstName>\\w+),\\s*(?<dob>\\d{1,2}/\\d{1,2}/\\d{4})\\n";
        Pattern pat = Pattern.compile(regex);
        Matcher mat = pat.matcher(people);

        mat.find(25);
        System.out.println( mat.group("firstName") + "\n" +
                mat.group("lastName") + "\n" +
                mat.group("dob") + "\n" +
                mat.start("firstName") + "\n" +
                mat.end("firstName"));

    }
}
