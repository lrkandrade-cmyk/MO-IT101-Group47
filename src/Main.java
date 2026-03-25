import java.util.Scanner;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {

    static String[] months = {
            "January", "February", "March", "April",
            "May", "June", "July", "August",
            "September", "October", "November", "December"
    };

    public static void main(String[] args) {
        login(); // Start program
    }

    // ================= LOGIN =================
    //asks for user input and checks if equals to log-in credentials
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

    // ================= MENUS =================
    static void payrollMenu() {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n1 Process Payroll");
        System.out.println("2 Exit Program");
        System.out.print("Choice: ");
        int choice = sc.nextInt();

        if (choice == 1) processPayroll();
        else System.exit(0);
    }

    static void processPayroll() {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n1 One Employee");
        System.out.println("2 All Employees");
        System.out.println("3 Exit Program");
        System.out.print("Choice: ");
        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 1) payrollOneEmployee();
        else if (choice == 2) payrollAllEmployees();
        else System.exit(0);
    }

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

    // ================= DISPLAY =================
    static void displayEmployee(String empNum) {
        try (BufferedReader br = new BufferedReader(new FileReader("employee.csv"))) {
            String line;
            boolean found = false;

            br.readLine();

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

            if (!found) System.out.println("Employee number does not exist");

        } catch (IOException e) {
            System.out.println("Error reading employee file");
        }
    }

    // ================= PAYROLL =================
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

        Set<Integer> monthsWorked = getMonthsWorked(employee.id);

        for (int month : monthsWorked) {
            System.out.println("\nMonth: " + months[month - 1]);

            computePayroll(employee.id, employee.rate, 1, month);
            computePayroll(employee.id, employee.rate, 2, month);
        }
    }

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

                Set<Integer> monthsWorked = getMonthsWorked(emp.id);

                for (int month : monthsWorked) {
                    System.out.println("\nMonth: " + months[month - 1]);

                    computePayroll(emp.id, emp.rate, 1, month);
                    computePayroll(emp.id, emp.rate, 2, month);
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading employee file");
        }
    }

    // ================= File Processing =================
    //Retrieves employee info from CSV
    static Employee getEmployeeData(String empNum) {
        try (BufferedReader br = new BufferedReader(new FileReader("employee.csv"))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
    //Splits CSV into parts but avoids commas inside quotes such as addresses
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

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
        System.out.println("Rate: " + emp.rate);
    }
    //Checks months worked by employee from csv file
    static Set<Integer> getMonthsWorked(String empId) {
        Set<Integer> monthsWorked = new TreeSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader("attendance.csv"))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.replace("\"", "").split(",");

                if (!data[0].equals(empId)) continue;

                int month = Integer.parseInt(data[3].split("/")[0]);
                monthsWorked.add(month);
            }

        } catch (IOException e) {
            System.out.println("Error reading attendance file");
        }

        return monthsWorked;
    }

    // ================= PAYROLL =================
    static void computePayroll(String empId, double rate, int cutoff, int month) {
    //Calculates gross salary by multiplying total hours worked per cut off to hourly rate
        double totalHours = calculateHoursWorked(empId, cutoff, month);
        double grossSalary = totalHours * rate;
    //Computes net salary by subtracting deductions to gross salary first
    // before computing and subtracting taxable income.

        double deductions = calculateDeductions(grossSalary);
        double taxableIncome = grossSalary - deductions;
        double tax = computeTax(taxableIncome);

        double netSalary = grossSalary - (deductions + tax);

        if (cutoff == 1) {
            System.out.println("\nCutoff Date: " + months[month - 1] + " 1-15");
        } else {
            System.out.println("\nCutoff Date: " + months[month - 1] + " 16-30");
        }

        System.out.println("Total Hours Worked: " + totalHours);
        System.out.println("Gross Salary: " + grossSalary);
        System.out.println("SSS: " + computeSSS(grossSalary));
        System.out.println("PhilHealth: " + computePhilHealth(grossSalary));
        System.out.println("Pag-Ibig: " + computePagibig(grossSalary));
        System.out.println("Tax: " + tax);
        System.out.println("Total Deductions: " + deductions);
        System.out.println("Net Salary: " + netSalary);
    }
    // ================= HOURS WORKED =================
    static double calculateHoursWorked(String empId, int cutoff, int targetMonth) {
        double totalHours = 0;

        try (BufferedReader br = new BufferedReader(new FileReader("attendance.csv"))) {
            String line;
            br.readLine();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");

            while ((line = br.readLine()) != null) {
                String[] attendanceData = line.replace("\"", "").split(",");

                if (attendanceData.length < 6) continue;

                if (attendanceData[0].equals(empId)) {

                    String[] parts = attendanceData[3].split("/");

                    int month = Integer.parseInt(parts[0]);
                    int day = Integer.parseInt(parts[1]);

                    if (month == targetMonth) {
                        if ((cutoff == 1 && day <= 15) ||
                                (cutoff == 2 && day >= 16)) {

                            LocalTime login = LocalTime.parse(attendanceData[4], formatter);
                            LocalTime logout = LocalTime.parse(attendanceData[5], formatter);

                            LocalTime start = LocalTime.of(8, 0);
                            LocalTime end = LocalTime.of(17, 0);

        // Adds 10 minutes grace period in which if login in time is 10 minutes or less after login, it counts the full hour
                            if (login.isBefore(start.plusMinutes(10))) login = start;
                            if (logout.isAfter(end)) logout = end;

                            if (logout.isAfter(login)) {
                                totalHours += Duration.between(login, logout).toMinutes() / 60.0;
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading attendance file");
        }

        return totalHours;
    }

    // ================= DEDUCTIONS =================
    static double calculateDeductions(double salary) {
        return computeSSS(salary) + computePhilHealth(salary) + computePagibig(salary);
    }

    static double computeSSS(double monthlySalary) {
        // Salary cutoff values (upper bounds) for each contribution tier.
        double[] salaryLimits = { 3250, 3750, 4250, 4750, 5250, 5750, 6250, 6750, 7250, 7750, 8250, 8750, 9250, 9750, 10250, 10750, 11250, 11750, 12250, 12750, 13250, 13750, 14250, 14750, 15250, 15750, 16250, 16750, 17250, 17750, 18250, 18750, 19250, 19750, 20250, 20750, 21250, 21750, 22250, 22750, 23250, 23750, 24250, 24750 };
        // Corresponding SSS contribution amounts for each bracket.
        double[] sssContributions = { 135, 157.50, 180, 202.50, 225, 247.50, 270, 292.50, 315, 337.50, 360, 382.50, 405, 427.50, 450, 472.50, 495, 517.50, 540, 562.50, 585, 607.50, 630, 652.50, 675, 697.50, 720, 742.50, 765, 787.50, 810, 832.50, 855, 877.50, 900, 922.50, 945, 967.50, 990, 1012.50, 1035, 1057.50, 1080, 1102.50, 1125 };
        // Walk through each bracket and return the first contribution whose
        // upper salary limit is greater than the employee's salary.

        for (int index = 0; index < salaryLimits.length; index++) {
            if (monthlySalary < salaryLimits[index]) {
                return sssContributions[index];
            }
        }
        // If salary exceeds all brackets use max contribution
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
    //Represents employees and stores their information
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