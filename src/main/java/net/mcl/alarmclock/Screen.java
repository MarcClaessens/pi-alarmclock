package net.mcl.alarmclock;

public interface Screen {
     double getHeight();

     double getWidth();

     void fullScreen();

     void exitFullScreen();

     boolean isFullScreen();
    
     void setMenuScene();
     void setAlarmTimeScene();
    
     void setClockScene();

	void setRssScene();
}
