package wojcik.czarek.tabliczkadzielenia.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import wojcik.czarek.tabliczkadzielenia.Constants;
import wojcik.czarek.tabliczkadzielenia.Fragments.Authors;
import wojcik.czarek.tabliczkadzielenia.Fragments.DiffLevelFrag;
import wojcik.czarek.tabliczkadzielenia.Fragments.Game;
import wojcik.czarek.tabliczkadzielenia.Fragments.Menu;
import wojcik.czarek.tabliczkadzielenia.Fragments.Summary;
import wojcik.czarek.tabliczkadzielenia.Level;
import wojcik.czarek.tabliczkadzielenia.Question;
import wojcik.czarek.tabliczkadzielenia.R;
import wojcik.czarek.tabliczkadzielenia.Status;
import java.util.*;

public class MyActivity extends AppCompatActivity {

    public static int ALL_QUESTION_COUNT = 0;

    public static Level[] DIFFICULT_LEVELS;
    public static Status GAME_STATUS;
    public static Context CONTEXT;
    public static Question[] GAME_QUESTIONS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        Menu menu = new Menu();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        CONTEXT = this;
        if(savedInstanceState == null) {
            transaction.add(R.id.fragment_container, menu, Constants.MENU_TAG);
            transaction.addToBackStack(null);
            transaction.commit();
            init();
        }
    }

    private void init()
    {
        GAME_STATUS = new Status(null);
        LoadDifficultLevels();
        GAME_QUESTIONS = Question.GET_QUESTIONS();
        ArrayList<Question> arrayList = new ArrayList<>(Arrays.asList(GAME_QUESTIONS));
        Collections.shuffle(arrayList);
        GAME_QUESTIONS = arrayList.toArray(GAME_QUESTIONS);
    }

    public void NewGame(View view) {
        Game game = new Game();
        game.setRetainInstance(true);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, game, Constants.GAME_FRAGMENT_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
        init();
        game.CreateGame();
    }

    public void LevelSelect(View view) {
        DiffLevelFrag diffLevelFrag = new DiffLevelFrag();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, diffLevelFrag, Constants.DIFF_LEVEL_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void AuthorsInfo(View view) {
        Authors authors = new Authors();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, authors, Constants.AUTHORS_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        Menu menuFragment = null;
        menuFragment = (Menu) getSupportFragmentManager().findFragmentByTag(Constants.MENU_TAG);
        if(menuFragment != null && menuFragment.isVisible()) {
            System.exit(0);
        }
        boolean callSuper = true;
        Summary summary = null;
        summary = (Summary) getSupportFragmentManager().findFragmentByTag(Constants.SUMMARY);
        if(summary != null && summary.isVisible())
        {
            summary.BackToMenu();
            callSuper = false;
        }

        if(callSuper)
        {
            super.onBackPressed();
        }
    }

    public void Exit(View view) {
        System.exit(0);
    }

    private void LoadDifficultLevels()
    {
        String [] difficultLevelsArray = getResources().getStringArray(R.array.diffLevelArray);
        DIFFICULT_LEVELS = new Level[difficultLevelsArray.length];
        int counter = 0;
        for(String diffLevel : difficultLevelsArray) {
            String [] temp = diffLevel.split(",");
            DIFFICULT_LEVELS[counter] = new Level(Integer.valueOf(temp[0]), temp[1], Integer.valueOf(temp[2]), Integer.valueOf(temp[3]));
            counter++;
        }
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        int lastDiffLevelId = sharedPreferences.getInt(Constants.DIFF_LEVEL_TAG, 1);
        for(Level level : DIFFICULT_LEVELS) {
            if(level.getId() == lastDiffLevelId) {
                GAME_STATUS.setDifficultLevel(level);
                level.setActive(true);
            }
        }
    }
}
