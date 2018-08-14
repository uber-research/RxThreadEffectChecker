package com.ubercab.rxthreadchecker.testdata;

import com.ubercab.rxthreadchecker.testdata.Stream;
import com.ubercab.rxthreadchecker.testdata.Scheduler;
import java.util.Random;

import com.ubercab.rxthreadchecker.qual.*;
import org.checkerframework.checker.guieffect.qual.*;

/**
 * Dependently typecheck streams using static Schedulers a la io.reactivex.android.schedulers.AndroidSchedulers
 */

public class StreamSchedulers {
    private @UI Object uiEffectfulAction = null;
    private Object safeAction = null;

    //Correct usage examples; no type-checking errors should be raised.
    private void safeStreamEffects() {

        Stream s = new Stream(); //Make a new stream
        s.subscribe(safeAction); //subscribe an effect-free callback


        Stream s1 = new Stream(); //Make a new stream
        @UIThread Stream s2 = s1.observeOn(Scheduler.uiThread()); // Specify that it should be observed on the UI thread
        s2.subscribe(uiEffectfulAction); //subscribe a callback with UI effect


        Stream s3 = new Stream(); //Make a new stream
        @UIThread Stream s4 = s3.observeOn(Scheduler.uiThread()); // Specify that it should be observed on the UI thread
        @UIThread Stream s5 = s4.doWork(null); //Perform some effect-free operations (e.g. map, filter, etc.)  s5 should have inferred @UIThread annotation.
        s5.subscribe(uiEffectfulAction);
    }


    //Incorrect usage, type-checking errors should be raised
    private void unsafeStreamManip() {
        Stream s1 = new Stream(); //Make a new stream
        @CompThread Stream s2 = s1.observeOn(Scheduler.computationThread()); //Specify that it should be observed on a computation thread
        // :: error: (rxjava.thread.violation)
        s2.subscribe(uiEffectfulAction);//subscribe a callback with UI effect

        Stream s3 = new Stream(); //Make a new stream
        // :: error: (rxjava.thread.violation)
        s3.subscribe(uiEffectfulAction); //subscribe a callback with UI effect


        Scheduler sched = randomSched(); //Scheduler thread type is UIThread $\sqcup$ CompThread
        Stream s4 = new Stream();
        Stream s5 = s4.observeOn(sched);
        // :: error: (rxjava.thread.violation)
        s5.subscribe(uiEffectfulAction); // Stream may (not must!) be on UI thread, so checker should error
    }

    private Scheduler randomSched() {
        Random r = new Random();
        if(r.nextBoolean()){
            return Scheduler.uiThread();
        } else {
            return Scheduler.computationThread();
        }
    }
}
