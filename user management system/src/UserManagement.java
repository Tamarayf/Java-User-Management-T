import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
//Parent  class User 
abstract class User implements Comparable {//implement comparable interface
    //attribute
    protected String name;
    protected String email;
    protected String password; 
    protected String accessLevel;
    protected int ID;
    
    //constructor
    public User(int ID, String name, String password, String accLevel, boolean hash) throws NoSuchAlgorithmException {
        setName(name);
        setPassword(password, hash);
        setAccessLevel(accLevel);
        setID(ID);
    }
//getter to all the attribute since they are protected (only child can access)
    public String getName(){return name;}
    public String getEmail(){return email;}
    public String getPassword(){return password;}
    public String getAccLevel(){return accessLevel;}
    public int getID() {return ID;}
//setter for name 
    public void setName(String Name) throws IllegalArgumentException {
        //checking each character in the name to see if there's a digit 
        //to throw  an exception that tell the user that name should'nt contain digit  
        // since any name don't have number in the spelling
        for (int i = 0; i < Name.length(); i++) {
            if (Character.isDigit(Name.charAt(i))) {
                throw new IllegalArgumentException("Name cannot have any digits, please enter your name again");
            }
        }
                //if there's no digit assign to the class attribute
        this.name = Name;
    }
  //setter for id to assign the id to the attribute id
    private void setID(int ID) {
        this.ID = ID;
    }
//an abstract method that let the employee and custumer have different criteria to an email
    public abstract void setEmail(String Email);
 //setter for Password  which include checking on password if it is valide
    //if ot throw an exception to tell user how to validate the password 
    public void setPassword(String password, boolean hash) throws NoSuchAlgorithmException{
        if (hash) {// checking if the password need hashing do the hash
            if (!validPassword(password)) {
                throw new IllegalArgumentException("Password must be consisting of:\n1. At least 8 characters\n2. A mixture of both uppercase and lowercase letters\n3. A mixture of letters and numbers \n4. Inclusion of at least one special character, e.g., ! @ # ? ]");   
            }
            this.password = hashPassword(password);
        }
        else {//if he does'nt since it is already loaded form textfile hashed assign it as it s
            this.password = password;
        }
    }
//setter for access level which throws an exception if the user enter different AccessLevel
// (throws the exception for telling that only enter the access available)

    public void setAccessLevel(String accessLevel) throws IllegalArgumentException { 
        if (!accessLevel.equalsIgnoreCase("customer") && !accessLevel.equalsIgnoreCase("employee"))
            throw new IllegalArgumentException("Access level should be customer or employee");
        else
            this.accessLevel = accessLevel; 
    }
//method to Hash the password return the password hashed by characters and number to be protected 
// since it is too dangerous to save the password same as user
 
    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
//the  pass  validation  method to check if the pass is valid or no
    private boolean validPassword(String password) {
        if (password.length() < 8)// checking  password length if less than the required 
            return false;
        int occurence[] = new int[4];//checking the occurence of the CHaractere at least 
        //the character should occured 1 time 
        char c;
        for (int i = 0; i < password.length(); i++) {
            c = password.charAt(i);
            if (Character.isAlphabetic(c)) {
                if (Character.isUpperCase(c))
                    occurence[0]++;
                else
                    occurence[1]++;//occurence of  lowe case
            }
            else if (Character.isDigit(c))//digit character occurence
                occurence[2]++;
            else
                occurence[3]++;//other character occurence
        }
        for (int i = 0; i < occurence.length; i++) {
            if (occurence[i] < 1)//if any off the character  doesn't exist return false
                return false;
        }
        //else
        return true; 
    }
//ovverriding compare to method from object
    public int compareTo(Object o) throws IllegalArgumentException {
        if (!(o instanceof User))//checking if the object is instance of user 
            throw new IllegalArgumentException("Only user instances must be compared");//if no throw exception to bee a user of a child of users 
        int val = this.getName().compareTo(((User) o).getName());//checking the val if there's difference between  eachcharacter of name of users
        return val > 0 ? 1 : val == 0 ? 0 : -1;//return the difference 
    }
//to String of users
    public String toString() {
        return ID + "," + name + "," + email + "," + password + "," + accessLevel;
    }
}

//child of user employee
class Employee extends User  {
    //attribute
    private String JobTitle;
    private String department;
//constructor
    public Employee(int ID, String name, String email, String password, String AccLevel, String department, String jobTitle, boolean hash) throws NoSuchAlgorithmException, IllegalArgumentException {
        super(ID,name, password, AccLevel,hash);
        setEmail(email);
        setDepartment(department);
        setJobTitle(jobTitle);
    }
//getter 
    public String getJobTitle() { return JobTitle; }
    public String getDepartment() { return department; }
//letting the user only put the availble department

