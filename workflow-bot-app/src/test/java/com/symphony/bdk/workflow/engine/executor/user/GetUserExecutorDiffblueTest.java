package com.symphony.bdk.workflow.engine.executor.user;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.core.auth.AuthenticatorFactory;
import com.symphony.bdk.core.config.model.BdkConfig;
import com.symphony.bdk.core.service.connection.ConnectionService;
import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.ext.group.SymphonyGroupService;
import com.symphony.bdk.gen.api.model.V2UserDetail;
import com.symphony.bdk.workflow.engine.SpringBdkGateway;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.user.GetUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GetUserExecutorDiffblueTest {
  /**
   * Test {@link GetUserExecutor#execute(ActivityExecutorContext)}.
   * <ul>
   *   <li>Then calls {@link UserService#getUserDetail(Long)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetUserExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext); then calls getUserDetail(Long)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void GetUserExecutor.execute(ActivityExecutorContext)"})
  void testExecute_thenCallsGetUserDetail() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    GetUserExecutor getUserExecutor = new GetUserExecutor();
    GetUser getUser = mock(GetUser.class);
    when(getUser.getUserId()).thenReturn("42");
    UserService userService = mock(UserService.class);
    when(userService.getUserDetail(Mockito.<Long>any())).thenReturn(new V2UserDetail());
    SpringBdkGateway springBdkGateway = new SpringBdkGateway(mock(BdkConfig.class), mock(AuthenticatorFactory.class),
        mock(MessageService.class), mock(StreamService.class), userService, mock(ConnectionService.class),
        mock(SymphonyGroupService.class), mock(SessionService.class));

    ActivityExecutorContext<GetUser> context = mock(ActivityExecutorContext.class);
    doNothing().when(context).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(context.bdk()).thenReturn(springBdkGateway);
    when(context.getActivity()).thenReturn(getUser);

    // Act
    getUserExecutor.execute(context);

    // Assert
    verify(userService).getUserDetail(eq(42L));
    verify(context).bdk();
    verify(context).getActivity();
    verify(context).setOutputVariable(eq("user"), isA(Object.class));
    verify(getUser).getUserId();
  }
}
