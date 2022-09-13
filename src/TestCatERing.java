import businesslogic.UseCaseLogicException;
import businesslogic.kitchen.SummarySheet;
import businesslogic.kitchen.Task;
import businesslogic.recipe.KitchenJob;
import businesslogic.shifts.KitchenShift;
import businesslogic.shifts.Shift;
import businesslogic.shifts.ShiftBoard;
import businesslogic.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TestCatERing {

    public static void main(String[] args) {
        try {
            TestUtils.setRandomValues(true);
            TestUtils.setPrintDebug(true);

            // 0. FAKE LOGIN
            User u = TestUtils.fakeLogin();
            System.out.println(u + "\n");


            // 1. CREATE SUMMARY SHEET
            SummarySheet sumSh = TestUtils.createSummarySheet();
            System.out.println(sumSh.sumInfotoString() + "\n");


            // 2. ADD KITCHEN JOB
            ArrayList<KitchenJob> jobs;
            jobs = TestUtils.addKitchenJob();
            jobs = TestUtils.addKitchenJob();
            for(KitchenJob job : jobs) {
                System.out.println(job.toString());
            }
            System.out.println("\n");


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
            for(Shift shift: shiftsOnDisplay){
                System.out.println(shift.toString());
            }
            System.out.println("\n");

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
