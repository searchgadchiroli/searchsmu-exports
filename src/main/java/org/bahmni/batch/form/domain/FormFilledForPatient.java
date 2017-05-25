package org.bahmni.batch.form.domain;

import java.util.Date;
import java.util.List;

public class FormFilledForPatient {

    private Integer person_id;
    private Integer obs_id;
    private Integer visit_id;
    private Date visit_date;
    private Integer encounter_id;
    private List<Obs> obs;

    public Integer getPerson_id() {
        return person_id;
    }

    public void setPerson_id(Integer person_id) {
        this.person_id = person_id;
    }

    public Integer getObs_id() {
        return obs_id;
    }

    public void setObs_id(Integer obs_id) {
        this.obs_id = obs_id;
    }

    public Integer getVisit_id() {
        return visit_id;
    }

    public void setVisit_id(Integer visit_id) {
        this.visit_id = visit_id;
    }

    public Date getVisit_date() {
        return visit_date;
    }

    public void setVisit_date(Date visit_date) {
        this.visit_date = visit_date;
    }

    public Integer getEncounter_id() {
        return encounter_id;
    }

    public void setEncounter_id(Integer encounter_id) {
        this.encounter_id = encounter_id;
    }

    public List<Obs> getObs() {
        return obs;
    }

    public void setObs(List<Obs> obs) {
        this.obs = obs;
    }
}
