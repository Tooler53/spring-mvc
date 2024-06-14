package ru.yarilin.springcourse.dao.project1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yarilin.springcourse.models.project1.Book;
import ru.yarilin.springcourse.models.project1.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    private static final String FIND_ALL_SQL = """
            select * from book
            """;

    private static final String FIND_ONE_BY_ID_SQL = FIND_ALL_SQL + """
            where id = ?
            """;

    private static final String FIND_ALL_BY_PERSON_ID_SQL = FIND_ALL_SQL + """
            where person_id = ?
            """;

    private static final String CREATE_NEW_BOOK_SQL = """
            INSERT INTO book (name, author, year)
            VALUES
            (?,?,?);
            """;

    private static final String UPDATE_BOOK_BY_ID_SQL = """
            UPDATE Book
            SET name = ?, author = ?, year = ?
            WHERE id = ?
            """;
    private static final String UPDATE_PERSON_BOOK_BY_ID_SQL = """
            UPDATE Book
            SET person_id = ?
            WHERE id = ?
            """;

    private static final String DELETE_BOOK_BY_ID_SQL = """
            delete from book where id = ?
            """;


    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query(FIND_ALL_SQL, (resultSet, rowNum) -> {
            Book book = new Book();
            book.setId(resultSet.getInt("id"));
            book.setName(resultSet.getString("name"));
            book.setAuthor(resultSet.getString("author"));
            book.setYear(resultSet.getInt("year"));
            return book;
        });
    }

    public List<Book> indexByUserId(int id) {
        return jdbcTemplate.query(FIND_ALL_BY_PERSON_ID_SQL, new BeanPropertyRowMapper<>(Book.class), id);
    }

    public Book view(int id) {
        return jdbcTemplate.query(FIND_ONE_BY_ID_SQL, (resultSet, rowNum) -> {
                    PersonDAOProject personDAOProject = new PersonDAOProject(jdbcTemplate, this);

                    int person_id = resultSet.getInt("person_id");

                    Book book = new Book();
                    book.setId(resultSet.getInt("id"));
                    book.setName(resultSet.getString("name"));
                    book.setAuthor(resultSet.getString("author"));
                    book.setYear(resultSet.getInt("year"));
                    book.setPersonId(person_id);

                    if (person_id > 0) {
                        Optional<Person> person = Optional.ofNullable(personDAOProject.view(person_id));
                        book.setPerson(person.orElse(null));
                    }

                    return book;
                }, id)
                .stream()
                .findAny()
                .orElse(null);
    }

    public void create(Book book) {
        jdbcTemplate.update(CREATE_NEW_BOOK_SQL, book.getName(), book.getAuthor(), book.getYear());
    }

    public void update(int id, Book book) {
        jdbcTemplate.update(UPDATE_BOOK_BY_ID_SQL, book.getName(), book.getAuthor(), book.getYear(), id);
    }

    public void update_person(Book book) {
        jdbcTemplate.update(UPDATE_PERSON_BOOK_BY_ID_SQL, book.getPersonId(), book.getId());
    }

    public void delete_person(int id) {
        jdbcTemplate.update(UPDATE_PERSON_BOOK_BY_ID_SQL, null, id);
    }

    public void delete(int id) {
        jdbcTemplate.update(DELETE_BOOK_BY_ID_SQL, id);
    }
}
