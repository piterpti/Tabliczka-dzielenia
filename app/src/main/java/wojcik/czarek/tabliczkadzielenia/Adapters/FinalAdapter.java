package wojcik.czarek.tabliczkadzielenia.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import wojcik.czarek.tabliczkadzielenia.Achievement;
import wojcik.czarek.tabliczkadzielenia.R;

/**
 * Created by Piter on 09/06/2016.
 */
public class FinalAdapter extends ArrayAdapter {
    private static final int COLOR_ACTIVE = Color.GREEN;
    private static final int COLOR_INACTIVE = Color.RED;

    public FinalAdapter(Context context, Achievement[] list) {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Achievement achievement = (Achievement) getItem(position);
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.final_adapter, parent, false);
        }
        TextView listItem = (TextView) convertView.findViewById(R.id.final_text);
        TextView ans = (TextView) convertView.findViewById(R.id.final_correct);
        listItem.setText(achievement.getName());

        if(achievement.isUnlocked())
        {
            listItem.setBackgroundColor(COLOR_ACTIVE);
            ans.setText("V");
            ans.setBackgroundColor(COLOR_ACTIVE);
        }
        else
        {
            listItem.setBackgroundColor(COLOR_INACTIVE);
            ans.setText("X");
            ans.setBackgroundColor(COLOR_INACTIVE);
        }
        return convertView;
    }
}
