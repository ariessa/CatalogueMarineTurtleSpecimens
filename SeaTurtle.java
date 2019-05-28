// SeaTurtle.java
//
// By: Nurul Ariessa Binti Norramli 

import java.util.Scanner;

interface Turtle {
  public String turtleSpecies();
  public double turtleWeight();
  public double turtleLength();
  public int turtleWorkingFlippers();
}

interface Date {
  public String checkDate();
}

interface Time {
  public String checkTime();
}

interface Location {
  public String checkLocation();
}

public class SeaTurtle implements Turtle, Date, Time, Location {

  private String species;
  private double weight;
  private double length;
  private int workingFlippers;
  private String date;
  private String time;
  private String location;

  // Constructor
  public SeaTurtle() {
    species = "";
    weight = 0.00;
    length = 0.00;
    workingFlippers = 0;
    date = "";
    time = "";
    location = "";
  }

  // Setter method for species
  public void setSpecies(String species) {
    this.species = species;
  }

  // Setter method for weight
  public void setWeight(double weight) {
    this.weight = weight;
  }

  // Setter method for length
  public void setLength(double length) {
    this.length = length;
  }

  // Setter method for workingFlippers
  public void setWorkingFlippers(int workingFlippers) {
    this.workingFlippers = workingFlippers;
  }

  // Setter method for date
  public void setDate(String date) {
    this.date = date;
  }
  
  // Setter method for time
  public void setTime(String time) {
    this.time = time;
  }

  // Setter method for location
  public void setLocation(String location) {
    this.location = location;
  }

  // Getter method for species
  public String getSpecies() {
    return species;
  }

  // Getter method for weight
  public double getWeight() {
    return weight;
  }

  // Getter method for length
  public double getLength() {
    return length;
  }

  // Getter method for workingFlippers
  public int getWorkingFlippers() {
    return workingFlippers;
  }

  // Getter method for date
  public String getDate() {
    return date;
  }

  // Getter method for time
  public String getTime() {
    return time;
  }

  // Getter method for location
  public String getLocation() {
    return location;
  }

  // Prompts user to enter the species of turtle
  public String turtleSpecies() {

    int choice = 0;
    String species = "";

    while (choice < 1 || choice > 7) {

      Scanner scan = new Scanner(System.in);

      System.out.println("\n\t\tTypes of Species");
      System.out.println("\t\t----------------------");
      System.out.println("\n\n\t\t1. Leatherback");
      System.out.println("\t\t2. Loggerhead");
      System.out.println("\t\t3. Green");
      System.out.println("\t\t4. Flatback");
      System.out.println("\t\t5. Hawksbill");
      System.out.println("\t\t6. Kemp's Ridley");
      System.out.println("\t\t7. Olive Ridley");
      System.out.print("\n\t\tEnter Your Choice [1|2|3|4|5|6|7]: ");

      if (scan.hasNextInt()) {
        choice = scan.nextInt();

        switch (choice) {
          case 1:
            species = species + "Leatherback";
            break;

          case 2:
            species = species + "Loggerhead";
            break;

          case 3:
            species = species + "Green";
            break;

          case 4:
            species = species + "Flatback";
            break;

          case 5:
            species = species + "Hawksbill";
            break;

          case 6:
            species = species + "Kemp's Ridley";
            break;

          case 7:
            species = species + "Olive Ridley";
            break;

          default:
            System.out.println("\t\tThe input entered is out of range!");
        }
      }

      else {
        System.out.println("\t\tIncorrect input entered!");
      }

      scan.nextLine();
    }
    return species;
  }

  // Prompts user to enter the weight of turtle
  public double turtleWeight() {

    double weight = 0.00;

    while (weight <= 0.00) {

      Scanner scan = new Scanner(System.in);

      System.out.print("\n\t\tEnter weight (in kg): ");

      if (scan.hasNextDouble()) {
        weight = scan.nextDouble();

        if (weight <= 0.00)
          System.out.println("\t\tThe value entered must be higher than 0!");
      }

      else {
        System.out.println("\t\tIncorrect input entered!");
      }

      scan.nextLine();
    }
    return weight;
  }

  // Prompts user to enter the length of turtle
  public double turtleLength() {

    double length = 0.00;

    while (length <= 0) {

      Scanner scan = new Scanner(System.in);

      System.out.print("\n\t\tEnter length (in cm): ");

      if (scan.hasNextDouble()) {
        length = scan.nextDouble();

        if (length < 0)
          System.out.println("\t\tThe value entered must be higher than 0!");
      }

      else {
        System.out.println("\t\tIncorrect input entered!");
      }

      scan.nextLine();
    }

    return length;
  }

  // Prompts user to enter the number of working flippers on turtle
  public int turtleWorkingFlippers() {

    int workingFlippers = 3;

    while (workingFlippers < 0 || workingFlippers > 2) {

      Scanner scan = new Scanner(System.in);

      System.out.print("\n\t\tEnter number of working flippers [0|1|2]: ");

      if (scan.hasNextInt()) {
        workingFlippers = scan.nextInt();

        if (workingFlippers < 0 || workingFlippers > 2)
          System.out.println("\t\tThe value entered is out of range!");
      }

      else {
        System.out.println("\t\tIncorrect input entered!");
      }

      scan.nextLine();
    }
    return workingFlippers;
  }

  // Prompts user to enter sampling date
  public String checkDate() {

     String date = "";
     Boolean notEmpty = false;
     Scanner scan = new Scanner(System.in);

     while (notEmpty == false) {

       System.out.print("\n\t\tEnter sampling date [YYYY-MM-DD]: ");

       if (scan.hasNextLine()) {
         date = scan.nextLine();
         notEmpty = true;
       }

       else {
         System.out.println("\t\tIncorrect input entered!");
       }
     }
     return date;
  }

  // Prompts user to enter sampling time
  public String checkTime() {

    String time = "";
    Boolean notEmpty = false;
    Scanner scan = new Scanner(System.in);

    while (notEmpty == false) {

      System.out.print("\n\t\tEnter sampling time in 24 hour system [HH:MM]: ");

      if (scan.hasNextLine()) {
        time = scan.nextLine();
        notEmpty = true;
      }

      else {
        System.out.println("\t\tIncorrect input entered!");
      }
    }
    return time;
  }

  // Prompts user to enter sampling location
  public String checkLocation() {

    String location = "";
    Scanner scan = new Scanner(System.in);
    Boolean notEmpty = false;

    while (notEmpty == false) {

      System.out.print("\n\t\tEnter sampling location [E.g. Batu Kawa]: ");

      if (scan.hasNextLine()) {
        location = scan.nextLine();
        notEmpty = true;
      }

      else {
        System.out.println("\t\tIncorrect input entered!");
      }
    }
    return location;
  }
}
