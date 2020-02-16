package lighting.server.scene;

import java.util.ArrayList;
import java.util.List;

public class Scenes {

	private String name;
	private List<Scene> scenes = new ArrayList<>();

	public Scenes() {
		this.name = "test";
		/*for (int i = 0; i < Scene.SCENE_COUNT; i++) {
			int[] dmxValues = new int[Scene.SCENE_DMX_VALUES];
			String name = String.format("Scene %d", i + 1);
			scenes.add(new Scene(i, "test",  dmxValues));
		}*/
	}

	public String getName() {
		return name;
	}

	public Scene getScene(int sceneId) {
		return scenes.get(sceneId - 1);
	}

	public List<Scene> getScenes() {
		return scenes;
	}

	public void addSceneToScenes(Scene scene) {
		this.scenes.add(scene);
	}

}
