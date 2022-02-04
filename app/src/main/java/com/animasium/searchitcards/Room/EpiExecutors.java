package com.animasium.searchitcards.Room;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.MainThread;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.LogRecord;

public class EpiExecutors {

    private static final Object LOCK = new Object();
    private static  EpiExecutors sInstance;
    private final Executor diskIO;
    private final Executor mainThread;
    private final Executor networkIO;

    public EpiExecutors(Executor diskIO, Executor mainThread, Executor networkIO) {
        this.diskIO = diskIO;
        this.mainThread = mainThread;
        this.networkIO = networkIO;
    }

    public static  EpiExecutors getInstance(){
        if (sInstance == null){
            synchronized (LOCK){
                sInstance = new EpiExecutors(Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(3),
                        new MainThreadExecutor());
            }
        }
        return sInstance;
    }

    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());
        @Override
        public void execute(Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}
