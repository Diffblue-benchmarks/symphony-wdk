package com.symphony.devsol.client;

import com.symphony.bdk.core.auth.jwt.UserClaim;
import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.gen.api.model.V2UserDetail;
import com.symphony.bdk.gen.api.model.UserV2;
import com.symphony.devsol.model.wdk.Profile;
import com.symphony.devsol.model.wdk.SimpleUser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SymphonyClientTest {

  @Mock
  private UserService users;

  @Mock
  private SessionService session;

  @InjectMocks
  private SymphonyClient symphonyClient;

  @BeforeEach
  void setUp() {
    ReflectionTestUtils.setField(symphonyClient, "appId", "testAppId");
    ReflectionTestUtils.setField(symphonyClient, "admins", List.of(100L, 200L));
  }

  @Test
  void shouldReturnAppIdAndDisplayName() {
    V2UserDetail sessionUser = new V2UserDetail();
    sessionUser.setDisplayName("Test Bot");
    when(session.getSession()).thenReturn(sessionUser);

    Map<String, ?> result = symphonyClient.getAppId();

    assertThat(result, hasEntry("appId", "testAppId"));
    assertThat(result, hasEntry("name", "Test Bot"));
  }

  @Test
  void shouldReturnAdminProfileWhenUserIsAdmin() {
    UserClaim user = new UserClaim("", 100L, null, null, null, null, null, null);

    Profile profile = symphonyClient.getProfile(user);

    assertThat(profile.isAdmin(), is(true));
  }

  @Test
  void shouldReturnNonAdminProfileWhenUserIsNotAdmin() {
    UserClaim user = new UserClaim("", 999L, null, null, null, null, null, null);

    Profile profile = symphonyClient.getProfile(user);

    assertThat(profile.isAdmin(), is(false));
  }

  @Test
  void shouldReturnSimpleUserWhenFoundById() {
    UserV2 userV2 = new UserV2();
    userV2.setId(42L);
    userV2.setDisplayName("John Doe");
    when(users.listUsersByIds(List.of(42L))).thenReturn(List.of(userV2));

    SimpleUser result = symphonyClient.getSymphonyUser(42L);

    assertThat(result.getId(), equalTo(42L));
    assertThat(result.getDisplayName(), equalTo("John Doe"));
  }

  @Test
  void shouldThrowNotFoundWhenUserNotFoundById() {
    when(users.listUsersByIds(List.of(99L))).thenReturn(Collections.emptyList());

    assertThrows(ResponseStatusException.class, () -> symphonyClient.getSymphonyUser(99L));
  }

  @Test
  void shouldReturnFilteredAndSortedUsersForQuery() {
    UserV2 user1 = new UserV2();
    user1.setId(1L);
    user1.setDisplayName("Zebra");
    UserV2 user2 = new UserV2();
    user2.setId(2L);
    user2.setDisplayName("Alpha");
    UserV2 userWithNullName = new UserV2();
    userWithNullName.setId(3L);
    userWithNullName.setDisplayName(null);
    when(users.searchUsers(any(), anyBoolean())).thenReturn(List.of(user1, user2, userWithNullName));

    List<SimpleUser> result = symphonyClient.getSymphonyUser("test");

    assertThat(result, hasSize(2));
    assertThat(result.get(0).getDisplayName(), equalTo("Alpha"));
    assertThat(result.get(1).getDisplayName(), equalTo("Zebra"));
  }

  @Test
  void shouldReturnUserMapForBulkUserIds() {
    UserV2 user1 = new UserV2();
    user1.setId(10L);
    user1.setDisplayName("User One");
    UserV2 user2 = new UserV2();
    user2.setId(20L);
    user2.setDisplayName("User Two");
    when(users.listUsersByIds(List.of(10L, 20L))).thenReturn(List.of(user1, user2));

    Map<Long, String> result = symphonyClient.getSymphonyUsers(List.of(10L, 20L));

    assertThat(result, hasEntry(10L, "User One"));
    assertThat(result, hasEntry(20L, "User Two"));
  }
}
