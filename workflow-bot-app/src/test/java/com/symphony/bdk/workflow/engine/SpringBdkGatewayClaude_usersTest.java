package com.symphony.bdk.workflow.engine;

import com.symphony.bdk.core.auth.AuthenticatorFactory;
import com.symphony.bdk.core.config.model.BdkConfig;
import com.symphony.bdk.core.service.connection.ConnectionService;
import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.ext.group.SymphonyGroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

class SpringBdkGatewayClaude_usersTest {

  private SpringBdkGateway gateway;
  private UserService userService;

  @BeforeEach
  void setUp() {
    // Given: A SpringBdkGateway with mocked dependencies
    BdkConfig config = new BdkConfig();
    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    MessageService messageService = Mockito.mock(MessageService.class);
    StreamService streamService = Mockito.mock(StreamService.class);
    userService = Mockito.mock(UserService.class);
    ConnectionService connectionService = Mockito.mock(ConnectionService.class);
    SymphonyGroupService groupService = Mockito.mock(SymphonyGroupService.class);
    SessionService sessionService = Mockito.mock(SessionService.class);

    gateway = new SpringBdkGateway(config, authenticatorFactory,
        messageService, streamService, userService, connectionService, groupService, sessionService);
  }

  // Tests for users() method

  @Test
  void users_shouldReturnNonNullUserService() {
    // When: Calling users()
    UserService result = gateway.users();

    // Then: The result should not be null
    assertThat(result).isNotNull();
  }

  @Test
  void users_shouldReturnUserService() {
    // When: Calling users()
    UserService result = gateway.users();

    // Then: The result should be an instance of UserService
    assertThat(result).isInstanceOf(UserService.class);
  }

  @Test
  void users_shouldReturnSameInstanceAsConstructorParameter() {
    // When: Calling users()
    UserService result = gateway.users();

    // Then: The result should be the same instance that was passed to the constructor
    assertThat(result).isSameAs(userService);
  }

  @Test
  void users_shouldReturnConsistentResult() {
    // When: Calling users() multiple times
    UserService result1 = gateway.users();
    UserService result2 = gateway.users();
    UserService result3 = gateway.users();

    // Then: All results should be the same instance
    assertThat(result1).isSameAs(result2);
    assertThat(result2).isSameAs(result3);
    assertThat(result1).isSameAs(result3);
  }

  @Test
  void users_shouldReturnUserServiceWithCorrectIdentity() {
    // When: Calling users()
    UserService result = gateway.users();

    // Then: The result should have the same identity hash code as the original
    assertThat(System.identityHashCode(result))
        .isEqualTo(System.identityHashCode(userService));
  }

  @Test
  void users_shouldNotReturnNull() {
    // When: Calling users()
    UserService result = gateway.users();

    // Then: The result should not be equal to null
    assertThat(result).isNotEqualTo(null);
  }

  @Test
  void users_shouldReturnSameReferenceOnSubsequentCalls() {
    // When: Calling users() twice and comparing references
    UserService firstCall = gateway.users();
    UserService secondCall = gateway.users();

    // Then: Both calls should return the exact same reference
    assertThat(firstCall == secondCall).isTrue();
  }

  @Test
  void users_shouldMaintainReferenceAcrossMultipleCalls() {
    // When: Calling users() multiple times in succession
    for (int i = 0; i < 10; i++) {
      UserService result = gateway.users();

      // Then: Each call should return the same instance
      assertThat(result).isSameAs(userService);
    }
  }

  @Test
  void users_shouldReturnDifferentInstancesForDifferentGateways() {
    // Given: A second SpringBdkGateway with a different UserService
    UserService userService2 = Mockito.mock(UserService.class);
    SpringBdkGateway gateway2 = new SpringBdkGateway(
        new BdkConfig(),
        Mockito.mock(AuthenticatorFactory.class),
        Mockito.mock(MessageService.class),
        Mockito.mock(StreamService.class),
        userService2,
        Mockito.mock(ConnectionService.class),
        Mockito.mock(SymphonyGroupService.class),
        Mockito.mock(SessionService.class)
    );

    // When: Calling users() on both gateways
    UserService result1 = gateway.users();
    UserService result2 = gateway2.users();

    // Then: The results should be different instances
    assertThat(result1).isNotSameAs(result2);
  }

  @Test
  void users_shouldReturnServiceThatEqualsOriginal() {
    // When: Calling users()
    UserService result = gateway.users();

    // Then: The result should be equal to the original service
    assertThat(result).isEqualTo(userService);
  }

  @Test
  void users_shouldReturnServiceWithSameToString() {
    // When: Calling users() and converting to string
    String resultString = gateway.users().toString();
    String originalString = userService.toString();

    // Then: The toString values should be identical
    assertThat(resultString).isEqualTo(originalString);
  }

  @Test
  void users_shouldReturnServiceWithSameHashCode() {
    // When: Calling users() and getting hash code
    int resultHashCode = gateway.users().hashCode();
    int originalHashCode = userService.hashCode();

    // Then: The hash codes should be identical
    assertThat(resultHashCode).isEqualTo(originalHashCode);
  }

  @Test
  void users_shouldNotModifyInternalState() {
    // Given: Initial call to users()
    UserService firstResult = gateway.users();

    // When: Calling users() again after the first call
    UserService secondResult = gateway.users();

    // Then: The method should not have modified any internal state
    assertThat(firstResult).isSameAs(secondResult);
    assertThat(gateway.users()).isSameAs(userService);
  }

  @Test
  void users_shouldReturnExpectedTypeImplementation() {
    // When: Calling users()
    Object result = gateway.users();

    // Then: The result should be assignable to UserService interface
    assertThat(UserService.class.isAssignableFrom(result.getClass())).isTrue();
  }

  @Test
  void users_shouldNotCreateNewInstance() {
    // Given: The original userService mock
    UserService original = userService;

    // When: Calling users()
    UserService result = gateway.users();

    // Then: No new instance should have been created
    assertThat(result).isSameAs(original);
    assertThat(result == original).isTrue();
  }
}
