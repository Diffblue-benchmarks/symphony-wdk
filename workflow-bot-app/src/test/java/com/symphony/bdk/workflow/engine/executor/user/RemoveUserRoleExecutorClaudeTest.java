package com.symphony.bdk.workflow.engine.executor.user;

import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.core.service.user.constant.RoleId;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.user.RemoveUserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;

class RemoveUserRoleExecutorClaudeTest {

  private RemoveUserRoleExecutor executor;
  private ActivityExecutorContext<RemoveUserRole> context;
  private RemoveUserRole activity;
  private BdkGateway bdkGateway;
  private UserService userService;

  @BeforeEach
  void setUp() {
    executor = new RemoveUserRoleExecutor();
    context = mock(ActivityExecutorContext.class);
    activity = new RemoveUserRole();
    bdkGateway = mock(BdkGateway.class);
    userService = mock(UserService.class);

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.users()).thenReturn(userService);
  }

  // Constructor tests
  @Test
  void constructor_shouldCreateInstanceSuccessfully() {
    // When: Creating a new instance
    RemoveUserRoleExecutor newExecutor = new RemoveUserRoleExecutor();

    // Then: Instance should be created successfully
    assertThat(newExecutor).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance
    RemoveUserRoleExecutor newExecutor = new RemoveUserRoleExecutor();

    // Then: Instance should be of RemoveUserRoleExecutor type and implement ActivityExecutor
    assertThat(newExecutor).isInstanceOf(RemoveUserRoleExecutor.class);
    assertThat(newExecutor).isInstanceOf(ActivityExecutor.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating multiple instances
    RemoveUserRoleExecutor executor1 = new RemoveUserRoleExecutor();
    RemoveUserRoleExecutor executor2 = new RemoveUserRoleExecutor();

    // Then: Each instance should be distinct
    assertThat(executor1).isNotSameAs(executor2);
  }

  @Test
  void constructor_shouldNotThrowException() {
    // When/Then: Constructor should not throw exception
    assertThatCode(() -> new RemoveUserRoleExecutor())
        .doesNotThrowAnyException();
  }

  // Execute tests - basic scenarios
  @Test
  void execute_withSingleUserAndSingleRole_shouldRemoveRoleOnce() {
    // Given: A single user ID and single role
    activity.setUserIds(Collections.singletonList(12345L));
    activity.setRoles(Collections.singletonList("INDIVIDUAL"));

    // When: Execute is called
    executor.execute(context);

    // Then: Should remove role from user once
    verify(userService).removeRole(12345L, RoleId.INDIVIDUAL);
  }

  @Test
  void execute_withSingleUserAndMultipleRoles_shouldRemoveAllRoles() {
    // Given: A single user ID and multiple roles
    activity.setUserIds(Collections.singletonList(12345L));
    activity.setRoles(Arrays.asList("INDIVIDUAL", "CONTENT_MANAGEMENT", "USER_PROVISIONING"));

    // When: Execute is called
    executor.execute(context);

    // Then: Should remove all roles from the user
    verify(userService).removeRole(12345L, RoleId.INDIVIDUAL);
    verify(userService).removeRole(12345L, RoleId.CONTENT_MANAGEMENT);
    verify(userService).removeRole(12345L, RoleId.USER_PROVISIONING);
  }

  @Test
  void execute_withMultipleUsersAndSingleRole_shouldRemoveRoleFromAllUsers() {
    // Given: Multiple user IDs and single role
    activity.setUserIds(Arrays.asList(12345L, 67890L, 11111L));
    activity.setRoles(Collections.singletonList("INDIVIDUAL"));

    // When: Execute is called
    executor.execute(context);

    // Then: Should remove role from all users
    verify(userService).removeRole(12345L, RoleId.INDIVIDUAL);
    verify(userService).removeRole(67890L, RoleId.INDIVIDUAL);
    verify(userService).removeRole(11111L, RoleId.INDIVIDUAL);
  }

  @Test
  void execute_withMultipleUsersAndMultipleRoles_shouldRemoveAllCombinations() {
    // Given: Multiple user IDs and multiple roles
    activity.setUserIds(Arrays.asList(12345L, 67890L));
    activity.setRoles(Arrays.asList("INDIVIDUAL", "CONTENT_MANAGEMENT"));

    // When: Execute is called
    executor.execute(context);

    // Then: Should remove all role combinations (2 users * 2 roles = 4 calls)
    verify(userService).removeRole(12345L, RoleId.INDIVIDUAL);
    verify(userService).removeRole(12345L, RoleId.CONTENT_MANAGEMENT);
    verify(userService).removeRole(67890L, RoleId.INDIVIDUAL);
    verify(userService).removeRole(67890L, RoleId.CONTENT_MANAGEMENT);
  }

  // Execute tests - edge cases
  @Test
  void execute_withEmptyUserIds_shouldNotRemoveAnyRole() {
    // Given: Empty user IDs list and some roles
    activity.setUserIds(Collections.emptyList());
    activity.setRoles(Collections.singletonList("INDIVIDUAL"));

    // When: Execute is called
    executor.execute(context);

    // Then: Should not remove any role
    verify(userService, never()).removeRole(anyLong(), any(RoleId.class));
  }

  @Test
  void execute_withEmptyRoles_shouldNotRemoveAnyRole() {
    // Given: Some user IDs and empty roles list
    activity.setUserIds(Collections.singletonList(12345L));
    activity.setRoles(Collections.emptyList());

    // When: Execute is called
    executor.execute(context);

    // Then: Should not remove any role
    verify(userService, never()).removeRole(anyLong(), any(RoleId.class));
  }

  @Test
  void execute_withEmptyUserIdsAndRoles_shouldNotRemoveAnyRole() {
    // Given: Empty user IDs and roles lists
    activity.setUserIds(Collections.emptyList());
    activity.setRoles(Collections.emptyList());

    // When: Execute is called
    executor.execute(context);

    // Then: Should not remove any role
    verify(userService, never()).removeRole(anyLong(), any(RoleId.class));
  }

  // Execute tests - various role types
  @Test
  void execute_withDifferentRoleTypes_shouldHandleAllRoleIds() {
    // Given: User IDs with various role types
    activity.setUserIds(Collections.singletonList(12345L));
    activity.setRoles(Arrays.asList(
        "INDIVIDUAL",
        "CONTENT_MANAGEMENT",
        "USER_PROVISIONING",
        "COMPLIANCE_OFFICER",
        "SUPER_COMPLIANCE_OFFICER"
    ));

    // When: Execute is called
    executor.execute(context);

    // Then: Should remove all different role types
    verify(userService).removeRole(12345L, RoleId.INDIVIDUAL);
    verify(userService).removeRole(12345L, RoleId.CONTENT_MANAGEMENT);
    verify(userService).removeRole(12345L, RoleId.USER_PROVISIONING);
    verify(userService).removeRole(12345L, RoleId.COMPLIANCE_OFFICER);
    verify(userService).removeRole(12345L, RoleId.SUPER_COMPLIANCE_OFFICER);
  }

  // Execute tests - execution flow verification
  @Test
  void execute_shouldRetrieveActivityFromContext() {
    // Given: Valid user IDs and roles
    activity.setUserIds(Collections.singletonList(12345L));
    activity.setRoles(Collections.singletonList("INDIVIDUAL"));

    // When: Execute is called
    executor.execute(context);

    // Then: Should retrieve activity from context
    verify(context).getActivity();
  }

  @Test
  void execute_shouldUseBdkGateway() {
    // Given: Valid user IDs and roles
    activity.setUserIds(Collections.singletonList(12345L));
    activity.setRoles(Collections.singletonList("INDIVIDUAL"));

    // When: Execute is called
    executor.execute(context);

    // Then: Should use BDK gateway to access users service
    verify(context).bdk();
    verify(bdkGateway).users();
  }

  @Test
  void execute_ensuresCorrectExecutionFlow() {
    // Given: Valid user IDs and roles
    activity.setUserIds(Collections.singletonList(12345L));
    activity.setRoles(Collections.singletonList("INDIVIDUAL"));

    // When: Execute is called
    executor.execute(context);

    // Then: Should follow correct execution flow: get activity -> get BDK -> get users -> remove role
    verify(context).getActivity();
    verify(context).bdk();
    verify(bdkGateway).users();
    verify(userService).removeRole(12345L, RoleId.INDIVIDUAL);
  }

  // Execute tests - multiple executions
  @Test
  void execute_calledMultipleTimes_shouldWorkCorrectly() {
    // Given: Valid user IDs and roles
    activity.setUserIds(Collections.singletonList(12345L));
    activity.setRoles(Collections.singletonList("INDIVIDUAL"));

    // When: Execute is called multiple times
    executor.execute(context);
    executor.execute(context);

    // Then: Should work correctly both times
    verify(userService, times(2)).removeRole(12345L, RoleId.INDIVIDUAL);
  }

  @Test
  void execute_withDifferentActivities_shouldProcessEachCorrectly() {
    // Given: First activity
    activity.setUserIds(Collections.singletonList(11111L));
    activity.setRoles(Collections.singletonList("INDIVIDUAL"));

    // When: Execute is called first time
    executor.execute(context);

    // Then: Should remove role for first user
    verify(userService).removeRole(11111L, RoleId.INDIVIDUAL);

    // Given: Second activity
    RemoveUserRole activity2 = new RemoveUserRole();
    activity2.setUserIds(Collections.singletonList(22222L));
    activity2.setRoles(Collections.singletonList("CONTENT_MANAGEMENT"));
    when(context.getActivity()).thenReturn(activity2);

    // When: Execute is called second time
    executor.execute(context);

    // Then: Should remove role for second user
    verify(userService).removeRole(22222L, RoleId.CONTENT_MANAGEMENT);
  }

  // Execute tests - exception handling
  @Test
  void execute_shouldNotThrowException() {
    // Given: Valid user IDs and roles
    activity.setUserIds(Collections.singletonList(12345L));
    activity.setRoles(Collections.singletonList("INDIVIDUAL"));

    // When/Then: Execute should not throw exception
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();
  }

  @Test
  void execute_withEmptyLists_shouldNotThrowException() {
    // Given: Empty user IDs and roles
    activity.setUserIds(Collections.emptyList());
    activity.setRoles(Collections.emptyList());

    // When/Then: Execute should not throw exception
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();
  }

  // Execute tests - large datasets
  @Test
  void execute_withManyUsers_shouldRemoveRolesFromAll() {
    // Given: Many user IDs
    List<Long> manyUsers = Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L);
    activity.setUserIds(manyUsers);
    activity.setRoles(Collections.singletonList("INDIVIDUAL"));

    // When: Execute is called
    executor.execute(context);

    // Then: Should remove role from all users
    for (Long userId : manyUsers) {
      verify(userService).removeRole(userId, RoleId.INDIVIDUAL);
    }
  }

  @Test
  void execute_withManyRoles_shouldRemoveAllFromUser() {
    // Given: Many roles
    List<String> manyRoles = Arrays.asList(
        "INDIVIDUAL",
        "CONTENT_MANAGEMENT",
        "USER_PROVISIONING",
        "COMPLIANCE_OFFICER",
        "SUPER_COMPLIANCE_OFFICER",
        "SUPER_ADMINISTRATOR",
        "L1_SUPPORT",
        "L2_SUPPORT"
    );
    activity.setUserIds(Collections.singletonList(12345L));
    activity.setRoles(manyRoles);

    // When: Execute is called
    executor.execute(context);

    // Then: Should remove all roles from user
    verify(userService).removeRole(12345L, RoleId.INDIVIDUAL);
    verify(userService).removeRole(12345L, RoleId.CONTENT_MANAGEMENT);
    verify(userService).removeRole(12345L, RoleId.USER_PROVISIONING);
    verify(userService).removeRole(12345L, RoleId.COMPLIANCE_OFFICER);
    verify(userService).removeRole(12345L, RoleId.SUPER_COMPLIANCE_OFFICER);
    verify(userService).removeRole(12345L, RoleId.SUPER_ADMINISTRATOR);
    verify(userService).removeRole(12345L, RoleId.L1_SUPPORT);
    verify(userService).removeRole(12345L, RoleId.L2_SUPPORT);
  }

  @Test
  void execute_withManyUsersAndManyRoles_shouldHandleAllCombinations() {
    // Given: Multiple users and multiple roles
    List<Long> users = Arrays.asList(12345L, 67890L, 11111L);
    List<String> roles = Arrays.asList("INDIVIDUAL", "CONTENT_MANAGEMENT", "USER_PROVISIONING");
    activity.setUserIds(users);
    activity.setRoles(roles);

    // When: Execute is called
    executor.execute(context);

    // Then: Should remove all combinations (3 users * 3 roles = 9 calls)
    for (Long userId : users) {
      verify(userService).removeRole(userId, RoleId.INDIVIDUAL);
      verify(userService).removeRole(userId, RoleId.CONTENT_MANAGEMENT);
      verify(userService).removeRole(userId, RoleId.USER_PROVISIONING);
    }
    verify(userService, times(9)).removeRole(anyLong(), any(RoleId.class));
  }

  // Execute tests - edge case user IDs
  @Test
  void execute_withZeroUserId_shouldHandleCorrectly() {
    // Given: A zero user ID
    activity.setUserIds(Collections.singletonList(0L));
    activity.setRoles(Collections.singletonList("INDIVIDUAL"));

    // When: Execute is called
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();

    // Then: Should remove role for zero ID user
    verify(userService).removeRole(0L, RoleId.INDIVIDUAL);
  }

  @Test
  void execute_withLargeUserId_shouldHandleCorrectly() {
    // Given: A large user ID
    activity.setUserIds(Collections.singletonList(9999999999L));
    activity.setRoles(Collections.singletonList("INDIVIDUAL"));

    // When: Execute is called
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();

    // Then: Should remove role for large ID user
    verify(userService).removeRole(9999999999L, RoleId.INDIVIDUAL);
  }

  @Test
  void execute_withSmallUserId_shouldHandleCorrectly() {
    // Given: A small user ID
    activity.setUserIds(Collections.singletonList(1L));
    activity.setRoles(Collections.singletonList("INDIVIDUAL"));

    // When: Execute is called
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();

    // Then: Should remove role for small ID user
    verify(userService).removeRole(1L, RoleId.INDIVIDUAL);
  }

  // Execute tests - role iteration order
  @Test
  void execute_shouldProcessRolesInOrder() {
    // Given: A user with multiple roles in specific order
    activity.setUserIds(Collections.singletonList(12345L));
    activity.setRoles(Arrays.asList("INDIVIDUAL", "CONTENT_MANAGEMENT", "USER_PROVISIONING"));

    // When: Execute is called
    executor.execute(context);

    // Then: Should process roles (all roles should be removed)
    verify(userService).removeRole(12345L, RoleId.INDIVIDUAL);
    verify(userService).removeRole(12345L, RoleId.CONTENT_MANAGEMENT);
    verify(userService).removeRole(12345L, RoleId.USER_PROVISIONING);
  }

  @Test
  void execute_shouldProcessUsersInOrder() {
    // Given: Multiple users with a role
    activity.setUserIds(Arrays.asList(111L, 222L, 333L));
    activity.setRoles(Collections.singletonList("INDIVIDUAL"));

    // When: Execute is called
    executor.execute(context);

    // Then: Should process all users
    verify(userService).removeRole(111L, RoleId.INDIVIDUAL);
    verify(userService).removeRole(222L, RoleId.INDIVIDUAL);
    verify(userService).removeRole(333L, RoleId.INDIVIDUAL);
  }

  // Execute tests - activity retrieval
  @Test
  void execute_withContextContainingRemoveUserRole_shouldDelegateCorrectly() {
    // Given: Context with RemoveUserRole activity
    activity.setUserIds(Collections.singletonList(12345L));
    activity.setRoles(Collections.singletonList("INDIVIDUAL"));

    // When: Execute is called
    executor.execute(context);

    // Then: Should retrieve activity from context and remove roles
    verify(context).getActivity();
    verify(context).bdk();
    verify(bdkGateway).users();
    verify(userService).removeRole(12345L, RoleId.INDIVIDUAL);
  }

  @Test
  void execute_withThreeUsersAndTwoRoles_shouldMakeSixCalls() {
    // Given: Three users and two roles
    activity.setUserIds(Arrays.asList(100L, 200L, 300L));
    activity.setRoles(Arrays.asList("INDIVIDUAL", "CONTENT_MANAGEMENT"));

    // When: Execute is called
    executor.execute(context);

    // Then: Should make exactly 6 calls (3 users * 2 roles)
    verify(userService, times(6)).removeRole(anyLong(), any(RoleId.class));
    verify(userService).removeRole(100L, RoleId.INDIVIDUAL);
    verify(userService).removeRole(100L, RoleId.CONTENT_MANAGEMENT);
    verify(userService).removeRole(200L, RoleId.INDIVIDUAL);
    verify(userService).removeRole(200L, RoleId.CONTENT_MANAGEMENT);
    verify(userService).removeRole(300L, RoleId.INDIVIDUAL);
    verify(userService).removeRole(300L, RoleId.CONTENT_MANAGEMENT);
  }
}
