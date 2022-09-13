package businesslogic.kitchen;

import businesslogic.CatERing;
import businesslogic.UseCaseLogicException;
import businesslogic.event.Event;
import businesslogic.event.Service;
import businesslogic.recipe.KitchenJob;
import businesslogic.shifts.Shift;
import businesslogic.user.User;
import persistence.BatchUpdateHandler;
import persistence.PersistenceManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SummarySheet {

    private Event event;
    private Service service;

    private ArrayList<KitchenJob> kitchenJobList;
    private ArrayList<Task> taskList;
    private Object UseCaseLogicException;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public Event getEvent() {

        return event;
    }

    public Service getService() {
        return service;
    }

    public SummarySheet(Event ev, Service serv) {

        this.event = ev;
        this.service = serv;

        this.kitchenJobList = new ArrayList<>();
        this.taskList = new ArrayList<>();
    }

    public ArrayList<KitchenJob> getKitchenJobList() {
        return kitchenJobList;
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public Task addTask(KitchenJob kitchenJob, Shift kiS, User coo) {

        Task taskToAdd = new Task(kiS, kitchenJob, coo);
        taskList.add(taskToAdd);
        return taskToAdd;
    }


    public Task markTaskReady(Task tsk) {
        tsk.markReady();
        return tsk;
    }


    public ArrayList<KitchenJob> addKitchenJob(KitchenJob obj) {
        kitchenJobList.add(obj);
        return kitchenJobList;
    }

    public ArrayList<KitchenJob> orderJobLIst(int posWanted, int originalPosition) throws UseCaseLogicException {

        Collections.swap(kitchenJobList, posWanted, originalPosition);
        return kitchenJobList;


    }

    public int getJobPosition(KitchenJob job) {
        for (KitchenJob jobToFind : kitchenJobList) {
            if (job.getId() == jobToFind.getId())
                return kitchenJobList.indexOf(jobToFind);
        }
        return 0;


    }

    public ArrayList<Task> deleteTask(Task task) throws UseCaseLogicException {
        taskList.remove(task);
        return taskList;
    }


    public Task modifyTask(Task tsk, KitchenJob kitchenJob, User coo, Shift kiS) throws UseCaseLogicException {

        Task toReturn = tsk.modifyTask(kitchenJob, kiS, coo);
        return toReturn;


    }

    public Task addEstimateOrQuantity(Task tsk, Integer est, Integer qt) throws UseCaseLogicException {

        User usr = CatERing.getInstance().getUserManager().getCurrentUser();
        tsk.addEstimateOrQuantity(est, qt);
        return tsk;

    }


    /* Persistence methods */


    public static void saveNewSummarySheet(SummarySheet summarySheet) {

        String summarySheetInsert = "INSERT INTO catering.summarysheet (event_id, service_id) VALUES (?, ?)";
        PersistenceManager.executeBatchUpdate(summarySheetInsert, 1, new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {

                ps.setInt(1, summarySheet.event.getId());
                ps.setInt(2, summarySheet.service.getId());
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                if (count == 0) {
                    summarySheet.id = rs.getInt(1);
                }
            }
        });

    }


    public static void saveKitchenJobAdded(SummarySheet summarySheet, KitchenJob job) {

        String summarySheetInsert = "INSERT INTO catering.KitchenJobs (recipe_id, summary_sheet_id, position) VALUES (?, ?, ?)";
        PersistenceManager.executeBatchUpdate(summarySheetInsert, 1, new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setInt(1, job.getRecipe().getId());
                ps.setInt(2, summarySheet.getId());
                ps.setInt(3, summarySheet.getJobPosition(job));
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                if (count == 0) {
                    job.setId(rs.getInt(1));
                }
            }
        });

    }

    public static void saveKitchenJobPosition(SummarySheet summarySheet, ArrayList<KitchenJob> rearrangedList) {
        int listSize = rearrangedList.size();
        for (KitchenJob job : rearrangedList) {
            String upd = "UPDATE catering.KitchenJobs SET recipe_id=" + job.getRecipe().getId() + ", summary_sheet_id=" + summarySheet.getId() + ",position=" + listSize + " WHERE job_id =" + job.getId();
            PersistenceManager.executeUpdate(upd);
            listSize--;
        }
    }


    public String sumInfotoString() {
        return event.toString() + "\n" + service.toString() +"\n";

    }

    public String jobsToString() {

        String toReturn = "";

        for ( KitchenJob kitJob: kitchenJobList) {
            toReturn.concat(kitJob.toString() + "\n");

        }

        return toReturn;

    }


}
