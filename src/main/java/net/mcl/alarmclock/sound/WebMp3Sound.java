package net.mcl.alarmclock.sound;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class WebMp3Sound implements Sound {
	private final URL url;
	
	
   public WebMp3Sound(String url) {
        this.url = getUrl(url);
    }

   private URL getUrl(String url) {
       try {
           return new URL(url);
       } catch (MalformedURLException e) {
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
			throw new RuntimeException(e);
		}
	}
}
