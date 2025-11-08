package com.symphony.bdk.workflow.engine.executor.group;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
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
import com.symphony.bdk.ext.group.gen.api.model.ReadGroup;
import com.symphony.bdk.workflow.engine.SpringBdkGateway;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.group.GetGroup;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GetGroupExecutorDiffblueTest {
  /**
   * Method under test: {@link GetGroupExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  void testExecute() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    GetGroupExecutor getGroupExecutor = new GetGroupExecutor();
    SymphonyGroupService groupService = mock(SymphonyGroupService.class);
    when(groupService.getGroup(Mockito.<String>any())).thenReturn(new ReadGroup());
    SpringBdkGateway springBdkGateway = new SpringBdkGateway(mock(BdkConfig.class), mock(AuthenticatorFactory.class),
        mock(MessageService.class), mock(StreamService.class), mock(UserService.class), mock(ConnectionService.class),
        groupService, mock(SessionService.class));

    ActivityExecutorContext<GetGroup> execution = mock(ActivityExecutorContext.class);
    doNothing().when(execution).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(execution.bdk()).thenReturn(springBdkGateway);
    when(execution.getActivity()).thenReturn(new GetGroup());

    // Act
    getGroupExecutor.execute(execution);

    // Assert
    verify(groupService).getGroup(isNull());
    verify(execution).bdk();
    verify(execution).getActivity();
    verify(execution).setOutputVariable(eq("group"), isA(Object.class));
  }
}
