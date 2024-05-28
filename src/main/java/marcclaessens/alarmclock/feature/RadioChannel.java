package marcclaessens.alarmclock.feature;

public class RadioChannel {
	private final int index;
	private final String label;
	private boolean defaultSelection;
	
	public RadioChannel(int index, String label, boolean defaultSelection) {
		this.index = index;
		this.label = label;
		this.defaultSelection = defaultSelection;
	}

	public boolean isDefaultSelection() {
		return defaultSelection;
	}

	public void setDefaultSelection(boolean defaultSelection) {
		this.defaultSelection = defaultSelection;
	}

	public int getIndex() {
		return index;
	}

	public String getLabel() {
		return label;
	}
	
	
}
