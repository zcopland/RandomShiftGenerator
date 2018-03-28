/*
Zach Wise-Copland
3-37-2018
*/

import java.lang.Math.*;
import java.util.Random;

public class RandomShift {
   //Variables
   private int startDay = 0; 
   private int endDay = 0;
   private double hoursPerDay = 0;
   private int numberOfEmployees = 0;
   private double totalHours = 0;
   private double maxHoursPerDay = 0;
   private double maxHoursPerWeek = 40;
   private double minShiftHour = 2;
   private String[] employeeArray;
   private double[][] employeeHoursArray;
   private static String[] daysArray = {"Sunday", "Monday", "Tuesday",
      "Wednesday", "Thursday", "Friday", "Saturday"};
   Random rnd = new Random();
   
   
   //4 arg constructor
   public RandomShift (int startDay, int endDay, double hoursPerDay, 
    double maxHoursPerDay, int numberOfEmployees) {
      this.startDay = startDay;
      this.endDay = endDay;
      this.hoursPerDay = hoursPerDay;
      this.numberOfEmployees = numberOfEmployees;
      this.maxHoursPerDay = maxHoursPerDay;
      totalHours = hoursPerDay * (endDay - startDay);
      employeeArray = new String[numberOfEmployees];
      employeeHoursArray = new double[numberOfEmployees][(endDay - startDay) + 1];
      
      //Set values to employeeArray & employeeHoursArray
      generateHours();
   }
   
   private void generateHours() {
      //Go through the first dimension of the array
      for (int j = 0; j < (endDay - startDay) + 1; j++) {
         //Go through the second dimension of the array
         for (int i = 0; i < numberOfEmployees; i++) {
            //Assign generic names to the employees
            employeeArray[i] = "Employee: " + (i +1);
            //Get a random number
            double rndDouble = maxHoursPerDay * rnd.nextDouble();
            //If the random shift is < minimum shift hour, set it to 0
            if (rndDouble < minShiftHour) {
               rndDouble = 0; 
            }
            //Convert to String with 1 decimal place
            String strDouble = String.format("%.1f", rndDouble);
            //Set the value to the employeesHoursArray
            employeeHoursArray[i][j] = Double.parseDouble(strDouble);
            //Display it for the user (in the log)
            System.out.println("" + employeeArray[i] + " " + dayIntToString(j + startDay) + " -> " + employeeHoursArray[i][j]);
         }
      }
      //Readability
      System.out.println("\n------\n");
   }
   
      
   
   
   //Get the day int and convert to readable String
   private String dayIntToString (int day) {
      String dayString = "Sunday";     
      switch (day) {
         case 1:
            dayString = "Monday";
            break;
         case 2:
            dayString = "Tuesday";
            break;
         case 3:
            dayString = "Wednesday";
            break;
         case 4:
            dayString = "Thursday";
            break;
         case 5:
            dayString = "Friday";
            break;
         case 6:
            dayString = "Saturday";
            break;
      }
      
      return dayString;
   }
   
   
   
   //Getters
   public int getStartDay() {
      return startDay;
   }
   public int getEndDay() {
      return endDay;
   }
   public double getHoursPerDay() {
      return hoursPerDay;
   }
   public double getTotalHours() {
      return totalHours;
   }
   public double getMaxHoursPerDay() {
      return maxHoursPerDay;
   }
   public int getNumberOfEmployees() {
      return numberOfEmployees;
   }
   
   
}