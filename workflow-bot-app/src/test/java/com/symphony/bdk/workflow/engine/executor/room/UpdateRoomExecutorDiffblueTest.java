package com.symphony.bdk.workflow.engine.executor.room;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.gen.api.model.V3RoomAttributes;
import com.symphony.bdk.gen.api.model.V3RoomDetail;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.room.UpdateRoom;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;

@ContextConfiguration(classes = {UpdateRoomExecutor.class})
@ExtendWith(SpringExtension.class)
class UpdateRoomExecutorDiffblueTest {
  @Autowired private UpdateRoomExecutor updateRoomExecutor;

  /**
   * Test {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link Obo} (default constructor) UserId is one.
   *   <li>Then calls {@link UpdateRoom#getObo()}.
   * </ul>
   *
   * <p>Method under test: {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given Obo (default constructor) UserId is one; then calls getObo()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UpdateRoomExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenOboUserIdIsOne_thenCallsGetObo() {
    // Arrange
    Obo obo = new Obo();
    obo.setUserId(1L);
    obo.setUsername("janedoe");

    UpdateRoom updateRoom = mock(UpdateRoom.class);
    when(updateRoom.getObo()).thenReturn(obo);

    ActivityExecutorContext<UpdateRoom> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenThrow(new IllegalArgumentException());
    when(execution.getActivity()).thenReturn(updateRoom);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> updateRoomExecutor.execute(execution));
    verify(execution).bdk();
    verify(execution, atLeast(1)).getActivity();
    verify(updateRoom, atLeast(1)).getObo();
  }

  /**
   * Test {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link UpdateRoom} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext); given UpdateRoom (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UpdateRoomExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenUpdateRoom() {
    // Arrange
    ActivityExecutorContext<UpdateRoom> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenThrow(new IllegalArgumentException());
    when(execution.getActivity()).thenReturn(new UpdateRoom());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> updateRoomExecutor.execute(execution));
    verify(execution).bdk();
    verify(execution).getActivity();
  }

  /**
   * Test {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link UpdateRoom} (default constructor) Active is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given UpdateRoom (default constructor) Active is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UpdateRoomExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenUpdateRoomActiveIsTrue() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();
    updateRoom.setActive(true);

    ActivityExecutorContext<UpdateRoom> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenThrow(new IllegalArgumentException());
    when(execution.getActivity()).thenReturn(updateRoom);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> updateRoomExecutor.execute(execution));
    verify(execution).bdk();
    verify(execution).getActivity();
  }

  /**
   * Test {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link UpdateRoom} (default constructor) Keywords is {@link HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given UpdateRoom (default constructor) Keywords is HashMap()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UpdateRoomExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenUpdateRoomKeywordsIsHashMap() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();
    updateRoom.setKeywords(new HashMap<>());

    ActivityExecutorContext<UpdateRoom> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenThrow(new IllegalArgumentException());
    when(execution.getActivity()).thenReturn(updateRoom);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> updateRoomExecutor.execute(execution));
    verify(execution).bdk();
    verify(execution).getActivity();
  }

  /**
   * Test {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link UpdateRoom} (default constructor) MembersCanInvite is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given UpdateRoom (default constructor) MembersCanInvite is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UpdateRoomExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenUpdateRoomMembersCanInviteIsTrue() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();
    updateRoom.setMembersCanInvite(true);

    ActivityExecutorContext<UpdateRoom> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenThrow(new IllegalArgumentException());
    when(execution.getActivity()).thenReturn(updateRoom);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> updateRoomExecutor.execute(execution));
    verify(execution).bdk();
    verify(execution).getActivity();
  }

  /**
   * Test {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link UpdateRoom} (default constructor) RoomDescription is {@code Activity}.
   * </ul>
   *
   * <p>Method under test: {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given UpdateRoom (default constructor) RoomDescription is 'Activity'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UpdateRoomExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenUpdateRoomRoomDescriptionIsActivity() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();
    updateRoom.setRoomDescription("Activity");

    ActivityExecutorContext<UpdateRoom> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenThrow(new IllegalArgumentException());
    when(execution.getActivity()).thenReturn(updateRoom);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> updateRoomExecutor.execute(execution));
    verify(execution).bdk();
    verify(execution).getActivity();
  }

  /**
   * Test {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link UpdateRoom} (default constructor) RoomName is {@code Activity}.
   * </ul>
   *
   * <p>Method under test: {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given UpdateRoom (default constructor) RoomName is 'Activity'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UpdateRoomExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenUpdateRoomRoomNameIsActivity() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();
    updateRoom.setRoomName("Activity");

    ActivityExecutorContext<UpdateRoom> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenThrow(new IllegalArgumentException());
    when(execution.getActivity()).thenReturn(updateRoom);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> updateRoomExecutor.execute(execution));
    verify(execution).bdk();
    verify(execution).getActivity();
  }

  /**
   * Test {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>When {@link ActivityExecutorContext} {@link ActivityExecutorContext#getActivity()} throw
   *       {@link IllegalArgumentException#IllegalArgumentException()}.
   * </ul>
   *
   * <p>Method under test: {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); when ActivityExecutorContext getActivity() throw IllegalArgumentException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UpdateRoomExecutor.execute(ActivityExecutorContext)"})
  void testExecute_whenActivityExecutorContextGetActivityThrowIllegalArgumentException() {
    // Arrange
    ActivityExecutorContext<UpdateRoom> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenThrow(new IllegalArgumentException());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> updateRoomExecutor.execute(execution));
    verify(execution).getActivity();
  }

  /**
   * Test {@link UpdateRoomExecutor#doOboWithCache(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link UpdateRoomExecutor#doOboWithCache(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test doOboWithCache(ActivityExecutorContext); then throw IllegalArgumentException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "com.symphony.bdk.gen.api.model.V3RoomDetail UpdateRoomExecutor.doOboWithCache(ActivityExecutorContext)"
  })
  void testDoOboWithCache_thenThrowIllegalArgumentException() {
    // Arrange
    ActivityExecutorContext<UpdateRoom> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenThrow(new IllegalArgumentException());

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class, () -> updateRoomExecutor.doOboWithCache(execution));
    verify(execution).getActivity();
  }

  /**
   * Test {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link UpdateRoom} with no fields set; then calls getRoomInfo and setOutputVariable.
   * </ul>
   *
   * <p>Method under test: {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given UpdateRoom with no fields set; then calls getRoomInfo and setOutputVariable")
  @MethodsUnderTest({"void UpdateRoomExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenUpdateRoomNoFields_thenCallsGetRoomInfoAndSetsOutput() {
    // Arrange
    UpdateRoomExecutor executor = new UpdateRoomExecutor();

    V3RoomDetail roomDetail = new V3RoomDetail();
    StreamService streamService = mock(StreamService.class);
    when(streamService.getRoomInfo(Mockito.<String>any())).thenReturn(roomDetail);

    BdkGateway bdk = mock(BdkGateway.class);
    when(bdk.streams()).thenReturn(streamService);

    UpdateRoom updateRoom = new UpdateRoom();
    updateRoom.setStreamId("!streamId");

    ActivityExecutorContext<UpdateRoom> execution = mock(ActivityExecutorContext.class);
    doNothing().when(execution).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(execution.getActivity()).thenReturn(updateRoom);
    when(execution.bdk()).thenReturn(bdk);

    // Act
    executor.execute(execution);

    // Assert
    verify(streamService).getRoomInfo("!streamId");
    verify(streamService, never()).updateRoom(Mockito.<String>any(), Mockito.<V3RoomAttributes>any());
    verify(execution).setOutputVariable(eq("room"), eq(roomDetail));
  }

  /**
   * Test {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link UpdateRoom} with roomName set; then calls updateRoom and getRoomInfo.
   * </ul>
   *
   * <p>Method under test: {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given UpdateRoom with roomName set; then calls updateRoom and getRoomInfo")
  @MethodsUnderTest({"void UpdateRoomExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenUpdateRoomWithRoomName_thenCallsUpdateRoomAndGetRoomInfo() {
    // Arrange
    UpdateRoomExecutor executor = new UpdateRoomExecutor();

    V3RoomDetail roomDetail = new V3RoomDetail();
    StreamService streamService = mock(StreamService.class);
    when(streamService.getRoomInfo(Mockito.<String>any())).thenReturn(roomDetail);

    BdkGateway bdk = mock(BdkGateway.class);
    when(bdk.streams()).thenReturn(streamService);

    UpdateRoom updateRoom = new UpdateRoom();
    updateRoom.setStreamId("!streamId");
    updateRoom.setRoomName("My Room");

    ActivityExecutorContext<UpdateRoom> execution = mock(ActivityExecutorContext.class);
    doNothing().when(execution).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(execution.getActivity()).thenReturn(updateRoom);
    when(execution.bdk()).thenReturn(bdk);

    // Act
    executor.execute(execution);

    // Assert
    verify(streamService).updateRoom(eq("!streamId"), isA(V3RoomAttributes.class));
    verify(streamService).getRoomInfo("!streamId");
    verify(execution).setOutputVariable(eq("room"), eq(roomDetail));
  }

  /**
   * Test {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link UpdateRoom} with keywords set; then toAttributes maps keywords to RoomTags.
   * </ul>
   *
   * <p>Method under test: {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given UpdateRoom with keywords set; then toAttributes maps keywords to RoomTags")
  @MethodsUnderTest({"void UpdateRoomExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenUpdateRoomWithKeywords_thenMapsKeywords() {
    // Arrange
    UpdateRoomExecutor executor = new UpdateRoomExecutor();

    V3RoomDetail roomDetail = new V3RoomDetail();
    StreamService streamService = mock(StreamService.class);
    when(streamService.getRoomInfo(Mockito.<String>any())).thenReturn(roomDetail);

    BdkGateway bdk = mock(BdkGateway.class);
    when(bdk.streams()).thenReturn(streamService);

    Map<String, String> keywords = new HashMap<>();
    keywords.put("key1", "value1");

    UpdateRoom updateRoom = new UpdateRoom();
    updateRoom.setStreamId("!streamId");
    updateRoom.setKeywords(keywords);

    ActivityExecutorContext<UpdateRoom> execution = mock(ActivityExecutorContext.class);
    doNothing().when(execution).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(execution.getActivity()).thenReturn(updateRoom);
    when(execution.bdk()).thenReturn(bdk);

    // Act
    executor.execute(execution);

    // Assert
    verify(streamService).updateRoom(eq("!streamId"), isA(V3RoomAttributes.class));
    verify(streamService).getRoomInfo("!streamId");
  }

  /**
   * Test {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link UpdateRoom} with active set; then calls setRoomActive and getRoomInfo.
   * </ul>
   *
   * <p>Method under test: {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given UpdateRoom with active set; then calls setRoomActive and getRoomInfo")
  @MethodsUnderTest({"void UpdateRoomExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenUpdateRoomWithActive_thenCallsSetRoomActiveAndGetRoomInfo() {
    // Arrange
    UpdateRoomExecutor executor = new UpdateRoomExecutor();

    V3RoomDetail roomDetail = new V3RoomDetail();
    StreamService streamService = mock(StreamService.class);
    when(streamService.getRoomInfo(Mockito.<String>any())).thenReturn(roomDetail);
    when(streamService.setRoomActive(Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(null);

    BdkGateway bdk = mock(BdkGateway.class);
    when(bdk.streams()).thenReturn(streamService);

    UpdateRoom updateRoom = new UpdateRoom();
    updateRoom.setStreamId("!streamId");
    updateRoom.setActive(true);

    ActivityExecutorContext<UpdateRoom> execution = mock(ActivityExecutorContext.class);
    doNothing().when(execution).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(execution.getActivity()).thenReturn(updateRoom);
    when(execution.bdk()).thenReturn(bdk);

    // Act
    executor.execute(execution);

    // Assert
    verify(streamService).setRoomActive("!streamId", true);
    verify(streamService).getRoomInfo("!streamId");
    verify(execution).setOutputVariable(eq("room"), eq(roomDetail));
  }

  /**
   * Test {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given OBO with userId; then calls doOboWithCache and sets output.
   * </ul>
   *
   * <p>Method under test: {@link UpdateRoomExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given OBO with userId; then calls doOboWithCache and sets output")
  @MethodsUnderTest({"void UpdateRoomExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenOboWithUserId_thenCallsDoOboWithCacheAndSetsOutput() {
    // Arrange
    UpdateRoomExecutor executor = new UpdateRoomExecutor();

    V3RoomDetail roomDetail = new V3RoomDetail();
    StreamService oboStreamService = mock(StreamService.class);
    when(oboStreamService.getRoomInfo(Mockito.<String>any())).thenReturn(roomDetail);

    OboServices oboServices = mock(OboServices.class);
    when(oboServices.streams()).thenReturn(oboStreamService);

    AuthSession authSession = mock(AuthSession.class);

    BdkGateway bdk = mock(BdkGateway.class);
    when(bdk.obo(Mockito.<Long>any())).thenReturn(authSession);
    when(bdk.obo(Mockito.<AuthSession>any())).thenReturn(oboServices);

    Obo obo = new Obo();
    obo.setUserId(42L);

    UpdateRoom updateRoom = new UpdateRoom();
    updateRoom.setStreamId("!streamId");
    updateRoom.setObo(obo);

    ActivityExecutorContext<UpdateRoom> execution = mock(ActivityExecutorContext.class);
    doNothing().when(execution).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(execution.getActivity()).thenReturn(updateRoom);
    when(execution.bdk()).thenReturn(bdk);

    // Act
    executor.execute(execution);

    // Assert
    verify(oboStreamService).getRoomInfo("!streamId");
    verify(execution).setOutputVariable(eq("room"), eq(roomDetail));
  }

  /**
   * Test {@link UpdateRoomExecutor#doOboWithCache(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given OBO with username and no fields to update; then calls OBO getRoomInfo.
   * </ul>
   *
   * <p>Method under test: {@link UpdateRoomExecutor#doOboWithCache(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test doOboWithCache(ActivityExecutorContext); given OBO with username and no fields; then calls OBO getRoomInfo")
  @MethodsUnderTest({
    "com.symphony.bdk.gen.api.model.V3RoomDetail UpdateRoomExecutor.doOboWithCache(ActivityExecutorContext)"
  })
  void testDoOboWithCache_givenUsernameAndNoFields_thenCallsOboGetRoomInfo() throws Exception {
    // Arrange
    UpdateRoomExecutor executor = new UpdateRoomExecutor();

    V3RoomDetail roomDetail = new V3RoomDetail();
    StreamService oboStreamService = mock(StreamService.class);
    when(oboStreamService.getRoomInfo(Mockito.<String>any())).thenReturn(roomDetail);

    OboServices oboServices = mock(OboServices.class);
    when(oboServices.streams()).thenReturn(oboStreamService);

    AuthSession authSession = mock(AuthSession.class);

    BdkGateway bdk = mock(BdkGateway.class);
    when(bdk.obo(Mockito.<String>any())).thenReturn(authSession);
    when(bdk.obo(Mockito.<AuthSession>any())).thenReturn(oboServices);

    Obo obo = new Obo();
    obo.setUsername("oboUser");

    UpdateRoom updateRoom = new UpdateRoom();
    updateRoom.setStreamId("!streamId");
    updateRoom.setObo(obo);

    ActivityExecutorContext<UpdateRoom> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenReturn(updateRoom);
    when(execution.bdk()).thenReturn(bdk);

    // Act
    V3RoomDetail result = executor.doOboWithCache(execution);

    // Assert
    verify(oboStreamService).getRoomInfo("!streamId");
    verify(oboStreamService, never()).updateRoom(Mockito.<String>any(), Mockito.<V3RoomAttributes>any());
    org.assertj.core.api.Assertions.assertThat(result).isEqualTo(roomDetail);
  }

  /**
   * Test {@link UpdateRoomExecutor#doOboWithCache(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given OBO with username and roomName set; then calls OBO updateRoom and getRoomInfo.
   * </ul>
   *
   * <p>Method under test: {@link UpdateRoomExecutor#doOboWithCache(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test doOboWithCache(ActivityExecutorContext); given OBO with username and roomName set; then calls OBO updateRoom and getRoomInfo")
  @MethodsUnderTest({
    "com.symphony.bdk.gen.api.model.V3RoomDetail UpdateRoomExecutor.doOboWithCache(ActivityExecutorContext)"
  })
  void testDoOboWithCache_givenUsernameAndRoomName_thenCallsOboUpdateRoomAndGetRoomInfo()
      throws Exception {
    // Arrange
    UpdateRoomExecutor executor = new UpdateRoomExecutor();

    V3RoomDetail roomDetail = new V3RoomDetail();
    StreamService oboStreamService = mock(StreamService.class);
    when(oboStreamService.getRoomInfo(Mockito.<String>any())).thenReturn(roomDetail);

    OboServices oboServices = mock(OboServices.class);
    when(oboServices.streams()).thenReturn(oboStreamService);

    AuthSession authSession = mock(AuthSession.class);

    BdkGateway bdk = mock(BdkGateway.class);
    when(bdk.obo(Mockito.<String>any())).thenReturn(authSession);
    when(bdk.obo(Mockito.<AuthSession>any())).thenReturn(oboServices);

    Obo obo = new Obo();
    obo.setUsername("oboUser");

    UpdateRoom updateRoom = new UpdateRoom();
    updateRoom.setStreamId("!streamId");
    updateRoom.setRoomName("OBO Room");
    updateRoom.setObo(obo);

    ActivityExecutorContext<UpdateRoom> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenReturn(updateRoom);
    when(execution.bdk()).thenReturn(bdk);

    // Act
    V3RoomDetail result = executor.doOboWithCache(execution);

    // Assert
    verify(oboStreamService).updateRoom(eq("!streamId"), isA(V3RoomAttributes.class));
    verify(oboStreamService).getRoomInfo("!streamId");
    org.assertj.core.api.Assertions.assertThat(result).isEqualTo(roomDetail);
  }

  /**
   * Test {@link UpdateRoomExecutor#doOboWithCache(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given OBO with username and active set; then throws IllegalArgumentException.
   * </ul>
   *
   * <p>Method under test: {@link UpdateRoomExecutor#doOboWithCache(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test doOboWithCache(ActivityExecutorContext); given OBO with username and active set; then throws IllegalArgumentException")
  @MethodsUnderTest({
    "com.symphony.bdk.gen.api.model.V3RoomDetail UpdateRoomExecutor.doOboWithCache(ActivityExecutorContext)"
  })
  void testDoOboWithCache_givenUsernameAndActiveSet_thenThrowsIllegalArgumentException() {
    // Arrange
    UpdateRoomExecutor executor = new UpdateRoomExecutor();

    AuthSession authSession = mock(AuthSession.class);

    BdkGateway bdk = mock(BdkGateway.class);
    when(bdk.obo(Mockito.<String>any())).thenReturn(authSession);

    Obo obo = new Obo();
    obo.setUsername("oboUser");

    UpdateRoom updateRoom = new UpdateRoom();
    updateRoom.setStreamId("!streamId");
    updateRoom.setActive(true);
    updateRoom.setObo(obo);
    updateRoom.setId("activity-id");

    ActivityExecutorContext<UpdateRoom> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenReturn(updateRoom);
    when(execution.bdk()).thenReturn(bdk);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> executor.doOboWithCache(execution));
  }
}
