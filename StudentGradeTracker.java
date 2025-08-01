import java.util.*;

public class StudentGradeTracker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> grades = new ArrayList<>();

        System.out.print("Enter number of students: ");
        int n = scanner.nextInt();

        for (int i = 0; i < n; i++) {
            System.out.print("Enter grade for student " + (i + 1) + ": ");
            grades.add(scanner.nextInt());
        }

        int sum = 0, max = grades.get(0), min = grades.get(0);
        for (int grade : grades) {
            sum += grade;
            if (grade > max) max = grade;
            if (grade < min) min = grade;
        }

        double avg = (double) sum / grades.size();
        System.out.println("\n--- Grade Report ---");
        System.out.println("Average: " + avg);
        System.out.println("Highest: " + max);
        System.out.println("Lowest: " + min);
    }
}

Input
Enter number of students: 3
Enter grade for student 1: 85
Enter grade for student 2: 42
Enter grade for student 3: 93

--- Grade Report ---
Average: 73.33333333333333
Highest: 93
Lowest: 42

=== Code Execution Successful ===