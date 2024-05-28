package marcclaessens.alarmclock.sound;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Sound from a web source (can be an MP3 radio stream).
 */
class WebMp3Sound extends AbstractSound {
	private static final Logger LOGGER = LogManager.getLogger(WebMp3Sound.class);


	public WebMp3Sound(SoundSourceType type, int index, String url, int delayMillis) {
		super(type, index, url, delayMillis);
	}

	
	private URL getUrl() {
		try {
			return new URL(getSource());
		} catch (MalformedURLException e) {
			LOGGER.error(e);
			return null;
		}
	}

	@Override
	public InputStream getSoundStream() {
		try {
			URL urlImpl = getUrl();
			URLConnection connection = urlImpl.openConnection();
			connection.setConnectTimeout(2000);
			connection.getInputStream();
			return new BufferedInputStream(urlImpl.openStream());
		} catch (IOException e) {
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public String toString() {
		return "WebMp3Sound " + getSource();
	}
}
