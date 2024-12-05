/*********************************************************
* Filename: <StudentAthlete.java>
* Author: <Kailey Turpening>
* Collaborators: <N/A>
* Created: <11/30/2024>
* Modified: <N/A>
* Link: <N/A>
*
* Purpose: <Contains attributes and methods to add values to schedule arrays and text files.>
*
* Attributes: name, sport, weeklyPracticeTimes, weeklyGameTimes, weeklyMeetingTimes, weeklyClassTimes, 
* weeklySleepTimes, sharedWeeklySchedule
*
* Methods: StudentAthlete(name, sport), getName(), getSport(), getSharedWeeklySchedule(), addPracticeTime(dayIndex, time)
* addGameTime(dayIndex, time), addMeetingTime(dayIndex, time), addClassTime(dayIndex, time), addSleepTime(dayIndex, bedTime, wakeUpTime)
* addTimeToSchedule(dayIndex, time, schedule), saveDetailsToFile(filePath), writeSchedule(writer, title, schedule)
* displaySchedule(), calculateSleepDuration(bedTime, wakeUpTime), printSchedule(title, schedule, daysOfWeek)
*********************************************************/
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class StudentAthlete {
    private String name;
    private String sport;
    private ArrayList<ArrayList<String>> weeklyPracticeTimes = new ArrayList<>();
    private ArrayList<ArrayList<String>> weeklyGameTimes = new ArrayList<>();
    private ArrayList<ArrayList<String>> weeklyMeetingTimes = new ArrayList<>();
    private ArrayList<ArrayList<String>> weeklyClassTimes = new ArrayList<>();
    private ArrayList<ArrayList<String>> weeklySleepTimes = new ArrayList<>();
    private ArrayList<ArrayList<String>> sharedWeeklySchedule = new ArrayList<>(); 

    public StudentAthlete(String name, String sport) {
        this.name = name;
        this.sport = sport;
        for (int i = 0; i < 7; i++) {
            weeklyPracticeTimes.add(new ArrayList<>());
            weeklyGameTimes.add(new ArrayList<>());
            weeklyMeetingTimes.add(new ArrayList<>());
            weeklyClassTimes.add(new ArrayList<>());
            weeklySleepTimes.add(new ArrayList<>());
            sharedWeeklySchedule.add(new ArrayList<>());
        }
    }

    public String getName() {
        return name;
    }

    public String getSport() {
        return sport;
    }

    public ArrayList<ArrayList<String>> getSharedWeeklySchedule() {
        return sharedWeeklySchedule;
    }
    
    public void addPracticeTime(int dayIndex, String time) throws SchedulerException {
        addTimeToSchedule(dayIndex, time, weeklyPracticeTimes);
        
    }

    public void addGameTime(int dayIndex, String time) throws SchedulerException {
        addTimeToSchedule(dayIndex, time, weeklyGameTimes);
        
    }

    public void addMeetingTime(int dayIndex, String time) throws SchedulerException {
        addTimeToSchedule(dayIndex, time, weeklyMeetingTimes);
        
    }

    public void addClassTime(int dayIndex, String time) throws SchedulerException {
        addTimeToSchedule(dayIndex, time, weeklyClassTimes);
        
    }

    public void addSleepTime(int dayIndex, String time) throws SchedulerException {
        addTimeToSchedule(dayIndex, time, weeklySleepTimes);
        
    }

    public void addTimeToSchedule(int dayIndex, String time, ArrayList<ArrayList<String>> schedule) throws SchedulerException {
        if (dayIndex < 0 || dayIndex >= 7) {
            throw new SchedulerException("Invalid day index.");
        }

        ArrayList<String> sharedDayTimes = sharedWeeklySchedule.get(dayIndex);
        if (sharedDayTimes.contains(time)) {
            throw new SchedulerException("Time overlaps with an existing entry on the same day.");
        }
        schedule.get(dayIndex).add(time);
        sharedDayTimes.add(time);
    }

    public void saveDetailsToFile(String filePath) throws SchedulerException {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write("Name: " + name + "\n");
            writer.write("Sport: " + sport + "\n\n");
    
            writeSchedule(writer, "Practice Times", weeklyPracticeTimes);
            writeSchedule(writer, "Game Times", weeklyGameTimes);
            writeSchedule(writer, "Meeting Times", weeklyMeetingTimes);
            writeSchedule(writer, "Class Times", weeklyClassTimes);
            writeSchedule(writer, "Sleep Times", weeklySleepTimes);
        } catch (IOException e) {
            throw new SchedulerException("Error saving details to file: " + e.getMessage());
        }
    }
    
    public void writeSchedule(FileWriter writer, String title, ArrayList<ArrayList<String>> schedule) throws IOException {
        writer.write(title + ":\n");
        for (int i = 0; i < schedule.size(); i++) {
            if (schedule.get(i).isEmpty()) {
                writer.write("Day " + (i + 1) + ": No entries\n");
            } else {
                writer.write("Day " + (i + 1) + ": " + String.join(", ", schedule.get(i)) + "\n");
            }
        }
        writer.write("\n");
    }
    
    public void displaySchedule() {
        String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        printSchedule("Weekly Practice Times", weeklyPracticeTimes, daysOfWeek);
        printSchedule("Weekly Game Times", weeklyGameTimes, daysOfWeek);
        printSchedule("Weekly Meeting Times", weeklyMeetingTimes, daysOfWeek);
        printSchedule("Weekly Class Times", weeklyClassTimes, daysOfWeek);
        printSchedule("Weekly Sleep Times", weeklySleepTimes, daysOfWeek);
    }

    private void printSchedule(String title, ArrayList<ArrayList<String>> schedule, String[] daysOfWeek) {
        System.out.println(title + ":");
        for (int i = 0; i < schedule.size(); i++) {
            ArrayList<String> times = schedule.get(i);
            if (!times.isEmpty()) {
                System.out.println(daysOfWeek[i] + ": " + times);
            }
        }
    }


    public void addSleepTime(int dayIndex, String bedTime, String wakeUpTime) throws SchedulerException {
        if (dayIndex < 0 || dayIndex >= 7) {
            throw new SchedulerException("Invalid day index.");
        }

        int sleepDuration = calculateSleepDuration(bedTime, wakeUpTime);
        if (sleepDuration < 6) {
            throw new SchedulerException("Sleep duration must be at least 6 hours.");
        }

        weeklySleepTimes.get(dayIndex).add("Bedtime: " + bedTime);
        weeklySleepTimes.get(dayIndex).add("Wake-up: " + wakeUpTime);

        sharedWeeklySchedule.get(dayIndex).add(bedTime + " (Bedtime)");
        sharedWeeklySchedule.get(dayIndex).add(wakeUpTime + " (Wake-up)");
    }

    public int calculateSleepDuration(String bedTime, String wakeUpTime) throws SchedulerException {
        try {
            int bed = Integer.parseInt(bedTime);
            int wake = Integer.parseInt(wakeUpTime);

            if (bed < 0 || bed > 2400 || wake < 0 || wake > 2400 || bed % 100 >= 60 || wake % 100 >= 60) {
                throw new SchedulerException("Invalid time format. Use military time (e.g., 2200 for 10 PM).");
            }
            int bedHours = bed / 100;
            int bedMinutes = bed % 100;
            int wakeHours = wake / 100;
            int wakeMinutes = wake % 100;

            int sleepMinutes = ((wakeHours * 60 + wakeMinutes) - (bedHours * 60 + bedMinutes));
            if (sleepMinutes < 0) {
                sleepMinutes += 24 * 60; 
            }
            return sleepMinutes / 60; 
        } catch (NumberFormatException e) {
            throw new SchedulerException("Invalid time input. Use military time (e.g., 2200 for 10 PM).");
        }
    }



}
