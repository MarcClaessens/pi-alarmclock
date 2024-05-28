package marcclaessens.alarmclock.sound;

import java.util.Set;
import java.util.TreeSet;

public final class SoundRepository {
	private static final Set<AbstractSound> COLLECTION = new TreeSet<>();
	
	private SoundRepository() {
		// helper class
	}
	
	public static Sound get(SoundSourceType type) {
		if (type.isIndexed()) {
			throw new IllegalArgumentException("Expected index for type " + type);
		}
		return COLLECTION.stream().filter(s -> s.getType().equals(type)).findFirst().orElseThrow(() -> new IllegalStateException("Invalid sound key " + type));
	}
	
	public static Sound get(SoundSourceType type, int index) {
		if (!type.isIndexed()) {
			throw new IllegalArgumentException("Did not expect index for type " + type);
		}
		return COLLECTION.stream().filter(s -> s.getType().equals(type) && s.getIndex() == index).findFirst().orElseThrow(() -> new IllegalStateException("Invalid sound key " + type));
	}

	
	public static void register(SoundSourceType type, String source, int delayMillis) {
		register(type, 0, source, delayMillis);
	}
	
	public static void register(SoundSourceType type, int index, String source, int delayMillis) {
		if (source.startsWith("http")) {
			COLLECTION.add(new WebMp3Sound(type, index, source, delayMillis));
		} else {
			COLLECTION.add(new FileSound(type, index, source, delayMillis));
		}
	}
}
