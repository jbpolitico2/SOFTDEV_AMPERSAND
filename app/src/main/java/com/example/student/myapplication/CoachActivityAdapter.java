package com.example.student.myapplication;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
public class CoachActivityAdapter extends ArrayAdapter {
    List list = new ArrayList();
    public CoachActivityAdapter(Context context, int resource) {
        super(context, resource);
    }


    public void add(CoachContactsActivity object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row;
        row =convertView;
        contactHandler contactHandler;
        if (row == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.activity_layout,parent,false);
            contactHandler = new contactHandler();
            contactHandler.txActivityName = (TextView) row.findViewById(R.id.txActivityName);
            contactHandler.txActivityDecs = (TextView) row.findViewById(R.id.txActivityDecs);

            row.setTag(contactHandler);
        }
        else{

            contactHandler = (contactHandler)row.getTag();

        }
        CoachContactsActivity contacts =(CoachContactsActivity) this.getItem(position);
        contactHandler.txActivityName.setText(contacts.getActivity_name());
        contactHandler.txActivityDecs.setText(contacts.getActivity_desc());

        return row;

    }
    static class contactHandler {
        TextView txActivityName, txActivityDecs;
    }
}
