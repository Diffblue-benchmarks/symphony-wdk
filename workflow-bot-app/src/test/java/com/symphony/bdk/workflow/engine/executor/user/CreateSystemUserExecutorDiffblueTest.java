package com.symphony.bdk.workflow.engine.executor.user;

import static org.mockito.ArgumentMatchers.isA;
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
import com.symphony.bdk.gen.api.model.UserSystemInfo;
import com.symphony.bdk.gen.api.model.V2UserCreate;
import com.symphony.bdk.gen.api.model.V2UserDetail;
import com.symphony.bdk.workflow.engine.SpringBdkGateway;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.user.CreateSystemUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CreateSystemUserExecutorDiffblueTest {
  /**
   * Test {@link CreateSystemUserExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>When {@link CreateSystemUser} (default constructor).
   *   <li>Then calls {@link UserService#getUserDetail(Long)}.
   * </ul>
   *
   * <p>Method under test: {@link CreateSystemUserExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); when CreateSystemUser (default constructor); then calls getUserDetail(Long)")
  @Tag("ContributionFromDiffblue")
  void testExecute_whenCreateSystemUser_thenCallsGetUserDetail() {
    // Arrange
    CreateSystemUserExecutor createSystemUserExecutor = new CreateSystemUserExecutor();

    UserService userService = mock(UserService.class);
    when(userService.create(Mockito.<V2UserCreate>any()))
        .thenReturn(new V2UserDetail().userSystemInfo(new UserSystemInfo().id(42L)));
    when(userService.getUserDetail(Mockito.<Long>any())).thenReturn(new V2UserDetail());
    SpringBdkGateway springBdkGateway =
        new SpringBdkGateway(
            mock(BdkConfig.class),
            mock(AuthenticatorFactory.class),
            mock(MessageService.class),
            mock(StreamService.class),
            userService,
            mock(ConnectionService.class),
            mock(SymphonyGroupService.class),
            mock(SessionService.class));

    ActivityExecutorContext<CreateSystemUser> context = mock(ActivityExecutorContext.class);
    doNothing().when(context).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(context.getActivity()).thenReturn(new CreateSystemUser());
    when(context.bdk()).thenReturn(springBdkGateway);

    // Act
    createSystemUserExecutor.execute(context);

    // Assert
    verify(userService).create(isA(V2UserCreate.class));
    verify(userService).getUserDetail(42L);
    verify(context).bdk();
    verify(context).getActivity();
    verify(context).setOutputVariable(Mockito.eq("user"), isA(Object.class));
  }
}
