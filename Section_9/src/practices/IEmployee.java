package Section_9.src.practices;

public class IEmployee {

    private String firstName;
    private String lastName;
    private int age;

    public IEmployee(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    @Override
    public String toString() {
        return "IEmployee{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                '}';
    }
}
