package com.symphony.bdk.workflow.engine.executor.user;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.core.service.user.constant.RoleId;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.user.AddUserRole;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AddUserRoleExecutor.class})
@ExtendWith(SpringExtension.class)
class AddUserRoleExecutorDiffblueTest {
  @Autowired private AddUserRoleExecutor addUserRoleExecutor;

  /**
   * Test {@link AddUserRoleExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link AddUserRole} (default constructor).
   *   <li>Then calls {@link ActivityExecutorContext#getActivity()}.
   * </ul>
   *
   * <p>Method under test: {@link AddUserRoleExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given AddUserRole (default constructor); then calls getActivity()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AddUserRoleExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenAddUserRole_thenCallsGetActivity() {
    // Arrange
    ActivityExecutorContext<AddUserRole> context = mock(ActivityExecutorContext.class);
    when(context.getActivity()).thenReturn(new AddUserRole());

    // Act
    addUserRoleExecutor.execute(context);

    // Assert
    verify(context).getActivity();
  }

  /**
   * Test {@link AddUserRoleExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add one.
   *   <li>Then calls {@link ActivityExecutorContext#getActivity()}.
   * </ul>
   *
   * <p>Method under test: {@link AddUserRoleExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given ArrayList() add one; then calls getActivity()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AddUserRoleExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenArrayListAddOne_thenCallsGetActivity() {
    // Arrange
    ArrayList<Long> userIds = new ArrayList<>();
    userIds.add(1L);

    AddUserRole addUserRole = new AddUserRole();
    addUserRole.setUserIds(userIds);

    ActivityExecutorContext<AddUserRole> context = mock(ActivityExecutorContext.class);
    when(context.getActivity()).thenReturn(addUserRole);

    // Act
    addUserRoleExecutor.execute(context);

    // Assert
    verify(context).getActivity();
  }

  /**
   * Test {@link AddUserRoleExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given userIds and roles are both non-empty.
   *   <li>Then calls {@link UserService#addRole(Long, RoleId)} for each combination.
   * </ul>
   *
   * <p>Method under test: {@link AddUserRoleExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given userIds and roles non-empty; then calls addRole")
  @Tag("ContributionFromDiffblue")
  @MethodsUnderTest({"void AddUserRoleExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenUserIdsAndRoles_thenCallsAddRole() {
    // Arrange
    ArrayList<Long> userIds = new ArrayList<>();
    userIds.add(123L);

    ArrayList<String> roles = new ArrayList<>();
    roles.add("ADMINISTRATOR");

    AddUserRole addUserRole = new AddUserRole();
    addUserRole.setUserIds(userIds);
    addUserRole.setRoles(roles);

    UserService userService = mock(UserService.class);
    doNothing().when(userService).addRole(123L, RoleId.ADMINISTRATOR);

    BdkGateway bdkGateway = mock(BdkGateway.class);
    when(bdkGateway.users()).thenReturn(userService);

    ActivityExecutorContext<AddUserRole> context = mock(ActivityExecutorContext.class);
    when(context.getActivity()).thenReturn(addUserRole);
    when(context.bdk()).thenReturn(bdkGateway);

    // Act
    addUserRoleExecutor.execute(context);

    // Assert
    verify(userService).addRole(123L, RoleId.ADMINISTRATOR);
    verify(context).getActivity();
    verify(context).bdk();
  }
}
