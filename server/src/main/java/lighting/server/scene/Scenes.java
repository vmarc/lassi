package lighting.server.scene;

import java.util.ArrayList;
import java.util.List;

public class Scenes {

	private final List<Scene> scenes = new ArrayList<>();

	public Scenes() {
		for (int i = 0; i < Scene.SCENE_COUNT; i++) {
			int[] dmxValues = new int[Scene.SCENE_DMX_VALUES];
			String name = String.format("Scene %d", i + 1);
			scenes.add(new Scene(i, name, dmxValues));
		}
	}

	public Scene getScene(int sceneId) {
		return scenes.get(sceneId - 1);
	}

	public List<Scene> getScenes() {
		return scenes;
	}
}
