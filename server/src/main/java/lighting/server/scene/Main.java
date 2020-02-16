package lighting.server.scene;

import lighting.server.ArtNetSender;
import lighting.server.IO.SceneSerialization;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {


        System.out.println("test");
        SceneSerialization serialization = new SceneSerialization();
        try {
            serialization.saveScenesToJSON();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
