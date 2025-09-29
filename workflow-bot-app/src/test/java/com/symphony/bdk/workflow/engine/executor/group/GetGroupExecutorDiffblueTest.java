package com.symphony.bdk.workflow.engine.executor.group;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.core.auth.AuthenticatorFactory;
import com.symphony.bdk.core.config.model.BdkConfig;
import com.symphony.bdk.core.service.connection.ConnectionService;
import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.ext.group.SymphonyGroupService;
import com.symphony.bdk.ext.group.gen.api.model.ReadGroup;
import com.symphony.bdk.workflow.engine.SpringBdkGateway;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.group.GetGroup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GetGroupExecutorDiffblueTest {
  /**
   * Test {@link GetGroupExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link SymphonyGroupService} {@link SymphonyGroupService#getGroup(String)} return
   *       {@link ReadGroup} (default constructor).
   *   <li>Then calls {@link SymphonyGroupService#getGroup(String)}.
   * </ul>
   *
   * <p>Method under test: {@link GetGroupExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given SymphonyGroupService getGroup(String) return ReadGroup (default constructor); then calls getGroup(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void GetGroupExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenSymphonyGroupServiceGetGroupReturnReadGroup_thenCallsGetGroup() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    GetGroupExecutor getGroupExecutor = new GetGroupExecutor();

    SymphonyGroupService groupService = mock(SymphonyGroupService.class);
    when(groupService.getGroup(Mockito.<String>any())).thenReturn(new ReadGroup());
    SpringBdkGateway springBdkGateway =
        new SpringBdkGateway(
            mock(BdkConfig.class),
            mock(AuthenticatorFactory.class),
            mock(MessageService.class),
            mock(StreamService.class),
            mock(UserService.class),
            mock(ConnectionService.class),
            groupService,
            mock(SessionService.class));

    ActivityExecutorContext<GetGroup> execution = mock(ActivityExecutorContext.class);
    doNothing().when(execution).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(execution.bdk()).thenReturn(springBdkGateway);
    when(execution.getActivity()).thenReturn(new GetGroup());

    // Act
    getGroupExecutor.execute(execution);

    // Assert
    verify(groupService).getGroup(null);
    verify(execution).bdk();
    verify(execution).getActivity();
    verify(execution).setOutputVariable(eq("group"), isA(Object.class));
  }
}
