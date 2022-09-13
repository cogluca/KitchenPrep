package businesslogic.kitchen;

import businesslogic.CatERing;
import businesslogic.recipe.KitchenJob;
import businesslogic.shifts.Shift;
import businesslogic.user.User;
import persistence.BatchUpdateHandler;
import persistence.PersistenceManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class Task {

    private Integer id;
    private User cook;
    private Shift kitchenShift;
    private KitchenJob kitchenJob;
    private boolean completed = false;

    public Integer getEstimate() {
        return estimate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    private Integer estimate;
    private Integer quantity;


    public void setEstimate(Integer estimate) {
        this.estimate = estimate;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


    public void setCompleted() {
        this.completed = true;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public Task(Shift kiS, KitchenJob kitJob, User coo) {

        this.id = 0;
        this.kitchenShift = kiS;
        this.kitchenJob = kitJob;
        if (coo != null && kiS.isAvailable(coo))
            this.cook = coo;
        this.estimate = 0;
        this.quantity = 0;
        this.completed = false;

    }

    public Integer getId() {
        return id;
    }

    public User getCook() {
        return cook;
    }

    public Shift getKitchenShift() {
        return kitchenShift;
    }

    public KitchenJob getKitchenJob() {
        return kitchenJob;
    }

    public Task addEstimateOrQuantity(Integer est, Integer qt) {
        if (est != -1) {
            this.estimate = est;
        }
        if (qt != -1) {
            this.quantity = qt;
        }
        return this;
    }

    public void markReady() {
        this.completed = true;
    }

    public Task modifyTask(KitchenJob kitchenJob, Shift shift, User coo) {
        if (kitchenJob != null)
            this.kitchenJob = kitchenJob;
        if (shift != null)
            this.kitchenShift = shift;
        if (coo != null && (this.kitchenShift.isAvailable(coo) || shift.isAvailable(coo)))
            this.cook = coo;
        return this;
    }


    public String toString() {

        String toReturn = "";
        if(estimate == 0 && quantity == 0)
            toReturn = kitchenShift.toString() + "\n" + kitchenJob.toString() + "\n" +  cook.toString() + "\n" + "completed: " + completed;
        else
            toReturn = kitchenShift.toString() + "\n" + kitchenJob.toString() + "\n" +  cook.toString() + "\n" +
                    "estimate: " + estimate.toString() + "\n" + "quantity: " + quantity.toString()+ "\n" + "completed: " + completed;

        return toReturn;
    }


    //Persistence methods//

    public static void deleteTask(Task tsk) {

        String delTsk = "DELETE FROM Task WHERE id = " + tsk.getId();
        PersistenceManager.executeUpdate(delTsk);

    }


    public static void taskCreated(SummarySheet summarySheet, Task tsk) {

        String tskToInsert = "";
        if (tsk.getCook() != null)
            tskToInsert = "INSERT INTO catering.Task (id, sumsheet_id, cook_id, kitchen_shift_id, kitchen_job_id) VALUES (?,?,?,?,?)";
        else
            tskToInsert = "INSERT INTO catering.Task (id, sumsheet_id, kitchen_shift_id, kitchen_job_id) VALUES (?,?,?,?)";

        int[] result = PersistenceManager.executeBatchUpdate(tskToInsert, 1, new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {

                ps.setInt(1, tsk.getId());
                ps.setInt(2, summarySheet.getId());
                if (tsk.getCook() != null) {
                    ps.setInt(3, tsk.getCook().getId());
                    ps.setInt(4, tsk.getKitchenShift().getId());
                    ps.setInt(5, tsk.getKitchenJob().getId());
                } else {
                    ps.setInt(3, tsk.getKitchenShift().getId());
                    ps.setInt(4, tsk.getKitchenJob().getId());
                }
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                if (count == 0) {
                    tsk.setId(rs.getInt(1));
                }
            }

        });


    }

    public static void saveTaskCompleted(Task task) {
        String upd = "UPDATE task SET ready = true WHERE id = " + task.getId();
        PersistenceManager.executeUpdate(upd);
    }

    public static void saveTaskDeleted(Task task) {
        String del = "DELETE FROM task WHERE id = " + task.getId();
        PersistenceManager.executeUpdate(del);
    }

    public static void saveTaskEdited(Task task) {
        String upd = "UPDATE task SET kitchen_shift_id = " + task.getKitchenShift().getId() + ", kitchen_job_id = " + task.getKitchenJob().getId() +
                " WHERE id = " + task.getId();
        PersistenceManager.executeUpdate(upd);

        upd = "UPDATE task SET cook_id = " + (task.getCook() == null ? -1 : task.getCook().getId())
                + " WHERE id = " + task.getId();
        PersistenceManager.executeUpdate(upd);
    }

    public static void saveQuantityOrEstimateAdded(Task task) {
        System.out.println(task.getId());
        String upd = "UPDATE task SET estimate = " + task.getEstimate() + ", quantity = " + task.getQuantity() + " WHERE id =" + task.getId();

        PersistenceManager.executeUpdate(upd);


    }


}
