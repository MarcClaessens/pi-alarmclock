package net.mcl.alarmclock.sound;

import java.io.InputStream;

public interface Sound {
	InputStream getSoundStream();

	void alterSource(String source);

	int getDelayMillis();
}
