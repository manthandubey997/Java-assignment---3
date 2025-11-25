public class Student {
    private int rollNumber;
    private String studentName;
    private int[] marks; // marks for 3 subjects

    public Student(int rollNumber, String studentName, int[] marks) throws InvalidMarksException {
        this.rollNumber = rollNumber;
        this.studentName = studentName;
        this.marks = marks;
        validateMarks(); // validate on creation - may throw InvalidMarksException
    }

    // Validate marks in range 0..100. Throws custom checked exception if invalid.
    public void validateMarks() throws InvalidMarksException {
        if (marks == null || marks.length != 3) {
            throw new InvalidMarksException("Marks array must contain exactly 3 subject marks.");
        }
        for (int i = 0; i < marks.length; i++) {
            int m = marks[i];
            if (m < 0 || m > 100) {
                throw new InvalidMarksException("Invalid marks for subject " + (i + 1) + ": " + m + ". Must be 0-100.");
            }
        }
    }

    // Calculates average marks (double)
    public double calculateAverage() {
        int sum = 0;
        for (int m : marks) sum += m;
        return (double) sum / marks.length;
    }

    // Determines pass/fail. Consider pass if all marks >= 35 (example rule).
    public String getResultStatus() {
        for (int m : marks) {
            if (m < 35) return "Fail";
        }
        return "Pass";
    }

    // Display details
    public void displayResult() {
        System.out.println("Roll Number: " + rollNumber);
        System.out.println("Student Name: " + studentName);
        System.out.print("Marks: ");
        for (int m : marks) System.out.print(m + " ");
        System.out.println();
        System.out.println("Average: " + calculateAverage());
        System.out.println("Result: " + getResultStatus());
    }

    public int getRollNumber() {
        return rollNumber;
    }
}
