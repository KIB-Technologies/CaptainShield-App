package com.kibtechnologies.captainshieid.service;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxSchedulers {

    public RxSchedulers() {
    }

    /**
     * IO thread pool scheduler
     */
    public static Scheduler worker() {
        return Schedulers.newThread();
    }

    /**
     * IO thread pool scheduler
     */
    public static Scheduler io() {
        return Schedulers.io();
    }

    /**
     * Computation thread pool scheduler
     */
    public static Scheduler computation() {
        return Schedulers.computation();
    }

    /**
     * Main Thread scheduler
     */
    public static Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }

}
