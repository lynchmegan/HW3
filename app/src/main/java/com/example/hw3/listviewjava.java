package com.example.hw3;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class listviewjava extends ArrayAdapter<String>{
    Context context;
    ArrayList<String> title;

    listviewjava(Context c, ArrayList<String> title) {
        super(c, R.layout.listitem,title);
        this.context = c;
        this.title=title;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View row = vi.inflate(R.layout.listitem, parent, false);
        TextView titles = (row.findViewById(R.id.textView1));
        int pos = position+1;
        titles.setText(+pos + ". " + title.get(position));
        pos++;
        return row;
    }

}



