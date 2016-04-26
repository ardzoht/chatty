package com.chatty.android.chatty.content;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Alejandro on 25/04/2016.
 */
public class ChannelAdapter<T> extends ArrayAdapter<T> {

    public ChannelAdapter(Context context, List<T> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View listItem = convertView;

        if(null == convertView) {
            listItem = inflater.inflate(
                    android.R.layout.simple_expandable_list_item_2,
                    parent,
                    false);
        }

        TextView channelTitle = (TextView)listItem.findViewById(android.R.id.text1);
        TextView channelDescription = (TextView)listItem.findViewById(android.R.id.text2);

        Channel item = (Channel) getItem(position);

        channelTitle.setText(item.getName());
        channelDescription.setText(item.getDescription());

        return listItem;
    }
}
