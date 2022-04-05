package Section_2.src.exercises.ex8;

public class Customer {

    private String name;
    private double initialDeposit;

    public Customer(String name, double initialDeposit) {
        this.name = name;
        this.initialDeposit = initialDeposit;
    }

    void customerMethod() {
        Bank b = new Bank();
        b.vault = 0;
    }
}
