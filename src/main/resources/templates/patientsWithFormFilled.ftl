select distinct obs.person_id,
pid.identifier as identifier,
concat(pn.given_name, ' ', pn.family_name) name,
person.birthdate as birthDate,
person.death_date as deathDate,
floor(datediff(IFNULL(person.death_date, CURDATE()), person.birthdate) / 365) AS age,
person.gender gender,
pa.city_village village,
pa.address4 block,
pa.address3 tehsil,
pa.county_district district
from obs
join person on obs.person_id = person.person_id
join patient_identifier pid on person.person_id = pid.patient_id
join person_name pn on pn.person_id = person.person_id
join person_address pa on pn.person_id = pa.person_id
where obs.concept_id = ${input["form"].formName.id?c}
and obs.voided = 0
and person.voided = 0
and date(obs_datetime) BETWEEN '${input["startDate"]}' AND '${input["endDate"]}';


