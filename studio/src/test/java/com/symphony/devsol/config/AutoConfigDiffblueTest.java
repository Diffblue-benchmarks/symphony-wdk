package com.symphony.devsol.config;

import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class AutoConfigDiffblueTest {
  /**
   * Test {@link AutoConfig#init()}.
   *
   * <p>Method under test: {@link AutoConfig#init()}
   */
  @Test
  @DisplayName("Test init()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AutoConfig.init()"})
  void testInit() {
    // Arrange
    AutoConfig autoConfig = new AutoConfig();

    // Act and Assert (no exception thrown)
    autoConfig.init();
  }
}
