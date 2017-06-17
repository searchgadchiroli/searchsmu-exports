package org.bahmni.batch.form.domain;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateRange {

    private String startDateString;

    private String endDateString;

    public String getStartDateString(){
        return startDateString;
    }

    public String getEndDateString(){
        return endDateString;
    }

    public void setStartDateString(String startDateString) {
        this.startDateString = startDateString;
    }

    public void setEndDateString(String endDateString) {
        this.endDateString = endDateString;
    }
}
