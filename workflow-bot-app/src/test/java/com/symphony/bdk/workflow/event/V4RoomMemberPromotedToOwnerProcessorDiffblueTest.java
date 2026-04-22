package com.symphony.bdk.workflow.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.camunda.bpm.engine.RuntimeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {V4RoomMemberPromotedToOwnerProcessor.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class V4RoomMemberPromotedToOwnerProcessorDiffblueTest {
  @MockBean private RuntimeService runtimeService;

  @Autowired private V4RoomMemberPromotedToOwnerProcessor v4RoomMemberPromotedToOwnerProcessor;

  /**
   * Test {@link V4RoomMemberPromotedToOwnerProcessor#V4RoomMemberPromotedToOwnerProcessor(RuntimeService)}.
   *
   * <p>Method under test: {@link
   * V4RoomMemberPromotedToOwnerProcessor#V4RoomMemberPromotedToOwnerProcessor(RuntimeService)}
   */
  @Test
  @DisplayName("Test V4RoomMemberPromotedToOwnerProcessor(RuntimeService)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void V4RoomMemberPromotedToOwnerProcessor.<init>(RuntimeService)"
  })
  void testNewV4RoomMemberPromotedToOwnerProcessor() {
    // Arrange and Act
    // The processor is autowired via Spring context (constructor is invoked during context setup)

    // Assert
    assertEquals("room-member-promoted-to-owner-event", v4RoomMemberPromotedToOwnerProcessor.eventName);
    assertEquals(runtimeService, v4RoomMemberPromotedToOwnerProcessor.runtimeService);
  }
}
