import java.util.List;
import java.util.Scanner;

public class StudentManagementApp {
    private static StudentDAO dao = new StudentDAO();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice = -1;

        do {
            System.out.println("\n=============================================");
            System.out.println("   STUDENT MANAGEMENT SYSTEM (CAPSTONE)      ");
            System.out.println("=============================================");
            System.out.println("1. Add New Student");
            System.out.println("2. View All Students");
            System.out.println("3. Update Student Record");
            System.out.println("4. Delete Student Record");
            System.out.println("5. Exit");
            System.out.println("=============================================");
            System.out.print("Enter your choice (1-5): ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        addStudentView();
                        break;
                    case 2:
                        viewStudentsView();
                        break;
                    case 3:
                        updateStudentView();
                        break;
                    case 4:
                        deleteStudentView();
                        break;
                    case 5:
                        System.out.println("Exiting system. Goodbye! 👋");
                        break;
                    default:
                        System.out.println("❌ Invalid choice! Enter between 1 and 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Error: Invalid input format. Please enter a valid number.");
            }
        } while (choice != 5);

        scanner.close();
    }

    private static void addStudentView() {
        System.out.println("\n--- Add New Student ---");
        System.out.print("Enter Full Name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter Email Address: ");
        String email = scanner.nextLine().trim();

        System.out.print("Enter Course: ");
        String course = scanner.nextLine().trim();

        System.out.print("Enter GPA: ");
        double gpa = Double.parseDouble(scanner.nextLine());

        Student s = new Student(name, email, course, gpa);
        if (dao.addStudent(s)) {
            System.out.println("✅ Student added successfully!");
        } else {
            System.out.println("❌ Failed to add student.");
        }
    }

    private static void viewStudentsView() {
        List<Student> list = dao.getAllStudents();
        if (list.isEmpty()) {
            System.out.println("\nNo student records found in database.");
            return;
        }

        System.out.println("\n---------------------------------------------------------------------------------------------------");
        System.out.println("                                     STUDENT DIRECTORY                                            ");
        System.out.println("---------------------------------------------------------------------------------------------------");
        for (Student s : list) {
            System.out.println(s);
        }
        System.out.println("---------------------------------------------------------------------------------------------------");
    }

    private static void updateStudentView() {
        System.out.print("\nEnter Student ID to Update: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Updated Name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter Updated Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Enter Updated Course: ");
        String course = scanner.nextLine().trim();

        System.out.print("Enter Updated GPA: ");
        double gpa = Double.parseDouble(scanner.nextLine());

        Student s = new Student(id, name, email, course, gpa);
        if (dao.updateStudent(s)) {
            System.out.println("✅ Student record ID " + id + " updated successfully!");
        } else {
            System.out.println("❌ Student ID not found or update failed.");
        }
    }

    private static void deleteStudentView() {
        System.out.print("\nEnter Student ID to Delete: ");
        int id = Integer.parseInt(scanner.nextLine());

        if (dao.deleteStudent(id)) {
            System.out.println("✅ Student ID " + id + " deleted successfully.");
        } else {
            System.out.println("❌ Student ID not found or delete failed.");
        }
    }
}