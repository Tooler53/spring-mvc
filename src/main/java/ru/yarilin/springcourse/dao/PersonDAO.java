package ru.yarilin.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yarilin.springcourse.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    private static final String FIND_ALL_SQL = """
            select id, name, age, email, address from person
            """;

    private static final String FIND_ONE_BY_ID_SQL = FIND_ALL_SQL + """
            where id = ?
            """;

    private static final String FIND_ONE_BY_EMAIL_SQL = FIND_ALL_SQL + """
            where email = ?
            """;

    private static final String CREATE_NEW_PERSON_SQL = """
            INSERT INTO person (name, age, email, address)
            VALUES
            (?,?,?,?);
            """;

    private static final String UPDATE_PERSON_BY_ID_SQL = """
            UPDATE person
            SET name = ?, age = ?, email = ?, address = ?
            WHERE id = ?
            """;

    private static final String DELETE_PERSON_BY_ID_SQL = """
            delete from person where id = ?
            """;


    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query(FIND_ALL_SQL, new BeanPropertyRowMapper<>(Person.class));
    }

    public Person view(int id) {
        return jdbcTemplate.query(FIND_ONE_BY_ID_SQL, new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream()
                .findAny()
                .orElse(null);
    }

    public Optional<Person> view(String email) {
        return jdbcTemplate.query(FIND_ONE_BY_EMAIL_SQL, new Object[]{email}, new BeanPropertyRowMapper<>(Person.class))
                .stream()
                .findAny();
    }

    public void create(Person person) {
        jdbcTemplate.update(CREATE_NEW_PERSON_SQL, person.getName(), person.getAge(), person.getEmail(), person.getAddress());
    }

    public void update(Person person) {
        jdbcTemplate.update(UPDATE_PERSON_BY_ID_SQL, person.getName(), person.getAge(), person.getEmail(), person.getAddress(), person.getId());
    }

    public void delete(int id) {
        jdbcTemplate.update(DELETE_PERSON_BY_ID_SQL, id);
    }


    ///////////////////////////////
    //// Множественная вставка ////
    ///////////////////////////////


    public void testMultipleUpdate() {
        List<Person> people = create1000people();

        long before = System.currentTimeMillis();
        for (Person person : people) {
            jdbcTemplate.update(CREATE_NEW_PERSON_SQL, person.getName(), person.getAge(), person.getEmail(), person.getAddress());
        }
        long after = System.currentTimeMillis();

        System.out.println("Time: " + (after - before));
    }

    public void testBatchUpdate() {
        List<Person> people = create1000people();
        long before = System.currentTimeMillis();

        jdbcTemplate.batchUpdate(CREATE_NEW_PERSON_SQL, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1, people.get(i).getName());
                preparedStatement.setInt(2, people.get(i).getAge());
                preparedStatement.setString(3, people.get(i).getEmail());
                preparedStatement.setString(4, people.get(i).getAddress());
            }

            @Override
            public int getBatchSize() {
                return people.size();
            }
        });

        long after = System.currentTimeMillis();
        System.out.println("Time: " + (after - before));
    }

    private List<Person> create1000people() {
        List<Person> people = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            people.add(new Person("Name" + i, 30, "test" + i + "@mail.ru", "address" + i));
        }

        return people;
    }
}
