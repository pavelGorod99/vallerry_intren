package Section_3.src.exercises;

import java.util.Locale;
import java.util.Scanner;

public class Test {

    Scanner scan = new Scanner(System.in);

    void ex5() {

        String someText = "12345 Big St., Alphabet City, CA 90210";
        String t1[] = someText.split(",");
        String t2[] = t1[0].split(" ", 2);
        System.out.println( "The building number: " + t2[0] + "\n" +
                            "The street: \"" + t2[1] + "\"" + "\n" +
                            "City: \"" + t1[1].trim() + "\"");
        String t3[] = t1[2].trim().split(" ");
        System.out.println( "State: \"" + t3[0] + "\"" + "\n" +
                            "Postal Code: " + t3[1]);
    }

    void ex4() {

        System.out.print("Enter your text: ");
        String text = scan.nextLine();
        System.out.println("Your changed text is: " + text.trim().replace(text.substring(text.length() - 1), text.substring(text.length() - 1).toUpperCase(Locale.ROOT)));
    }

    void ex3() {
        System.out.print("Enter your text: ");
        String text = scan.nextLine();
        System.out.println("Your clean text: " + text.trim());
    }

    public static void main(String[] args) {
        Test t = new Test();
        t.ex5();
        t.ex4();
        t.ex3();
    }
}
