package Section_2.src.exercises.ex11;

public class Friends {

    private final static String friends[] = {"Vasile", "Ion", "Andrei"};

    public String[] getFriends() {
        return friends;
    }

    public static void setFriends(String[] f) {
        int i = 0;
        for (String el: f) {
            friends[i] = el;
            i++;
        }
    }
}
