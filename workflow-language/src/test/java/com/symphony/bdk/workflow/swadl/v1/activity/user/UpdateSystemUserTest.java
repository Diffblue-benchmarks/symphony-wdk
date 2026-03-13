package com.symphony.bdk.workflow.swadl.v1.activity.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UpdateSystemUserTest {

  @Test
  void constructorShouldSetTypeToSystem() {
    UpdateSystemUser user = new UpdateSystemUser();

    assertEquals("SYSTEM", user.getType());
  }
}
