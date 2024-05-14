package Controller.GameController;

import Controller.Controller;
import java.util.Random;

public abstract class GameController<T> extends Controller {

    protected String state;

    protected int generateRandomNumber(int start, int end) {
        Random random = new Random();
        return start + random.nextInt(end - start);
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public abstract void setQuest();
    public abstract T getQuest();
    public abstract String processQuest(String input);
}
