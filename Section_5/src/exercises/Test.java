package Section_5.src.exercises;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Scanner;

public class Test {

    NumberFormat moneyFormatter = NumberFormat.getNumberInstance();

    static void ex11() {
        System.out.println("Ex11");

        String num1 = "37";
        String num2 = "13";

        System.out.println("Sum of " + num1 + " + " + num2 + " = " + (Integer.parseInt(num1) + Integer.parseInt(num2)));
    }

    static void ex8() {
        double num1 = 123456.783;
        System.out.printf("$%,.2f\n", num1);
        String t1 = String.format("$%,.2f\n", num1);
        String decimalFormat1 = new DecimalFormat("$###,###.##").format(num1);

        double num2 = -9876.32532;
        System.out.printf("(%,.3f)\n", Math.abs(num2));
        String t2 = String.format("(%,.3f)\n", num2);

        double num3 = 23.19283928394829182;
        System.out.printf("%ef\n", num3);
        String t3 = String.format("%ef\n", num3);

        int num4 = 123456;
        System.out.printf("%010d\n", num4);
        String t4 = String.format("%010d\n", num4);

        double num5 = -9876.35532;
        System.out.printf("%,.1f\n", num5);
        String t5 = String.format("%,.1f\n", num5);
    }

    public static void main(String[] args) {
        ex11();
        ex8();
    }
}
