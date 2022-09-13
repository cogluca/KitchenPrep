package businesslogic.event;

import businesslogic.user.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.PersistenceManager;
import persistence.ResultHandler;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Event implements EventItemInfo {

    private int id;
    private User owner;
    private String name;
    private ArrayList<Service> listOfServices;
    private Date dateStart;
    private Date dateEnd;
    private int participants;
    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getOwner(){
        return this.owner;
    }

    public User getOrganizer() {
        return organizer;
    }

    private User organizer;

    public Event(String name) {
        this.name = name;
        this.listOfServices = new ArrayList<>();
        id = 0;
    }

    public int getId() {
        return id;
    }
    public void addService(Service serv) {
        listOfServices.add(serv);
    }

    public boolean hasService(Service serv){

        for(Service toSearch: this.listOfServices){
            if(toSearch.equals(serv))
                return true;
        }
        return false;

    }

    public ArrayList<Service> getListOfServices() {
        return this.listOfServices;
    }

    public String toString() {
        return name + ": " + dateStart + "-" + dateEnd + ", " + participants + " pp. (" + organizer.getUserName() + ")";
    }

    public String getOrganizerUsername() {
        return organizer.getUserName();
    }

    public String getDateStart() {
        return dateStart.toString();
    }

    public int getEventId() {
        return id;
    }

    public int getParticipants() {
        return participants;
    }

    public String getEventName() {return name;}

    // STATIC METHODS FOR PERSISTENCE

    public static ArrayList<Event> loadAllEventInfo() {
        ArrayList<Event> all = new ArrayList<>();
        String query = "SELECT * FROM Events WHERE true";
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                String n = rs.getString("name");
                Event e = new Event(n);
                e.id = rs.getInt("id");
                e.dateStart = rs.getDate("date_start");
                e.dateEnd = rs.getDate("date_end");
                e.participants = rs.getInt("expected_participants");
                int org = rs.getInt("organizer_id");
                e.organizer = User.loadUserById(org);
                all.add(e);
            }
        });

        for (Event e : all) {
            e.listOfServices = Service.loadServiceInfoForEvent(e.id);
        }

        return all;
    }
}
