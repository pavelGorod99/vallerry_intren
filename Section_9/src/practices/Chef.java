package Section_9.src.practices;

public interface Chef {

    default void cook(String food) {
        System.out.println("I'm cooking " + food);
    }

    default String skill() {
        return "I have cooking skill";
    }
}
