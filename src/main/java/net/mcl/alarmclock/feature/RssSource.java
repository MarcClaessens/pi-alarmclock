package net.mcl.alarmclock.feature;

public class RssSource {
    private final String sourceUrl;
    private final String label;

    public RssSource(String label, String sourceUrl) {
        this.label = label;
        this.sourceUrl = sourceUrl;
    }

    public String getLabel() {
        return label;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

}
