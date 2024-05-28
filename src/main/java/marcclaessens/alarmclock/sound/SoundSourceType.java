package marcclaessens.alarmclock.sound;

public enum SoundSourceType {
	RADIO_CHANNEL(true), RADIO_ALARM(false), REPEATING_ALARM(false), WHITENOISE(false);
	
	private boolean indexed;
	
	SoundSourceType(boolean b) {
		indexed = b;
	}
	
	public boolean isIndexed() {
		return indexed;
	}
}
