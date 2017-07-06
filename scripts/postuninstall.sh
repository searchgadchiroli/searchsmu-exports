#!/bin/bash

rm /etc/init.d/bahmni-export
rm -rf /var/log/bahmni-export

rm -rf /opt/bahmni-export
rm -f /var/www/bahmni_config/bahmni_export

# removing the cron job
crontab -u bahmni -l | grep -v '/usr/bin/bahmni-export >/dev/null 2>&1'  | crontab -u bahmni -
