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

@ContextConfiguration(classes = {V4UserLeftRoomProcessor.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class V4UserLeftRoomProcessorDiffblueTest {
  @Autowired
  private V4UserLeftRoomProcessor v4UserLeftRoomProcessor;

  @MockBean
  private RuntimeService runtimeService;

  /**
   * Test {@link V4UserLeftRoomProcessor#V4UserLeftRoomProcessor(RuntimeService)}.
   *
   * <p>Method under test: {@link V4UserLeftRoomProcessor#V4UserLeftRoomProcessor(RuntimeService)}
   */
  @Test
  @DisplayName("Test new V4UserLeftRoomProcessor(RuntimeService)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void V4UserLeftRoomProcessor.<init>(RuntimeService)"})
  void testNewV4UserLeftRoomProcessor() {
    // Arrange and Act
    RuntimeService runtimeService = mock(RuntimeService.class);
    V4UserLeftRoomProcessor processor = new V4UserLeftRoomProcessor(runtimeService);

    // Assert
    assertEquals("user-left-room", processor.eventName);
    assertEquals(runtimeService, processor.runtimeService);
  }
}
