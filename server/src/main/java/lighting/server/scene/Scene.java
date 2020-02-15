package lighting.server.scene;

import java.time.Duration;

public class Scene {

	public static final int SCENE_COUNT = 9;
	public static final int SCENE_DMX_VALUES = 512;

	private int id;
	private int[] dmxValues;
	private Duration time;

	public Scene() {
	}

	public Scene(int id, int[] dmxValues, Duration time) {
		this.id = id;
		this.dmxValues = dmxValues;
		this.time = time;
	}

	public int getId() {
		return id;
	}

	public Duration getTime() {
		return time;
	}

	public int[] getDmxValues() {
		return dmxValues;
	}

	public int getDmxValue(int index) {
		return dmxValues[index];
	}

	public void setDmxValue(int index, int value) {
		dmxValues[index] = value;
	}
}
