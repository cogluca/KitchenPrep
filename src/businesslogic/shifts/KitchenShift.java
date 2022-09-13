package businesslogic.shifts;

import businesslogic.user.User;

import java.util.ArrayList;
import java.util.Date;

public class KitchenShift implements Shift {

    private int id = 0;
    private Date date;
    private Date deadline;
    private int beginTime = 0;
    private int endTime = 0;
    private boolean lock = false;
    private ArrayList<User> availabilities;

    public int getId() {
        return id;
    }

    public KitchenShift(Date date, int beginTime, int endTime) {

        this.date = date;
        this.beginTime = beginTime;
        this.endTime = endTime;
        availabilities = new ArrayList<>();


    }

    public String toString() {
        return "date: " + date.getDate() + "\n beginTime: "+beginTime+ "\n endTime: "+ endTime;
    }


    @Override
    public int getAvailabilitiesSize() {
        return this.availabilities.size() ;
    }

    @Override
    public ArrayList<User> getAvailabilities() {
        return this.availabilities;
    }


    @Override
    public void setLocation() {

    }

    @Override
    public void setAvailability(User user) {
        this.availabilities.add(user);
    }

    @Override
    public void setLocalDeadline(Date date) {

    }

    @Override
    public boolean isAvailable(User coo) {
        for(User usrToSort: this.availabilities){
            if(usrToSort.equals(coo))
                return true;
        }
        return false;
    }
}
