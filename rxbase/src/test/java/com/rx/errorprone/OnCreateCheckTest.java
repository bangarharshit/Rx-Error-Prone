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
public class OnCreateCheckTest {

  @Rule public TemporaryFolder temporaryFolder = new TemporaryFolder();

  private CompilationTestHelper compilationTestHelper;

  @Before
  public void setup() {
    compilationTestHelper = CompilationTestHelper.newInstance(OnCreateCheck.class, getClass());
    compilationTestHelper.setArgs(Arrays.asList("-d", temporaryFolder.getRoot().getAbsolutePath()));

  }

  @Test
  public void testPositiveCases() {
    compilationTestHelper.addSourceFile("OnCreateCheckPositiveCases.java").doTest();
  }

  @Test
  public void testNegativeCases() {
    compilationTestHelper.addSourceFile("OnCreateCheckNegativeCases.java").doTest();
  }
}
