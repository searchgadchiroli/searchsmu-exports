package org.bahmni.batch.form.domain;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import java.util.*;

import static org.apache.commons.collections.CollectionUtils.find;

public class BahmniForm {

	private List<BahmniForm> children = new ArrayList<>();

	private BahmniForm parent;

	private Concept formName;

	private List<Concept> fields = new ArrayList<>();

	private int depthToParent;

	private Long totalVisitsFilledIn;


	public List<BahmniForm> getChildren() {
		return children;
	}

	public void addChild(BahmniForm bahmniForm){
		children.add(bahmniForm);
	}

	public BahmniForm getParent() {
		return parent;
	}

	public void setParent(BahmniForm parent) {
		this.parent = parent;
	}

	public Concept getFormName() {
		return formName;
	}

	public void setFormName(Concept formName) {
		this.formName = formName;
	}

	public List<Concept> getFields() {
		return fields;
	}

	public Concept getField(final Integer id){
	    Object object = find(fields, new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                return ((Concept) object).getId().equals(id);
            }
        });
	    return object != null ? (Concept) object : null;
    }

	public void addField(Concept concept){
		fields.add(concept);
	}

	public int getDepthToParent() {
		return depthToParent;
	}

	public void setDepthToParent(int depthToParent) {
		this.depthToParent = depthToParent;
	}

	public String getDisplayName() {
		if(formName == null)
			return "";
		return formName.getName().replaceAll("\\s", "_").replaceAll(",","").replaceAll("/","").toLowerCase() ;
	}

    public Long getTotalVisitsFilledIn() {
        return totalVisitsFilledIn;
    }

    public void setTotalVisitsFilledIn(Long totalVisitsFilledIn) {
        this.totalVisitsFilledIn = totalVisitsFilledIn;
    }
}
