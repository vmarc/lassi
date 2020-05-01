package lighting.server;

import lighting.server.sceneX.ISceneXService;

public class Recorder {

    private static ISceneXService service;

    public Recorder(ISceneXService service) {
        this.service = service;
    }

    public static void main(String[] args) {
        service.recordScene(0);
    }
}
