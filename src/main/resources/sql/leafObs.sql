select
  pid.identifier as identifier,
  concat(pn.given_name, ' ', pn.family_name) name,
  person.birthdate as birthDate,
  floor(datediff(CURDATE(), person.birthdate) / 365) AS age,
  person.gender gender,
  encounter.visit_id as visit_id,
  o.concept_id as conceptId,
       o.obs_id as id,
       coalesce(DATE_FORMAT(o.value_datetime, '%d/%b/%Y'),o.value_numeric,o.value_text,cv.code,cvn.concept_full_name,cvn.concept_short_name) as value,
       obs_con.concept_full_name as conceptName
from obs o
  join person on o.person_id = person.person_id
  join patient_identifier pid on person.person_id = pid.patient_id
  join person_name pn on pn.person_id = person.person_id
  join concept_view obs_con on(o.concept_id = obs_con.concept_id)
  join encounter on o.encounter_id = encounter.encounter_id
  left outer join concept codedConcept on o.value_coded = codedConcept.concept_id
  left outer join concept_reference_term_map_view cv on (cv.concept_id = codedConcept.concept_id and cv.concept_map_type_name = 'SAME-AS' and cv.concept_reference_source_name = 'EndTB-Export')
  left outer join concept_view cvn on (codedConcept.concept_id = cvn.concept_id)
where
  o.obs_id in (:childObsIds)
  and obs_con.concept_id  in (:leafConceptIds)
  and o.voided=0;
