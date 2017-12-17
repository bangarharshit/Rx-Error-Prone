package com.rx.errorprone;

import com.google.errorprone.CompilationTestHelper;
import com.rx.errorprone.rx1.OnCreateCheck;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class SubscribeOnErrorMissingCheckTest {
  @Rule public TemporaryFolder temporaryFolder = new TemporaryFolder();

  private CompilationTestHelper compilationTestHelper;

  @Before
  public void setup() {
    compilationTestHelper = CompilationTestHelper.newInstance(SubscribeOnErrorMissingCheck.class, getClass());
    compilationTestHelper.setArgs(Arrays.asList("-d", temporaryFolder.getRoot().getAbsolutePath()));
  }

  @Test
  public void testPositiveCases() {
    compilationTestHelper.addSourceFile("SubscribeOnErrorMissingCheckPositiveCases.java").doTest();
  }

  @Test
  public void testNegativeCases() {
    compilationTestHelper.addSourceFile("SubscribeOnErrorMissingCheckNegativeCases.java").doTest();
  }
}
