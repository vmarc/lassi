package lighting.server.scene;

import java.io.IOException;

public interface SceneService {

	boolean recordScene(int button_id);

	boolean playSceneFromButton(int button) throws IOException;

	boolean playSceneFromId(String id) throws IOException;

	boolean recordSceneMultipleFrames(int button_id);

	boolean stopRecording();

	void stop();

	void pause(boolean bool);
}
