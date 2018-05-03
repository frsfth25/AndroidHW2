package com.farisfath25.androidhw2;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

/*
Faris Fathurrahman - 14050141015
HW#2
CENG427 - Mobile Programming Devices

Resources:
- http://abhiandroid.com/materialdesign/tablayout-example-android-studio.html
- https://www.youtube.com/watch?v=BqMIcugsCFc
- https://stackoverflow.com/questions/24772828/how-to-parse-html-table-using-jsoup
- https://try.jsoup.org/
- https://www.journaldev.com/10416/android-listview-with-custom-adapter-example-tutorial
- https://stackoverflow.com/questions/29743535/android-listview-onclick-open-a-website
- http://wptrafficanalyzer.in/blog/item-long-click-handler-for-listfragment-in-android/
- http://abhiandroid.com/ui/progressdialog
- https://www.androidhive.info/2012/07/android-detect-internet-connection-status/
- https://stackoverflow.com/questions/4280608/disable-a-whole-activity-from-user-action?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
- https://stackoverflow.com/questions/3221488/blur-or-dim-background-when-android-popupwindow-active?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
- https://stackoverflow.com/questions/18324555/android-copy-existing-project-with-new-name-in-android-studio?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
- https://stackoverflow.com/questions/31990372/remove-old-and-add-new-git-in-android-studio?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
- https://stackoverflow.com/questions/2372415/how-to-change-color-of-android-listview-separator-line?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
- https://romannurik.github.io/AndroidAssetStudio/icons-launcher.html
- http://www.gadgetsaint.com/tips/change-statusbar-color-android/#.WurckR6sa00
*/

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    //LinearLayout bossLayout;

    private FrameLayout simpleFrameLayout;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //bossLayout = (LinearLayout) findViewById(R.id.theBoss);

        // get the reference of FrameLayout and TabLayout
        simpleFrameLayout = findViewById(R.id.simpleFrameLayout);
        tabLayout = findViewById(R.id.simpleTabLayout);

        // Create a new first Tab
        TabLayout.Tab firstTab = tabLayout.newTab();
        firstTab.setText("Food"); // set the Text for the first Tab
        firstTab.setIcon(R.drawable.icon_food); // set an icon for the first tab

        //<div>Icons made by <a href="https://www.flaticon.com/authors/smalllikeart" title="smalllikeart">smalllikeart</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>

        tabLayout.addTab(firstTab); // add  the tab at in the TabLayout

        // Create a new second Tab
        TabLayout.Tab secondTab = tabLayout.newTab();
        secondTab.setText("Announcements"); // set the Text for the second Tab
        secondTab.setIcon(R.drawable.icon_announce); // set an icon for the second tab

        //<div>Icons made by <a href="https://www.flaticon.com/authors/roundicons" title="Roundicons">Roundicons</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>

        tabLayout.addTab(secondTab); // add  the tab  in the TabLayout

        // Create a new third Tab
        TabLayout.Tab thirdTab = tabLayout.newTab();
        thirdTab.setText("News"); // set the Text for the first Tab
        thirdTab.setIcon(R.drawable.icon_news); // set an icon for the first tab

        //<div>Icons made by <a href="http://www.freepik.com" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>

        tabLayout.addTab(thirdTab); // add  the tab at in the TabLayout

        // Manually check for the internet connection
        if (!ConnectivityReceiver.isConnected()) {
            String message = "App is frozen since no internet connection.\nPlease connect to internet and try again.";
            Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
            toast.show();

            //bossLayout.getForeground().setAlpha(220);
            //simpleFrameLayout.getForeground().setAlpha(220);

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            //finish();
        }

        Fragment fragment = new FragmentOne();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.simpleFrameLayout, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();

        // perform setOnTabSelectedListener event on TabLayout
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // get the current selected tab's position and replace the fragment accordingly
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new FragmentOne();
                        break;
                    case 1:
                        fragment = new FragmentTwo();
                        break;
                    case 2:
                        fragment = new FragmentThree();
                        break;
                }
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.simpleFrameLayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(this);
    }


    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showWarn(isConnected);
    }

    // Showing the status in Toast view
    private void showWarn(boolean isConnected) {
        String message;

        if (isConnected) {
            message = "Connected to Internet";

            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            //simpleFrameLayout.getForeground().setAlpha(0);

        } else {
            message = "App is frozen since there is no internet connection.\nPlease connect to internet and try again.";

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            //simpleFrameLayout.getForeground().setAlpha(220);

            //finish();
        }

        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_lock_power_off)
                .setMessage("Exit the application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();

                        int pid = android.os.Process.myPid();
                        android.os.Process.killProcess(pid);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    public void openSite(View v)
    {
        TextView tv= findViewById(R.id.txtView);

        new AlertDialog.Builder(v.getContext())
                .setMessage("Open the Food Menu page on your default browser?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    String url;
                    Uri uri;
                    Intent intent;

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        url = "https://www.ybu.edu.tr/sks";
                        uri = Uri.parse(url);
                        intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", null)
                .show();

    }

}
