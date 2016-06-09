package wojcik.czarek.tabliczkadzielenia.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import wojcik.czarek.tabliczkadzielenia.Activities.MyActivity;
import wojcik.czarek.tabliczkadzielenia.Adapters.LevelListAdapter;
import wojcik.czarek.tabliczkadzielenia.Constants;
import wojcik.czarek.tabliczkadzielenia.Level;
import wojcik.czarek.tabliczkadzielenia.R;


public class DiffLevelFrag extends Fragment implements AdapterView.OnItemClickListener{

    private ListView difficultLevelListView;
    private LevelListAdapter adapter;

    public DiffLevelFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diff_level, container, false);
        difficultLevelListView = (ListView) view.findViewById(R.id.lvl_levelsList);
        adapter = new LevelListAdapter(MyActivity.CONTEXT, MyActivity.DIFFICULT_LEVELS);
        difficultLevelListView.setAdapter(adapter);
        for(Level diffLevel : MyActivity.DIFFICULT_LEVELS) {
            if(diffLevel.equals(MyActivity.GAME_STATUS.getDifficultLevel())) {
                diffLevel.setActive(true);
            } else {
                diffLevel.setActive(false);
            }
        }
        difficultLevelListView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Level level = (Level) parent.getItemAtPosition(position);
        MyActivity.GAME_STATUS.setDifficultLevel(level);
        level.setActive(true);
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Constants.DIFF_LEVEL_TAG, MyActivity.GAME_STATUS.getDifficultLevel().getId());
        editor.commit();
        BackToMenu();

    }

    private void BackToMenu() {
        Menu menu = new Menu();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, menu, Constants.MENU_TAG);
        transaction.commit();
    }
}
