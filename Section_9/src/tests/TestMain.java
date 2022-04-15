package Section_9.src.tests;

import Section_9.src.practices.Main;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestMain {

    @Test
    public void testNameToSalary() {
        Main test = new Main();
        test.main(new String[0]);
        int salary = test.getSalary("Flinstone113");
        assertEquals(1423000, salary);
    }

    @Test
    public void test2NameToSalary() {
        Main test = new Main();
        test.main(new String[0]);
        int salary = test.getSalary("XXX");
        assertEquals(-1, salary);
    }
}