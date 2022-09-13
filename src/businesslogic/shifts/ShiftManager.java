package businesslogic.shifts;

import businesslogic.CatERing;
import businesslogic.event.Event;
import businesslogic.event.Service;

public class ShiftManager {

    private ShiftBoard currentShiftBoard;




    public ShiftManager() {

        this.currentShiftBoard = null;

    }

//TODO
    public ShiftBoard getBoard() {

        return this.currentShiftBoard;


    }

    public ShiftBoard createBoard(Event ev, Service serv) {

        ShiftBoard board = new ShiftBoard(ev, serv);
        setBoard(board);
        return board;
    }

    public void setBoard(ShiftBoard shiftBoard) {

        this.currentShiftBoard = shiftBoard;

    }






}
