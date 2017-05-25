select max(visit_count) max_visit_count from
  (select distinct count(encounter.visit_id) visit_count, obs.person_id
from obs
  join encounter on obs.encounter_id = encounter.encounter_id
  join visit on visit.visit_id = encounter.visit_id
where obs.concept_id = (:form_concept_id)
      and obs.voided = 0
      and visit.voided = 0
GROUP BY obs.person_id) abc
;
