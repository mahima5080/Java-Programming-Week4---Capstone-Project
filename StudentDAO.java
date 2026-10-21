import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public StudentDAO() {
        DatabaseConfig.initializeStorage();
    }

    public boolean addStudent(Student student) {
        List<Student> students = getAllStudents();
        int newId = students.isEmpty() ? 1 : students.get(students.size() - 1).getId() + 1;
        student.setId(newId);
        students.add(student);

        return saveAllStudents(students);
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        File file = new File(DatabaseConfig.FILE_NAME);
        if (!file.exists()) return students;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    String email = parts[2].trim();
                    String course = parts[3].trim();
                    double gpa = Double.parseDouble(parts[4].trim());

                    students.add(new Student(id, name, email, course, gpa));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("❌ Reading Error: " + e.getMessage());
        }
        return students;
    }

    public boolean updateStudent(Student updatedStudent) {
        List<Student> students = getAllStudents();
        boolean found = false;

        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == updatedStudent.getId()) {
                students.set(i, updatedStudent);
                found = true;
                break;
            }
        }

        if (found) {
            return saveAllStudents(students);
        }
        return false;
    }

    public boolean deleteStudent(int id) {
        List<Student> students = getAllStudents();
        boolean removed = students.removeIf(s -> s.getId() == id);

        if (removed) {
            return saveAllStudents(students);
        }
        return false;
    }

    private boolean saveAllStudents(List<Student> students) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DatabaseConfig.FILE_NAME))) {
            for (Student s : students) {
                writer.write(s.getId() + "," + s.getName() + "," + s.getEmail() + "," + s.getCourse() + "," + s.getGpa());
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            System.out.println("❌ Saving Error: " + e.getMessage());
            return false;
        }
    }
}