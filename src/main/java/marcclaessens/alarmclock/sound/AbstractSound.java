package marcclaessens.alarmclock.sound;

import java.util.Objects;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

abstract class AbstractSound implements Sound, Comparable<AbstractSound> {
	private final SoundSourceType type;
	private final String source;
	private final int delayMillis;
	private final int index;

	public AbstractSound(SoundSourceType type, int index, String source, int delayMillis) {
		this.type = type;
		this.index = index;
		this.source = source;
		this.delayMillis = delayMillis;
	}

	final int getIndex() {
		return index;
	}
	
	public final SoundSourceType getType() {
		return type;
	}

	public final int getDelayMillis() {
		return delayMillis;
	}

	protected final String getSource() {
		return source;
	}

	public boolean equals(Object obj) {
		if (obj instanceof AbstractSound s) {
			return Objects.equals(type, s.type) && Objects.equals(index, s.index);
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(type).append(index).toHashCode();
	}

	@Override
	public int compareTo(AbstractSound o) {
		return new CompareToBuilder().append(type, o.type).append(index, o.index).toComparison();
	}
}
