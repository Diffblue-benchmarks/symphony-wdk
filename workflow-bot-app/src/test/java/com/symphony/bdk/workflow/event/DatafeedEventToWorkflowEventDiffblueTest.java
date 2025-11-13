package com.symphony.bdk.workflow.event;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
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
import com.symphony.bdk.workflow.IntegrationTest;
import com.symphony.bdk.workflow.engine.WorkflowEngine;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DatafeedEventToWorkflowEvent.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class DatafeedEventToWorkflowEventDiffblueTest {
  @Autowired private DatafeedEventToWorkflowEvent datafeedEventToWorkflowEvent;

  @MockBean private WorkflowEngine workflowEngine;

  /**
   * Test {@link DatafeedEventToWorkflowEvent#onMessageSent(RealTimeEvent)}.
   *
   * <p>Method under test: {@link DatafeedEventToWorkflowEvent#onMessageSent(RealTimeEvent)}
   */
  @Test
  @DisplayName("Test onMessageSent(RealTimeEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DatafeedEventToWorkflowEvent.onMessageSent(RealTimeEvent)"})
  void testOnMessageSent() {
    // Arrange
    doNothing().when(workflowEngine).onEvent(Mockito.<RealTimeEvent<Object>>any());
    RealTimeEvent<V4MessageSent> event =
        IntegrationTest.messageReceived("Not all who wander are lost");

    // Act
    datafeedEventToWorkflowEvent.onMessageSent(event);

    // Assert
    verify(workflowEngine).onEvent(isA(RealTimeEvent.class));
  }

  /**
   * Test {@link DatafeedEventToWorkflowEvent#onSymphonyElementsAction(RealTimeEvent)}.
   *
   * <p>Method under test: {@link
   * DatafeedEventToWorkflowEvent#onSymphonyElementsAction(RealTimeEvent)}
   */
  @Test
  @DisplayName("Test onSymphonyElementsAction(RealTimeEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DatafeedEventToWorkflowEvent.onSymphonyElementsAction(RealTimeEvent)"})
  void testOnSymphonyElementsAction() {
    // Arrange
    doNothing().when(workflowEngine).onEvent(Mockito.<RealTimeEvent<Object>>any());
    RealTimeEvent<V4SymphonyElementsAction> event =
        IntegrationTest.form("42", "42", new HashMap<>(), "42");

    // Act
    datafeedEventToWorkflowEvent.onSymphonyElementsAction(event);

    // Assert
    verify(workflowEngine).onEvent(isA(RealTimeEvent.class));
  }

  /**
   * Test {@link DatafeedEventToWorkflowEvent#onConnectionRequested(RealTimeEvent)}.
   *
   * <p>Method under test: {@link DatafeedEventToWorkflowEvent#onConnectionRequested(RealTimeEvent)}
   */
  @Test
  @DisplayName("Test onConnectionRequested(RealTimeEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DatafeedEventToWorkflowEvent.onConnectionRequested(RealTimeEvent)"})
  void testOnConnectionRequested() {
    // Arrange
    doNothing().when(workflowEngine).onEvent(Mockito.<RealTimeEvent<Object>>any());
    V4Initiator initiator = new V4Initiator();
    RealTimeEvent<V4ConnectionRequested> event =
        new RealTimeEvent<>(initiator, new V4ConnectionRequested());

    // Act
    datafeedEventToWorkflowEvent.onConnectionRequested(event);

    // Assert
    verify(workflowEngine).onEvent(isA(RealTimeEvent.class));
  }

  /**
   * Test {@link DatafeedEventToWorkflowEvent#onConnectionAccepted(RealTimeEvent)}.
   *
   * <p>Method under test: {@link DatafeedEventToWorkflowEvent#onConnectionAccepted(RealTimeEvent)}
   */
  @Test
  @DisplayName("Test onConnectionAccepted(RealTimeEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DatafeedEventToWorkflowEvent.onConnectionAccepted(RealTimeEvent)"})
  void testOnConnectionAccepted() {
    // Arrange
    doNothing().when(workflowEngine).onEvent(Mockito.<RealTimeEvent<Object>>any());
    RealTimeEvent<V4ConnectionAccepted> event = IntegrationTest.connectionAccepted();

    // Act
    datafeedEventToWorkflowEvent.onConnectionAccepted(event);

    // Assert
    verify(workflowEngine).onEvent(isA(RealTimeEvent.class));
  }

  /**
   * Test {@link DatafeedEventToWorkflowEvent#onMessageSuppressed(RealTimeEvent)}.
   *
   * <p>Method under test: {@link DatafeedEventToWorkflowEvent#onMessageSuppressed(RealTimeEvent)}
   */
  @Test
  @DisplayName("Test onMessageSuppressed(RealTimeEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DatafeedEventToWorkflowEvent.onMessageSuppressed(RealTimeEvent)"})
  void testOnMessageSuppressed() {
    // Arrange
    doNothing().when(workflowEngine).onEvent(Mockito.<RealTimeEvent<Object>>any());
    RealTimeEvent<V4MessageSuppressed> event = IntegrationTest.messageSuppressed();

    // Act
    datafeedEventToWorkflowEvent.onMessageSuppressed(event);

    // Assert
    verify(workflowEngine).onEvent(isA(RealTimeEvent.class));
  }

  /**
   * Test {@link DatafeedEventToWorkflowEvent#onSharedPost(RealTimeEvent)}.
   *
   * <p>Method under test: {@link DatafeedEventToWorkflowEvent#onSharedPost(RealTimeEvent)}
   */
  @Test
  @DisplayName("Test onSharedPost(RealTimeEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DatafeedEventToWorkflowEvent.onSharedPost(RealTimeEvent)"})
  void testOnSharedPost() {
    // Arrange
    doNothing().when(workflowEngine).onEvent(Mockito.<RealTimeEvent<Object>>any());
    RealTimeEvent<V4SharedPost> event = IntegrationTest.postShared();

    // Act
    datafeedEventToWorkflowEvent.onSharedPost(event);

    // Assert
    verify(workflowEngine).onEvent(isA(RealTimeEvent.class));
  }

  /**
   * Test {@link DatafeedEventToWorkflowEvent#onInstantMessageCreated(RealTimeEvent)}.
   *
   * <p>Method under test: {@link
   * DatafeedEventToWorkflowEvent#onInstantMessageCreated(RealTimeEvent)}
   */
  @Test
  @DisplayName("Test onInstantMessageCreated(RealTimeEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DatafeedEventToWorkflowEvent.onInstantMessageCreated(RealTimeEvent)"})
  void testOnInstantMessageCreated() {
    // Arrange
    doNothing().when(workflowEngine).onEvent(Mockito.<RealTimeEvent<Object>>any());
    RealTimeEvent<V4InstantMessageCreated> event = IntegrationTest.imCreated();

    // Act
    datafeedEventToWorkflowEvent.onInstantMessageCreated(event);

    // Assert
    verify(workflowEngine).onEvent(isA(RealTimeEvent.class));
  }

  /**
   * Test {@link DatafeedEventToWorkflowEvent#onRoomCreated(RealTimeEvent)}.
   *
   * <p>Method under test: {@link DatafeedEventToWorkflowEvent#onRoomCreated(RealTimeEvent)}
   */
  @Test
  @DisplayName("Test onRoomCreated(RealTimeEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DatafeedEventToWorkflowEvent.onRoomCreated(RealTimeEvent)"})
  void testOnRoomCreated() {
    // Arrange
    doNothing().when(workflowEngine).onEvent(Mockito.<RealTimeEvent<Object>>any());
    RealTimeEvent<V4RoomCreated> event = IntegrationTest.roomCreated();

    // Act
    datafeedEventToWorkflowEvent.onRoomCreated(event);

    // Assert
    verify(workflowEngine).onEvent(isA(RealTimeEvent.class));
  }

  /**
   * Test {@link DatafeedEventToWorkflowEvent#onRoomUpdated(RealTimeEvent)}.
   *
   * <p>Method under test: {@link DatafeedEventToWorkflowEvent#onRoomUpdated(RealTimeEvent)}
   */
  @Test
  @DisplayName("Test onRoomUpdated(RealTimeEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DatafeedEventToWorkflowEvent.onRoomUpdated(RealTimeEvent)"})
  void testOnRoomUpdated() {
    // Arrange
    doNothing().when(workflowEngine).onEvent(Mockito.<RealTimeEvent<Object>>any());
    RealTimeEvent<V4RoomUpdated> event = IntegrationTest.roomUpdated();

    // Act
    datafeedEventToWorkflowEvent.onRoomUpdated(event);

    // Assert
    verify(workflowEngine).onEvent(isA(RealTimeEvent.class));
  }

  /**
   * Test {@link DatafeedEventToWorkflowEvent#onRoomDeactivated(RealTimeEvent)}.
   *
   * <p>Method under test: {@link DatafeedEventToWorkflowEvent#onRoomDeactivated(RealTimeEvent)}
   */
  @Test
  @DisplayName("Test onRoomDeactivated(RealTimeEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DatafeedEventToWorkflowEvent.onRoomDeactivated(RealTimeEvent)"})
  void testOnRoomDeactivated() {
    // Arrange
    doNothing().when(workflowEngine).onEvent(Mockito.<RealTimeEvent<Object>>any());
    RealTimeEvent<V4RoomDeactivated> event = IntegrationTest.roomDeactivated();

    // Act
    datafeedEventToWorkflowEvent.onRoomDeactivated(event);

    // Assert
    verify(workflowEngine).onEvent(isA(RealTimeEvent.class));
  }

  /**
   * Test {@link DatafeedEventToWorkflowEvent#onRoomReactivated(RealTimeEvent)}.
   *
   * <p>Method under test: {@link DatafeedEventToWorkflowEvent#onRoomReactivated(RealTimeEvent)}
   */
  @Test
  @DisplayName("Test onRoomReactivated(RealTimeEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DatafeedEventToWorkflowEvent.onRoomReactivated(RealTimeEvent)"})
  void testOnRoomReactivated() {
    // Arrange
    doNothing().when(workflowEngine).onEvent(Mockito.<RealTimeEvent<Object>>any());
    RealTimeEvent<V4RoomReactivated> event = IntegrationTest.roomReactivated();

    // Act
    datafeedEventToWorkflowEvent.onRoomReactivated(event);

    // Assert
    verify(workflowEngine).onEvent(isA(RealTimeEvent.class));
  }

  /**
   * Test {@link DatafeedEventToWorkflowEvent#onUserRequestedToJoinRoom(RealTimeEvent)}.
   *
   * <p>Method under test: {@link
   * DatafeedEventToWorkflowEvent#onUserRequestedToJoinRoom(RealTimeEvent)}
   */
  @Test
  @DisplayName("Test onUserRequestedToJoinRoom(RealTimeEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DatafeedEventToWorkflowEvent.onUserRequestedToJoinRoom(RealTimeEvent)"})
  void testOnUserRequestedToJoinRoom() {
    // Arrange
    doNothing().when(workflowEngine).onEvent(Mockito.<RealTimeEvent<Object>>any());
    RealTimeEvent<V4UserRequestedToJoinRoom> event = IntegrationTest.connectionRequested();

    // Act
    datafeedEventToWorkflowEvent.onUserRequestedToJoinRoom(event);

    // Assert
    verify(workflowEngine).onEvent(isA(RealTimeEvent.class));
  }

  /**
   * Test {@link DatafeedEventToWorkflowEvent#onUserJoinedRoom(RealTimeEvent)}.
   *
   * <p>Method under test: {@link DatafeedEventToWorkflowEvent#onUserJoinedRoom(RealTimeEvent)}
   */
  @Test
  @DisplayName("Test onUserJoinedRoom(RealTimeEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DatafeedEventToWorkflowEvent.onUserJoinedRoom(RealTimeEvent)"})
  void testOnUserJoinedRoom() {
    // Arrange
    doNothing().when(workflowEngine).onEvent(Mockito.<RealTimeEvent<Object>>any());
    RealTimeEvent<V4UserJoinedRoom> event = IntegrationTest.userJoined();

    // Act
    datafeedEventToWorkflowEvent.onUserJoinedRoom(event);

    // Assert
    verify(workflowEngine).onEvent(isA(RealTimeEvent.class));
  }

  /**
   * Test {@link DatafeedEventToWorkflowEvent#onUserLeftRoom(RealTimeEvent)}.
   *
   * <p>Method under test: {@link DatafeedEventToWorkflowEvent#onUserLeftRoom(RealTimeEvent)}
   */
  @Test
  @DisplayName("Test onUserLeftRoom(RealTimeEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DatafeedEventToWorkflowEvent.onUserLeftRoom(RealTimeEvent)"})
  void testOnUserLeftRoom() {
    // Arrange
    doNothing().when(workflowEngine).onEvent(Mockito.<RealTimeEvent<Object>>any());
    RealTimeEvent<V4UserLeftRoom> event = IntegrationTest.userLeft();

    // Act
    datafeedEventToWorkflowEvent.onUserLeftRoom(event);

    // Assert
    verify(workflowEngine).onEvent(isA(RealTimeEvent.class));
  }

  /**
   * Test {@link DatafeedEventToWorkflowEvent#onRoomMemberPromotedToOwner(RealTimeEvent)}.
   *
   * <p>Method under test: {@link
   * DatafeedEventToWorkflowEvent#onRoomMemberPromotedToOwner(RealTimeEvent)}
   */
  @Test
  @DisplayName("Test onRoomMemberPromotedToOwner(RealTimeEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DatafeedEventToWorkflowEvent.onRoomMemberPromotedToOwner(RealTimeEvent)"
  })
  void testOnRoomMemberPromotedToOwner() {
    // Arrange
    doNothing().when(workflowEngine).onEvent(Mockito.<RealTimeEvent<Object>>any());
    RealTimeEvent<V4RoomMemberPromotedToOwner> event = IntegrationTest.roomMemberPromoted();

    // Act
    datafeedEventToWorkflowEvent.onRoomMemberPromotedToOwner(event);

    // Assert
    verify(workflowEngine).onEvent(isA(RealTimeEvent.class));
  }

  /**
   * Test {@link DatafeedEventToWorkflowEvent#onRoomMemberDemotedFromOwner(RealTimeEvent)}.
   *
   * <p>Method under test: {@link
   * DatafeedEventToWorkflowEvent#onRoomMemberDemotedFromOwner(RealTimeEvent)}
   */
  @Test
  @DisplayName("Test onRoomMemberDemotedFromOwner(RealTimeEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DatafeedEventToWorkflowEvent.onRoomMemberDemotedFromOwner(RealTimeEvent)"
  })
  void testOnRoomMemberDemotedFromOwner() {
    // Arrange
    doNothing().when(workflowEngine).onEvent(Mockito.<RealTimeEvent<Object>>any());
    RealTimeEvent<V4RoomMemberDemotedFromOwner> event = IntegrationTest.roomOwnerDemoted();

    // Act
    datafeedEventToWorkflowEvent.onRoomMemberDemotedFromOwner(event);

    // Assert
    verify(workflowEngine).onEvent(isA(RealTimeEvent.class));
  }
}
