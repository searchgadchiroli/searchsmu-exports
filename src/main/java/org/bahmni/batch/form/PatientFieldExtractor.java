package org.bahmni.batch.form;

import org.bahmni.batch.form.domain.*;
import org.bahmni.batch.helper.SMUVillageCodeMapping;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PatientFieldExtractor implements FieldExtractor<Patient>, FlatFileHeaderCallback{

    public static final String DATE_FORMAT = "dd/MMM/yyyy";
    private BahmniForm form;

	public PatientFieldExtractor(BahmniForm form){
		this.form = form;
	}

	@Override
	public Object[] extract(Patient patient) {
		List<Object> row = new ArrayList<>();


		System.out.println("Writing patient"+patient.getPerson().getId());
		row.add(patient.getIdentifier());
		row.add(patient.getPerson().getName());
		row.add(patient.getPerson().getAge());
		row.add(patient.getPerson().getBirthDate());
		row.add(patient.getPerson().getGender());
		row.add(patient.getPerson().getAddress().getVillage());
		row.add(patient.getPerson().getAddress().getBlock());
		row.add(patient.getPerson().getAddress().getTehsil());
		row.add(patient.getPerson().getAddress().getDistrict());
		row.add(SMUVillageCodeMapping.villageCodes.get(patient.getPerson().getAddress().getVillage()));


		int visit_number = 0;
        System.out.println("Forms filled" +patient.getFormsFilled().size());
        for (FormFilledForPatient formFilledForPatient: patient.getFormsFilled()) {
            row.add(++visit_number);
            row.add(new SimpleDateFormat(DATE_FORMAT).format(formFilledForPatient.getVisit_date()));
            for (Concept field : form.getFields()){
                if(field.isCoded()){
                    //TODO explore optimising this by having the same objects in both form and obs
                    for (Concept answer: field.getAnswers()) {
                        row.add(isThisAnswerChosen(field, answer, formFilledForPatient.getObs()) ? "1" : "0");
                    }
                }else {
                    row.add(massageStringValue(getObsValue(field, formFilledForPatient.getObs())));
                }
            }
        }


        return row.toArray();
	}

    private boolean isThisAnswerChosen(Concept field, Concept answer, List<Obs> obsList) {
	    //TODO scope for optimising CPU cycles
	    for(Obs obs : obsList){
	        if(obs.getField().equals(field)){
	            if(answer.getId().equals(Integer.parseInt(obs.getValue()))) return true;
            }
        }
        return false;
    }

    private String getObsValue(Concept field, List<Obs> obsList){
        //TODO scope for optimising CPU cycles
        for (Obs obs: obsList) {
            if(obs.getField().equals(field)){
                return obs.getValue();
            }
        }
        return null;
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
        System.out.println("Getting header for Form "+form.getFormName().getName()+" filled in total visits "+form.getTotalVisitsFilledIn());
        if(form.getTotalVisitsFilledIn() == null){
	        return "";
        }
		StringBuilder sb = new StringBuilder();

		sb.append("Patient Identifier");
		sb.append(",").append("Patient Name");
		sb.append(",").append("Patient Age");
		sb.append(",").append("Patient Birth date");
		sb.append(",").append("Patient Gender");
		sb.append(",").append("Patient Village");
		sb.append(",").append("Patient Block");
		sb.append(",").append("Patient Tehsil");
		sb.append(",").append("Patient District");
		sb.append(",").append("Patient ncd_village_no");
        for (int i = 0; i < form.getTotalVisitsFilledIn(); i++) {
            sb.append(",").append("visit_no");
            sb.append(",").append("visit_date");
            for (Concept field : form.getFields()) {
                int visit_number = i + 1;
                if(field.isCoded()){
                    for (Concept answer: field.getAnswers()) {
                        sb.append(",");
                        sb.append(headerForCodedAnswer(visit_number, field, answer));
                    }
                }else {
                    sb.append(",");
                    sb.append(headerForNonCodedAsnwer(visit_number, field));
                }
            }

        }
		return sb.toString();
	}

    private String headerForNonCodedAsnwer(int visit_number, Concept field) {
        return visit_number + "_" + field.getFormattedTitle();
    }

    private String headerForCodedAnswer(int visit_number, Concept field, Concept answer) {
        return visit_number +"_"+field.getFormattedTitle()+"-"+answer.getFormattedTitle();
    }

}
