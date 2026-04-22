package com.symphony.bdk.workflow.engine.executor.room;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.auth.AuthenticatorFactory;
import com.symphony.bdk.core.config.model.BdkConfig;
import com.symphony.bdk.core.service.connection.ConnectionService;
import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.core.service.stream.OboStreamService;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.ext.group.SymphonyGroupService;
import com.symphony.bdk.gen.api.model.RoomSystemInfo;
import com.symphony.bdk.gen.api.model.Stream;
import com.symphony.bdk.gen.api.model.V3RoomAttributes;
import com.symphony.bdk.gen.api.model.V3RoomDetail;
import com.symphony.bdk.workflow.engine.SpringBdkGateway;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.room.CreateRoom;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CreateRoomExecutorDiffblueTest {
  /**
   * Test {@link CreateRoomExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link V3RoomDetail} (default constructor) roomSystemInfo {@link RoomSystemInfo}
   *       (default constructor).
   *   <li>Then calls {@link StreamService#create(V3RoomAttributes)}.
   * </ul>
   *
   * <p>Method under test: {@link CreateRoomExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given V3RoomDetail (default constructor) roomSystemInfo RoomSystemInfo (default constructor); then calls create(V3RoomAttributes)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CreateRoomExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenV3RoomDetailRoomSystemInfoRoomSystemInfo_thenCallsCreate() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    CreateRoomExecutor createRoomExecutor = new CreateRoomExecutor();

    V3RoomDetail v3RoomDetail = new V3RoomDetail();
    v3RoomDetail.roomSystemInfo(new RoomSystemInfo());

    StreamService streamService = mock(StreamService.class);
    when(streamService.create(Mockito.<V3RoomAttributes>any())).thenReturn(v3RoomDetail);
    SpringBdkGateway springBdkGateway =
        new SpringBdkGateway(
            mock(BdkConfig.class),
            mock(AuthenticatorFactory.class),
            mock(MessageService.class),
            streamService,
            mock(UserService.class),
            mock(ConnectionService.class),
            mock(SymphonyGroupService.class),
            mock(SessionService.class));

    ActivityExecutorContext<CreateRoom> execution = mock(ActivityExecutorContext.class);
    doNothing().when(execution).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(execution.bdk()).thenReturn(springBdkGateway);
    when(execution.getActivity()).thenReturn(new CreateRoom());

    // Act
    createRoomExecutor.execute(execution);

    // Assert
    verify(streamService).create(isA(V3RoomAttributes.class));
    verify(execution).bdk();
    verify(execution).getActivity();
    verify(execution).setOutputVariable(eq("roomId"), isNull());
  }

  /**
   * Test {@link CreateRoomExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link CreateRoom} with userIds, roomName, and roomDescription.
   *   <li>Then calls {@link StreamService#create(V3RoomAttributes)} and addMemberToRoom.
   * </ul>
   *
   * <p>Method under test: {@link CreateRoomExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given CreateRoom with userIds name and description; then creates room and adds members")
  @MethodsUnderTest({"void CreateRoomExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenCreateRoomWithUserIdsNameAndDescription_thenCreatesRoomAndAddsMembers() {
    // Arrange
    CreateRoomExecutor createRoomExecutor = new CreateRoomExecutor();

    RoomSystemInfo roomSystemInfo = new RoomSystemInfo();
    roomSystemInfo.setId("room-id-123");

    V3RoomDetail v3RoomDetail = new V3RoomDetail();
    v3RoomDetail.roomSystemInfo(roomSystemInfo);

    StreamService streamService = mock(StreamService.class);
    when(streamService.create(Mockito.<V3RoomAttributes>any())).thenReturn(v3RoomDetail);
    doNothing().when(streamService).addMemberToRoom(anyLong(), anyString());
    SpringBdkGateway springBdkGateway =
        new SpringBdkGateway(
            mock(BdkConfig.class),
            mock(AuthenticatorFactory.class),
            mock(MessageService.class),
            streamService,
            mock(UserService.class),
            mock(ConnectionService.class),
            mock(SymphonyGroupService.class),
            mock(SessionService.class));

    CreateRoom createRoom = new CreateRoom();
    createRoom.setUserIds(Arrays.asList(1L, 2L));
    createRoom.setRoomName("Test Room");
    createRoom.setRoomDescription("A test room description");

    ActivityExecutorContext<CreateRoom> execution = mock(ActivityExecutorContext.class);
    doNothing().when(execution).setOutputVariable(anyString(), any());
    when(execution.bdk()).thenReturn(springBdkGateway);
    when(execution.getActivity()).thenReturn(createRoom);

    // Act
    createRoomExecutor.execute(execution);

    // Assert
    verify(streamService).create(isA(V3RoomAttributes.class));
    verify(execution).setOutputVariable(eq("roomId"), eq("room-id-123"));
  }

  /**
   * Test {@link CreateRoomExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link CreateRoom} with userIds only (no name/description).
   *   <li>Then calls {@link StreamService#create(java.util.List)}.
   * </ul>
   *
   * <p>Method under test: {@link CreateRoomExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given CreateRoom with userIds only; then creates MIM stream")
  @MethodsUnderTest({"void CreateRoomExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenCreateRoomWithUserIdsOnly_thenCreatesMimStream() {
    // Arrange
    CreateRoomExecutor createRoomExecutor = new CreateRoomExecutor();

    Stream stream = new Stream();
    stream.setId("mim-stream-id");

    StreamService streamService = mock(StreamService.class);
    when(streamService.create(Mockito.<java.util.List<Long>>any())).thenReturn(stream);
    SpringBdkGateway springBdkGateway =
        new SpringBdkGateway(
            mock(BdkConfig.class),
            mock(AuthenticatorFactory.class),
            mock(MessageService.class),
            streamService,
            mock(UserService.class),
            mock(ConnectionService.class),
            mock(SymphonyGroupService.class),
            mock(SessionService.class));

    CreateRoom createRoom = new CreateRoom();
    createRoom.setUserIds(Arrays.asList(1L, 2L));

    ActivityExecutorContext<CreateRoom> execution = mock(ActivityExecutorContext.class);
    doNothing().when(execution).setOutputVariable(anyString(), any());
    when(execution.bdk()).thenReturn(springBdkGateway);
    when(execution.getActivity()).thenReturn(createRoom);

    // Act
    createRoomExecutor.execute(execution);

    // Assert
    verify(streamService).create(isA(java.util.List.class));
    verify(execution).setOutputVariable(eq("roomId"), eq("mim-stream-id"));
  }

  /**
   * Test {@link CreateRoomExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link CreateRoom} with keywords set.
   *   <li>Then keywords are added to room attributes.
   * </ul>
   *
   * <p>Method under test: {@link CreateRoomExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given CreateRoom with keywords; then keywords added to room attributes")
  @MethodsUnderTest({"void CreateRoomExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenCreateRoomWithKeywords_thenKeywordsAddedToRoomAttributes() {
    // Arrange
    CreateRoomExecutor createRoomExecutor = new CreateRoomExecutor();

    RoomSystemInfo roomSystemInfo = new RoomSystemInfo();
    roomSystemInfo.setId("room-with-keywords");

    V3RoomDetail v3RoomDetail = new V3RoomDetail();
    v3RoomDetail.roomSystemInfo(roomSystemInfo);

    StreamService streamService = mock(StreamService.class);
    when(streamService.create(Mockito.<V3RoomAttributes>any())).thenReturn(v3RoomDetail);
    SpringBdkGateway springBdkGateway =
        new SpringBdkGateway(
            mock(BdkConfig.class),
            mock(AuthenticatorFactory.class),
            mock(MessageService.class),
            streamService,
            mock(UserService.class),
            mock(ConnectionService.class),
            mock(SymphonyGroupService.class),
            mock(SessionService.class));

    Map<String, String> keywords = new HashMap<>();
    keywords.put("key1", "value1");
    keywords.put("key2", "value2");

    CreateRoom createRoom = new CreateRoom();
    createRoom.setRoomName("Keyword Room");
    createRoom.setKeywords(keywords);

    ActivityExecutorContext<CreateRoom> execution = mock(ActivityExecutorContext.class);
    doNothing().when(execution).setOutputVariable(anyString(), any());
    when(execution.bdk()).thenReturn(springBdkGateway);
    when(execution.getActivity()).thenReturn(createRoom);

    // Act
    createRoomExecutor.execute(execution);

    // Assert
    verify(streamService).create(isA(V3RoomAttributes.class));
    verify(execution).setOutputVariable(eq("roomId"), eq("room-with-keywords"));
  }

  /**
   * Test {@link CreateRoomExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link CreateRoom} with OBO user by username.
   *   <li>Then calls OBO streams create.
   * </ul>
   *
   * <p>Method under test: {@link CreateRoomExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given CreateRoom with OBO username; then delegates to doOboWithCache")
  @MethodsUnderTest({"void CreateRoomExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenCreateRoomWithOboUsername_thenDelegatesToDoOboWithCache() throws Exception {
    // Arrange
    CreateRoomExecutor createRoomExecutor = new CreateRoomExecutor();

    Obo obo = new Obo();
    obo.setUsername("oboUser");

    RoomSystemInfo roomSystemInfo = new RoomSystemInfo();
    roomSystemInfo.setId("obo-room-id");

    V3RoomDetail v3RoomDetail = new V3RoomDetail();
    v3RoomDetail.roomSystemInfo(roomSystemInfo);

    OboStreamService oboStreamService = mock(OboStreamService.class);
    when(oboStreamService.create(Mockito.<V3RoomAttributes>any())).thenReturn(v3RoomDetail);

    OboServices oboServices = mock(OboServices.class);
    when(oboServices.streams()).thenReturn(oboStreamService);

    AuthSession authSession = mock(AuthSession.class);

    BdkGateway bdkGateway = mock(BdkGateway.class);
    when(bdkGateway.obo("oboUser")).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);

    CreateRoom createRoom = new CreateRoom();
    createRoom.setObo(obo);

    ActivityExecutorContext<CreateRoom> execution = mock(ActivityExecutorContext.class);
    doNothing().when(execution).setOutputVariable(anyString(), any());
    when(execution.bdk()).thenReturn(bdkGateway);
    when(execution.getActivity()).thenReturn(createRoom);

    // Act
    createRoomExecutor.execute(execution);

    // Assert
    verify(oboStreamService).create(isA(V3RoomAttributes.class));
    verify(execution).setOutputVariable(eq("roomId"), eq("obo-room-id"));
  }

  /**
   * Test {@link CreateRoomExecutor#doOboWithCache(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given OBO with username, uids, name, and description.
   *   <li>Then creates room and adds members via OBO.
   * </ul>
   *
   * <p>Method under test: {@link CreateRoomExecutor#doOboWithCache(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test doOboWithCache(ActivityExecutorContext); given OBO username with uids name description; then creates room and adds members via OBO")
  @MethodsUnderTest({"java.lang.String CreateRoomExecutor.doOboWithCache(ActivityExecutorContext)"})
  void testDoOboWithCache_givenOboUsernameWithUidsNameDescription_thenCreatesRoomAndAddsMembersViaObo()
      throws Exception {
    // Arrange
    CreateRoomExecutor createRoomExecutor = new CreateRoomExecutor();

    Obo obo = new Obo();
    obo.setUsername("oboUser");

    RoomSystemInfo roomSystemInfo = new RoomSystemInfo();
    roomSystemInfo.setId("obo-room-members-id");

    V3RoomDetail v3RoomDetail = new V3RoomDetail();
    v3RoomDetail.roomSystemInfo(roomSystemInfo);

    OboStreamService oboStreamService = mock(OboStreamService.class);
    when(oboStreamService.create(Mockito.<V3RoomAttributes>any())).thenReturn(v3RoomDetail);
    doNothing().when(oboStreamService).addMemberToRoom(anyLong(), anyString());

    OboServices oboServices = mock(OboServices.class);
    when(oboServices.streams()).thenReturn(oboStreamService);

    AuthSession authSession = mock(AuthSession.class);

    BdkGateway bdkGateway = mock(BdkGateway.class);
    when(bdkGateway.obo("oboUser")).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);

    CreateRoom createRoom = new CreateRoom();
    createRoom.setObo(obo);
    createRoom.setUserIds(Arrays.asList(10L, 20L));
    createRoom.setRoomName("OBO Room");
    createRoom.setRoomDescription("OBO Room Description");

    ActivityExecutorContext<CreateRoom> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenReturn(bdkGateway);
    when(execution.getActivity()).thenReturn(createRoom);

    // Act
    String result = createRoomExecutor.doOboWithCache(execution);

    // Assert
    verify(oboStreamService).create(isA(V3RoomAttributes.class));
    verify(oboStreamService).addMemberToRoom(eq(10L), eq("obo-room-members-id"));
    verify(oboStreamService).addMemberToRoom(eq(20L), eq("obo-room-members-id"));
  }

  /**
   * Test {@link CreateRoomExecutor#doOboWithCache(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given OBO with username and uids only (no name/description).
   *   <li>Then creates MIM stream via OBO.
   * </ul>
   *
   * <p>Method under test: {@link CreateRoomExecutor#doOboWithCache(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test doOboWithCache(ActivityExecutorContext); given OBO username with uids only; then creates MIM stream via OBO")
  @MethodsUnderTest({"java.lang.String CreateRoomExecutor.doOboWithCache(ActivityExecutorContext)"})
  void testDoOboWithCache_givenOboUsernameWithUidsOnly_thenCreatesMimStreamViaObo()
      throws Exception {
    // Arrange
    CreateRoomExecutor createRoomExecutor = new CreateRoomExecutor();

    Obo obo = new Obo();
    obo.setUsername("oboUser");

    Stream stream = new Stream();
    stream.setId("obo-mim-id");

    OboStreamService oboStreamService = mock(OboStreamService.class);
    when(oboStreamService.create(Mockito.<java.util.List<Long>>any())).thenReturn(stream);

    OboServices oboServices = mock(OboServices.class);
    when(oboServices.streams()).thenReturn(oboStreamService);

    AuthSession authSession = mock(AuthSession.class);

    BdkGateway bdkGateway = mock(BdkGateway.class);
    when(bdkGateway.obo("oboUser")).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);

    CreateRoom createRoom = new CreateRoom();
    createRoom.setObo(obo);
    createRoom.setUserIds(Arrays.asList(10L, 20L));

    ActivityExecutorContext<CreateRoom> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenReturn(bdkGateway);
    when(execution.getActivity()).thenReturn(createRoom);

    // Act
    String result = createRoomExecutor.doOboWithCache(execution);

    // Assert
    verify(oboStreamService).create(isA(java.util.List.class));
  }

  /**
   * Test {@link CreateRoomExecutor#doOboWithCache(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given OBO with username and no uids.
   *   <li>Then creates room via OBO using room attributes.
   * </ul>
   *
   * <p>Method under test: {@link CreateRoomExecutor#doOboWithCache(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test doOboWithCache(ActivityExecutorContext); given OBO username with no uids; then creates room via OBO using attributes")
  @MethodsUnderTest({"java.lang.String CreateRoomExecutor.doOboWithCache(ActivityExecutorContext)"})
  void testDoOboWithCache_givenOboUsernameWithNoUids_thenCreatesRoomViaOboUsingAttributes()
      throws Exception {
    // Arrange
    CreateRoomExecutor createRoomExecutor = new CreateRoomExecutor();

    Obo obo = new Obo();
    obo.setUsername("oboUser");

    RoomSystemInfo roomSystemInfo = new RoomSystemInfo();
    roomSystemInfo.setId("obo-attr-room-id");

    V3RoomDetail v3RoomDetail = new V3RoomDetail();
    v3RoomDetail.roomSystemInfo(roomSystemInfo);

    OboStreamService oboStreamService = mock(OboStreamService.class);
    when(oboStreamService.create(Mockito.<V3RoomAttributes>any())).thenReturn(v3RoomDetail);

    OboServices oboServices = mock(OboServices.class);
    when(oboServices.streams()).thenReturn(oboStreamService);

    AuthSession authSession = mock(AuthSession.class);

    BdkGateway bdkGateway = mock(BdkGateway.class);
    when(bdkGateway.obo("oboUser")).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);

    CreateRoom createRoom = new CreateRoom();
    createRoom.setObo(obo);
    createRoom.setRoomName("OBO Attr Room");

    ActivityExecutorContext<CreateRoom> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenReturn(bdkGateway);
    when(execution.getActivity()).thenReturn(createRoom);

    // Act
    String result = createRoomExecutor.doOboWithCache(execution);

    // Assert
    verify(oboStreamService).create(isA(V3RoomAttributes.class));
  }

  /**
   * Test {@link CreateRoomExecutor#doOboWithCache(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given OBO with userId (not username).
   *   <li>Then creates room via OBO using userId.
   * </ul>
   *
   * <p>Method under test: {@link CreateRoomExecutor#doOboWithCache(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test doOboWithCache(ActivityExecutorContext); given OBO userId; then authenticates by userId")
  @MethodsUnderTest({"java.lang.String CreateRoomExecutor.doOboWithCache(ActivityExecutorContext)"})
  void testDoOboWithCache_givenOboUserId_thenAuthenticatesByUserId() throws Exception {
    // Arrange
    CreateRoomExecutor createRoomExecutor = new CreateRoomExecutor();

    Obo obo = new Obo();
    obo.setUserId(42L);

    RoomSystemInfo roomSystemInfo = new RoomSystemInfo();
    roomSystemInfo.setId("obo-userid-room-id");

    V3RoomDetail v3RoomDetail = new V3RoomDetail();
    v3RoomDetail.roomSystemInfo(roomSystemInfo);

    OboStreamService oboStreamService = mock(OboStreamService.class);
    when(oboStreamService.create(Mockito.<V3RoomAttributes>any())).thenReturn(v3RoomDetail);

    OboServices oboServices = mock(OboServices.class);
    when(oboServices.streams()).thenReturn(oboStreamService);

    AuthSession authSession = mock(AuthSession.class);

    BdkGateway bdkGateway = mock(BdkGateway.class);
    when(bdkGateway.obo(42L)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);

    CreateRoom createRoom = new CreateRoom();
    createRoom.setObo(obo);

    ActivityExecutorContext<CreateRoom> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenReturn(bdkGateway);
    when(execution.getActivity()).thenReturn(createRoom);

    // Act
    String result = createRoomExecutor.doOboWithCache(execution);

    // Assert
    verify(bdkGateway).obo(42L);
    verify(oboStreamService).create(isA(V3RoomAttributes.class));
  }
}
