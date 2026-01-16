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

class SpringBdkGatewayClaude_groupsTest {

  private SpringBdkGateway gateway;
  private SymphonyGroupService groupService;

  @BeforeEach
  void setUp() {
    // Given: A SpringBdkGateway with mocked dependencies
    BdkConfig config = new BdkConfig();
    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    MessageService messageService = Mockito.mock(MessageService.class);
    StreamService streamService = Mockito.mock(StreamService.class);
    UserService userService = Mockito.mock(UserService.class);
    ConnectionService connectionService = Mockito.mock(ConnectionService.class);
    groupService = Mockito.mock(SymphonyGroupService.class);
    SessionService sessionService = Mockito.mock(SessionService.class);

    gateway = new SpringBdkGateway(config, authenticatorFactory,
        messageService, streamService, userService, connectionService, groupService, sessionService);
  }

  // Tests for groups() method

  @Test
  void groups_shouldReturnNonNullSymphonyGroupService() {
    // When: Calling groups()
    SymphonyGroupService result = gateway.groups();

    // Then: The result should not be null
    assertThat(result).isNotNull();
  }

  @Test
  void groups_shouldReturnSymphonyGroupService() {
    // When: Calling groups()
    SymphonyGroupService result = gateway.groups();

    // Then: The result should be an instance of SymphonyGroupService
    assertThat(result).isInstanceOf(SymphonyGroupService.class);
  }

  @Test
  void groups_shouldReturnSameInstanceAsConstructorParameter() {
    // When: Calling groups()
    SymphonyGroupService result = gateway.groups();

    // Then: The result should be the same instance that was passed to the constructor
    assertThat(result).isSameAs(groupService);
  }

  @Test
  void groups_shouldReturnConsistentResult() {
    // When: Calling groups() multiple times
    SymphonyGroupService result1 = gateway.groups();
    SymphonyGroupService result2 = gateway.groups();
    SymphonyGroupService result3 = gateway.groups();

    // Then: All results should be the same instance
    assertThat(result1).isSameAs(result2);
    assertThat(result2).isSameAs(result3);
    assertThat(result1).isSameAs(result3);
  }

  @Test
  void groups_shouldReturnSymphonyGroupServiceWithCorrectIdentity() {
    // When: Calling groups()
    SymphonyGroupService result = gateway.groups();

    // Then: The result should have the same identity hash code as the original
    assertThat(System.identityHashCode(result))
        .isEqualTo(System.identityHashCode(groupService));
  }

  @Test
  void groups_shouldNotReturnNull() {
    // When: Calling groups()
    SymphonyGroupService result = gateway.groups();

    // Then: The result should not be equal to null
    assertThat(result).isNotEqualTo(null);
  }

  @Test
  void groups_shouldReturnSameReferenceOnSubsequentCalls() {
    // When: Calling groups() twice and comparing references
    SymphonyGroupService firstCall = gateway.groups();
    SymphonyGroupService secondCall = gateway.groups();

    // Then: Both calls should return the exact same reference
    assertThat(firstCall == secondCall).isTrue();
  }

  @Test
  void groups_shouldMaintainReferenceAcrossMultipleCalls() {
    // When: Calling groups() multiple times in succession
    for (int i = 0; i < 10; i++) {
      SymphonyGroupService result = gateway.groups();

      // Then: Each call should return the same instance
      assertThat(result).isSameAs(groupService);
    }
  }

  @Test
  void groups_shouldReturnDifferentInstancesForDifferentGateways() {
    // Given: A second SpringBdkGateway with a different SymphonyGroupService
    SymphonyGroupService groupService2 = Mockito.mock(SymphonyGroupService.class);
    SpringBdkGateway gateway2 = new SpringBdkGateway(
        new BdkConfig(),
        Mockito.mock(AuthenticatorFactory.class),
        Mockito.mock(MessageService.class),
        Mockito.mock(StreamService.class),
        Mockito.mock(UserService.class),
        Mockito.mock(ConnectionService.class),
        groupService2,
        Mockito.mock(SessionService.class)
    );

    // When: Calling groups() on both gateways
    SymphonyGroupService result1 = gateway.groups();
    SymphonyGroupService result2 = gateway2.groups();

    // Then: The results should be different instances
    assertThat(result1).isNotSameAs(result2);
  }

  @Test
  void groups_shouldReturnServiceThatEqualsOriginal() {
    // When: Calling groups()
    SymphonyGroupService result = gateway.groups();

    // Then: The result should be equal to the original service
    assertThat(result).isEqualTo(groupService);
  }

  @Test
  void groups_shouldReturnServiceWithSameToString() {
    // When: Calling groups() and converting to string
    String resultString = gateway.groups().toString();
    String originalString = groupService.toString();

    // Then: The toString values should be identical
    assertThat(resultString).isEqualTo(originalString);
  }

  @Test
  void groups_shouldReturnServiceWithSameHashCode() {
    // When: Calling groups() and getting hash code
    int resultHashCode = gateway.groups().hashCode();
    int originalHashCode = groupService.hashCode();

    // Then: The hash codes should be identical
    assertThat(resultHashCode).isEqualTo(originalHashCode);
  }

  @Test
  void groups_shouldNotModifyInternalState() {
    // Given: Initial call to groups()
    SymphonyGroupService firstResult = gateway.groups();

    // When: Calling groups() again after the first call
    SymphonyGroupService secondResult = gateway.groups();

    // Then: The method should not have modified any internal state
    assertThat(firstResult).isSameAs(secondResult);
    assertThat(gateway.groups()).isSameAs(groupService);
  }

  @Test
  void groups_shouldReturnExpectedTypeImplementation() {
    // When: Calling groups()
    Object result = gateway.groups();

    // Then: The result should be assignable to SymphonyGroupService interface
    assertThat(SymphonyGroupService.class.isAssignableFrom(result.getClass())).isTrue();
  }

  @Test
  void groups_shouldNotCreateNewInstance() {
    // Given: The original groupService mock
    SymphonyGroupService original = groupService;

    // When: Calling groups()
    SymphonyGroupService result = gateway.groups();

    // Then: No new instance should have been created
    assertThat(result).isSameAs(original);
    assertThat(result == original).isTrue();
  }
}
