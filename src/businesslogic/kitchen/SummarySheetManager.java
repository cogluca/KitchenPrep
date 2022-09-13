package businesslogic.kitchen;

import businesslogic.CatERing;
import businesslogic.UseCaseLogicException;
import businesslogic.event.Event;
import businesslogic.event.Service;
import businesslogic.recipe.KitchenJob;
import businesslogic.shifts.ShiftBoard;
import businesslogic.shifts.KitchenShift;
import businesslogic.shifts.Shift;
import businesslogic.user.User;

import java.util.ArrayList;

public class SummarySheetManager {

    public SummarySheet currentSumSheet;

    public ShiftBoard currentKitchenBoard;

    public ArrayList<SummarySheetEventReceiver> eventReceivers;

    public void addReceiver(SummarySheetEventReceiver receiver) {
        this.eventReceivers.add(receiver);
    }

    public SummarySheetManager() {
        eventReceivers = new ArrayList<>();
    }

    public SummarySheet generateSummarySheet(Event ev, Service serv) throws UseCaseLogicException {

        User user = CatERing.getInstance().getUserManager().getCurrentUser();

        if (user.isChef() && ev != null && serv != null && ev.hasService(serv)) {
            SummarySheet sumSheet = new SummarySheet(ev, serv);
            this.setCurrentSumSheet(sumSheet);
            this.notifySumSheetCreated(sumSheet);
            notifySumSheetCreated(sumSheet);
            return sumSheet;
        } else
            throw new UseCaseLogicException();
    }

    public void setCurrentSumSheet(SummarySheet sumSheet) {
        this.currentSumSheet = sumSheet;
    }

    public void setCurrentBoard(ShiftBoard kiSBoard) {
        this.currentKitchenBoard = kiSBoard;
    }

    public SummarySheetManager getSummarySheetManager() {
        return this;
    }


    public SummarySheet openSummarySheet(SummarySheet sumSheet) throws UseCaseLogicException {
        User usr = CatERing.getInstance().getUserManager().getCurrentUser();
        if (usr.isChef()) {
            this.setCurrentSumSheet(sumSheet);
        } else
            throw new UseCaseLogicException();

        return sumSheet;
    }

    public ArrayList<KitchenJob> addKitchenJob(KitchenJob job) throws UseCaseLogicException {

        User usr = CatERing.getInstance().getUserManager().getCurrentUser();
        if (usr.isChef() && this.currentSumSheet != null) {
            ArrayList<KitchenJob> toReturn = currentSumSheet.addKitchenJob(job);
            notifyKitchenJobToList(this.currentSumSheet, job);
            return toReturn;
        } else {
            throw new UseCaseLogicException();
        }

    }

    public ArrayList<KitchenJob> orderJobList(KitchenJob kitchenJob, int posWanted, int originalPos) throws UseCaseLogicException {
        User usr = CatERing.getInstance().getUserManager().getCurrentUser();
        if (usr.isChef() && this.currentSumSheet != null) {
            ArrayList<KitchenJob> jobsToNotify = this.currentSumSheet.orderJobLIst(posWanted, originalPos);
            notifyListRearranged(currentSumSheet, jobsToNotify);
            return jobsToNotify;
        } else {
            throw new UseCaseLogicException();
        }
    }


    public ShiftBoard getBoard() throws UseCaseLogicException {
        User usr = CatERing.getInstance().getUserManager().getCurrentUser();

        if (usr.isChef() && currentSumSheet != null) {
            ShiftBoard board = CatERing.getInstance().getShiftManager().getBoard();
            if (board != null) {
                this.setCurrentBoard(board);
            } else {
                board = CatERing.getInstance().getShiftManager().createBoard(currentSumSheet.getEvent(), currentSumSheet.getService());
                this.setCurrentBoard(board);
            }
            return board;
        } else
            throw new UseCaseLogicException();
    }

    public Task addTask(KitchenJob kitchenJob, Shift kitchenShift, User coo) throws UseCaseLogicException {
        User usr = CatERing.getInstance().getUserManager().getCurrentUser();
        if (currentSumSheet != null && usr.isChef() && coo.isCook()) {
            Task toReturn = currentSumSheet.addTask(kitchenJob, kitchenShift, coo);
            notifyTaskCreated(currentSumSheet, toReturn);
            return toReturn;
        } else
            throw new UseCaseLogicException();


    }

