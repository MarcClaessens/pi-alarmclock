package net.mcl.alarmclock.feature;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

class WeatherReportImpl implements WeatherReport {
    private static final Logger LOGGER = LogManager.getLogger(WeatherReportImpl.class);

    private boolean reportOn;
    private List<WeatherReportListener> listeners = new ArrayList<>();
    private final String source;

    public WeatherReportImpl(String source) {
        this.source = source;
        // update every ten minutes
        Duration refresh = Duration.millis(10 * 60 * 1000L);
        Timeline timeline = new Timeline(new KeyFrame(refresh, this::updateWeather));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    @Override
    public void setWeatherReportOn(boolean messageOn) {
        this.reportOn = messageOn;
        if (!messageOn) {
            updateWeather(null);
        } else {
            updateWeather(new ActionEvent());
        }
    }

    @Override
    public void toggleWeatherReportOn() {
        setWeatherReportOn(!this.reportOn);
    }

    @Override
    public void registerListener(WeatherReportListener l) {
        listeners.add(l);
    }

    private void updateWeather(ActionEvent event) {
        if (listeners.size() > 0) {
            if (event == null) {
                listeners.parallelStream().forEach(l -> l.updateWeatherStatus(""));
            } else {
                String report = getWeatherReport();
                listeners.parallelStream().forEach(l -> l.updateWeatherStatus(report));
            }
        }
    }

    private String getWeatherReport() {
        try {
            URL url = new URL(source);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            String line = null;
            try (InputStreamReader in = new InputStreamReader((InputStream) conn.getContent())) {
                BufferedReader buff = new BufferedReader(in);
                line = buff.readLine();
            }
            conn.disconnect();
            return parseLine(line);
        } catch (IOException e) {
            LOGGER.error(e);
        }
        return null;
    }

    private static String parseLine(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            StringBuilder sb = new StringBuilder();
            sb.append(jsonObject.getJSONArray("weather").getJSONObject(0).getString("description"));
            sb.append(" / min ");
            sb.append(jsonObject.getJSONObject("main").getInt("temp_min"));
            sb.append("°C / max ");
            sb.append(jsonObject.getJSONObject("main").getInt("temp_max"));
            sb.append("°C");
            return sb.toString();
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return null;
    }

    @Override
    public boolean isWeatherReportOn() {
        return reportOn;
    }

}
