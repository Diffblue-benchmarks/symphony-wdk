package com.symphony.bdk.workflow.engine.executor.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.gen.api.model.UserV2;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.user.GetUsers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class GetUsersExecutorTest {

  private GetUsersExecutor executor;
  private ActivityExecutorContext<GetUsers> context;
  private BdkGateway bdkGateway;
  private UserService userService;
  private GetUsers activity;

  @BeforeEach
  void setUp() {
    executor = new GetUsersExecutor();
    context = mock(ActivityExecutorContext.class);
    bdkGateway = mock(BdkGateway.class);
    userService = mock(UserService.class);
    activity = new GetUsers();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.users()).thenReturn(userService);
  }

  @Test
  void shouldGetUsersByUsernames() {
    List<String> usernames = Arrays.asList("user1", "user2");
    activity.setUsernames(usernames);
    activity.setActive(true);

    List<UserV2> expectedUsers = Arrays.asList(new UserV2(), new UserV2());
    when(userService.listUsersByUsernames(anyList(), anyBoolean())).thenReturn(expectedUsers);

    executor.execute(context);

    verify(userService).listUsersByUsernames(eq(usernames), eq(true));
    verify(context).setOutputVariable(eq("users"), eq(expectedUsers));
  }

  @Test
  void shouldGetUsersByUserIds() {
    List<Long> userIds = Arrays.asList(123L, 456L);
    activity.setUserIds(userIds);
    activity.setLocal(true);
    activity.setActive(false);

    List<UserV2> expectedUsers = Arrays.asList(new UserV2(), new UserV2());
    when(userService.listUsersByIds(anyList(), anyBoolean(), anyBoolean())).thenReturn(expectedUsers);

    executor.execute(context);

    verify(userService).listUsersByIds(eq(userIds), eq(true), eq(false));
    verify(context).setOutputVariable(eq("users"), eq(expectedUsers));
  }

  @Test
  void shouldGetUsersByEmails() {
    List<String> emails = Arrays.asList("user1@test.com", "user2@test.com");
    activity.setEmails(emails);
    activity.setLocal(false);
    activity.setActive(true);

    List<UserV2> expectedUsers = Arrays.asList(new UserV2(), new UserV2());
    when(userService.listUsersByEmails(anyList(), anyBoolean(), anyBoolean())).thenReturn(expectedUsers);

    executor.execute(context);

    verify(userService).listUsersByEmails(eq(emails), eq(false), eq(true));
    verify(context).setOutputVariable(eq("users"), eq(expectedUsers));
  }

  @Test
  void shouldCallDoOboWithCacheWhenOboIsSet() {
    List<String> usernames = Arrays.asList("user1");
    activity.setUsernames(usernames);
    activity.setActive(true);
    Obo obo = new Obo();
    obo.setUsername("obouser");
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    UserService oboUserService = mock(UserService.class);
    List<UserV2> expectedUsers = Arrays.asList(new UserV2());

    when(bdkGateway.obo("obouser")).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.users()).thenReturn(oboUserService);
    when(oboUserService.listUsersByUsernames(anyList(), anyBoolean())).thenReturn(expectedUsers);

    executor.execute(context);

    verify(bdkGateway).obo("obouser");
    verify(oboServices).users();
    verify(oboUserService).listUsersByUsernames(eq(usernames), eq(true));
    verify(context).setOutputVariable(eq("users"), eq(expectedUsers));
  }

  @Test
  void shouldGetUsersByUsernamesWithOboUsername() {
    List<String> usernames = Arrays.asList("targetUser");
    activity.setUsernames(usernames);
    activity.setActive(true);
    Obo obo = new Obo();
    obo.setUsername("obouser");
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    UserService oboUserService = mock(UserService.class);
    List<UserV2> expectedUsers = Arrays.asList(new UserV2());

    when(bdkGateway.obo("obouser")).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.users()).thenReturn(oboUserService);
    when(oboUserService.listUsersByUsernames(anyList(), anyBoolean())).thenReturn(expectedUsers);

    executor.execute(context);

    verify(oboUserService).listUsersByUsernames(eq(usernames), eq(true));
    verify(context).setOutputVariable(eq("users"), eq(expectedUsers));
  }

  @Test
  void shouldGetUsersByUsernamesWithOboUserId() {
    List<String> usernames = Arrays.asList("targetUser");
    activity.setUsernames(usernames);
    activity.setActive(false);
    Obo obo = new Obo();
    obo.setUserId(999L);
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    UserService oboUserService = mock(UserService.class);
    List<UserV2> expectedUsers = Arrays.asList(new UserV2());

    when(bdkGateway.obo(999L)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.users()).thenReturn(oboUserService);
    when(oboUserService.listUsersByUsernames(anyList(), anyBoolean())).thenReturn(expectedUsers);

    executor.execute(context);

    verify(bdkGateway).obo(999L);
    verify(oboUserService).listUsersByUsernames(eq(usernames), eq(false));
    verify(context).setOutputVariable(eq("users"), eq(expectedUsers));
  }

  @Test
  void shouldGetUsersByUserIdsWithObo() {
    List<Long> userIds = Arrays.asList(111L, 222L);
    activity.setUserIds(userIds);
    activity.setLocal(true);
    activity.setActive(true);
    Obo obo = new Obo();
    obo.setUsername("obouser");
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    UserService oboUserService = mock(UserService.class);
    List<UserV2> expectedUsers = Arrays.asList(new UserV2(), new UserV2());

    when(bdkGateway.obo("obouser")).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.users()).thenReturn(oboUserService);
    when(oboUserService.listUsersByIds(anyList(), anyBoolean(), anyBoolean())).thenReturn(expectedUsers);

    executor.execute(context);

    verify(oboUserService).listUsersByIds(eq(userIds), eq(true), eq(true));
    verify(context).setOutputVariable(eq("users"), eq(expectedUsers));
  }

  @Test
  void shouldGetUsersByEmailsWithObo() {
    List<String> emails = Arrays.asList("test@example.com");
    activity.setEmails(emails);
    activity.setLocal(false);
    activity.setActive(false);
    Obo obo = new Obo();
    obo.setUserId(777L);
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    UserService oboUserService = mock(UserService.class);
    List<UserV2> expectedUsers = Arrays.asList(new UserV2());

    when(bdkGateway.obo(777L)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.users()).thenReturn(oboUserService);
    when(oboUserService.listUsersByEmails(anyList(), anyBoolean(), anyBoolean())).thenReturn(expectedUsers);

    executor.execute(context);

    verify(oboUserService).listUsersByEmails(eq(emails), eq(false), eq(false));
    verify(context).setOutputVariable(eq("users"), eq(expectedUsers));
  }

  @Test
  void shouldReturnEmptyListWhenOboAndNoAttributesSet() {
    Obo obo = new Obo();
    obo.setUsername("obouser");
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    UserService oboUserService = mock(UserService.class);

    when(bdkGateway.obo("obouser")).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.users()).thenReturn(oboUserService);

    executor.execute(context);

    verify(context).setOutputVariable(eq("users"), eq(Collections.emptyList()));
  }
}
