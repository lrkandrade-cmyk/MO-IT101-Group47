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

            static void login(){

                Scanner sc = new Scanner(System.in);

                String username = "admin";
                String password = "1234";

                System.out.print("Username: ");
                String user = sc.nextLine();

                System.out.print("Password: ");
                String pass = sc.nextLine();

                if(user.equals(username) && pass.equals(password)){
                    System.out.println("Login Successful");
                    menu();
                }else{
                    System.out.println("Wrong login");
                }

            }

            static void menu(){

                Scanner sc = new Scanner(System.in);

                System.out.println("\n1 View Employees");
                System.out.println("2 Compute Payroll");
                System.out.println("3 Exit");

                System.out.print("Choice: ");
                int choice = sc.nextInt();

                if(choice == 1){
                    readEmployees();
                }
                else if(choice == 2){
                    computePayroll();

                }
                else if(choice == 3){
                    System.exit(0);
                }

            } static void computePayroll(){

        try{

            BufferedReader br = new BufferedReader(new FileReader("attendance.csv"));

            String line;

            br.readLine(); // skip header
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");

            while((line = br.readLine()) != null){

                line = line.replace("\"","");

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

        }
        catch(IOException e){
            System.out.println("Error reading attendance file.");
        }

    }

        static void readEmployees() {

            try {

                BufferedReader br = new BufferedReader(new FileReader("employee.csv"));

                String line;

                br.readLine(); // skip header

                while ((line = br.readLine()) != null) {

                    line = line.replace("\"", "");

                    String[] data = line.split(",");

                    String id = data[0];
                    String lastname = data[1];
                    String firstname = data[2];

                    System.out.println(id + " - " + firstname + " " + lastname);

                }

                br.close();
            }
            catch (IOException e) {
                System.out.println("Error reading employee file.");
            }




                try {

                    BufferedReader br = new BufferedReader(new FileReader("employee.csv"));

                    String line;

                    br.readLine(); // skip header

                    while ((line = br.readLine()) != null) {

                        line = line.replace("\"", "");

                        String[] data = line.split(",");

                        String id = data[0];
                        String lastname = data[1];
                        String firstname = data[2];

                        System.out.println(id + " - " + firstname + " " + lastname);

                    }

                    br.close();

                } catch (IOException e) {
                    System.out.println("Error reading employee file.");
                }

            }
        }
