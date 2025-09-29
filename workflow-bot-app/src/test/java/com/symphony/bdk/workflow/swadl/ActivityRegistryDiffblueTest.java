package com.symphony.bdk.workflow.swadl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ActivityRegistryDiffblueTest {
  /**
   * Test {@link ActivityRegistry#getActivityTypes()}.
   *
   * <p>Method under test: {@link ActivityRegistry#getActivityTypes()}
   */
  @Test
  @DisplayName("Test getActivityTypes()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Set ActivityRegistry.getActivityTypes()"})
  void testGetActivityTypes() {
    // Arrange and Act
    Set<Class<? extends BaseActivity>> actualActivityTypes = ActivityRegistry.getActivityTypes();

    // Assert
    assertEquals(48, actualActivityTypes.size());
  }
}
