package lighting.server.scene;


import java.io.IOException;

public interface ISceneService {
    boolean recordScene(int button_id);
    boolean playSceneFromButton(int button) throws IOException;
    void playSceneFromId(String id) throws IOException;
    boolean recordSceneMultipleFrames(int button_id);
    boolean stopRecording();
}
