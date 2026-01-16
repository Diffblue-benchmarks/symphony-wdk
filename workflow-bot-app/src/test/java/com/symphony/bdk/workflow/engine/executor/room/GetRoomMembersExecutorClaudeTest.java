package com.symphony.bdk.workflow.engine.executor.room;

import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.gen.api.model.MemberInfo;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.room.GetRoomMembers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

class GetRoomMembersExecutorClaudeTest {

  private GetRoomMembersExecutor executor;
  private ActivityExecutorContext<GetRoomMembers> context;
  private GetRoomMembers activity;
  private BdkGateway bdkGateway;
  private StreamService streamService;

  @BeforeEach
  void setUp() {
    executor = new GetRoomMembersExecutor();
    context = mock(ActivityExecutorContext.class);
    activity = new GetRoomMembers();
    bdkGateway = mock(BdkGateway.class);
    streamService = mock(StreamService.class);

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.streams()).thenReturn(streamService);
  }

  // Tests for constructor

  @Test
  void constructor_shouldCreateInstanceSuccessfully() {
    // When: Creating a new instance
    GetRoomMembersExecutor newExecutor = new GetRoomMembersExecutor();

    // Then: Instance should be created successfully
    assertThat(newExecutor).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance
    GetRoomMembersExecutor newExecutor = new GetRoomMembersExecutor();

    // Then: Instance should be of correct type
    assertThat(newExecutor).isInstanceOf(GetRoomMembersExecutor.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating multiple instances
    GetRoomMembersExecutor executor1 = new GetRoomMembersExecutor();
    GetRoomMembersExecutor executor2 = new GetRoomMembersExecutor();

    // Then: Each instance should be distinct
    assertThat(executor1).isNotSameAs(executor2);
  }

  @Test
  void constructor_shouldNotThrowException() {
    // When/Then: Constructor should not throw exception
    assertThatCode(() -> new GetRoomMembersExecutor())
        .doesNotThrowAnyException();
  }

  // Tests for execute method - basic scenarios

  @Test
  void execute_withValidStreamId_shouldGetMembersAndSetOutput() {
    // Given: Activity with stream ID
    String streamId = "streamId123";
    activity.setStreamId(streamId);
    List<MemberInfo> members = Arrays.asList(new MemberInfo(), new MemberInfo());
    when(streamService.listRoomMembers(streamId)).thenReturn(members);

    // When: Execute is called
    executor.execute(context);

    // Then: Should get room members and set output
    verify(streamService).listRoomMembers(streamId);
    verify(context).setOutputVariable("members", members);
  }

  @Test
  void execute_withDifferentStreamIds_shouldUseCorrectStreamId() {
    // Given: Activity with specific stream IDs
    String streamId1 = "stream123";
    String streamId2 = "stream456";
    List<MemberInfo> members1 = Arrays.asList(new MemberInfo());
    List<MemberInfo> members2 = Arrays.asList(new MemberInfo(), new MemberInfo());

    // First call with stream ID 1
    activity.setStreamId(streamId1);
    when(streamService.listRoomMembers(streamId1)).thenReturn(members1);
    executor.execute(context);

    // Second call with stream ID 2
    activity.setStreamId(streamId2);
    when(streamService.listRoomMembers(streamId2)).thenReturn(members2);
    executor.execute(context);

    // Then: Should use correct stream IDs
    verify(streamService).listRoomMembers(streamId1);
    verify(streamService).listRoomMembers(streamId2);
    verify(context).setOutputVariable("members", members1);
    verify(context).setOutputVariable("members", members2);
  }

  @Test
  void execute_withEmptyMemberList_shouldSetOutputWithEmptyList() {
    // Given: Activity where listRoomMembers returns empty list
    String streamId = "streamId123";
    activity.setStreamId(streamId);
    List<MemberInfo> emptyMembers = Collections.emptyList();
    when(streamService.listRoomMembers(streamId)).thenReturn(emptyMembers);

    // When: Execute is called
    executor.execute(context);

    // Then: Should set output variable with empty list
    verify(streamService).listRoomMembers(streamId);
    verify(context).setOutputVariable("members", emptyMembers);
  }

  @Test
  void execute_withNullMemberList_shouldSetOutputWithNull() {
    // Given: Activity where listRoomMembers returns null
    String streamId = "streamId123";
    activity.setStreamId(streamId);
    when(streamService.listRoomMembers(streamId)).thenReturn(null);

    // When: Execute is called
    executor.execute(context);

    // Then: Should set output variable with null
    verify(streamService).listRoomMembers(streamId);
    verify(context).setOutputVariable("members", null);
  }

  @Test
  void execute_withSingleMember_shouldSetOutputWithSingleMember() {
    // Given: Activity where listRoomMembers returns single member
    String streamId = "streamId123";
    activity.setStreamId(streamId);
    MemberInfo singleMember = new MemberInfo();
    List<MemberInfo> members = Collections.singletonList(singleMember);
    when(streamService.listRoomMembers(streamId)).thenReturn(members);

    // When: Execute is called
    executor.execute(context);

    // Then: Should set output variable with single member list
    verify(streamService).listRoomMembers(streamId);
    verify(context).setOutputVariable("members", members);
  }

  @Test
  void execute_withMultipleMembers_shouldSetOutputWithAllMembers() {
    // Given: Activity where listRoomMembers returns multiple members
    String streamId = "streamId123";
    activity.setStreamId(streamId);
    List<MemberInfo> members = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      members.add(new MemberInfo());
    }
    when(streamService.listRoomMembers(streamId)).thenReturn(members);

    // When: Execute is called
    executor.execute(context);

    // Then: Should set output variable with all members
    verify(streamService).listRoomMembers(streamId);
    verify(context).setOutputVariable("members", members);
  }

  // Tests for execute method - edge cases

  @Test
  void execute_shouldNotThrowException() {
    // Given: Valid activity setup
    String streamId = "streamId123";
    activity.setStreamId(streamId);
    List<MemberInfo> members = Arrays.asList(new MemberInfo());
    when(streamService.listRoomMembers(streamId)).thenReturn(members);

    // When/Then: Execute should not throw exception
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();
  }

  @Test
  void execute_calledMultipleTimes_shouldWorkCorrectly() {
    // Given: Valid activity setup
    String streamId = "streamId123";
    activity.setStreamId(streamId);
    List<MemberInfo> members = Arrays.asList(new MemberInfo());
    when(streamService.listRoomMembers(streamId)).thenReturn(members);

    // When: Execute is called multiple times
    executor.execute(context);
    executor.execute(context);
    executor.execute(context);

    // Then: Should work correctly all times
    verify(streamService, times(3)).listRoomMembers(streamId);
    verify(context, times(3)).setOutputVariable("members", members);
  }

  @Test
  void execute_withSpecialCharactersInStreamId_shouldPassThrough() {
    // Given: Activity with stream ID containing special characters
    String streamId = "stream-id_123!@#$%^&*()";
    activity.setStreamId(streamId);
    List<MemberInfo> members = Arrays.asList(new MemberInfo());
    when(streamService.listRoomMembers(streamId)).thenReturn(members);

    // When: Execute is called
    executor.execute(context);

    // Then: Should pass stream ID through correctly
    verify(streamService).listRoomMembers(streamId);
    verify(context).setOutputVariable("members", members);
  }

  @Test
  void execute_withEmptyStreamId_shouldPassThrough() {
    // Given: Activity with empty stream ID
    String streamId = "";
    activity.setStreamId(streamId);
    List<MemberInfo> members = Arrays.asList(new MemberInfo());
    when(streamService.listRoomMembers(streamId)).thenReturn(members);

    // When: Execute is called
    executor.execute(context);

    // Then: Should pass empty stream ID through
    verify(streamService).listRoomMembers(streamId);
    verify(context).setOutputVariable("members", members);
  }

  @Test
  void execute_withNullStreamId_shouldPassThrough() {
    // Given: Activity with null stream ID
    activity.setStreamId(null);
    List<MemberInfo> members = Arrays.asList(new MemberInfo());
    when(streamService.listRoomMembers(null)).thenReturn(members);

    // When: Execute is called
    executor.execute(context);

    // Then: Should pass null stream ID through
    verify(streamService).listRoomMembers(null);
    verify(context).setOutputVariable("members", members);
  }

  // Tests for output variable key consistency

  @Test
  void execute_shouldUseCorrectOutputVariableKey() {
    // Given: Valid activity setup
    String streamId = "streamId123";
    activity.setStreamId(streamId);
    List<MemberInfo> members = Arrays.asList(new MemberInfo());
    when(streamService.listRoomMembers(streamId)).thenReturn(members);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use "members" as the output variable key
    verify(context).setOutputVariable("members", members);
  }

  // Tests for interaction patterns

  @Test
  void execute_shouldCallMethodsInCorrectOrder() {
    // Given: Valid activity setup
    String streamId = "streamId123";
    activity.setStreamId(streamId);
    List<MemberInfo> members = Arrays.asList(new MemberInfo());
    when(streamService.listRoomMembers(streamId)).thenReturn(members);

    // When: Execute is called
    executor.execute(context);

    // Then: Should call methods in correct order
    // First get the activity
    verify(context).getActivity();
    // Then get the BDK gateway
    verify(context).bdk();
    // Then get the stream service
    verify(bdkGateway).streams();
    // Then list room members
    verify(streamService).listRoomMembers(streamId);
    // Finally set output
    verify(context).setOutputVariable("members", members);
  }

  @Test
  void execute_shouldOnlyCallStreamServiceOnce() {
    // Given: Valid activity setup
    String streamId = "streamId123";
    activity.setStreamId(streamId);
    List<MemberInfo> members = Arrays.asList(new MemberInfo());
    when(streamService.listRoomMembers(streamId)).thenReturn(members);

    // When: Execute is called once
    executor.execute(context);

    // Then: Should call listRoomMembers exactly once
    verify(streamService, times(1)).listRoomMembers(streamId);
  }

  // Tests for verifying statelessness

  @Test
  void execute_withDifferentContexts_shouldWorkIndependently() {
    // Given: Two different contexts with different activities
    ActivityExecutorContext<GetRoomMembers> context1 = mock(ActivityExecutorContext.class);
    ActivityExecutorContext<GetRoomMembers> context2 = mock(ActivityExecutorContext.class);
    GetRoomMembers activity1 = new GetRoomMembers();
    GetRoomMembers activity2 = new GetRoomMembers();
    BdkGateway bdkGateway1 = mock(BdkGateway.class);
    BdkGateway bdkGateway2 = mock(BdkGateway.class);
    StreamService streamService1 = mock(StreamService.class);
    StreamService streamService2 = mock(StreamService.class);

    activity1.setStreamId("stream1");
    activity2.setStreamId("stream2");
    when(context1.getActivity()).thenReturn(activity1);
    when(context2.getActivity()).thenReturn(activity2);
    when(context1.bdk()).thenReturn(bdkGateway1);
    when(context2.bdk()).thenReturn(bdkGateway2);
    when(bdkGateway1.streams()).thenReturn(streamService1);
    when(bdkGateway2.streams()).thenReturn(streamService2);

    List<MemberInfo> members1 = Arrays.asList(new MemberInfo());
    List<MemberInfo> members2 = Arrays.asList(new MemberInfo(), new MemberInfo());
    when(streamService1.listRoomMembers("stream1")).thenReturn(members1);
    when(streamService2.listRoomMembers("stream2")).thenReturn(members2);

    // When: Execute is called with different contexts
    executor.execute(context1);
    executor.execute(context2);

    // Then: Should handle each context independently
    verify(streamService1).listRoomMembers("stream1");
    verify(streamService2).listRoomMembers("stream2");
    verify(context1).setOutputVariable("members", members1);
    verify(context2).setOutputVariable("members", members2);
  }
}
