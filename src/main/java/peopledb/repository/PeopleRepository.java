package peopledb.repository;

import peopledb.annotation.SQL;
import peopledb.model.Address;
import peopledb.model.CrudOperation;
import peopledb.model.Person;

import java.math.BigDecimal;
import java.sql.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

import static peopledb.repository.AddressRepository.getAddress;

public class PeopleRepository extends CrudRepository<Person> {

    private AddressRepository addressRepository = null;

    public static final String SAVE_PERSON_SQL = """
        INSERT INTO PEOPLE (FIRST_NAME, LAST_NAME, DOB, SALARY, EMAIL, HOME_ADDRESS, BIZ_ADDRESS, PARENT_ID) 
        VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;
    public static final String FIND_BY_ID_SQL = """
            SELECT
            PARENT.ID AS PARENT_ID, PARENT.FIRST_NAME AS PARENT_FIRST_NAME, PARENT.LAST_NAME AS PARENT_LAST_NAME, PARENT.DOB AS PARENT_DOB, PARENT.SALARY AS PARENT_SALARY, PARENT.EMAIL AS PARENT_EMAIL,
            CHILD.ID AS CHILD_ID, CHILD.FIRST_NAME AS CHILD_FIRST_NAME, CHILD.LAST_NAME AS CHILD_LAST_NAME, CHILD.DOB AS CHILD_DOB, CHILD.SALARY AS CHILD_SALARY, CHILD.EMAIL AS CHILD_EMAIL,
            HOME.ID AS HOME_ID, HOME.STREET_ADDRESS AS HOME_STREET_ADDRESS, HOME.ADDRESS2 AS HOME_ADDRESS2, HOME.CITY AS HOME_CITY, HOME.STATE AS HOME_STATE, HOME.POSTCODE AS HOME_POSTCODE, HOME.COUNTY AS HOME_COUNTY, HOME.REGION AS HOME_REGION, HOME.COUNTRY AS HOME_COUNTRY,
            BIZ.ID AS BIZ_ID, BIZ.STREET_ADDRESS AS BIZ_STREET_ADDRESS, BIZ.ADDRESS2 AS BIZ_ADDRESS2, BIZ.CITY AS BIZ_CITY, BIZ.STATE AS BIZ_STATE, BIZ.POSTCODE AS BIZ_POSTCODE, BIZ.COUNTY AS BIZ_COUNTY, BIZ.REGION AS BIZ_REGION, BIZ.COUNTRY AS BIZ_COUNTRY      \s
            FROM PEOPLE AS PARENT
            LEFT OUTER JOIN PEOPLE AS CHILD ON PARENT.ID = CHILD.PARENT_ID
            LEFT OUTER JOIN ADDRESSES AS HOME ON PARENT.HOME_ADDRESS = HOME.ID
            LEFT OUTER JOIN ADDRESSES AS BIZ ON PARENT.BIZ_ADDRESS = BIZ.ID
            WHERE PARENT.ID = ?;
            """;
    public static final String FIND_ALL_SQL = """
    SELECT 
    PARENT.ID AS PARENT_ID, PARENT.FIRST_NAME AS PARENT_FIRST_NAME, PARENT.LAST_NAME AS PARENT_LAST_NAME, PARENT.DOB AS PARENT_DOB, PARENT.SALARY AS PARENT_SALARY, PARENT.EMAIL AS PARENT_EMAIL 
    FROM PEOPLE AS PARENT
    """;
    public static final String SELECT_COUNT_SQL = "SELECT COUNT(*) FROM PEOPLE";
    public static final String DELETE_SQL = "DELETE  FROM PEOPLE WHERE ID=?";
    public static final String DELETE_IN_SQL = "DELETE FROM PEOPLE WHERE ID IN (:ids)";
    public static final String UPDATE_SQL = "UPDATE PEOPLE SET FIRST_NAME=?, LAST_NAME=?, DOB=?, SALARY=? WHERE ID=?";

    public PeopleRepository(Connection connection) {
        super(connection);
        addressRepository = new AddressRepository(connection);
    }

    @Override
    @SQL(value = FIND_BY_ID_SQL, operationType = CrudOperation.FIND_BY_ID)
    @SQL(value = FIND_ALL_SQL, operationType = CrudOperation.FIND_ALL)
    @SQL(value = SELECT_COUNT_SQL, operationType = CrudOperation.COUNT)
    @SQL(value = DELETE_SQL, operationType = CrudOperation.DELETE_ONE)
    @SQL(value = DELETE_IN_SQL, operationType = CrudOperation.DELETE_MANY)
    Person extractEntityFromResultSet(ResultSet rs) throws SQLException {
        Person parent;
        Person previous_parent = null;
        do {
            parent = extractPerson(rs, "PARENT_").get();

            if (previous_parent != null && parent.equals(previous_parent)) {
                parent = previous_parent;
            }

            Optional<Person> child = extractPerson(rs, "CHILD_");

//            String email = getValueByAlias("EMAIL", rs, String.class);
            Address homeAddress = getAddress(rs, "HOME_");
            Address bizAddress = getAddress(rs, "BIZ_");

//            parent.setEmail(email);
            parent.setHomeAddress(homeAddress);
            parent.setSecondAddress(bizAddress);
            child.ifPresent(parent::addChild);
//            parent.addChild(child);

            if (previous_parent == null || previous_parent.equals(parent)) {
                previous_parent = parent;
            }

        } while (rs.next());
        return parent;
    }

    private Optional<Person> extractPerson(ResultSet rs, String aliasPrefix) throws SQLException {
        Long personId = getValueByAlias(aliasPrefix + "ID", rs, Long.class);
        if (personId == null) {
            return  Optional.empty();
        }
        String firstName = getValueByAlias(aliasPrefix + "FIRST_NAME", rs, String.class);
        String lastName = getValueByAlias(aliasPrefix + "LAST_NAME", rs, String.class);
        ZonedDateTime dob = ZonedDateTime.of(getValueByAlias(aliasPrefix + "DOB", rs, Timestamp.class).toLocalDateTime(), ZoneId.of("+0"));
        BigDecimal salary = getValueByAlias(aliasPrefix + "SALARY", rs, BigDecimal.class);
        Person person = new Person(personId, firstName, lastName, dob, salary);
        return Optional.of(person);
    }

    @Override
    @SQL(value = SAVE_PERSON_SQL, operationType = CrudOperation.SAVE)
    protected void mapForSave(Person entity, PreparedStatement ps) throws SQLException {
        ps.setString(1, entity.getFirstName());
        ps.setString(2, entity.getLastName());
        ps.setTimestamp(3, convertDoubleToTimestamp(entity.getDob()));
        ps.setBigDecimal(4, entity.getSalary());
        ps.setString(5, entity.getEmail());

        addPersonAddress(entity, ps, entity.getHomeAddress(), 6);
        addPersonAddress(entity, ps, entity.getSecondAddress(), 7);

        associateChildWithParent(entity, ps);
    }

    private void associateChildWithParent(Person entity, PreparedStatement ps) throws SQLException {
        Optional<Person> parent = entity.getParent();
        if (parent.isPresent()) {
            ps.setLong(8, parent.get().getId());
        } else {
            ps.setObject(8, null);
        }
    }

    @Override
    protected void postSave(Person entity, long id) {
        entity.getChildren().stream()
                .forEach(this::save);
    }

    private void addPersonAddress(Person entity, PreparedStatement ps, Optional<Address> personAddress, int parameterIndex) throws SQLException {
        Address savedAddress;
        if (personAddress.isPresent()) {
            savedAddress = addressRepository.save(personAddress.get());
            ps.setLong(parameterIndex, savedAddress.ID());
        } else {
            ps.setObject(parameterIndex, null);
        }
    }

    @Override
    @SQL(value = UPDATE_SQL, operationType = CrudOperation.UPDATE)
    protected void mapForUpdate(Person entity, PreparedStatement ps) throws SQLException {
        ps.setString(1, entity.getFirstName());
        ps.setString(2, entity.getLastName());
        ps.setTimestamp(3, convertDoubleToTimestamp(entity.getDob()));
        ps.setBigDecimal(4, entity.getSalary());
    }

    private Timestamp convertDoubleToTimestamp(ZonedDateTime dob) {
        return Timestamp.valueOf(dob.withZoneSameInstant(ZoneId.of("+0")).toLocalDateTime());
    }
}
