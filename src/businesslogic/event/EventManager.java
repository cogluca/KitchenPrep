package businesslogic.event;

import javafx.collections.ObservableList;

import java.util.ArrayList;

public class EventManager {

    public ArrayList<Event> getEventInfo() {
        ArrayList<Event> toReturn = Event.loadAllEventInfo();
        toReturn.size();
        return toReturn;
    }


}
