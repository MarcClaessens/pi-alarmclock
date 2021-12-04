package marcclaessens.alarmclock.sound;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;

import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.JavaSoundAudioDevice;

/**
 * slightly modified implementation of javazoom JavaSoundAudioDevice, optionally
 * using a dedicated Mixer instead of default device
 * 
 * @author marc
 */

public class MixerJavaSoundAudioDevice extends JavaSoundAudioDevice {
	private final Mixer mixer;
	private SourceDataLine source = null;
	private AudioFormat fmt = null;
	private byte[] byteBuf = new byte[4096];

	public MixerJavaSoundAudioDevice(Mixer mixer) {
		this.mixer = mixer;
	}

	@Override
	protected void setAudioFormat(AudioFormat fmt0) {
		fmt = fmt0;
	}

	@Override
	protected AudioFormat getAudioFormat() {
		if (fmt == null) {
			Decoder decoder = getDecoder();
			fmt = new AudioFormat(decoder.getOutputFrequency(), 16, decoder.getOutputChannels(), true, false);
		}
		return fmt;
	}

	@Override
	protected void createSource() throws JavaLayerException {
		Throwable t = null;
		try {
			Line line;
			if (mixer != null) {
				line = mixer.getLine(getSourceLineInfo());
			} else {
				line = AudioSystem.getLine(getSourceLineInfo());
			}

			if (line instanceof SourceDataLine) {
				source = (SourceDataLine) line;

				// source.open(fmt, millisecondsToBytes(fmt, 2000));
				source.open(fmt);
				/*
				 * if (source.isControlSupported(FloatControl.Type.MASTER_GAIN)) { FloatControl
				 * c = (FloatControl)source.getControl(FloatControl.Type.MASTER_GAIN);
				 * c.setValue(c.getMaximum()); }
				 */
				source.start();

			}
		} catch (RuntimeException ex) {
			t = ex;
		} catch (LinkageError ex) {
			t = ex;
		} catch (LineUnavailableException ex) {
			t = ex;
		}
		if (source == null)
			throw new JavaLayerException("cannot obtain source audio line", t);
	}

	@Override
	protected void closeImpl() {
		if (source != null) {
			source.close();
		}
	}

	@Override
	protected void writeImpl(short[] samples, int offs, int len) throws JavaLayerException {
		if (source == null)
			createSource();

		byte[] b = toByteArray(samples, offs, len);
		source.write(b, 0, len * 2);
	}

	@Override
	protected byte[] getByteArray(int length) {
		if (byteBuf.length < length) {
			byteBuf = new byte[length + 1024];
		}
		return byteBuf;
	}

	@Override
	protected void flushImpl() {
		if (source != null) {
			source.drain();
		}
	}

	@Override
	public int getPosition() {
		int pos = 0;
		if (source != null) {
			pos = (int) (source.getMicrosecondPosition() / 1000);
		}
		return pos;
	}

}
