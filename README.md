Payroll System Project
Team Details

Team Name: MO-IT101-Group47
Date Added: 

Members and Contributions:

Kean Andrade – Implemented the login system, menu navigation, and CSV file reading.

Jyvs Kenth Aycardo – Assisted with payroll computation and program testing.

Kurt Gabriel Pagaduan – Helped organize project files and documentation.

Program Details

This Java Payroll System reads employee and attendance data from CSV files and calculates the salary of employees based on their working hours.

The system works as follows:

The user logs into the system using a username and password.

After logging in, a menu is displayed with the following options:

View Employees

Compute Payroll

Exit

When the user selects View Employees, the system reads the employee data from employee.csv and displays the employee ID and name.

When the user selects Compute Payroll, the system reads attendance data from attendance.csv.

The program calculates the hours worked by comparing the login and logout times using Java's LocalTime and Duration classes.

The salary is computed using the formula:

Salary = Hours Worked × Hourly Rate

The system then displays the employee ID, employee name, hours worked, and gross pay.

Example Output:

Employee ID: 10034
Employee Name: Beatriz Santos
Hours Worked: 7.72
Gross Pay: ₱1157.50
Project Plan Link

Project Plan Document:
https://docs.google.com/spreadsheets/d/1g04PKozvkaz77Ghqhjd6WK6bXPx7r2daCM_ZHe1qN5k/edit?usp=sharing
