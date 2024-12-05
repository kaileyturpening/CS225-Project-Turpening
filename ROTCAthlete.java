/*********************************************************
* Filename: <ROTCAthlete.java>
* Author: <Kailey Turpening>
* Collaborators: <N/A>
* Created: <11/30/2024>
* Modified: <N/A>
* Link: <N/A>
*
* Purpose: <Contains ROTC ONLY attributes and methods to add values to schedule arrays and text files.>
*
* Attributes: rank, rotcEvents
*
* Methods: ROTCAthlete(name, sport, rank), getRank(), setRank(rank), addROTCEvent(dayIndex, time)
* getROTCEvents(), saveDetailsToFile(filePath)
*********************************************************/
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ROTCAthlete extends StudentAthlete {
    private String rank;
    private ArrayList<String> rotcEvents = new ArrayList<>();

    public ROTCAthlete(String name, String sport, String rank) {
        super(name, sport);
        this.rank = rank;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public void addROTCEvent(int dayIndex, String time) throws SchedulerException {
        ArrayList<ArrayList<String>> sharedWeeklySchedule = super.getSharedWeeklySchedule();
        ArrayList<String> sharedDaySchedule = sharedWeeklySchedule.get(dayIndex);
    
        super.addTimeToSchedule(dayIndex, time, sharedWeeklySchedule);
    
        rotcEvents.add(time);
    }
    
    public ArrayList<String> getROTCEvents() {
        return rotcEvents;
    }

    @Override
    public void saveDetailsToFile(String filePath) throws SchedulerException {
        try (FileWriter writer = new FileWriter(filePath, true)) { // Append mode
            writer.write("Name: " + getName() + "\n");
            writer.write("Sport: " + getSport() + "\n");
            writer.write("Rank: " + rank + "\n");

            writer.write("ROTC Events:\n");
            for (String event : rotcEvents) {
                writer.write("- " + event + "\n");
            }
        } catch (IOException e) {
            throw new SchedulerException("Error saving details to file: " + e.getMessage());
        }
    }

}
