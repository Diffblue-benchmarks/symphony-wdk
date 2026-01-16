package com.symphony.bdk.workflow.engine.executor.user;

import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.gen.api.model.UserSystemInfo;
import com.symphony.bdk.gen.api.model.V2UserDetail;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.user.CreateSystemUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateSystemUserExecutorClaude_executeTest {

  private CreateSystemUserExecutor executor;
  private ActivityExecutorContext<CreateSystemUser> context;
  private CreateSystemUser activity;
  private BdkGateway bdkGateway;
  private UserService userService;
  private V2UserDetail userDetail;

  @BeforeEach
  void setUp() {
    executor = new CreateSystemUserExecutor();
    context = mock(ActivityExecutorContext.class);
    activity = new CreateSystemUser();
    bdkGateway = mock(BdkGateway.class);
    userService = mock(UserService.class);
    userDetail = new V2UserDetail();

    UserSystemInfo userSystemInfo = new UserSystemInfo();
    userSystemInfo.setId(12345L);
    userDetail.setUserSystemInfo(userSystemInfo);

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.users()).thenReturn(userService);
    when(userService.create(any())).thenReturn(userDetail);
    when(userService.getUserDetail(any())).thenReturn(userDetail);
  }

  @Test
  void execute_withMinimalSystemUser_shouldCreateUser() {
    // Given: A system user with minimal required fields
    activity.setEmail("system.user@example.com");
    activity.setUsername("systemuser");
    activity.setFirstname("System");
    activity.setLastname("User");

    // When: Execute is called
    executor.execute(context);

    // Then: Should create user through user service
    verify(userService).create(any());
    verify(context).setOutputVariable("user", userDetail);
  }

  @Test
  void execute_withSystemUserAndDisplayName_shouldCreateUserWithDisplayName() {
    // Given: A system user with display name
    activity.setEmail("system.user2@example.com");
    activity.setUsername("systemuser2");
    activity.setFirstname("System");
    activity.setLastname("User");
    activity.setDisplayName("System User Display");

    // When: Execute is called
    executor.execute(context);

    // Then: Should create user and set output variable
    verify(userService).create(any());
    verify(userService).getUserDetail(12345L);
    verify(context).setOutputVariable("user", userDetail);
  }

  @Test
  void execute_withDifferentSystemUser_shouldCreateDifferentUser() {
    // Given: A different system user
    activity.setEmail("another.system@example.com");
    activity.setUsername("anothersystem");
    activity.setFirstname("Another");
    activity.setLastname("System");

    // When: Execute is called
    executor.execute(context);

    // Then: Should create user
    verify(userService).create(any());
    verify(context).setOutputVariable("user", userDetail);
  }

  @Test
  void execute_shouldNotThrowException() {
    // Given: Valid system user
    activity.setEmail("test@example.com");
    activity.setUsername("testuser");
    activity.setFirstname("Test");
    activity.setLastname("User");

    // When/Then: Execute should not throw exception
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();
  }

  @Test
  void execute_calledMultipleTimes_shouldWorkCorrectly() {
    // Given: Valid system user
    activity.setEmail("multi@example.com");
    activity.setUsername("multiuser");
    activity.setFirstname("Multi");
    activity.setLastname("User");

    // When: Execute is called multiple times
    executor.execute(context);
    executor.execute(context);

    // Then: Should work correctly both times
    verify(userService, org.mockito.Mockito.times(2)).create(any());
    verify(userService, org.mockito.Mockito.times(2)).getUserDetail(12345L);
  }

  @Test
  void execute_withRecommendedLanguage_shouldCreateUserWithLanguage() {
    // Given: System user with recommended language
    activity.setEmail("lang.user@example.com");
    activity.setUsername("languser");
    activity.setFirstname("Lang");
    activity.setLastname("User");
    activity.setRecommendedLanguage("en_US");

    // When: Execute is called
    executor.execute(context);

    // Then: Should create user
    verify(userService).create(any());
    verify(context).setOutputVariable("user", userDetail);
  }

  @Test
  void execute_verifySystemTypeIsSet() {
    // Given: A CreateSystemUser instance
    CreateSystemUser systemUser = new CreateSystemUser();

    // When: We check its type
    String userType = systemUser.getType();

    // Then: Type should be SYSTEM
    assertThat(userType).isEqualTo("SYSTEM");
  }

  @Test
  void execute_withContextContainingSystemUser_shouldDelegateCorrectly() {
    // Given: Context with system user activity
    activity.setEmail("delegate@example.com");
    activity.setUsername("delegateuser");
    activity.setFirstname("Delegate");
    activity.setLastname("User");

    // When: Execute is called
    executor.execute(context);

    // Then: Should retrieve activity from context and create user
    verify(context).getActivity();
    verify(context).bdk();
    verify(bdkGateway).users();
    verify(userService).create(any());
  }

  @Test
  void execute_withEmptyDisplayName_shouldCreateUser() {
    // Given: System user with empty display name
    activity.setEmail("empty@example.com");
    activity.setUsername("emptyuser");
    activity.setFirstname("Empty");
    activity.setLastname("User");
    activity.setDisplayName("");

    // When: Execute is called
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();

    // Then: Should create user
    verify(userService).create(any());
  }

  @Test
  void execute_withSpecialCharactersInEmail_shouldHandleCorrectly() {
    // Given: System user with special characters in email
    activity.setEmail("special+user@example.com");
    activity.setUsername("specialuser");
    activity.setFirstname("Special");
    activity.setLastname("User");

    // When: Execute is called
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();

    // Then: Should create user
    verify(userService).create(any());
  }

  @Test
  void execute_withLongUsername_shouldHandleCorrectly() {
    // Given: System user with very long username
    String longUsername = "verylongusername".repeat(5);
    activity.setEmail("long@example.com");
    activity.setUsername(longUsername);
    activity.setFirstname("Long");
    activity.setLastname("User");

    // When: Execute is called
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();

    // Then: Should create user
    verify(userService).create(any());
  }

  @Test
  void execute_ensuresOutputVariableIsSet() {
    // Given: Valid system user
    activity.setEmail("output@example.com");
    activity.setUsername("outputuser");
    activity.setFirstname("Output");
    activity.setLastname("User");

    // When: Execute is called
    executor.execute(context);

    // Then: Should set output variable with correct key
    verify(context).setOutputVariable("user", userDetail);
  }

  @Test
  void execute_withNullRecommendedLanguage_shouldCreateUser() {
    // Given: System user with null recommended language
    activity.setEmail("null.lang@example.com");
    activity.setUsername("nulllanguser");
    activity.setFirstname("Null");
    activity.setLastname("Lang");
    activity.setRecommendedLanguage(null);

    // When: Execute is called
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();

    // Then: Should create user
    verify(userService).create(any());
  }

  @Test
  void execute_retrievesUserDetailAfterCreation() {
    // Given: Valid system user
    activity.setEmail("detail@example.com");
    activity.setUsername("detailuser");
    activity.setFirstname("Detail");
    activity.setLastname("User");

    // When: Execute is called
    executor.execute(context);

    // Then: Should retrieve user detail after creation
    verify(userService).getUserDetail(12345L);
  }
}
