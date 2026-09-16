public class GameState {

    String label = "";
    boolean[] inventory = new boolean[50];
    int[] vars = new int[50];
    String music = "";
    String image = "";

    public GameState() {

    }

    public void setLabel(String newLabel) {
        label = newLabel;
    }

    public void updateCheckpoint(GameState copyFrom) {
        label = copyFrom.label;
        inventory = copyFrom.inventory.clone();
        vars = copyFrom.vars.clone();
        music = copyFrom.music;
        image = copyFrom.image;
    }

    public void giveItem(int itemNum) {
        inventory[itemNum] = true;
    }

    public void removeItem(int itemNum) {
        inventory[itemNum] = false;
    }

    public void variableMath(String statement) {

        String[] parts = statement.split(" ");
        int varNum = Integer.parseInt(parts[0].substring(1));
        String operation = parts[1];
        int numOperator = Integer.parseInt(parts[2]);

        switch (operation) {
            case "=":
                vars[varNum] = numOperator;
                System.out.println(varNum + " -- " + vars[varNum]);
                break;
            case "+":
                vars[varNum] += numOperator;
                break;
            case "-":
                vars[varNum] -= numOperator;
                break;
        }

    }

    public void playMusic(String fileName) {
        music = fileName;
    }

    public void showImage(String fileName) {
        image = fileName;
    }

    public boolean ifCondition(String condition) {
        String[] parts = condition.split(" ");
        int varNum = Integer.parseInt(parts[0].substring(1));
        if(condition.charAt(0) == 'V') {
            String operation = parts[1];
            int numOperator = Integer.parseInt(parts[2]);switch (operation) {
                case "=":
                    return vars[varNum] == numOperator;
                case "<":
                    return vars[varNum] < numOperator;
                case ">":
                    return vars[varNum] > numOperator;
                default:
                    return true;
            }
        } else {
            return inventory[varNum];
        }



    }


}
