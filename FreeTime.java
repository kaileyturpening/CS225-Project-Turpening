/*********************************************************
* Filename: <FreeTime.java>
* Author: <Kailey Turpening>
* Collaborators: <N/A>
* Created: <11/30/2024>
* Modified: <N/A>
* Link: <N/A>
*
* Purpose: <Serves similar purpose as StudentAthlete but only adds free time events.>
*
* Attributes: daysOfWeek, freeTimes
*
* Methods: FreeTime(), getSharedSchedule(studentAthlete), addFreeTime(dayIndex, time, studentAthlete)
* displaySchedule(), saveDetailsToFile(filePath), writeSchedule(writer, schedule)
*********************************************************/
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FreeTime {
    private String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    private ArrayList<ArrayList<String>> freeTimes = new ArrayList<>();

    public FreeTime() {
        // Initialize freeTimes for each day of the week
        if (freeTimes.isEmpty()) {
            for (int i = 0; i < 7; i++) {
                freeTimes.add(new ArrayList<>());
            }
        }
    }

    public ArrayList<ArrayList<String>> getSharedSchedule(StudentAthlete studentAthlete) {
        return studentAthlete.getSharedWeeklySchedule();
    }

    public void addFreeTime(int dayIndex, String time, StudentAthlete studentAthlete) throws SchedulerException {
        // Add the time to the shared schedule
        studentAthlete.addTimeToSchedule(dayIndex, time, freeTimes);
        saveDetailsToFile("student_athlete_details.txt"); // Save after adding free time
    }

    public void displaySchedule() {
        System.out.println("Free Times added:");
        for (int i = 0; i < freeTimes.size(); i++) {
            ArrayList<String> dayFreeTimes = freeTimes.get(i);
            if (!dayFreeTimes.isEmpty()) {
                System.out.println(daysOfWeek[i] + ": " + dayFreeTimes);
            }
        }
    }

    public void saveDetailsToFile(String filePath) throws SchedulerException {
        try (FileWriter writer = new FileWriter(filePath, true)) { // Append mode
            writer.write("Weekly Free Times:\n");
            writeSchedule(writer, freeTimes);
        } catch (IOException e) {
            throw new SchedulerException("Error saving details to file: " + e.getMessage());
        }
    }

    private void writeSchedule(FileWriter writer, ArrayList<ArrayList<String>> schedule) throws IOException {
        for (int i = 0; i < schedule.size(); i++) {
            writer.write(daysOfWeek[i] + ": " + schedule.get(i) + "\n");
        }
        writer.write("\n");
    }
}
