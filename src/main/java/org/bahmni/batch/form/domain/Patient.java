package org.bahmni.batch.form.domain;

import java.util.List;

public class Patient {

    private Integer obs_id;
    private Integer person_id;
    private Person person;
    private String visit_id;
    private String encounter_id;
    private List<FormFilledForPatient> formsFilled;


    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Integer getPerson_id() {
        return person_id;
    }

    public void setPerson_id(Integer person_id) {
        this.person_id = person_id;
    }

    public String getVisit_id() {
        return visit_id;
    }

    public void setVisit_id(String visit_id) {
        this.visit_id = visit_id;
    }

    public String getEncounter_id() {
        return encounter_id;
    }

    public void setEncounter_id(String encounter_id) {
        this.encounter_id = encounter_id;
    }

    public Integer getObs_id() {
        return obs_id;
    }

    public void setObs_id(Integer obs_id) {
        this.obs_id = obs_id;
    }

    public List<FormFilledForPatient> getFormsFilled() {
        return formsFilled;
    }

    public void setFormsFilled(List<FormFilledForPatient> formsFilled) {
        this.formsFilled = formsFilled;
    }
}
