import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.ArrayList;

public class Scene {

    private JsonObject scene;
    private JsonObject gameData;
    private ArrayList<String> labels = new ArrayList<String>();
    private int commandNum; //keeps track of what displayed option corresponds to what number on screen for adding with commands
    private GameState[] gameStates;

    public Scene (JsonObject dataObject, GameState[] gStates) {
        gameData = dataObject;
        scene = gameData.getAsJsonObject("titleScreen");
        gameStates = gStates;

    }

    public String makeText () {
        JsonArray textArray = scene.getAsJsonArray("text");
        String textString = "";
        for(int i = 0; i < textArray.size(); i++) {
            textString += textArray.get(i).getAsString();
            if(i < textArray.size() - 1) { textString += "\n"; }
        }
        return textString;
    }

    public String makeOptions() {
        JsonArray textArray = scene.getAsJsonArray("options");
        JsonArray optionResults = scene.getAsJsonArray("labels");
        String textString = "";
        labels.clear();
        for(commandNum = 0; commandNum < textArray.size(); commandNum++) {
            textString += (commandNum + 1) + ". " + textArray.get(commandNum).getAsString();
            labels.add(optionResults.get(commandNum).getAsString());
            if(commandNum < textArray.size() - 1) { textString += "\n"; }
        }
        return textString;
    }


    public void changeScene(String sceneName) {
        scene = gameData.getAsJsonObject(sceneName);
    }

    public String processChoice(int choice) {
        changeScene(labels.get(choice - 1));

        String show = makeText() + "\n" + makeOptions();

        //process commands

        return show;
    }

}
