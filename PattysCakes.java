// import any necessary packages here below
import java.util.Scanner;
public class PattysCakes {
   public static void main(String[] args) {
      // write your program here.

      Scanner in = new Scanner(System.in);

      String[] flavors = {
        "Vanilla Delight",
        "Chocolate Dream",
        "Strawberry Bliss",
        "Caramel Drizzle"
      };

      double[] prices = {
        2.5,
        3.0,
        2.75,
        3.5
      };

      int howMany = 0;
      double salesTotal = 0.0;

      System.out.print("Welcome to Patty's Cakes!\n\n");

      while(true) {
        System.out.print("How many cupcakes would you like, 4 or 6? ");
        howMany = in.nextInt();
        if((howMany == 4) || (howMany == 6)){
            break;
        } else {
            System.out.println("Invalid selection, please try again.");
        }
      }

      System.out.printf("Great! Let's start filling your %d-pack.\n\n", howMany);

      System.out.println("Cupcake Menu:");
      for(int i = 0; i <= 3; i++){
      //  System.out.printf("%d. %-17s: ", (i+1), flavors[i]);
        System.out.print((i + 1) + ". " + flavors[i] + ": ");
        if(i != 2) { 
            System.out.print(" ");
        }
        System.out.printf("$%.2f%n", prices[i]);
      }

      String finalPrint = "\nHere are the cupcakes in your pack:\n";

      int i = 1;
      while(i <= howMany){
        System.out.print("Select cupcake #" + i + ": ");
        int sel = in.nextInt();
        if((sel >= 1) && (sel <= 4)) {
            finalPrint += "   - " + flavors[sel - 1] + "\n";
            salesTotal += prices[sel - 1];
            i += 1;
        } else {
            System.out.println("Invalid selection, please try again.");
        }
      }

      System.out.println(finalPrint);

      System.out.printf("Sales Total:      $%.2f%n", salesTotal);
      System.out.printf("8%% Sales Tax:     $%.2f%n", (salesTotal * .08));
      System.out.printf("Total:            $%.2f%n", (salesTotal * 1.08));

      System.out.println("\nThank you for ordering from Patty's Cakes!");

      in.close();
      
   }

   static void test(){
      System.out.println("Worked!");
   }
   
   
}