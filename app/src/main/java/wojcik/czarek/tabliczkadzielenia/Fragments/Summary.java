package wojcik.czarek.tabliczkadzielenia.Fragments;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import wojcik.czarek.tabliczkadzielenia.Activities.MyActivity;
import wojcik.czarek.tabliczkadzielenia.Adapters.FinalAdapter;
import wojcik.czarek.tabliczkadzielenia.Constants;
import wojcik.czarek.tabliczkadzielenia.NonScrollListView;
import wojcik.czarek.tabliczkadzielenia.Question;
import wojcik.czarek.tabliczkadzielenia.R;
import java.util.*;


public class Summary extends Fragment {

    private TextView resultTextView;
    private Button menuButton;
    private NonScrollListView achievementList;

    public Summary() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_summary, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        resultTextView = (TextView) view.findViewById(R.id.end_resultTv);
        menuButton = (Button) view.findViewById(R.id.end_menuButton);
        achievementList = (NonScrollListView) view.findViewById(R.id.end_achListView);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackToMenu();
            }
        });
        int correctAnswers = MyActivity.GAME_STATUS.getCorrectAnswers();
        int questionCount = MyActivity.GAME_STATUS.getDifficultLevel().getQuestionCount();
        float percentAnswers = ((float) correctAnswers / (float) questionCount) * 100f;
        String result = " " + correctAnswers + "/" + questionCount;
        result +=  " (" + String.format("%.2f", percentAnswers) + "%)";
        resultTextView.setText(result);
        FinalAdapter finalAdapter = new FinalAdapter(MyActivity.CONTEXT, MyActivity.GAME_STATUS.getUnlockedAchievements((int)percentAnswers));
        achievementList.setAdapter(finalAdapter);
        SaveMistakes();
    }

    public void BackToMenu()
    {
        Menu menu = new Menu();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, menu, Constants.MENU_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void SaveMistakes() {
        String superMemo = "";
        Arrays.sort(MyActivity.GAME_QUESTIONS, new Comparator<Question>() {
            @Override
            public int compare(Question lhs, Question rhs) {
                return Integer.valueOf(lhs.getId()).compareTo(Integer.valueOf(rhs.getId()));
            }
        });
        for(Question t : MyActivity.GAME_QUESTIONS) {
            superMemo += t.getMistakes() + ",";
        }
        PreferenceManager.getDefaultSharedPreferences(MyActivity.CONTEXT).edit().putString(Constants.SUPER_MEMO_SHARED, superMemo).commit();
        ArrayList<Question> arrayList = new ArrayList<>(Arrays.asList(MyActivity.GAME_QUESTIONS));
        Collections.shuffle(arrayList);
        MyActivity.GAME_QUESTIONS = arrayList.toArray(MyActivity.GAME_QUESTIONS);
    }

}
