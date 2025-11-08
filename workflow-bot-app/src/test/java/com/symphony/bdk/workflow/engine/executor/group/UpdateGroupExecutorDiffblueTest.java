package com.symphony.bdk.workflow.engine.executor.group;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.group.UpdateGroup;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UpdateGroupExecutor.class})
@ExtendWith(SpringExtension.class)
class UpdateGroupExecutorDiffblueTest {
  @Autowired
  private UpdateGroupExecutor updateGroupExecutor;

  /**
   * Test {@link UpdateGroupExecutor#execute(ActivityExecutorContext)}.
   * <ul>
   *   <li>Given {@link UpdateGroup} (default constructor).</li>
   *   <li>Then calls {@link ActivityExecutorContext#getActivity()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UpdateGroupExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext); given UpdateGroup (default constructor); then calls getActivity()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void UpdateGroupExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenUpdateGroup_thenCallsGetActivity() throws IOException {
    // Arrange
    ActivityExecutorContext<UpdateGroup> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenReturn(new UpdateGroup());

    // Act
    updateGroupExecutor.execute(execution);

    // Assert
    verify(execution, atLeast(1)).getActivity();
  }
}
