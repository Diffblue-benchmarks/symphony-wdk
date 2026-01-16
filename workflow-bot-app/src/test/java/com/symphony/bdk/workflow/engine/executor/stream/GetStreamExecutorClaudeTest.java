package com.symphony.bdk.workflow.engine.executor.stream;

import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.gen.api.model.V2StreamAttributes;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.stream.GetStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;

class GetStreamExecutorClaudeTest {

  private GetStreamExecutor executor;
  private ActivityExecutorContext<GetStream> context;
  private GetStream activity;
  private BdkGateway bdkGateway;
  private StreamService streamService;
  private OboServices oboServices;
  private AuthSession authSession;
  private StreamService oboStreamService;
  private V2StreamAttributes streamAttributes;

  @BeforeEach
  void setUp() {
    executor = new GetStreamExecutor();
    context = mock(ActivityExecutorContext.class);
    activity = new GetStream();
    bdkGateway = mock(BdkGateway.class);
    streamService = mock(StreamService.class);
    oboServices = mock(OboServices.class);
    authSession = mock(AuthSession.class);
    oboStreamService = mock(StreamService.class);
    streamAttributes = new V2StreamAttributes();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.streams()).thenReturn(streamService);
  }

  // Tests for constructor

  @Test
  void constructor_shouldCreateInstanceSuccessfully() {
    // When: Creating a new instance
    GetStreamExecutor newExecutor = new GetStreamExecutor();

    // Then: Instance should be created successfully
    assertThat(newExecutor).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance
    GetStreamExecutor newExecutor = new GetStreamExecutor();

    // Then: Instance should be of correct type
    assertThat(newExecutor).isInstanceOf(GetStreamExecutor.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating multiple instances
    GetStreamExecutor executor1 = new GetStreamExecutor();
    GetStreamExecutor executor2 = new GetStreamExecutor();

    // Then: Each instance should be distinct
    assertThat(executor1).isNotSameAs(executor2);
  }

  @Test
  void constructor_shouldNotThrowException() {
    // When/Then: Constructor should not throw exception
    assertThatCode(() -> new GetStreamExecutor())
        .doesNotThrowAnyException();
  }

  // Tests for execute method - non-OBO scenarios

  @Test
  void execute_withNoObo_shouldGetStreamAndSetOutput() {
    // Given: Activity with stream ID and no OBO
    String streamId = "streamId123";
    activity.setStreamId(streamId);
    activity.setObo(null);
    when(streamService.getStream(streamId)).thenReturn(streamAttributes);

    // When: Execute is called
    executor.execute(context);

    // Then: Should get stream using regular stream service and set output
    verify(streamService).getStream(streamId);
    verify(context).setOutputVariable("stream", streamAttributes);
    verify(bdkGateway, never()).obo((String) null);
    verify(bdkGateway, never()).obo((Long) null);
  }

  @Test
  void execute_withEmptyOboObject_shouldUseNonOboPath() {
    // Given: Activity with empty OBO object (both username and userId are null)
    String streamId = "streamId123";
    Obo emptyObo = new Obo();
    emptyObo.setUsername(null);
    emptyObo.setUserId(null);
    activity.setStreamId(streamId);
    activity.setObo(emptyObo);
    when(streamService.getStream(streamId)).thenReturn(streamAttributes);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use non-OBO path since OBO is not valid
    verify(streamService).getStream(streamId);
    verify(context).setOutputVariable("stream", streamAttributes);
    verify(bdkGateway, never()).obo((String) null);
  }

  @Test
  void execute_withDifferentStreamIds_shouldUseCorrectStreamId() {
    // Given: Activity with specific stream ID
    String streamId1 = "stream123";
    String streamId2 = "stream456";

    // First call with stream ID 1
    activity.setStreamId(streamId1);
    activity.setObo(null);
    when(streamService.getStream(streamId1)).thenReturn(streamAttributes);
    executor.execute(context);

    // Second call with stream ID 2
    activity.setStreamId(streamId2);
    when(streamService.getStream(streamId2)).thenReturn(streamAttributes);
    executor.execute(context);

    // Then: Should use correct stream IDs
    verify(streamService).getStream(streamId1);
    verify(streamService).getStream(streamId2);
  }

  // Tests for execute method - OBO scenarios with username

  @Test
  void execute_withOboUsername_shouldUseOboServices() {
    // Given: Activity with OBO username
    String streamId = "streamId123";
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setStreamId(streamId);
    activity.setObo(obo);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.getStream(streamId)).thenReturn(streamAttributes);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use OBO services
    verify(bdkGateway).obo(oboUsername);
    verify(bdkGateway).obo(authSession);
    verify(oboStreamService).getStream(streamId);
    verify(context).setOutputVariable("stream", streamAttributes);
    verify(streamService, never()).getStream(org.mockito.ArgumentMatchers.anyString());
  }

  // Tests for execute method - OBO scenarios with userId

  @Test
  void execute_withOboUserId_shouldUseOboServices() {
    // Given: Activity with OBO user ID
    String streamId = "streamId123";
    Long oboUserId = 987654321L;
    Obo obo = new Obo();
    obo.setUsername(null);
    obo.setUserId(oboUserId);
    activity.setStreamId(streamId);
    activity.setObo(obo);

    when(bdkGateway.obo(oboUserId)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.getStream(streamId)).thenReturn(streamAttributes);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use OBO services
    verify(bdkGateway).obo(oboUserId);
    verify(bdkGateway).obo(authSession);
    verify(oboStreamService).getStream(streamId);
    verify(context).setOutputVariable("stream", streamAttributes);
    verify(streamService, never()).getStream(org.mockito.ArgumentMatchers.anyString());
  }

  // Tests for execute method - OBO preference (username over userId)

  @Test
  void execute_withBothOboUsernameAndUserId_shouldPreferUsername() {
    // Given: Activity with both OBO username and user ID
    String streamId = "streamId123";
    String oboUsername = "obo.user";
    Long oboUserId = 987654321L;
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    obo.setUserId(oboUserId);
    activity.setStreamId(streamId);
    activity.setObo(obo);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.getStream(streamId)).thenReturn(streamAttributes);

    // When: Execute is called
    executor.execute(context);

    // Then: Should prefer username over user ID
    verify(bdkGateway).obo(oboUsername);
    verify(bdkGateway, never()).obo(oboUserId);
    verify(oboStreamService).getStream(streamId);
    verify(context).setOutputVariable("stream", streamAttributes);
  }

  // Tests for execute method - edge cases

  @Test
  void execute_shouldNotThrowException() {
    // Given: Valid activity setup
    String streamId = "streamId123";
    activity.setStreamId(streamId);
    activity.setObo(null);
    when(streamService.getStream(streamId)).thenReturn(streamAttributes);

    // When/Then: Execute should not throw exception
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();
  }

  @Test
  void execute_calledMultipleTimes_shouldWorkCorrectly() {
    // Given: Valid activity setup
    String streamId = "streamId123";
    activity.setStreamId(streamId);
    activity.setObo(null);
    when(streamService.getStream(streamId)).thenReturn(streamAttributes);

    // When: Execute is called multiple times
    executor.execute(context);
    executor.execute(context);

    // Then: Should work correctly both times
    verify(streamService, times(2)).getStream(streamId);
    verify(context, times(2)).setOutputVariable("stream", streamAttributes);
  }

  @Test
  void execute_withNullStreamAttributes_shouldStillSetOutputVariable() {
    // Given: Activity where getStream returns null
    String streamId = "streamId123";
    activity.setStreamId(streamId);
    activity.setObo(null);
    when(streamService.getStream(streamId)).thenReturn(null);

    // When: Execute is called
    executor.execute(context);

    // Then: Should still set output variable with null value
    verify(streamService).getStream(streamId);
    verify(context).setOutputVariable("stream", null);
  }

  // Tests for doOboWithCache method

  @Test
  void doOboWithCache_withOboUsername_shouldReturnStreamAttributes() {
    // Given: Activity with OBO username
    String streamId = "streamId123";
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setStreamId(streamId);
    activity.setObo(obo);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.getStream(streamId)).thenReturn(streamAttributes);

    // When: doOboWithCache is called
    V2StreamAttributes result = executor.doOboWithCache(context);

    // Then: Should get stream via OBO and return the result
    assertThat(result).isSameAs(streamAttributes);
    verify(bdkGateway).obo(oboUsername);
    verify(oboStreamService).getStream(streamId);
  }

  @Test
  void doOboWithCache_withOboUserId_shouldReturnStreamAttributes() {
    // Given: Activity with OBO user ID
    String streamId = "streamId123";
    Long oboUserId = 987654321L;
    Obo obo = new Obo();
    obo.setUsername(null);
    obo.setUserId(oboUserId);
    activity.setStreamId(streamId);
    activity.setObo(obo);

    when(bdkGateway.obo(oboUserId)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.getStream(streamId)).thenReturn(streamAttributes);

    // When: doOboWithCache is called
    V2StreamAttributes result = executor.doOboWithCache(context);

    // Then: Should get stream via OBO and return the result
    assertThat(result).isSameAs(streamAttributes);
    verify(bdkGateway).obo(oboUserId);
    verify(oboStreamService).getStream(streamId);
  }

  @Test
  void doOboWithCache_withDifferentStreamIds_shouldUseCorrectStreamId() {
    // Given: Activity with OBO
    String streamId1 = "stream123";
    String streamId2 = "stream456";
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setObo(obo);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    V2StreamAttributes streamAttributes1 = new V2StreamAttributes();
    V2StreamAttributes streamAttributes2 = new V2StreamAttributes();
    when(oboStreamService.getStream(streamId1)).thenReturn(streamAttributes1);
    when(oboStreamService.getStream(streamId2)).thenReturn(streamAttributes2);

    // First call with stream ID 1
    activity.setStreamId(streamId1);
    V2StreamAttributes result1 = executor.doOboWithCache(context);

    // Second call with stream ID 2
    activity.setStreamId(streamId2);
    V2StreamAttributes result2 = executor.doOboWithCache(context);

    // Then: Should use correct stream IDs and return correct stream attributes
    assertThat(result1).isSameAs(streamAttributes1);
    assertThat(result2).isSameAs(streamAttributes2);
    verify(oboStreamService).getStream(streamId1);
    verify(oboStreamService).getStream(streamId2);
  }

  @Test
  void doOboWithCache_shouldNotThrowException() {
    // Given: Valid activity with OBO
    String streamId = "streamId123";
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setStreamId(streamId);
    activity.setObo(obo);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.getStream(streamId)).thenReturn(streamAttributes);

    // When/Then: doOboWithCache should not throw exception
    assertThatCode(() -> executor.doOboWithCache(context))
        .doesNotThrowAnyException();
  }

  @Test
  void doOboWithCache_calledMultipleTimes_shouldWorkCorrectly() {
    // Given: Valid activity with OBO
    String streamId = "streamId123";
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setStreamId(streamId);
    activity.setObo(obo);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.getStream(streamId)).thenReturn(streamAttributes);

    // When: doOboWithCache is called multiple times
    V2StreamAttributes result1 = executor.doOboWithCache(context);
    V2StreamAttributes result2 = executor.doOboWithCache(context);

    // Then: Should work correctly both times and return same result
    assertThat(result1).isSameAs(streamAttributes);
    assertThat(result2).isSameAs(streamAttributes);
    verify(oboStreamService, times(2)).getStream(streamId);
  }

  @Test
  void doOboWithCache_withNullStreamAttributes_shouldReturnNull() {
    // Given: Activity with OBO where getStream returns null
    String streamId = "streamId123";
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setStreamId(streamId);
    activity.setObo(obo);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.getStream(streamId)).thenReturn(null);

    // When: doOboWithCache is called
    V2StreamAttributes result = executor.doOboWithCache(context);

    // Then: Should return null
    assertThat(result).isNull();
    verify(oboStreamService).getStream(streamId);
  }

  @Test
  void doOboWithCache_withBothUsernameAndUserId_shouldPreferUsername() {
    // Given: Activity with both OBO username and user ID
    String streamId = "streamId123";
    String oboUsername = "obo.user";
    Long oboUserId = 987654321L;
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    obo.setUserId(oboUserId);
    activity.setStreamId(streamId);
    activity.setObo(obo);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.getStream(streamId)).thenReturn(streamAttributes);

    // When: doOboWithCache is called
    V2StreamAttributes result = executor.doOboWithCache(context);

    // Then: Should prefer username over user ID
    assertThat(result).isSameAs(streamAttributes);
    verify(bdkGateway).obo(oboUsername);
    verify(bdkGateway, never()).obo(oboUserId);
    verify(oboStreamService).getStream(streamId);
  }
}
