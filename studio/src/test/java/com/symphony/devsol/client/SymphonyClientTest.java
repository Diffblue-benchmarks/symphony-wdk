package com.symphony.devsol.client;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.gen.api.model.UserSearchQuery;
import com.symphony.bdk.gen.api.model.UserV2;
import com.symphony.devsol.model.wdk.SimpleUser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class SymphonyClientTest {

  private SymphonyClient symphonyClient;
  private UserService userService;

  @BeforeEach
  void setUp() {
    userService = mock(UserService.class);
    symphonyClient = new SymphonyClient(userService, null);
  }

  @Test
  void shouldReturnSearchedUsersWhenQueryProvided() {
    UserV2 user1 = new UserV2().id(1L).displayName("Alice");
    UserV2 user2 = new UserV2().id(2L).displayName("Bob");
    UserV2 user3 = new UserV2().id(3L).displayName("Charlie");
    when(userService.searchUsers(any(UserSearchQuery.class), eq(true)))
        .thenReturn(Arrays.asList(user1, user2, user3));

    List<SimpleUser> result = symphonyClient.getSymphonyUser("test");

    assertThat(result, hasSize(3));
    assertThat(result.get(0).getId(), equalTo(1L));
    assertThat(result.get(0).getDisplayName(), equalTo("Alice"));
    assertThat(result.get(1).getId(), equalTo(2L));
    assertThat(result.get(1).getDisplayName(), equalTo("Bob"));
    assertThat(result.get(2).getId(), equalTo(3L));
    assertThat(result.get(2).getDisplayName(), equalTo("Charlie"));
  }

  @Test
  void shouldFilterUsersWithNullDisplayName() {
    UserV2 user1 = new UserV2().id(1L).displayName("Alice");
    UserV2 user2 = new UserV2().id(2L).displayName(null);
    UserV2 user3 = new UserV2().id(3L).displayName("Charlie");
    when(userService.searchUsers(any(UserSearchQuery.class), eq(true)))
        .thenReturn(Arrays.asList(user1, user2, user3));

    List<SimpleUser> result = symphonyClient.getSymphonyUser("test");

    assertThat(result, hasSize(2));
    assertThat(result.get(0).getDisplayName(), equalTo("Alice"));
    assertThat(result.get(1).getDisplayName(), equalTo("Charlie"));
  }

  @Test
  void shouldSortUsersByDisplayName() {
    UserV2 user1 = new UserV2().id(1L).displayName("Zoe");
    UserV2 user2 = new UserV2().id(2L).displayName("Alice");
    UserV2 user3 = new UserV2().id(3L).displayName("Mike");
    when(userService.searchUsers(any(UserSearchQuery.class), eq(true)))
        .thenReturn(Arrays.asList(user1, user2, user3));

    List<SimpleUser> result = symphonyClient.getSymphonyUser("test");

    assertThat(result, hasSize(3));
    assertThat(result.get(0).getDisplayName(), equalTo("Alice"));
    assertThat(result.get(1).getDisplayName(), equalTo("Mike"));
    assertThat(result.get(2).getDisplayName(), equalTo("Zoe"));
  }

  @Test
  void shouldLimitResultsToTen() {
    List<UserV2> users = Arrays.asList(
        new UserV2().id(1L).displayName("User01"),
        new UserV2().id(2L).displayName("User02"),
        new UserV2().id(3L).displayName("User03"),
        new UserV2().id(4L).displayName("User04"),
        new UserV2().id(5L).displayName("User05"),
        new UserV2().id(6L).displayName("User06"),
        new UserV2().id(7L).displayName("User07"),
        new UserV2().id(8L).displayName("User08"),
        new UserV2().id(9L).displayName("User09"),
        new UserV2().id(10L).displayName("User10"),
        new UserV2().id(11L).displayName("User11"),
        new UserV2().id(12L).displayName("User12")
    );
    when(userService.searchUsers(any(UserSearchQuery.class), eq(true)))
        .thenReturn(users);

    List<SimpleUser> result = symphonyClient.getSymphonyUser("user");

    assertThat(result, hasSize(10));
  }

  @Test
  void shouldReturnEmptyListWhenNoUsersFound() {
    when(userService.searchUsers(any(UserSearchQuery.class), eq(true)))
        .thenReturn(Collections.emptyList());

    List<SimpleUser> result = symphonyClient.getSymphonyUser("test");

    assertThat(result, empty());
  }

  @Test
  void shouldFilterOutNullNamesAndSort() {
    UserV2 user1 = new UserV2().id(1L).displayName("Zoe");
    UserV2 user2 = new UserV2().id(2L).displayName(null);
    UserV2 user3 = new UserV2().id(3L).displayName("Alice");
    UserV2 user4 = new UserV2().id(4L).displayName(null);
    UserV2 user5 = new UserV2().id(5L).displayName("Mike");
    when(userService.searchUsers(any(UserSearchQuery.class), eq(true)))
        .thenReturn(Arrays.asList(user1, user2, user3, user4, user5));

    List<SimpleUser> result = symphonyClient.getSymphonyUser("test");

    assertThat(result, hasSize(3));
    assertThat(result.get(0).getDisplayName(), equalTo("Alice"));
    assertThat(result.get(1).getDisplayName(), equalTo("Mike"));
    assertThat(result.get(2).getDisplayName(), equalTo("Zoe"));
  }

  @Test
  void shouldHandleSingleUser() {
    UserV2 user = new UserV2().id(1L).displayName("John");
    when(userService.searchUsers(any(UserSearchQuery.class), eq(true)))
        .thenReturn(Collections.singletonList(user));

    List<SimpleUser> result = symphonyClient.getSymphonyUser("john");

    assertThat(result, hasSize(1));
    assertThat(result.get(0).getId(), equalTo(1L));
    assertThat(result.get(0).getDisplayName(), equalTo("John"));
  }

  @Test
  void shouldReturnUserWhenUserIdExists() {
    UserV2 user = new UserV2().id(123L).displayName("John Doe");
    when(userService.listUsersByIds(List.of(123L)))
        .thenReturn(Collections.singletonList(user));

    SimpleUser result = symphonyClient.getSymphonyUser(123L);

    assertThat(result.getId(), equalTo(123L));
    assertThat(result.getDisplayName(), equalTo("John Doe"));
  }

  @Test
  void shouldThrowNotFoundExceptionWhenUserIdDoesNotExist() {
    when(userService.listUsersByIds(List.of(999L)))
        .thenReturn(Collections.emptyList());

    ResponseStatusException exception = assertThrows(
        ResponseStatusException.class,
        () -> symphonyClient.getSymphonyUser(999L)
    );

    assertThat(exception.getStatusCode().value(), equalTo(404));
  }
}
