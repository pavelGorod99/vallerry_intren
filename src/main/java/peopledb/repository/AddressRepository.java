package peopledb.repository;

import peopledb.annotation.SQL;
import peopledb.model.Address;
import peopledb.model.CrudOperation;
import peopledb.model.Region;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

public class AddressRepository extends CrudRepository<Address> {

    public AddressRepository(Connection connection) {
        super(connection);
    }

    @Override
    void mapForUpdate(Address entity, PreparedStatement ps) throws SQLException {

    }

    public static final String SAVE_ADDRESS_SQL = """
                    INSERT INTO ADDRESSES (STREET_ADDRESS, ADDRESS2, CITY, STATE, POSTCODE, COUNTY, REGION, COUNTRY) 
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?)
    """;

    @Override
    @SQL(operationType = CrudOperation.SAVE, value = SAVE_ADDRESS_SQL)
    void mapForSave(Address entity, PreparedStatement ps) throws SQLException {
        ps.setString(1, entity.streetAddress());
        ps.setString(2, entity.address2());
        ps.setString(3, entity.city());
        ps.setString(4, entity.state());
        ps.setString(5, entity.postcode());
        ps.setString(6, entity.county());
        ps.setString(7, entity.region().toString());
        ps.setString(8, entity.country());
    }

    @Override
    @SQL(operationType = CrudOperation.FIND_BY_ID, value = """
            SELECT ID, STREET_ADDRESS, ADDRESS2, CITY, STATE, POSTCODE, COUNTY, REGION, COUNTRY
            FROM ADDRESSES
            WHERE ID = ?
            """)
    Address extractEntityFromResultSet(ResultSet rs) throws SQLException {
        return getAddress(rs, "HOME_");
    }

    public static Address getAddress(ResultSet rs, String aliasPrefix) throws SQLException {
        Long ID = getValueByAlias(aliasPrefix + "ID", rs, Long.class);
        if (ID == null) return null;
        String streetAddress = getValueByAlias(aliasPrefix + "STREET_ADDRESS", rs, String.class);
        String address2 = getValueByAlias(aliasPrefix + "ADDRESS2", rs, String.class);
        String city = getValueByAlias(aliasPrefix + "CITY", rs, String.class);
        String state = getValueByAlias(aliasPrefix + "STATE", rs, String.class);
        String postcode = getValueByAlias(aliasPrefix + "POSTCODE", rs, String.class);
        String county = getValueByAlias(aliasPrefix + "COUNTY", rs, String.class);
        Region region = Region.valueOf(getValueByAlias(aliasPrefix + "REGION", rs, String.class).toUpperCase(Locale.ROOT));
        String country = getValueByAlias(aliasPrefix + "COUNTRY", rs, String.class);
        return new Address(ID, streetAddress, address2, city, state, postcode, country, county, region);
    }


}
