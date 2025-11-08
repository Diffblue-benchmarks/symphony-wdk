package com.symphony.bdk.workflow.event;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import com.symphony.bdk.gen.api.model.V4ConnectionAccepted;
import com.symphony.bdk.gen.api.model.V4ConnectionRequested;
import com.symphony.bdk.gen.api.model.V4Initiator;
import com.symphony.bdk.gen.api.model.V4InstantMessageCreated;
import com.symphony.bdk.gen.api.model.V4MessageSent;
import com.symphony.bdk.gen.api.model.V4MessageSuppressed;
import com.symphony.bdk.gen.api.model.V4RoomCreated;
import com.symphony.bdk.gen.api.model.V4RoomDeactivated;
import com.symphony.bdk.gen.api.model.V4RoomMemberDemotedFromOwner;
import com.symphony.bdk.gen.api.model.V4RoomMemberPromotedToOwner;
import com.symphony.bdk.gen.api.model.V4RoomReactivated;
import com.symphony.bdk.gen.api.model.V4RoomUpdated;
import com.symphony.bdk.gen.api.model.V4SharedPost;
import com.symphony.bdk.gen.api.model.V4SymphonyElementsAction;
import com.symphony.bdk.gen.api.model.V4UserJoinedRoom;
import com.symphony.bdk.gen.api.model.V4UserLeftRoom;
import com.symphony.bdk.gen.api.model.V4UserRequestedToJoinRoom;
import com.symphony.bdk.spring.events.RealTimeEvent;
import com.symphony.bdk.workflow.engine.WorkflowEngine;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DatafeedEventToWorkflowEvent.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class DatafeedEventToWorkflowEventDiffblueTest {
  @Autowired
  private DatafeedEventToWorkflowEvent datafeedEventToWorkflowEvent;

  @MockBean
  private WorkflowEngine workflowEngine;

  /**
   * Method under test:
   * {@link DatafeedEventToWorkflowEvent#onMessageSent(RealTimeEvent)}
   */
  @Test
  void testOnMessageSent() {
    // Arrange
    doNothing().when(workflowEngine).onEvent(Mockito.<RealTimeEvent<Object>>any());
    V4Initiator initiator = new V4Initiator();

    // Act
    datafeedEventToWorkflowEvent.onMessageSent(new RealTimeEvent<>(initiator, new V4MessageSent()));

    // Assert that nothing has changed
    verify(workflowEngine).onEvent(isA(RealTimeEvent.class));
  }

  /**
   * Method under test:
   * {@link DatafeedEventToWorkflowEvent#onSymphonyElementsAction(RealTimeEvent)}
   */
  @Test
  void testOnSymphonyElementsAction() {
    // Arrange
    doNothing().when(workflowEngine).onEvent(Mockito.<RealTimeEvent<Object>>any());
    V4Initiator initiator = new V4Initiator();

    // Act
    datafeedEventToWorkflowEvent
        .onSymphonyElementsAction(new RealTimeEvent<>(initiator, new V4SymphonyElementsAction()));

    // Assert that nothing has changed
    verify(workflowEngine).onEvent(isA(RealTimeEvent.class));
  }

  /**
   * Method under test:
   * {@link DatafeedEventToWorkflowEvent#onConnectionRequested(RealTimeEvent)}
   */
  @Test
  void testOnConnectionRequested() {
    // Arrange
    doNothing().when(workflowEngine).onEvent(Mockito.<RealTimeEvent<Object>>any());
    V4Initiator initiator = new V4Initiator();

    // Act
    datafeedEventToWorkflowEvent.onConnectionRequested(new RealTimeEvent<>(initiator, new V4ConnectionRequested()));

    // Assert that nothing has changed
    verify(workflowEngine).onEvent(isA(RealTimeEvent.class));
  }

  /**
   * Method under test:
   * {@link DatafeedEventToWorkflowEvent#onConnectionAccepted(RealTimeEvent)}
   */
  @Test
  void testOnConnectionAccepted() {
    // Arrange
    doNothing().when(workflowEngine).onEvent(Mockito.<RealTimeEvent<Object>>any());
    V4Initiator initiator = new V4Initiator();

    // Act
    datafeedEventToWorkflowEvent.onConnectionAccepted(new RealTimeEvent<>(initiator, new V4ConnectionAccepted()));

    // Assert that nothing has changed
    verify(workflowEngine).onEvent(isA(RealTimeEvent.class));
  }

  /**
   * Method under test:
   * {@link DatafeedEventToWorkflowEvent#onMessageSuppressed(RealTimeEvent)}
   */
  @Test
  void testOnMessageSuppressed() {
    // Arrange
    doNothing().when(workflowEngine).onEvent(Mockito.<RealTimeEvent<Object>>any());
    V4Initiator initiator = new V4Initiator();

    // Act
    datafeedEventToWorkflowEvent.onMessageSuppressed(new RealTimeEvent<>(initiator, new V4MessageSuppressed()));

    // Assert that nothing has changed
    verify(workflowEngine).onEvent(isA(RealTimeEvent.class));
  }

  /**
   * Method under test:
   * {@link DatafeedEventToWorkflowEvent#onSharedPost(RealTimeEvent)}
   */
  @Test
  void testOnSharedPost() {
    // Arrange
    doNothing().when(workflowEngine).onEvent(Mockito.<RealTimeEvent<Object>>any());
    V4Initiator initiator = new V4Initiator();

    // Act
    datafeedEventToWorkflowEvent.onSharedPost(new RealTimeEvent<>(initiator, new V4SharedPost()));

    // Assert that nothing has changed
    verify(workflowEngine).onEvent(isA(RealTimeEvent.class));
  }

  /**
   * Method under test:
   * {@link DatafeedEventToWorkflowEvent#onInstantMessageCreated(RealTimeEvent)}
   */
  @Test
  void testOnInstantMessageCreated() {
    // Arrange
    doNothing().when(workflowEngine).onEvent(Mockito.<RealTimeEvent<Object>>any());
    V4Initiator initiator = new V4Initiator();

    // Act
    datafeedEventToWorkflowEvent.onInstantMessageCreated(new RealTimeEvent<>(initiator, new V4InstantMessageCreated()));

    // Assert that nothing has changed
    verify(workflowEngine).onEvent(isA(RealTimeEvent.class));
  }

  /**
   * Method under test:
   * {@link DatafeedEventToWorkflowEvent#onRoomCreated(RealTimeEvent)}
   */
  @Test
  void testOnRoomCreated() {
    // Arrange
    doNothing().when(workflowEngine).onEvent(Mockito.<RealTimeEvent<Object>>any());
    V4Initiator initiator = new V4Initiator();

    // Act
    datafeedEventToWorkflowEvent.onRoomCreated(new RealTimeEvent<>(initiator, new V4RoomCreated()));

    // Assert that nothing has changed
    verify(workflowEngine).onEvent(isA(RealTimeEvent.class));
  }

  /**
   * Method under test:
   * {@link DatafeedEventToWorkflowEvent#onRoomUpdated(RealTimeEvent)}
   */
  @Test
  void testOnRoomUpdated() {
    // Arrange
    doNothing().when(workflowEngine).onEvent(Mockito.<RealTimeEvent<Object>>any());
    V4Initiator initiator = new V4Initiator();

    // Act
    datafeedEventToWorkflowEvent.onRoomUpdated(new RealTimeEvent<>(initiator, new V4RoomUpdated()));

    // Assert that nothing has changed
    verify(workflowEngine).onEvent(isA(RealTimeEvent.class));
  }

  /**
   * Method under test:
   * {@link DatafeedEventToWorkflowEvent#onRoomDeactivated(RealTimeEvent)}
   */
  @Test
  void testOnRoomDeactivated() {
    // Arrange
    doNothing().when(workflowEngine).onEvent(Mockito.<RealTimeEvent<Object>>any());
    V4Initiator initiator = new V4Initiator();

    // Act
    datafeedEventToWorkflowEvent.onRoomDeactivated(new RealTimeEvent<>(initiator, new V4RoomDeactivated()));

    // Assert that nothing has changed
    verify(workflowEngine).onEvent(isA(RealTimeEvent.class));
  }

  /**
   * Method under test:
   * {@link DatafeedEventToWorkflowEvent#onRoomReactivated(RealTimeEvent)}
   */
  @Test
  void testOnRoomReactivated() {
    // Arrange
    doNothing().when(workflowEngine).onEvent(Mockito.<RealTimeEvent<Object>>any());
    V4Initiator initiator = new V4Initiator();

    // Act
    datafeedEventToWorkflowEvent.onRoomReactivated(new RealTimeEvent<>(initiator, new V4RoomReactivated()));

    // Assert that nothing has changed
    verify(workflowEngine).onEvent(isA(RealTimeEvent.class));
  }

  /**
   * Method under test:
   * {@link DatafeedEventToWorkflowEvent#onUserRequestedToJoinRoom(RealTimeEvent)}
   */
  @Test
  void testOnUserRequestedToJoinRoom() {
    // Arrange
    doNothing().when(workflowEngine).onEvent(Mockito.<RealTimeEvent<Object>>any());
    V4Initiator initiator = new V4Initiator();

    // Act
    datafeedEventToWorkflowEvent
        .onUserRequestedToJoinRoom(new RealTimeEvent<>(initiator, new V4UserRequestedToJoinRoom()));

    // Assert that nothing has changed
    verify(workflowEngine).onEvent(isA(RealTimeEvent.class));
  }

  /**
   * Method under test:
   * {@link DatafeedEventToWorkflowEvent#onUserJoinedRoom(RealTimeEvent)}
   */
  @Test
  void testOnUserJoinedRoom() {
    // Arrange
    doNothing().when(workflowEngine).onEvent(Mockito.<RealTimeEvent<Object>>any());
    V4Initiator initiator = new V4Initiator();

    // Act
    datafeedEventToWorkflowEvent.onUserJoinedRoom(new RealTimeEvent<>(initiator, new V4UserJoinedRoom()));

    // Assert that nothing has changed
    verify(workflowEngine).onEvent(isA(RealTimeEvent.class));
  }

  /**
   * Method under test:
   * {@link DatafeedEventToWorkflowEvent#onUserLeftRoom(RealTimeEvent)}
   */
  @Test
  void testOnUserLeftRoom() {
    // Arrange
    doNothing().when(workflowEngine).onEvent(Mockito.<RealTimeEvent<Object>>any());
    V4Initiator initiator = new V4Initiator();

    // Act
    datafeedEventToWorkflowEvent.onUserLeftRoom(new RealTimeEvent<>(initiator, new V4UserLeftRoom()));

    // Assert that nothing has changed
    verify(workflowEngine).onEvent(isA(RealTimeEvent.class));
  }

  /**
   * Method under test:
   * {@link DatafeedEventToWorkflowEvent#onRoomMemberPromotedToOwner(RealTimeEvent)}
   */
  @Test
  void testOnRoomMemberPromotedToOwner() {
    // Arrange
    doNothing().when(workflowEngine).onEvent(Mockito.<RealTimeEvent<Object>>any());
    V4Initiator initiator = new V4Initiator();

    // Act
    datafeedEventToWorkflowEvent
        .onRoomMemberPromotedToOwner(new RealTimeEvent<>(initiator, new V4RoomMemberPromotedToOwner()));

    // Assert that nothing has changed
    verify(workflowEngine).onEvent(isA(RealTimeEvent.class));
  }

  /**
   * Method under test:
   * {@link DatafeedEventToWorkflowEvent#onRoomMemberDemotedFromOwner(RealTimeEvent)}
   */
  @Test
  void testOnRoomMemberDemotedFromOwner() {
    // Arrange
    doNothing().when(workflowEngine).onEvent(Mockito.<RealTimeEvent<Object>>any());
    V4Initiator initiator = new V4Initiator();

    // Act
    datafeedEventToWorkflowEvent
        .onRoomMemberDemotedFromOwner(new RealTimeEvent<>(initiator, new V4RoomMemberDemotedFromOwner()));

    // Assert that nothing has changed
    verify(workflowEngine).onEvent(isA(RealTimeEvent.class));
  }
}
