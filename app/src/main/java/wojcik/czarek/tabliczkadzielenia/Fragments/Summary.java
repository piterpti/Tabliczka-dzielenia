package wojcik.czarek.tabliczkadzielenia.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import wojcik.czarek.tabliczkadzielenia.Constants;
import wojcik.czarek.tabliczkadzielenia.R;


public class Summary extends Fragment {


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
        
    }

    public void BackToMenu()
    {
        Menu menu = new Menu();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, menu, Constants.MENU_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
