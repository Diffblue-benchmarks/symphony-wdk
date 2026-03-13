package com.symphony.bdk.workflow.engine.executor.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.gen.api.model.UserSystemInfo;
import com.symphony.bdk.gen.api.model.V2UserDetail;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.user.CreateSystemUser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreateSystemUserExecutorTest {

  private CreateSystemUserExecutor executor;
  private ActivityExecutorContext<CreateSystemUser> context;
  private BdkGateway bdkGateway;
  private UserService userService;
  private CreateSystemUser activity;

  @BeforeEach
  void setUp() {
    executor = new CreateSystemUserExecutor();
    context = mock(ActivityExecutorContext.class);
    bdkGateway = mock(BdkGateway.class);
    userService = mock(UserService.class);
    activity = new CreateSystemUser();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.users()).thenReturn(userService);
  }

  @Test
  void shouldDelegateToCreateUserExecutor() {
    activity.setEmail("system.user@test.com");
    activity.setUsername("systemuser");
    activity.setFirstname("System");
    activity.setLastname("User");

    V2UserDetail createdUser = mock(V2UserDetail.class);
    UserSystemInfo userSystemInfo = mock(UserSystemInfo.class);
    when(userSystemInfo.getId()).thenReturn(12345L);
    when(createdUser.getUserSystemInfo()).thenReturn(userSystemInfo);

    V2UserDetail userDetail = mock(V2UserDetail.class);
    when(userService.create(any())).thenReturn(createdUser);
    when(userService.getUserDetail(any())).thenReturn(userDetail);

    executor.execute(context);

    verify(userService).create(any());
    verify(userService).getUserDetail(eq(12345L));
    verify(context).setOutputVariable(eq("user"), eq(userDetail));
  }
}
