// DriverProgram.java
//
// By: Nurul Ariessa Binti Norramli 

import java.util.Scanner;

public class DriverProgram {

  // Clears the screen using backspace
  static void clearScreen() {
    for (int clear = 0; clear < 100; clear++) {
      System.out.println("\b") ;
    }
  }

  // Asks user to go back to main menu or end the program
  static int menuOrEnd() {
    Scanner scan = new Scanner(System.in);
    int choice = 0;
    while (choice < 1 || choice > 2) {
      System.out.println("\n\n\n\n\tWould You Like to Go Back to Main Menu ?\n");
      System.out.println("\t 1. Yes");
      System.out.println("\t 2. No");
      System.out.print("\n\tEnter Your Choice : ");

      if (scan.hasNextInt())
        choice = scan.nextInt();

      else
        System.out.println("Incorrect input entered!");

      scan.nextLine();
    }
    return choice;
  }

  public static void main(String[] args) {

    int choice = 0;
    int exit = 0;;
    int workingFlippers = 0;
    int day = 0;
    int month = 0;
    int year = 0;
    String species = "";
    String location = "";
    double weight = 0.00;
    double length = 0.00;
    Scanner scan = new Scanner(System.in);
    SeaTurtle seaTurtle = new SeaTurtle();
    Record record = new Record();
    AdvRecord adv = new AdvRecord();

    while (choice < 1 || choice > 6 || exit == 1) {

      clearScreen();

      // Display menu
      System.out.println("\t\t What Would You Like To Do ?");
      System.out.println("\t\t----------------------");
      System.out.println("\n\n\t\t1. Enter New Record");
      System.out.println("\t\t2. View Record");
      System.out.println("\t\t3. Edit Record");
	    System.out.println("\t\t4. Delete Record");
      System.out.println("\t\t5. Search Record");
      System.out.println("\t\t6. Generate Summary Report");
      System.out.print("\n\t\tEnter Your Choice [1|2|3|4|5|6]: ");

      if (scan.hasNextInt()) {
        choice = scan.nextInt() ;
      }

      else {
        System.out.println("Incorrect input entered!");
      }

      scan.nextLine();

      switch (choice) {

        // Add record
        case 1:
          clearScreen();
          record.addRecord();
          exit = menuOrEnd();
          break;

        // View record
        case 2:
          clearScreen();
          record.viewRecord();
          exit = menuOrEnd();
          break;

        // Edit record
        case 3:
          clearScreen();
          record.editRecord();
          exit = menuOrEnd();
          break;

        // Delete record
        case 4:
          clearScreen();
          record.deleteRecord();
          exit = menuOrEnd();
          break;

        // Search record
        case 5:
          clearScreen();
          adv.searchRecord();
          exit = menuOrEnd();
          break;

        // Generate summary report
        case 6:
          clearScreen();
          adv.reportFilter();
          exit = menuOrEnd();
          break;
      }
    }
  }
}