    public void setDepartment(String department) throws IllegalArgumentException{
        //creating an array saved all the department available for the company
        final String[] dep = new String[] { "Human Resources (HR)", "Finance and Accounting", "Sales and Marketing", "Information Technology (IT)", "Operations and Production", "Research and Development (R&D)", "Customer Service", "Administration", "Legal", "Supply Chain and Logistics" };
        String s = "";
        for (int i = 0; i < dep.length; i++) {
            if (department.equalsIgnoreCase(dep[i])) {//checking if  the department entered equal to one of the array content
                this.department=dep[i];//if they are equal assign it to the atribute 
                return; 
            }
            s += dep[i] + "\n";//if no save the content of array in string
        }
        throw new IllegalArgumentException("Department must be one of the following: " + s) ;//throw the exception to tell the user the available department by printing s
    }
    //setter for Job
    public void setJobTitle(String jobTitle) {
        this.JobTitle = jobTitle;
    }
//the abstract method of user 
    public void setEmail(String s) {
        //check the user email if they have more than @  throw exception
        if(s.split("@").length != 2 )
            throw new IllegalArgumentException("Wrong email, write the email correctly");
            //user email shpould end with anemail for the company  not any email acceptable like gmail or thers 
        if (!s.endsWith("@company.com"))//if it's not the user company email throw exception 
            throw new IllegalArgumentException("Invalid email, the email should belongs to the company (@company.com)");
        super.email = s; //the assign the email if it's acceptable
    }
//toString of Employee
    public String toString() {
        return super.toString() + "," + department + "," + JobTitle;
    }
}
//another child of User
class Customer extends User {
    //attribute
    private String billingAddress;
    private String creditCard;
//constructor
    public Customer(int ID, String Name, String Email, String Password, String AccLevel,String billingAdress, String credit, boolean hash) throws NoSuchAlgorithmException {
        super(ID, Name, Password, AccLevel, hash);
        setEmail(Email);
        setBillingAddress(billingAdress);
        setCreditCard(credit);
    }
//setter for credit card of the employee
    public void setCreditCard(String creditCard) throws IllegalArgumentException {
        if (creditCard.length() != 16)//if the credit card are not 16 digit throw exception
            throw new IllegalArgumentException("Credit card number must be a 16 digit number");
        for (int i = 0; i < creditCard.length(); i++) { //check if the user enter other than number and throw another exception
            if (!Character.isDigit(creditCard.charAt(i)))
                throw new IllegalArgumentException("Credit card number must only contain numbers");
        }
        //if  the credit card is valid assign it to the attribute
        this.creditCard = creditCard;
    }
//abstract method of user 
    public void setEmail(String s){
        if(s.split("@").length != 2) //check of the @ if it is more than 1 tell the user to renter another email
            throw new IllegalArgumentException("Wrong email, write the email correctly");
        if (s.endsWith("@company.com"))//customer email should'nt be the company email
            throw new IllegalArgumentException("Invalid email");
        super.email = s;    
    }
//setter for Address
    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }
//getter  for  creditcard
    public String getCreditcard() {
        return creditCard;
    }
//getter for billingAdress
    public String getBillingAdress() {
        return billingAddress;
    }
//toString of the customer
    public String toString() {
        return super.toString() + "," + billingAddress + "," + creditCard;
    }
}

class UserManager{
    private ArrayList<User> list;//creating list of users
    //files
    private File customersFile;
    private File employeesFile;

