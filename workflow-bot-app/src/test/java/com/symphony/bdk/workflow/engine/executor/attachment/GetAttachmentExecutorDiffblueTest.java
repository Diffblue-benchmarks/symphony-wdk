package com.symphony.bdk.workflow.engine.executor.attachment;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.attachment.GetAttachment;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {GetAttachmentExecutor.class})
@ExtendWith(SpringExtension.class)
class GetAttachmentExecutorDiffblueTest {
  @Autowired private GetAttachmentExecutor getAttachmentExecutor;

  /**
   * Test {@link GetAttachmentExecutor#execute(ActivityExecutorContext)}.
   *
   * <p>Method under test: {@link GetAttachmentExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void GetAttachmentExecutor.execute(ActivityExecutorContext)"})
  void testExecute() throws IOException {
    // Arrange
    ActivityExecutorContext<GetAttachment> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenThrow(new IllegalArgumentException());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> getAttachmentExecutor.execute(execution));
    verify(execution).getActivity();
  }
}
