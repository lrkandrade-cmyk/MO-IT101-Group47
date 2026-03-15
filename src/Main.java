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
    //asks for login  credentials
    static void login() {

        Scanner sc = new Scanner(System.in);


        System.out.print("Username: ");
        String username = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        if (username.equals("payroll_staff") && password.equals("12345")) {
            payrollMenu();
        } else if (username.equals("employee") && password.equals("12345")) {
            employeeMenu();
        } else {
            System.out.println("Incorrect username and/or password");
            System.exit(0);
        }
    }
    // Shows menu for payroll staff
    static void payrollMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n1 Process Payroll");
        System.out.println("2 Exit Program");
        System.out.println("Choice");
        int choice = sc.nextInt();
        sc.nextLine();
        if (choice == 1) {
            processPayroll();
        } else if (choice == 2) {
            System.exit(0);
        }
    }
    static void processPayroll() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n1 One Employee");
        System.out.println("2 All Employees");
        System.out.println("3 Exit Program");
        System.out.println("Choice: ");
        int choice = sc.nextInt();
        sc.nextLine();
        if (choice == 1) {
            payrollOneEmployee();
        } else if (choice == 2) {
            payrollAllEmployees();
        } else if (choice == 3) {
            System.exit(0);
        }
    }
    // Processes payroll for one employee
    static void payrollOneEmployee() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Employee Number: ");
        String empNum = sc.nextLine();
        try {
            BufferedReader br = new BufferedReader(new FileReader("employee.csv"));
            String line;
            boolean found = false;

            br.readLine();

            while ((line = br.readLine()) != null) {
    // only splits commas if not inside quotes
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                String id = data[0];
                String lastname = data[1];
                String firstname = data[2];
                String birthdate = data[3];
                double rate = Double.parseDouble(data[18]);

                if (id.equals(empNum)) {
                    System.out.println("\nEmployee Number:" + id);
                    System.out.println("First Name: " + firstname);
                    System.out.println("Last Name: " + lastname);
                    System.out.println("Birthday: " + birthdate);

                    computePayroll(id, rate);
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Employee number does not exist");
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error reading employee file");
        }
    }
    // Processes payroll for all employees
    static void payrollAllEmployees() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("employee.csv"));
            String line;

            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                String id = data[0];
                String lastname = data[1];
                String firstname = data[2];
                String birthdate = data[3];
                double rate = Double.parseDouble(data[18]);
                System.out.println("\n========================");
                System.out.println("Employee Number:" + id);
                System.out.println("Name: " + firstname + " " +lastname);
                System.out.println("Birthday: " + birthdate);

                computePayroll(id, rate);
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error reading employee file");
        }
    }
    // Shows menu for employees
    static void employeeMenu() {

        Scanner sc = new Scanner(System.in);

        System.out.println("\n1 Enter Employee Number ");
        System.out.println("2 Exit Program");
        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 1) {
            enterEmployeeNumber();
        } else if (choice == 2) {
            System.exit(0);
        }
    }
    // asks for employee number
    static void enterEmployeeNumber() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Employee Number: ");
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
    //Calculates payroll
    static void computePayroll(String empId, double rate) {

        try {

            BufferedReader br = new BufferedReader(new FileReader("attendance.csv"));
            String line;

            double[] cutoff1 = new double[13];
            double[] cutoff2 = new double[13];

            br.readLine(); // skip header

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");

            while ((line = br.readLine()) != null) {

                line = line.replace("\"", "");
                String[] data = line.split(",");

                String id = data[0];
                String date = data[3];
                String login = data[4];
                String logout = data[5];

                if (id.equals(empId)) {

                    String[] parts = date.split("/");
                    int month = Integer.parseInt(parts[0]);
                    int day = Integer.parseInt(parts[1]);

                    if (month >= 6 && month <= 12) {

                        LocalTime loginTime = LocalTime.parse(login, formatter);
                        LocalTime logoutTime = LocalTime.parse(logout, formatter);

                        LocalTime start = LocalTime.of(8,0);
                        LocalTime grace = LocalTime.of(8,10);

                        if(loginTime.isBefore(start))
                            loginTime = start;

                        else if(loginTime.isAfter(start) && loginTime.isBefore(grace))
                            loginTime = start;

                        LocalTime end = LocalTime.of(17, 0);

                        if (loginTime.isBefore(start))
                            loginTime = start;

                        if (logoutTime.isAfter(end))
                            logoutTime = end;

                        if (logoutTime.isBefore(loginTime))
                            continue;

                        Duration duration = Duration.between(loginTime, logoutTime);
                        double hoursWorked = duration.toMinutes() / 60.0;

                        if (day <= 15) {
                            cutoff1[month] += hoursWorked;
                        } else {
                            cutoff2[month] += hoursWorked;
                        }
                    }
                }
            }
            br.close();
            String[] months = {"", "Jan", "Feb", "Mar", "Apr", "May",
                    "June", "July", "Aug", "Sep", "Oct", "Nov", "Dec"};

            for (int m = 6; m <= 12; m++) {
                double gross1 = cutoff1[m] * rate;
                double gross2 = cutoff2[m] * rate;

                double monthlyTotal = gross1 + gross2;

                double sss = computeSSS(monthlyTotal);
                double philhealth = computePhilHealth(monthlyTotal);
                double pagibig = computePagibig(monthlyTotal);

                double taxableIncome = monthlyTotal - (sss + philhealth + pagibig);
                double tax = computeTax(taxableIncome);
                double deductions = sss + philhealth + pagibig;

                double net2 = gross2 -deductions;
                double totalNetSalary = gross1 + net2;

                System.out.println("\nMonth: " + months[m]);

                System.out.println("Cutoff Date: " + months[m] + " 1-15");
                System.out.println("Total Hours Worked: " + cutoff1[m]);
                System.out.println("Gross Salary: " + gross1);
                System.out.println("Net Salary: " + gross1);

                System.out.println("\nCutoff Date: " + months[m] + " 16 - 30");
                System.out.println("Total Hours Worked: " + cutoff2[m]);
                System.out.println("Gross Salary: " + gross2);

                System.out.println("SSS: " + sss);
                System.out.println("PhilHealth: " + philhealth);
                System.out.println("Pag-Ibig: " + pagibig);
                System.out.println("Tax: " + tax);
                System.out.println("Total Deductions: " + deductions);
                System.out.println("Net Salary: " + totalNetSalary) ;
            }
        } catch (IOException e) {
            System.out.println("Error reading attendance file.");
        }
    }
    static double computeSSS(double salary) {
        if (salary <= 3250) {
            return 135;
        } else if (salary <= 3750) {
            return 157.5;
        } else if (salary <= 4250) {
            return 180;
        } else if (salary <= 4750) {
            return 202.5;
        } else if (salary <= 5250) {
            return 225;
        } else if (salary <= 5750) {
            return 247.5;
        } else if (salary <= 6250) {
            return 270;
        } else if (salary <= 6750) {
            return 292.5;
        } else if (salary <= 7250) {
            return 315;
        } else if (salary <= 7750) {
            return 337.5;
        } else if (salary <= 8250) {
            return 360;
        } else if (salary <= 8750) {
            return 382.5;
        } else if (salary <= 9250) {
            return 405;
        } else if (salary <= 9750) {
            return 427.5;
        } else if (salary <= 10250) {
            return 450;
        } else if (salary <= 10750) {
            return 472.5;
        } else if (salary <= 11250) {
            return 495;
        } else if (salary <= 11750) {
            return 517.5;
        } else if (salary <= 12250) {
            return 540;
        } else if (salary <= 12750) {
            return 562.5;
        } else if (salary <= 13250) {
            return 585;
        } else if (salary <= 13750) {
            return 607.5;
        } else if (salary <= 14250) {
            return 630;
        } else if (salary <= 14750) {
            return 652.5;
        } else if (salary <= 15250) {
            return 675;
        } else if (salary <= 15750) {
            return 697.5;
        } else if (salary <= 16250) {
            return 720;
        } else if (salary <= 16750) {
            return 742.5;
        } else if (salary <= 17250) {
            return 765;
        } else if (salary <= 17750) {
            return 787.5;
        } else if (salary <= 18250) {
            return 810;
        } else if (salary <= 18750) {
            return 832.5;
        } else if (salary <= 19250) {
            return 855;
        } else if (salary <= 19750) {
            return 877.5;
        } else if (salary <= 20250) {
            return 900;
        } else if (salary <= 20750) {
            return 922.5;
        } else if (salary <= 21250) {
            return 945;
        } else if (salary <= 21750) {
            return 967.5;
        } else if (salary <= 22250) {
            return 990;
        } else if (salary <= 22750) {
            return 1012.5;
        } else if (salary <= 23250) {
            return 1035;
        } else if (salary <= 23750) {
            return 1057.50;
        } else if (salary <= 24250) {
            return 1080;
        } else if (salary <= 24750) {
            return 1102.5;
        } else {
            return 1125;
        }
    }
    static double computePhilHealth(double salary){

        double contribution = salary * 0.03;

        if(contribution < 300)
            contribution = 300;

        if(contribution > 1800)
            contribution = 1800;

        return contribution / 2;
    }
    static double computePagibig(double salary) {

        double contribution;

        if (salary <= 1500) {
            contribution = (salary * 0.01);
        } else {
            contribution = (salary * 0.02);
        }
        if (contribution > 100) {
            contribution = 100;
        }
        return contribution;
    }
    static double computeTax(double salary) {
        if (salary <= 20832) {
            return 0;
        } else if (salary <= 33333) {
            return (salary * 0.2);
        } else if (salary <= 66667) {
            return (salary * 0.25) + 2500;
        } else if (salary <= 166667) {
            return (salary * 0.3) + 10833;
        } else if (salary <= 666667) {
            return (salary * 0.32) + 40833.33;
        } else {
            return (salary * 0.35) + 200833;
        }
    }
}
