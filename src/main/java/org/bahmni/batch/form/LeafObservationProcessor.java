package org.bahmni.batch.form;

import org.bahmni.batch.BatchUtils;
import org.bahmni.batch.form.domain.*;
import org.bahmni.batch.helper.FreeMarkerEvaluator;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
@Scope(value="prototype")
public class LeafObservationProcessor implements ItemProcessor<Patient, Patient> {


    private String formsFilledForPatientSql;

    @Value("classpath:sql/formsFilledForPatient.sql")
    private Resource formsFilledForPatientSqlResource;


    private BahmniForm form;

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;


	@Autowired
	private FormFieldTransformer formFieldTransformer;

	@Autowired
	private FreeMarkerEvaluator<BahmniForm> freeMarkerEvaluator;

	@Autowired
	private ObjectFactory<ObservationProcessor> observationProcessorFactory;



	@Override
	public Patient process(Patient patient) throws Exception {
		System.out.println("Processing patient "+patient.getPerson().getIdentifier()+"for form"+form.getDisplayName());
		updatePatientWithFormsFilled(patient);
		return patient;
	}

    private void updatePatientWithFormsFilled(Patient patient) throws Exception {
        Map<String,Integer> params = new HashMap<>();
        params.put("form_concept_id",form.getFormName().getId());
        params.put("person_id", patient.getPerson_id());

        List<FormFilledForPatient> formsFilledForPatient = jdbcTemplate.query(formsFilledForPatientSql, params, new BeanPropertyRowMapper<>(FormFilledForPatient.class));
        formsFilledForPatient = pickLatestFormInEveryVisit(formsFilledForPatient);

        for (FormFilledForPatient formFilledForPatient: formsFilledForPatient)
        {
            Map<String, Object> map = new HashMap<>();
            map.put("obs_id", formFilledForPatient.getObs_id());
            map.put("parent_obs_id", formFilledForPatient.getObs_id());
            ObservationProcessor observationProcessor = observationProcessorFactory.getObject();
            observationProcessor.setForm(form);
            System.out.println("Getting obs filled for Form filled on "+formFilledForPatient.getVisit_date());
            List<Obs> obs = observationProcessor.process(map);
            formFilledForPatient.setObs(obs);
            patient.setFormsFilled(formsFilledForPatient);
        }
	}

    private List<FormFilledForPatient> pickLatestFormInEveryVisit(List<FormFilledForPatient> formsFilledForPatient) {
        return formsFilledForPatient;
    }


    public void setForm(BahmniForm form) {
		this.form = form;
	}

	public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void setFormFieldTransformer(FormFieldTransformer formFieldTransformer) {
		this.formFieldTransformer = formFieldTransformer;
	}

    @PostConstruct
    public void postConstruct(){
        this.formsFilledForPatientSql = BatchUtils.convertResourceOutputToString(formsFilledForPatientSqlResource);

    }

}
