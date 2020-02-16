package lighting.server.scene;

import lighting.server.IO.SceneSerialization;

import java.io.IOException;

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
