package businesslogic.shifts;

import businesslogic.event.Event;
import businesslogic.event.Service;

import java.util.ArrayList;
import java.util.Date;

public class ShiftBoard {

    private Event event;
    private Service service;
    private ArrayList<Shift> shiftsToDo;


    public Event getEvent() {
        return event;
    }

    public Service getService() {
        return service;
    }

    public ShiftBoard(Event ev, Service serv) {

        this.event = ev;
        this.service = serv;
        this.shiftsToDo = new ArrayList<>();

    }

    public String toString() {

        String toReturn = "";
        toReturn = event.toString() +"\n" + service.toString() + "\n";
        for(Shift shift: shiftsToDo){

            toReturn.concat(shift.toString()+ "\n");

        }
        return toReturn;

    }

    public Shift getShift(int i) {return shiftsToDo.get(i);}

    public int tableSize() {return shiftsToDo.size();}

    public void addShift (Shift shift) {
        this.shiftsToDo.add(shift);
    }

    public ArrayList<Shift> getShifts() {
        return this.shiftsToDo;
    }







}
