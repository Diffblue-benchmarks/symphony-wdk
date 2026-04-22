package com.symphony.bdk.workflow.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

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

@ContextConfiguration(classes = {V4RoomReactivatedProcessor.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class V4RoomReactivatedProcessorDiffblueTest {
  @Autowired
  private V4RoomReactivatedProcessor v4RoomReactivatedProcessor;

  @MockBean private RuntimeService runtimeService;

  /**
   * Test {@link V4RoomReactivatedProcessor#V4RoomReactivatedProcessor(RuntimeService)}.
   *
   * <p>Method under test: {@link V4RoomReactivatedProcessor#V4RoomReactivatedProcessor(RuntimeService)}
   */
  @Test
  @DisplayName("Test V4RoomReactivatedProcessor(RuntimeService)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void V4RoomReactivatedProcessor.<init>(RuntimeService)"})
  void testNewV4RoomReactivatedProcessor() {
    // Arrange and Act
    RuntimeService runtimeServiceMock = mock(RuntimeService.class);
    V4RoomReactivatedProcessor processor = new V4RoomReactivatedProcessor(runtimeServiceMock);

    // Assert
    assertEquals("room-reactivated", processor.eventName);
    assertEquals(runtimeServiceMock, processor.runtimeService);
  }
}
