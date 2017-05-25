select distinct obs.person_id
from obs
join person on obs.person_id = person.person_id
where obs.concept_id = ${input.formName.id?c}
and obs.voided = 0
and person.voided = 0;
