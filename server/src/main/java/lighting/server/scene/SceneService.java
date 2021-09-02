package lighting.server.scene;

import java.io.IOException;

public interface SceneService {

	boolean recordScene(int buttonId);

	boolean playSceneFromButton(int buttonId) throws IOException;

	boolean playSceneFromId(String sceneId) throws IOException;

	boolean recordSceneMultipleFrames(int buttonId);

	boolean stopRecording();

	void stop();

	void pause(boolean bool);
}
