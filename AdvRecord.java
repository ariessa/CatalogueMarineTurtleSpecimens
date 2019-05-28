// AdvRecord.java
//
// By: Nurul Ariessa Binti Norramli 

import java.util.Scanner;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

interface AdvancedRecord {
  public void searchRecord();
}

interface ReportFilter {
  public void reportFilter();
  public void reportFilteredByLocation();
  public void reportFilteredByDate();
  public void reportFilteredByBoth();
}

public class AdvRecord implements AdvancedRecord,  ReportFilter {

  // Clears the screen using backspace
  static void clearScreen() {
    for(int clear = 0; clear < 100; clear++) {
      System.out.println("\b") ;
    }
  }

  // Prints the result of search query
  public void searchRecord() {

    Connection c = null;
    Statement stmt = null;
    Scanner scan = new Scanner(System.in);
    String sql = "";
    String inputs = "";
    Boolean notEmpty = false;
    String search = "";
    Boolean isSpecies = false;
    Boolean isWeight = false;
    Boolean isLength = false;
    Boolean isFlippers = false;
    Boolean isDate = false;
    Boolean isTime = false;
    Boolean isLocation = false;
    Boolean combine = false;
    String inSpecies = "";
    String inLocation = "";
    Double inWeightMin = 0.00;
    Double inWeightMax = 0.00;
    Double inLengthMin = 0.00;
    Double inLengthMax = 0.00;
    int inFlipperMin = 0;
    int inFlipperMax = 0;
    String inTimeMin = "";
    String inTimeMax = "";
    String inDateMin = "";
    String inDateMax = "";

    try {
       Class.forName("org.sqlite.JDBC");
       c = DriverManager.getConnection("jdbc:sqlite:sampling.db");
       c.setAutoCommit(false);
       // System.out.println("\n\t\tOpened database successfully");
       stmt = c.createStatement();

       while (notEmpty == false) {
         clearScreen();
         System.out.println("\t\tNOTE: Perform search by separating the queries using comma [,]");
         System.out.println("\t\t      The queries can be typed in any order");
         System.out.println("\t\t      > between 12kgs-15kgs, Leatherback, between 2019-2-20 and 2019-3-20, between 4cm-5cm");
         System.out.println("\t\t      > between 10:00hrs-20:00hrs, 1 flipper - 2 flippers, Caught in Batu Kawa");
         System.out.print("\n\n\t\tSearch: ");

         if (scan.hasNextLine()) {
           inputs = scan.nextLine();
           notEmpty = true;
         }

         else {
           System.out.println("Incorrect input entered!");
         }
       }

       // Split input by comma [,]
       // Remove whitespace and comma
       String inputList[] = inputs.split("\\s*,\\s*");

       for(int i = 0; i < inputList.length; i++){

        if (inputList[i].matches("(?i:.*kg.*)") || inputList[i].matches("(?i:.*kgs.*)")) {
          // Split input by dash [-]
          String inWeightList[] = inputList[i].split("-");
          // Replace all letters with empty spaces
          // Convert String to Double
          inWeightMin = Double.parseDouble(inWeightList[0].replaceAll("[^\\d.]", ""));
          inWeightMax = Double.parseDouble(inWeightList[1].replaceAll("[^\\d.]", ""));
          isWeight = true;
        }

        else if (inputList[i].matches("(?i:.*cm.*)")) {
          // Split input by dash [-]
          String inLengthList[] = inputList[i].split("-");
          // Convert String to Double
          inLengthMin = Double.parseDouble(inLengthList[0].replaceAll("[^\\d.]", ""));
          System.out.println(inLengthMin);
          inLengthMax = Double.parseDouble(inLengthList[1].replaceAll("[^\\d.]", ""));
          System.out.println(inLengthMax);
          isLength = true;
        }

        else if (inputList[i].matches("(?i:.*hrs.*)") || inputList[i].matches("(?i:.*hr.*)")) {
          // Split input by dash [-]
          String inTimeList[] = inputList[i].split("-");
          inTimeMin = inTimeList[0].replaceAll("[^\\d.]", "");
          inTimeMax = inTimeList[1].replaceAll("[^\\d.]", "");
          isTime = true;
        }

        else if (inputList[i].matches("(?i:.*and.*)")) {
          // Split input by 'and' keyword
          String inDateList[] = inputList[i].split("and");
          inDateMin = inDateList[0].replaceAll("[^\\d-]", "");
          inDateMax = inDateList[1].replaceAll("[^\\d-]", "");
          isDate = true;
        }

        else if (inputList[i].matches("(?i:.*flipper.*)") || inputList[i].matches("(?i:.*flippers.*)")) {
          // Split input by dash [-]
          String inFlipperList[] = inputList[i].split("-");
          // Convert String to Integer
          inFlipperMin = Integer.parseInt(inFlipperList[0].replaceAll("[^\\d.]", ""));
          inFlipperMax = Integer.parseInt(inFlipperList[1].replaceAll("[^\\d.]", ""));
          isFlippers = true;
        }

        else if (inputList[i].matches("(?i:.*caught.*)")) {
          // Replace all 'caught in' with empty spaces
          inLocation = inputList[i].replaceAll("(?i)caught in ", "");
          isLocation = true;
        }

        else {
          // Replace all whitespace with empty spaces
           inSpecies = inputList[i];
           isSpecies = true;
        }
       }

      search = search + "SELECT *, ROWID FROM SAMPLING WHERE";

      if (isSpecies == true) {
        search = search + " (SPECIES = '" + inSpecies + "')";
        combine = true;
      }

      if (isWeight == true) {
        if (combine == true) {
          search = search.concat(" AND (WEIGHT BETWEEN " + inWeightMin + " AND " + inWeightMax + ")");
        }
        else {
          search = search.concat(" (WEIGHT BETWEEN " + inWeightMin + " AND " + inWeightMax + ")");
          combine = true;
        }
      }

      if (isLength == true) {
        if (combine == true) {
          search = search.concat(" AND (LENGTH BETWEEN " + inLengthMin + " AND " + inLengthMax + ")");
        }
        else {
          search = search.concat(" (LENGTH BETWEEN " + inLengthMin + " AND " + inLengthMax + ")");
          combine = true;
        }
      }

      if (isLocation == true) {
        if (combine == true) {
          search = search.concat(" AND (LOCATION = '" + inLocation + "')");
        }
        else {
          search = search.concat(" (LOCATION = '" + inLocation + "')");
          combine = true;
        }
      }

      if (isFlippers == true) {
        if (combine == true) {
          search = search.concat(" AND (FLIPPERS BETWEEN " + inFlipperMin + " AND " + inFlipperMax + ")");
        }
        else {
          search = search.concat(" (FLIPPERS BETWEEN " + inFlipperMin + " AND " + inFlipperMax + ")");
          combine = true;
        }
      }

      if (isDate == true) {
        if (combine == true) {
          search = search.concat(" AND (DATE BETWEEN '" + inDateMin + "' AND '" + inDateMax + "')");
        }
        else {
          search = search.concat(" (DATE BETWEEN '" + inDateMin + "' AND '" + inDateMax + "')");
          combine = true;
        }
      }

      if (isTime == true) {
        if (combine == true) {
          search = search.concat(" AND (TIME BETWEEN " + inTimeMin + " AND " + inTimeMax + ")");
        }
        else {
          search = search.concat(" (TIME BETWEEN " + inTimeMin + " AND " + inTimeMax + ")");
          combine = true;
        }
      }

      search = search.concat(";");
      // System.out.println(search);

      ResultSet rs = stmt.executeQuery(search);
      clearScreen();

      if (!rs.isBeforeFirst() ) {
        System.out.println("\n\tYou searched for the following query/queries: " + inputs);
        System.out.println("\n\tNo result is found");
        return;
      }

      System.out.println("\n\tYou searched for the following query/queries: " + inputs);

       System.out.print("\n\n\n\t");

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
        rs.close();
        stmt.close();
        c.close();
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
        // System.out.println("\n\n\n\tOperation done successfully");
  }

  // Prompts user to enter report filter
  public void reportFilter() {

    int choice = 0;
    Record rec = new Record();

    while (choice < 1 || choice > 3) {
      clearScreen();
      rec.viewRecord();
      Scanner scan = new Scanner(System.in);
      System.out.println("\n\n\tTypes of Report Filter");
      System.out.println("\t----------------------");
      System.out.println("\n\n\t1. By Location");
      System.out.println("\t2. By Date");
      System.out.println("\t3. By Both");
      System.out.print("\n\tEnter Your Choice [1-3]: ");

      if (scan.hasNextInt()) {
        choice = scan.nextInt();

        switch (choice) {
          case 1:
            reportFilteredByLocation();
            break;

          case 2:
            reportFilteredByDate();
            break;

          case 3:
            reportFilteredByBoth();
            break;

          default:
            System.out.println("\tThe input entered is out of range!");
        }
      }

      else {
        System.out.println("\tIncorrect input entered!");
      }

      scan.nextLine();
    }
  }

  // Prints the result of report filtered by location
  public void reportFilteredByLocation() {

    Boolean notEmpty = false;
    String inputs = "";
    Connection c = null;
    Statement stmt = null;
    Scanner scan = new Scanner(System.in);
    String sql = "";

    while (notEmpty == false) {
      clearScreen();
      System.out.println("\n\n\tFilter Report By Location");
      System.out.println("\t---------------------------------");
      System.out.print("\n\tEnter Location: ");

      if (scan.hasNextLine()) {
        inputs = scan.nextLine();
        notEmpty = true;
      }

      else {
        System.out.println("\tIncorrect input entered!");
      }
    }

    clearScreen();

    try {
       Class.forName("org.sqlite.JDBC");
       c = DriverManager.getConnection("jdbc:sqlite:sampling.db");
       c.setAutoCommit(false);
       // System.out.println("\n\t\tOpened database successfully");
       stmt = c.createStatement();
       ResultSet rs = stmt.executeQuery( "SELECT *, ROWID  FROM SAMPLING WHERE LOCATION = '" + inputs + "';" );
       DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy\tHH:mm:ss");
       LocalDateTime now = LocalDateTime.now();

       if (!rs.isBeforeFirst() ) {
         System.out.println("\n\tYou filtered the report using the following query/queries: " + inputs);
         System.out.println("\n\tNo result is found");
         return;
       }

       System.out.println("\n\n\n\tSummary Report as of " + dtf.format(now));
       System.out.println("\n\tFiltered by Location: " + inputs);
       System.out.print("\n\n\n\t");

       for (int i = 0; i < 150; i++) {
         System.out.print("-");
       }

       System.out.print("\n\t");

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
         int rowID = rs.getInt("ROWID");
         String species = rs.getString("species");
         Double weight = rs.getDouble("weight");
         Double length = rs.getDouble("length");
         int workingFlippers = rs.getInt("flippers");
         int day = rs.getInt("day");
         int month = rs.getInt("month");
         int year = rs.getInt("year");
         int hour = rs.getInt("hour");
         int minute = rs.getInt("minute");
         String location = rs.getString("location");

         // Display records to screen
         System.out.format("\n\t%-6s", rowID);
         System.out.format("    %-13s", species);
         System.out.format("    %-8s", weight);
         System.out.format("    %-8s", length);
         System.out.format("    %-8s", workingFlippers);
         String date = day + "/" + month + "/" + year;
         System.out.format("    %-14s", date);
         String time = hour + ":" + minute + " hrs";
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

  // Prints the result of report filtered by date
  public void reportFilteredByDate() {
    Boolean notEmpty = false;
    String inputs = "";
    String search = "";
    Connection c = null;
    Statement stmt = null;
    Scanner scan = new Scanner(System.in);
    String sql = "";
    String inDateMin = "";
    String inDateMax = "";

    while (notEmpty == false) {
      clearScreen();
      System.out.println("\n\n\tFilter Report By Date");
      System.out.println("\t---------------------------------");
      System.out.print("\n\tEnter Date [YYYY-MM-DD]: ");

      if (scan.hasNextLine()) {
        inputs = scan.nextLine();
        notEmpty = true;
      }

      else {
        System.out.println("\tIncorrect input entered!");
      }
    }

      String inDateList[] = inputs.split("until");
      inDateMin = inDateList[0].replaceAll("[^\\d-]", "");
      inDateMax = inDateList[1].replaceAll("[^\\d-]", "");
    clearScreen();

    try {
       Class.forName("org.sqlite.JDBC");
       c = DriverManager.getConnection("jdbc:sqlite:sampling.db");
       c.setAutoCommit(false);
       // System.out.println("\n\t\tOpened database successfully");
       stmt = c.createStatement();
       search = search + "SELECT *, ROWID FROM SAMPLING WHERE";
       search = search.concat(" (DATE BETWEEN '" + inDateMin + "' AND '" + inDateMax + "');");
       ResultSet rs = stmt.executeQuery(search);
       DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy\tHH:mm:ss");
       LocalDateTime now = LocalDateTime.now();

       if (!rs.isBeforeFirst() ) {
         System.out.println("\n\tYou filtered the report using the following query/queries: " + inputs);
         System.out.println("\n\tNo result is found");
         return;
       }

       System.out.println("\n\n\n\tSummary Report as of " + dtf.format(now));
       System.out.println("\n\tFiltered by Date: " + inputs);
       System.out.print("\n\n\n\t");

       for (int i = 0; i < 150; i++) {
         System.out.print("-");
       }

       System.out.print("\n\t");

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
         int rowID = rs.getInt("ROWID");
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

  // Prints the result of report filtered by both location and date
  public void reportFilteredByBoth() {
    Boolean notEmpty = false;
    String inLocation = "";
    String search = "";
    String inputs = "";
    Connection c = null;
    Statement stmt = null;
    Scanner scan = new Scanner(System.in);
    String sql = "";
    String inDateMin = "";
    String inDateMax = "";

    while (notEmpty == false) {
      clearScreen();
      System.out.println("\n\n\tFilter Report By Location and Date");
      System.out.println("\t---------------------------------");
      System.out.print("\n\tEnter Location: ");

      if (scan.hasNextLine()) {
        inLocation = scan.nextLine();
        notEmpty = true;
      }

      else {
        System.out.println("\tIncorrect input entered!");
      }
    }
    while (notEmpty == true) {
      clearScreen();
      System.out.println("\n\n\tFilter Report By Location and Date");
      System.out.println("\t\t---------------------------------");
      System.out.print("\n\tEnter Date [Example: 2019-02-30 until 2019-03-14]: ");

      if (scan.hasNextLine()) {
        inputs = scan.nextLine();
        notEmpty = false;
      }

      else {
        System.out.println("\tIncorrect input entered!");
      }
    }
    clearScreen();

    // Split input by 'and' keyword
    String inDateList[] = inputs.split("until");
    inDateMin = inDateList[0].replaceAll("[^\\d-]", "");
    inDateMax = inDateList[1].replaceAll("[^\\d-]", "");

    try {
       Class.forName("org.sqlite.JDBC");
       c = DriverManager.getConnection("jdbc:sqlite:sampling.db");
       c.setAutoCommit(false);
       // System.out.println("\n\t\tOpened database successfully");
       stmt = c.createStatement();
       search = search + "SELECT *, ROWID FROM SAMPLING WHERE";
       search = search.concat(" (LOCATION = '" + inLocation + "')");
       search = search.concat(" AND (DATE BETWEEN '" + inDateMin + "' AND '" + inDateMax + "');");
       ResultSet rs = stmt.executeQuery(search);
       DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy\tHH:mm:ss");
       LocalDateTime now = LocalDateTime.now();

       if (!rs.isBeforeFirst() ) {
         System.out.println("\n\tYou filtered the report using the following query/queries: " + inputs);
         System.out.println("\n\tNo result is found");
         return;
       }
       
       System.out.println("\n\n\n\tSummary Report As Of " + dtf.format(now));
       System.out.println("\n\tFiltered by Location: " + inLocation);
       System.out.println("\n\t            Date    : " + inputs);
       System.out.print("\n\n\n\t");

       for (int i = 0; i < 150; i++) {
         System.out.print("-");
       }

       System.out.print("\n\t");

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
         int rowID = rs.getInt("ROWID");
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
}
