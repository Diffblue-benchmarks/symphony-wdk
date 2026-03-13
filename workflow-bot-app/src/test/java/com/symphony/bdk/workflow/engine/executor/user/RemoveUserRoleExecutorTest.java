package com.symphony.bdk.workflow.engine.executor.user;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.core.service.user.constant.RoleId;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.user.RemoveUserRole;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class RemoveUserRoleExecutorTest {

  private RemoveUserRoleExecutor executor;

  @Mock
  private ActivityExecutorContext<RemoveUserRole> context;

  @Mock
  private BdkGateway bdkGateway;

  @Mock
  private UserService userService;

  @BeforeEach
  void setUp() {
    executor = new RemoveUserRoleExecutor();
  }

  @Test
  void execute_shouldRemoveRoleFromSingleUser() {
    // Arrange
    RemoveUserRole activity = new RemoveUserRole();
    activity.setUserIds(List.of(12345L));
    activity.setRoles(List.of("INDIVIDUAL"));

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.users()).thenReturn(userService);

    // Act
    executor.execute(context);

    // Assert
    verify(userService).removeRole(12345L, RoleId.INDIVIDUAL);
  }

  @Test
  void execute_shouldRemoveRoleFromMultipleUsers() {
    // Arrange
    RemoveUserRole activity = new RemoveUserRole();
    activity.setUserIds(List.of(12345L, 67890L));
    activity.setRoles(List.of("INDIVIDUAL"));

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.users()).thenReturn(userService);

    // Act
    executor.execute(context);

    // Assert
    verify(userService).removeRole(12345L, RoleId.INDIVIDUAL);
    verify(userService).removeRole(67890L, RoleId.INDIVIDUAL);
  }

  @Test
  void execute_shouldRemoveMultipleRolesFromSingleUser() {
    // Arrange
    RemoveUserRole activity = new RemoveUserRole();
    activity.setUserIds(List.of(12345L));
    activity.setRoles(List.of("INDIVIDUAL", "CONTENT_MANAGEMENT"));

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.users()).thenReturn(userService);

    // Act
    executor.execute(context);

    // Assert
    verify(userService).removeRole(12345L, RoleId.INDIVIDUAL);
    verify(userService).removeRole(12345L, RoleId.CONTENT_MANAGEMENT);
  }

  @Test
  void execute_shouldRemoveMultipleRolesFromMultipleUsers() {
    // Arrange
    RemoveUserRole activity = new RemoveUserRole();
    activity.setUserIds(List.of(11111L, 22222L, 33333L));
    activity.setRoles(List.of("INDIVIDUAL", "CONTENT_MANAGEMENT", "USER_PROVISIONING"));

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.users()).thenReturn(userService);

    // Act
    executor.execute(context);

    // Assert
    verify(userService).removeRole(11111L, RoleId.INDIVIDUAL);
    verify(userService).removeRole(11111L, RoleId.CONTENT_MANAGEMENT);
    verify(userService).removeRole(11111L, RoleId.USER_PROVISIONING);
    verify(userService).removeRole(22222L, RoleId.INDIVIDUAL);
    verify(userService).removeRole(22222L, RoleId.CONTENT_MANAGEMENT);
    verify(userService).removeRole(22222L, RoleId.USER_PROVISIONING);
    verify(userService).removeRole(33333L, RoleId.INDIVIDUAL);
    verify(userService).removeRole(33333L, RoleId.CONTENT_MANAGEMENT);
    verify(userService).removeRole(33333L, RoleId.USER_PROVISIONING);
  }

  @Test
  void execute_shouldHandleEmptyUserList() {
    // Arrange
    RemoveUserRole activity = new RemoveUserRole();
    activity.setUserIds(List.of());
    activity.setRoles(List.of("INDIVIDUAL"));

    when(context.getActivity()).thenReturn(activity);

    // Act
    executor.execute(context);

    // Assert
    verifyNoInteractions(userService);
  }

  @Test
  void execute_shouldHandleEmptyRoleList() {
    // Arrange
    RemoveUserRole activity = new RemoveUserRole();
    activity.setUserIds(List.of(12345L));
    activity.setRoles(List.of());

    when(context.getActivity()).thenReturn(activity);

    // Act
    executor.execute(context);

    // Assert
    verifyNoInteractions(userService);
  }
}
