package lighting.server.scene;

import java.util.ArrayList;
import java.util.List;

public class SceneStatusesMessage {

	private final List<Boolean> statuses = new ArrayList<>();

	public SceneStatusesMessage(int currentSceneId) {
		for (int i = 0; i < Scene.SCENE_COUNT; i++) {
			statuses.add(i == currentSceneId);
		}
	}

	public List<Boolean> getStatuses() {
		return statuses;
	}
}
