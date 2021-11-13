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

	private final File file;

	public FileSound(File file) {
		this.file = file;
		if (!file.exists()) {
			LOGGER.error("Oops, {} does not exist", file);
		}
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
	public String toString() {
		return "FileSound " + file.getAbsolutePath();
	}
}
