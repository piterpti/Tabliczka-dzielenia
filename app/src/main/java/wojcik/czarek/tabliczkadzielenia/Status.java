package wojcik.czarek.tabliczkadzielenia;

import java.util.*;

import wojcik.czarek.tabliczkadzielenia.Activities.Achievement;

public class Status {

    private Level difficultLevel;
    private Question[] questionList;
    private int currentQuestion = 0;
    private Achievement[] unlockedAchievements;


    public Status(Level difficultLevel) {
        this.difficultLevel = difficultLevel;
    }

    public Level getDifficultLevel() {
        return difficultLevel;
    }

    public void setDifficultLevel(Level difficultLevel) {
        this.difficultLevel = difficultLevel;
    }

    public Question[] getQuestionList() {
        return questionList;
    }

    public void setQuestionList(Question[] questionList) {
        this.questionList = questionList;
    }

    public int getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(int currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public Question getTask()
    {
        if(currentQuestion - 1 < difficultLevel.getQuestionCount() )
            return questionList[currentQuestion - 1];
        else
            return questionList[0];
    }

    public boolean setToNextTaskIfExist()
    {
        currentQuestion++;
        if(currentQuestion - 1 >= difficultLevel.getQuestionCount() )
            return false;
        else
            return true;
    }

    public int getCorrectAnswers()
    {
        if(questionList.length < 1)
            return -1;
        int correctAnswers = 0;
        for(Question task : questionList) {
            if(task.isCorrectAnswer())
                correctAnswers++;
        }
        return correctAnswers;
    }

    public Achievement [] getUnlockedAchievements(int percent)
    {
        Achievement [] toReturn = Achievement.GET_ACHIEVEMENTS();
        for(Achievement a : toReturn) {
            if(a.getPercent() <= percent) {
                a.setIsUnlocked(true);
            }
        }
        return toReturn;
    }
}
