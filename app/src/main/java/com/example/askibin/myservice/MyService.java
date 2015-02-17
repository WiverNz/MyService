package com.example.askibin.myservice;

/**
 * Created by askibin on 12.02.2015.
 */

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @since 1.0
 * My service class
 */
public class MyService extends Service {
    /**
     * @since 1.0
     * tag for log
     */
    private final String mLogTag = "myLogs";

    /**
     * @since 1.0
     * main timer
     */
    private Timer mTimer;
    /**
     * @since 1.0
     * main timer task
     */
    private TimerTask mTimerTask;
    /**
     * @since 1.0
     * default interval for timer
     */
    private final long mDefInterval = 1000;
    /**
     * @since 1.0
     * interval for timer
     */
    private long mInterval = mDefInterval;

    /**
     * @since 1.0
     * on create service
     */
    public final void onCreate() {
        super.onCreate();
        Log.d(mLogTag, "MyService onCreate");
        //mTimer = new Timer();
        //schedule();
    }

    /**
     * @since 1.0
     * schedule
     */
    public final void schedule() {
        if (mTimerTask != null) {
            mTimerTask.cancel();
        }
        if (mInterval > 0) {
            mTimerTask = new TimerTask() {
                public void run() {
                    Log.d(mLogTag, "run");
                }
            };
            mTimer.schedule(mTimerTask, mDefInterval, mInterval);
        }
    }

    /**
     * @param gap step
     * @return current interval
     * @since 1.0
     * up the interval
     */
    public final long upInterval(final long gap) {
        mInterval = mInterval + gap;
        schedule();
        return mInterval;
    }

    /**
     * @param gap step
     * @return current interval
     * @since 1.0
     * down the interval
     */
    public final long downInterval(final long gap) {
        mInterval = mInterval - gap;
        if (mInterval < 0) {
            mInterval = 0;
        }
        schedule();
        return mInterval;
    }

    //public IBinder onBind(Intent arg0) {
    //    Log.d(LOG_TAG, "MyService onBind");
    //    return binder;
    //}

    //    class MyBinder extends Binder {
//        MyService getService() {
//            return MyService.this;
//        }
//    }

    /**
     * @since 1.0
     * set mBinder
     */
    private final IAdd.Stub mBinder = new IAdd.Stub() {
        @Override
        public int add(final int num1, final int num2) {
            Log.d(mLogTag, "MyService add");
            return (num1 + num2);
        }
    };

    /**
     * @param intent intent
     * @return binder
     * @since 1.0
     * on bind
     */
    public final IBinder onBind(final Intent intent) {
        Log.d(mLogTag, "MyService onBind");
        return mBinder;
    }
}
