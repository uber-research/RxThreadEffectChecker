package com.ubercab.rxthreadchecker.testdata;

import com.ubercab.rxthreadchecker.qual.*;
import org.checkerframework.checker.guieffect.qual.*;

// Mock stream library meant to mimic key features of rx.Observable without a full implementation:
//  * Newly instantiated streams carry no guarantee of which thread they'll be observed on
//      ( constructor returns annotated @AnyThread)
//  * There exist method(s) that explicitly restrict the thread-pool
//      (i.e. Stream#observeUI, Stream#observeComp) (e.g. Observable#observeOn(Schedulers.IO), etc.)
//  * There exist method(s) that do work over the stream without modifying its threadpool
//      (i.e. Stream#doWork) (e.g. Observable#map, Observable#filter, etc.)
//  * There exist method(s) that attach potentially effect-ful code to a stream, which must be checked against its threadpool
//      (i.e. Stream#subscribe) (e.g. Observable#subscribe)
public class Stream {

    public @AnyThread Stream(){}

    // :: warning: (cast.unsafe)
    public @UIThread Stream observeUI(){return (@UIThread Stream) this;}

    // :: warning: (cast.unsafe)
    public @CompThread Stream observeComp(){return (@CompThread Stream) this;}

    // :: warning: (cast.unsafe)
    public @PolyThread Stream observeOn(@PolyThread Scheduler thread) {return (@PolyThread Stream) this;}

    public @PolyThread Stream doWork(@PolyThread Stream this, Object callback) {return this;}

    @SubscribeEffects({0}) public @PolyThread Stream subscribe(@PolyThread Stream this, @PolyUI Object callback) {return this;}
}
