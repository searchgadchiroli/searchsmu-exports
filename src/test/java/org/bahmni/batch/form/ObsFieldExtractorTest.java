package org.bahmni.batch.form;

import org.bahmni.batch.form.domain.BahmniForm;
import org.bahmni.batch.form.domain.Concept;
import org.bahmni.batch.form.domain.Obs;
import org.bahmni.batch.form.domain.Person;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ObsFieldExtractorTest {

	@Test
	public void shouldExtractObsListToObjectArray(){
		BahmniForm form = new BahmniForm();
		form.setFormName(new Concept(0,"Blood Pressure",1));
		form.addField(new Concept(1,"Systolic",0));
		form.addField(new Concept(2,"Diastolic",0));
		ObsFieldExtractor fieldExtractor = new ObsFieldExtractor(form);

		List<Obs> obsList = new ArrayList<>();
		Person person = new Person();
		Obs systolic = new Obs(1, 0, new Concept(1, "Systolic", 0), "120");
		systolic.setPerson(person);
		obsList.add(systolic);
		Obs diastolic = new Obs(1, 0, new Concept(2, "Diastolic", 0), "80");
		diastolic.setPerson(person);
		obsList.add(diastolic);

		List<Object> result = Arrays.asList(fieldExtractor.extract(obsList));


		assertEquals(9,result.size());
		assertTrue(result.contains("120"));
		assertTrue(result.contains("80"));
	}

	@Test
	public void ensureThatSplCharsAreHandledInCSVInTheObsValue(){
		BahmniForm form = new BahmniForm();
		form.setFormName(new Concept(0,"Blood Pressure",1));
		form.addField(new Concept(1,"Systolic",0));
		form.addField(new Concept(2,"Diastolic",0));

		ObsFieldExtractor fieldExtractor = new ObsFieldExtractor(form);

		List<Obs> obsList = new ArrayList<>();
		Obs systolicObs = new Obs(1, 0, new Concept(1, "Systolic", 0), "abc\ndef\tghi,klm");
		Person person = new Person();
		systolicObs.setPerson(person);
		obsList.add(systolicObs);

		Object[] result = fieldExtractor.extract(obsList);

		for (int i = 0; i < result.length; i++) {
			System.out.println(result[i]);
		}
		assertEquals(new Integer(1),result[0]);
		assertEquals("abc def ghi klm",result[7]);
	}

}
