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

import io.reactivex.*;
import io.reactivex.functions.*;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.android.schedulers.AndroidSchedulers;
import com.ubercab.rxthreadchecker.qual.*;
import org.checkerframework.checker.guieffect.qual.*;


/**
 * Combine UI effect typing with RX Observable thread typing
 */
public class ObservablesWithLambdaConsumers {


    @UIEffect
    private static void uiEffectMethod() {
        System.out.print("Doing some UI work");
    }

    @SafeEffect
    private static void noEffectMethod() {
        System.out.print("Doing some background work");
    }

    private static void srcVisibleDoUIMethod(@UI Consumer<Object> uiEffectfulConsumer) {
        Observable.empty().observeOn(AndroidSchedulers.mainThread()).doAfterNext(uiEffectfulConsumer);
    }

    private void safeConsumerLambdaEffects()
    {
        Observable.empty().doAfterNext(arg -> noEffectMethod());
        srcVisibleDoUIMethod(arg -> uiEffectMethod());
        Observable.empty().observeOn(AndroidSchedulers.mainThread()).doAfterNext(arg -> uiEffectMethod());
    }

    private void unsafeConsumerLambdaEffects()
    {
        // :: error: (rxjava.thread.violation)
        Observable.empty().doAfterNext(arg -> uiEffectMethod());
    }

}
