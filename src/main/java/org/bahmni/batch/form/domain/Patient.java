package org.bahmni.batch.form.domain;

import java.util.List;

public class Patient {

    private String identifier;
    private Person person;
    private List<FormFilledForPatient> formsFilled;


    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<FormFilledForPatient> getFormsFilled() {
        return formsFilled;
    }

    public void setFormsFilled(List<FormFilledForPatient> formsFilled) {
        this.formsFilled = formsFilled;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
