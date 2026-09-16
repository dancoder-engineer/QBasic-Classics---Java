public class GameState {

    String label = "";
    boolean[] inventory = new boolean[50];
    int[] vars = new int[50];
    String music = "";
    String image = "";

    public GameState() {

    }

    public void copyState(GameState copyFrom) {

    }

    public void giveItem(int itemNum) {
        inventory[itemNum] = true;
    }

    public void removeItem(int itemNum) {
        inventory[itemNum] = false;
    }

    public void changeVar(String statement) {

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


}
