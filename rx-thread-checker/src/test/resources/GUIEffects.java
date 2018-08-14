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

import org.checkerframework.checker.guieffect.qual.UIEffect;
import org.checkerframework.checker.guieffect.qual.SafeEffect;

// This test case is simply a sanity-check that the GUI Effect checker (provided with checker-framework) works as expected.
// No custom checker features are used.
public class GUIEffects {

    @UIEffect
    private static void uiEffectMethod() {
        System.out.print("Doing some UI work");
        for(int i = 0 ; i < 3 ; i++){ System.out.print("."); sleep(100);}
        System.out.println("");
    }

    @SafeEffect
    private static void noEffectMethod() {
        System.out.println("Calling method with UI effect from method marked safe");
        // :: error: (call.invalid.ui)
        uiEffectMethod(); // Error: Calling UIEffectful method from effect-free method
    }

    private static void sleep(int millis) { try { Thread.sleep(millis); } catch(InterruptedException e) { e.printStackTrace(); } }


}
