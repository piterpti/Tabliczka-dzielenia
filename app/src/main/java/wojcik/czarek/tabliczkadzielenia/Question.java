package wojcik.czarek.tabliczkadzielenia;

import android.util.Log;

import java.util.*;

import wojcik.czarek.tabliczkadzielenia.Activities.MyActivity;

public class Question implements Comparable<Question> {

    private int id;
    private int first;
    private int second;
    private int mistakes;
    private boolean correctAnswer = false;

    public Question(int id, int first, int second, int mistakes) {
        this.id = id;
        this.first = first;
        this.second = second;
        this.mistakes = mistakes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int getMistakes() {
        return mistakes;
    }

    public void setMistakes(int mistakes) {
        this.mistakes = mistakes;
    }

    public boolean isCorrectAnswer() {
        return correctAnswer;
    }

    public String getCorrectAnswer()
    {
        int result = first / second;
        return String.valueOf(result);
    }

    public boolean CheckCorrectAnswer(String res)
    {
        if(res.equals(getCorrectAnswer()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void setCorrectAnswer(boolean correctAnswer)
    {
        this.correctAnswer = correctAnswer;
        if(correctAnswer)
        {
            mistakes--;
        }
        else
        {
            mistakes++;
        }
    }

    public static Question [] GET_QUESTIONS()
    {
        ArrayList<Question> questionList = new ArrayList<>();
        int counter = 0;
        for(int i = 1; i <= 100; i++)
        {
            for(int a = 1; a <= 100; a++)
            {
                if(i % a == 0 && i / a < 10) {
                    questionList.add(new Question(counter, i, a, 0));
                    counter++;
                }
            }
        }
        Question[] toReturn = new Question[questionList.size()];
        toReturn = questionList.toArray(toReturn);
        return toReturn;
    }

    public static Question[] GenerateQuestionForGame(int count)
    {
        Question[] toReturn = new Question[count];

        for(int i = 0; i < count; i++)
        {
            toReturn[i] = MyActivity.GAME_QUESTIONS[i];
        }

        ArrayList<Question> arrayList = new ArrayList<>(Arrays.asList(toReturn));
        Collections.shuffle(arrayList);
        toReturn = arrayList.toArray(toReturn);
        return toReturn;
    }

    @Override
    public String toString() {
        return first + " / " + second + " = ";
    }

    public String toStringDebug() {
        return first + " / " + second + " = " + " | " + mistakes;
    }

    @Override
    public int compareTo(Question another) {
        if(mistakes > another.mistakes)
            return -1;
        else if(mistakes == another.mistakes)
        {
            return 0;
        }
        else
            return 1;
    }
}
