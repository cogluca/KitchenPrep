package businesslogic.shifts;

import businesslogic.user.User;

import java.util.ArrayList;
import java.util.Date;

public class Block implements Shift{
    @Override
    public void setLocation() {

    }

    @Override
    public void setAvailability(User user) {

    }

    @Override
    public void setLocalDeadline(Date date) {

    }

    @Override
    public boolean isAvailable(User coo) {
        return false;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public int getAvailabilitiesSize() {
        return 0;
    }

    @Override
    public ArrayList<User> getAvailabilities() {
        return null;
    }


}
