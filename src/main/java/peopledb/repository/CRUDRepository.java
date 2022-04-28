package peopledb.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

abstract class CRUDRepository<T extends Entity> {

    protected Connection connection;

    public CRUDRepository(Connection connection) {
        this.connection = connection;
    }

    public T save(T entity) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getSaveSql(), Statement.RETURN_GENERATED_KEYS);
            mapForSave(entity, preparedStatement);
            int recordsAffected = preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            while (generatedKeys.next())  {
                long ID = generatedKeys.getLong(1);
                entity.setId(ID);
                System.out.println(entity);
            }
            System.out.println("Records affected: " + recordsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }

    public T findById(Long id) {

        T entity = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getFindByIdSql());
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                entity = extractEntityFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }

    public List<T> findAll() {
        List<T> entity = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(getFindAllSql());
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                entity.add(extractEntityFromResultSet(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }

    protected abstract String getFindAllSql();

    abstract T extractEntityFromResultSet(ResultSet rs) throws SQLException;

    /**
     *
     * @return Returns a String that represents the SQL needed to retrieve one entity
     * The SQL must contain one SQL parameter, i.e. "?", that will bind to the
     * entity's ID.
     */
    protected abstract String getFindByIdSql();

    abstract void mapForSave(T entity, PreparedStatement preparedStatement) throws SQLException;

    abstract String getSaveSql();
}
