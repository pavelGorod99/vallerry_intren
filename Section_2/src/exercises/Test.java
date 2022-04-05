package Section_2.src.exercises;

import Section_2.src.exercises.ex11.Friends;
import Section_2.src.exercises.ex7.Car;
import Section_2.src.exercises.ex10.Math;

public class Test {

    void tic_tack_toe(){

        for (int i = 0, j = 0; i < 3 && j < 3; j++) {

            if (i == j) System.out.print("o\t");
            else if ((j > 0 && i == 0) || (i > 0 && j == 0)) System.out.print("x\t");
            else System.out.print("o\t");

            if (j == 2) {j = -1; i++;System.out.println();}
        }
    }

    void ex5(String ... args) {
        for (String arg: args) {System.out.println("arg: " + arg);}
    }

    static void ex6() {System.out.println("You can access me");}

    void ex7() {
        Car car = new Car();
        System.out.println(car);
    }

    void ex10() {
        System.out.println("Euler number e=" + Math.e);
    }

    void ex11() {}

    public static void main(String[] args) {

        Test t = new Test();
        t.tic_tack_toe();
        t.ex5("1", "2", "3", "4");
        Test.ex6();
        t.ex7();
        t.ex10();
    }
}
