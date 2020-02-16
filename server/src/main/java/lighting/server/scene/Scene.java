package lighting.server.scene;

import java.time.Duration;

public class Scene {

	public static final int SCENE_COUNT = 9;
	public static final int SCENE_DMX_VALUES = 512;

	private int id;
	private int[] dmxValues;
	private String name;

	public Scene() {
	}

	public Scene(int id, String name, int[] dmxValues) {
		this.id = id;
		this.dmxValues = dmxValues;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
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
