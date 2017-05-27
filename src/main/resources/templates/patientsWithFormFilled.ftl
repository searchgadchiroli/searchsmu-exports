select distinct obs.person_id,
pid.identifier as identifier,
concat(pn.given_name, ' ', pn.family_name) name,
person.birthdate as birthDate,
floor(datediff(CURDATE(), person.birthdate) / 365) AS age,
person.gender gender
from obs
join person on obs.person_id = person.person_id
join patient_identifier pid on person.person_id = pid.patient_id
join person_name pn on pn.person_id = person.person_id
where obs.concept_id = ${input.formName.id?c}
and obs.voided = 0
and person.voided = 0;


