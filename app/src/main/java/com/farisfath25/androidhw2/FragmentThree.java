package com.farisfath25.androidhw2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


@SuppressWarnings("ALL")
public class FragmentThree extends Fragment {

    private Activity act;
    private View v;

    private ListView listView;
    private ArrayList<contentItem> newsList;
    private CustomAdapter newsAdapter;
    private ProgressDialog progressDialog;

    public FragmentThree() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_two, container, false);

        act = getActivity();

        //elements added
        listView = v.findViewById(R.id.lvContent);

        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {

                final int index = position;

                new AlertDialog.Builder(v.getContext())
                        .setMessage("Open the News link on your default browser?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            String url;
                            Uri uri;
                            Intent intent;

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                url = newsList.get(index).getUrl();
                                uri = Uri.parse(url);
                                intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return;
            }
        };

        listView.setOnItemClickListener(listener);

        newsList = new ArrayList<>();

        getNews();

        return v;
    }

    private void getNews() {
        progressDialog = new ProgressDialog(v.getContext());
        progressDialog.setMessage("Fetching..."); // Setting Message
        progressDialog.setTitle("News Data Progress"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);

        //getAnnouncements operation
        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();

                try {
                    Document doc = Jsoup.connect("http://www.ybu.edu.tr/muhendislik/bilgisayar/").get();

                    //String title = doc.title();
                    Elements newsLinks = doc.select("div.contentNews div.cnContent div.cncItem a");

                    //builder.append(title).append("\n");

                    for (Element newsLink : newsLinks) {
                        builder.append("\n").append("Text: ").append(newsLink.attr("title"))
                                .append("\n").append("Link: ").append(newsLink.absUrl("href"));

                        String title = newsLink.attr("title");
                        String url = newsLink.absUrl("href");

                        newsList.add(new contentItem(title, url));

                    }
                } catch (IOException e) {
                    builder.append("Error: ").append(e.getMessage()).append("\n");
                }

                act.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //content.setText(builder.toString());

                        progressDialog.dismiss();

                        newsAdapter = new CustomAdapter(newsList, Objects.requireNonNull(getActivity()).getApplicationContext());
                        listView.setAdapter(newsAdapter);
                    }
                });
            }
        }).start();
    }
}
