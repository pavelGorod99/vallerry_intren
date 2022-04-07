package Section_4.src.exercises;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

    static void ex3() {
        System.out.println("\nEx3");
        String fullName = "Gorodetchi Pavel";

        String regex = "(?<firstName>\\w+)\\s(?<lastName>\\w+)";
        Pattern pat = Pattern.compile(regex);
        Matcher mat = pat.matcher(fullName);
        if (mat.matches())
            System.out.println( "First name: " + mat.group("firstName") + "\n" +
                                "Last name: " + mat.group("lastName"));
    }

    static void ex4() {
        System.out.println("\nEx4");
        // FORMAT   <street>, <apartment>, <country>, <city>, <postal_code>
        String address = "Str. Suceava 120, ap 418, Republic Of Moldova, Chisinau, MD-2051";
        String address1 = "Strada Vasile Goldis 15, ap. 51, Romania, Alba Iulia, 500321";
        String address2 = "Str. Cirimpei Nicolae 45/2, ap. 2, Romania, Cluj-Napoca, 400154";

        String regex = "(?<fullStreetName>\\w+[.]?\\s*(?<streetName>[\\w+\\s*]*\\d+[/]?\\d*)),\\s*(?<apartment>\\w*[.]?\\s*(?<apartNumber>\\d+)),\\s*(?<country>[\\w+\\s+]*),\\s*(?<city>[\\w\\.?\\s*[-]?]*),\\s*(?<postalCode>\\w*[-]?\\d+)";

        Pattern pat = Pattern.compile(regex);

        Matcher mat = pat.matcher(address);
        showAddress(mat);

        mat = pat.matcher(address1);
        showAddress(mat);

        mat = pat.matcher(address2);
        showAddress(mat);
    }

    static void showAddress(@NotNull Matcher mat) {
        if (mat.matches())
            System.out.println( mat.group("fullStreetName") + " | " +
                    mat.group("apartment") + " | " +
                    mat.group("country") + " | " +
                    mat.group("city") + " | " +
                    mat.group("postalCode"));
    }

    static void ex5() {
        System.out.println("Ex5");
        String email = "pavel.gorodetchi@gmail.com";
        String bu_email = "pavel.gorodetchi@stu.ibu.edu.ba";
        String email_ex1 = "pavel#gorod!{}tchi@gmail.com";
        String email_ex2 = "pavel#gorod!{}tchi@gmail.uab.com";

        String regex = "(?<part1>(\\w+[#!%$‘&+*–/=?^_`.{|}~]*)*)(?<part2>\\@)((?<part3>\\w+)(?<part4>.)(?<part5>(\\w+[.]?)+))";

        Pattern pat = Pattern.compile(regex);

        Matcher mat = pat.matcher(email);
        showEmailAddressParts(mat);

        mat = pat.matcher(bu_email);
        showEmailAddressParts(mat);

        mat = pat.matcher(email_ex1);
        showEmailAddressParts(mat);

        mat = pat.matcher(email_ex2);
        showEmailAddressParts(mat);
    }

    static void showEmailAddressParts(@NotNull Matcher mat) {
        if (mat.matches())
            System.out.println( mat.group("part1") + " " +
                    mat.group("part2") + " " +
                    mat.group("part3") + " " +
                    mat.group("part4") + " " +
                    mat.group("part5"));
    }

    public static void main(String[] args) {
        ex5();
        ex4();
        ex3();
    }
}
