// Record.java
//
// By: Nurul Ariessa Binti Norramli 

import java.util.Scanner;
import java.sql.*;

interface BasicRecord {
  public void addRecord();
  public void viewRecord();
  public void editRecord();
  public void deleteRecord();
}

public class Record implements BasicRecord {

  // Clears the screen using backspace
  static void clearScreen() {
    for(int clear = 0; clear < 100; clear++) {
      System.out.println("\b") ;
    }
  }

  // Prompts user to add sampling details
  public void addRecord() {

    SeaTurtle seaTurtle = new SeaTurtle();
    String species = "";
    double weight = 0;
    double length = 0;
    int workingFlippers = 0;
    String date = "";
    String time = "";
    String location = "";
    Connection c = null;
    Statement stmt = null;

    seaTurtle.setSpecies(seaTurtle.turtleSpecies());
    seaTurtle.setWeight(seaTurtle.turtleWeight());
    seaTurtle.setLength(seaTurtle.turtleLength());
    seaTurtle.setWorkingFlippers(seaTurtle.turtleWorkingFlippers());
    seaTurtle.setDate(seaTurtle.checkDate());
    seaTurtle.setTime(seaTurtle.checkTime());
    seaTurtle.setLocation(seaTurtle.checkLocation());

    species = seaTurtle.getSpecies();
    weight = seaTurtle.getWeight();
    length = seaTurtle.getLength();
    workingFlippers = seaTurtle.getWorkingFlippers();
    date = seaTurtle.getDate();
    time = seaTurtle.getTime();
    location = seaTurtle.getLocation();

    try {
       Class.forName("org.sqlite.JDBC");
       c = DriverManager.getConnection("jdbc:sqlite:sampling.db");
       c.setAutoCommit(false);
       // System.out.println("\n\n\t\tOpened database successfully");
       stmt = c.createStatement();
       String sql = "INSERT INTO SAMPLING (SPECIES, WEIGHT, LENGTH, FLIPPERS, DATE, TIME, LOCATION) " +
                    "VALUES ('" + species + "'," + weight + "," + length + "," + workingFlippers + ", '" + date + "', '"  + time + "', '" + location + "');";
       stmt.executeUpdate(sql);
       ResultSet rs = stmt.executeQuery( "SELECT *, ROWID FROM SAMPLING WHERE ROWID = (SELECT MAX(ROWID) FROM SAMPLING);" );

       clearScreen();

       System.out.println("\n\n\n\tYou have added a new record as follows:");
       System.out.print("\n\t");

       for (int i = 0; i < 150; i++) {
         System.out.print("-");
       }

       System.out.format("\n\t%-6s", "ID");
       System.out.format("    %-13s", "Species");
       System.out.format("    %-8s", "Weight");
       System.out.format("    %-8s", "Length");
       System.out.format("    %-8s", "Flippers");
       System.out.format("    %-14s", "Date");
       System.out.format("    %-14s", "Time");
       System.out.println("   Location");
       System.out.print("\n\t");

       for (int i = 0; i < 150; i++) {
         System.out.print("-");
       }

       System.out.print("\n\t");

       while ( rs.next() ) {
         int outRowID = rs.getInt("rowid");
         String outSpecies = rs.getString("species");
         Double outWeight = rs.getDouble("weight");
         Double outLength = rs.getDouble("length");
         int outWorkingFlippers = rs.getInt("flippers");
         String outDate = rs.getString("date");
         String outTime = rs.getString("time");
         String outLocation = rs.getString("location");

         // Display records to screen
         System.out.format("\n\t%-6s", outRowID);
         System.out.format("    %-13s", outSpecies);
         System.out.format("    %-8s", outWeight);
         System.out.format("    %-8s", outLength);
         System.out.format("    %-8s", outWorkingFlippers);
         System.out.format("    %-14s", outDate);
         System.out.format("    %-14s", outTime);
         System.out.println("   " + outLocation);
       }
       stmt.close();
       c.commit();
       c.close();
     } catch ( Exception e ) {
       System.err.println( e.getClass().getName() + ": " + e.getMessage() );
       System.exit(0);
    }
    // System.out.println("\n\n\n\tRecords created successfully");
  }

