package persistence;

import businesslogic.kitchen.*;

import businesslogic.recipe.KitchenJob;
import businesslogic.shifts.KitchenShift;

import java.util.ArrayList;

public class KitchenPersistence implements SummarySheetEventReceiver {


    @Override
    public void updateTaskCreated(SummarySheet summarySheet, Task tsk) {Task.taskCreated(summarySheet, tsk);}

    @Override
    public void updateSheetCreated(SummarySheet sumSheet) {SummarySheet.saveNewSummarySheet(sumSheet);}

    @Override
    public void updateModifiedTask(SummarySheet summarySheet, Task tsk) {Task.saveTaskEdited(tsk);}


    @Override
    public void updateKitchenJobAdded(SummarySheet summarySheet, KitchenJob kitchenJob) { SummarySheet.saveKitchenJobAdded(summarySheet, kitchenJob);}

    @Override
    public void updateTaskDeleted(Task tsk) {
        Task.deleteTask(tsk);
    }

    @Override
    public void listRearrang(SummarySheet summarySheet, ArrayList<KitchenJob> jobLists) { SummarySheet.saveKitchenJobPosition(summarySheet, jobLists);}

    @Override
    public void updateTaskReady(SummarySheet summarySheet, Task tsk) {Task.saveTaskCompleted(tsk);}

    @Override
    public void updateEstimateOrQuantity(SummarySheet summarySheet, Task tsk) {Task.saveQuantityOrEstimateAdded(tsk);}

}
