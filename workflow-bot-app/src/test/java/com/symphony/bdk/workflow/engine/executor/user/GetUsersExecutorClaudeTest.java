package com.symphony.bdk.workflow.engine.executor.user;

import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.user.OboUserService;
import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.gen.api.model.UserV2;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.user.GetUsers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class GetUsersExecutorClaudeTest {

  private GetUsersExecutor executor;
  private ActivityExecutorContext<GetUsers> context;
  private GetUsers activity;
  private BdkGateway bdkGateway;
  private UserService userService;
  private List<UserV2> userList;

  @BeforeEach
  void setUp() {
    executor = new GetUsersExecutor();
    context = mock(ActivityExecutorContext.class);
    activity = new GetUsers();
    bdkGateway = mock(BdkGateway.class);
    userService = mock(UserService.class);

    UserV2 user1 = new UserV2();
    user1.setId(12345L);
    user1.setUsername("user1");
    UserV2 user2 = new UserV2();
    user2.setId(67890L);
    user2.setUsername("user2");
    userList = Arrays.asList(user1, user2);

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.users()).thenReturn(userService);
  }

  @Test
  void constructor_shouldCreateInstanceSuccessfully() {
    // When: Creating a new instance
    GetUsersExecutor newExecutor = new GetUsersExecutor();

    // Then: Instance should be created successfully
    assertThat(newExecutor).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance
    GetUsersExecutor newExecutor = new GetUsersExecutor();

    // Then: Instance should be of GetUsersExecutor type and implement ActivityExecutor
    assertThat(newExecutor).isInstanceOf(GetUsersExecutor.class);
    assertThat(newExecutor).isInstanceOf(ActivityExecutor.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating multiple instances
    GetUsersExecutor executor1 = new GetUsersExecutor();
    GetUsersExecutor executor2 = new GetUsersExecutor();

    // Then: Each instance should be distinct
    assertThat(executor1).isNotSameAs(executor2);
  }

  @Test
  void constructor_shouldNotThrowException() {
    // When/Then: Constructor should not throw exception
    assertThatCode(() -> new GetUsersExecutor())
        .doesNotThrowAnyException();
  }

  @Test
  void execute_withUsernames_shouldListUsersByUsernames() {
    // Given: Activity with usernames
    List<String> usernames = Arrays.asList("user1", "user2");
    activity.setUsernames(usernames);
    activity.setActive(true);
    when(userService.listUsersByUsernames(usernames, true)).thenReturn(userList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should list users by usernames and set output variable
    verify(userService).listUsersByUsernames(usernames, true);
    verify(context).setOutputVariable("users", userList);
  }

  @Test
  void execute_withUsernamesAndNullActive_shouldListUsersByUsernamesWithNullActive() {
    // Given: Activity with usernames and null active flag
    List<String> usernames = Arrays.asList("user1", "user2");
    activity.setUsernames(usernames);
    activity.setActive(null);
    when(userService.listUsersByUsernames(usernames, null)).thenReturn(userList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should list users by usernames with null active
    verify(userService).listUsersByUsernames(usernames, null);
    verify(context).setOutputVariable("users", userList);
  }

  @Test
  void execute_withUserIds_shouldListUsersByIds() {
    // Given: Activity with user IDs
    List<Long> userIds = Arrays.asList(12345L, 67890L);
    activity.setUserIds(userIds);
    activity.setLocal(true);
    activity.setActive(false);
    when(userService.listUsersByIds(userIds, true, false)).thenReturn(userList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should list users by IDs and set output variable
    verify(userService).listUsersByIds(userIds, true, false);
    verify(context).setOutputVariable("users", userList);
  }

  @Test
  void execute_withUserIdsAndNullFlags_shouldListUsersByIdsWithNullFlags() {
    // Given: Activity with user IDs and null flags
    List<Long> userIds = Arrays.asList(12345L, 67890L);
    activity.setUserIds(userIds);
    activity.setLocal(null);
    activity.setActive(null);
    when(userService.listUsersByIds(userIds, null, null)).thenReturn(userList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should list users by IDs with null flags
    verify(userService).listUsersByIds(userIds, null, null);
    verify(context).setOutputVariable("users", userList);
  }

  @Test
  void execute_withEmails_shouldListUsersByEmails() {
    // Given: Activity with emails
    List<String> emails = Arrays.asList("user1@example.com", "user2@example.com");
    activity.setEmails(emails);
    activity.setLocal(false);
    activity.setActive(true);
    when(userService.listUsersByEmails(emails, false, true)).thenReturn(userList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should list users by emails and set output variable
    verify(userService).listUsersByEmails(emails, false, true);
    verify(context).setOutputVariable("users", userList);
  }

  @Test
  void execute_withEmailsAndNullFlags_shouldListUsersByEmailsWithNullFlags() {
    // Given: Activity with emails and null flags
    List<String> emails = Arrays.asList("user1@example.com", "user2@example.com");
    activity.setEmails(emails);
    activity.setLocal(null);
    activity.setActive(null);
    when(userService.listUsersByEmails(emails, null, null)).thenReturn(userList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should list users by emails with null flags
    verify(userService).listUsersByEmails(emails, null, null);
    verify(context).setOutputVariable("users", userList);
  }

  @Test
  void execute_withEmptyUserList_shouldSetEmptyListAsOutput() {
    // Given: Activity with usernames that returns empty list
    List<String> usernames = Arrays.asList("nonexistent");
    activity.setUsernames(usernames);
    when(userService.listUsersByUsernames(usernames, null)).thenReturn(Collections.emptyList());

    // When: Execute is called
    executor.execute(context);

    // Then: Should set empty list as output variable
    verify(context).setOutputVariable("users", Collections.emptyList());
  }

  @Test
  void execute_withNullUserList_shouldSetNullAsOutput() {
    // Given: Activity with usernames that returns null
    List<String> usernames = Arrays.asList("user1");
    activity.setUsernames(usernames);
    when(userService.listUsersByUsernames(usernames, null)).thenReturn(null);

    // When: Execute is called
    executor.execute(context);

    // Then: Should set null as output variable
    verify(context).setOutputVariable("users", null);
  }

  @Test
  void execute_shouldNotThrowException() {
    // Given: Valid activity with usernames
    activity.setUsernames(Arrays.asList("user1"));
    when(userService.listUsersByUsernames(anyList(), any())).thenReturn(userList);

    // When/Then: Execute should not throw exception
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();
  }

  @Test
  void execute_calledMultipleTimes_shouldWorkCorrectly() {
    // Given: Valid activity with usernames
    List<String> usernames = Arrays.asList("user1");
    activity.setUsernames(usernames);
    when(userService.listUsersByUsernames(usernames, null)).thenReturn(userList);

    // When: Execute is called multiple times
    executor.execute(context);
    executor.execute(context);

    // Then: Should work correctly both times
    verify(userService, org.mockito.Mockito.times(2)).listUsersByUsernames(usernames, null);
    verify(context, org.mockito.Mockito.times(2)).setOutputVariable("users", userList);
  }

  @Test
  void execute_shouldRetrieveActivityFromContext() {
    // Given: Valid activity with usernames
    activity.setUsernames(Arrays.asList("user1"));
    when(userService.listUsersByUsernames(anyList(), any())).thenReturn(userList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should retrieve activity from context
    verify(context).getActivity();
  }

  @Test
  void execute_shouldUseBdkGateway() {
    // Given: Valid activity with usernames
    activity.setUsernames(Arrays.asList("user1"));
    when(userService.listUsersByUsernames(anyList(), any())).thenReturn(userList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use BDK gateway to access users service
    verify(context).bdk();
    verify(bdkGateway).users();
  }

  @Test
  void execute_shouldSetOutputVariableWithCorrectKey() {
    // Given: Valid activity with usernames
    activity.setUsernames(Arrays.asList("user1"));
    when(userService.listUsersByUsernames(anyList(), any())).thenReturn(userList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should set output variable with "users" key
    verify(context).setOutputVariable("users", userList);
  }

  @Test
  void execute_ensuresCorrectExecutionFlowForUsernames() {
    // Given: Valid activity with usernames
    List<String> usernames = Arrays.asList("user1");
    activity.setUsernames(usernames);
    when(userService.listUsersByUsernames(usernames, null)).thenReturn(userList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should follow correct execution flow
    verify(context).getActivity();
    verify(context).bdk();
    verify(bdkGateway).users();
    verify(userService).listUsersByUsernames(usernames, null);
    verify(context).setOutputVariable("users", userList);
  }

  @Test
  void execute_ensuresCorrectExecutionFlowForUserIds() {
    // Given: Valid activity with user IDs
    List<Long> userIds = Arrays.asList(12345L);
    activity.setUserIds(userIds);
    when(userService.listUsersByIds(userIds, null, null)).thenReturn(userList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should follow correct execution flow
    verify(context).getActivity();
    verify(context).bdk();
    verify(bdkGateway).users();
    verify(userService).listUsersByIds(userIds, null, null);
    verify(context).setOutputVariable("users", userList);
  }

  @Test
  void execute_ensuresCorrectExecutionFlowForEmails() {
    // Given: Valid activity with emails
    List<String> emails = Arrays.asList("user@example.com");
    activity.setEmails(emails);
    when(userService.listUsersByEmails(emails, null, null)).thenReturn(userList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should follow correct execution flow
    verify(context).getActivity();
    verify(context).bdk();
    verify(bdkGateway).users();
    verify(userService).listUsersByEmails(emails, null, null);
    verify(context).setOutputVariable("users", userList);
  }

  @Test
  void execute_withSingleUsername_shouldHandleCorrectly() {
    // Given: Activity with single username
    List<String> usernames = Arrays.asList("singleuser");
    activity.setUsernames(usernames);
    when(userService.listUsersByUsernames(usernames, null)).thenReturn(Arrays.asList(userList.get(0)));

    // When: Execute is called
    executor.execute(context);

    // Then: Should handle single user correctly
    verify(userService).listUsersByUsernames(usernames, null);
  }

  @Test
  void execute_withMultipleUsernames_shouldHandleCorrectly() {
    // Given: Activity with multiple usernames
    List<String> usernames = Arrays.asList("user1", "user2", "user3", "user4");
    activity.setUsernames(usernames);
    when(userService.listUsersByUsernames(usernames, null)).thenReturn(userList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should handle multiple users correctly
    verify(userService).listUsersByUsernames(usernames, null);
  }

  @Test
  void execute_withSingleUserId_shouldHandleCorrectly() {
    // Given: Activity with single user ID
    List<Long> userIds = Arrays.asList(12345L);
    activity.setUserIds(userIds);
    when(userService.listUsersByIds(userIds, null, null)).thenReturn(Arrays.asList(userList.get(0)));

    // When: Execute is called
    executor.execute(context);

    // Then: Should handle single user ID correctly
    verify(userService).listUsersByIds(userIds, null, null);
  }

  @Test
  void execute_withMultipleUserIds_shouldHandleCorrectly() {
    // Given: Activity with multiple user IDs
    List<Long> userIds = Arrays.asList(12345L, 67890L, 11111L, 22222L);
    activity.setUserIds(userIds);
    when(userService.listUsersByIds(userIds, null, null)).thenReturn(userList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should handle multiple user IDs correctly
    verify(userService).listUsersByIds(userIds, null, null);
  }

  @Test
  void execute_withSingleEmail_shouldHandleCorrectly() {
    // Given: Activity with single email
    List<String> emails = Arrays.asList("user@example.com");
    activity.setEmails(emails);
    when(userService.listUsersByEmails(emails, null, null)).thenReturn(Arrays.asList(userList.get(0)));

    // When: Execute is called
    executor.execute(context);

    // Then: Should handle single email correctly
    verify(userService).listUsersByEmails(emails, null, null);
  }

  @Test
  void execute_withMultipleEmails_shouldHandleCorrectly() {
    // Given: Activity with multiple emails
    List<String> emails = Arrays.asList("user1@example.com", "user2@example.com", "user3@example.com");
    activity.setEmails(emails);
    when(userService.listUsersByEmails(emails, null, null)).thenReturn(userList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should handle multiple emails correctly
    verify(userService).listUsersByEmails(emails, null, null);
  }

  @Test
  void execute_withUsernamesTakesPrecedenceOverUserIds() {
    // Given: Activity with both usernames and user IDs (usernames should take precedence based on code order)
    List<String> usernames = Arrays.asList("user1");
    List<Long> userIds = Arrays.asList(12345L);
    activity.setUsernames(usernames);
    activity.setUserIds(userIds);
    when(userService.listUsersByUsernames(usernames, null)).thenReturn(userList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use usernames (takes precedence)
    verify(userService).listUsersByUsernames(usernames, null);
    verifyNoMoreInteractions(userService);
  }

  @Test
  void execute_withUserIdsTakesPrecedenceOverEmails() {
    // Given: Activity with both user IDs and emails (user IDs should take precedence based on code order)
    List<Long> userIds = Arrays.asList(12345L);
    List<String> emails = Arrays.asList("user@example.com");
    activity.setUserIds(userIds);
    activity.setEmails(emails);
    when(userService.listUsersByIds(userIds, null, null)).thenReturn(userList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use user IDs (takes precedence)
    verify(userService).listUsersByIds(userIds, null, null);
    verifyNoMoreInteractions(userService);
  }

  @Test
  void execute_withActiveTrue_shouldPassActiveTrueToService() {
    // Given: Activity with active = true
    List<String> usernames = Arrays.asList("user1");
    activity.setUsernames(usernames);
    activity.setActive(true);
    when(userService.listUsersByUsernames(usernames, true)).thenReturn(userList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should pass active = true to service
    verify(userService).listUsersByUsernames(usernames, true);
  }

  @Test
  void execute_withActiveFalse_shouldPassActiveFalseToService() {
    // Given: Activity with active = false
    List<String> usernames = Arrays.asList("user1");
    activity.setUsernames(usernames);
    activity.setActive(false);
    when(userService.listUsersByUsernames(usernames, false)).thenReturn(userList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should pass active = false to service
    verify(userService).listUsersByUsernames(usernames, false);
  }

  @Test
  void execute_withLocalTrue_shouldPassLocalTrueToService() {
    // Given: Activity with local = true
    List<Long> userIds = Arrays.asList(12345L);
    activity.setUserIds(userIds);
    activity.setLocal(true);
    when(userService.listUsersByIds(userIds, true, null)).thenReturn(userList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should pass local = true to service
    verify(userService).listUsersByIds(userIds, true, null);
  }

  @Test
  void execute_withLocalFalse_shouldPassLocalFalseToService() {
    // Given: Activity with local = false
    List<Long> userIds = Arrays.asList(12345L);
    activity.setUserIds(userIds);
    activity.setLocal(false);
    when(userService.listUsersByIds(userIds, false, null)).thenReturn(userList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should pass local = false to service
    verify(userService).listUsersByIds(userIds, false, null);
  }

  @Test
  void execute_withOboUsername_shouldUseOboWithUsername() {
    // Given: Activity with OBO username and usernames to search
    Obo obo = new Obo();
    obo.setUsername("obouser");
    activity.setObo(obo);
    List<String> usernames = Arrays.asList("user1", "user2");
    activity.setUsernames(usernames);
    activity.setActive(true);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    OboUserService oboUserService = mock(OboUserService.class);

    when(bdkGateway.obo("obouser")).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.users()).thenReturn(oboUserService);
    when(oboUserService.listUsersByUsernames(usernames, true)).thenReturn(userList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use OBO with username
    verify(bdkGateway).obo("obouser");
    verify(bdkGateway).obo(authSession);
    verify(oboServices).users();
    verify(oboUserService).listUsersByUsernames(usernames, true);
    verify(context).setOutputVariable("users", userList);
  }

  @Test
  void execute_withOboUserId_shouldUseOboWithUserId() {
    // Given: Activity with OBO user ID and user IDs to search
    Obo obo = new Obo();
    obo.setUserId(98765L);
    activity.setObo(obo);
    List<Long> userIds = Arrays.asList(12345L, 67890L);
    activity.setUserIds(userIds);
    activity.setLocal(true);
    activity.setActive(false);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    OboUserService oboUserService = mock(OboUserService.class);

    when(bdkGateway.obo(98765L)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.users()).thenReturn(oboUserService);
    when(oboUserService.listUsersByIds(userIds, true, false)).thenReturn(userList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use OBO with user ID
    verify(bdkGateway).obo(98765L);
    verify(bdkGateway).obo(authSession);
    verify(oboServices).users();
    verify(oboUserService).listUsersByIds(userIds, true, false);
    verify(context).setOutputVariable("users", userList);
  }

  @Test
  void execute_withOboUsernameAndEmails_shouldUseOboToSearchByEmails() {
    // Given: Activity with OBO username and emails to search
    Obo obo = new Obo();
    obo.setUsername("obouser");
    activity.setObo(obo);
    List<String> emails = Arrays.asList("user1@example.com", "user2@example.com");
    activity.setEmails(emails);
    activity.setLocal(false);
    activity.setActive(true);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    OboUserService oboUserService = mock(OboUserService.class);

    when(bdkGateway.obo("obouser")).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.users()).thenReturn(oboUserService);
    when(oboUserService.listUsersByEmails(emails, false, true)).thenReturn(userList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use OBO to search by emails
    verify(bdkGateway).obo("obouser");
    verify(bdkGateway).obo(authSession);
    verify(oboServices).users();
    verify(oboUserService).listUsersByEmails(emails, false, true);
    verify(context).setOutputVariable("users", userList);
  }

  @Test
  void execute_withOboButNoUsernameOrUserId_shouldNotUseObo() {
    // Given: Activity with OBO but no username or user ID (OBO is effectively disabled)
    Obo obo = new Obo();
    activity.setObo(obo);
    List<String> usernames = Arrays.asList("user1");
    activity.setUsernames(usernames);
    when(userService.listUsersByUsernames(usernames, null)).thenReturn(userList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use regular service (not OBO)
    verify(bdkGateway).users();
    verify(userService).listUsersByUsernames(usernames, null);
    verify(context).setOutputVariable("users", userList);
  }

  @Test
  void execute_withOboUsername_shouldPrioritizeOboOverRegularCall() {
    // Given: Activity with OBO username
    Obo obo = new Obo();
    obo.setUsername("obouser");
    activity.setObo(obo);
    List<String> usernames = Arrays.asList("user1");
    activity.setUsernames(usernames);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    OboUserService oboUserService = mock(OboUserService.class);

    when(bdkGateway.obo("obouser")).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.users()).thenReturn(oboUserService);
    when(oboUserService.listUsersByUsernames(usernames, null)).thenReturn(userList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use OBO service, not regular service
    verify(oboUserService).listUsersByUsernames(usernames, null);
    verifyNoMoreInteractions(userService);
  }

  @Test
  void doOboWithCache_withUsernames_shouldReturnUsersByUsernames() {
    // Given: Activity with usernames
    List<String> usernames = Arrays.asList("user1", "user2");
    activity.setUsernames(usernames);
    activity.setActive(true);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    OboUserService oboUserService = mock(OboUserService.class);

    Obo obo = new Obo();
    obo.setUsername("obouser");
    activity.setObo(obo);

    when(bdkGateway.obo("obouser")).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.users()).thenReturn(oboUserService);
    when(oboUserService.listUsersByUsernames(usernames, true)).thenReturn(userList);

    // When: doOboWithCache is called
    executor.execute(context);

    // Then: Should return users by usernames
    verify(oboUserService).listUsersByUsernames(usernames, true);
    verify(context).setOutputVariable("users", userList);
  }

  @Test
  void doOboWithCache_withUserIds_shouldReturnUsersByIds() {
    // Given: Activity with user IDs
    List<Long> userIds = Arrays.asList(12345L, 67890L);
    activity.setUserIds(userIds);
    activity.setLocal(true);
    activity.setActive(false);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    OboUserService oboUserService = mock(OboUserService.class);

    Obo obo = new Obo();
    obo.setUserId(98765L);
    activity.setObo(obo);

    when(bdkGateway.obo(98765L)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.users()).thenReturn(oboUserService);
    when(oboUserService.listUsersByIds(userIds, true, false)).thenReturn(userList);

    // When: doOboWithCache is called
    executor.execute(context);

    // Then: Should return users by IDs
    verify(oboUserService).listUsersByIds(userIds, true, false);
    verify(context).setOutputVariable("users", userList);
  }

  @Test
  void doOboWithCache_withEmails_shouldReturnUsersByEmails() {
    // Given: Activity with emails
    List<String> emails = Arrays.asList("user1@example.com", "user2@example.com");
    activity.setEmails(emails);
    activity.setLocal(false);
    activity.setActive(true);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    OboUserService oboUserService = mock(OboUserService.class);

    Obo obo = new Obo();
    obo.setUsername("obouser");
    activity.setObo(obo);

    when(bdkGateway.obo("obouser")).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.users()).thenReturn(oboUserService);
    when(oboUserService.listUsersByEmails(emails, false, true)).thenReturn(userList);

    // When: doOboWithCache is called
    executor.execute(context);

    // Then: Should return users by emails
    verify(oboUserService).listUsersByEmails(emails, false, true);
    verify(context).setOutputVariable("users", userList);
  }

  @Test
  void doOboWithCache_withNoSearchCriteria_shouldReturnEmptyList() {
    // Given: Activity with OBO but no search criteria
    Obo obo = new Obo();
    obo.setUsername("obouser");
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    OboUserService oboUserService = mock(OboUserService.class);

    when(bdkGateway.obo("obouser")).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.users()).thenReturn(oboUserService);

    // When: doOboWithCache is called
    executor.execute(context);

    // Then: Should set empty list as output
    verify(context).setOutputVariable("users", Collections.emptyList());
  }

  @Test
  void doOboWithCache_withUsernamesTakesPrecedenceOverUserIds() {
    // Given: Activity with both usernames and user IDs
    List<String> usernames = Arrays.asList("user1");
    List<Long> userIds = Arrays.asList(12345L);
    activity.setUsernames(usernames);
    activity.setUserIds(userIds);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    OboUserService oboUserService = mock(OboUserService.class);

    Obo obo = new Obo();
    obo.setUsername("obouser");
    activity.setObo(obo);

    when(bdkGateway.obo("obouser")).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.users()).thenReturn(oboUserService);
    when(oboUserService.listUsersByUsernames(usernames, null)).thenReturn(userList);

    // When: doOboWithCache is called
    executor.execute(context);

    // Then: Should use usernames (takes precedence)
    verify(oboUserService).listUsersByUsernames(usernames, null);
    verifyNoMoreInteractions(oboUserService);
  }

  @Test
  void doOboWithCache_withUserIdsTakesPrecedenceOverEmails() {
    // Given: Activity with both user IDs and emails
    List<Long> userIds = Arrays.asList(12345L);
    List<String> emails = Arrays.asList("user@example.com");
    activity.setUserIds(userIds);
    activity.setEmails(emails);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    OboUserService oboUserService = mock(OboUserService.class);

    Obo obo = new Obo();
    obo.setUsername("obouser");
    activity.setObo(obo);

    when(bdkGateway.obo("obouser")).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.users()).thenReturn(oboUserService);
    when(oboUserService.listUsersByIds(userIds, null, null)).thenReturn(userList);

    // When: doOboWithCache is called
    executor.execute(context);

    // Then: Should use user IDs (takes precedence)
    verify(oboUserService).listUsersByIds(userIds, null, null);
    verifyNoMoreInteractions(oboUserService);
  }

  @Test
  void doOboWithCache_withOboUsernamePreferredOverUserId() {
    // Given: Activity with both OBO username and user ID (username should be preferred)
    List<String> usernames = Arrays.asList("user1");
    activity.setUsernames(usernames);

    Obo obo = new Obo();
    obo.setUsername("obouser");
    obo.setUserId(98765L);
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    OboUserService oboUserService = mock(OboUserService.class);

    when(bdkGateway.obo("obouser")).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.users()).thenReturn(oboUserService);
    when(oboUserService.listUsersByUsernames(usernames, null)).thenReturn(userList);

    // When: doOboWithCache is called
    executor.execute(context);

    // Then: Should use OBO username (not user ID)
    verify(bdkGateway).obo("obouser");
    verify(oboUserService).listUsersByUsernames(usernames, null);
  }

  @Test
  void execute_withAllNullSearchCriteria_shouldSetNullAsOutput() {
    // Given: Activity with no search criteria
    activity.setUsernames(null);
    activity.setUserIds(null);
    activity.setEmails(null);

    // When: Execute is called
    executor.execute(context);

    // Then: Should set null as output
    verify(context).setOutputVariable("users", null);
  }
}
