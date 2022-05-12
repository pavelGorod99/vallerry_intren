package com.vallerry.peopledbweb.web.controller;

import com.vallerry.peopledbweb.biz.model.Person;
import com.vallerry.peopledbweb.data.PersonRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private PersonRepository personRepository;

    public PeopleController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @ModelAttribute("people")
    public Iterable<Person> getPeople() {
        return personRepository.findAll();
    }

    @ModelAttribute
    public Person getPerson() {
        return new Person();
    }

    @GetMapping
    public String showPeoplePage() {
        return "people";
    }

    @PostMapping
    public String savePerson(@Valid Person person, Errors errors) {
        if (!errors.hasErrors()) {
            personRepository.save(person);
            return "redirect:people";
        }
        return "people";
    }
}
