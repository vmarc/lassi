package lighting.server.scene;

public class Main {
    public static void main(String[] args) {
        SceneServiceImpl sceneService = new SceneServiceImpl();
        sceneService.saveSceneToJSON();

    }
}
