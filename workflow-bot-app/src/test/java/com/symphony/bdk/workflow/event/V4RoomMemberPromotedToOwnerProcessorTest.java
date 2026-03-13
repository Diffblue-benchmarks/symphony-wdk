package com.symphony.bdk.workflow.event;

import org.camunda.bpm.engine.RuntimeService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class V4RoomMemberPromotedToOwnerProcessorTest {

  @Test
  void constructorShouldInitializeProcessorWithRuntimeService() {
    RuntimeService mockRuntimeService = mock(RuntimeService.class);

    V4RoomMemberPromotedToOwnerProcessor processor = new V4RoomMemberPromotedToOwnerProcessor(mockRuntimeService);

    assertNotNull(processor);
  }
}
