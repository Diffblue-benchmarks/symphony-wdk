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

@ContextConfiguration(classes = {V4InstantMessageCreatedProcessor.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class V4InstantMessageCreatedProcessorDiffblueTest {
  @MockBean private RuntimeService runtimeService;

  @Autowired private V4InstantMessageCreatedProcessor v4InstantMessageCreatedProcessor;

  /**
   * Test {@link V4InstantMessageCreatedProcessor#V4InstantMessageCreatedProcessor(RuntimeService)}.
   *
   * <p>Method under test: {@link
   * V4InstantMessageCreatedProcessor#V4InstantMessageCreatedProcessor(RuntimeService)}
   */
  @Test
  @DisplayName("Test V4InstantMessageCreatedProcessor(RuntimeService)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void V4InstantMessageCreatedProcessor.<init>(RuntimeService)"
  })
  void testNewV4InstantMessageCreatedProcessor() {
    // Arrange and Act
    // The processor is autowired via Spring context (constructor is invoked during context setup)

    // Assert
    assertEquals("im-created", v4InstantMessageCreatedProcessor.eventName);
    assertEquals(runtimeService, v4InstantMessageCreatedProcessor.runtimeService);
  }
}
