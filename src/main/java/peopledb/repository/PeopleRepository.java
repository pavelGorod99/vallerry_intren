package peopledb.repository;

import peopledb.model.Person;

import java.math.BigDecimal;
import java.sql.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class PeopleRepository extends CRUDRepository<Person> {

    public static final String SAVE_PERSON_SQL = "INSERT INTO PEOPLE (FIRST_NAME, LAST_NAME, DOB) VALUES (?, ?, ?)";
    public static final String FIND_BY_ID_SQL = "SELECT ID, FIRST_NAME, LAST_NAME, DOB, SALARY FROM PEOPLE WHERE ID=?";
    public static final String FIND_ALL_SQL = "SELECT ID, FIRST_NAME, LAST_NAME, DOB, SALARY FROM PEOPLE";

    public PeopleRepository(Connection connection) {
        super(connection);
    }

    @Override
    Person extractEntityFromResultSet(ResultSet rs) throws SQLException {
        long personId = rs.getLong("ID");
        String firstName = rs.getString("FIRST_NAME");
        String lastName = rs.getString("LAST_NAME");
        ZonedDateTime dob = ZonedDateTime.of(rs.getTimestamp("DOB").toLocalDateTime(), ZoneId.of("+0"));
        BigDecimal salary = rs.getBigDecimal("SALARY");
        return new Person(personId, firstName, lastName, dob, salary);
    }

    @Override
    protected String getFindByIdSql() {
        return FIND_BY_ID_SQL;
    }

    @Override
    void mapForSave(Person entity, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, entity.getFirstName());
        preparedStatement.setString(2, entity.getLastName());
        preparedStatement.setTimestamp(3, convertDoubleToTimestamp(entity.getDob()));
    }

    @Override
    String getSaveSql() {
        return SAVE_PERSON_SQL;
    }

//    public List<Person> findAll() {
//        List<Person> people = new ArrayList<>();
//        try {
//            PreparedStatement ps = connection.prepareStatement(FIND_ALL_SQL);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next())
//                people.add(extractEntityFromResultSet(rs));
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return people;
//    }

    @Override
    protected String getFindAllSql() {
        return FIND_ALL_SQL;
    }

//    private Person extractPersonFromResultSet(ResultSet resultSet) throws SQLException {
//        long personId = resultSet.getLong("ID");
//        String firstName = resultSet.getString("FIRST_NAME");
//        String lastName = resultSet.getString("LAST_NAME");
//        ZonedDateTime dob = ZonedDateTime.of(resultSet.getTimestamp("DOB").toLocalDateTime(), ZoneId.of("+0"));
//        BigDecimal salary = resultSet.getBigDecimal("SALARY");
//        return new Person(personId, firstName, lastName, dob, salary);
//    }

    public long count() {
        long count = 0;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM PEOPLE");
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                count = rs.getLong(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public void delete (Person person) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE  FROM PEOPLE WHERE ID=?");
            ps.setLong(1, person.getId());
            int affectedRecordCount = ps.executeUpdate();
            System.out.println(affectedRecordCount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Person ... people) {
//        for (Person person: people) {
//            delete(person);
//        }

        try {
            Statement stmt = connection.createStatement();

            String ids = Stream.of(people)
                    .map(Person::getId)
                    .map(String::valueOf)
                    .collect(joining(","));
            System.out.println(ids);
            int affectedCount = stmt.executeUpdate("DELETE FROM PEOPLE WHERE ID IN (:ids)".replace(":ids", ids));
            System.out.println(affectedCount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Person person) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE PEOPLE SET FIRST_NAME=?, LAST_NAME=?, DOB=?, SALARY=? WHERE ID=?");
            ps.setString(1, person.getFirstName());
            ps.setString(2, person.getLastName());
            ps.setTimestamp(3, convertDoubleToTimestamp(person.getDob()));
            ps.setBigDecimal(4, person.getSalary());
            ps.setLong(5, person.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Timestamp convertDoubleToTimestamp(ZonedDateTime dob) {
        return Timestamp.valueOf(dob.withZoneSameInstant(ZoneId.of("+0")).toLocalDateTime());
    }
}
