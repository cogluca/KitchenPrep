import businesslogic.CatERing;
import businesslogic.UseCaseLogicException;
import businesslogic.event.Event;
import businesslogic.event.Service;
import businesslogic.kitchen.SummarySheet;
import businesslogic.kitchen.Task;
import businesslogic.recipe.KitchenJob;
import businesslogic.recipe.Recipe;
import businesslogic.shifts.KitchenShift;
import businesslogic.shifts.Shift;
import businesslogic.shifts.ShiftBoard;
import businesslogic.user.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TestUtils {

    public static int getRandomNumber(int min, int max) {
        return !randomValues ? 0 : (int) ((Math.random() * (max - min)) + min);
    }

    private static boolean randomValues = true;

    private static boolean printDebug = true;

    public static void setRandomValues(boolean randomValuesOn) {
        TestUtils.randomValues = randomValuesOn;
    }

    public static void setPrintDebug(boolean printDebug) {
        TestUtils.printDebug = printDebug;
    }


    public static User fakeLogin() {
        return fakeLogin("Lidia");
    }

    public static User fakeLogin(String user) {
        if (printDebug) System.out.println("---TEST FAKE LOGIN---");
        CatERing.getInstance().getUserManager().fakeLogin(user);
        return CatERing.getInstance().getUserManager().getCurrentUser();
    }


    public static SummarySheet createSummarySheet() throws UseCaseLogicException {
        if (printDebug) System.out.println("---TEST CREATE SUMMARY SHEET---");
        List<Event> events = CatERing.getInstance().getEventManager().getEventInfo();

        Event e = events.get(TestUtils.getRandomNumber(0, events.size()));

        Service s = e.getListOfServices().get(TestUtils.getRandomNumber(0, e.getListOfServices().size()));

        return CatERing.getInstance().getSumSheetManager().generateSummarySheet(e, s);
    }

    public static SummarySheet getSummarySheet() throws UseCaseLogicException {
        if (printDebug) System.out.println("---TEST GET SUMMARY SHEET---");
        boolean tempPrintDebugValue = printDebug;
        printDebug = false;
        SummarySheet sumSh = createSummarySheet();
        printDebug = tempPrintDebugValue;

        return CatERing.getInstance().getSumSheetManager().openSummarySheet(sumSh);
    }

    public static ArrayList<KitchenJob> addKitchenJob() throws UseCaseLogicException {
        if (printDebug) System.out.println("---TEST ADD KITCHEN JOB---");
        List<Recipe> recipes = Recipe.getAllRecipes();
        KitchenJob job = new KitchenJob(recipes.get(TestUtils.getRandomNumber(0, recipes.size())));

        return CatERing.getInstance().getSumSheetManager().addKitchenJob(job);
    }


    public static void rearrangeKitchenJob(KitchenJob job, int position) throws UseCaseLogicException {
        if (printDebug) System.out.println("---TEST SORT KITCHEN JOB---");
        CatERing.getInstance().getSumSheetManager().orderJobList(job, position, 0);
    }


    public static ShiftBoard checkShiftBoard() throws UseCaseLogicException {
        if (printDebug) System.out.println("---TEST SHIFTBOARD---");

        ShiftBoard shiftBoard = CatERing.getInstance().getSumSheetManager().getBoard();
        Date date = Calendar.getInstance().getTime();
        shiftBoard.addShift(new KitchenShift(date, 8, 10));
        shiftBoard.addShift(new KitchenShift(date, 10, 12));
        return shiftBoard;
    }


    public static Task createTask() throws UseCaseLogicException {
        if (printDebug) System.out.println("---TEST ASSIGN TASK---");

        boolean tempPrintDebugValue = printDebug;
        printDebug = false;
        ShiftBoard shiftBoard = checkShiftBoard();


        User Antonietta = User.loadUserById(6);
        User Paola = User.loadUserById(7);
        shiftBoard.getShift(0).setAvailability(Antonietta);
        shiftBoard.getShift(1).setAvailability(Paola);
        printDebug = tempPrintDebugValue;

        Shift workShift = shiftBoard.getShift(TestUtils.getRandomNumber(0, 1));

        List<KitchenJob> jobs = CatERing.getInstance().getSumSheetManager().currentSumSheet.getKitchenJobList();
        KitchenJob job = jobs.get(TestUtils.getRandomNumber(0, jobs.size()));

        User cook = workShift.getAvailabilities().get(TestUtils.getRandomNumber(0, workShift.getAvailabilitiesSize() - 1));

        return CatERing.getInstance().getSumSheetManager().addTask(job, workShift, cook);
    }


    public static void addTaskEstimateOrQuantity() throws UseCaseLogicException {
        if (printDebug) System.out.println("---TEST ADD TASK INFO---");
        List<Task> listOfTasks = CatERing.getInstance().getSumSheetManager().currentSumSheet.getTaskList();
        CatERing.getInstance().getSumSheetManager().addEstimateOrQuantity(listOfTasks.get(TestUtils.getRandomNumber(0, listOfTasks.size() - 1)), 30, TestUtils.getRandomNumber(1, 10));

    }

    public static void modifyTask(Task task) throws UseCaseLogicException {
        if (printDebug) System.out.println("---TEST EDIT TASK---");
        Shift aShift = CatERing.getInstance().getSumSheetManager().getBoard().getShift(TestUtils.getRandomNumber(0, 1));
        List<KitchenJob> jobs = CatERing.getInstance().getSumSheetMgr().currentSumSheet.getKitchenJobList();
        CatERing.getInstance().getSumSheetMgr().modifyTask(task, jobs.get(0).equals(task.getKitchenJob()) ? jobs.get(1) : jobs.get(0),
                aShift, aShift.getAvailabilities().get(TestUtils.getRandomNumber(0, aShift.getAvailabilitiesSize())));

    }

    public static void deleteTask() throws UseCaseLogicException {
        if (printDebug) System.out.println("---TEST DELETE TASK---");
        List<Task> tasks = CatERing.getInstance().getSumSheetManager().currentSumSheet.getTaskList();
        CatERing.getInstance().getSumSheetManager().deleteTask(tasks.get(getRandomNumber(0, tasks.size())));
    }

    public static void setTaskCompleted() throws UseCaseLogicException {
        if (printDebug) System.out.println("---TEST SET TASK COMPLETED---");
        List<Task> tasks = CatERing.getInstance().getSumSheetManager().currentSumSheet.getTaskList();

        CatERing.getInstance().getSumSheetManager().markTaskReady(tasks.get(getRandomNumber(0, tasks.size())));
    }


}
