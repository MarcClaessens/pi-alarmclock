#!/bin/bash
cd /home/pi/alarmclock
export DISPLAY=:0.0

nohup java -Xms32M -Xmx256M -cp alarmclock-*-SNAPSHOT-jar-with-dependencies.jar marcclaessens.alarmclock.Main -Dlog4j2.file=./log4j2.xml & 
exit 0
