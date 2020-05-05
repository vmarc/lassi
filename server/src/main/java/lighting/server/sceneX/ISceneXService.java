package lighting.server.sceneX;

import lighting.server.frame.Frame;

import java.io.IOException;

public interface ISceneXService {
    boolean recordScene(int button_id);
    void playSceneFromButton(int button) throws IOException;
    Frame getLiveData();
    boolean isRecordingDone();
}
