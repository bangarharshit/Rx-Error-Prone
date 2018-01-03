package com.rx.errorprone;

import com.google.errorprone.CompilationTestHelper;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class DanglingSubscriptionCheckTest {

  @Rule public TemporaryFolder temporaryFolder = new TemporaryFolder();

  private CompilationTestHelper compilationTestHelper;

  @Before
  public void setup() {
    compilationTestHelper =
        CompilationTestHelper.newInstance(DanglingSubscriptionCheck.class, getClass());
    compilationTestHelper.setArgs(
        Arrays.asList(
            "-d",
            temporaryFolder.getRoot().getAbsolutePath(),
            "-XepOpt:LifeCycleClasses" + "=com.rx.errorprone.ComponentWithLifeCycle"));
  }

  @Test
  public void testPositiveCases() {
    compilationTestHelper.addSourceFile("DanglingSubscriptionPositiveCases.java").doTest();
  }

  @Test
  public void testNegativeCases() {
    compilationTestHelper.addSourceFile("DanglingSubscriptionNegativeCases.java").doTest();
  }

  @Test
  public void testNegativeCases2() {
    compilationTestHelper.addSourceFile("DanglingSubscriptionNegativeCases2.java").doTest();
  }
}
