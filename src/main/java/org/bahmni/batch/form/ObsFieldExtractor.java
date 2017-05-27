package org.bahmni.batch.form;

import org.bahmni.batch.form.domain.BahmniForm;
import org.bahmni.batch.form.domain.Concept;
import org.bahmni.batch.form.domain.Obs;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObsFieldExtractor implements FieldExtractor<List<Obs>>, FlatFileHeaderCallback{

	private BahmniForm form;

	public ObsFieldExtractor(BahmniForm form){
		this.form = form;
	}

	@Override
	public Object[] extract(List<Obs> obsList) {
		List<Object> row = new ArrayList<>();

		if(obsList.size()==0)
			return row.toArray();

		System.out.println("Found Obs list containing something for form "+form.getDisplayName());

		Map<Concept,String> obsRow = new HashMap<>();
		for(Obs obs: obsList){
			if(obsRow.containsKey(obs.getField())){
				obsRow.put(obs.getField(),obsRow.get(obs.getField())+";"+obs.getValue());
			}else {
				obsRow.put(obs.getField(), obs.getValue());
			}
		}

		row.add(obsList.get(0).getId());

		if(form.getParent()!=null){
			row.add(obsList.get(0).getParentId());
		}

		row.add(obsList.get(0).getPerson().getId());
		row.add(obsList.get(0).getPerson().getName());
		row.add(obsList.get(0).getPerson().getAge());
		row.add(obsList.get(0).getPerson().getBirthDate());
		row.add(obsList.get(0).getPerson().getGender());
		row.add(obsList.get(0).getVisit_id());

		for(Concept field: form.getFields()){
			row.add(massageStringValue(obsRow.get(field)));
		}

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

		sb.append("Patient Identifier").append(",")
				.append("Patient Name").append(",")
				.append("Age").append(",")
				.append("Birth Date").append(",")
				.append("Gender").append(",")
				.append("visit_id");
		for (Concept field : form.getFields()) {
			sb.append(",");
			sb.append(field.getFormattedTitle());
		}
		return sb.toString();
	}

}
