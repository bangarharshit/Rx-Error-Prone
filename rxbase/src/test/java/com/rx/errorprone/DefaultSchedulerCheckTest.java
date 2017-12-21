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
public class DefaultSchedulerCheckTest {

  @Rule public TemporaryFolder temporaryFolder = new TemporaryFolder();

  private CompilationTestHelper compilationTestHelper;

  @Before
  public void setup() {
    compilationTestHelper =
        CompilationTestHelper.newInstance(DefaultSchedulerCheck.class, getClass());
    compilationTestHelper.setArgs(Arrays.asList("-d", temporaryFolder.getRoot().getAbsolutePath()));
  }

  @Test
  public void testPositiveCases() {
    compilationTestHelper.addSourceFile("DefaultSchedulerCheckPositiveCases.java").doTest();
  }

  @Test
  public void testNegativeCases() {
    compilationTestHelper.addSourceFile("DefaultSchedulerCheckNegativeCases.java").doTest();
  }
}
