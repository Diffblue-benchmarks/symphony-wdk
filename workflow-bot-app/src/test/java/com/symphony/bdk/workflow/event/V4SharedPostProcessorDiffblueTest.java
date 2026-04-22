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

@ContextConfiguration(classes = {V4SharedPostProcessor.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class V4SharedPostProcessorDiffblueTest {
  @Autowired
  private V4SharedPostProcessor v4SharedPostProcessor;

  @MockBean private RuntimeService runtimeService;

  /**
   * Test {@link V4SharedPostProcessor#V4SharedPostProcessor(RuntimeService)}.
   *
   * <p>Method under test: {@link V4SharedPostProcessor#V4SharedPostProcessor(RuntimeService)}
   */
  @Test
  @DisplayName("Test V4SharedPostProcessor(RuntimeService)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void V4SharedPostProcessor.<init>(RuntimeService)"})
  void testNewV4SharedPostProcessor() {
    // Arrange and Act
    RuntimeService runtimeServiceMock = mock(RuntimeService.class);
    V4SharedPostProcessor processor = new V4SharedPostProcessor(runtimeServiceMock);

    // Assert
    assertEquals("post-shared", processor.eventName);
    assertEquals(runtimeServiceMock, processor.runtimeService);
  }
}
