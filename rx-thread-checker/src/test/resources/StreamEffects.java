/*
 *  Copyright (c) 2017-2018 Uber Technologies, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
