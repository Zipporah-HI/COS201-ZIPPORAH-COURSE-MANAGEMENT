import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CourseManager {
    private static List<Course> courses = new ArrayList<>();
    private static final String FILE_NAME = "courses.txt";
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        System.out.println("STUDENT COURSE MANAGEMENT SYSTEM");

        while (running) { 
            System.out.println("\n1. Add Course");
            System.out.println("2. View All Courses");
            System.out.println("3. Search Course by Code");
            System.out.println("4. Compute Total Units");
            System.out.println("5. Save to File");
            System.out.println("6. Load from File");
            System.out.println("7. Exit Program");
            System.out.print("Select an option (1-7): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": addCourse(); break;
                case "2": viewAllCourses(); break;
                case "3": searchCourse(); break;
                case "4": 
                    int total = computeTotalUnitsRecursive(0);
                    System.out.println("\nTotal Credit Units: " + total);
                    break;
                case "5": saveToFile(); break;
                case "6": loadFromFile(); break;
                case "7": 
                    running = false; 
                    System.out.println("Exiting program... Goodbye!");
                    break;
                default: 
                    System.out.println("Invalid selection. Please enter a number between 1 and 7.");
            }
        }
    }

    private static void addCourse() {
        System.out.print("Enter Course Code (e.g., COS201): ");
        String code = scanner.nextLine();
        
        System.out.print("Enter Course Title: ");
        String title = scanner.nextLine();
        
        System.out.print("Enter Credit Units: ");
        try {
            int units = Integer.parseInt(scanner.nextLine().trim());
            if (units <= 0) throw new NumberFormatException();
            courses.add(new Course(code, title, units)); // Internal structure [cite: 16]
            System.out.println("Course added successfully!");
        } catch (NumberFormatException e) {
            System.out.println("Error: Units must be a valid positive number.");
        }
    }

    private static void viewAllCourses() {
        if (courses.isEmpty()) {
            System.out.println("No courses recorded yet.");
            return;
        }
        System.out.println("\n--- Recorded Courses ---");
        for (Course c : courses) {
            System.out.println(c.toString());
        }
    }

    private static void searchCourse() {
        System.out.print("Enter Course Code to search: ");
        String searchCode = scanner.nextLine().trim().toUpperCase();
        
        boolean found = false;
        for (Course c : courses) {
            if (c.getCourseCode().equals(searchCode)) {
                System.out.println("\nCourse Found:");
                System.out.println(c.toString());
                found = true;
                break;
            }
        }
        if (!found) System.out.println("Course not found.");
    }

    private static int computeTotalUnitsRecursive(int index) {
        if (index >= courses.size()) {
            return 0;
        }
        return courses.get(index).getUnit() + computeTotalUnitsRecursive(index + 1);
    }

    private static void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Course c : courses) {
                writer.println(c.getCourseCode() + "," + c.getCourseTitle() + "," + c.getUnit());
            }
            System.out.println("Courses saved successfully to " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    private static void loadFromFile() {
        courses.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    courses.add(new Course(parts[0], parts[1], Integer.parseInt(parts[2])));
                }
            }
            System.out.println("Courses loaded successfully from " + FILE_NAME);
        } catch (FileNotFoundException e) {
            System.out.println("No saved data found. Start by adding new courses.");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }
}