package com.ubercab.rxthreadchecker.testdata;

import com.ubercab.rxthreadchecker.testdata.Stream;
import com.ubercab.rxthreadchecker.qual.*;
import org.checkerframework.checker.guieffect.qual.*;


/**
 * Combine UI effect typing with Stream thread-pool typing
 */
public class StreamEffects {

    private @UI Object uiEffectfulAction = null;
    private Object safeAction = null;

    //Correct usage examples; no type-checking errors should be raised.
    private void safeStreamEffects() {
        Stream s = new Stream(); //Make a new stream
        s.subscribe(safeAction); //subscribe an effect-free callback

        Stream s1 = new Stream(); //Make a new stream
        @UIThread Stream s2 = s1.observeUI(); // Specify that it should be observed on the UI thread
        s2.subscribe(uiEffectfulAction); //subscribe a callback with UI effect

        Stream s3 = new Stream(); //Make a new stream
        @UIThread Stream s4 = s3.observeUI(); // Specify that it should be observed on the UI thread
        @UIThread Stream s5 = s4.doWork(null); //Perform some effect-free operations (e.g. map, filter, etc.)  s5 should have inferred @UIThread annotation.
        s5.subscribe(uiEffectfulAction);

    }


    //Incorrect usage, type-checking errors should be raised
    private void unsafeStreamManip() {
        Stream s1 = new Stream(); //Make a new stream
        @CompThread  Stream s2 = s1.observeComp(); //Specify that it should be observed on a computation thread
        // :: error: (rxjava.thread.violation)
        s2.subscribe(uiEffectfulAction);//subscribe a callback with UI effect

        Stream s3 = new Stream(); //Make a new stream
        // :: error: (rxjava.thread.violation)
        s3.subscribe(uiEffectfulAction); //subscribe a callback with UI effect
    }

}
