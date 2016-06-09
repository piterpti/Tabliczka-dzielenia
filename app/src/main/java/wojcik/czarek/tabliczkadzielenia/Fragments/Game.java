package wojcik.czarek.tabliczkadzielenia.Fragments;


import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import wojcik.czarek.tabliczkadzielenia.Activities.MyActivity;
import wojcik.czarek.tabliczkadzielenia.Constants;
import wojcik.czarek.tabliczkadzielenia.Level;
import wojcik.czarek.tabliczkadzielenia.Question;
import wojcik.czarek.tabliczkadzielenia.R;
import wojcik.czarek.tabliczkadzielenia.Status;


/**
 * A simple {@link Fragment} subclass.
 */
public class Game extends Fragment {

    private final int PROGRESS_BAR_CHANGE = 20;
    private final int TIME_TO_WAIT = 1000;

    private Button[] answerButtons;
    private TextView verdictTextView;
    private ProgressBar progressBar;
    private TextView progressTextView;
    private Button backToMenu;
    private TextView currentTaskTextView;
    private String enteredText = "";
    private String task;
    private CountDownTimer answerTimer;
    private boolean nextQuestion;
    private Question currentTask;

    public Game() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        answerButtons = new Button[10];
        answerButtons[0] = (Button) view.findViewById(R.id.game_button0);
        answerButtons[1] = (Button) view.findViewById(R.id.game_button1);
        answerButtons[2] = (Button) view.findViewById(R.id.game_button2);
        answerButtons[3] = (Button) view.findViewById(R.id.game_button3);
        answerButtons[4] = (Button) view.findViewById(R.id.game_button4);
        answerButtons[5] = (Button) view.findViewById(R.id.game_button5);
        answerButtons[6] = (Button) view.findViewById(R.id.game_button6);
        answerButtons[7] = (Button) view.findViewById(R.id.game_button7);
        answerButtons[8] = (Button) view.findViewById(R.id.game_button8);
        answerButtons[9] = (Button) view.findViewById(R.id.game_button9);
        verdictTextView = (TextView) view.findViewById(R.id.game_verdictTextView);
        progressBar = (ProgressBar) view.findViewById(R.id.game_progressBar);
        progressTextView = (TextView) view.findViewById(R.id.game_progressTextView);
        currentTaskTextView = (TextView) view.findViewById(R.id.game_taskTextView);
        backToMenu = (Button) view.findViewById(R.id.game_backToMenu);
        backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackToMenu();
            }
        });
        AddListenerToButtons();
        LoadQuestion();
    }

    private void AddListenerToButtons() {
        for(Button b : answerButtons)
        {
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button button = (Button) v;
                    enteredText += button.getText();
                    currentTaskTextView.setText(task + " " + enteredText);
                    if(currentTask.CheckCorrectAnswer(enteredText)) {
                        Verdict(true);
                    }
                    else {
                        Verdict(false);
                    }
                }
            });
        }
    }

    private void Verdict(boolean correct)
    {
        enteredText = "";
        TurnOffTimer();
        verdictTextView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        LockUnlockButtons(false);
        currentTask.setCorrectAnswer(correct);
        if(correct)
        {
            verdictTextView.setText(getResources().getString(R.string.game_goodAnswer));
            //verdictTextView.setTextColor(GOOD_ANSWER_COLOR);
        }
        else
        {
            verdictTextView.setText(getResources().getString(R.string.game_wrongAnswer));
            //verdictTextView.setTextColor(WRONG_ANSWER_COLOR);
            currentTaskTextView.setVisibility(View.INVISIBLE);
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                nextQuestion = true;
                LoadQuestion();
                LockUnlockButtons(true);
            }
        }, TIME_TO_WAIT);
    }

    private void LockUnlockButtons(boolean onOff)
    {
        for(Button button : answerButtons)
        {
            button.setEnabled(onOff);
        }
    }

    public void CreateGame()
    {
        Level level = MyActivity.GAME_STATUS.getDifficultLevel();
        MyActivity.GAME_STATUS = new Status(level);
        MyActivity.GAME_STATUS.setQuestionList(Question.GenerateQuestionForGame(MyActivity.GAME_STATUS.getDifficultLevel().getQuestionCount()));
        nextQuestion = true;
    }

    private void LoadQuestion()
    {
        if(nextQuestion)
        {
            nextQuestion = false;
            if(!MyActivity.GAME_STATUS.setToNextTaskIfExist())
            {
                GoToSummary();
                return;
            }
            CreateTimer(MyActivity.GAME_STATUS.getDifficultLevel().getAnswerTime() * 1000);
        }
        verdictTextView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        currentTaskTextView.setVisibility(View.VISIBLE);
        progressTextView.setText(MyActivity.GAME_STATUS.getCurrentQuestion() + " | " + MyActivity.GAME_STATUS.getDifficultLevel().getQuestionCount());
        currentTask = MyActivity.GAME_STATUS.getTask();
        task = currentTask.toString();
        currentTaskTextView.setText(task + " " + enteredText);
    }

    private void GoToSummary() {
        Summary summary = new Summary();
        summary.setRetainInstance(true);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, summary, Constants.SUMMARY);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void BackToMenu()
    {
        TurnOffTimer();
        Menu menu = new Menu();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, menu, Constants.MENU_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void CreateTimer(int time) {
        TurnOffTimer();
        progressBar.setProgress(100);
        answerTimer = new CountDownTimer(time, PROGRESS_BAR_CHANGE) {
            @Override
            public void onTick(long millisUntilFinished) {
                float ppp = millisUntilFinished / MyActivity.GAME_STATUS.getDifficultLevel().getAnswerTime() / 10;
                progressBar.setProgress((int) ppp);
            }

            @Override
            public void onFinish() {
                Verdict(false);
            }
        };
        answerTimer.start();
    }

    private void TurnOffTimer()
    {
        if(answerTimer != null) {
            answerTimer.cancel();
            answerTimer = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        TurnOffTimer();
    }

}
