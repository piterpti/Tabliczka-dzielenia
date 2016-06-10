package wojcik.czarek.tabliczkadzielenia;

/**
 * Created by Piter on 09/06/2016.
 */
public class Achievement {
    public static final String A1 = "25 % poprawnych odpowiedzi!";
    public static final String A2 = "50 % poprawnych odpowiedzi!";
    public static final String A3 = "75 % poprawnych odpowiedzi!";
    public static final String A4 = "100 % poprawnych odpowiedzi!";

    private boolean isUnlocked = false;
    private int percent;
    private String name;

    public Achievement(String name, int percent) {
        this.name = name;
        this.percent = percent;
    }

    public boolean isUnlocked() {
        return isUnlocked;
    }

    public void setIsUnlocked(boolean isUnlocked) {
        this.isUnlocked = isUnlocked;
    }

    public static Achievement[] GET_ACHIEVEMENTS()
    {
        return new Achievement[]{
                new Achievement(A1, 25),
                new Achievement(A2, 50),
                new Achievement(A3, 75),
                new Achievement(A4, 100)
        };
    }

    public int getPercent() {
        return percent;
    }

    public String getName() {
        return name;
    }
}
