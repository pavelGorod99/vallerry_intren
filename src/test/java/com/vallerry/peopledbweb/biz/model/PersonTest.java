package com.vallerry.peopledbweb.biz.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    @Test
    public void canParse() {
        String csvLine = "742048,Drs.,    Lizeth,P,Mccoll,F,lizeth.mccoll@ibm.com,   Renato Mccoll,Serena Mccoll,Broxton,7/5/1973,08:14:43 AM,47.19,49,1/7/1995,  Q1,H1,1995,1,January,Jan,7,Saturday,Sat,25.67,147446,14%,171-86-6830,216-703-2865,Alliance,Stark,Alliance,OH,44601,Midwest,lpmccoll,b5CDsd1Mz.";
        Person person = Person.parse(csvLine);
        assertThat(person.getDob()).isEqualTo(LocalDate.of(1973, 7, 5));
    }

}