/*********************************************************
* Filename: <SchedulerApp.java>
* Author: <Kailey Turpening>
* Collaborators: <N/A>
* Created: <11/30/2024>
* Modified: <N/A>
* Link: <N/A>
*
* Purpose: <Includes all user input and output for a user creating Student Athlete schedule.>
*
* Attributes: NONE
*
* Methods: main(args), firstPart(sc), start(sc), startActualSchedule(sc, athlete), startFreeTimeSchedule(sc, athlete)
* printSchedule(daysOfWeek, schedule), changeToInt(value), reRun(sc, athlete, free)
* promptAndAddPracticeTime(sc, athlete, dayIndex, currentDay),promptAndAddGameTime(sc, athlete, dayIndex, currentDay)
* promptAndAddMeetingTime(sc, athlete, dayIndex, currentDay), promptAndAddClassTime(sc, athlete, dayIndex, currentDay)
* promptAndAddSleepTime(sc, athlete, dayIndex, currentDay), promptAndAddROTCEvent(sc, athlete, dayIndex, currentDay)
*********************************************************/
import java.util.ArrayList;
import java.util.Scanner;

public class SchedulerApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SchedulerApp app = new SchedulerApp();
        app.firstPart(sc);
        sc.close();
    }
        
    public void firstPart(Scanner sc) {
        String isAthlete = "";
        while (!isAthlete.equalsIgnoreCase("yes") && !isAthlete.equalsIgnoreCase("no")) {
            System.out.print("Are you a student athlete? (yes/no): ");
            isAthlete = sc.nextLine();
        
            if (isAthlete.equalsIgnoreCase("yes")) {
                start(sc); 
            } else if (isAthlete.equalsIgnoreCase("no")) {
                System.out.println("You will not need a student athlete schedule. Goodbye.");
            } else {
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            }
        }
    }
        
    public void start(Scanner sc) {
        String filePath = "student_athlete_details.txt"; 
        
        System.out.print("Are you also in ROTC? (yes/no): ");
        String isROTC = sc.nextLine();
        
        while (!isROTC.equalsIgnoreCase("yes") && !isROTC.equalsIgnoreCase("no")) {
            System.out.print("Invalid input. Are you also in ROTC? (yes/no): ");
            isROTC = sc.nextLine();
        }
        
        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        while (name.isEmpty()) {
            System.out.println("Invalid input. Enter your name: ");
            name = sc.nextLine();
        }
        
        System.out.print("Enter your sport: ");
        String sport = sc.nextLine();
        while (sport.isEmpty()) {
            System.out.println("Invalid input. Enter your sport: ");
            sport = sc.nextLine();
        }
        
        try {
            StudentAthlete athlete;
            //POLYMORPHISM
            if (isROTC.equalsIgnoreCase("yes")) {
                System.out.print("Enter your rank: ");
                String rank = sc.nextLine();
                while (rank.isEmpty()) {
                System.out.println("Invalid input. Enter your rank: ");
                    rank = sc.nextLine();}
                athlete = new ROTCAthlete(name, sport, rank);} 
                
            else {
                athlete = new StudentAthlete(name, sport);
                athlete.saveDetailsToFile(filePath);}
            
            System.out.println("Details saved successfully!");
            
            startActualSchedule(sc, athlete);} 
            catch (SchedulerException e) {
                System.err.println("Error saving details");}     
    }
    
    public void startActualSchedule(Scanner sc, StudentAthlete athlete) {
        String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        int dayIndex = 0;
        while (dayIndex < daysOfWeek.length) {
            String currentDay = daysOfWeek[dayIndex];
            System.out.println("This schedule will go in order of the week. Now you are adding events to " + currentDay + ".");
            System.out.println("""
                    What would you like to add? (enter 1-8)
                    1. Practice times
                    2. Game times
                    3. Meeting times
                    4. Class times
                    5. Sleep times
                    6. ROTC Events (if applicable)
                    7. Move to the next day
                    8. Exit
                    """);
            String userAnswerString = sc.nextLine();
            try {
                int userAnswer = Integer.parseInt(userAnswerString);
                if (userAnswer < 1 || userAnswer > 8) {
                    System.out.println("Invalid choice. Please enter a number between 1 and 8.");
                    continue;}
                switch (userAnswer) {
                    case 1:
                        promptAndAddPracticeTime(sc, athlete, dayIndex, currentDay);
                        break;
                    case 2:
                        promptAndAddGameTime(sc, athlete, dayIndex, currentDay);
                        break;
                    case 3:
                        promptAndAddMeetingTime(sc, athlete, dayIndex, currentDay);
                        break;
                    case 4:
                        promptAndAddClassTime(sc, athlete, dayIndex, currentDay);
                        break;
                    case 5:
                        promptAndAddSleepTime(sc, athlete, dayIndex, currentDay);
                        break;
                    case 6:
                        if (athlete instanceof ROTCAthlete) {
                            promptAndAddROTCEvent(sc, (ROTCAthlete) athlete, dayIndex, currentDay);
                        } else {
                            System.out.println("This option is not applicable as you are not in ROTC.");
                        }
                        break;
                    case 7:
                        dayIndex++;
                        break;
                    case 8:
                        System.out.println("Exiting Schedule Creation.");
                        athlete.displaySchedule();
                        startFreeTimeSchedule(sc, athlete);
                        return;
                    default:
                        System.out.println("Invalid input. Please try again.");
                        break;}
                if (dayIndex >= daysOfWeek.length) {
                    System.out.println("You have completed your weekly schedule setup.");
                    athlete.displaySchedule();
                    startFreeTimeSchedule(sc, athlete);
                    return;}
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number from 1 to 8.");}
        }
    }
    
    public void promptAndAddPracticeTime(Scanner sc, StudentAthlete athlete, int dayIndex, String currentDay) {
        System.out.println("Enter practice time for " + currentDay + " in military time (e.g., 1900 means 7 pm): ");
        String time = sc.nextLine();
        changeToInt(time);
        try {
            athlete.addPracticeTime(dayIndex, time);
            athlete.saveDetailsToFile("student_athlete_details.txt");
        } catch (SchedulerException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public void promptAndAddGameTime(Scanner sc, StudentAthlete athlete, int dayIndex, String currentDay) {
        System.out.println("Enter game time for " + currentDay + " in military time (e.g., 1900 means 7 pm): ");
        String time = sc.nextLine();
        changeToInt(time);
        try {
            athlete.addGameTime(dayIndex, time);
            athlete.saveDetailsToFile("student_athlete_details.txt");
        } catch (SchedulerException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public void promptAndAddMeetingTime(Scanner sc, StudentAthlete athlete, int dayIndex, String currentDay) {
        System.out.println("Enter meeting time for " + currentDay + " in military time (e.g., 1900 means 7 pm): ");
        String time = sc.nextLine();
        changeToInt(time);
        try {
            athlete.addMeetingTime(dayIndex, time);
            athlete.saveDetailsToFile("student_athlete_details.txt");
        } catch (SchedulerException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public void promptAndAddClassTime(Scanner sc, StudentAthlete athlete, int dayIndex, String currentDay) {
        System.out.println("Enter class time for " + currentDay + " in military time (e.g., 1900 means 7 pm): ");
        String time = sc.nextLine();
        changeToInt(time);
        try {
            athlete.addClassTime(dayIndex, time);
            athlete.saveDetailsToFile("student_athlete_details.txt");
            System.out.println("Class time added.");
        } catch (SchedulerException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public void promptAndAddROTCEvent(Scanner sc, ROTCAthlete athlete, int dayIndex, String currentDay) {
        System.out.println("Enter ROTC event time for " + currentDay + " in military time (e.g., 1900 means 7 pm): ");
        String time = sc.nextLine();
        changeToInt(time);
        try {
            athlete.addROTCEvent(dayIndex, time);
            athlete.saveDetailsToFile("student_athlete_details.txt");
        } catch (SchedulerException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public void promptAndAddSleepTime(Scanner sc, StudentAthlete athlete, int dayIndex, String currentDay) {
        System.out.println("Enter bedtime for " + currentDay + " in military time (e.g., 2200 for 10 PM): ");
        String bedTime = sc.nextLine();
        changeToInt(bedTime);
    
        System.out.println("Enter wake-up time in military time (e.g., 0700 for 7 AM): ");
        String wakeUpTime = sc.nextLine();
        changeToInt(wakeUpTime);
    
        try {
            athlete.addSleepTime(dayIndex, bedTime, wakeUpTime);
            athlete.saveDetailsToFile("student_athlete_details.txt");
        } catch (SchedulerException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public void startFreeTimeSchedule(Scanner sc, StudentAthlete athlete) {
        FreeTime free = new FreeTime(); // Create a new FreeTime object
        String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        int dayIndex = 0;
        
        while (dayIndex < daysOfWeek.length) {
            String currentDay = daysOfWeek[dayIndex];
            System.out.println("The purpose of this section is to add typically unscheduled events into your schedule so you can manage your time easier. Events such as meal prep, studying, doing homework, socializing, team building/bonding, etc. should be added in this section.");
            System.out.println("This free time schedule will go in order of the week. Now you are adding events to " + currentDay + ".");
            System.out.println("""
                    Select which option you would like (enter 1-3):
                    1. Free time entry
                    2. Move to the next day
                    3. Exit
                    """);

            String userInputString = sc.nextLine();

            try {
                int userInput = Integer.parseInt(userInputString);

                if (userInput < 1 || userInput > 3) {
                    System.out.println("Invalid choice. Please enter a number between 1 and 3.");
                    continue;
                }

                switch (userInput) {
                    case 1: {
                        System.out.println("Enter time for free time activity for " + currentDay + " in military time (e.g., 1900 means 7 pm): ");
                        String freeTime = sc.nextLine();
                        changeToInt(freeTime); // Convert input to integer (optional validation step)

                        try {
                            free.addFreeTime(dayIndex, freeTime, athlete); // Access the FreeTime method to add an event
                        } catch (SchedulerException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    }
                    
                    case 2: {
                        System.out.println("Moving to the next day...");
                        dayIndex++;
                        if (dayIndex >= 7) {
                            free.displaySchedule(); 
                            reRun(sc, athlete, free);
                        }
                        break;
                    }
                    case 3: {
                        System.out.println("Exiting Schedule Creation.");
                        free.displaySchedule(); 
                        reRun(sc, athlete, free);
                        return;
                    }
                }

                if (dayIndex >= daysOfWeek.length) {
                    System.out.println("You have completed your weekly schedule setup.");
                    free.displaySchedule();
                    return;
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number from 1 to 3.");
            }
        }
    }

    public void printSchedule(String[] daysOfWeek, ArrayList<ArrayList<String>> schedule) {
        for (int i = 0; i < daysOfWeek.length; i++) {
            System.out.println(daysOfWeek[i] + ": " + schedule.get(i));
        }
    }

    public void changeToInt(String value){
        try {
            int intValue= Integer.parseInt(value);
            if (intValue < 0 || intValue >2400){
                System.out.println("Error, invalid entry time.");
            }
        } catch (Exception e) {
            System.out.println("Error, invalid entry time.");
        }
    }

    public void reRun(Scanner sc, StudentAthlete athlete, FreeTime free){
        String ans="";
        while(ans.isEmpty()){
        System.out.println("Would you like to add more to your schedule? (yes/no)");
        ans = sc.nextLine();
        if (ans.equalsIgnoreCase("yes")) {
            startActualSchedule(sc, athlete); 
        } else if (ans.equalsIgnoreCase("no")) {
            System.out.println("You are done with creating a schedule. Goodbye.");
            athlete.displaySchedule();
            free.displaySchedule();
        } else {
            System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            ans="";
        }
        }

    }   
    
}

