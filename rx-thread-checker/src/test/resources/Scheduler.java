package com.ubercab.rxthreadchecker.testdata;

import com.ubercab.rxthreadchecker.qual.*;


//Mock Scheduler library meant to mimic key features of ReactiveX schedulers without a full implementation.
// Users use the static methods to get handles on particular threads (e.g. a computation thread, main UI thread, etc.) or request a thread without any constraints on what thread that might be( i.e. with anyThread() )

public class Scheduler {

    private Scheduler() { }

    public static @UIThread Scheduler uiThread() {
        return new @UIThread Scheduler();
    }

    public static @CompThread Scheduler computationThread() {
        return new @CompThread Scheduler();
    }

    public static @AnyThread Scheduler anyThread() {
        return new Scheduler();
    }

}
