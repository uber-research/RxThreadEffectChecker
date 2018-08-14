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
