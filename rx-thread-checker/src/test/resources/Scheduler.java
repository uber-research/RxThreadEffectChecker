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
