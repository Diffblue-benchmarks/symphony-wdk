package com.symphony.bdk.workflow.engine.executor.user;

import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.gen.api.model.V2UserDetail;
import com.symphony.bdk.gen.api.model.V2UserSystemInfo;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.user.CreateSystemUser;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateSystemUserExecutorTest {

  @Mock
  private ActivityExecutorContext<CreateSystemUser> context;

  @Mock
  private BdkGateway bdk;

  @Mock
  private UserService userService;

  @Test
  void shouldCreateSystemUserByDelegatingToCreateUserExecutor() {
    CreateSystemUser activity = new CreateSystemUser();
    activity.setEmail("test@example.com");
    activity.setUsername("testuser");

    V2UserSystemInfo userSystemInfo = new V2UserSystemInfo();
    userSystemInfo.setId(42L);

    V2UserDetail createdUser = new V2UserDetail();
    createdUser.setUserSystemInfo(userSystemInfo);

    V2UserDetail userDetail = new V2UserDetail();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.users()).thenReturn(userService);
    when(userService.create(any())).thenReturn(createdUser);
    when(userService.getUserDetail(42L)).thenReturn(userDetail);

    new CreateSystemUserExecutor().execute(context);

    verify(userService).create(any());
    verify(userService).getUserDetail(42L);
    verify(context).setOutputVariable("user", userDetail);
  }
}
