#!/bin/bash

if [ ! -d /opt/bahmni-export/conf ]; then
    mkdir -p /opt/bahmni-export/conf
fi

#create bahmni user and group if doesn't exist
USERID=bahmni
GROUPID=bahmni
/bin/id -g $GROUPID 2>/dev/null
[ $? -eq 1 ]
groupadd bahmni

/bin/id $USERID 2>/dev/null
[ $? -eq 1 ]
useradd -g bahmni bahmni

#create bahmni_export directory if it does not exist
if [ ! -d /home/bahmni/bahmni_export ]; then
    mkdir -p /home/bahmni/bahmni_export
fi

if [ ! -d /opt/bahmni-export/log/ ]; then
    mkdir -p /opt/bahmni-export/log/
fi

if [ ! -d /opt/bahmni-export/run/ ]; then
    mkdir -p /opt/bahmni-export/run/
fi


#create links
ln -s /opt/bahmni-export/run /var/run/bahmni-export
ln -s /opt/bahmni-export/bahmni-export /var/run/bahmni-export/bahmni-export
ln -s /opt/bahmni-export/bin/bahmni-export /etc/init.d/bahmni-export
ln -s /opt/bahmni-export/log /var/log/bahmni-export
ln -s /home/bahmni/bahmni_export /opt/bahmni-export/bahmni_export


# permissions
chown -R bahmni:bahmni /etc/init.d/bahmni-export
chown -R bahmni:bahmni /opt/bahmni-export
chown -R bahmni:bahmni /var/log/bahmni-export
chown -R bahmni:bahmni /home/bahmni/bahmni_export

# adding cron job for scheduling the job at 11:30PM everyday
crontab -u bahmni -l | { cat; echo "30 23 * * * /usr/bin/bahmni-export >/dev/null 2>&1"; } | crontab -u bahmni -
