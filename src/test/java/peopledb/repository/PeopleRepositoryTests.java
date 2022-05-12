package peopledb.repository;

import org.junit.jupiter.api.*;
import peopledb.model.Address;
import peopledb.model.Person;
import peopledb.model.Region;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;

public class PeopleRepositoryTests {

    private Connection connection;
    private PeopleRepository repo;

    @BeforeAll
    void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:~/peopletest;TRACE_LEVEL_SYSTEM_OUT=0".replace("~", System.getProperty("user.home")));
        connection.setAutoCommit(false);
        repo = new PeopleRepository(connection);
    }

    @AfterEach
    void realAfterEach() throws SQLException {
        connection.rollback();
    }

    @AfterAll
    void tearDown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    @Test
    public void canSaveOnePerson() throws SQLException {
        Person john = new Person("Pavel4", "Gorodetchi1", ZonedDateTime.of(1980, 11, 15, 15, 0, 0, 0, ZoneId.of("-6")));
        john.setEmail("asfdsgesfdev");
        john.setSalary(new BigDecimal(1243653));
        Person savedPerson = repo.save(john);
        assertThat(savedPerson.getId()).isGreaterThan(0);
    }

    @Test
    public void canSaveTwoPeople(){
        Person john = new Person("John", "Smith", ZonedDateTime.of(1980, 11, 15, 15, 0, 0, 0, ZoneId.of("-6")));
        Person bobby = new Person("Bobby", "Smith", ZonedDateTime.of(1992, 9, 13, 15, 0, 0, 0, ZoneId.of("-6")));
        Person savedPerson1 = repo.save(john);
        Person savedPerson2 = repo.save(bobby);
        assertThat(savedPerson1.getId()).isNotEqualTo(savedPerson2.getId());
    }

    @Test
    public void canSavePersonWithHomeAddress(){
        Person john = new Person("Pavel", "Smith", ZonedDateTime.of(1980, 11, 15, 15, 0, 0, 0, ZoneId.of("-6")));
        Address address = new Address(null, "123 Beale St.", "Apt. 1A", "Wala wala", "WA", "90210", "United States", "Fulton Country", Region.WEST);
        john.setHomeAddress(address);
        Person savedPerson = repo.save(john);
        assertThat(savedPerson.getHomeAddress().get().ID()).isGreaterThan(0);
    }

    @Test
    public void canSavePersonWithBizAddress(){
        Person john = new Person("Pavel2", "Smith2", ZonedDateTime.of(1980, 11, 15, 15, 0, 0, 0, ZoneId.of("-6")));
        Address address = new Address(null, "123 Beale St.", "Apt. 1A", "Wala wala", "WA", "90210", "United States", "Fulton Country", Region.WEST);
        john.setSecondAddress(address);

        Person savedPerson = repo.save(john);
        assertThat(savedPerson.getSecondAddress().get().ID()).isGreaterThan(0);
    }

    @Test
    public void canSasePersonWithChildren() throws SQLException {
        Person john = new Person("Pavel", "Smith", ZonedDateTime.of(1980, 11, 15, 15, 0, 0, 0, ZoneId.of("-6")));
        john.addChild(new Person("Johnny", "Smith", ZonedDateTime.of(2000, 10, 10, 15, 0, 0, 0, ZoneId.of("-6"))));
        john.addChild(new Person("Emma", "Smith", ZonedDateTime.of(2010, 3, 10, 15, 0, 0, 0, ZoneId.of("-6"))));
        john.addChild(new Person("Jenny", "Smith", ZonedDateTime.of(2015, 5, 10, 15, 0, 0, 0, ZoneId.of("-6"))));

        Person savedPerson = repo.save(john);
        savedPerson.getChildren().stream()
                        .map(Person::getId)
                                .forEach(ID -> assertThat(ID).isGreaterThan(0));
//        connection.commit();
    }

    @Test
    public void canFindPersonWithHomeAddress() {

        Person john = new Person("Pavel5", "Smith", ZonedDateTime.of(1980, 11, 15, 15, 0, 0, 0, ZoneId.of("-6")));
        Address address = new Address(null, "123 Beale St.", "Apt. 1A", "Wala wala", "WA", "90210", "United States", "Fulton Country", Region.WEST);
        john.setHomeAddress(address);
        Person savedPerson = repo.save(john);
        Person foundPerson = repo.findById(savedPerson.getId());
        assertThat(foundPerson.getHomeAddress().get().state()).isEqualTo("WA");
    }

    @Test
    public void canFindPersonByIdWithChildren() {

        Person john = new Person("Pavel", "Smith", ZonedDateTime.of(1980, 11, 15, 15, 0, 0, 0, ZoneId.of("-6")));
        john.addChild(new Person("Johny", "Smith", ZonedDateTime.of(2000, 10, 10, 15, 0, 0, 0, ZoneId.of("-6"))));
        john.addChild(new Person("Emma", "Smith", ZonedDateTime.of(2010, 3, 10, 15, 0, 0, 0, ZoneId.of("-6"))));
        john.addChild(new Person("Jenny", "Smith", ZonedDateTime.of(2015, 5, 10, 15, 0, 0, 0, ZoneId.of("-6"))));

        Person savedPerson = repo.save(john);
        Person foundPerson = repo.findById(savedPerson.getId());

        foundPerson.getChildren().stream()
                        .forEach(System.out::println);

        assertThat(foundPerson.getChildren().stream().map(Person::getFirstName).collect(toSet()))
                .contains("Johny", "Emma", "Jenny");
    }

    @Test
    public void canFindPersonWithBizAddress() {

        Person john = new Person("Pavel5", "Smith", ZonedDateTime.of(1980, 11, 15, 15, 0, 0, 0, ZoneId.of("-6")));
        Address address = new Address(null, "123 Beale St.", "Apt. 1A", "Wala wala", "WA", "90210", "United States", "Fulton Country", Region.WEST);
        john.setSecondAddress(address);
        Person savedPerson = repo.save(john);
        Person foundPerson = repo.findById(savedPerson.getId());
        assertThat(foundPerson.getSecondAddress().get().state()).isEqualTo("WA");
    }

    @Test
    public void canFindPersonById() {
        Person savedPerson = repo.save(new Person("SOME", "JACK", ZonedDateTime.now()));
        Person foundPerson = repo.findById(savedPerson.getId());
        assertThat(foundPerson).isEqualTo(savedPerson);
    }

    @Test
    public void canFindPersonByIdWithAddress() {
        Person somebody = repo.save(new Person("SOME", "JACK", ZonedDateTime.now()));
        Address address = new Address(null, "123 Beale St.", "Apt. 1A", "Wala wala", "WA", "90210", "United States", "Fulton Country", Region.WEST);
        somebody.setHomeAddress(address);

        Person savedPerson = repo.save(somebody);
        Person foundPerson = repo.findById(savedPerson.getId());
        assertThat(foundPerson.getHomeAddress().get().state()).isEqualTo("WA");
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
    @Disabled
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
        System.out.println("SAVED PERSON ID = " + p1.getId());

        savedPerson.setSalary(new BigDecimal("73000.34"));
        repo.update(savedPerson);

        Person p2 = repo.findById(savedPerson.getId());

        assertThat(p2.getSalary()).isNotEqualTo(p1.getSalary());
    }

    @Test
    @Disabled
    public void loadData() throws IOException, SQLException {

        Files.lines(Path.of("C:\\Users\\pavel\\IdeaProjects\\vallerry_intren\\Section_9\\src\\practices\\Hr5m.csv"))
                .skip(1)
                .map(s -> s.split(","))
                .map(a -> {
                    LocalDate dob = LocalDate.parse(a[10], DateTimeFormatter.ofPattern("M/d/yyyy"));
                    LocalTime tob = LocalTime.parse(a[11], DateTimeFormatter.ofPattern("hh:mm:ss a"));
                    LocalDateTime dtob = LocalDateTime.of(dob, tob);
                    ZonedDateTime zdtob = ZonedDateTime.of(dtob, ZoneId.of("+0"));
                    Person person = new Person(a[2], a[4], zdtob);
                    person.setSalary(new BigDecimal(a[25]));
                    person.setEmail(a[6]);
                    return person;
                })
                .forEach(repo::save);

//        connection.commit();
    }
}


