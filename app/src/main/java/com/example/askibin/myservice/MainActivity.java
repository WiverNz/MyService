package com.example.askibin.myservice;

import android.app.Service;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @since 1.0
 * Main activity class
 * http://www.compiletimeerror.com/2015/01/android-aidl-tutorial-with-example.html#.VOB3T9hoh5I
 * http://habrahabr.ru/post/139432/
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
     * add service
     */
    protected IAdd AddService;
    ServiceConnection AddServiceConnection;
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
        initConnection();
    }

    void initConnection() {
        AddServiceConnection = new ServiceConnection() {

            @Override
            public void onServiceDisconnected(ComponentName name) {
                // TODO Auto-generated method stub
                AddService = null;
                Toast.makeText(getApplicationContext(), "Service Disconnected",
                        Toast.LENGTH_SHORT).show();
                Log.d("IRemote", "Binding - Service disconnected");
            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                // TODO Auto-generated method stub
                AddService = IAdd.Stub.asInterface((IBinder) service);
                Toast.makeText(getApplicationContext(),
                        "Addition Service Connected", Toast.LENGTH_SHORT)
                        .show();
                Log.d("IRemote", "Binding is done - Service connected");
            }
        };
        if (AddService == null) {
            Intent it = new Intent();
            it.setAction("service.Calculator");
            // binding to remote service
            bindService(it, AddServiceConnection, Service.BIND_AUTO_CREATE);
        }
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
    protected void onDestroy() {
        super.onDestroy();
        unbindService(AddServiceConnection);
    }

    public void onClickStart(final View v) {
        startService(intent);
    }
    public void onClickStop(final View v) {
        stopService(intent);
    }

    public void onClickUp(final View v) {
        // TODO Auto-generated method stub
        try {
            Toast.makeText(getApplicationContext(),
                    "result " + AddService.add(1, 1), Toast.LENGTH_SHORT)
                    .show();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void onClickDown(final View v) {

    }
}
