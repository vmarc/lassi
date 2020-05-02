package lighting.server.sceneX;

import java.io.IOException;

public interface ISceneXService {
    void recordScene(int button_id);
    void playSceneFromButton(int button) throws IOException;
}
