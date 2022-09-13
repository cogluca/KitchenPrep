import businesslogic.UseCaseLogicException;
import businesslogic.kitchen.SummarySheet;
import businesslogic.kitchen.Task;
import businesslogic.recipe.KitchenJob;
import businesslogic.shifts.Shift;
import businesslogic.shifts.ShiftBoard;
import businesslogic.user.User;

import java.util.ArrayList;

public class TestCatERing1a {

    public static void main(String[] args) {
        try {
            TestUtils.setRandomValues(true);
            TestUtils.setPrintDebug(false);

            // 0. FAKE LOGIN
            User u = TestUtils.fakeLogin();
            System.out.println(u + "\n");

            TestUtils.setPrintDebug(true);
            // 1a. GET SUMMARY SHEET
            SummarySheet gettedSheet = TestUtils.getSummarySheet();
            System.out.println(gettedSheet.sumInfotoString());

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


            // 6. ADD TASK INFO
            TestUtils.addTaskEstimateOrQuantity();
            System.out.println(task.toString());



        } catch (UseCaseLogicException e) {
            System.out.println("Errore di logica nello use case");
        }
    }
}
