package ru.yarilin.springcourse.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.yarilin.springcourse.dao.PersonDAO;
import ru.yarilin.springcourse.models.Person;
import ru.yarilin.springcourse.util.PersonValidator;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private PersonDAO personDao;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonDAO personDao, PersonValidator personValidator) {
        this.personDao = personDao;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personDao.index());

        return "people/index";
    }

    @GetMapping("/view/id={id}")
    public String view(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDao.view(id));
        return "people/view";
    }

    @RequestMapping("/create")
    public String create(HttpServletRequest request, @ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if (request.getMethod().equals("POST") && !bindingResult.hasErrors()) {
            personDao.create(person);
            return "redirect:/people";
        }

        return "people/create";
    }

    @RequestMapping("/update")
    public String update(@RequestParam(value = "id", required = false) int id, HttpServletRequest request, @ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        person.setId(id);

        personValidator.validate(person, bindingResult);

        if (request.getMethod().equals("POST") && !bindingResult.hasErrors()) {
            personDao.update(person);
            return String.format("redirect:view/id=%s", person.getId());
        }

        return "people/update";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam(value = "id") int id) {
        personDao.delete(id);
        return "redirect:/people";
    }

    @GetMapping("/without")
    public String withoutBatch() {
        personDao.testMultipleUpdate();
        return "";
    }

    @GetMapping("/with")
    public String withBatch() {
        personDao.testBatchUpdate();
        return "";
    }
}
