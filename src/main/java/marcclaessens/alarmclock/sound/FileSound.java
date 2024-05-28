package marcclaessens.alarmclock.sound;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Sound from local MP3 file.
 */
class FileSound extends AbstractSound {
	private static final Logger LOGGER = LogManager.getLogger(FileSound.class);


	public FileSound(SoundSourceType type, int index, String fileName, int delayMillis) {
		super(type, index, fileName, delayMillis);
	}

	@Override
	public InputStream getSoundStream() {
		try {
			return new BufferedInputStream(new FileInputStream(new File(getSource())));
		} catch (FileNotFoundException e) {
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	
	@Override
	public String toString() {
		return "FileSound " + getSource();
	}
}
