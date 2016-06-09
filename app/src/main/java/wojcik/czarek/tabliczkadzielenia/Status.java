package wojcik.czarek.tabliczkadzielenia;

import java.util.*;

public class Status {

    private Level difficultLevel;
    private ArrayList<Question> questionList;
    private int currentQuestion = 0;


    public Status(Level difficultLevel) {
        this.difficultLevel = difficultLevel;
        questionList = new ArrayList<>();
    }

    public Level getDifficultLevel() {
        return difficultLevel;
    }

    public void setDifficultLevel(Level difficultLevel) {
        this.difficultLevel = difficultLevel;
    }
}
