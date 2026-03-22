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
        // Start the system by asking the user to log in
        login();
    }

    // Handles user login authentication
    static void login() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Username: ");
        String username = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        // Check user credentials and redirect to appropriate menu
        if (username.equals("payroll_staff") && password.equals("12345")) {
            payrollMenu();
        } else if (username.equals("employee") && password.equals("12345")) {
            employeeMenu();
        } else {
            System.out.println("Incorrect username and/or password");
            System.exit(0);
        }
    }

    // Displays menu for payroll staff users
    static void payrollMenu() {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n1 Process Payroll");
        System.out.println("2 Exit Program");
        System.out.print("Choice: ");

        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 1) {
            processPayroll();
        } else if (choice == 2) {
            System.exit(0);
        }
    }

    // Displays payroll processing options
    static void processPayroll() {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n1 One Employee");
        System.out.println("2 All Employees");
        System.out.println("3 Exit Program");
        System.out.print("Choice: ");

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

    // Processes payroll for a single employee
    static void payrollOneEmployee() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Employee Number: ");
        String empNum = sc.nextLine();

        try (BufferedReader br = new BufferedReader(new FileReader("employee.csv"))) {

            String line;
            boolean found = false;

            br.readLine(); // skip header

            while ((line = br.readLine()) != null) {

                String[] employeeData = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                // Basic validation for CSV format
                if (employeeData.length < 19) continue;

                String id = employeeData[0];
                String lastName = employeeData[1];
                String firstName = employeeData[2];
                String birthDate = employeeData[3];
                double rate = Double.parseDouble(employeeData[18]);

                if (id.equals(empNum)) {
                    System.out.println("\nEmployee Number: " + id);
                    System.out.println("First Name: " + firstName);
                    System.out.println("Last Name: " + lastName);
                    System.out.println("Birthday: " + birthDate);

                    computePayroll(id, rate);
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("Employee number does not exist");
            }

        } catch (IOException e) {
            System.out.println("Error reading employee file");
        }
    }

    // Processes payroll for all employees
    static void payrollAllEmployees() {
        try (BufferedReader br = new BufferedReader(new FileReader("employee.csv"))) {

            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {

                String[] employeeData = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                if (employeeData.length < 19) continue;

                String id = employeeData[0];
                String lastName = employeeData[1];
                String firstName = employeeData[2];
                String birthDate = employeeData[3];
                double rate = Double.parseDouble(employeeData[18]);

                System.out.println("\n========================");
                System.out.println("Employee Number: " + id);
                System.out.println("Name: " + firstName + " " + lastName);
                System.out.println("Birthday: " + birthDate);

                computePayroll(id, rate);
            }

        } catch (IOException e) {
            System.out.println("Error reading employee file");
        }
    }

    // Displays employee menu
    static void employeeMenu() {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n1 Enter Employee Number");
        System.out.println("2 Exit Program");
        System.out.print("Choice: ");

        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 1) {
            enterEmployeeNumber();
        } else {
            System.exit(0);
        }
    }

    // Gets employee number input
    static void enterEmployeeNumber() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Employee Number: ");
        String employeeNumber = sc.nextLine();

        searchEmployee(employeeNumber);
    }

    // Searches and displays employee information
    static void searchEmployee(String empNum) {
        try (BufferedReader br = new BufferedReader(new FileReader("employee.csv"))) {

            String line;
            boolean found = false;

            br.readLine();

            while ((line = br.readLine()) != null) {

                line = line.replaceAll("\"", "");
                String[] employeeData = line.split(",");

                if (employeeData.length < 4) continue;

                String id = employeeData[0];
                String lastName = employeeData[1];
                String firstName = employeeData[2];
                String birthDate = employeeData[3];

                if (id.equals(empNum)) {
                    System.out.println("\nEmployee Number: " + id);
                    System.out.println("Employee Name: " + firstName + " " + lastName);
                    System.out.println("Employee Birth Date: " + birthDate);
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("Employee Number does not exist");
            }

        } catch (IOException e) {
            System.out.println("Error reading file");
        }
    }

    // Computes payroll including hours, deductions, and net salary
    static void computePayroll(String empId, double rate) {
        try (BufferedReader br = new BufferedReader(new FileReader("attendance.csv"))) {

            String line;

            double[] firstCutoffHours = new double[13];
            double[] secondCutoffHours = new double[13];

            br.readLine();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");

            while ((line = br.readLine()) != null) {

                line = line.replace("\"", "");
                String[] attendanceData = line.split(",");

                if (attendanceData.length < 6) continue;

                String id = attendanceData[0];
                String date = attendanceData[3];
                String login = attendanceData[4];
                String logout = attendanceData[5];

                if (!id.equals(empId)) continue;

                String[] parts = date.split("/");
                int month = Integer.parseInt(parts[0]);
                int day = Integer.parseInt(parts[1]);

                if (month < 6 || month > 12) continue;

                LocalTime loginTime = LocalTime.parse(login, formatter);
                LocalTime logoutTime = LocalTime.parse(logout, formatter);

                LocalTime start = LocalTime.of(8, 0);
                LocalTime grace = LocalTime.of(8, 10);
                LocalTime end = LocalTime.of(17, 0);

                // Adjust login time based on grace period
                if (loginTime.isBefore(start) || loginTime.isBefore(grace)) {
                    loginTime = start;
                }

                // Limit logout time
                if (logoutTime.isAfter(end)) {
                    logoutTime = end;
                }

                if (logoutTime.isBefore(loginTime)) continue;

                Duration duration = Duration.between(loginTime, logoutTime);
                double hoursWorked = duration.toMinutes() / 60.0;

                if (day <= 15) {
                    firstCutoffHours[month] += hoursWorked;
                } else {
                    secondCutoffHours[month] += hoursWorked;
                }
            }

            String[] months = {"", "Jan", "Feb", "Mar", "Apr", "May",
                    "June", "July", "Aug", "Sep", "Oct", "Nov", "Dec"};

            for (int m = 6; m <= 12; m++) {

                double gross1 = firstCutoffHours[m] * rate;
                double gross2 = secondCutoffHours[m] * rate;
                double totalGross = gross1 + gross2;

                double sss = computeSSS(totalGross);
                double philhealth = computePhilHealth(totalGross);
                double pagibig = computePagibig(totalGross);

                double deductions = sss + philhealth + pagibig;
                double taxableIncome = totalGross - deductions;
                double tax = computeTax(taxableIncome);

                double totalNetSalary = totalGross - (deductions + tax);

                System.out.println("\nMonth: " + months[m]);
                System.out.println("Total Gross Salary: " + totalGross);
                System.out.println("SSS: " + sss);
                System.out.println("PhilHealth: " + philhealth);
                System.out.println("Pag-Ibig: " + pagibig);
                System.out.println("Tax: " + tax);
                System.out.println("Total Deductions: " + (deductions + tax));
                System.out.println("Net Salary: " + totalNetSalary);
            }

        } catch (IOException e) {
            System.out.println("Error reading attendance file.");
        }
    } static double computeSSS(double salary) {
        if (salary <= 3250) return 135;
        else if (salary <= 3750) return 157.5;
        else if (salary <= 4250) return 180;
        else if (salary <= 4750) return 202.5;
        else if (salary <= 5250) return 225;
        else if (salary <= 5750) return 247.5;
        else if (salary <= 6250) return 270;
        else if (salary <= 6750) return 292.5;
        else if (salary <= 7250) return 315;
        else if (salary <= 7750) return 337.5;
        else if (salary <= 8250) return 360;
        else if (salary <= 8750) return 382.5;
        else if (salary <= 9250) return 405;
        else if (salary <= 9750) return 427.5;
        else if (salary <= 10250) return 450;
        else if (salary <= 10750) return 472.5;
        else if (salary <= 11250) return 495;
        else if (salary <= 11750) return 517.5;
        else if (salary <= 12250) return 540;
        else if (salary <= 12750) return 562.5;
        else if (salary <= 13250) return 585;
        else if (salary <= 13750) return 607.5;
        else if (salary <= 14250) return 630;
        else if (salary <= 14750) return 652.5;
        else if (salary <= 15250) return 675;
        else if (salary <= 15750) return 697.5;
        else if (salary <= 16250) return 720;
        else if (salary <= 16750) return 742.5;
        else if (salary <= 17250) return 765;
        else if (salary <= 17750) return 787.5;
        else if (salary <= 18250) return 810;
        else if (salary <= 18750) return 832.5;
        else if (salary <= 19250) return 855;
        else if (salary <= 19750) return 877.5;
        else if (salary <= 20250) return 900;
        else if (salary <= 20750) return 922.5;
        else if (salary <= 21250) return 945;
        else if (salary <= 21750) return 967.5;
        else if (salary <= 22250) return 990;
        else if (salary <= 22750) return 1012.5;
        else if (salary <= 23250) return 1035;
        else if (salary <= 23750) return 1057.5;
        else if (salary <= 24250) return 1080;
        else if (salary <= 24750) return 1102.5;
        else return 1125;
    }

    static double computePhilHealth(double salary) {
        double contribution = salary * 0.03;
        if (contribution < 300) contribution = 300;
        if (contribution > 1800) contribution = 1800;
        return contribution / 2;
    }

    static double computePagibig(double salary) {
        double contribution = (salary <= 1500) ? salary * 0.01 : salary * 0.02;
        return Math.min(contribution, 100);
    }

    static double computeTax(double salary) {
        if (salary <= 20832) return 0;
        else if (salary <= 33333) return salary * 0.2;
        else if (salary <= 66667) return (salary * 0.25) + 2500;
        else if (salary <= 166667) return (salary * 0.3) + 10833;
        else if (salary <= 666667) return (salary * 0.32) + 40833.33;
        else return (salary * 0.35) + 200833;
    }
}