import businesslogic.UseCaseLogicException;
import businesslogic.kitchen.SummarySheet;
import businesslogic.kitchen.Task;
import businesslogic.recipe.KitchenJob;
import businesslogic.shifts.Shift;
import businesslogic.shifts.ShiftBoard;

import java.util.ArrayList;

public class TestCatERing5a {
    public static void main(String[] args) {
        try {

            TestUtils.setRandomValues(true);
            TestUtils.setPrintDebug(true);

            // 0. FAKE LOGIN
            TestUtils.fakeLogin();

            // 1. CREATE SUMMARY SHEET
            SummarySheet sumSh = TestUtils.createSummarySheet();
            System.out.println(sumSh.sumInfotoString() + "\n");


            // 2. ADD KITCHEN JOB
            ArrayList<KitchenJob> jobs;
            jobs = TestUtils.addKitchenJob();
            jobs = TestUtils.addKitchenJob();
            for(KitchenJob job : jobs) {
                System.out.println(job.toString() + "\n");
            }


            // 3. SORT KITCHEN JOB
            KitchenJob aJob = jobs.get(0);
            KitchenJob anotherJob = jobs.get(1);
            TestUtils.rearrangeKitchenJob(aJob, 1);

            for(KitchenJob job : jobs) {
                System.out.println(job.toString() + "\n");
            }


            // 4. CHECK BOARD
            ShiftBoard shiftBoard = TestUtils.checkShiftBoard();
            ArrayList<Shift> shiftsOnDisplay = shiftBoard.getShifts();
            for(Shift shift: shiftsOnDisplay) {
                System.out.println(shift.toString()+"\n");
            }

            // 5. ASSIGN TASK
            Task task = TestUtils.createTask();
            System.out.println(task.toString() + "\n");

            // 5a. EDIT TASK
            TestUtils.modifyTask(task);
            System.out.println(task.toString());


        } catch (UseCaseLogicException e) {
            System.out.println("Errore di logica nello use case");
        }
    }
}
