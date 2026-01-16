package com.symphony.bdk.workflow.event;

import com.symphony.bdk.workflow.swadl.v1.Event;
import com.symphony.bdk.workflow.swadl.v1.event.ConnectionAcceptedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.ConnectionRequestedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.FormRepliedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.ImCreatedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.MessageReceivedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.MessageSuppressedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.PostSharedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.RequestReceivedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.RoomCreatedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.RoomDeactivatedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.RoomMemberDemotedFromOwnerEvent;
import com.symphony.bdk.workflow.swadl.v1.event.RoomMemberPromotedToOwnerEvent;
import com.symphony.bdk.workflow.swadl.v1.event.RoomReactivatedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.RoomUpdatedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.TimerFiredEvent;
import com.symphony.bdk.workflow.swadl.v1.event.UserJoinedRoomEvent;
import com.symphony.bdk.workflow.swadl.v1.event.UserLeftRoomEvent;
import com.symphony.bdk.workflow.swadl.v1.event.UserRequestedToJoinRoomEvent;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WorkflowEventTypeClaudeTest {

  // Tests for values() method

  @Test
  void values_shouldReturnAllEnumConstants() {
    // When: Calling values() method
    WorkflowEventType[] values = WorkflowEventType.values();

    // Then: All 19 enum constants should be present
    assertThat(values).hasSize(19);
    assertThat(values).containsExactly(
        WorkflowEventType.MESSAGE_RECEIVED,
        WorkflowEventType.MESSAGE_SUPPRESSED,
        WorkflowEventType.IM_CREATED,
        WorkflowEventType.POST_SHARED,
        WorkflowEventType.ROOM_CREATED,
        WorkflowEventType.ROOM_UPDATED,
        WorkflowEventType.ROOM_DEACTIVATED,
        WorkflowEventType.ROOM_REACTIVATED,
        WorkflowEventType.ROOM_MEMBER_PROMOTED_TO_OWNER,
        WorkflowEventType.ROOM_MEMBER_DEMOTED_FROM_OWNER,
        WorkflowEventType.USER_REQUESTED_JOIN_ROOM,
        WorkflowEventType.USER_JOINED_ROOM,
        WorkflowEventType.USER_LEFT_ROOM,
        WorkflowEventType.CONNECTION_REQUESTED,
        WorkflowEventType.CONNECTION_ACCEPTED,
        WorkflowEventType.FORM_REPLIED,
        WorkflowEventType.REQUEST_RECEIVED,
        WorkflowEventType.TIME_FIRED,
        WorkflowEventType.ONE_OF
    );
  }

  @Test
  void values_shouldReturnNonNullArray() {
    // When: Calling values() method
    WorkflowEventType[] values = WorkflowEventType.values();

    // Then: The returned array should not be null
    assertThat(values).isNotNull();
  }

  @Test
  void values_shouldReturnNewArrayInstance() {
    // When: Calling values() method twice
    WorkflowEventType[] values1 = WorkflowEventType.values();
    WorkflowEventType[] values2 = WorkflowEventType.values();

    // Then: Different array instances should be returned (defensive copy)
    assertThat(values1).isNotSameAs(values2);
    assertThat(values1).containsExactly(values2);
  }

  @Test
  void values_shouldReturnArrayInDeclarationOrder() {
    // When: Calling values() method
    WorkflowEventType[] values = WorkflowEventType.values();

    // Then: The enum constants should be in the order they are declared
    assertThat(values[0]).isEqualTo(WorkflowEventType.MESSAGE_RECEIVED);
    assertThat(values[1]).isEqualTo(WorkflowEventType.MESSAGE_SUPPRESSED);
    assertThat(values[18]).isEqualTo(WorkflowEventType.ONE_OF);
  }

  @Test
  void values_modifyingReturnedArray_shouldNotAffectSubsequentCalls() {
    // Given: First call to values()
    WorkflowEventType[] values1 = WorkflowEventType.values();

    // When: Modifying the returned array
    values1[0] = WorkflowEventType.ONE_OF;

    // And: Calling values() again
    WorkflowEventType[] values2 = WorkflowEventType.values();

    // Then: The second call should return unmodified array
    assertThat(values2[0]).isEqualTo(WorkflowEventType.MESSAGE_RECEIVED);
  }

  // Tests for valueOf() method

  @Test
  void valueOf_withMessageReceived_shouldReturnMessageReceivedEnum() {
    // When: Calling valueOf with "MESSAGE_RECEIVED"
    WorkflowEventType result = WorkflowEventType.valueOf("MESSAGE_RECEIVED");

    // Then: Should return the MESSAGE_RECEIVED enum constant
    assertThat(result).isEqualTo(WorkflowEventType.MESSAGE_RECEIVED);
  }

  @Test
  void valueOf_withMessageSuppressed_shouldReturnMessageSuppressedEnum() {
    // When: Calling valueOf with "MESSAGE_SUPPRESSED"
    WorkflowEventType result = WorkflowEventType.valueOf("MESSAGE_SUPPRESSED");

    // Then: Should return the MESSAGE_SUPPRESSED enum constant
    assertThat(result).isEqualTo(WorkflowEventType.MESSAGE_SUPPRESSED);
  }

  @Test
  void valueOf_withImCreated_shouldReturnImCreatedEnum() {
    // When: Calling valueOf with "IM_CREATED"
    WorkflowEventType result = WorkflowEventType.valueOf("IM_CREATED");

    // Then: Should return the IM_CREATED enum constant
    assertThat(result).isEqualTo(WorkflowEventType.IM_CREATED);
  }

  @Test
  void valueOf_withPostShared_shouldReturnPostSharedEnum() {
    // When: Calling valueOf with "POST_SHARED"
    WorkflowEventType result = WorkflowEventType.valueOf("POST_SHARED");

    // Then: Should return the POST_SHARED enum constant
    assertThat(result).isEqualTo(WorkflowEventType.POST_SHARED);
  }

  @Test
  void valueOf_withRoomCreated_shouldReturnRoomCreatedEnum() {
    // When: Calling valueOf with "ROOM_CREATED"
    WorkflowEventType result = WorkflowEventType.valueOf("ROOM_CREATED");

    // Then: Should return the ROOM_CREATED enum constant
    assertThat(result).isEqualTo(WorkflowEventType.ROOM_CREATED);
  }

  @Test
  void valueOf_withRoomUpdated_shouldReturnRoomUpdatedEnum() {
    // When: Calling valueOf with "ROOM_UPDATED"
    WorkflowEventType result = WorkflowEventType.valueOf("ROOM_UPDATED");

    // Then: Should return the ROOM_UPDATED enum constant
    assertThat(result).isEqualTo(WorkflowEventType.ROOM_UPDATED);
  }

  @Test
  void valueOf_withRoomDeactivated_shouldReturnRoomDeactivatedEnum() {
    // When: Calling valueOf with "ROOM_DEACTIVATED"
    WorkflowEventType result = WorkflowEventType.valueOf("ROOM_DEACTIVATED");

    // Then: Should return the ROOM_DEACTIVATED enum constant
    assertThat(result).isEqualTo(WorkflowEventType.ROOM_DEACTIVATED);
  }

  @Test
  void valueOf_withRoomReactivated_shouldReturnRoomReactivatedEnum() {
    // When: Calling valueOf with "ROOM_REACTIVATED"
    WorkflowEventType result = WorkflowEventType.valueOf("ROOM_REACTIVATED");

    // Then: Should return the ROOM_REACTIVATED enum constant
    assertThat(result).isEqualTo(WorkflowEventType.ROOM_REACTIVATED);
  }

  @Test
  void valueOf_withRoomMemberPromotedToOwner_shouldReturnRoomMemberPromotedToOwnerEnum() {
    // When: Calling valueOf with "ROOM_MEMBER_PROMOTED_TO_OWNER"
    WorkflowEventType result = WorkflowEventType.valueOf("ROOM_MEMBER_PROMOTED_TO_OWNER");

    // Then: Should return the ROOM_MEMBER_PROMOTED_TO_OWNER enum constant
    assertThat(result).isEqualTo(WorkflowEventType.ROOM_MEMBER_PROMOTED_TO_OWNER);
  }

  @Test
  void valueOf_withRoomMemberDemotedFromOwner_shouldReturnRoomMemberDemotedFromOwnerEnum() {
    // When: Calling valueOf with "ROOM_MEMBER_DEMOTED_FROM_OWNER"
    WorkflowEventType result = WorkflowEventType.valueOf("ROOM_MEMBER_DEMOTED_FROM_OWNER");

    // Then: Should return the ROOM_MEMBER_DEMOTED_FROM_OWNER enum constant
    assertThat(result).isEqualTo(WorkflowEventType.ROOM_MEMBER_DEMOTED_FROM_OWNER);
  }

  @Test
  void valueOf_withUserRequestedJoinRoom_shouldReturnUserRequestedJoinRoomEnum() {
    // When: Calling valueOf with "USER_REQUESTED_JOIN_ROOM"
    WorkflowEventType result = WorkflowEventType.valueOf("USER_REQUESTED_JOIN_ROOM");

    // Then: Should return the USER_REQUESTED_JOIN_ROOM enum constant
    assertThat(result).isEqualTo(WorkflowEventType.USER_REQUESTED_JOIN_ROOM);
  }

  @Test
  void valueOf_withUserJoinedRoom_shouldReturnUserJoinedRoomEnum() {
    // When: Calling valueOf with "USER_JOINED_ROOM"
    WorkflowEventType result = WorkflowEventType.valueOf("USER_JOINED_ROOM");

    // Then: Should return the USER_JOINED_ROOM enum constant
    assertThat(result).isEqualTo(WorkflowEventType.USER_JOINED_ROOM);
  }

  @Test
  void valueOf_withUserLeftRoom_shouldReturnUserLeftRoomEnum() {
    // When: Calling valueOf with "USER_LEFT_ROOM"
    WorkflowEventType result = WorkflowEventType.valueOf("USER_LEFT_ROOM");

    // Then: Should return the USER_LEFT_ROOM enum constant
    assertThat(result).isEqualTo(WorkflowEventType.USER_LEFT_ROOM);
  }

  @Test
  void valueOf_withConnectionRequested_shouldReturnConnectionRequestedEnum() {
    // When: Calling valueOf with "CONNECTION_REQUESTED"
    WorkflowEventType result = WorkflowEventType.valueOf("CONNECTION_REQUESTED");

    // Then: Should return the CONNECTION_REQUESTED enum constant
    assertThat(result).isEqualTo(WorkflowEventType.CONNECTION_REQUESTED);
  }

  @Test
  void valueOf_withConnectionAccepted_shouldReturnConnectionAcceptedEnum() {
    // When: Calling valueOf with "CONNECTION_ACCEPTED"
    WorkflowEventType result = WorkflowEventType.valueOf("CONNECTION_ACCEPTED");

    // Then: Should return the CONNECTION_ACCEPTED enum constant
    assertThat(result).isEqualTo(WorkflowEventType.CONNECTION_ACCEPTED);
  }

  @Test
  void valueOf_withFormReplied_shouldReturnFormRepliedEnum() {
    // When: Calling valueOf with "FORM_REPLIED"
    WorkflowEventType result = WorkflowEventType.valueOf("FORM_REPLIED");

    // Then: Should return the FORM_REPLIED enum constant
    assertThat(result).isEqualTo(WorkflowEventType.FORM_REPLIED);
  }

  @Test
  void valueOf_withRequestReceived_shouldReturnRequestReceivedEnum() {
    // When: Calling valueOf with "REQUEST_RECEIVED"
    WorkflowEventType result = WorkflowEventType.valueOf("REQUEST_RECEIVED");

    // Then: Should return the REQUEST_RECEIVED enum constant
    assertThat(result).isEqualTo(WorkflowEventType.REQUEST_RECEIVED);
  }

  @Test
  void valueOf_withTimeFired_shouldReturnTimeFiredEnum() {
    // When: Calling valueOf with "TIME_FIRED"
    WorkflowEventType result = WorkflowEventType.valueOf("TIME_FIRED");

    // Then: Should return the TIME_FIRED enum constant
    assertThat(result).isEqualTo(WorkflowEventType.TIME_FIRED);
  }

  @Test
  void valueOf_withOneOf_shouldReturnOneOfEnum() {
    // When: Calling valueOf with "ONE_OF"
    WorkflowEventType result = WorkflowEventType.valueOf("ONE_OF");

    // Then: Should return the ONE_OF enum constant
    assertThat(result).isEqualTo(WorkflowEventType.ONE_OF);
  }

  @Test
  void valueOf_withLowercase_shouldThrowIllegalArgumentException() {
    // When/Then: Calling valueOf with lowercase should throw IllegalArgumentException
    assertThatThrownBy(() -> WorkflowEventType.valueOf("message_received"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No enum constant")
        .hasMessageContaining("WorkflowEventType.message_received");
  }

  @Test
  void valueOf_withMixedCase_shouldThrowIllegalArgumentException() {
    // When/Then: Calling valueOf with mixed case should throw IllegalArgumentException
    assertThatThrownBy(() -> WorkflowEventType.valueOf("Message_Received"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No enum constant")
        .hasMessageContaining("WorkflowEventType.Message_Received");
  }

  @Test
  void valueOf_withNull_shouldThrowNullPointerException() {
    // When/Then: Calling valueOf with null should throw NullPointerException
    assertThatThrownBy(() -> WorkflowEventType.valueOf(null))
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  void valueOf_withEmptyString_shouldThrowIllegalArgumentException() {
    // When/Then: Calling valueOf with empty string should throw IllegalArgumentException
    assertThatThrownBy(() -> WorkflowEventType.valueOf(""))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No enum constant")
        .hasMessageContaining("WorkflowEventType.");
  }

  @Test
  void valueOf_withInvalidValue_shouldThrowIllegalArgumentException() {
    // When/Then: Calling valueOf with invalid value should throw IllegalArgumentException
    assertThatThrownBy(() -> WorkflowEventType.valueOf("INVALID_EVENT"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No enum constant")
        .hasMessageContaining("WorkflowEventType.INVALID_EVENT");
  }

  @Test
  void valueOf_withWhitespace_shouldThrowIllegalArgumentException() {
    // When/Then: Calling valueOf with whitespace should throw IllegalArgumentException
    assertThatThrownBy(() -> WorkflowEventType.valueOf("   "))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No enum constant");
  }

  @Test
  void valueOf_withLeadingSpace_shouldThrowIllegalArgumentException() {
    // When/Then: Calling valueOf with leading space should throw IllegalArgumentException
    assertThatThrownBy(() -> WorkflowEventType.valueOf(" MESSAGE_RECEIVED"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No enum constant");
  }

  @Test
  void valueOf_withTrailingSpace_shouldThrowIllegalArgumentException() {
    // When/Then: Calling valueOf with trailing space should throw IllegalArgumentException
    assertThatThrownBy(() -> WorkflowEventType.valueOf("MESSAGE_RECEIVED "))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No enum constant");
  }

  @Test
  void valueOf_returnsSameInstance_whenCalledMultipleTimes() {
    // When: Calling valueOf multiple times with the same value
    WorkflowEventType result1 = WorkflowEventType.valueOf("MESSAGE_RECEIVED");
    WorkflowEventType result2 = WorkflowEventType.valueOf("MESSAGE_RECEIVED");

    // Then: Should return the same instance (enums are singletons)
    assertThat(result1).isSameAs(result2);
  }

  @Test
  void valueOf_shouldBeConsistentWithEnumName() {
    // Given: All enum values
    WorkflowEventType[] values = WorkflowEventType.values();

    // When/Then: valueOf should return the same enum when passed the enum's name
    for (WorkflowEventType value : values) {
      assertThat(WorkflowEventType.valueOf(value.name())).isSameAs(value);
    }
  }

  // Tests for getEventType() method

  @Test
  void getEventType_withMessageReceivedEvent_shouldReturnMessageReceived() {
    // Given: An event with MessageReceived set
    Event event = new Event();
    MessageReceivedEvent messageReceivedEvent = new MessageReceivedEvent();
    event.setMessageReceived(messageReceivedEvent);

    // When: Calling getEventType
    Optional<WorkflowEventType> result = WorkflowEventType.getEventType(event);

    // Then: Should return MESSAGE_RECEIVED
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(WorkflowEventType.MESSAGE_RECEIVED);
  }

  @Test
  void getEventType_withMessageSuppressedEvent_shouldReturnMessageSuppressed() {
    // Given: An event with MessageSuppressed set
    Event event = new Event();
    MessageSuppressedEvent messageSuppressedEvent = new MessageSuppressedEvent();
    event.setMessageSuppressed(messageSuppressedEvent);

    // When: Calling getEventType
    Optional<WorkflowEventType> result = WorkflowEventType.getEventType(event);

    // Then: Should return MESSAGE_SUPPRESSED
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(WorkflowEventType.MESSAGE_SUPPRESSED);
  }

  @Test
  void getEventType_withImCreatedEvent_shouldReturnImCreated() {
    // Given: An event with ImCreated set
    Event event = new Event();
    ImCreatedEvent imCreatedEvent = new ImCreatedEvent();
    event.setImCreated(imCreatedEvent);

    // When: Calling getEventType
    Optional<WorkflowEventType> result = WorkflowEventType.getEventType(event);

    // Then: Should return IM_CREATED
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(WorkflowEventType.IM_CREATED);
  }

  @Test
  void getEventType_withPostSharedEvent_shouldReturnPostShared() {
    // Given: An event with PostShared set
    Event event = new Event();
    PostSharedEvent postSharedEvent = new PostSharedEvent();
    event.setPostShared(postSharedEvent);

    // When: Calling getEventType
    Optional<WorkflowEventType> result = WorkflowEventType.getEventType(event);

    // Then: Should return POST_SHARED
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(WorkflowEventType.POST_SHARED);
  }

  @Test
  void getEventType_withRoomCreatedEvent_shouldReturnRoomCreated() {
    // Given: An event with RoomCreated set
    Event event = new Event();
    RoomCreatedEvent roomCreatedEvent = new RoomCreatedEvent();
    event.setRoomCreated(roomCreatedEvent);

    // When: Calling getEventType
    Optional<WorkflowEventType> result = WorkflowEventType.getEventType(event);

    // Then: Should return ROOM_CREATED
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(WorkflowEventType.ROOM_CREATED);
  }

  @Test
  void getEventType_withRoomUpdatedEvent_shouldReturnRoomUpdated() {
    // Given: An event with RoomUpdated set
    Event event = new Event();
    RoomUpdatedEvent roomUpdatedEvent = new RoomUpdatedEvent();
    event.setRoomUpdated(roomUpdatedEvent);

    // When: Calling getEventType
    Optional<WorkflowEventType> result = WorkflowEventType.getEventType(event);

    // Then: Should return ROOM_UPDATED
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(WorkflowEventType.ROOM_UPDATED);
  }

  @Test
  void getEventType_withRoomDeactivatedEvent_shouldReturnRoomDeactivated() {
    // Given: An event with RoomDeactivated set
    Event event = new Event();
    RoomDeactivatedEvent roomDeactivatedEvent = new RoomDeactivatedEvent();
    event.setRoomDeactivated(roomDeactivatedEvent);

    // When: Calling getEventType
    Optional<WorkflowEventType> result = WorkflowEventType.getEventType(event);

    // Then: Should return ROOM_DEACTIVATED
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(WorkflowEventType.ROOM_DEACTIVATED);
  }

  @Test
  void getEventType_withRoomReactivatedEvent_shouldReturnRoomReactivated() {
    // Given: An event with RoomReactivated set
    Event event = new Event();
    RoomReactivatedEvent roomReactivatedEvent = new RoomReactivatedEvent();
    event.setRoomReactivated(roomReactivatedEvent);

    // When: Calling getEventType
    Optional<WorkflowEventType> result = WorkflowEventType.getEventType(event);

    // Then: Should return ROOM_REACTIVATED
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(WorkflowEventType.ROOM_REACTIVATED);
  }

  @Test
  void getEventType_withRoomMemberPromotedToOwnerEvent_shouldReturnRoomMemberPromotedToOwner() {
    // Given: An event with RoomMemberPromotedToOwner set
    Event event = new Event();
    RoomMemberPromotedToOwnerEvent roomMemberPromotedToOwnerEvent = new RoomMemberPromotedToOwnerEvent();
    event.setRoomMemberPromotedToOwner(roomMemberPromotedToOwnerEvent);

    // When: Calling getEventType
    Optional<WorkflowEventType> result = WorkflowEventType.getEventType(event);

    // Then: Should return ROOM_MEMBER_PROMOTED_TO_OWNER
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(WorkflowEventType.ROOM_MEMBER_PROMOTED_TO_OWNER);
  }

  @Test
  void getEventType_withRoomMemberDemotedFromOwnerEvent_shouldReturnRoomMemberDemotedFromOwner() {
    // Given: An event with RoomMemberDemotedFromOwner set
    Event event = new Event();
    RoomMemberDemotedFromOwnerEvent roomMemberDemotedFromOwnerEvent = new RoomMemberDemotedFromOwnerEvent();
    event.setRoomMemberDemotedFromOwner(roomMemberDemotedFromOwnerEvent);

    // When: Calling getEventType
    Optional<WorkflowEventType> result = WorkflowEventType.getEventType(event);

    // Then: Should return ROOM_MEMBER_DEMOTED_FROM_OWNER
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(WorkflowEventType.ROOM_MEMBER_DEMOTED_FROM_OWNER);
  }

  @Test
  void getEventType_withUserRequestedJoinRoomEvent_shouldReturnUserRequestedJoinRoom() {
    // Given: An event with UserRequestedJoinRoom set
    Event event = new Event();
    UserRequestedToJoinRoomEvent userRequestedToJoinRoomEvent = new UserRequestedToJoinRoomEvent();
    event.setUserRequestedJoinRoom(userRequestedToJoinRoomEvent);

    // When: Calling getEventType
    Optional<WorkflowEventType> result = WorkflowEventType.getEventType(event);

    // Then: Should return USER_REQUESTED_JOIN_ROOM
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(WorkflowEventType.USER_REQUESTED_JOIN_ROOM);
  }

  @Test
  void getEventType_withUserJoinedRoomEvent_shouldReturnUserJoinedRoom() {
    // Given: An event with UserJoinedRoom set
    Event event = new Event();
    UserJoinedRoomEvent userJoinedRoomEvent = new UserJoinedRoomEvent();
    event.setUserJoinedRoom(userJoinedRoomEvent);

    // When: Calling getEventType
    Optional<WorkflowEventType> result = WorkflowEventType.getEventType(event);

    // Then: Should return USER_JOINED_ROOM
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(WorkflowEventType.USER_JOINED_ROOM);
  }

  @Test
  void getEventType_withUserLeftRoomEvent_shouldReturnUserLeftRoom() {
    // Given: An event with UserLeftRoom set
    Event event = new Event();
    UserLeftRoomEvent userLeftRoomEvent = new UserLeftRoomEvent();
    event.setUserLeftRoom(userLeftRoomEvent);

    // When: Calling getEventType
    Optional<WorkflowEventType> result = WorkflowEventType.getEventType(event);

    // Then: Should return USER_LEFT_ROOM
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(WorkflowEventType.USER_LEFT_ROOM);
  }

  @Test
  void getEventType_withConnectionRequestedEvent_shouldReturnConnectionRequested() {
    // Given: An event with ConnectionRequested set
    Event event = new Event();
    ConnectionRequestedEvent connectionRequestedEvent = new ConnectionRequestedEvent();
    event.setConnectionRequested(connectionRequestedEvent);

    // When: Calling getEventType
    Optional<WorkflowEventType> result = WorkflowEventType.getEventType(event);

    // Then: Should return CONNECTION_REQUESTED
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(WorkflowEventType.CONNECTION_REQUESTED);
  }

  @Test
  void getEventType_withConnectionAcceptedEvent_shouldReturnConnectionAccepted() {
    // Given: An event with ConnectionAccepted set
    Event event = new Event();
    ConnectionAcceptedEvent connectionAcceptedEvent = new ConnectionAcceptedEvent();
    event.setConnectionAccepted(connectionAcceptedEvent);

    // When: Calling getEventType
    Optional<WorkflowEventType> result = WorkflowEventType.getEventType(event);

    // Then: Should return CONNECTION_ACCEPTED
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(WorkflowEventType.CONNECTION_ACCEPTED);
  }

  @Test
  void getEventType_withFormRepliedEvent_shouldReturnFormReplied() {
    // Given: An event with FormReplied set
    Event event = new Event();
    FormRepliedEvent formRepliedEvent = new FormRepliedEvent();
    event.setFormReplied(formRepliedEvent);

    // When: Calling getEventType
    Optional<WorkflowEventType> result = WorkflowEventType.getEventType(event);

    // Then: Should return FORM_REPLIED
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(WorkflowEventType.FORM_REPLIED);
  }

  @Test
  void getEventType_withRequestReceivedEvent_shouldReturnRequestReceived() {
    // Given: An event with RequestReceived set
    Event event = new Event();
    RequestReceivedEvent requestReceivedEvent = new RequestReceivedEvent();
    event.setRequestReceived(requestReceivedEvent);

    // When: Calling getEventType
    Optional<WorkflowEventType> result = WorkflowEventType.getEventType(event);

    // Then: Should return REQUEST_RECEIVED
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(WorkflowEventType.REQUEST_RECEIVED);
  }

  @Test
  void getEventType_withTimerFiredEvent_shouldReturnTimeFired() {
    // Given: An event with TimerFired set
    Event event = new Event();
    TimerFiredEvent timerFiredEvent = new TimerFiredEvent();
    event.setTimerFired(timerFiredEvent);

    // When: Calling getEventType
    Optional<WorkflowEventType> result = WorkflowEventType.getEventType(event);

    // Then: Should return TIME_FIRED
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(WorkflowEventType.TIME_FIRED);
  }

  @Test
  void getEventType_withOneOfEvent_shouldReturnOneOf() {
    // Given: An event with OneOf list containing an event
    Event event = new Event();
    Event innerEvent = new Event();
    MessageReceivedEvent messageReceivedEvent = new MessageReceivedEvent();
    innerEvent.setMessageReceived(messageReceivedEvent);

    List<Event> oneOfList = new ArrayList<>();
    oneOfList.add(innerEvent);
    event.setOneOf(oneOfList);

    // When: Calling getEventType
    Optional<WorkflowEventType> result = WorkflowEventType.getEventType(event);

    // Then: Should return ONE_OF
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(WorkflowEventType.ONE_OF);
  }

  @Test
  void getEventType_withEmptyEvent_shouldReturnEmpty() {
    // Given: An event with no fields set
    Event event = new Event();

    // When: Calling getEventType
    Optional<WorkflowEventType> result = WorkflowEventType.getEventType(event);

    // Then: Should return empty Optional
    assertThat(result).isEmpty();
  }

  @Test
  void getEventType_withEmptyOneOfList_shouldReturnEmpty() {
    // Given: An event with empty OneOf list
    Event event = new Event();
    event.setOneOf(new ArrayList<>());

    // When: Calling getEventType
    Optional<WorkflowEventType> result = WorkflowEventType.getEventType(event);

    // Then: Should return empty Optional (CollectionUtils.isEmpty returns true for empty list)
    assertThat(result).isEmpty();
  }

  @Test
  void getEventType_shouldReturnFirstMatchingType() {
    // Given: An event with MESSAGE_RECEIVED set (first to be checked in enum order)
    Event event = new Event();
    MessageReceivedEvent messageReceivedEvent = new MessageReceivedEvent();
    event.setMessageReceived(messageReceivedEvent);

    // When: Calling getEventType
    Optional<WorkflowEventType> result = WorkflowEventType.getEventType(event);

    // Then: Should return MESSAGE_RECEIVED (first match wins)
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(WorkflowEventType.MESSAGE_RECEIVED);
  }

  @Test
  void getEventType_withMultipleEventsSet_shouldReturnFirstMatch() {
    // Given: An event with both MessageReceived and MessageSuppressed set
    // (This tests the order of checking in the enum)
    Event event = new Event();
    MessageReceivedEvent messageReceivedEvent = new MessageReceivedEvent();
    MessageSuppressedEvent messageSuppressedEvent = new MessageSuppressedEvent();
    event.setMessageReceived(messageReceivedEvent);
    event.setMessageSuppressed(messageSuppressedEvent);

    // When: Calling getEventType
    Optional<WorkflowEventType> result = WorkflowEventType.getEventType(event);

    // Then: Should return MESSAGE_RECEIVED (first in enum order)
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(WorkflowEventType.MESSAGE_RECEIVED);
  }

  @Test
  void getEventType_oneOfHasPriorityOverOtherEvents() {
    // Given: An event with both OneOf and MessageReceived set
    Event event = new Event();
    Event innerEvent = new Event();
    MessageReceivedEvent messageReceivedEvent = new MessageReceivedEvent();
    innerEvent.setMessageReceived(messageReceivedEvent);

    List<Event> oneOfList = new ArrayList<>();
    oneOfList.add(innerEvent);
    event.setOneOf(oneOfList);

    // Also set MessageReceived on outer event
    MessageReceivedEvent outerMessageReceived = new MessageReceivedEvent();
    event.setMessageReceived(outerMessageReceived);

    // When: Calling getEventType
    Optional<WorkflowEventType> result = WorkflowEventType.getEventType(event);

    // Then: Should return MESSAGE_RECEIVED since it comes before ONE_OF in enum order
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(WorkflowEventType.MESSAGE_RECEIVED);
  }
}
