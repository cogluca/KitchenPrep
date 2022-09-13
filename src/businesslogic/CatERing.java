package businesslogic;

import businesslogic.event.EventManager;
import businesslogic.kitchen.SummarySheetManager;
import businesslogic.menu.Menu;
import businesslogic.menu.MenuManager;
import businesslogic.recipe.RecipeManager;
import businesslogic.shifts.ShiftManager;
import businesslogic.user.UserManager;
import persistence.KitchenPersistence;
import persistence.MenuPersistence;
import persistence.PersistenceManager;

public class CatERing {
    private static CatERing singleInstance;

    public static CatERing getInstance() {
        if (singleInstance == null) {
            singleInstance = new CatERing();
        }
        return singleInstance;
    }

    private MenuManager menuMgr;
    private RecipeManager recipeMgr;
    private UserManager userMgr;
    private EventManager eventMgr;
    private SummarySheetManager sumSheetMgr;
    private ShiftManager shiftMngr;

    private MenuPersistence menuPersistence;
    private KitchenPersistence kitchenPersistence;

    public SummarySheetManager getSumSheetMgr() {
        return sumSheetMgr;
    }

    private CatERing() {
        menuMgr = new MenuManager();
        recipeMgr = new RecipeManager();
        userMgr = new UserManager();
        eventMgr = new EventManager();
        sumSheetMgr = new SummarySheetManager();
        menuPersistence = new MenuPersistence();
        kitchenPersistence = new KitchenPersistence();
        shiftMngr = new ShiftManager();

        menuMgr.addEventReceiver(menuPersistence);
        sumSheetMgr.addSumSheetReceiver(kitchenPersistence);
    }


    public MenuManager getMenuManager() {
        return menuMgr;
    }

    public RecipeManager getRecipeManager() {
        return recipeMgr;
    }

    public UserManager getUserManager() {
        return userMgr;
    }

    public SummarySheetManager getSumSheetManager() {return sumSheetMgr;}

    public EventManager getEventManager() { return eventMgr; }

    public ShiftManager getShiftManager() { return shiftMngr;}
}
