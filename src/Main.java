import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
// Payroll System Project - Group 47
public class Main {

    public static void main(String[] args) {


        login();   // start the program

    }

    static void login() {

        Scanner sc = new Scanner(System.in);


        System.out.print("Username: ");
        String username = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();
/*
        if(username.equals("payroll_staff") && password.equals("12345")) {
            System.out.println("Login Successful");
            payrollMenu();
                }
*/

        if (username.equals("employee") && password.equals("12345")) {
            employeeMenu();
        } else {
            System.out.println("Incorrect username and/or password");
            System.exit(0);
        }
    }

    /*  // Shows menu for payroll staff
    static void payrollMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n1 Process Payroll");
        System.out.println("2 Exit Program");
        System.out.println("Choice");
        int choice = sc.nextInt();
        if (choice == 1) {
            payrollMenu();
        } else if (choice == 2) {
            System.exit(0);
        }
    }
    /*static void processPayroll(){
            Scanner sc = new Scanner(System.in);
            System.out.println("\n1 One Employee");
            System.out.println("2 All Employees");
            System.out.println("3 Exit Program");
            System.out.println("Choice: ");
            int choice = sc.nextInt();
            if (choice == 1) {
                payrollOneEmployee();
            }
            if (choice == 2) {
                payrollALlEmployees();
            else if (choice == 3) {
                System.exit(0);
                }
            }
        }
        */

    static void employeeMenu() {

        Scanner sc = new Scanner(System.in);

        System.out.println("\n1 Enter Employee Number ");
        System.out.println("2 Exit Program");
        int choice = sc.nextInt();
        if (choice == 1) {
            enterEmployeeNumber();
        } else if (choice == 2) {
            System.exit(0);
        }
    }
    // asks for employee number
    static void enterEmployeeNumber() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Employee Number : ");
        String employeeNumber = sc.nextLine();

        searchEmployee(employeeNumber);
    }
    //reads file,searches and displays employee info
    static void searchEmployee(String empNum) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("employee.csv"));
            String line;
            boolean found = false;
            br.readLine();
            while ((line = br.readLine()) != null) {
                line = line.replaceAll("\"", "");

                String[] data = line.split(",");

                String id = data[0];
                String lastname = data[1];
                String firstname = data[2];
                String birthdate = data[3];
                if (id.equals(empNum)) {
                    System.out.println("\nEmployee Number : " + id);
                    System.out.println("Employee Name : " + firstname + " " + lastname);
                    System.out.println("Employee Birth Date : " + birthdate);
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Employee Number does not exist");
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
    }
    /*
   static void computePayroll() {

        try {

            BufferedReader br = new BufferedReader(new FileReader("attendance.csv"));

            String line;

            br.readLine(); // skip header
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");

            while ((line = br.readLine()) != null) {

                line = line.replace("\"", "");

                String[] data = line.split(",");

                String id = data[0];
                String lastname = data[1];
                String firstname = data[2];
                String login = data[4];
                String logout = data[5];

                LocalTime loginTime = LocalTime.parse(login, formatter);
                LocalTime logoutTime = LocalTime.parse(logout, formatter);

                Duration duration = Duration.between(loginTime, logoutTime);

                double hoursWorked = duration.toMinutes() / 60.0;

                double hourlyRate = 150;
                double salary = hoursWorked * hourlyRate;

                System.out.println(id + " " + firstname + " " + lastname);
                System.out.printf("Hours Worked: %.2f\n", hoursWorked);
                System.out.printf("Gross Pay: ₱%.2f\n", salary);
                System.out.println();

            }

            br.close();

        } catch (IOException e) {
            System.out.println("Error reading attendance file.");
        }

    }
*/

}
