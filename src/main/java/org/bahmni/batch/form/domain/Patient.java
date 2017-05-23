package org.bahmni.batch.form.domain;

public class Patient {

    private String obs_id;
    private String person_id;
    private Person person;
    private String visit_id;
    private String encounter_id;


    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getPerson_id() {
        return person_id;
    }

    public void setPerson_id(String person_id) {
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

    public String getObs_id() {
        return obs_id;
    }

    public void setObs_id(String obs_id) {
        this.obs_id = obs_id;
    }
}
