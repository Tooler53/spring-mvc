package ru.yarilin.springcourse.dao.project1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yarilin.springcourse.models.project1.Person;

import java.util.List;

@Component
public class PersonDAOProject {
    private final JdbcTemplate jdbcTemplate;

    private BookDAO bookDAO;

    private static final String FIND_ALL_SQL = """
            select * from person
            """;

    private static final String FIND_ONE_BY_ID_SQL = FIND_ALL_SQL + """
            where id = ?
            """;

    private static final String CREATE_NEW_PERSON_SQL = """
            INSERT INTO person (fio, age)
            VALUES
            (?,?);
            """;

    private static final String UPDATE_PERSON_BY_ID_SQL = """
            UPDATE person
            SET fio = ?, age = ?
            WHERE id = ?
            """;

    private static final String DELETE_PERSON_BY_ID_SQL = """
            delete from person where id = ?
            """;


    @Autowired
    public PersonDAOProject(JdbcTemplate jdbcTemplate, BookDAO bookDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.bookDAO = bookDAO;
    }

    public List<Person> index() {
        return jdbcTemplate.query(FIND_ALL_SQL, new BeanPropertyRowMapper<>(Person.class));
    }

    public Person view(int id) {
        Person person = jdbcTemplate.query(FIND_ONE_BY_ID_SQL, new BeanPropertyRowMapper<>(Person.class), id)
                .stream()
                .findAny()
                .orElse(null);

        person.setBookList(bookDAO.indexByUserId(id));

        return person;
    }

    public void create(Person person) {
        jdbcTemplate.update(CREATE_NEW_PERSON_SQL, person.getFio(), person.getAge());
    }

    public void update(int id, Person person) {
        jdbcTemplate.update(UPDATE_PERSON_BY_ID_SQL, person.getFio(), person.getAge(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update(DELETE_PERSON_BY_ID_SQL, id);
    }
}
