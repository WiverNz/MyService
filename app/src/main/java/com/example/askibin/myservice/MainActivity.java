package com.example.askibin.myservice;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * @since 1.0
 * Main activity class
 * http://www.compiletimeerror.com/2015/01/android-aidl-tutorial-with-example.html#.VOB3T9hoh5I
 */
public class MainActivity extends ActionBarActivity {
    /**
     * @since 1.0
     * tag for log
     */
    private final String mLogTag = "myLogs";
    /**
     * @since 1.0
     * is bound captured
     */
    private boolean mBound = false;
    /**
     * @since 1.0
     * is bound captured
     */
    ServiceConnection sConn;
    Intent intent;
    MyService myService;
    TextView tvInterval;
    long interval;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvInterval = (TextView) findViewById(R.id.tvInterval);
        intent = new Intent(this, MyService.class);
        sConn = new ServiceConnection() {

            public void onServiceConnected(final ComponentName name, final IBinder binder) {
                Log.d(mLogTag, "MainActivity onServiceConnected");
                myService = ((MyService.MyBinder) binder).getService();
                mBound = true;
            }

            public void onServiceDisconnected(final ComponentName name) {
                Log.d(mLogTag, "MainActivity onServiceDisconnected");
                mBound = false;
            }
        };
    }


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onStart() {
        super.onStart();
        bindService(intent, sConn, 0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!bound) return;
        unbindService(sConn);
        bound = false;
    }

    public void onClickStart(final View v) {
        startService(intent);
    }
    public void onClickStop(final View v) {
        stopService(intent);
    }

    public void onClickUp(final View v) {
        if (!mBound) return;
        interval = myService.upInterval(500);
        tvInterval.setText("interval = " + interval);
    }

    public void onClickDown(final View v) {
        if (!mBound) return;
        interval = myService.downInterval(500);
        tvInterval.setText("interval = " + interval);
    }
}
