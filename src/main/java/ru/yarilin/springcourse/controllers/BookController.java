package ru.yarilin.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.yarilin.springcourse.dao.project1.BookDAO;
import ru.yarilin.springcourse.dao.project1.PersonDAOProject;
import ru.yarilin.springcourse.models.project1.Book;
import ru.yarilin.springcourse.models.project1.Person;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/book")
public class BookController {
    private BookDAO bookDAO;
    private PersonDAOProject personDaoProject;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAOProject personDaoProject) {
        this.bookDAO = bookDAO;
        this.personDaoProject = personDaoProject;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());

        return "book/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        Book book = bookDAO.view(id);
        model.addAttribute("books", book);

        if (book.getPerson() == null) {
            List<Person> people = personDaoProject.index();

            for (Person person : people) {
                System.out.println(person);
            }
            model.addAttribute("people", people);
        }

        return "book/show";
    }

    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("books", new Book());

        return "book/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("books") Book book) {
        bookDAO.create(book);

        return "redirect:/book";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("books", bookDAO.view(id));

        return "book/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("books") Book book, @PathVariable("id") int id) {
        bookDAO.update(id, book);

        return "redirect:/book";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);

        return "redirect:/book";
    }

    @PatchMapping("/add_person/{id}")
    public String add_person(@ModelAttribute("books") Book book) {
        bookDAO.update_person(book);

        return "redirect:/book";
    }

    @DeleteMapping("/delete_person/{id}")
    public String delete_person(@PathVariable("id") int id) {
        bookDAO.delete_person(id);

        return "redirect:/book";
    }
}
