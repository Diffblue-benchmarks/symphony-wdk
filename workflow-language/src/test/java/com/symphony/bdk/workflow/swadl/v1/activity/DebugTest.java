package com.symphony.bdk.workflow.swadl.v1.activity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DebugTest {

  @Test
  void shouldSetRandomIdOnConstruction() {
    Debug debug = new Debug();

    assertNotNull(debug.getId());
    assertTrue(debug.getId().startsWith("randomId-"));
  }
}