    public UserManager() throws IOException, NoSuchAlgorithmException {
        list = new ArrayList<User>();//initialize the arraylist
        //initialize the file and set their name 
        customersFile = new File("./customers.txt");
        employeesFile = new File("./employees.txt");
//checking if the file is created  if no create the customer file
        if (!customersFile.exists()) {
            customersFile.createNewFile();
        } 
        else {
            readUsersFromFile(customersFile.getName());
        }        
        //checking if the file is created  if no create the employee file
        if (!employeesFile.exists()) {
            employeesFile.createNewFile();
        }
        else {
            readUsersFromFile(employeesFile.getName());
        }
    }
 //method for creation of usersand add it the textfile and arraylist
 public void create(User u) throws NoSuchAlgorithmException, IOException {
    list.add(u);
    writeToFile(u,true);
}

//reading the file data in array
    public String read(int j) {
        String s = "";
        User u;
        //since the array if user we put 2 condition to see if the user want to read  the file customer or employee
        for (int i = 0; i < list.size(); i++) {
            u=list.get(i);
            //check the user if it is instance of employee
            if (j == 0 && (u instanceof Employee)) {
                
                s += ((Employee)u).toString() + "\n";//type cast and add it to the empty string
            }
            else if (j == 1 && (u instanceof Customer)) {//same for the customer check type cast add to the string 
                s += ((Customer)u).toString() + "\n";
            }
        }
        return s;//return the string
    }
//update method to lett the user update  data for any user he choose 
    public void update(int id, int ch, String val,int caller) throws NoSuchAlgorithmException, IOException {
        if(list.size()==0)
        {System.out.println("File empty,no user to update");}
        else {
            int x = search(id);
            if (x == -1)
                throw new IllegalArgumentException("No user with ID: " + id);
            if (list.get(x) instanceof Employee && caller == 2)
                throw new IllegalArgumentException("Not an employee");
            if (list.get(x) instanceof Customer && caller == 1)
                throw new IllegalArgumentException("Not a customer");
            if (list.get(x) instanceof Employee) {
                Employee e = ((Employee)list.get(x));
                if (ch == 1)
                    e.setName(val);
                else if (ch == 2)
                    e.setEmail(val);
                else if (ch == 3)
                    e.setPassword(val,false);
                else if (ch == 4)
                    e.setAccessLevel(val);
                else if (ch == 5)
                e.setDepartment(val); 
                else
                e.setJobTitle(val);
            }
            else {
                Customer c = ((Customer)list.get(x));
                if (ch == 1)
                c.setName(val);
                else if (ch == 2)
                    c.setEmail(val);
                else if (ch == 3)
                    c.setPassword(val, false);
                else if (ch == 4)
                    c.setAccessLevel(val);
                else if (ch == 5)
                    c.setBillingAddress(val);
                else
                    c.setCreditCard(val);
            }
            updateFile();
             
    }
    }
    //delete user method

    public void delete(int ID) throws IOException {
        if(list.size()==0)//checking if the list is empty first
            System.out.println("File is empty");
        else{ //if it is not remove from the array the user and update the file to update the changes
            int i = search(ID);
            if (i == -1) 
                throw new IllegalArgumentException("No user with ID: " + ID);
            list.remove(i);
            updateFile();   
        }             
    }
//searching user method
    public int search(int ID) {

        //seracching if the user is found in the txt  based on the unique id
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getID() == ID) {
                return i; //return the position in the array
            }
        }
        return -1;//if not found equal   -1
    }
       //sorting method using the insertion algorithm
    public void sort() throws NumberFormatException, NoSuchAlgorithmException, IllegalArgumentException, IOException {
        int j;
        User tmp;
          if(list.size()==0)//cheking if the list empty 
             System.out.println("File is empty");
           else{//sort   the array  using the compareto on alphabetical order 
        for (int i = 1; i < list.size(); i++) {
            tmp = list.get(i);
            for(j = i; (j > 0) && ((list.get(j-1).compareTo(tmp) > 0)); j--) {
                list.set(j, list.get(j-1));
            }
            list.set(j, tmp);
            updateFile();//change the changes in the file
        }
       
    }
    }
    //read the data from the file method 
    private void readUsersFromFile(String filename) throws FileNotFoundException, NoSuchAlgorithmException {
        File file = new File(filename); //create a new file object to represent the file with the given filename.
        Scanner scanner = new Scanner(file); //create a new scanner object to read data from the file.
    
        while (scanner.hasNextLine()) { //loop as long as there is lines to read
            String line = scanner.nextLine(); //read the nextline and store it
            String[] p = line.split(","); //split on , to get the data
    
            if (p[4].equalsIgnoreCase("Employee")) { //check if the fifth element is equal to employee
                Employee e = new Employee(Integer.parseInt(p[0]), p[1], p[2], p[3], p[4], p[5], p[6], false); //creating an employee with the data that are stored in the array, hash is set to false because the password is already hashed
                list.add(e); //adding it to the list
            } 
            else { //if not employee, it would be a customer
                Customer c = new Customer(Integer.parseInt(p[0]), p[1], p[2], p[3], p[4], p[5], p[6], false); //creating an customer with the data that are stored in the array, hash is set to false because the password is already hashed
                list.add(c); //adding it to the list
            }
        }
        scanner.close(); //closing the scanner
    }
    
    private void writeToFile(User u, boolean append) throws IOException {
        String filename, data; //creating two strings
        if (u instanceof Employee) { //checking if the user is an employee
            filename = employeesFile.getName(); //getting the employee's filename
            data = ((Employee)u).toString(); //casting the user to an employee and calling the toString
        }
        else { //if not employee, it would be a customer
            filename = customersFile.getName(); //getting the customers filename
            data = ((Customer)u).toString(); //casting the user to a customer and calling the toString
        }
        FileWriter writer = new FileWriter(filename, append); //creating a FileWriter object that takes the filename and uses append to check if the file needs to be rewritten
        writer.write(data + "\n"); //writing the data to the file
        writer.close(); //closing the writer
    }
    
    private void updateFile() throws IOException{
        int emp = 0, cust = 0; //creating two integers to represent the number of employees and customers
        for (int i = 0; i < list.size(); i++) { //looping through the arraylist
            if ((list.get(i) instanceof Employee) && emp < 1) { //checking if the user is an employee and checking if it is the first one
                writeToFile(list.get(i), false); //rewriting the file and adding the user to it
                emp++; //incrementing the number of employees
            }
            else if ((list.get(i) instanceof Customer) && cust < 1) { //checking if the user is a customer and checking if it is the first one
                writeToFile(list.get(i), false); //rewriting the file and adding the user to it
                cust++; //incrementing the number of customers
            }
            else {
                writeToFile(list.get(i), true); //writing at the end of the file if not the first employee or customer 
            }
        }
    }
}

