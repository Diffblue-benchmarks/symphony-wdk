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

class SpringBdkGatewayClaude_streamsTest {

  private SpringBdkGateway gateway;
  private StreamService streamService;

  @BeforeEach
  void setUp() {
    // Given: A SpringBdkGateway with mocked dependencies
    BdkConfig config = new BdkConfig();
    AuthenticatorFactory authenticatorFactory = Mockito.mock(AuthenticatorFactory.class);
    MessageService messageService = Mockito.mock(MessageService.class);
    streamService = Mockito.mock(StreamService.class);
    UserService userService = Mockito.mock(UserService.class);
    ConnectionService connectionService = Mockito.mock(ConnectionService.class);
    SymphonyGroupService groupService = Mockito.mock(SymphonyGroupService.class);
    SessionService sessionService = Mockito.mock(SessionService.class);

    gateway = new SpringBdkGateway(config, authenticatorFactory,
        messageService, streamService, userService, connectionService, groupService, sessionService);
  }

  // Tests for streams() method

  @Test
  void streams_shouldReturnNonNullStreamService() {
    // When: Calling streams()
    StreamService result = gateway.streams();

    // Then: The result should not be null
    assertThat(result).isNotNull();
  }

  @Test
  void streams_shouldReturnStreamService() {
    // When: Calling streams()
    StreamService result = gateway.streams();

    // Then: The result should be an instance of StreamService
    assertThat(result).isInstanceOf(StreamService.class);
  }

  @Test
  void streams_shouldReturnSameInstanceAsConstructorParameter() {
    // When: Calling streams()
    StreamService result = gateway.streams();

    // Then: The result should be the same instance that was passed to the constructor
    assertThat(result).isSameAs(streamService);
  }

  @Test
  void streams_shouldReturnConsistentResult() {
    // When: Calling streams() multiple times
    StreamService result1 = gateway.streams();
    StreamService result2 = gateway.streams();
    StreamService result3 = gateway.streams();

    // Then: All results should be the same instance
    assertThat(result1).isSameAs(result2);
    assertThat(result2).isSameAs(result3);
    assertThat(result1).isSameAs(result3);
  }

  @Test
  void streams_shouldReturnStreamServiceWithCorrectIdentity() {
    // When: Calling streams()
    StreamService result = gateway.streams();

    // Then: The result should have the same identity hash code as the original
    assertThat(System.identityHashCode(result))
        .isEqualTo(System.identityHashCode(streamService));
  }

  @Test
  void streams_shouldNotReturnNull() {
    // When: Calling streams()
    StreamService result = gateway.streams();

    // Then: The result should not be equal to null
    assertThat(result).isNotEqualTo(null);
  }

  @Test
  void streams_shouldReturnSameReferenceOnSubsequentCalls() {
    // When: Calling streams() twice and comparing references
    StreamService firstCall = gateway.streams();
    StreamService secondCall = gateway.streams();

    // Then: Both calls should return the exact same reference
    assertThat(firstCall == secondCall).isTrue();
  }

  @Test
  void streams_shouldMaintainReferenceAcrossMultipleCalls() {
    // When: Calling streams() multiple times in succession
    for (int i = 0; i < 10; i++) {
      StreamService result = gateway.streams();

      // Then: Each call should return the same instance
      assertThat(result).isSameAs(streamService);
    }
  }

  @Test
  void streams_shouldReturnDifferentInstancesForDifferentGateways() {
    // Given: A second SpringBdkGateway with a different StreamService
    StreamService streamService2 = Mockito.mock(StreamService.class);
    SpringBdkGateway gateway2 = new SpringBdkGateway(
        new BdkConfig(),
        Mockito.mock(AuthenticatorFactory.class),
        Mockito.mock(MessageService.class),
        streamService2,
        Mockito.mock(UserService.class),
        Mockito.mock(ConnectionService.class),
        Mockito.mock(SymphonyGroupService.class),
        Mockito.mock(SessionService.class)
    );

    // When: Calling streams() on both gateways
    StreamService result1 = gateway.streams();
    StreamService result2 = gateway2.streams();

    // Then: The results should be different instances
    assertThat(result1).isNotSameAs(result2);
  }

  @Test
  void streams_shouldReturnServiceThatEqualsOriginal() {
    // When: Calling streams()
    StreamService result = gateway.streams();

    // Then: The result should be equal to the original service
    assertThat(result).isEqualTo(streamService);
  }

  @Test
  void streams_shouldReturnServiceWithSameToString() {
    // When: Calling streams() and converting to string
    String resultString = gateway.streams().toString();
    String originalString = streamService.toString();

    // Then: The toString values should be identical
    assertThat(resultString).isEqualTo(originalString);
  }

  @Test
  void streams_shouldReturnServiceWithSameHashCode() {
    // When: Calling streams() and getting hash code
    int resultHashCode = gateway.streams().hashCode();
    int originalHashCode = streamService.hashCode();

    // Then: The hash codes should be identical
    assertThat(resultHashCode).isEqualTo(originalHashCode);
  }

  @Test
  void streams_shouldNotModifyInternalState() {
    // Given: Initial call to streams()
    StreamService firstResult = gateway.streams();

    // When: Calling streams() again after the first call
    StreamService secondResult = gateway.streams();

    // Then: The method should not have modified any internal state
    assertThat(firstResult).isSameAs(secondResult);
    assertThat(gateway.streams()).isSameAs(streamService);
  }

  @Test
  void streams_shouldReturnExpectedTypeImplementation() {
    // When: Calling streams()
    Object result = gateway.streams();

    // Then: The result should be assignable to StreamService interface
    assertThat(StreamService.class.isAssignableFrom(result.getClass())).isTrue();
  }

  @Test
  void streams_shouldNotCreateNewInstance() {
    // Given: The original streamService mock
    StreamService original = streamService;

    // When: Calling streams()
    StreamService result = gateway.streams();

    // Then: No new instance should have been created
    assertThat(result).isSameAs(original);
    assertThat(result == original).isTrue();
  }
}