    public Task addEstimateOrQuantity(Task tsk, Integer est, Integer qt) throws UseCaseLogicException {

        User usr = CatERing.getInstance().getUserManager().getCurrentUser();
        if (tsk != null && usr.isChef()) {
            Task toNotify = currentSumSheet.addEstimateOrQuantity(tsk, est, qt);
            notifyEstimateTimeOrQuantity(currentSumSheet, toNotify);
            return tsk;
        } else
            throw new UseCaseLogicException();
    }

    public Task markTaskReady(Task tsk) throws UseCaseLogicException {

        User usr = CatERing.getInstance().getUserManager().getCurrentUser();
        if (currentSumSheet != null && usr.isChef()) {
            currentSumSheet.markTaskReady(tsk);
            notifyTaskReady(currentSumSheet, tsk);
            return tsk;
        } else
            throw new UseCaseLogicException();


    }

    public Task modifyTask(Task tsk, KitchenJob kitchenJob, Shift kitchenShift, User coo) throws UseCaseLogicException {
        User usr = CatERing.getInstance().getUserManager().getCurrentUser();
        System.out.println(coo.isCook());
        if (currentSumSheet != null && usr.isChef() && coo.isCook()) {

            Task toReturn = currentSumSheet.modifyTask(tsk, kitchenJob, coo, kitchenShift);
            notifyModifiedTask(currentSumSheet, tsk);
            return toReturn;

        } else
            throw new UseCaseLogicException();


    }

    public ArrayList<Task> deleteTask(Task tsk) throws UseCaseLogicException {
        User usr = CatERing.getInstance().getUserManager().getCurrentUser();
        if (currentSumSheet != null && usr.isChef()) {
            ArrayList<Task> toReturn = currentSumSheet.deleteTask(tsk);
            notifyTaskDeleted(currentSumSheet, tsk);
            return toReturn;
        } else
            throw new UseCaseLogicException();
    }

    //notifier
    public void addSumSheetReceiver(SummarySheetEventReceiver sumEvRec) {
        eventReceivers.add(sumEvRec);
    }

    public void removeSumSheetReceiver(SummarySheetEventReceiver sumEvRec) {
        eventReceivers.remove(sumEvRec);
    }

    public void notifySumSheetCreated(SummarySheet sumSheet) {
        for (SummarySheetEventReceiver er : eventReceivers) {
            er.updateSheetCreated(sumSheet);
        }
    }

    public void notifyTaskCreated(SummarySheet sumSheet, Task tsk) {

        for (SummarySheetEventReceiver er : eventReceivers) {
            er.updateTaskCreated(sumSheet, tsk);
        }

    }

    public void notifyModifiedTask(SummarySheet sumSheet, Task tsk) {
        for (SummarySheetEventReceiver er : eventReceivers) {
            er.updateModifiedTask(sumSheet, tsk);
        }
    }

    public void notifyKitchenJobToList(SummarySheet sumSheet, KitchenJob kitchenJob) {
        for (SummarySheetEventReceiver er : eventReceivers) {
            er.updateKitchenJobAdded(sumSheet, kitchenJob);
        }
    }


    public void notifyTaskDeleted(SummarySheet sumSheet, Task tsk) {
        for (SummarySheetEventReceiver er : eventReceivers) {
            er.updateTaskDeleted(tsk);
        }
    }

    public void notifyListRearranged(SummarySheet sumSheet, ArrayList<KitchenJob> kitchenJobList) {
        for (SummarySheetEventReceiver er : eventReceivers) {
            er.listRearrang(currentSumSheet, kitchenJobList);
        }
    }

    public void notifyTaskReady(SummarySheet sumSheet, Task tsk) {
        for (SummarySheetEventReceiver er : eventReceivers) {
            er.updateTaskReady(sumSheet, tsk);
        }
    }

    public void notifyEstimateTimeOrQuantity(SummarySheet sumSheet, Task tsk) {
        for (SummarySheetEventReceiver er : eventReceivers) {
            er.updateEstimateOrQuantity(sumSheet, tsk);
        }
    }

//methods


}
