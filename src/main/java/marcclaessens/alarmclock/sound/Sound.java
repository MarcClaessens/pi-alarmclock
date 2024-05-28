package marcclaessens.alarmclock.sound;

import java.io.InputStream;

public interface Sound {
	InputStream getSoundStream();

	int getDelayMillis();
}
