package org.bahmni.batch.form.domain;

public class Obs {
	private Integer id;
	private Integer parentId;
	private Concept field;
	private String value;
	private Person person;
	private Integer visit_id;

	public Obs(){}

	public Obs(Integer id, Integer parentId, Concept field, String value) {
		this.id = id;
		this.parentId = parentId;
		this.field = field;
		this.value = value;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Concept getField() {
		return field;
	}

	public void setField(Concept field) {
		this.field = field;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}


	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Integer getVisit_id() {
		return visit_id;
	}

	public void setVisit_id(Integer visit_id) {
		this.visit_id = visit_id;
	}
}
