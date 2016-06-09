package wojcik.czarek.tabliczkadzielenia;

/**
 * Created by Piter on 09/06/2016.
 */
public class Level {
    private int id;
    private String levelName;
    private int questionCount;
    private int answerTime;
    boolean active;

    public Level(int id, String levelName, int questionCount, int answerTime) {
        this.id = id;
        this.levelName = levelName;
        this.questionCount = questionCount;
        this.answerTime = answerTime;
        active = false;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }

    public int getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(int answerTime) {
        this.answerTime = answerTime;
    }

    @Override
    public boolean equals(Object o) {
        Level diff = (Level) o;
        return diff.getId() == this.id ? true : false;
    }
}
