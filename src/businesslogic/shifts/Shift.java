package businesslogic.shifts;

import businesslogic.user.User;

import java.util.ArrayList;
import java.util.Date;

public interface Shift {
    public void setLocation();
    public void setAvailability(User usr);
    public void setLocalDeadline(Date date);
    public boolean isAvailable(User coo);
    public int getId();
    public int getAvailabilitiesSize();


   public ArrayList<User> getAvailabilities();
}