public class TamaraYoussef {
    static int count = 1;//this counter is for the id since the id is unique so i decided to give any user an id by the order
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        Scanner sc = new Scanner(System.in);
        UserManager um = new UserManager();//create user manager um
        int input; //user choice
        do {
            while (true) {
                try {
                    System.out.println("Press 1 to create an employee.\nPress 2 to create a customer.\nPress 3 to read employees file.\nPress 4 to read customers files.\nPress 5 to sort files\nPress 6 to update a employee data.\nPress 7 to update a customer data.\nPress 8 to delete a user.\nPress 9 to quit");
                    input = sc.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Enter an integer");
                    sc.nextLine();
                }
            }
            sc.nextLine();
            //case 1 creation of the employee data
            switch (input) {
                case 1:
                    um.create(getEmpData());
                    break;
                case 2:  //case 2 creation of the customer data
                    um.create(getCustData());
                    break;
                case 3: {
                    //case 3 read the data  of employee
                    String m = um.read(0);
                    if(m.equals(""))
                        System.out.println("File is empty,there is no any data");
                    else
                        System.out.println(m);
                    break;
                }
                case 4: {
                    //case 4 read the data of the customer
                    String m = um.read(1);
                    if(m.equals(""))
                        System.out.println("File is empty,there is no any data");
                    else
                        System.out.println(m);
                    break;
                }
                case 5:
                //case 5 sort the data base on alphabetical order
                    um.sort();
                    break;
                    case 6: {
                        //case 6 updating  the data of employee 
                        try {
                            //get the info needed for the update, the parameter is a string that describes what can be changed for the employee
                            String[] a = getForUpdate("Enter 1 to change name.\nEnter 2 to change email.\nEnter 3 to change password.\nEnter 4 to change access level.\nEnter 5 to change department.\nEnter 6 to change job title.");
                            um.update(Integer.parseInt(a[0]), Integer.parseInt(a[1]), a[2],1); //calling the update that take the id, then the number of the thing to be changed, then the new value, and a int value to determine how it should work 
                        } catch(IllegalArgumentException e) {
                            System.out.println(e.getMessage()); //if there 's' an exception occurs, print it 
                        }
                        break;
                    }
                    case 7: {
                        //case 7 updating the data of custumer
                        try {

                            //get the info needed for the update, the parameter is a string that describes what can be changed for the user
                            String[] a = getForUpdate("Enter 1 to change name.\nEnter 2 to change email.\nEnter 3 to change password.\nEnter 4 to change access level.\nEnter 5 to change billing address.\nEnter 6 to change credit card number.");
                            um.update(Integer.parseInt(a[0]), Integer.parseInt(a[1]), a[2],2); //calling the update that take the id, then the number of the thing to be changed, then the new value, and a int value to determine how it should work
                        } catch(IllegalArgumentException e) {
                            System.out.println(e.getMessage()); //if  there 's an exception occurs, print it 
                        }
                        break;
                    }
                    case 8: {
                        //case 8 delete  the data chosen
                        int i;
                        System.out.print("Enter user ID: "); //asking for the ID
                        try {
                            i = sc.nextInt(); //taking  the ID from user
                            um.delete(i); //  delete the user who has the  id
                            count--; //decrementing the count
                        } catch(InputMismatchException e) {
                            System.out.println("ID must be a number"); //printing that the number must be an integer in case of input mismatch
                        }
                        catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage()); //printing the error
                        }
                        break;
                    }
                case 9://exit 
                System.out.println("Exist");
                    break;
                    default:System.out.println("invalid choice ");
            }
        } while (input != 9);
    }

    public static Employee getEmpData() throws NoSuchAlgorithmException, IllegalArgumentException {
        String[] d = new String[7]; //creating an array to store provided data
        Scanner sc = new Scanner(System.in); //creating a scanner
        while (true) { //asking for data as long as there is an error in the provided ones
            try {
                d[0] = "" + count++; //setting the id
                System.out.print("Name: ");
                d[1] = sc.nextLine(); //getting the name
                System.out.print("Email: ");
                d[2] = sc.nextLine(); //getting the email
                System.out.print("Password: ");
                d[3] = sc.nextLine(); //getting the password
                System.out.print("Access level: ");
                d[4] = sc.nextLine(); //getting the access level
                System.out.print("Department: ");
                d[5] = sc.nextLine(); //getting the department name
                System.out.print("Job title: ");
                d[6] = sc.nextLine(); //getting the job title
                return new Employee(Integer.parseInt(d[0]),d[1],d[2],d[3],d[4],d[5],d[6],true); //returning an employee whose data is the ones provided, if correct
            } catch(IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    public static Customer getCustData() throws NoSuchAlgorithmException, IllegalArgumentException {
        String[] d = new String[7]; //creating an array to store the data
        Scanner sc = new Scanner(System.in); //creating a scanner
        while (true) { //asking for data as long as there is an error in the given ones
            try {
                d[0] = "" + count++; //setting the id
                System.out.print("Name: ");
                d[1] = sc.nextLine(); //getting the name
                System.out.print("Email: ");
                d[2] = sc.nextLine(); //getting the email
                System.out.print("Password: ");
                d[3] = sc.nextLine(); //getting the password
                System.out.print("Access level: ");
                d[4] = sc.nextLine(); //getting the access level
                System.out.print("Billing address: ");
                d[5] = sc.nextLine(); //getting the billing address
                System.out.print("Credit card: ");
                d[6] = sc.nextLine(); //getting the credit card number
                return new Customer(Integer.parseInt(d[0]),d[1],d[2],d[3],d[4],d[5],d[6],true); //returning a customer whose data is the ones provided, if correct
            } catch(IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public static String[] getForUpdate(String r) {
        Scanner sc = new Scanner(System.in); //creating a scanner
        int id, i = 0; //id is the user id, i is for the data to be changed
        String s; //s is for the new data
        String[] a = new String[3]; //where data will be stored, in order to be returned
        boolean valid = false; //valid is to check if the index is right for the data to be changed
        while (true) { //looping as long as there is an input mismatch
            try {
                System.out.print("Enter user ID: "); //asking for id
                id = sc.nextInt(); //reading it
                break;
            } catch (InputMismatchException e) {
                System.out.println("ID should be an integer");
                sc.nextLine(); //to read the \n left after the number
            }
        }
        sc.nextLine(); //to read the \n left after the number
        do { //looping as long as the index is wrong
            try {
                System.out.println(r);
                i = sc.nextInt(); //taking the index
                if (i >= 1 && i <= 6) //checking if it is valid
                    valid = true;
            } catch (InputMismatchException e) {
                System.out.println("Choice should be an integer");                
            }
            sc.nextLine(); //to read the \n left after the number
        } while (!valid);
        System.out.print("Enter the new data: "); //asking for the new data
        s = sc.nextLine(); //reading it
        //storing the needed information
        a[0] = "" + id;
        a[1] = "" + i;
        a[2] = s;
        return a; //returning the array that has all info
    }
}  

/*
Employees.txt
0,Tamara,tamra@company,Tamara123@,Human REsources (HR),hr
4,christelle,christelle@company.com, CHRistelle123#,employee,Legal,Lawyer
6,Anna maria,Annamaria@company.com,ANNa123maria@,employee,Finance and Accounting,Accountant

*/
/*
customers.txt
3,Tia,tia@gmail.com, TiaY123@1,customer,dbayeh,1234567898765432
5,Fadi,fadiyoussef@gmail.com,FADI123i$,customer,Beirut,2233445566778899
7,hinda,hinda@gmail.com,HINDa1234@,customer,Paris,2323454567678989

*/