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
package com.ubercab.rxthreadchecker.qual;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotates a method to specify the relationship between its receiver's thread type and its
 * arguments' effect types. The effects of the arguments at positions {@param value} must be
 * compatible with the receiver's thread.
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD})
public @interface SubscribeEffects {

  // The indices of parameters whose effect is applied on the method receiver's thread.
  int[] value();
}
