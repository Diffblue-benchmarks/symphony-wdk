package com.symphony.bdk.workflow.engine.executor.user;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.gen.api.model.UserV2;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.user.GetUsers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ContextConfiguration(classes = {GetUsersExecutor.class})
@ExtendWith(SpringExtension.class)
class GetUsersExecutorDiffblueTest {
  @Autowired private GetUsersExecutor getUsersExecutor;

  /**
   * Test {@link GetUsersExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link GetUsers} (default constructor).
   *   <li>Then calls {@link ActivityExecutorContext#getActivity()}.
   * </ul>
   *
   * <p>Method under test: {@link GetUsersExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given GetUsers (default constructor); then calls getActivity()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void GetUsersExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenGetUsers_thenCallsGetActivity() {
    // Arrange
    ActivityExecutorContext<GetUsers> context = mock(ActivityExecutorContext.class);
    doNothing().when(context).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(context.getActivity()).thenReturn(new GetUsers());

    // Act
    getUsersExecutor.execute(context);

    // Assert
    verify(context).getActivity();
    verify(context).setOutputVariable(eq("users"), isNull());
  }

  /**
   * Test {@link GetUsersExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link GetUsers} with usernames set.
   *   <li>Then calls {@link UserService#listUsersByUsernames(List, Boolean)}.
   * </ul>
   *
   * <p>Method under test: {@link GetUsersExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given GetUsers with usernames; then calls listUsersByUsernames")
  @Tag("ContributionFromDiffblue")
  @MethodsUnderTest({"void GetUsersExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenGetUsersWithUsernames_thenCallsListUsersByUsernames() {
    // Arrange
    GetUsers getUsers = new GetUsers();
    getUsers.setUsernames(Arrays.asList("alice", "bob"));
    getUsers.setActive(true);

    List<UserV2> userList = Collections.singletonList(new UserV2());
    UserService userService = mock(UserService.class);
    when(userService.listUsersByUsernames(Mockito.<List<String>>any(), Mockito.<Boolean>any()))
        .thenReturn(userList);

    BdkGateway bdk = mock(BdkGateway.class);
    when(bdk.users()).thenReturn(userService);

    ActivityExecutorContext<GetUsers> context = mock(ActivityExecutorContext.class);
    doNothing().when(context).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(context.getActivity()).thenReturn(getUsers);
    when(context.bdk()).thenReturn(bdk);

    // Act
    getUsersExecutor.execute(context);

    // Assert
    verify(userService).listUsersByUsernames(Arrays.asList("alice", "bob"), true);
    verify(context).setOutputVariable(eq("users"), eq(userList));
  }

  /**
   * Test {@link GetUsersExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link GetUsers} with userIds set.
   *   <li>Then calls {@link UserService#listUsersByIds(List, Boolean, Boolean)}.
   * </ul>
   *
   * <p>Method under test: {@link GetUsersExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given GetUsers with userIds; then calls listUsersByIds")
  @Tag("ContributionFromDiffblue")
  @MethodsUnderTest({"void GetUsersExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenGetUsersWithUserIds_thenCallsListUsersByIds() {
    // Arrange
    GetUsers getUsers = new GetUsers();
    getUsers.setUserIds(Arrays.asList(1L, 2L));
    getUsers.setLocal(false);
    getUsers.setActive(true);

    List<UserV2> userList = Collections.singletonList(new UserV2());
    UserService userService = mock(UserService.class);
    when(userService.listUsersByIds(Mockito.<List<Long>>any(), Mockito.<Boolean>any(),
        Mockito.<Boolean>any())).thenReturn(userList);

    BdkGateway bdk = mock(BdkGateway.class);
    when(bdk.users()).thenReturn(userService);

    ActivityExecutorContext<GetUsers> context = mock(ActivityExecutorContext.class);
    doNothing().when(context).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(context.getActivity()).thenReturn(getUsers);
    when(context.bdk()).thenReturn(bdk);

    // Act
    getUsersExecutor.execute(context);

    // Assert
    verify(userService).listUsersByIds(Arrays.asList(1L, 2L), false, true);
    verify(context).setOutputVariable(eq("users"), eq(userList));
  }

  /**
   * Test {@link GetUsersExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link GetUsers} with emails set.
   *   <li>Then calls {@link UserService#listUsersByEmails(List, Boolean, Boolean)}.
   * </ul>
   *
   * <p>Method under test: {@link GetUsersExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given GetUsers with emails; then calls listUsersByEmails")
  @Tag("ContributionFromDiffblue")
  @MethodsUnderTest({"void GetUsersExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenGetUsersWithEmails_thenCallsListUsersByEmails() {
    // Arrange
    GetUsers getUsers = new GetUsers();
    getUsers.setEmails(Arrays.asList("alice@example.com", "bob@example.com"));
    getUsers.setLocal(true);
    getUsers.setActive(false);

    List<UserV2> userList = Collections.singletonList(new UserV2());
    UserService userService = mock(UserService.class);
    when(userService.listUsersByEmails(Mockito.<List<String>>any(), Mockito.<Boolean>any(),
        Mockito.<Boolean>any())).thenReturn(userList);

    BdkGateway bdk = mock(BdkGateway.class);
    when(bdk.users()).thenReturn(userService);

    ActivityExecutorContext<GetUsers> context = mock(ActivityExecutorContext.class);
    doNothing().when(context).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(context.getActivity()).thenReturn(getUsers);
    when(context.bdk()).thenReturn(bdk);

    // Act
    getUsersExecutor.execute(context);

    // Assert
    verify(userService).listUsersByEmails(Arrays.asList("alice@example.com", "bob@example.com"), true, false);
    verify(context).setOutputVariable(eq("users"), eq(userList));
  }

  /**
   * Test {@link GetUsersExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link GetUsers} with OBO username set.
   *   <li>Then calls {@link GetUsersExecutor#doOboWithCache(ActivityExecutorContext)}.
   * </ul>
   *
   * <p>Method under test: {@link GetUsersExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given GetUsers with OBO username; then calls doOboWithCache")
  @Tag("ContributionFromDiffblue")
  @MethodsUnderTest({"void GetUsersExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenGetUsersWithOboUsername_thenCallsDoOboWithCache() throws Exception {
    // Arrange
    Obo obo = new Obo();
    obo.setUsername("oboUser");

    GetUsers getUsers = new GetUsers();
    getUsers.setObo(obo);
    getUsers.setUsernames(Arrays.asList("alice"));

    AuthSession authSession = mock(AuthSession.class);
    List<UserV2> userList = Collections.singletonList(new UserV2());

    UserService oboUserService = mock(UserService.class);
    when(oboUserService.listUsersByUsernames(Mockito.<List<String>>any(), Mockito.<Boolean>any()))
        .thenReturn(userList);

    OboServices oboServices = mock(OboServices.class);
    when(oboServices.users()).thenReturn(oboUserService);

    BdkGateway bdk = mock(BdkGateway.class);
    when(bdk.obo(Mockito.<String>any())).thenReturn(authSession);
    when(bdk.obo(Mockito.<AuthSession>any())).thenReturn(oboServices);

    ActivityExecutorContext<GetUsers> context = mock(ActivityExecutorContext.class);
    doNothing().when(context).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(context.getActivity()).thenReturn(getUsers);
    when(context.bdk()).thenReturn(bdk);

    // Act
    getUsersExecutor.execute(context);

    // Assert
    verify(context).setOutputVariable(eq("users"), eq(userList));
  }

  /**
   * Test {@link GetUsersExecutor#doOboWithCache(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link GetUsers} with OBO and usernames.
   *   <li>Then calls OBO {@link UserService#listUsersByUsernames(List, Boolean)}.
   * </ul>
   *
   * <p>Method under test: {@link GetUsersExecutor#doOboWithCache(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test doOboWithCache(ActivityExecutorContext); given GetUsers with OBO and usernames; then calls listUsersByUsernames via OBO")
  @Tag("ContributionFromDiffblue")
  @MethodsUnderTest(
      {"List GetUsersExecutor.doOboWithCache(ActivityExecutorContext)"})
  void testDoOboWithCache_givenUsernames_thenCallsOboListUsersByUsernames() throws Exception {
    // Arrange
    Obo obo = new Obo();
    obo.setUsername("oboUser");

    GetUsers getUsers = new GetUsers();
    getUsers.setObo(obo);
    getUsers.setUsernames(Arrays.asList("alice", "bob"));
    getUsers.setActive(true);

    AuthSession authSession = mock(AuthSession.class);
    List<UserV2> userList = Collections.singletonList(new UserV2());

    UserService oboUserService = mock(UserService.class);
    when(oboUserService.listUsersByUsernames(Mockito.<List<String>>any(), Mockito.<Boolean>any()))
        .thenReturn(userList);

    OboServices oboServices = mock(OboServices.class);
    when(oboServices.users()).thenReturn(oboUserService);

    BdkGateway bdk = mock(BdkGateway.class);
    when(bdk.obo(Mockito.<String>any())).thenReturn(authSession);
    when(bdk.obo(Mockito.<AuthSession>any())).thenReturn(oboServices);

    ActivityExecutorContext<GetUsers> context = mock(ActivityExecutorContext.class);
    when(context.getActivity()).thenReturn(getUsers);
    when(context.bdk()).thenReturn(bdk);

    // Act
    List<UserV2> result = getUsersExecutor.doOboWithCache(context);

    // Assert
    verify(oboUserService).listUsersByUsernames(Arrays.asList("alice", "bob"), true);
    org.assertj.core.api.Assertions.assertThat(result).isEqualTo(userList);
  }

  /**
   * Test {@link GetUsersExecutor#doOboWithCache(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link GetUsers} with OBO and userIds.
   *   <li>Then calls OBO {@link UserService#listUsersByIds(List, Boolean, Boolean)}.
   * </ul>
   *
   * <p>Method under test: {@link GetUsersExecutor#doOboWithCache(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test doOboWithCache(ActivityExecutorContext); given GetUsers with OBO and userIds; then calls listUsersByIds via OBO")
  @Tag("ContributionFromDiffblue")
  @MethodsUnderTest(
      {"List GetUsersExecutor.doOboWithCache(ActivityExecutorContext)"})
  void testDoOboWithCache_givenUserIds_thenCallsOboListUsersByIds() throws Exception {
    // Arrange
    Obo obo = new Obo();
    obo.setUsername("oboUser");

    GetUsers getUsers = new GetUsers();
    getUsers.setObo(obo);
    getUsers.setUserIds(Arrays.asList(10L, 20L));
    getUsers.setLocal(false);
    getUsers.setActive(true);

    AuthSession authSession = mock(AuthSession.class);
    List<UserV2> userList = Collections.singletonList(new UserV2());

    UserService oboUserService = mock(UserService.class);
    when(oboUserService.listUsersByIds(Mockito.<List<Long>>any(), Mockito.<Boolean>any(),
        Mockito.<Boolean>any())).thenReturn(userList);

    OboServices oboServices = mock(OboServices.class);
    when(oboServices.users()).thenReturn(oboUserService);

    BdkGateway bdk = mock(BdkGateway.class);
    when(bdk.obo(Mockito.<String>any())).thenReturn(authSession);
    when(bdk.obo(Mockito.<AuthSession>any())).thenReturn(oboServices);

    ActivityExecutorContext<GetUsers> context = mock(ActivityExecutorContext.class);
    when(context.getActivity()).thenReturn(getUsers);
    when(context.bdk()).thenReturn(bdk);

    // Act
    List<UserV2> result = getUsersExecutor.doOboWithCache(context);

    // Assert
    verify(oboUserService).listUsersByIds(Arrays.asList(10L, 20L), false, true);
    org.assertj.core.api.Assertions.assertThat(result).isEqualTo(userList);
  }

  /**
   * Test {@link GetUsersExecutor#doOboWithCache(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link GetUsers} with OBO and emails.
   *   <li>Then calls OBO {@link UserService#listUsersByEmails(List, Boolean, Boolean)}.
   * </ul>
   *
   * <p>Method under test: {@link GetUsersExecutor#doOboWithCache(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test doOboWithCache(ActivityExecutorContext); given GetUsers with OBO and emails; then calls listUsersByEmails via OBO")
  @Tag("ContributionFromDiffblue")
  @MethodsUnderTest(
      {"List GetUsersExecutor.doOboWithCache(ActivityExecutorContext)"})
  void testDoOboWithCache_givenEmails_thenCallsOboListUsersByEmails() throws Exception {
    // Arrange
    Obo obo = new Obo();
    obo.setUsername("oboUser");

    GetUsers getUsers = new GetUsers();
    getUsers.setObo(obo);
    getUsers.setEmails(Arrays.asList("x@example.com"));
    getUsers.setLocal(true);
    getUsers.setActive(false);

    AuthSession authSession = mock(AuthSession.class);
    List<UserV2> userList = Collections.singletonList(new UserV2());

    UserService oboUserService = mock(UserService.class);
    when(oboUserService.listUsersByEmails(Mockito.<List<String>>any(), Mockito.<Boolean>any(),
        Mockito.<Boolean>any())).thenReturn(userList);

    OboServices oboServices = mock(OboServices.class);
    when(oboServices.users()).thenReturn(oboUserService);

    BdkGateway bdk = mock(BdkGateway.class);
    when(bdk.obo(Mockito.<String>any())).thenReturn(authSession);
    when(bdk.obo(Mockito.<AuthSession>any())).thenReturn(oboServices);

    ActivityExecutorContext<GetUsers> context = mock(ActivityExecutorContext.class);
    when(context.getActivity()).thenReturn(getUsers);
    when(context.bdk()).thenReturn(bdk);

    // Act
    List<UserV2> result = getUsersExecutor.doOboWithCache(context);

    // Assert
    verify(oboUserService).listUsersByEmails(Arrays.asList("x@example.com"), true, false);
    org.assertj.core.api.Assertions.assertThat(result).isEqualTo(userList);
  }

  /**
   * Test {@link GetUsersExecutor#doOboWithCache(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link GetUsers} with OBO and no search criteria.
   *   <li>Then returns empty list.
   * </ul>
   *
   * <p>Method under test: {@link GetUsersExecutor#doOboWithCache(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test doOboWithCache(ActivityExecutorContext); given GetUsers with OBO and no criteria; then returns empty list")
  @Tag("ContributionFromDiffblue")
  @MethodsUnderTest(
      {"List GetUsersExecutor.doOboWithCache(ActivityExecutorContext)"})
  void testDoOboWithCache_givenNoCriteria_thenReturnsEmptyList() throws Exception {
    // Arrange
    Obo obo = new Obo();
    obo.setUsername("oboUser");

    GetUsers getUsers = new GetUsers();
    getUsers.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);

    BdkGateway bdk = mock(BdkGateway.class);
    when(bdk.obo(Mockito.<String>any())).thenReturn(authSession);

    ActivityExecutorContext<GetUsers> context = mock(ActivityExecutorContext.class);
    when(context.getActivity()).thenReturn(getUsers);
    when(context.bdk()).thenReturn(bdk);

    // Act
    List<UserV2> result = getUsersExecutor.doOboWithCache(context);

    // Assert
    org.assertj.core.api.Assertions.assertThat(result).isEmpty();
  }
}
