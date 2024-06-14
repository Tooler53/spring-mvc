package ru.yarilin.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.yarilin.springcourse.dao.project1.PersonDAOProject;
import ru.yarilin.springcourse.models.project1.Person;
import ru.yarilin.springcourse.util.PersonValidator;

@Controller
@RequestMapping("/reader")
public class ReaderController {
    private PersonDAOProject personDaoProject;

    @Autowired
    public ReaderController(PersonDAOProject personDaoProject) {
        this.personDaoProject = personDaoProject;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personDaoProject.index());

        return "reader/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDaoProject.view(id));

        return "reader/show";
    }

    @GetMapping("/new")
    public String newPerson(Model model) {
        model.addAttribute("person", new Person());

        return "reader/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") Person person) {
        personDaoProject.create(person);

        return "redirect:/reader";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDaoProject.view(id));

        return "reader/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") Person person, @PathVariable("id") int id) {
        personDaoProject.update(id, person);

        return "redirect:/reader";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDaoProject.delete(id);

        return "redirect:/reader";
    }
}
