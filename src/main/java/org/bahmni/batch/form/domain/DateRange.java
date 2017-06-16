package org.bahmni.batch.form.domain;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateRange {

    @Value("${startDate}")
    private String startDateString;

    @Value("${endDate}")
    private String endDateString;

    public String getStartDateString(){
        return startDateString;
    }

    public String getEndDateString(){
        return endDateString;
    }
}
