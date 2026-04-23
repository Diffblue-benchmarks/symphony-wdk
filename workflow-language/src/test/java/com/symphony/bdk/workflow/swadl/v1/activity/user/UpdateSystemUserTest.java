package com.symphony.bdk.workflow.swadl.v1.activity.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UpdateSystemUserTest {

  @Test
  void constructorSetsTypeToSystem() {
    UpdateSystemUser updateSystemUser = new UpdateSystemUser();

    assertEquals("SYSTEM", updateSystemUser.getType());
  }
}
