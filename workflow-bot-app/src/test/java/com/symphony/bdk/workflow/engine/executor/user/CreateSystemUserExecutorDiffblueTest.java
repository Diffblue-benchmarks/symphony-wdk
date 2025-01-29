package com.symphony.bdk.workflow.engine.executor.user;

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
import com.symphony.bdk.gen.api.model.UserSystemInfo;
import com.symphony.bdk.gen.api.model.V2UserCreate;
import com.symphony.bdk.gen.api.model.V2UserDetail;
import com.symphony.bdk.workflow.engine.SpringBdkGateway;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.user.CreateSystemUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CreateSystemUserExecutorDiffblueTest {
  /**
   * Test {@link CreateSystemUserExecutor#execute(ActivityExecutorContext)}.
   * <ul>
   *   <li>Given {@link V2UserDetail} (default constructor) userSystemInfo
   * {@link UserSystemInfo} (default constructor).</li>
   *   <li>Then calls {@link UserService#create(V2UserCreate)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CreateSystemUserExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext); given V2UserDetail (default constructor) userSystemInfo UserSystemInfo (default constructor); then calls create(V2UserCreate)")
  void testExecute_givenV2UserDetailUserSystemInfoUserSystemInfo_thenCallsCreate() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    CreateSystemUserExecutor createSystemUserExecutor = new CreateSystemUserExecutor();

    V2UserDetail v2UserDetail = new V2UserDetail();
    v2UserDetail.userSystemInfo(new UserSystemInfo());
    UserService userService = mock(UserService.class);
    when(userService.getUserDetail(Mockito.<Long>any())).thenReturn(new V2UserDetail());
    when(userService.create(Mockito.<V2UserCreate>any())).thenReturn(v2UserDetail);
    SpringBdkGateway springBdkGateway = new SpringBdkGateway(mock(BdkConfig.class), mock(AuthenticatorFactory.class),
        mock(MessageService.class), mock(StreamService.class), userService, mock(ConnectionService.class),
        mock(SymphonyGroupService.class), mock(SessionService.class));

    ActivityExecutorContext<CreateSystemUser> context = mock(ActivityExecutorContext.class);
    doNothing().when(context).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(context.bdk()).thenReturn(springBdkGateway);
    when(context.getActivity()).thenReturn(new CreateSystemUser());

    // Act
    createSystemUserExecutor.execute(context);

    // Assert
    verify(userService).create(isA(V2UserCreate.class));
    verify(userService).getUserDetail(isNull());
    verify(context).bdk();
    verify(context).getActivity();
    verify(context).setOutputVariable(eq("user"), isA(Object.class));
  }
}
