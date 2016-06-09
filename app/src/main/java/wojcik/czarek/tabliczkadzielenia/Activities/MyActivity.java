package wojcik.czarek.tabliczkadzielenia.Activities;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import wojcik.czarek.tabliczkadzielenia.Constants;
import wojcik.czarek.tabliczkadzielenia.Fragments.Menu;
import wojcik.czarek.tabliczkadzielenia.Fragments.Summary;
import wojcik.czarek.tabliczkadzielenia.R;

public class MyActivity extends AppCompatActivity {

    public static Context CONTEXT;

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
        }
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
            //summary.BackToMenu();
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
}
