package peopledb.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import peopledb.model.Person;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.Assert.assertThat;

public class PeopleRepositoryTests {

    private Connection connection;
    private PeopleRepository repo;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:~/peopletest".replace("~", System.getProperty("user.home")));
        connection.setAutoCommit(false);
        repo = new PeopleRepository(connection);
    }

    @AfterEach
    void tearDown() throws SQLException {
        System.out.println(122);
        if (connection != null) {
            connection.close();
        }
    }

    @Test
    public void canSaveOnePerson() throws SQLException {

        Person john = new Person("John", "Smith", ZonedDateTime.of(1980, 11, 15, 15, 0, 0, 0, ZoneId.of("-6")));
        Person savedPerson = repo.save(john);
        assertThat(savedPerson.getId()).isGreaterThan(0);
    }

    @Test
    public void canSaveTwoPeople() throws SQLException {

        Person john = new Person("John", "Smith", ZonedDateTime.of(1980, 11, 15, 15, 0, 0, 0, ZoneId.of("-6")));
        Person bobby = new Person("Bobby", "Smith", ZonedDateTime.of(1992, 9, 13, 15, 0, 0, 0, ZoneId.of("-6")));
        Person savedPerson1 = repo.save(john);
        Person savedPerson2 = repo.save(bobby);
        assertThat(savedPerson1.getId()).isNotEqualTo(savedPerson2.getId());
    }

    @Test
    public void canFindPersonById() {
        Person savedPerson = repo.save(new Person("SOME", "JACK", ZonedDateTime.now()));
        Person foundPerson = repo.findById(savedPerson.getId());
        System.out.println("##################");
        System.out.println(savedPerson);
        System.out.println(foundPerson);
        assertThat(foundPerson).isEqualTo(savedPerson);
    }

    @Test
    public void testPersonIdNotFound() {
        Optional<Person> foundPerson = Optional.ofNullable(repo.findById(-1L));
        assertThat(foundPerson).isEmpty();
    }

    @Test
    public void canDelete() {
        Person savedPerson = repo.save(new Person("SOME", "JACK", ZonedDateTime.now()));
        long startCount = repo.count();
        repo.delete(savedPerson);
        long endCount = repo.count();
        assertThat(endCount).isEqualTo(startCount - 1);
    }

    @Test
    public void canDeleteMultiplePeople() {
        Person p1 = repo.save(new Person("SOME2", "JACK2", ZonedDateTime.now()));
        Person p2 = repo.save(new Person("SOME3", "JACK3", ZonedDateTime.now()));

        repo.delete(p1, p2);
    }

    @Test
    public void experiment() {
        Person p1 = new Person(10L, "SOME3", "JACK3", ZonedDateTime.now());
        Person p2 = new Person(20L, "SOME3", "JACK3", ZonedDateTime.now());
        Person p3 = new Person(30L, "SOME3", "JACK3", ZonedDateTime.now());
        Person p4 = new Person(40L, "SOME3", "JACK3", ZonedDateTime.now());
        Person p5 = new Person(50L, "SOME3", "JACK3", ZonedDateTime.now());

        Person[] people = Arrays.asList(p1, p2, p3, p4, p5).toArray(new Person[]{});

        String ids = Stream.of(people)
                .map(Person::getId)
                .map(String::valueOf)
                .collect(joining(","));
        System.out.println(ids);
    }

    @Test
    public void canUpdate() {
        Person savedPerson = repo.save(new Person("SOME2", "JACK2", ZonedDateTime.now()));
        Person p1 = repo.findById(savedPerson.getId());

        savedPerson.setSalary(new BigDecimal("73000.34"));
        repo.update(savedPerson);

        Person p2 = repo.findById(savedPerson.getId());

        assertThat(p2.getSalary()).isNotEqualTo(p1.getSalary());
    }
}
