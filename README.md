Payroll System Project
Team Details

Team Name: MO-IT101-Group47
Date Added: 12/03/2026

Members and Contributions:

Kean Andrade-Base Program Structure

Developed the initial version of the payroll system including:
* Basic login authentication
* Initial menu system
* Method for reading employee data from employee.csv
* Basic payroll computation using attendance records
* Implementation of file reading using BufferedReader
* Initial calculation of hours worked and gross salary
* Basic console output for employee payroll information

Jyvs Kenth Aycardo-Payroll System Enhancements
Expanded and improved the base system by implementing:
* Authentication and Role Separation
* Employee Payroll Processing
* Attendance Processing
* CSV Handling Improvements
* Payroll Computation

Program Details

This Java Payroll System reads employee and attendance data from CSV files and calculates the salary of employees based on their working hours.

The system works as follows:

The user logs into the system using a username and password.

After logging in, a menu is displayed with the following options:

If username is: employee
Display options:
1. ﻿﻿﻿Enter your employee number
2. ﻿﻿﻿Exit the program

Enter your employee number
   If correct, display the details:
* ﻿﻿﻿Employee Number:
* ﻿﻿﻿Employee Name:
* ﻿﻿﻿Birthday:

If the employee number does not exist, display:
a. Employee number does not exist.

2. Exit the program
Terminate the program.

If username is: payroll_staff
Display options:
1. Process Payroll
2. Exit Program

Process Payroll 
Display sub-options:
1. ﻿﻿﻿One employee
2. ﻿﻿﻿All employees
3. ﻿﻿﻿Exit the program

1. One employee
a.Enter the employee number.
If the employee number is incorrect, display:
Employee number does not exist.
If correct, display employee details.
b. Exit the program

2. All employees
Follows same format as One employee but this time for all employees

3. Exit Program

Example Output:
Employee Number:10001
First Name: Manuel III
Last Name: Garcia
Birthday: 10/11/1983

Month: June
Cutoff Date: June 1-15
Total Hours Worked: 74.83333333333333
Gross Salary: 40088.965000000004
Net Salary: 40088.965000000004

Cutoff Date: June 16 - 30
Total Hours Worked: 76.81666666666669
Gross Salary: 41151.456500000015
SSS: 1125.0
PhilHealth: 900.0
Pag-Ibig: 100.0
Tax: 34567.62645000001
Total Deductions: 2125.0
Net Salary: 79115.42150000003

Project Plan Link

Project Plan Document:
https://docs.google.com/spreadsheets/d/1g04PKozvkaz77Ghqhjd6WK6bXPx7r2daCM_ZHe1qN5k/edit?usp=sharing

## Quality Assurance (QA) Test Cases

These test cases were created to verify that the Payroll System functions correctly under different scenarios, including valid inputs, invalid inputs, and edge cases.

---

### 1. Login System

| Test Case                 | Input                                        | Expected Output                                         |
| ------------------------- | -------------------------------------------- | ------------------------------------------------------- |
| Valid payroll staff login | Username: `payroll_staff`, Password: `12345` | Displays payroll menu (Process Payroll / Exit)          |
| Valid employee login      | Username: `employee`, Password: `12345`      | Displays employee menu (Enter Employee Number / Exit)   |
| Invalid username          | Username: `wrong`, Password: `12345`         | Displays "Incorrect username and/or password" and exits |
| Invalid password          | Username: `payroll_staff`, Password: `wrong` | Displays "Incorrect username and/or password" and exits |

---

### 2. Payroll Staff Menu

| Test Case              | Input       | Expected Output                                                |
| ---------------------- | ----------- | -------------------------------------------------------------- |
| Select Process Payroll | Choice: `1` | Displays payroll options (One Employee / All Employees / Exit) |
| Exit program           | Choice: `2` | Program terminates                                             |

---

### 3. Payroll Options

| Test Case            | Input       | Expected Output                            |
| -------------------- | ----------- | ------------------------------------------ |
| One Employee option  | Choice: `1` | Prompts "Enter Employee Number"            |
| All Employees option | Choice: `2` | Displays payroll details for all employees |
| Exit option          | Choice: `3` | Program terminates                         |

---

### 4. Payroll – One Employee

| Test Case               | Input         | Expected Output                              |
| ----------------------- | ------------- | -------------------------------------------- |
| Valid employee number   | e.g., `10001` | Displays employee info + payroll computation |
| Invalid employee number | e.g., `99999` | Displays "Employee number does not exist"    |

---

### 5. Payroll – All Employees

| Test Case             | Input       | Expected Output                                            |
| --------------------- | ----------- | ---------------------------------------------------------- |
| Process all employees | Choice: `2` | Displays payroll details for each employee in the CSV file |

---

### 6. Employee Menu

| Test Case               | Input                       | Expected Output                               |
| ----------------------- | --------------------------- | --------------------------------------------- |
| View employee details   | Choice: `1`, Enter valid ID | Displays employee number, name, and birthdate |
| Invalid employee number | Enter `99999`               | Displays "Employee Number does not exist"     |
| Exit program            | Choice: `2`                 | Program terminates                            |

---

### 7. Attendance & Time Handling

| Test Case                      | Scenario                  | Expected Output     |
| ------------------------------ | ------------------------- | ------------------- |
| Early login                    | Before 8:00 AM            | Adjusted to 8:00 AM |
| Late login within grace period | 8:01–8:10 AM              | Treated as 8:00 AM  |
| Late logout                    | After 5:00 PM             | Adjusted to 5:00 PM |
| Invalid time                   | Logout earlier than login | Entry is ignored    |

---

### 8. Payroll Computation

| Test Case                | Scenario                | Expected Output      |
| ------------------------ | ----------------------- | -------------------- |
| Gross salary calculation | Hours × hourly rate     | Correct gross salary |
| SSS deduction            | Based on salary range   | Correct SSS value    |
| PhilHealth deduction     | 3% (min 300, max 1800)  | Correct contribution |
| Pag-IBIG deduction       | 1% or 2% (max 100)      | Correct contribution |
| Tax calculation          | Based on salary bracket | Correct tax value    |
| Net salary               | Gross − deductions      | Correct net salary   |

---

### 9. File Handling

| Test Case              | Scenario             | Expected Output                           |
| ---------------------- | -------------------- | ----------------------------------------- |
| Missing employee.csv   | File not found       | Displays "Error reading employee file"    |
| Missing attendance.csv | File not found       | Displays "Error reading attendance file." |
| Correct file format    | Proper CSV structure | System runs successfully                  |

---

## Summary

All major system components login, menu navigation, employee lookup, payroll processing, and file handling  were tested and verified to function correctly.

