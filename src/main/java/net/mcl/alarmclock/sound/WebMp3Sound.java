package net.mcl.alarmclock.sound;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Sound from a web source (can be an MP3 radio stream).
 */
public class WebMp3Sound implements Sound {
    private static final Logger LOGGER = LogManager.getLogger(WebMp3Sound.class);

    private final URL url;

    public WebMp3Sound(String url) {
        this.url = getUrl(url);
    }

    private URL getUrl(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            LOGGER.error(e);
            return null;
        }
    }

    @Override
    public InputStream getSoundStream() {
        if (url == null) {
            return null;
        }
        try {
            return new BufferedInputStream(url.openStream());
        } catch (IOException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }
}
