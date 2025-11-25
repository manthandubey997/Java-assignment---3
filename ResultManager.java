import java.util.InputMismatchException;
import java.util.Scanner;

public class ResultManager {

    private static final int MAX_STUDENTS = 100; // size of array to store students
    private Student[] students;
    private int studentCount;
    private Scanner scanner;

    public ResultManager() {
        students = new Student[MAX_STUDENTS];
        studentCount = 0;
        scanner = new Scanner(System.in);
    }

    // Add student (handles input mismatches and uses custom InvalidMarksException)
    public void addStudent() {
        try {
            System.out.print("Enter Roll Number: ");
            int roll = scanner.nextInt();
            scanner.nextLine(); // consume newline

            System.out.print("Enter Student Name: ");
            String name = scanner.nextLine();

            int[] marks = new int[3];
            for (int i = 0; i < 3; i++) {
                System.out.print("Enter marks for subject " + (i + 1) + ": ");
                marks[i] = scanner.nextInt();
            }

            // Create student - constructor will call validateMarks() and may throw InvalidMarksException
            Student s = new Student(roll, name, marks);

            // store student if capacity allows
            if (studentCount < MAX_STUDENTS) {
                students[studentCount++] = s;
                System.out.println("Student added successfully. Returning to main menu...");
            } else {
                System.out.println("Cannot add more students. Storage full.");
            }

        } catch (InvalidMarksException ime) {
            // custom exception caught
            System.out.println("Error: " + ime.getMessage());
            System.out.println("Returning to main menu...");
            // clear newline left by nextInt? not necessary here
        } catch (InputMismatchException ime2) {
            // handles non-integer inputs for marks or roll number
            System.out.println("Input error: Please enter data in correct format (integers for roll and marks).");
            scanner.nextLine(); // clear bad token
            System.out.println("Returning to main menu...");
        } catch (Exception e) {
            // catch-all for unexpected exceptions
            System.out.println("An unexpected error occurred: " + e.getMessage());
            System.out.println("Returning to main menu...");
        }
    }

    // Show details for a student by roll number
    public void showStudentDetails() {
        try {
            System.out.print("Enter Roll Number to search: ");
            int key = scanner.nextInt();
            boolean found = false;
            for (int i = 0; i < studentCount; i++) {
                if (students[i].getRollNumber() == key) {
                    System.out.println();
                    students[i].displayResult();
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Student with roll number " + key + " not found.");
            }
            System.out.println("Search completed.");
        } catch (InputMismatchException ime) {
            System.out.println("Input error: Roll number must be an integer.");
            scanner.nextLine(); // clear bad token
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    // Main menu with loop and finally to close scanner on exit
    public void mainMenu() {
        try {
            while (true) {
                System.out.println("\n===== Student Result Management System =====");
                System.out.println("1. Add Student");
                System.out.println("2. Show Student Details");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");

                int choice;
                try {
                    choice = scanner.nextInt();
                } catch (InputMismatchException ime) {
                    System.out.println("Invalid input. Please enter numeric choice.");
                    scanner.nextLine(); // clear invalid token
                    continue;
                }

                switch (choice) {
                    case 1:
                        addStudent();
                        break;
                    case 2:
                        showStudentDetails();
                        break;
                    case 3:
                        System.out.println("Exiting program. Thank you!");
                        return; // exit mainMenu -> will go to finally block
                    default:
                        System.out.println("Invalid option. Try again.");
                }
            }
        } finally {
            // ensure scanner is closed & display closing message
            if (scanner != null) {
                scanner.close();
            }
            System.out.println("Scanner closed. Program terminated.");
        }
    }

    public static void main(String[] args) {
        ResultManager manager = new ResultManager();
        manager.mainMenu();
    }
}
