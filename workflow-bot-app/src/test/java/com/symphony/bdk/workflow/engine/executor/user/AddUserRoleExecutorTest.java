package com.symphony.bdk.workflow.engine.executor.user;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.core.service.user.constant.RoleId;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.user.AddUserRole;

import org.junit.jupiter.api.Test;

import java.util.List;

class AddUserRoleExecutorTest {

  private final AddUserRoleExecutor underTest = new AddUserRoleExecutor();

  @Test
  @SuppressWarnings("unchecked")
  void shouldAddRoleToUser() {
    ActivityExecutorContext<AddUserRole> context = mock(ActivityExecutorContext.class);
    BdkGateway bdk = mock(BdkGateway.class);
    UserService userService = mock(UserService.class);

    AddUserRole activity = new AddUserRole();
    activity.setUserIds(List.of(123L));
    activity.setRoles(List.of("INDIVIDUAL"));

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.users()).thenReturn(userService);

    underTest.execute(context);

    verify(userService).addRole(123L, RoleId.INDIVIDUAL);
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldAddMultipleRolesToMultipleUsers() {
    ActivityExecutorContext<AddUserRole> context = mock(ActivityExecutorContext.class);
    BdkGateway bdk = mock(BdkGateway.class);
    UserService userService = mock(UserService.class);

    AddUserRole activity = new AddUserRole();
    activity.setUserIds(List.of(111L, 222L));
    activity.setRoles(List.of("INDIVIDUAL", "SUPER_ADMINISTRATOR"));

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.users()).thenReturn(userService);

    underTest.execute(context);

    verify(userService).addRole(111L, RoleId.INDIVIDUAL);
    verify(userService).addRole(111L, RoleId.SUPER_ADMINISTRATOR);
    verify(userService).addRole(222L, RoleId.INDIVIDUAL);
    verify(userService).addRole(222L, RoleId.SUPER_ADMINISTRATOR);
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldDoNothingWhenUserIdsIsEmpty() {
    ActivityExecutorContext<AddUserRole> context = mock(ActivityExecutorContext.class);
    BdkGateway bdk = mock(BdkGateway.class);
    UserService userService = mock(UserService.class);

    AddUserRole activity = new AddUserRole();
    activity.setUserIds(List.of());
    activity.setRoles(List.of("INDIVIDUAL"));

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);

    underTest.execute(context);

    verifyNoInteractions(userService);
  }
}
