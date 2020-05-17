package lighting.server.scene;


import java.io.IOException;

public interface ISceneService {
    boolean recordScene(int button_id);
    void playSceneFromButton(int button) throws IOException;
    void playSceneFromId(String id) throws IOException;
    public boolean recordSceneMultipleFrames(int button_id);
    public void stopRecording();
}
