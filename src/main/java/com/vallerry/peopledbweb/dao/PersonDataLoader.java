package com.vallerry.peopledbweb.dao;

import com.vallerry.peopledbweb.biz.model.Person;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class PersonDataLoader implements ApplicationRunner {
    private PersonRepository personRepository;

    public PersonDataLoader(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (personRepository.count() == 0) {
            List<Person> people = List.of(
                    new Person(null, "Jake", "Snake", LocalDate.of(1950, 1, 1),"dummy@sample.com", new BigDecimal("150000"), null),
                    new Person(null, "Sarah", "Smith", LocalDate.of(1960, 2, 1),"dummy@sample.com", new BigDecimal("160000"), null),
                    new Person(null, "Johnny", "Jackson", LocalDate.of(1970, 3, 1),"dummy@sample.com", new BigDecimal("170000"), null),
                    new Person(null, "Bobby", "Kim", LocalDate.of(1980, 4, 1),"dummy@sample.com", new BigDecimal("180000"), null)
            );
            personRepository.saveAll(people);
        }
    }
}
