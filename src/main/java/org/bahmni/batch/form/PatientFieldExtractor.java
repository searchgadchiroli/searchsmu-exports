package org.bahmni.batch.form;

import org.bahmni.batch.form.domain.BahmniForm;
import org.bahmni.batch.form.domain.Concept;
import org.bahmni.batch.form.domain.Obs;
import org.bahmni.batch.form.domain.Patient;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatientFieldExtractor implements FieldExtractor<Patient>, FlatFileHeaderCallback{

	private BahmniForm form;

	public PatientFieldExtractor(BahmniForm form){
		this.form = form;
	}

	@Override
	public Object[] extract(Patient patient) {
		List<Object> row = new ArrayList<>();

//		if(patientList.size()==0)
//			return row.toArray();

//		System.out.println("Found Obs list containing something for form "+form.getDisplayName());

//		Map<Concept,String> obsRow = new HashMap<>();
//		for(Patient patient: patientList){
//			if(obsRow.containsKey(patient.getField())){
//				obsRow.put(patient.getField(),obsRow.get(patient.getField())+";"+patient.getValue());
//			}else {
//				obsRow.put(patient.getField(), patient.getValue());
//			}
//		}
//
//		row.add(patientList.get(0).getId());
//
//		if(form.getParent()!=null){
//			row.add(patientList.get(0).getParentId());
//		}
//
//		row.add(patientList.get(0).getPerson().getIdentifier());
//		row.add(patientList.get(0).getPerson().getName());
//		row.add(patientList.get(0).getPerson().getAge());
//		row.add(patientList.get(0).getPerson().getBirthDate());
//		row.add(patientList.get(0).getPerson().getGender());
//		row.add(patientList.get(0).getVisit_id());
//
//		for(Concept field: form.getFields()){
//			row.add(massageStringValue(obsRow.get(field)));
//		}

		System.out.println("Writing patient"+patient.getPerson().getIdentifier());
		row.add(patient.getObs_id());
		row.add(patient.getPerson().getIdentifier());
		row.add(patient.getVisit_id());
		row.add(patient.getEncounter_id());
		return row.toArray();
	}

	private String massageStringValue(String text){
		if(StringUtils.isEmpty(text))
			return text;
		return text.replaceAll("\n"," ").replaceAll("\t"," ").replaceAll(","," ");
	}

	@Override
	public void writeHeader(Writer writer) throws IOException {
		writer.write(getHeader());
	}

	private String getHeader() {
		StringBuilder sb = new StringBuilder();

		sb.append("id_" + form.getDisplayName()).append(",");
		if (form.getParent() != null) {
			sb.append("id_" + form.getParent().getDisplayName()).append(",");
		}

		sb.append("Patient").append(",")
		.append("visit_id").append(",")
		.append("encounter_id");

//		sb.append("Patient Identifier").append(",")
//				.append("Patient Name").append(",")
//				.append("Age").append(",")
//				.append("Birth Date").append(",")
//				.append("Gender").append(",")
//				.append("visit_id");
//		for (Concept field : form.getFields()) {
//			sb.append(",");
//			sb.append(field.getFormattedTitle());
//		}
		return sb.toString();
	}

}
