public class Shifts extends RandomShift {
   private int employeeNameInt;
   private String employeeName;
   private int dayInt;
   private String dayString;
   private double hours;
   
   public Shifts() {
      this.employeeNameInt = 0;
      this.dayInt = 0;
      this.hours = 0;
   }
   
   public Shifts (int employeeNameInt, int day, double hours) {
      super();
      this.employeeNameInt = employeeNameInt;
      this.employeeName = getEmployeeName(employeeNameInt);
      this.dayInt = day;
      this.dayString = getDay(day);
      this.hours = hours;
   }
   
   public String getEmployeeName() { return employeeName; }
   public String getDayString() { return dayString; }
   public double getHours() {return hours; }


}