# PI Alarm Clock

This is an alarmclock application to be run on my Raspberry Pi 4 using touchscreen.
Time is synchronized automatically with the OS.

You can configure a primary and secondary alarm source and the delay between them.
The first one is a radio channel, the second is an annoying rooster.

Radio channels,file sound location, button location and font icons are configurable in app.properties.


Other features include :

 - RSS updates
 - weather forcast
 - button color change
 - radio channel selection
 - whitenoise play

Requires Java 17 but is be backwards compatible with Java 11 if you make the necessary changes in the pom.xml.

License : GPL v3


## Installation instructions
 
### Prepare Raspbian

  Install Raspberry Imager  --    https://www.raspberrypi.com/software/
  
  Install full Desktop to memory card  (not the default one - it doesn't work properly)
 
### Boot up Pi

 Configure Wireless network
 
 Disable Bluetooth
 
 Configure for timezone Belgium/Brussels
 
 Change Keyboard to be/be
 
 Test if sound works via a youtube link
 
### Install Pre-requisites

    sudo apt-get update 
    sudo apt-get install lame mpg321 vim default-jdk
    
    sudo apt-get remove --purge wolfram-engine
    sudo apt-get full-upgrade
    sudo apt-get clean
    sudo apt-get autoremove
    
    vim ~/.bash_aliases
    ---  add ---
    alias ll='ls -al'
    ------
    
    . ~/.bash_aliases
    
    ll
    
    mkdir alarmclock
    mv alarmclock.tar.gz alarmclock
    cd alarmclock

### Install Pi-Clock

    [from Ubuntu]
    <cd marc-pi directory>
    scp target/alarmclock*-jar-with-dependencies.jar pi@192.168.1.6:/home/pi/alarmclock
    scp *.mp3 pi@192.168.1.6:/home/pi/alarmclock
    scp installation/alarm.sh pi@192.168.1.6:/home/pi/alarmclock/alarm.sh
    scp installation/app.properties pi@192.168.1.6:/home/pi/alarmclock/app.properties

    [from pi]
    cd ~/Desktop
    ln -s /home/pi/alarmclock/alarm.sh alarm.sh


### Autostart Pi-Clock
 
    cd ~/.config
    vim wayfire.ini


    ---  add ---
    [autostart]
    pic_clock=/home/pi/alarmclock/alarm.sh
    ------------

### Ideas for next releases

 select default radio-channel from the pre-selected radio channels as default wake up channel
 
 externalize log4j2.xml
 
 give a visual ( and audio ?)  warning if the requested stream is not available