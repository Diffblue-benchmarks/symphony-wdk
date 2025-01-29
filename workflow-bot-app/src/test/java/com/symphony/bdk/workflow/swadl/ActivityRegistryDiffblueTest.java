package com.symphony.bdk.workflow.swadl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ActivityRegistryDiffblueTest {
  /**
   * Test {@link ActivityRegistry#getActivityTypes()}.
   * <p>
   * Method under test: {@link ActivityRegistry#getActivityTypes()}
   */
  @Test
  @DisplayName("Test getActivityTypes()")
  void testGetActivityTypes() {
    // Arrange and Act
    Set<Class<? extends BaseActivity>> actualActivityTypes = ActivityRegistry.getActivityTypes();

    // Assert
    assertEquals(48, actualActivityTypes.size());
  }
}
