package com.farisfath25.androidhw2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

class CustomAdapter extends ArrayAdapter<contentItem> implements View.OnClickListener {

   private final Context mContext;

    public CustomAdapter(ArrayList<contentItem> data, Context context) {
        super(context, R.layout.custom_item_view, data);
        ArrayList<contentItem> dataSet = data;
        this.mContext = context;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        contentItem dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.custom_item_view, parent, false);
            viewHolder.txtTitle = convertView.findViewById(R.id.txtTitle);
            //viewHolder.txtUrl = convertView.findViewById(R.id.txtUrl);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        int lastPosition = position;

        viewHolder.txtTitle.setText(Objects.requireNonNull(dataModel).getTitle());

        // Return the completed view to render on screen
        return convertView;
    }

    @Override
    public void onClick(View view) {

    }

    // View lookup cache
    private static class ViewHolder {
        TextView txtTitle;
        // --Commented out by Inspection (30/04/2018 19:12):TextView txtUrl;

    }
}
