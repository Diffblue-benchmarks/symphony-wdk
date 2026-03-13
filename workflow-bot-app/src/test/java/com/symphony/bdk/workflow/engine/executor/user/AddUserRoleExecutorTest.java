package com.symphony.bdk.workflow.engine.executor.user;

import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.core.service.user.constant.RoleId;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.user.AddUserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddUserRoleExecutorTest {

  @Mock
  private ActivityExecutorContext<AddUserRole> context;

  @Mock
  private BdkGateway bdkGateway;

  @Mock
  private UserService userService;

  @InjectMocks
  private AddUserRoleExecutor executor;

  private AddUserRole addUserRole;

  @BeforeEach
  void setUp() {
    addUserRole = new AddUserRole();
    lenient().when(context.getActivity()).thenReturn(addUserRole);
    lenient().when(context.bdk()).thenReturn(bdkGateway);
    lenient().when(bdkGateway.users()).thenReturn(userService);
  }

  @Test
  void shouldAddSingleRoleToSingleUser() {
    // Arrange
    addUserRole.setUserIds(List.of(123L));
    addUserRole.setRoles(List.of("INDIVIDUAL"));

    // Act
    executor.execute(context);

    // Assert
    verify(userService).addRole(123L, RoleId.valueOf("INDIVIDUAL"));
  }

  @Test
  void shouldAddMultipleRolesToSingleUser() {
    // Arrange
    addUserRole.setUserIds(List.of(456L));
    addUserRole.setRoles(List.of("INDIVIDUAL", "CONTENT_MANAGEMENT"));

    // Act
    executor.execute(context);

    // Assert
    verify(userService).addRole(456L, RoleId.valueOf("INDIVIDUAL"));
    verify(userService).addRole(456L, RoleId.valueOf("CONTENT_MANAGEMENT"));
  }

  @Test
  void shouldAddSingleRoleToMultipleUsers() {
    // Arrange
    addUserRole.setUserIds(List.of(111L, 222L, 333L));
    addUserRole.setRoles(List.of("USER_PROVISIONING"));

    // Act
    executor.execute(context);

    // Assert
    verify(userService).addRole(111L, RoleId.valueOf("USER_PROVISIONING"));
    verify(userService).addRole(222L, RoleId.valueOf("USER_PROVISIONING"));
    verify(userService).addRole(333L, RoleId.valueOf("USER_PROVISIONING"));
  }

  @Test
  void shouldAddMultipleRolesToMultipleUsers() {
    // Arrange
    addUserRole.setUserIds(List.of(100L, 200L));
    addUserRole.setRoles(List.of("INDIVIDUAL", "CONTENT_MANAGEMENT"));

    // Act
    executor.execute(context);

    // Assert
    verify(userService).addRole(100L, RoleId.valueOf("INDIVIDUAL"));
    verify(userService).addRole(100L, RoleId.valueOf("CONTENT_MANAGEMENT"));
    verify(userService).addRole(200L, RoleId.valueOf("INDIVIDUAL"));
    verify(userService).addRole(200L, RoleId.valueOf("CONTENT_MANAGEMENT"));
  }

  @Test
  void shouldHandleEmptyUserIdsList() {
    // Arrange
    addUserRole.setUserIds(List.of());
    addUserRole.setRoles(List.of("INDIVIDUAL"));

    // Act
    executor.execute(context);

    // Assert
    verifyNoInteractions(userService);
  }

  @Test
  void shouldHandleEmptyRolesList() {
    // Arrange
    addUserRole.setUserIds(List.of(999L));
    addUserRole.setRoles(List.of());

    // Act
    executor.execute(context);

    // Assert
    verifyNoInteractions(userService);
  }

  @Test
  void shouldHandleEmptyBothLists() {
    // Arrange
    addUserRole.setUserIds(List.of());
    addUserRole.setRoles(List.of());

    // Act
    executor.execute(context);

    // Assert
    verifyNoInteractions(userService);
  }
}
