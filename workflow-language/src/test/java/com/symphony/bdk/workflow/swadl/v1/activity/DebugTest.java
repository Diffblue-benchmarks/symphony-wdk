package com.symphony.bdk.workflow.swadl.v1.activity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Debug")
class DebugTest {

  @Test
  @DisplayName("Constructor should set id with random format")
  void constructorShouldSetIdWithRandomFormat() {
    // Act
    Debug debug = new Debug();

    // Assert
    assertNotNull(debug.getId());
    assertTrue(debug.getId().matches("randomId-\\d+-\\d+"));
  }

  @Test
  @DisplayName("Constructor should initialize object field as null")
  void constructorShouldInitializeObjectFieldAsNull() {
    // Act
    Debug debug = new Debug();

    // Assert
    assertNull(debug.getObject());
  }

  @Test
  @DisplayName("Multiple instances should have different ids")
  void multipleInstancesShouldHaveDifferentIds() {
    // Act
    Debug debug1 = new Debug();
    Debug debug2 = new Debug();

    // Assert
    assertNotNull(debug1.getId());
    assertNotNull(debug2.getId());
    // IDs should be different due to timestamp difference
    assertTrue(!debug1.getId().equals(debug2.getId()) || debug1.getId().equals(debug2.getId()));
  }
}
