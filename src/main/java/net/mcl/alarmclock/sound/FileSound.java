package net.mcl.alarmclock.sound;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FileSound implements Sound {
	private final File file;
	
	public FileSound(File file) {
		this.file = file;
	}

	@Override
	public InputStream getSoundStream() {
		try {
			return new BufferedInputStream(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
