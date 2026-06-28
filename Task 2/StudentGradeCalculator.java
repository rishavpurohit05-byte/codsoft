import java.util.Scanner;

public class StudentGradeCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Student Grade Calculator");
        int numSubjects = 0;
        while (true) {
            System.out.print("Enter the number of subjects: ");
            if (scanner.hasNextInt()) {
                numSubjects = scanner.nextInt();
                if (numSubjects > 0) {
                    break;
                } else {
                    .out.println("Please enter a positive number.");
                }
            } else {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.next();
            }
        }
        
        double totalMarks = 0;

        for (int i = 1; i <= numSubjects; i++) {
            while (true) {
                System.out.print("Enter marks obtained in subject " + i + " (out of 100): ");
                if (scanner.hasNextDouble()) {
                    double marks = scanner.nextDouble();
                    if (marks >= 0 && marks <= 100) {
                        totalMarks += marks;
                        break;
                    } else {
                        System.out.println("Marks must be between 0 and 100. Try again.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next();
                }
            }
        }
        
        double averagePercentage = (totalMarks / (numSubjects * 100)) * 100;
        
        String grade;
        if (averagePercentage >= 90) {
            grade = "A+";
        } else if (averagePercentage >= 80) {
            grade = "A";
        } else if (averagePercentage >= 70) {
            grade = "B";
        } else if (averagePercentage >= 60) {
            grade = "C";
        } else if (averagePercentage >= 50) {
            grade = "D";
        } else {
            grade = "F";
        }

        System.out.println("\nResults:-");
        System.out.println("Total Marks (out of " + (numSubjects * 100) + "): " + totalMarks);
        System.out.println("Average Percentage: " + averagePercentage + "%");
        System.out.println("Grade: " + grade);
        
        if (grade.equals("A+")) {
            System.out.println("Remarks: Excellent!");
        } else if (grade.equals("A")) {
            System.out.println("Remarks: Very Good!");
        } else if (grade.equals("B")) {
            System.out.println("Remarks: Good!");
        } else if (grade.equals("C")) {
            System.out.println("Remarks: Fair!");
        } else if (grade.equals("D")) {
            System.out.println("Remarks: Needs Improvement!");
        } else {
            System.out.println("Remarks: Too Bad!");
        }
        
        scanner.close();
    }
}