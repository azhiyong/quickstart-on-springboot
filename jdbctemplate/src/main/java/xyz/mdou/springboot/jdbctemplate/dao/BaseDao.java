package xyz.mdou.springboot.jdbctemplate.dao;

import com.sun.deploy.util.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.ReflectionUtils;
import xyz.mdou.springboot.jdbctemplate.annotation.Column;
import xyz.mdou.springboot.jdbctemplate.annotation.Id;
import xyz.mdou.springboot.jdbctemplate.annotation.Ignore;
import xyz.mdou.springboot.jdbctemplate.annotation.Table;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Collectors;

public class BaseDao<ENTITY, ID> {

    private JdbcTemplate jdbcTemplate;
    private Class<ENTITY> clazz;

    @SuppressWarnings("unchecked")
    public BaseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.clazz = (Class<ENTITY>) ((ParameterizedType) (getClass().getGenericSuperclass())).getActualTypeArguments()[0];
    }

    public int save(ENTITY entity) {
        String table = getTableName();
        List<Field> fields = getFields(entity);
        List<String> columns = getColumns(fields);
        String[] params = new String[columns.size()];
        Arrays.fill(params, "?");
        Object[] values = fields.stream().map(f -> ReflectionUtils.getField(f, entity)).toArray();
        String sql = String.format("insert into %s (%s) values (%s)",
                table, String.join(",", columns),
                String.join(",", params));
        return jdbcTemplate.update(sql, values);
    }

    public List<ENTITY> findAll() {
        String table = getTableName();
        String sql = String.format("select * from %s", table);
        RowMapper<ENTITY> mapper = new BeanPropertyRowMapper<>(clazz);
        return jdbcTemplate.query(sql, mapper);
    }

    public ENTITY getOne(ID id) {
        String table = getTableName();
        String primaryColumn = getIdColumn();
        String sql = String.format("select * from %s where %s=?", table, primaryColumn);
        RowMapper<ENTITY> mapper = new BeanPropertyRowMapper<>(clazz);
        List<ENTITY> entities = jdbcTemplate.query(sql, new Object[]{id}, mapper);
        return entities.stream().findFirst().orElse(null);
    }

    public int deleteById(ID id) {
        String table = getTableName();
        String primaryColumn = getIdColumn();
        String sql = String.format("delete from %s where %s=?", table, primaryColumn);
        return jdbcTemplate.update(sql, id);
    }

    public int update(ID id, ENTITY entity) {
        String table = getTableName();
        List<Field> fields = getFields(entity);
        List<String> columns = getColumns(fields);
        String setColumns = columns.stream()
                .map(s -> String.format("%s=?", s))
                .collect(Collectors.joining(","));
        String primaryColumn = getIdColumn();
        String sql = String.format("update %s set %s where %s=?", table, setColumns, primaryColumn);

        List<Object> values = fields.stream()
                .map(f -> {
                    f.setAccessible(true);
                    return ReflectionUtils.getField(f, entity);
                })
                .collect(Collectors.toList());
        values.add(id);
        return jdbcTemplate.update(sql, values.toArray());
    }

    private String getTableName() {
        Table table = clazz.getAnnotation(Table.class);
        return Optional.ofNullable(table)
                .map(Table::value)
                .orElse(clazz.getName().toLowerCase());
    }

    private List<Field> getFields(ENTITY entity) {
        Field[] fields = clazz.getDeclaredFields();
        return Arrays.stream(fields)
                .filter(f -> Objects.isNull(f.getAnnotation(Ignore.class))
                        || Objects.isNull(f.getAnnotation(Id.class)))
                .filter(f -> {
                    f.setAccessible(true);
                    return Objects.nonNull(ReflectionUtils.getField(f, entity));
                })
                .collect(Collectors.toList());
    }

    private String getIdColumn() {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> Objects.nonNull(f.getAnnotation(Id.class)))
                .findFirst()
                .map(f -> Optional.ofNullable(f.getAnnotation(Column.class))
                        .map(Column::value).orElse(f.getName()))
                .orElse("id");
    }

    private List<String> getColumns(List<Field> fields) {
        return fields.stream()
                .map(f -> Optional.ofNullable(f.getAnnotation(Column.class))
                        .map(Column::value).orElse(f.getName()))
                .collect(Collectors.toList());
    }

}
