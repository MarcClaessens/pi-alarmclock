package net.mcl.alarmclock.sound;

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
public class FileSound implements Sound {
	private static final Logger LOGGER = LogManager.getLogger(FileSound.class);

	private File file;
	private final int delayMillis;

	public FileSound(String fileName, int delayMillis) {
		alterSource(fileName);
		if (!file.exists()) {
			LOGGER.error("Oops, {} does not exist", file);
		}
		this.delayMillis = delayMillis;
	}

	@Override
	public InputStream getSoundStream() {
		try {
			return new BufferedInputStream(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public int getDelayMillis() {
		return delayMillis;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof FileSound && ((FileSound) obj).file.equals(file);
	}

	@Override
	public String toString() {
		return "FileSound " + file.getAbsolutePath();
	}

	@Override
	public void alterSource(String fileName) {
		this.file = new File(fileName);
	}
}
