package lighting.server.sceneX;


import java.io.IOException;

public interface ISceneXService {
    boolean recordScene(int button_id);
    void playSceneFromButton(int button) throws IOException;
}
