package com.farisfath25.androidhw2;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class FragmentOne extends Fragment {

    private Activity act;

    private TextView textView;

    public FragmentOne() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_one, container, false);

        act = getActivity();

        //elements added
        textView = v.findViewById(R.id.txtView);

        getFood();

        //openSite(v);

        return v;
    }

    private void getFood() {
        //    Document doc = null;

        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();

                try {
                    Document doc = Jsoup.connect("http://www.ybu.edu.tr/sks/").get();

                    Element table = doc.select("table").get(0); //select the first found table
                    Elements rows = table.select("tr");

                    for (int i = 2; i < rows.size(); i++) //first and second rows are not important
                    {
                        Element row = rows.get(i);
                        Elements cols = row.select("td");

                        builder.append(cols.get(0).text()).append("\n\n");

                    }

                } catch (IOException e) {
                    builder.append("Error: ").append(e.getMessage()).append("\n");
                }

                act.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(builder.toString());
                    }
                });
            }
        }).start();
    }


 /*   public void openSite(View v)
    {
        TextView tv= v.findViewById(R.id.txtView);

        //alter text of textview widget
        tv.setText("This text view is clicked");

        //assign the textview forecolor
        tv.setTextColor(Color.GREEN);
    } */
}
