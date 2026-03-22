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
        login(); // Start program
    }

    // ================= LOGIN SYSTEM =================
    // Prompts user for credentials and redirects based on role
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

    // ================= PAYROLL STAFF MENU =================
    static void payrollMenu() {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n1 Process Payroll");
        System.out.println("2 Exit Program");
        System.out.print("Choice: ");
        int choice = sc.nextInt();

        if (choice == 1) {
            processPayroll();
        } else {
            System.exit(0);
        }
    }

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
        } else {
            System.exit(0);
        }
    }

    // ================= EMPLOYEE FUNCTIONS =================
    static void employeeMenu() {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n1 Enter Employee Number");
        System.out.println("2 Exit Program");
        System.out.print("Choice: ");
        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 1) {
            System.out.print("Enter Employee Number: ");
            String empNum = sc.nextLine();
            displayEmployee(empNum);
        } else {
            System.exit(0);
        }
    }

    // Reads employee.csv and displays basic info
    static void displayEmployee(String empNum) {
        try (BufferedReader br = new BufferedReader(new FileReader("employee.csv"))) {
            String line;
            boolean found = false;

            br.readLine(); // skip header

            while ((line = br.readLine()) != null) {
                String[] employeeData = line.replace("\"", "").split(",");

                if (employeeData[0].equals(empNum)) {
                    System.out.println("\nEmployee Number: " + employeeData[0]);
                    System.out.println("Name: " + employeeData[2] + " " + employeeData[1]);
                    System.out.println("Birthday: " + employeeData[3]);
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

    // ================= PAYROLL PROCESSING =================

    // Handles payroll for one employee
    static void payrollOneEmployee() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Employee Number: ");
        String empNum = sc.nextLine();

        Employee employee = getEmployeeData(empNum);

        if (employee == null) {
            System.out.println("Employee number does not exist");
            return;
        }

        printEmployeeDetails(employee);
        computePayroll(employee.id, employee.rate);
    }

    // Handles payroll for all employees
    static void payrollAllEmployees() {
        try (BufferedReader br = new BufferedReader(new FileReader("employee.csv"))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.replace("\"", "").split(",");

                Employee emp = new Employee(
                        data[0],
                        data[2],
                        data[1],
                        data[3],
                        Double.parseDouble(data[18])
                );

                System.out.println("\n========================");
                printEmployeeDetails(emp);
                computePayroll(emp.id, emp.rate);
            }

        } catch (IOException e) {
            System.out.println("Error reading employee file");
        }
    }

    // ================= HELPER METHODS =================

    // Retrieves employee info from CSV
    static Employee getEmployeeData(String empNum) {
        try (BufferedReader br = new BufferedReader(new FileReader("employee.csv"))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.replace("\"", "").split(",");

                if (data[0].equals(empNum)) {
                    return new Employee(
                            data[0],
                            data[2],
                            data[1],
                            data[3],
                            Double.parseDouble(data[18])
                    );
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading employee file");
        }
        return null;
    }

    static void printEmployeeDetails(Employee emp) {
        System.out.println("Employee Number: " + emp.id);
        System.out.println("Name: " + emp.firstName + " " + emp.lastName);
        System.out.println("Birthday: " + emp.birthdate);
    }

    // ================= PAYROLL COMPUTATION =================

    static void computePayroll(String empId, double rate) {

        double totalHours = calculateHoursWorked(empId);
        double grossSalary = totalHours * rate;

        double deductions = calculateDeductions(grossSalary);
        double taxableIncome = grossSalary - deductions;
        double tax = computeTax(taxableIncome);

        double netSalary = grossSalary - (deductions + tax);

        System.out.println("\nTotal Hours Worked: " + totalHours);
        System.out.println("Gross Salary: " + grossSalary);
        System.out.println("SSS: " + computeSSS(grossSalary));
        System.out.println("PhilHealth: " + computePhilHealth(grossSalary));
        System.out.println("Pag-Ibig: " + computePagibig(grossSalary));
        System.out.println("Tax: " + tax);
        System.out.println("Total Deductions: " + (deductions + tax));
        System.out.println("Net Salary: " + netSalary);
    }

    // Calculates total hours from attendance.csv
    static double calculateHoursWorked(String empId) {
        double totalHours = 0;

        try (BufferedReader br = new BufferedReader(new FileReader("attendance.csv"))) {
            String line;
            br.readLine();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");

            while ((line = br.readLine()) != null) {
                String[] attendanceData = line.replace("\"", "").split(",");

                if (attendanceData.length < 6) continue;

                if (attendanceData[0].equals(empId)) {

                    LocalTime login = LocalTime.parse(attendanceData[4], formatter);
                    LocalTime logout = LocalTime.parse(attendanceData[5], formatter);

                    LocalTime start = LocalTime.of(8, 0);
                    LocalTime end = LocalTime.of(17, 0);

                    // Apply grace period logic
                    if (login.isBefore(start.plusMinutes(10))) {
                        login = start;
                    }

                    if (logout.isAfter(end)) {
                        logout = end;
                    }

                    if (logout.isAfter(login)) {
                        totalHours += Duration.between(login, logout).toMinutes() / 60.0;
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading attendance file");
        }

        return totalHours;
    }

    static double calculateDeductions(double salary) {
        return computeSSS(salary) + computePhilHealth(salary) + computePagibig(salary);
    }

    // ================= DEDUCTIONS =================

    static double computeSSS(double salary) {
        if (salary <= 3250) return 135;
        if (salary <= 24750) return salary * 0.045;
        return 1125;
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
        if (salary <= 33333) return salary * 0.2;
        if (salary <= 66667) return salary * 0.25 + 2500;
        if (salary <= 166667) return salary * 0.3 + 10833;
        if (salary <= 666667) return salary * 0.32 + 40833.33;
        return salary * 0.35 + 200833;
    }

    // ================= DATA CLASS =================
    static class Employee {
        String id, firstName, lastName, birthdate;
        double rate;

        Employee(String id, String firstName, String lastName, String birthdate, double rate) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.birthdate = birthdate;
            this.rate = rate;
        }
    }
}