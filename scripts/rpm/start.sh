#!/bin/sh

. /etc/bahmni-installer/bahmni.conf
nohup java -DOPENMRS_DB_SERVER=${OPENMRS_DB_SERVER} -DOPENMRS_DB_USER=${OPENMRS_DB_USERNAME} -DOPENMRS_DB_PASSWORD=${OPENMRS_DB_PASSWORD} -jar /opt/bahmni-export/libs/bahmni-export-*.jar >> /opt/bahmni-export/log/bahmni-export.log 2>&1 &
echo $! > /var/run/bahmni-export/bahmni-export.pid
