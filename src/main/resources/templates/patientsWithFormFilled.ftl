select obs.obs_id, obs.person_id, e.visit_id, obs.encounter_id encounter_id
from obs
join person on obs.person_id = person.person_id
join encounter e on obs.encounter_id = e.encounter_id
where obs.concept_id = ${input.formName.id?c}
and person.voided = 0;