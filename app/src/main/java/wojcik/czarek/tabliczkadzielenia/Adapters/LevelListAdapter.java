package wojcik.czarek.tabliczkadzielenia.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import wojcik.czarek.tabliczkadzielenia.Activities.MyActivity;
import wojcik.czarek.tabliczkadzielenia.Fragments.Menu;
import wojcik.czarek.tabliczkadzielenia.Level;
import wojcik.czarek.tabliczkadzielenia.R;

/**
 * Created by Piter on 09/06/2016.
 */
public class LevelListAdapter extends ArrayAdapter {

    private static final int COLOR_ACTIVE = Color.GREEN;
    private static final int COLOR_INACTIVE = Color.GRAY;

    public LevelListAdapter(Context context, Level[] list) {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Level lvl = (Level) getItem(position);
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.level_adapter, parent, false);
        }
        TextView listItem = (TextView) convertView.findViewById(R.id.lvl_listItem);
        String textToDisp = lvl.getLevelName();
        listItem.setText(textToDisp);
        if(lvl.isActive())
            listItem.setBackgroundColor(COLOR_ACTIVE);
        else
            listItem.setBackgroundColor(COLOR_INACTIVE);
        return convertView;
    }
}
