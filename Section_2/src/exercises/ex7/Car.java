package Section_2.src.exercises.ex7;

public class Car {

    private String brend = "Mercedes";
    private String model = "AMG CLA 220";
    private int assembledYear = 2015;

    @Override
    public String toString() {
        return "Car{" +
                "brend='" + brend + '\'' +
                ", model='" + model + '\'' +
                ", assembledYear=" + assembledYear +
                '}';
    }
}
