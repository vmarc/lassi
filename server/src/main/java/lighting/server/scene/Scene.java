package lighting.server.scene;

import java.sql.Time;

public class Scene {

	public static final int SCENE_COUNT = 9;
	public static final int SCENE_DMX_VALUES = 512;

	private final int id;
	private final int[] dmxValues;

	public Scene(int id, int[] dmxValues, Time time) {
		this.id = id;
		this.dmxValues = dmxValues;
	}

	public int getId() {
		return id;
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
