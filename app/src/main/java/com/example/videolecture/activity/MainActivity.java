package com.example.videolecture.activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.videolecture.R;
import com.example.videolecture.firebase.Config;
import com.example.videolecture.firebase.NotificationUtils;
import com.example.videolecture.fragment.AboutUsFragment;
import com.example.videolecture.fragment.ConditionsFragment;
import com.example.videolecture.fragment.HomeFragment;
import com.example.videolecture.fragment.PolicyFragment;
import com.example.videolecture.fragment.ProductFragment;
import com.example.videolecture.framework.IAsyncWorkCompletedCallback;
import com.example.videolecture.framework.ServiceCaller;
import com.example.videolecture.model.MyPojo;
import com.example.videolecture.model.Result;
import com.example.videolecture.utilities.GooglePlayStoreAppVersionNameLoader;
import com.example.videolecture.utilities.Utility;
import com.example.videolecture.utilities.WSCallerVersionListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.dmoral.toasty.Toasty;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class MainActivity extends AppCompatActivity implements WSCallerVersionListener {
    private TextView mTextMessage;
    String phone;
    boolean isForceUpdate = true;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        Fragment fragment;

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new HomeFragment();
//                    mTextMessage.setText(R.string.title_home);
                    openFragment(fragment);
                    return true;
                case R.id.navigation_about:
                    fragment = new AboutUsFragment();
                    openFragment(fragment);
                    return true;
                case R.id.navigation_privacy:
                    fragment = new PolicyFragment();
//                    mTextMessage.setText(R.string.title_policy);
                    openFragment(fragment);
                    return true;
                case R.id.navigation_conditions:
                    fragment = new ConditionsFragment();
                    openFragment(fragment);
//                    mTextMessage.setText(R.string.title_conditions);
                    return true;
            }
            return false;
        }
    };

    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    @Override
    public void onGetResponse(boolean isUpdateAvailable) {
        Log.e("ResultAPPMAIN", String.valueOf(isUpdateAvailable));
        if (isUpdateAvailable) {
            showUpdateDialog();
        }
    }

    /**
     * Method to show update dialog
     */
    public void showUpdateDialog() {
        if (Utility.isOnline(MainActivity.this)) {
            android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
            alertDialogBuilder.setTitle(MainActivity.this.getString(R.string.app_name));
            alertDialogBuilder.setMessage("Your Application update is available!");
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("Update Now", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    MainActivity.this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                    dialog.cancel();
                }
            });
            alertDialogBuilder.setNegativeButton("Later", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (isForceUpdate) {
                        finish();
                    }
                    dialog.dismiss();
                }
            });
            alertDialogBuilder.show();
            alertDialogBuilder.setCancelable(false);
        } else {
            Toasty.error(MainActivity.this, "No Internet Connection").show();
        }
    }

    public void getLoginId() {
        List<Result> resultList = new ArrayList<>();
        ServiceCaller serviceCaller = new ServiceCaller(MainActivity.this);
        serviceCaller.callOtpData(phone, new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String workName, boolean isComplete) {
                if (isComplete) {
                    MyPojo myPojo = new Gson().fromJson(workName, MyPojo.class);
                    for (Result result : myPojo.getResult()) {
                        resultList.addAll(Arrays.asList(result));
                    }
                    if (resultList != null && resultList.size() != 0) {
                        SharedPreferences preferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putInt("id", resultList.get(resultList.size() - 1).getLoginId());
                        editor.putString("phone", phone);
                        editor.apply();
                        Toasty.success(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                        startActivity(intent);
//                        finish();
                    }
                } else {
                    Toasty.error(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);

        SharedPreferences preferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        phone = preferences.getString("phone", null);
        getLoginId();
        HomeFragment homeFragment = new HomeFragment();
        openFragment(homeFragment);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        ProductFragment productFragment=ProductFragment.newInstance(0,"","");
//        openFragment(productFragment);
        new GooglePlayStoreAppVersionNameLoader(MainActivity.this, MainActivity.this).execute();
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received
                    Intent intents = new Intent(MainActivity.this, MainActivity.class);
                    intents.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 1410,
                            intents, PendingIntent.FLAG_ONE_SHOT);
                    String message = intent.getStringExtra("message");
                    NotificationCompat.Builder notificationBuilder = new
                            NotificationCompat.Builder(MainActivity.this)
                            .setSmallIcon(R.drawable.logo)
                            .setContentTitle("Message")
                            .setContentText(message)
                            .setAutoCancel(true)
                            .setContentIntent(pendingIntent);

                    NotificationManager notificationManager =
                            (NotificationManager)
                                    getSystemService(Context.NOTIFICATION_SERVICE);

                    notificationManager.notify(1410, notificationBuilder.build());
//                    Toast.makeText(getApplicationContext(), "Notification: " + message, Toast.LENGTH_LONG).show();

                }
            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.id_logout) {
            SharedPreferences preferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
            showAlert();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return true;
    }

    public void openFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    public void setActionBartitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    public void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Logout");
        builder.setMessage("Do you want to exit");
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });
        builder.show();
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
}
