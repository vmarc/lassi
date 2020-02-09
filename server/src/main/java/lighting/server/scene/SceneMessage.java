package lighting.server.scene;

public class SceneMessage {

	private final Scene scene;

	public SceneMessage(Scene scene) {
		this.scene = scene;
	}

	public Scene getScene() {
		return scene;
	}
}
