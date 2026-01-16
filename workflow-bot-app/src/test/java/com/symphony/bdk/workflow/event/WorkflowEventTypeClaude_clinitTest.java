package com.symphony.bdk.workflow.event;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WorkflowEventTypeClaude_clinitTest {

  @Test
  void clinit_shouldInitializeMessageReceivedConstant() {
    // When: Accessing MESSAGE_RECEIVED constant (triggers class initialization)
    WorkflowEventType eventType = WorkflowEventType.MESSAGE_RECEIVED;

    // Then: The constant should be properly initialized with its event name
    assertThat(eventType).isNotNull();
    assertThat(eventType.getEventName()).isEqualTo("message-received_");
  }

  @Test
  void clinit_shouldInitializeMessageSuppressedConstant() {
    // When: Accessing MESSAGE_SUPPRESSED constant
    WorkflowEventType eventType = WorkflowEventType.MESSAGE_SUPPRESSED;

    // Then: The constant should be properly initialized with its event name
    assertThat(eventType).isNotNull();
    assertThat(eventType.getEventName()).isEqualTo("message-suppressed");
  }

  @Test
  void clinit_shouldInitializeImCreatedConstant() {
    // When: Accessing IM_CREATED constant
    WorkflowEventType eventType = WorkflowEventType.IM_CREATED;

    // Then: The constant should be properly initialized with its event name
    assertThat(eventType).isNotNull();
    assertThat(eventType.getEventName()).isEqualTo("im-created");
  }

  @Test
  void clinit_shouldInitializePostSharedConstant() {
    // When: Accessing POST_SHARED constant
    WorkflowEventType eventType = WorkflowEventType.POST_SHARED;

    // Then: The constant should be properly initialized with its event name
    assertThat(eventType).isNotNull();
    assertThat(eventType.getEventName()).isEqualTo("post-shared");
  }

  @Test
  void clinit_shouldInitializeRoomCreatedConstant() {
    // When: Accessing ROOM_CREATED constant
    WorkflowEventType eventType = WorkflowEventType.ROOM_CREATED;

    // Then: The constant should be properly initialized with its event name
    assertThat(eventType).isNotNull();
    assertThat(eventType.getEventName()).isEqualTo("room-created");
  }

  @Test
  void clinit_shouldInitializeRoomUpdatedConstant() {
    // When: Accessing ROOM_UPDATED constant
    WorkflowEventType eventType = WorkflowEventType.ROOM_UPDATED;

    // Then: The constant should be properly initialized with its event name
    assertThat(eventType).isNotNull();
    assertThat(eventType.getEventName()).isEqualTo("room-updated");
  }

  @Test
  void clinit_shouldInitializeRoomDeactivatedConstant() {
    // When: Accessing ROOM_DEACTIVATED constant
    WorkflowEventType eventType = WorkflowEventType.ROOM_DEACTIVATED;

    // Then: The constant should be properly initialized with its event name
    assertThat(eventType).isNotNull();
    assertThat(eventType.getEventName()).isEqualTo("room-deactivated");
  }

  @Test
  void clinit_shouldInitializeRoomReactivatedConstant() {
    // When: Accessing ROOM_REACTIVATED constant
    WorkflowEventType eventType = WorkflowEventType.ROOM_REACTIVATED;

    // Then: The constant should be properly initialized with its event name
    assertThat(eventType).isNotNull();
    assertThat(eventType.getEventName()).isEqualTo("room-reactivated");
  }

  @Test
  void clinit_shouldInitializeRoomMemberPromotedToOwnerConstant() {
    // When: Accessing ROOM_MEMBER_PROMOTED_TO_OWNER constant
    WorkflowEventType eventType = WorkflowEventType.ROOM_MEMBER_PROMOTED_TO_OWNER;

    // Then: The constant should be properly initialized with its event name
    assertThat(eventType).isNotNull();
    assertThat(eventType.getEventName()).isEqualTo("room-member-promoted-to-owner-event");
  }

  @Test
  void clinit_shouldInitializeRoomMemberDemotedFromOwnerConstant() {
    // When: Accessing ROOM_MEMBER_DEMOTED_FROM_OWNER constant
    WorkflowEventType eventType = WorkflowEventType.ROOM_MEMBER_DEMOTED_FROM_OWNER;

    // Then: The constant should be properly initialized with its event name
    assertThat(eventType).isNotNull();
    assertThat(eventType.getEventName()).isEqualTo("room-member-demoted-from-owner-event");
  }

  @Test
  void clinit_shouldInitializeUserRequestedJoinRoomConstant() {
    // When: Accessing USER_REQUESTED_JOIN_ROOM constant
    WorkflowEventType eventType = WorkflowEventType.USER_REQUESTED_JOIN_ROOM;

    // Then: The constant should be properly initialized with its event name
    assertThat(eventType).isNotNull();
    assertThat(eventType.getEventName()).isEqualTo("user-requested-join-room");
  }

  @Test
  void clinit_shouldInitializeUserJoinedRoomConstant() {
    // When: Accessing USER_JOINED_ROOM constant
    WorkflowEventType eventType = WorkflowEventType.USER_JOINED_ROOM;

    // Then: The constant should be properly initialized with its event name
    assertThat(eventType).isNotNull();
    assertThat(eventType.getEventName()).isEqualTo("user-joined-room");
  }

  @Test
  void clinit_shouldInitializeUserLeftRoomConstant() {
    // When: Accessing USER_LEFT_ROOM constant
    WorkflowEventType eventType = WorkflowEventType.USER_LEFT_ROOM;

    // Then: The constant should be properly initialized with its event name
    assertThat(eventType).isNotNull();
    assertThat(eventType.getEventName()).isEqualTo("user-left-room");
  }

  @Test
  void clinit_shouldInitializeConnectionRequestedConstant() {
    // When: Accessing CONNECTION_REQUESTED constant
    WorkflowEventType eventType = WorkflowEventType.CONNECTION_REQUESTED;

    // Then: The constant should be properly initialized with its event name
    assertThat(eventType).isNotNull();
    assertThat(eventType.getEventName()).isEqualTo("connection-requested");
  }

  @Test
  void clinit_shouldInitializeConnectionAcceptedConstant() {
    // When: Accessing CONNECTION_ACCEPTED constant
    WorkflowEventType eventType = WorkflowEventType.CONNECTION_ACCEPTED;

    // Then: The constant should be properly initialized with its event name
    assertThat(eventType).isNotNull();
    assertThat(eventType.getEventName()).isEqualTo("connection-accepted");
  }

  @Test
  void clinit_shouldInitializeFormRepliedConstant() {
    // When: Accessing FORM_REPLIED constant
    WorkflowEventType eventType = WorkflowEventType.FORM_REPLIED;

    // Then: The constant should be properly initialized with its event name
    assertThat(eventType).isNotNull();
    assertThat(eventType.getEventName()).isEqualTo("form-reply_");
  }

  @Test
  void clinit_shouldInitializeRequestReceivedConstant() {
    // When: Accessing REQUEST_RECEIVED constant
    WorkflowEventType eventType = WorkflowEventType.REQUEST_RECEIVED;

    // Then: The constant should be properly initialized with its event name
    assertThat(eventType).isNotNull();
    assertThat(eventType.getEventName()).isEqualTo("request-received_");
  }

  @Test
  void clinit_shouldInitializeTimeFiredConstant() {
    // When: Accessing TIME_FIRED constant
    WorkflowEventType eventType = WorkflowEventType.TIME_FIRED;

    // Then: The constant should be properly initialized with its event name
    assertThat(eventType).isNotNull();
    assertThat(eventType.getEventName()).isEqualTo("timerFired_date");
  }

  @Test
  void clinit_shouldInitializeOneOfConstant() {
    // When: Accessing ONE_OF constant
    WorkflowEventType eventType = WorkflowEventType.ONE_OF;

    // Then: The constant should be properly initialized with its event name
    assertThat(eventType).isNotNull();
    assertThat(eventType.getEventName()).isEqualTo("");
  }

  @Test
  void clinit_shouldInitializeAllConstantsWithCorrectOrdinals() {
    // When: Accessing all enum constants through values()
    WorkflowEventType[] values = WorkflowEventType.values();

    // Then: All constants should be initialized with correct ordinals
    assertThat(values).hasSize(19);
    assertThat(values[0]).isEqualTo(WorkflowEventType.MESSAGE_RECEIVED);
    assertThat(values[0].ordinal()).isEqualTo(0);
    assertThat(values[1]).isEqualTo(WorkflowEventType.MESSAGE_SUPPRESSED);
    assertThat(values[1].ordinal()).isEqualTo(1);
    assertThat(values[2]).isEqualTo(WorkflowEventType.IM_CREATED);
    assertThat(values[2].ordinal()).isEqualTo(2);
    assertThat(values[3]).isEqualTo(WorkflowEventType.POST_SHARED);
    assertThat(values[3].ordinal()).isEqualTo(3);
    assertThat(values[4]).isEqualTo(WorkflowEventType.ROOM_CREATED);
    assertThat(values[4].ordinal()).isEqualTo(4);
    assertThat(values[5]).isEqualTo(WorkflowEventType.ROOM_UPDATED);
    assertThat(values[5].ordinal()).isEqualTo(5);
    assertThat(values[6]).isEqualTo(WorkflowEventType.ROOM_DEACTIVATED);
    assertThat(values[6].ordinal()).isEqualTo(6);
    assertThat(values[7]).isEqualTo(WorkflowEventType.ROOM_REACTIVATED);
    assertThat(values[7].ordinal()).isEqualTo(7);
    assertThat(values[8]).isEqualTo(WorkflowEventType.ROOM_MEMBER_PROMOTED_TO_OWNER);
    assertThat(values[8].ordinal()).isEqualTo(8);
    assertThat(values[9]).isEqualTo(WorkflowEventType.ROOM_MEMBER_DEMOTED_FROM_OWNER);
    assertThat(values[9].ordinal()).isEqualTo(9);
    assertThat(values[10]).isEqualTo(WorkflowEventType.USER_REQUESTED_JOIN_ROOM);
    assertThat(values[10].ordinal()).isEqualTo(10);
    assertThat(values[11]).isEqualTo(WorkflowEventType.USER_JOINED_ROOM);
    assertThat(values[11].ordinal()).isEqualTo(11);
    assertThat(values[12]).isEqualTo(WorkflowEventType.USER_LEFT_ROOM);
    assertThat(values[12].ordinal()).isEqualTo(12);
    assertThat(values[13]).isEqualTo(WorkflowEventType.CONNECTION_REQUESTED);
    assertThat(values[13].ordinal()).isEqualTo(13);
    assertThat(values[14]).isEqualTo(WorkflowEventType.CONNECTION_ACCEPTED);
    assertThat(values[14].ordinal()).isEqualTo(14);
    assertThat(values[15]).isEqualTo(WorkflowEventType.FORM_REPLIED);
    assertThat(values[15].ordinal()).isEqualTo(15);
    assertThat(values[16]).isEqualTo(WorkflowEventType.REQUEST_RECEIVED);
    assertThat(values[16].ordinal()).isEqualTo(16);
    assertThat(values[17]).isEqualTo(WorkflowEventType.TIME_FIRED);
    assertThat(values[17].ordinal()).isEqualTo(17);
    assertThat(values[18]).isEqualTo(WorkflowEventType.ONE_OF);
    assertThat(values[18].ordinal()).isEqualTo(18);
  }

  @Test
  void clinit_shouldInitializeAllConstantsAccessibleByName() {
    // When/Then: All enum constants should be accessible by their names
    assertThat(WorkflowEventType.valueOf("MESSAGE_RECEIVED")).isEqualTo(WorkflowEventType.MESSAGE_RECEIVED);
    assertThat(WorkflowEventType.valueOf("MESSAGE_SUPPRESSED")).isEqualTo(WorkflowEventType.MESSAGE_SUPPRESSED);
    assertThat(WorkflowEventType.valueOf("IM_CREATED")).isEqualTo(WorkflowEventType.IM_CREATED);
    assertThat(WorkflowEventType.valueOf("POST_SHARED")).isEqualTo(WorkflowEventType.POST_SHARED);
    assertThat(WorkflowEventType.valueOf("ROOM_CREATED")).isEqualTo(WorkflowEventType.ROOM_CREATED);
    assertThat(WorkflowEventType.valueOf("ROOM_UPDATED")).isEqualTo(WorkflowEventType.ROOM_UPDATED);
    assertThat(WorkflowEventType.valueOf("ROOM_DEACTIVATED")).isEqualTo(WorkflowEventType.ROOM_DEACTIVATED);
    assertThat(WorkflowEventType.valueOf("ROOM_REACTIVATED")).isEqualTo(WorkflowEventType.ROOM_REACTIVATED);
    assertThat(WorkflowEventType.valueOf("ROOM_MEMBER_PROMOTED_TO_OWNER")).isEqualTo(WorkflowEventType.ROOM_MEMBER_PROMOTED_TO_OWNER);
    assertThat(WorkflowEventType.valueOf("ROOM_MEMBER_DEMOTED_FROM_OWNER")).isEqualTo(WorkflowEventType.ROOM_MEMBER_DEMOTED_FROM_OWNER);
    assertThat(WorkflowEventType.valueOf("USER_REQUESTED_JOIN_ROOM")).isEqualTo(WorkflowEventType.USER_REQUESTED_JOIN_ROOM);
    assertThat(WorkflowEventType.valueOf("USER_JOINED_ROOM")).isEqualTo(WorkflowEventType.USER_JOINED_ROOM);
    assertThat(WorkflowEventType.valueOf("USER_LEFT_ROOM")).isEqualTo(WorkflowEventType.USER_LEFT_ROOM);
    assertThat(WorkflowEventType.valueOf("CONNECTION_REQUESTED")).isEqualTo(WorkflowEventType.CONNECTION_REQUESTED);
    assertThat(WorkflowEventType.valueOf("CONNECTION_ACCEPTED")).isEqualTo(WorkflowEventType.CONNECTION_ACCEPTED);
    assertThat(WorkflowEventType.valueOf("FORM_REPLIED")).isEqualTo(WorkflowEventType.FORM_REPLIED);
    assertThat(WorkflowEventType.valueOf("REQUEST_RECEIVED")).isEqualTo(WorkflowEventType.REQUEST_RECEIVED);
    assertThat(WorkflowEventType.valueOf("TIME_FIRED")).isEqualTo(WorkflowEventType.TIME_FIRED);
    assertThat(WorkflowEventType.valueOf("ONE_OF")).isEqualTo(WorkflowEventType.ONE_OF);
  }
}
