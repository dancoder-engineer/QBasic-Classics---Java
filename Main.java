import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;

import java.util.Scanner;

public class Main {
public static void main(String[] args) throws IOException {
    //PattysCakes.test();

    FileReader gameFile = new FileReader(".\\gameData.json");
    JsonObject fullData = JsonParser.parseReader(gameFile).getAsJsonObject();
   // JsonObject metaData = fullData.getAsJsonObject("metadata");
    JsonObject gameData = fullData.getAsJsonObject("mainData");
    GameState[] gameStates = new GameState[2]; //[0] is current [1] is at checkpoint
    gameStates[0] = new GameState();
    gameStates[1] = new GameState();
    //String gameTitle = metaData.get("title").getAsString();

    Scanner in = new Scanner(System.in);
    
    
    Scene scene = new Scene (gameData, gameStates);
    scene.changeScene("titleScreen");


    int num = 0;

    String display = scene.processChoice(1);

    while(num >= 0) {
         

        


        System.out.println(display);
        num = in.nextInt();
        display = scene.processChoice(num);

    }

    in.close();




   gameFile.close();
}




    static void changeVar(String statement) {//int varNum, String operation, int numOperator) {
        String[] parts = statement.split(" ");
        int varNum = Integer.parseInt(parts[0].substring(1));
        String operation = parts[1];
        int numOperator = Integer.parseInt(parts[2]);

        System.out.printf("%d %s %d", varNum, operation, numOperator);


    }
        
        
}
