package com.symphony.bdk.workflow.engine.executor.user;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.gen.api.model.V2UserDetail;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.user.GetUser;

import org.junit.jupiter.api.Test;

class GetUserExecutorTest {

  @Test
  void shouldGetUserWhenExecuteCalled() {
    // Arrange
    GetUserExecutor executor = new GetUserExecutor();
    ActivityExecutorContext<GetUser> context = mock(ActivityExecutorContext.class);
    GetUser activity = mock(GetUser.class);
    BdkGateway bdkGateway = mock(BdkGateway.class);
    UserService userService = mock(UserService.class);
    V2UserDetail userDetail = mock(V2UserDetail.class);

    when(context.getActivity()).thenReturn(activity);
    when(activity.getUserId()).thenReturn("123456");
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.users()).thenReturn(userService);
    when(userService.getUserDetail(123456L)).thenReturn(userDetail);

    // Act
    executor.execute(context);

    // Assert
    verify(context).setOutputVariable(eq("user"), eq(userDetail));
  }
}
