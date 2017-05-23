package org.bahmni.batch.form;

import org.bahmni.batch.BatchUtils;
import org.bahmni.batch.form.domain.*;
import org.springframework.batch.item.ItemProcessor;
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

	private String obsDetailSql;

	private String leafObsSql;

	private BahmniForm form;

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;


	@Autowired
	private FormFieldTransformer formFieldTransformer;

	@Override
	public Patient process(Patient patient) throws Exception {
		System.out.println("Processing patient "+patient.getPerson().getIdentifier()+"for form"+form.getDisplayName());
		return patient;
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
}
