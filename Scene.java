import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;

public class Scene {

    private JsonObject scene;
    private JsonObject gameData;
    private ArrayList<String> labels = new ArrayList<String>();
    private int commandNum; //keeps track of what displayed option corresponds to what number on screen for adding with commands
    private GameState[] gameStates;
    private String outputText;

    public Scene (JsonObject dataObject, GameState[] gStates) {
        gameData = dataObject;
        scene = gameData.getAsJsonObject("titleScreen");
        gameStates = gStates;

    }

    public String makeText () {
        if(!scene.has("text")) { return ""; }
        JsonArray textArray = scene.getAsJsonArray("text");
        String textString = "";
        for(int i = 0; i < textArray.size(); i++) {
            textString += textArray.get(i).getAsString();
            if(i < textArray.size() - 1) { textString += "\n"; }
        }
        return textString;
    }

    public String makeOptions() {
        String textString = "";
        if(scene.has("options")) {
            JsonArray textArray = scene.getAsJsonArray("options");
            JsonArray optionResults = scene.getAsJsonArray("labels");
            for(commandNum = 0; commandNum < textArray.size(); commandNum++) {
                textString += (commandNum + 1) + ". " + textArray.get(commandNum).getAsString();
                labels.add(optionResults.get(commandNum).getAsString());
                if(commandNum < textArray.size() - 1) { textString += "\n"; }
            }

            textString += "\n";
        }

        return textString;
    }


    public void changeScene(String sceneName) {
        labels.clear();
        scene = gameData.getAsJsonObject(sceneName);
        gameStates[0].setLabel(sceneName);

        if (scene.has("Image")) {
            String imgName = scene.get("Image").getAsString();
            gameStates[0].setImage(imgName);
            //TODO logic to show image when on a platform that supports it 
        }

        if(scene.has("Junction")) {
            String nextScene = "";
            for(int i = 0; i < scene.getAsJsonArray("Junction").size(); i++){
                JsonArray junction = scene.getAsJsonArray("Junction").get(i).getAsJsonArray();
                if (gameStates[0].ifCondition(junction.get(0).getAsString())) {
                    nextScene = junction.get(1).getAsString();
                } else if (junction.size() > 2) {
                    nextScene = junction.get(2).getAsString();
                }
                if (!nextScene.equals("")) {
                    changeScene(nextScene); 
                    return;
                }
            }

        } else {
            commandNum = 0;
            outputText = "";
            outputText += makeText() + "\n" + makeOptions();
            processCommands();
        }
    }

    public String processChoice(int choice) {
        changeScene(labels.get(choice - 1));

        


        return outputText;
    }


    public void processCommands() {

        if(scene.has("commands")) {
            for (JsonElement command : scene.getAsJsonArray("commands")){
                processCommand(command.getAsJsonArray());
            }
        }

    }
    


    public void processCommand(JsonArray cmdArray) {

        String cmdName = cmdArray.get(0).getAsString();

        switch(cmdName) {
            case "Checkpoint":
                gameStates[1].updateCheckpoint(gameStates[0]);
                break;
            case "Game Over":
                outputText = "1. Return to Checkpoint";
                labels.clear();
                labels.add(gameStates[1].getLabel());
                gameStates[0].updateCheckpoint(gameStates[1]);
                break;
            case "Variable Math":
                gameStates[0].variableMath(cmdArray.get(1).getAsString());
                break;
            case "Give Item":
                gameStates[0].giveItem(cmdArray.get(1).getAsInt());
                break;
            case "Remove Item":
                gameStates[0].removeItem(cmdArray.get(1).getAsInt());
                break;
            case "Add Label If": 
                if(gameStates[0].ifCondition(cmdArray.get(1).getAsString())) { 
                    outputText += (commandNum + 1) + ". " + cmdArray.get(2).getAsString() + "\n";
                    labels.add(cmdArray.get(3).getAsString());
                    commandNum++;
                }
                break;

            
        }
    }

}








//     "Play Music": (commands) => { 
//         if (currentState.music != commands[1]) {
//             currentState.music = commands[1]
//             music.src = "./sound/" + commands[1]
//             music.play()
//         }
//     },

//     "Stop Music": () => { 
//         music.pause()
//     },

//     "Play SFX": (commands) => { 
//             sfx.src = "./sound/" + commands[1]
//             sfx.play()
//     },

