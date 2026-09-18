import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileOutputStream;

import java.util.Scanner;

public class Main {
    static GameState[] gameStates = new GameState[2]; //[0] is current [1] is at checkpoint
    static Scene scene = new Scene();
    static String display = "";

public static void main(String[] args) throws IOException {

    FileReader gameFile = new FileReader(".\\gameData.json");
    JsonObject fullData = JsonParser.parseReader(gameFile).getAsJsonObject();
   // JsonObject metaData = fullData.getAsJsonObject("metadata");
    JsonObject gameData = fullData.getAsJsonObject("mainData");
    JsonArray inventory = fullData.getAsJsonObject("mainData").getAsJsonArray("inventory");
    gameStates[0] = new GameState(inventory);
    gameStates[1] = new GameState(inventory);
    
    
    //String gameTitle = metaData.get("title").getAsString();

    Scanner in = new Scanner(System.in);
    
    
    scene = new Scene (gameData, gameStates);


    int num = 0;

    display = scene.setScene("titleScreen");




    while(num >= 0) {

        System.out.println(display);
        num = in.nextInt();

        if (num == 99) { 
            System.out.println(gameStates[0].inventoryString()); 
        } else if (num == 55) { 
            writeSave(); 
            continue;
        } else if (num == 77) { 
            loadSave();
            continue;
         } else {
            display = scene.processChoice(num);
        }

    }

    in.close();




   gameFile.close();
}




    static void changeVar(String statement) {
        String[] parts = statement.split(" ");
        int varNum = Integer.parseInt(parts[0].substring(1));
        String operation = parts[1];
        int numOperator = Integer.parseInt(parts[2]);

        System.out.printf("%d %s %d", varNum, operation, numOperator);


    }

    static void writeSave() throws FileNotFoundException, IOException {
        JsonObject statesToSave = new JsonObject();
        JsonObject currentState = gameStates[0].dataToJson();
        JsonObject checkpointState = gameStates[1].dataToJson();

        FileOutputStream FOS = new FileOutputStream("Save.sav");
        PrintWriter printer = new PrintWriter(FOS);

        statesToSave.add("currentState", currentState);
        statesToSave.add("checkpointState", checkpointState);

        printer.println(statesToSave.toString());

        printer.close();
        FOS.close();

    }

    static void loadSave() throws FileNotFoundException, IOException {
        FileReader saveFile = new FileReader("save.sav");
        JsonObject loadedData = JsonParser.parseReader(saveFile).getAsJsonObject();

        JsonObject currentState = loadedData.get("currentState").getAsJsonObject();
        JsonObject checkpointState = loadedData.get("checkpointState").getAsJsonObject();

        saveFile.close();

        gameStates[0].restoreState(currentState);
        gameStates[1].restoreState(checkpointState);

        if (!gameStates[0].getMusic().isEmpty()) {
            scene.playSong(gameStates[0].getMusic());
        }

        display = scene.setScene(gameStates[0].getLabel());
    }


        
        
}
