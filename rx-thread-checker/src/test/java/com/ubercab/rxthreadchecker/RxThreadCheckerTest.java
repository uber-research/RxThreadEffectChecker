package com.ubercab.rxthreadchecker;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import org.checkerframework.framework.test.CheckerFrameworkPerDirectoryTest;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@SuppressWarnings("CheckTestExtendsBaseClass")
@RunWith(JUnit4.class)
public class RxThreadCheckerTest extends CheckerFrameworkPerDirectoryTest {
  public RxThreadCheckerTest() {
    super(
        testSources(),
        com.ubercab.rxthreadchecker.RxThreadChecker.class,
        null,
        "-Anomsgtext",
        "-Anocheckjdk");
  }

  private static List<File> testSources() {
    return Arrays.asList(
        new File("src/test/resources").listFiles());
  }
}
