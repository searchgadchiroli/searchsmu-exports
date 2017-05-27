select concept_answer.answer_concept as id, concept.concept_full_name as name,
  COALESCE (cv.code , concept.concept_full_name ,concept.concept_short_name)as title
from concept_answer
join concept_view concept on concept_answer.answer_concept = concept.concept_id
  left outer join concept_reference_term_map_view cv on (cv.concept_id = concept.concept_id  and cv.concept_map_type_name = 'SAME-AS' and cv.concept_reference_source_name = 'EndTB-Export')
where concept_answer.concept_id = :questionConceptId;
