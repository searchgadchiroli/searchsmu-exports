select obs.obs_id, obs.encounter_id, visit.visit_id, visit.date_started visit_date
from obs
  join encounter on obs.encounter_id = encounter.encounter_id
  join visit on visit.visit_id = encounter.visit_id
where obs.concept_id = (:form_concept_id)
      and obs.voided = 0
      and visit.voided = 0
      and obs.person_id = (:person_id);
