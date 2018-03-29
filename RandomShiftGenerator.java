/*
Zach Wise-Copland
3-37-2018
*/

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.lang.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.*;
import javafx.collections.*;

public class RandomShiftGenerator extends Application {
   private String[] daysArray = {"Sunday", "Monday", "Tuesday",
      "Wednesday", "Thursday", "Friday", "Saturday"};
   Alert alertError = new Alert(AlertType.WARNING);
   RandomShift rs = new RandomShift();
   TableView<Shifts> tbShifts;
   
   
   public void start(Stage primaryStage) {
      //Initialize new GridPane
      GridPane gp = new GridPane();
      gp.setHgap(4);
      gp.setVgap(4);
      
      //Initialize new Pane
      Pane pane = new Pane();
      
      //Initialize new vbox
      VBox vbox = new VBox();
      
      //Initialize TableView
      //Name column
      TableColumn<Shifts, String> nameColumn = new TableColumn<>("Name");
      nameColumn.setMinWidth(100);
      nameColumn.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
      //Day column
      TableColumn<Shifts, String> dayColumn = new TableColumn<>("Day");
      dayColumn.setMinWidth(100);
      dayColumn.setCellValueFactory(new PropertyValueFactory<>("dayString"));
      //Hours column
      TableColumn<Shifts, Double> hoursColumn = new TableColumn<>("Hours");
      hoursColumn.setMinWidth(20);
      hoursColumn.setCellValueFactory(new PropertyValueFactory<>("hours"));
      
      tbShifts = new TableView<>();
      
      
      //Controls
      ChoiceBox cb1 = new ChoiceBox(FXCollections.observableArrayList(daysArray));
      cb1.setTooltip(new Tooltip("Select starting day"));
      ChoiceBox cb2 = new ChoiceBox(FXCollections.observableArrayList(daysArray));
      cb2.setTooltip(new Tooltip("Select ending day"));
      Button btnGenerate = new Button("Generate");
      Label lbDays = new Label("Days:");
      Label lbThru = new Label("thru");
      alertError.setTitle("Error");
      TextField tfMaxHours = new TextField();
      
      //Hours Slider
      Slider sdHours = new Slider();
      sdHours.setMin(4);
      sdHours.setMax(24);
      sdHours.setValue(14);
      sdHours.setShowTickMarks(true);
      sdHours.setShowTickLabels(true);
      sdHours.setMajorTickUnit(4);
      sdHours.setBlockIncrement(4);
      Label lbSDHours = new Label("16");
      
      //Employees Slider
      Slider sdEmployees = new Slider();
      sdEmployees.setMin(2);
      sdEmployees.setMax(10);
      sdEmployees.setValue(6);
      sdEmployees.setShowTickMarks(true);
      sdEmployees.setShowTickLabels(true);
      sdEmployees.setMajorTickUnit(1);
      sdEmployees.setBlockIncrement(1);
      Label lbSDEmployees = new Label("5");
      
      //Add controls to gp
      gp.add(lbDays, 0, 0);
      gp.add(cb1, 1, 0);
      gp.add(lbThru, 1, 1);
      gp.add(cb2, 1, 2);
      gp.add(new Label("Hours per day:"), 0, 3);
      gp.add(sdHours, 1, 3);
      gp.add(lbSDHours, 2, 3);
      gp.add(new Label("Max hours per day:"), 0, 4);
      gp.add(tfMaxHours, 1, 4);
      gp.add(new Label("Number of employees:"), 0, 5);
      gp.add(sdEmployees, 1, 5);
      gp.add(lbSDEmployees, 2, 5);
      gp.add(btnGenerate, 1, 6);
      
      //Slider listeners
      sdHours.valueProperty().addListener((v, oldValue, newValue) -> {
         lbSDHours.setText(String.format("%.1f", newValue.doubleValue()));
      });
      sdEmployees.valueProperty().addListener((v, oldValue, newValue) -> {
         lbSDEmployees.setText(String.format("%.0f", newValue.doubleValue()));
      });
      
      //Generate button actions
      btnGenerate.setOnAction(e -> {
         //Create number of errors
         int errors = 0;
         //Create error String for alert
         String error = "";
         //Get the numerical value of day
         int startDay = dayStringToInt(cb1.getValue().toString());
         int endDay = dayStringToInt(cb2.getValue().toString());
         //Get the hours, convert to 1 decimal string, then back to double
         String hoursString = String.format("%.1f", sdHours.getValue());
         double hoursDouble = Double.parseDouble(hoursString);
         //Get the employees, convert to 0 decimal string, then to int
         String employeesString = String.format("%.0f", sdEmployees.getValue());
         int numberOfEmployees = Integer.parseInt(employeesString);
         
         
         
         //Check if the start day cb is empty
         if (cb1.getSelectionModel().isEmpty()) {
            error += "\n-Start day";
            errors++;
         } 
         //Check if the end day cb is empty
         if (cb2.getSelectionModel().isEmpty()) {
            error += "\n-End day";
            errors++;
         }
         //Make sure start/end days are in correct order
         if (startDay >= endDay) {
            error += "\n-Start & end day must be in order";
            errors++;
         }
         //Check if the max hours tf is empty
         if (tfMaxHours.getText().trim().isEmpty() || tfMaxHours.getText() == null) {
            error += "\n-Max hours per day";
            errors++;
         } else if (Double.parseDouble(tfMaxHours.getText()) < 3 ||
            Double.parseDouble(tfMaxHours.getText()) > hoursDouble) {
            error += "\n-Max hours must be between 3 & " + hoursDouble;
            errors++;
         }
         //If there are no errors, continue
         if (errors < 1) {
            rs = new RandomShift(startDay, endDay, 
               hoursDouble, Double.parseDouble(tfMaxHours.getText()), 
               numberOfEmployees);
         } else {
            //There are errors, show the alert for fields
            alertError.setHeaderText("Please check the following input(s):");
            alertError.setContentText(error);
            alertError.showAndWait();
         }
         
         for (int i = 0; i < (rs.getEndDay() - rs.getStartDay()) + 1; i++) {
         
            for (int j = 0; j < rs.getNumberOfEmployees(); j++) {
               System.out.println("" + rs.getEmployeeName(j) + " on " + rs.getDay(i) + " -> " + rs.getEmployeeHours(i, j));
               
            }
            
         }
         
         //System.out.println("\n\n");
         
         //tbShifts.getItems().clear();
         tbShifts.setItems(getShifts());
         tbShifts.getColumns().addAll(nameColumn, dayColumn, hoursColumn);
         
         vbox.getChildren().clear();
         vbox.getChildren().add(tbShifts);
         
      });
      
      vbox.setPadding(new Insets(250, 0, 0, 0));
      pane.getChildren().addAll(vbox, gp);
      
      //Scene and stage initialization
      Scene scene = new Scene(pane, 350, 550); 
      primaryStage.setTitle("Random Shift Generator");
      primaryStage.setScene(scene);
      primaryStage.show();
   }
   
   //COnvert the day string to an int
   private int dayStringToInt (String day) {
      int dayInt = 0;     
      switch (day) {
         case "Monday":
            dayInt = 1;
            break;
         case "Tuesday":
            dayInt = 2;
            break;
         case "Wednesday":
            dayInt = 3;
            break;
         case "Thursday":
            dayInt = 4;
            break;
         case "Friday":
            dayInt = 5;
            break;
         case "Saturday":
            dayInt = 6;
            break;
      }
      
      return dayInt;
   }
   
   public ObservableList<Shifts> getShifts() {
      ObservableList<Shifts> shifts = FXCollections.observableArrayList();
      
      for (int i = 0; i < (rs.getEndDay() - rs.getStartDay()) + 1; i++) {
         
         for (int j = 0; j < rs.getNumberOfEmployees(); j++) {
            //System.out.println("" + rs.getEmployeeNumber(j) + " on " + rs.getDay(i) + " -> " + rs.getEmployeeHours(i, j));
            shifts.add(new Shifts(j, i, rs.getEmployeeHours(i, j)));
         }
         
      }
      
      return shifts;
   }
   
}