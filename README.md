# User Management System

This Java program implements a simple user management system for a company. The system supports CRUD (Create, Read, Update, Delete) operations on user data, including employees and customers. The program organizes and sorts user data using an abstract class and the Comparable interface. Additionally, it implements input validation through exception handling and encrypts passwords before saving them to a file.

## Features

- Create, Read, Update, and Delete user data
- Abstract class and Comparable interface for organizing and sorting data
- Input validation for user data
- Password encryption for user security
- Save user data to files and load from files



## User Class Hierarchy

The program defines three classes:

1. **User (Abstract Class):**
    - Attributes: name, email, password, access level, ID
    - Abstract methods for setting email and toString
    - Implements Comparable interface for sorting based on the user's name
    - Password is hashed for security

2. **Employee (Child of User):**
    - Additional attributes: job title, department
    - Methods for setting department and job title
    - Overrides abstract method setEmail
    - Overrides toString for employee-specific details

3. **Customer (Child of User):**
    - Additional attributes: billing address, credit card
    - Methods for setting billing address and credit card
    - Overrides abstract method setEmail
    - Overrides toString for customer-specific details

4. **UserManager:**
    - Manages the list of users, including creation, reading, updating, and deleting operations
    - Handles file operations for storing and retrieving user data


## Usage

To use the user management system, follow these steps:

1. Compile the Java program using a Java compiler.
2. Run the compiled program.
3. Choose from the provided options to perform various operations, such as creating users, reading user data, sorting, updating, and deleting users.
4. The program will store user data in separate files for employees (`employees.txt`) and customers (`customers.txt`).


## Contributing

Contributions are welcome! If you have suggestions, enhancements, or find issues, please open a new issue or submit a pull request.

## Developer

- Tamara youssef (Tamarayf)

# Java-User-Management-T >> README.md
git init
git add README.md

 echo # Java-User-Management-T
# Java-User-Management-T >> README.md
git init
git add README.md



echo # Java-User-Management-T
# Java-User-Management-T >> README.md
git init
git add README.md 


echo # Java-User-Management-T
