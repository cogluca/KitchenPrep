package businesslogic.kitchen;

import businesslogic.recipe.KitchenJob;
import businesslogic.shifts.KitchenShift;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface SummarySheetEventReceiver {

    public void updateTaskCreated (SummarySheet summarySheet, Task tsk);

    public void updateSheetCreated(SummarySheet sumSheet);

    public void updateModifiedTask (SummarySheet summarySheet, Task tsk);

    public void updateKitchenJobAdded(SummarySheet summarySheet, KitchenJob kitchenJob);

    public void updateTaskDeleted(Task tsk);

    public void listRearrang(SummarySheet summarySheet, ArrayList<KitchenJob> kitchenJobs);

    public void updateTaskReady(SummarySheet summarySheet, Task tsk);

    public void updateEstimateOrQuantity(SummarySheet summarySheet, Task tsk);

}
