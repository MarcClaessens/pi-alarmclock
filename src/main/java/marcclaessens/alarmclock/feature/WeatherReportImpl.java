package marcclaessens.alarmclock.feature;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import marcclaessens.alarmclock.WeatherReport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

class WeatherReportImpl implements WeatherReport, ActionListener {
    private static final Logger LOGGER = LogManager.getLogger(WeatherReportImpl.class);

    private boolean reportOn;
    private List<WeatherReportListener> listeners = new ArrayList<>();
    private final String source;

    public WeatherReportImpl(String source) {
        this.source = source;
        // update every ten minutes
        Timer timer = new Timer(1000 * 60 * 10, this);
        timer.start();
    }

    @Override
    public void setWeatherReportOn(boolean messageOn) {
        this.reportOn = messageOn;
        if (!messageOn) {
            updateWeather(false);
        } else {
            updateWeather(true);
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

    @Override
    public void actionPerformed(ActionEvent event) {
        if (reportOn) {
            updateWeather(true);
        }
    }

    private void updateWeather(boolean getReport) {
        if (listeners.size() > 0) {
            if (!getReport) {
                listeners.parallelStream().forEach(l -> l.updateWeatherStatus("ABC", false));
            } else {
                String report = getWeatherReport();
                listeners.parallelStream().forEach(l -> l.updateWeatherStatus(report, true));
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