  // Prints existing record
  public void viewRecord() {

    Connection c = null;
    Statement stmt = null;

    try {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:sampling.db");
      c.setAutoCommit(false);
      // System.out.println("\n\tOpened database successfully");

      stmt = c.createStatement();
      ResultSet rs = stmt.executeQuery( "SELECT *, ROWID  FROM SAMPLING;" );

      System.out.println("\n\n\n\tList of Records in Database");
       System.out.print("\n\t");

      for (int i = 0; i < 150; i++) {
        System.out.print("-");
      }

      System.out.format("\n\t%-6s", "ID");
      System.out.format("    %-13s", "Species");
      System.out.format("    %-8s", "Weight");
      System.out.format("    %-8s", "Length");
      System.out.format("    %-8s", "Flippers");
      System.out.format("    %-14s", "Date");
      System.out.format("    %-14s", "Time");
      System.out.println("   Location");
      System.out.print("\n\t");

      for (int i = 0; i < 150; i++) {
        System.out.print("-");
      }

      System.out.print("\n\t");

      while ( rs.next() ) {
        int rowID = rs.getInt("rowid");
        String species = rs.getString("species");
        Double weight = rs.getDouble("weight");
        Double length = rs.getDouble("length");
        int workingFlippers = rs.getInt("flippers");
        String date = rs.getString("date");
        String time = rs.getString("time");
        String location = rs.getString("location");

        // Display records to screen
        System.out.format("\n\t%-6s", rowID);
        System.out.format("    %-13s", species);
        System.out.format("    %-8s", weight);
        System.out.format("    %-8s", length);
        System.out.format("    %-8s", workingFlippers);
        System.out.format("    %-14s", date);
        System.out.format("    %-14s", time);
        System.out.println("   " + location);
      }
      rs.close();
      stmt.close();
      c.close();
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    // System.out.println("\n\n\n\tOperation done successfully");
  }

  // Edit existing record details
  public void editRecord() {

    Connection c = null;
    Statement stmt = null;
    int choice = 0;
    int id = 0;
    Scanner scan = new Scanner(System.in);
    SeaTurtle seaTurtle = new SeaTurtle();
    String sql = "";
    String species = "";
    double weight = 0.00;
    double length = 0.00;
    int workingFlippers = 0;
    String date = "";
    String time = "";
    String location = "";

    try {
       Class.forName("org.sqlite.JDBC");
       c = DriverManager.getConnection("jdbc:sqlite:sampling.db");
       c.setAutoCommit(false);
       // System.out.println("Opened database successfully");
       stmt = c.createStatement();
       ResultSet getMaxRow = stmt.executeQuery( "SELECT ROWID FROM SAMPLING WHERE ROWID = (SELECT MAX(ROWID) FROM SAMPLING);" );
       int maxRow = getMaxRow.getInt("rowid");

       while (id <= 0 || id > maxRow) {
         clearScreen();
         viewRecord();
         System.out.print("\n\n\tEnter ID: ");

         if (scan.hasNextInt()) {
           id = scan.nextInt();

           if (id <= 0 || id > maxRow)
             System.out.println("\n\tThe value entered is out of range!");
         }

         else {
           System.out.println("\n\tIncorrect input entered!");
         }

         scan.nextLine();
       }

       while (choice <= 0 || choice >= 9) {
         clearScreen();
         System.out.println("\tWhat Do You Want to Edit ?");
         System.out.println("\t--------------------------");
         System.out.println("\n\n\t1. Species");
         System.out.println("\t2. Weight");
         System.out.println("\t3. Length");
         System.out.println("\t4. Working Flippers");
         System.out.println("\t5. Date");
         System.out.println("\t6. Time");
         System.out.println("\t7. Location");
         System.out.println("\t8. All");
         System.out.print("\n\tEnter Your Choice [1-8]: ");

         if (scan.hasNextInt()) {
           choice = scan.nextInt();
           clearScreen();
           switch (choice) {
             case 1:
               seaTurtle.setSpecies(seaTurtle.turtleSpecies());
               species = seaTurtle.getSpecies();
               sql = sql + "UPDATE SAMPLING set SPECIES = '" + species + "' WHERE ROWID = " + id + ";";
               break;

             case 2:
               seaTurtle.setWeight(seaTurtle.turtleWeight());
               weight = seaTurtle.getWeight();
               sql = "UPDATE SAMPLING set WEIGHT = " + weight + " WHERE ROWID = " + id + ";";
               break;

             case 3:
               seaTurtle.setLength(seaTurtle.turtleLength());
               length = seaTurtle.getLength();
               sql = "UPDATE SAMPLING set LENGTH = " + length + " WHERE ROWID = " + id + ";";
               break;

             case 4:
               seaTurtle.setWorkingFlippers(seaTurtle.turtleWorkingFlippers());
               workingFlippers = seaTurtle.getWorkingFlippers();
               sql = "UPDATE SAMPLING set FLIPPERS = " + workingFlippers + " WHERE ROWID = " + id + ";";
               break;

             case 5:
               seaTurtle.setDate(seaTurtle.checkDate());
               date = seaTurtle.getDate();
               sql = "UPDATE SAMPLING set DATE = '" + date + "' WHERE ROWID = " + id + ";";
               break;

             case 6:
               seaTurtle.setTime(seaTurtle.checkTime());
               time = seaTurtle.getTime();
               sql = "UPDATE SAMPLING set TIME = '" + time + "' WHERE ROWID = " + id + ";";
               break;

             case 7:
               seaTurtle.setLocation(seaTurtle.checkLocation());
               location = seaTurtle.getLocation();
               sql = "UPDATE SAMPLING set LOCATION = '" + location + "' WHERE ROWID = " + id + ";";
               break;

             case 8:
               seaTurtle.setSpecies(seaTurtle.turtleSpecies());
               seaTurtle.setWeight(seaTurtle.turtleWeight());
               seaTurtle.setLength(seaTurtle.turtleLength());
               seaTurtle.setWorkingFlippers(seaTurtle.turtleWorkingFlippers());
               seaTurtle.setDate(seaTurtle.checkDate());
               seaTurtle.setTime(seaTurtle.checkTime());
               seaTurtle.setLocation(seaTurtle.checkLocation());
               species = seaTurtle.getSpecies();
               weight = seaTurtle.getWeight();
               length = seaTurtle.getLength();
               workingFlippers = seaTurtle.getWorkingFlippers();
               date = seaTurtle.getDate();
               time = seaTurtle.getTime();
               location = seaTurtle.getLocation();
               sql = "UPDATE SAMPLING set (SPECIES = '" + species + "', WEIGHT = "
                      + weight + ", LENGTH = " + length + ", FLIPPERS = "
                      + workingFlippers + ", DATE = '" + date + "', TIME = '"
                      + time + "', LOCATION = " + location
                      + "') WHERE ROWID = " + id + ";";
               break;

             default:
               System.out.println("\n\tThe input entered is out of range!");
           }
         }

         else {
           System.out.println("\n\tIncorrect input entered!");
         }

         scan.nextLine();
       }

       ResultSet rs = stmt.executeQuery( "SELECT *, ROWID FROM SAMPLING WHERE ROWID = " + id + ";" );
       clearScreen();
       System.out.println("\n\n\n\tYou have edited the following record:");
       System.out.print("\n\tBEFORE");
       System.out.print("\n\t");

       for (int i = 0; i < 150; i++) {
         System.out.print("-");
       }

       System.out.format("\n\t%-6s", "ID");
       System.out.format("    %-13s", "Species");
       System.out.format("    %-8s", "Weight");
       System.out.format("    %-8s", "Length");
       System.out.format("    %-8s", "Flippers");
       System.out.format("    %-14s", "Date");
       System.out.format("    %-14s", "Time");
       System.out.println("   Location");
       System.out.print("\n\t");

       for (int i = 0; i < 150; i++) {
         System.out.print("-");
       }

       System.out.print("\n\t");

       while ( rs.next() ) {
         int outRowID = rs.getInt("ROWID");
         String outSpecies = rs.getString("species");
         Double outWeight = rs.getDouble("weight");
         Double outLength = rs.getDouble("length");
         int outWorkingFlippers = rs.getInt("flippers");
         String outDate = rs.getString("date");
         String outTime = rs.getString("time");
         String outLocation = rs.getString("location");

         // Display records to screen
         System.out.format("\n\t%-6s", outRowID);
         System.out.format("    %-13s", outSpecies);
         System.out.format("    %-8s", outWeight);
         System.out.format("    %-8s", outLength);
         System.out.format("    %-8s", outWorkingFlippers);
         System.out.format("    %-14s", outDate);
         System.out.format("    %-14s", outTime);
         System.out.println("   " + outLocation);
       }
       stmt.executeUpdate(sql);
       rs = stmt.executeQuery( "SELECT *, ROWID FROM SAMPLING WHERE ROWID = " + id + ";" );
       System.out.print("\n\n\tAFTER");
       System.out.print("\n\t");

       for (int i = 0; i < 150; i++) {
         System.out.print("-");
       }

       System.out.format("\n\t%-6s", "ID");
       System.out.format("    %-13s", "Species");
       System.out.format("    %-8s", "Weight");
       System.out.format("    %-8s", "Length");
       System.out.format("    %-8s", "Flippers");
       System.out.format("    %-14s", "Date");
       System.out.format("    %-14s", "Time");
       System.out.println("   Location");
       System.out.print("\n\t");

       for (int i = 0; i < 150; i++) {
         System.out.print("-");
       }

       System.out.print("\n\t");

       while ( rs.next() ) {
         int outRowID = rs.getInt("ROWID");
         String outSpecies = rs.getString("species");
         Double outWeight = rs.getDouble("weight");
         Double outLength = rs.getDouble("length");
         int outWorkingFlippers = rs.getInt("flippers");
         String outDate = rs.getString("date");
         String outTime = rs.getString("time");
         String outLocation = rs.getString("location");

         // Display records to screen
         System.out.format("\n\t%-6s", outRowID);
         System.out.format("    %-13s", outSpecies);
         System.out.format("    %-8s", outWeight);
         System.out.format("    %-8s", outLength);
         System.out.format("    %-8s", outWorkingFlippers);
         System.out.format("    %-14s", outDate);
         System.out.format("    %-14s", outTime);
         System.out.println("   " + outLocation);
       }
       c.commit();
       getMaxRow.close();
       stmt.close();
       c.close();

     } catch ( Exception e ) {
       System.err.println( e.getClass().getName() + ": " + e.getMessage() );
       System.exit(0);
     }
     // System.out.println("\n\n\tOperation done successfully");
     System.out.print("\n\n\n");
  }

  // Deletes existing record
  public void deleteRecord() {
    Connection c = null;
    Statement stmt = null;
    int choice = 0;
    int id = 0;
    Scanner scan = new Scanner(System.in);
    SeaTurtle seaTurtle = new SeaTurtle();

    try {
       Class.forName("org.sqlite.JDBC");
       c = DriverManager.getConnection("jdbc:sqlite:sampling.db");
       c.setAutoCommit(false);
       System.out.println("Opened database successfully");
       stmt = c.createStatement();
       ResultSet getMaxRow = stmt.executeQuery( "SELECT ROWID FROM SAMPLING WHERE ROWID = (SELECT MAX(ROWID) FROM SAMPLING);" );
       int maxRow = getMaxRow.getInt("rowid");

       while (id <= 0 || id > maxRow) {
         clearScreen();
         viewRecord();
         System.out.print("\n\n\tEnter ID: ");

         if (scan.hasNextInt()) {
           id = scan.nextInt();

           if (id <= 0 || id > maxRow)
             System.out.println("\n\tThe value entered is out of range!");
         }

         else {
           System.out.println("\n\tIncorrect input entered!");
         }

         scan.nextLine();
       }

      clearScreen();
      ResultSet rs = stmt.executeQuery( "SELECT *, ROWID FROM SAMPLING WHERE ROWID = " + id + ";" );
      System.out.println("\n\n\n\tYou have deleted the following record:");
      System.out.print("\n\t");

      for (int i = 0; i < 150; i++) {
        System.out.print("-");
      }

      System.out.format("\n\t%-6s", "ID");
      System.out.format("    %-13s", "Species");
      System.out.format("    %-8s", "Weight");
      System.out.format("    %-8s", "Length");
      System.out.format("    %-8s", "Flippers");
      System.out.format("    %-14s", "Date");
      System.out.format("    %-14s", "Time");
      System.out.println("   Location");
      System.out.print("\n\t");

      for (int i = 0; i < 150; i++) {
        System.out.print("-");
      }

      System.out.print("\n\t");

      while ( rs.next() ) {
        int outRowID = rs.getInt("ROWID");
        String outSpecies = rs.getString("species");
        Double outWeight = rs.getDouble("weight");
        Double outLength = rs.getDouble("length");
        int outWorkingFlippers = rs.getInt("flippers");
        String outDate = rs.getString("date");
        String outTime = rs.getString("time");
        String outLocation = rs.getString("location");

        // Display records to screen
        System.out.format("\n\t%-6s", outRowID);
        System.out.format("    %-13s", outSpecies);
        System.out.format("    %-8s", outWeight);
        System.out.format("    %-8s", outLength);
        System.out.format("    %-8s", outWorkingFlippers);
        System.out.format("    %-14s", outDate);
        System.out.format("    %-14s", outTime);
        System.out.println("   " + outLocation);
      }
      String sql = "DELETE from SAMPLING where ROWID = " + id + ";";
      stmt.executeUpdate(sql);
      c.commit();
      getMaxRow.close();
      stmt.close();
      c.close();

    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    // System.out.println("\n\tOperation done successfully");
    viewRecord();
  }
}
