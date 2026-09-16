import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;

import java.util.Scanner;

public class Main {
public static void main(String[] args) throws IOException {
    //PattysCakes.test();

    // FileReader gameFile = new FileReader(".\\gameData.json");
    // JsonObject fullData = JsonParser.parseReader(gameFile).getAsJsonObject();
    // JsonObject metaData = fullData.getAsJsonObject("metadata");
    // JsonObject gameData = fullData.getAsJsonObject("mainData");
    // GameState[] gameStates = new GameState[2]; //[0] is current [1] is at checkpoint
    // String gameTitle = metaData.get("title").getAsString();

    // Scanner in = new Scanner(System.in);
    
    
    // Scene scene = new Scene (gameData, gameStates);
    // scene.changeScene("Intro");


    // int num = 0;

    // while(num >= 0) {

    //     String display = "";

    //     // display += scene.makeText();
    //     // display += "\n\n";
    //     // display += scene.makeOptions();
    //     System.out.println(display);
    //     num = in.nextInt();
    //     scene.processChoice(num);

    // }

    // in.close();


    String varOper = "v4 = 2";
    changeVar(varOper);




   // gameFile.close();
}




    static void changeVar(String statement) {//int varNum, String operation, int numOperator) {
        String[] parts = statement.split(" ");
        int varNum = Integer.parseInt(parts[0].substring(1));
        String operation = parts[1];
        int numOperator = Integer.parseInt(parts[2]);

        System.out.printf("%d %s %d", varNum, operation, numOperator);


    }
        
        
}
