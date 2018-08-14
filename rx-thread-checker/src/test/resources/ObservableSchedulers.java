package com.ubercab.rxthreadchecker.testdata;

import io.reactivex.*;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.android.schedulers.AndroidSchedulers;
import com.ubercab.rxthreadchecker.qual.*;
import org.checkerframework.checker.guieffect.qual.*;


/**
 * Combine UI effect typing with RX Observable thread typing
 */
public class ObservableSchedulers {

    //Correct usage examples; no type-checking errors should be raised.
    private void safeObservableEffects(
            io.reactivex.@UI Observer<Object> uiEffectfulAction,
            io.reactivex.Observer<Object> safeAction)
    {
        Observable<Object> o = Observable.empty(); //Make a new stream
        o.subscribe(safeAction); //subscribe an effect-free callback
        //Equivalent operations without intermediate variables:
        Observable.empty().subscribe(safeAction);

        Observable<Object> o1 = Observable.empty(); //Make a new stream
        @UIThread Observable<Object> o2 = o1.observeOn(AndroidSchedulers.mainThread()); // Specify that it should be observed on the UI thread
        o2.subscribe(uiEffectfulAction); //subscribe a callback with UI effect
        //Equivalent operations without intermediate variables:
        Observable.empty().observeOn(AndroidSchedulers.mainThread()).subscribe(uiEffectfulAction);

        Observable<Object> o3 = Observable.empty(); //Make a new stream
        @UIThread Observable<Object> o4 = o3.observeOn(AndroidSchedulers.mainThread()); // Specify that it should be observed on the UI thread
        @UIThread Observable<Object> o5 = o4.doOnEach(safeAction); //Perform some effect-free operations (e.g. map, filter, etc.)  o5 should have inferred @UIThread annotation
        o5.subscribe(uiEffectfulAction);
        //Equivalent operations without intermediate variables:
        Observable.empty().observeOn(AndroidSchedulers.mainThread()).doOnEach(safeAction).subscribe(uiEffectfulAction);
    }

    //Incorrect usage, type-checking errors should be raised
    private void unsafeObservableEffects(io.reactivex.@UI Observer<Object> uiEffectfulAction)
    {
        Observable<Object> o1 = Observable.empty(); //Make a new stream
        @CompThread Observable<Object> o2 = o1.observeOn(Schedulers.computation()); //Specify that it should be observed on a computation thread
        // :: error: (rxjava.thread.violation)
        o2.subscribe(uiEffectfulAction);//subscribe a callback with UI effect

        //Equivalent operations without intermediate variables:
        // :: error: (rxjava.thread.violation)
        Observable.empty().observeOn(Schedulers.computation()).subscribe(uiEffectfulAction);

        Observable<Object> o3 = Observable.empty(); //Make a new stream
        // :: error: (rxjava.thread.violation)
        o3.subscribe(uiEffectfulAction); //subscribe a callback with UI effect

        //Equivalent operations without intermediate variables:
        // :: error: (rxjava.thread.violation)
        Observable.empty().subscribe(uiEffectfulAction);
    }

    private void safeObservableMergeEffects(io.reactivex.@UI Observer<Object> uiEffectfulAction)
    {
        Observable<Object> o1 = Observable.empty();
        @UIThread Observable<Object> o2 = o1.observeOn(AndroidSchedulers.mainThread());
        @UIThread Observable<Object> o3 = o1.observeOn(AndroidSchedulers.mainThread());
        Observable.merge(o2, o3).subscribe(uiEffectfulAction);
    }

    private void unsafeObservableMergeEffects(io.reactivex.@UI Observer<Object> uiEffectfulAction)
    {
        Observable<Object> o1 = Observable.empty();
        @UIThread Observable<Object> o2 = Observable.empty().observeOn(AndroidSchedulers.mainThread());
        // :: error: (rxjava.thread.violation)
        Observable.merge(o1, o2).subscribe(uiEffectfulAction);
    }
}
