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
package com.ubercab.rxthreadchecker;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.checkerframework.framework.test.CheckerFrameworkPerDirectoryTest;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class RxThreadCheckerTest extends CheckerFrameworkPerDirectoryTest {
  public RxThreadCheckerTest() {
    super(
        testSources(),
        com.ubercab.rxthreadchecker.RxThreadChecker.class,
        null,
        "-Anomsgtext",
            "-Anocheckjdk",
            "-AprintErrorStack",
            //"-AstubWarnIfNotFound",
            //"-AstubWarnIfNotFoundIgnoresClasses",
            "-Astubs="+testStubString());
  }

  private static List<File> testSources() {
    return Arrays.asList(
        new File("src/test/resources").listFiles());
  }

  private static String testStubString() {
    return testStubFiles().stream().map(f -> f.getAbsolutePath()).collect(Collectors.joining(":"));
  }

  private static List<File> testStubFiles() {
    return Arrays.asList(
            new File("src/main/resources/stub/").listFiles());
  }
}
