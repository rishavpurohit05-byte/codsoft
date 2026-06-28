import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
class Student {
    private String name;
    private int rollNumber;
    private String grade;
    private int age;

    public Student(String name, int rollNumber, String grade, int age) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
        this.age = age;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getRollNumber() { return rollNumber; }
    public void setRollNumber(int rollNumber) { this.rollNumber = rollNumber; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    @Override
    public String toString() {
        return String.format("Name: %s | Roll No: %d | Grade: %s | Age: %d",
                name, rollNumber, grade, age);
    }
}

class StudentManagementSystem {
    private List<Student> students;
    private static final String FILE_NAME = "students.txt";

    public StudentManagementSystem() {
        students = new ArrayList<>();
        loadFromFile();
    }

    public boolean addStudent(Student s) {
        if (findByRollNumber(s.getRollNumber()) != null) {
            System.out.println("Error: Roll number already exists.");
            return false;
        }
        students.add(s);
        saveToFile();                  
        return true;
    }

    public boolean removeStudent(int rollNumber) {
        Student s = findByRollNumber(rollNumber);
        if (s == null) {
            System.out.println("Student not found.");
            return false;
        }
        students.remove(s);
        saveToFile();
        return true;
    }

    public Student searchStudent(int rollNumber) {
        return findByRollNumber(rollNumber);
    }

    public boolean editStudent(int rollNumber, String newName, String newGrade, int newAge) {
        Student s = findByRollNumber(rollNumber);
        if (s == null) {
            System.out.println("Student not found.");
            return false;
        }
        s.setName(newName);
        s.setGrade(newGrade);
        s.setAge(newAge);
        saveToFile();
        return true;
    }

    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students in the system.");
        } else {
            System.out.println("--- All Students ---");
            for (Student s : students) {
                System.out.println(s);
            }
        }
    }

    private Student findByRollNumber(int rollNumber) {
        for (Student s : students) {
            if (s.getRollNumber() == rollNumber) {
                return s;
            }
        }
        return null;
    }

    private void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    try {
                        String name = parts[0];
                        int roll = Integer.parseInt(parts[1]);
                        String grade = parts[2];
                        int age = Integer.parseInt(parts[3]);
                        students.add(new Student(name, roll, grade, age));
                    } catch (NumberFormatException e) {
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Student s : students) {
                writer.println(s.getName() + "," + s.getRollNumber() + ","
                        + s.getGrade() + "," + s.getAge());
            }
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }
}

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static StudentManagementSystem sms = new StudentManagementSystem();

    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            printMenu();
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1: addStudent(); break;
                case 2: removeStudent(); break;
                case 3: searchStudent(); break;
                case 4: sms.displayAllStudents(); break;
                case 5: editStudent(); break;
                case 6: exit = true; break;
                default: System.out.println("Invalid choice. Try again.");
            }
        }
        System.out.println("Exiting... Goodbye!");
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\nSTUDENT MANAGEMENT SYSTEM");
        System.out.println("1. Add Student");
        System.out.println("2. Remove Student");
        System.out.println("3. Search Student");
        System.out.println("4. Display All Students");
        System.out.println("5. Edit Student");
        System.out.println("6. Exit");
    }

    private static void addStudent() {
        System.out.println("--- Add New Student ---");
        String name = getNonEmptyString("Name: ");
        int roll = getPositiveInt("Roll Number: ");
        String grade = getNonEmptyString("Grade (e.g., A, B+): ");
        int age = getPositiveInt("Age: ");

        Student s = new Student(name, roll, grade, age);
        if (sms.addStudent(s)) {
            System.out.println("Student added successfully.");
        }
    }

    private static void removeStudent() {
        System.out.println("Remove Student");
        int roll = getPositiveInt("Enter roll number to remove: ");
        if (sms.removeStudent(roll)) {
            System.out.println("Student removed.");
        }
    }

    private static void searchStudent() {
        System.out.println("--- Search Student ---");
        int roll = getPositiveInt("Enter roll number to search: ");
        Student s = sms.searchStudent(roll);
        if (s != null) {
            System.out.println("Found: " + s);
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void editStudent() {
        System.out.println("--- Edit Student ---");
        int roll = getPositiveInt("Enter roll number of student to edit: ");
        Student existing = sms.searchStudent(roll);
        if (existing == null) {
            System.out.println("Student not found.");
            return;
        }
        System.out.println("Current details: " + existing);
        String newName = getNonEmptyString("New name (leave blank to keep '" + existing.getName() + "'): ");
        String newGrade = getNonEmptyString("New grade (leave blank to keep '" + existing.getGrade() + "'): ");
        int newAge = getPositiveIntOrKeep("New age (0 to keep " + existing.getAge() + "): ", existing.getAge());

        if (newName.isEmpty()) newName = existing.getName();
        if (newGrade.isEmpty()) newGrade = existing.getGrade();

        if (sms.editStudent(roll, newName, newGrade, newAge)) {
            System.out.println("Student updated.");
        }
    }

    private static String getNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("This field cannot be empty. Please try again.");
        }
    }

    private static int getPositiveInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value > 0) {
                    return value;
                }
                System.out.println("Please enter a positive number.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid integer. Please try again.");
            }
        }
    }

    private static int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a number.");
            }
        }
    }

    private static int getPositiveIntOrKeep(String prompt, int existing) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) return existing;
            try {
                int value = Integer.parseInt(input);
                if (value == 0) return existing;
                if (value > 0) return value;
                System.out.println("Please enter a positive number or 0 to keep current.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid integer. Try again.");
            }
        }
    }
}