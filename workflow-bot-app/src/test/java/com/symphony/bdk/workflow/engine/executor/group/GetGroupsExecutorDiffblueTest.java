package com.symphony.bdk.workflow.engine.executor.group;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.core.auth.AuthenticatorFactory;
import com.symphony.bdk.core.config.model.BdkConfig;
import com.symphony.bdk.core.service.connection.ConnectionService;
import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.ext.group.SymphonyGroupService;
import com.symphony.bdk.ext.group.gen.api.model.GroupList;
import com.symphony.bdk.ext.group.gen.api.model.SortOrder;
import com.symphony.bdk.ext.group.gen.api.model.Status;
import com.symphony.bdk.workflow.engine.SpringBdkGateway;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.group.GetGroups;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GetGroupsExecutorDiffblueTest {
  /**
   * Test {@link GetGroupsExecutor#execute(ActivityExecutorContext)}.
   * <ul>
   *   <li>Then calls
   * {@link SymphonyGroupService#listGroups(Status, String, String, Integer, SortOrder)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetGroupsExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext); then calls listGroups(Status, String, String, Integer, SortOrder)")
  void testExecute_thenCallsListGroups() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    GetGroupsExecutor getGroupsExecutor = new GetGroupsExecutor();
    SymphonyGroupService groupService = mock(SymphonyGroupService.class);
    when(groupService.listGroups(Mockito.<Status>any(), Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<Integer>any(), Mockito.<SortOrder>any())).thenReturn(new GroupList());
    SpringBdkGateway springBdkGateway = new SpringBdkGateway(mock(BdkConfig.class), mock(AuthenticatorFactory.class),
        mock(MessageService.class), mock(StreamService.class), mock(UserService.class), mock(ConnectionService.class),
        groupService, mock(SessionService.class));

    ActivityExecutorContext<GetGroups> execution = mock(ActivityExecutorContext.class);
    doNothing().when(execution).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(execution.bdk()).thenReturn(springBdkGateway);
    when(execution.getActivity()).thenReturn(new GetGroups());

    // Act
    getGroupsExecutor.execute(execution);

    // Assert
    verify(groupService).listGroups(isNull(), isNull(), isNull(), eq(100), eq(SortOrder.ASC));
    verify(execution).bdk();
    verify(execution, atLeast(1)).getActivity();
    verify(execution).setOutputVariable(eq("groups"), isA(Object.class));
  }
}
