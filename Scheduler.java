import java.util.Scanner;

public class Scheduler {
    public void displaySchedule(StudentAthlete athlete) {
        System.out.println("\nCurrent Schedule:");
        for (String activity : athlete.schedule) {
            System.out.println("- " + activity);
        }
    }

    public void modifySchedule(Scanner scanner, StudentAthlete athlete) {
        System.out.println("\nWould you like to add or remove activities? (add/remove/done):");
        while (true) {
            System.out.print("Choice: ");
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("done")) break;

            if (choice.equalsIgnoreCase("add")) {
                System.out.print("Enter activity to add: ");
                String newActivity = scanner.nextLine();
                athlete.schedule.add(newActivity);
            } else if (choice.equalsIgnoreCase("remove")) {
                System.out.print("Enter activity to remove: ");
                String removeActivity = scanner.nextLine();
                athlete.schedule.remove(removeActivity);
            } else {
                System.out.println("Invalid option.");
            }
        }
    }
}
