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
