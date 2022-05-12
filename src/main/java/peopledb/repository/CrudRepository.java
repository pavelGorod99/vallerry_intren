package peopledb.repository;

import peopledb.annotation.Id;
import peopledb.annotation.MultiSQL;
import peopledb.annotation.SQL;
import peopledb.model.CrudOperation;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

abstract class CrudRepository<T> {

    protected Connection connection;

    public CrudRepository(Connection connection) {
        this.connection = connection;
    }

    private String getSqlByAnnotation(CrudOperation operationType, Supplier<String> sqlGetter) {

        Stream<SQL> multiSqlStream = Arrays.stream(this.getClass().getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(MultiSQL.class))
                .map(m -> m.getAnnotation(MultiSQL.class))
                .flatMap(msql -> Arrays.stream(msql.value()));


        Stream<SQL> sqlStream = Arrays.stream(this.getClass().getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(SQL.class))
                .map(m -> m.getAnnotation(SQL.class));

        return Stream.concat(multiSqlStream, sqlStream)
                .filter(a -> a.operationType().equals(operationType))
                .map(SQL::value)
                .findFirst().orElseGet(sqlGetter);
    }

    public long count() {
        long count = 0;
        try {
            PreparedStatement ps = connection.prepareStatement(getSqlByAnnotation(CrudOperation.COUNT, this::getCountSql));
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                count = rs.getLong(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public void delete (T entity) {
        try {
            PreparedStatement ps = connection.prepareStatement(getSqlByAnnotation(CrudOperation.DELETE_ONE, this::getDeleteSql));
            ps.setLong(1, getIdByAnnotation(entity));
            int affectedRecordCount = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setIdByAnnotation(Long id, T entity) {
        Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Id.class))
                .forEach(f -> {
                    f.setAccessible(true);
                    try {
                        f.set(entity, id);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Unable to set ID field value");
                    }
                });
    }

    private Long getIdByAnnotation(T entity) {
        return Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Id.class))
                .map(f -> {
                    f.setAccessible(true);
                    Long id = null;
                    try {
                        id = (long)f.get(entity);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                    return id;
                })
                .findFirst().orElseThrow(() -> new RuntimeException("No ID annotated field found"));
    }

    public void delete(T ... entity) {
//        for (Person person: entity) {
//            delete(person);
//        }
        try {
            Statement stmt = connection.createStatement();

            String ids = Stream.of(entity)
                    .map(this::getIdByAnnotation)
                    .map(String::valueOf)
                    .collect(joining(","));
            int affectedCount = stmt.executeUpdate(getSqlByAnnotation(CrudOperation.DELETE_MANY, this::getDeleteInSql).replace(":ids", ids));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(T entity) {
        try {
            PreparedStatement ps = connection.prepareStatement(getSqlByAnnotation(CrudOperation.UPDATE, this::getUpdateSql));
            mapForUpdate(entity, ps);
            ps.setLong(5, getIdByAnnotation(entity));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    abstract void mapForUpdate(T entity, PreparedStatement ps) throws SQLException;

    abstract void mapForSave(T entity, PreparedStatement preparedStatement) throws SQLException;

    /**
     *
     * @return Should return a SQL string like:
     * "DELETE FROM PEOPLE WHERE ID IN (:ids)"
     * Be sure to include the '(:ids)' named parameter & call it 'ids'
     */


    public T save(T entity) {
        try {
            PreparedStatement ps = connection.prepareStatement(getSqlByAnnotation(CrudOperation.SAVE, this::getSaveSql), Statement.RETURN_GENERATED_KEYS);
            mapForSave(entity, ps);

            int affectedRecords = ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();

            while (generatedKeys.next())  {
                long ID = generatedKeys.getLong(1);
                setIdByAnnotation(ID, entity);
                postSave(entity, ID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }

    public T findById(Long id) {

        T entity = null;

        try {
            PreparedStatement ps = connection.prepareStatement(getSqlByAnnotation(CrudOperation.FIND_BY_ID, this::getFindByIdSql));
            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                entity = extractEntityFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }

    public List<T> findAll() {
        List<T> entities = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(getSqlByAnnotation(CrudOperation.FIND_ALL, this::getFindAllSql));
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                entities.add(extractEntityFromResultSet(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entities;
    }

    protected void postSave(T entity, long id) {}

    protected static <T> T getValueByAlias(String alias, ResultSet rs, Class<T> clazz) throws SQLException {
        int columnCount = rs.getMetaData().getColumnCount();
        for (int colIdx = 1; colIdx <= columnCount; colIdx++) {
            if (alias.equals(rs.getMetaData().getColumnLabel(colIdx))) {
                return (T) rs.getObject(colIdx);
            }
        }
        throw new SQLException(String.format("Column not found for alias: '%s'", alias));
    }


    abstract T extractEntityFromResultSet(ResultSet rs) throws SQLException;

    /**
     *
     * @return Returns a String that represents the SQL needed to retrieve one entity
     * The SQL must contain one SQL parameter, i.e. "?", that will bind to the
     * entity's ID.
     */
    protected String getFindByIdSql() {
        throw new RuntimeException("SQL not defined.");
    };

    protected String getFindAllSql() {
        throw new RuntimeException("SQL not defined.");
    };

    protected String getCountSql() {
        throw new RuntimeException("SQL not defined.");
    };

    protected String getDeleteSql() {
        throw new RuntimeException("SQL not defined.");
    };

    protected String getSaveSql() {
        throw new RuntimeException("SQL not defined.");
    };

    protected String getUpdateSql() {
        throw new RuntimeException("SQL not defined.");
    }

    protected String getDeleteInSql() {
        throw new RuntimeException("SQL not defined.");
    };
}
