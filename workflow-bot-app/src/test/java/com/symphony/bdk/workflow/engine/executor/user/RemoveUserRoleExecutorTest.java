package com.symphony.bdk.workflow.engine.executor.user;

import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.core.service.user.constant.RoleId;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.user.RemoveUserRole;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RemoveUserRoleExecutorTest {

  @InjectMocks
  private RemoveUserRoleExecutor executor;

  @Mock
  private ActivityExecutorContext<RemoveUserRole> context;

  @Mock
  private BdkGateway bdk;

  @Mock
  private UserService userService;

  @Test
  void shouldRemoveRoleFromUser() {
    RemoveUserRole activity = new RemoveUserRole();
    activity.setUserIds(List.of(123L));
    activity.setRoles(List.of("INDIVIDUAL"));

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.users()).thenReturn(userService);

    executor.execute(context);

    verify(userService).removeRole(123L, RoleId.valueOf("INDIVIDUAL"));
  }

  @Test
  void shouldRemoveMultipleRolesFromMultipleUsers() {
    RemoveUserRole activity = new RemoveUserRole();
    activity.setUserIds(List.of(111L, 222L));
    activity.setRoles(List.of("INDIVIDUAL", "SUPER_ADMINISTRATOR"));

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.users()).thenReturn(userService);

    executor.execute(context);

    verify(userService).removeRole(111L, RoleId.valueOf("INDIVIDUAL"));
    verify(userService).removeRole(111L, RoleId.valueOf("SUPER_ADMINISTRATOR"));
    verify(userService).removeRole(222L, RoleId.valueOf("INDIVIDUAL"));
    verify(userService).removeRole(222L, RoleId.valueOf("SUPER_ADMINISTRATOR"));
  }

  @Test
  void shouldDoNothingWhenUserIdsIsEmpty() {
    RemoveUserRole activity = new RemoveUserRole();
    activity.setUserIds(List.of());
    activity.setRoles(List.of("INDIVIDUAL"));

    when(context.getActivity()).thenReturn(activity);

    executor.execute(context);

    verifyNoInteractions(bdk);
  }

  @Test
  void shouldDoNothingWhenRolesIsEmpty() {
    RemoveUserRole activity = new RemoveUserRole();
    activity.setUserIds(List.of(123L));
    activity.setRoles(List.of());

    when(context.getActivity()).thenReturn(activity);

    executor.execute(context);

    verifyNoInteractions(bdk);
  }
}
