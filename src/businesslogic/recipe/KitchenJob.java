package businesslogic.recipe;

import businesslogic.CatERing;
import businesslogic.kitchen.SummarySheet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.PersistenceManager;
import persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class KitchenJob {

    public int getId() {
        return id;
    }

    public Recipe getRecipe() {
        return rec;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id = 0;

    private Recipe rec;


    public KitchenJob(int id, Recipe rep) {
        this.id = id;
        this.rec = rep;

    }

    public KitchenJob(Recipe rec) {
        this.id = 0;
        this.rec = rec;


    }

    public String toString() {
        return rec.toString();
    }

    public static ArrayList<KitchenJob> loadKitchenJobs() {

        int currentSumSheetId = CatERing.getInstance().getSumSheetManager().currentSumSheet.getId();

        String query = "SELECT * from KitchenJobs WHERE summary_sheet_id = " + currentSumSheetId;
        ArrayList<KitchenJob> all = new ArrayList<>();

        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                int id = rs.getInt("job_id");
                int position = rs.getInt("position");
                int recipeId = rs.getInt("recipe_id");
                Recipe rec = Recipe.loadRecipeById(recipeId);

                KitchenJob job = new KitchenJob(id, rec);
                all.add(position, job);

            }
        });;

        return all;
    }


}
