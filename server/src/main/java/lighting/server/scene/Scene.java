package lighting.server.scene;

public class Scene {

	public static final int SCENE_COUNT = 9;
	public static final int SCENE_DMX_VALUES = 512;

	private final int id;
	private final String name;
	private final int[] dmxValues;

	public Scene(int id, String name, int[] dmxValues) {
		this.id = id;
		this.name = name;
		this.dmxValues = dmxValues;
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
