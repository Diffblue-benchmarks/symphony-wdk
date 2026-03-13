package com.symphony.bdk.workflow.swadl.v1.activity.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateSystemUserTest {

  @Test
  void constructorShouldSetTypeToSystem() {
    // Act
    CreateSystemUser createSystemUser = new CreateSystemUser();

    // Assert
    assertEquals("SYSTEM", createSystemUser.getType());
  }
}
