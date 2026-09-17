public class GameState {

    private String label = "titleScreen";
    private boolean[] inventory = new boolean[50];
    private int[] vars = new int[50];
    private String music = "";
    private String image = "";

    public GameState() {

    }

    public String getImage() {
        return image;
    }

    public void setimage(String image){
        this.music = image;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music){
        this.music = music;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String newLabel) {
        label = newLabel;
    }

    public void setImage(String newImg) {
        image = newImg;
    }

    public int[] getVars() {
        return vars;
    }

    public boolean[] getInventory() {
        return inventory;
    }

    public void updateCheckpoint(GameState copyFrom) {
        label = copyFrom.getLabel();
        inventory = copyFrom.getInventory().clone();
        vars = copyFrom.getVars().clone();
        music = copyFrom.getMusic();
        image = copyFrom.getImage();
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
        String operation = parts[1];
        if(condition.charAt(0) == 'V') {
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
            return (operation.equals("Owned")) ? inventory[varNum] : !inventory[varNum];
        }
    }


}
