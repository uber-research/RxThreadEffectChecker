/*
 * Copyright (C) 2017. Uber Technologies
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.uber.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.Observable;
import java.util.concurrent.TimeUnit;
import org.checkerframework.checker.guieffect.qual.UIEffect;

/** Sample activity. */
public class MainActivity extends AppCompatActivity {

    @Override
    @UIEffect
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	
	Observable<Object> stream = Observable.just(new Object());

	stream
	    .delay(1000, TimeUnit.MILLISECONDS)
	    .observeOn(AndroidSchedulers.mainThread())
	    .subscribe( o -> doSomethingOnUI(o) ); // This should not error, since we're performing UI effects on a stream running on the UI thread

	stream
	    .delay(1000, TimeUnit.MILLISECONDS)
	    .subscribe( o -> doSomethingOnUI(o) ); // This should error with `rxjava.thread.violation`, since we're performing UI effects on a stream running on a computation thread
    }
    
    @UIEffect
    private void doSomethingOnUI(Object o) {
	// fake method for example
    }
}
