package marcclaessens.alarmclock.feature;

public class RadioChannelSource {
    private final String sourceUrl;
    private final String label;

    public RadioChannelSource(String label, String sourceUrl) {
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
