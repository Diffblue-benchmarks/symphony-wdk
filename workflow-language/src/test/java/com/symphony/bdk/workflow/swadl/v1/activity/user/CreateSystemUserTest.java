package com.symphony.bdk.workflow.swadl.v1.activity.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateSystemUserTest {

  @Test
  void shouldSetTypeToSystemOnConstruction() {
    CreateSystemUser createSystemUser = new CreateSystemUser();

    assertEquals("SYSTEM", createSystemUser.getType());
  }
}
