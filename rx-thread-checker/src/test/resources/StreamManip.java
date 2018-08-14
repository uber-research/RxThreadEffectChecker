package com.ubercab.rxthreadchecker.testdata;

import com.ubercab.rxthreadchecker.testdata.Stream;
import com.ubercab.rxthreadchecker.qual.*;

public class StreamManip {

    //Correct usage examples; no type-checking errors should be raised.
    private void safeStreamManip() {
        @AnyThread Stream foo1 = new Stream();
        @UIThread Stream foo2 = foo1.observeUI();
        @UIThread Stream foo3 = foo2.doWork(0);

        @AnyThread Stream bar1 = new Stream();
        @CompThread Stream bar2 = bar1.observeComp();
        @CompThread Stream bar3 = bar2.doWork(0);
    }


    //Incorrect usage: doWork, when called on a @UIThread Stream, should give back a @UIThread Stream
    private void unsafeStreamManip() {
        @AnyThread Stream s1 = new Stream();
        @UIThread  Stream s2 = s1.observeUI();
        // :: error: (assignment.type.incompatible)
        @CompThread  Stream s3 = s2.doWork(0); //Error: doWork shouldn't modify the threadpool.
    }

}
