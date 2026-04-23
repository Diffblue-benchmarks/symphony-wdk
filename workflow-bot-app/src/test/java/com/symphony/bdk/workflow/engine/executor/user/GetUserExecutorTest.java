package com.symphony.bdk.workflow.engine.executor.user;

import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.gen.api.model.V2UserDetail;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.user.GetUser;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetUserExecutorTest {

  @Mock
  private ActivityExecutorContext<GetUser> context;

  @Mock
  private BdkGateway bdk;

  @Mock
  private UserService userService;

  @Test
  void shouldGetUserDetailAndSetOutputVariable() {
    GetUser activity = new GetUser();
    activity.setUserId("12345");
    V2UserDetail userDetail = new V2UserDetail();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.users()).thenReturn(userService);
    when(userService.getUserDetail(12345L)).thenReturn(userDetail);

    new GetUserExecutor().execute(context);

    verify(userService).getUserDetail(12345L);
    verify(context).setOutputVariable("user", userDetail);
  }
}
