package lighting.server.scene;

public interface SceneService {

	Reply recordScene(int sceneId);

	Reply gotoScene(int sceneId);

	Scene getScene(int sceneId);

	Scene getCurrentScene();

	Scenes getScenes();

	void printSetup(Setup setup);
}
