package com.sample.sampleapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by connieleung on 1/18/15.
 */
public class PersonListAdapter extends ArrayAdapter<Person> {

    private Context context;
    private List<Person> lstPerson;

    public PersonListAdapter(Context context, List<Person> objects) {
        super(context, R.layout.list_item, objects);
        this.context = context;
        this.lstPerson = objects;
    }

    @Override
    public int getCount() {
        return lstPerson.size();
    }

    @Override
    public Person getItem(int position) {
        return lstPerson.get(position);
    }

    @Override
    public long getItemId(int position) {
        Person p = getItem(position);
        return p == null ? 1 : p.hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater  inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvFirstname = (TextView) convertView.findViewById(R.id.tvFirstname);
        holder.tvLastname = (TextView) convertView.findViewById(R.id.tvLastname);
        holder.tvGender = (TextView) convertView.findViewById(R.id.tvGender);

        // getting movie data for the row
        Person p = this.getItem(position);

        holder.tvFirstname.setText(p.getFirstname());
        holder.tvLastname.setText(p.getLastname());
        holder.tvGender.setText(p.getGender().name());

        return convertView;
    }

    static class ViewHolder {
        TextView tvFirstname;
        TextView tvLastname;
        TextView tvGender;
    }
}
