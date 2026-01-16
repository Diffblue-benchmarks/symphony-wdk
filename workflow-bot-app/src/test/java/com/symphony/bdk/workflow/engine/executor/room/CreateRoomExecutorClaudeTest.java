package com.symphony.bdk.workflow.engine.executor.room;

import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.gen.api.model.RoomSystemInfo;
import com.symphony.bdk.gen.api.model.Stream;
import com.symphony.bdk.gen.api.model.V3RoomAttributes;
import com.symphony.bdk.gen.api.model.V3RoomDetail;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.room.CreateRoom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateRoomExecutorClaudeTest {

  private CreateRoomExecutor executor;
  private ActivityExecutorContext<CreateRoom> context;
  private CreateRoom activity;
  private BdkGateway bdkGateway;
  private StreamService streamService;
  private OboServices oboServices;
  private AuthSession authSession;
  private StreamService oboStreamService;

  @BeforeEach
  void setUp() {
    executor = new CreateRoomExecutor();
    context = mock(ActivityExecutorContext.class);
    activity = new CreateRoom();
    bdkGateway = mock(BdkGateway.class);
    streamService = mock(StreamService.class);
    oboServices = mock(OboServices.class);
    authSession = mock(AuthSession.class);
    oboStreamService = mock(StreamService.class);

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.streams()).thenReturn(streamService);
  }

  // Tests for constructor

  @Test
  void constructor_shouldCreateInstanceSuccessfully() {
    // When: Creating a new instance
    CreateRoomExecutor newExecutor = new CreateRoomExecutor();

    // Then: Instance should be created successfully
    assertThat(newExecutor).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance
    CreateRoomExecutor newExecutor = new CreateRoomExecutor();

    // Then: Instance should be of correct type
    assertThat(newExecutor).isInstanceOf(CreateRoomExecutor.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating multiple instances
    CreateRoomExecutor executor1 = new CreateRoomExecutor();
    CreateRoomExecutor executor2 = new CreateRoomExecutor();

    // Then: Each instance should be distinct
    assertThat(executor1).isNotSameAs(executor2);
  }

  @Test
  void constructor_shouldNotThrowException() {
    // When/Then: Constructor should not throw exception
    assertThatCode(() -> new CreateRoomExecutor())
        .doesNotThrowAnyException();
  }

  // Tests for execute method - creating room with attributes only (no users)

  @Test
  void execute_withRoomAttributesOnlyNoObo_shouldCreateRoomAndSetOutputVariable() {
    // Given: Activity with room name only (no users)
    String roomName = "Test Room";
    activity.setRoomName(roomName);
    activity.setUserIds(null);
    activity.setObo(null);

    V3RoomDetail roomDetail = createV3RoomDetail("roomId123");
    when(streamService.create(any(V3RoomAttributes.class))).thenReturn(roomDetail);

    // When: Execute is called
    executor.execute(context);

    // Then: Should create room with attributes and set output variable
    ArgumentCaptor<V3RoomAttributes> attributesCaptor = ArgumentCaptor.forClass(V3RoomAttributes.class);
    verify(streamService).create(attributesCaptor.capture());
    V3RoomAttributes capturedAttributes = attributesCaptor.getValue();
    assertThat(capturedAttributes.getName()).isEqualTo(roomName);
    verify(context).setOutputVariable("roomId", "roomId123");
  }

  @Test
  void execute_withFullRoomAttributesNoObo_shouldCreateRoomWithAllAttributes() {
    // Given: Activity with full room attributes
    String roomName = "Test Room";
    String description = "Test Description";
    activity.setRoomName(roomName);
    activity.setRoomDescription(description);
    activity.setIsPublic(true);
    activity.setViewHistory(true);
    activity.setDiscoverable(false);
    activity.setReadOnly(false);
    activity.setCopyProtected(true);
    activity.setCrossPod(false);
    activity.setMultilateralRoom(true);
    activity.setMembersCanInvite(false);
    activity.setSubType("MEETING");
    activity.setUserIds(null);
    activity.setObo(null);

    V3RoomDetail roomDetail = createV3RoomDetail("roomId456");
    when(streamService.create(any(V3RoomAttributes.class))).thenReturn(roomDetail);

    // When: Execute is called
    executor.execute(context);

    // Then: Should create room with all attributes
    ArgumentCaptor<V3RoomAttributes> attributesCaptor = ArgumentCaptor.forClass(V3RoomAttributes.class);
    verify(streamService).create(attributesCaptor.capture());
    V3RoomAttributes capturedAttributes = attributesCaptor.getValue();
    assertThat(capturedAttributes.getName()).isEqualTo(roomName);
    assertThat(capturedAttributes.getDescription()).isEqualTo(description);
    assertThat(capturedAttributes.getPublic()).isTrue();
    assertThat(capturedAttributes.getViewHistory()).isTrue();
    assertThat(capturedAttributes.getDiscoverable()).isFalse();
    assertThat(capturedAttributes.getReadOnly()).isFalse();
    assertThat(capturedAttributes.getCopyProtected()).isTrue();
    assertThat(capturedAttributes.getCrossPod()).isFalse();
    assertThat(capturedAttributes.getMultiLateralRoom()).isTrue();
    assertThat(capturedAttributes.getMembersCanInvite()).isFalse();
    assertThat(capturedAttributes.getSubType()).isEqualTo("MEETING");
    verify(context).setOutputVariable("roomId", "roomId456");
  }

  @Test
  void execute_withKeywordsNoObo_shouldCreateRoomWithKeywords() {
    // Given: Activity with keywords
    String roomName = "Test Room";
    Map<String, String> keywords = new HashMap<>();
    keywords.put("topic", "technology");
    keywords.put("region", "US");
    activity.setRoomName(roomName);
    activity.setKeywords(keywords);
    activity.setUserIds(null);
    activity.setObo(null);

    V3RoomDetail roomDetail = createV3RoomDetail("roomId789");
    when(streamService.create(any(V3RoomAttributes.class))).thenReturn(roomDetail);

    // When: Execute is called
    executor.execute(context);

    // Then: Should create room with keywords
    ArgumentCaptor<V3RoomAttributes> attributesCaptor = ArgumentCaptor.forClass(V3RoomAttributes.class);
    verify(streamService).create(attributesCaptor.capture());
    V3RoomAttributes capturedAttributes = attributesCaptor.getValue();
    assertThat(capturedAttributes.getKeywords()).hasSize(2);
    assertThat(capturedAttributes.getKeywords().get(0).getKey()).isIn("topic", "region");
    assertThat(capturedAttributes.getKeywords().get(1).getKey()).isIn("topic", "region");
    verify(context).setOutputVariable("roomId", "roomId789");
  }

  // Tests for execute method - creating MIM (Multi-party Instant Message) with users only

  @Test
  void execute_withUsersOnlyNoObo_shouldCreateMIM() {
    // Given: Activity with users only (no name, no description)
    List<Long> userIds = Arrays.asList(111L, 222L, 333L);
    activity.setUserIds(userIds);
    activity.setRoomName(null);
    activity.setRoomDescription(null);
    activity.setObo(null);

    Stream stream = new Stream();
    stream.setId("mimId123");
    when(streamService.create(userIds)).thenReturn(stream);

    // When: Execute is called
    executor.execute(context);

    // Then: Should create MIM with users
    verify(streamService).create(userIds);
    verify(context).setOutputVariable("roomId", "mimId123");
  }

  @Test
  void execute_withUsersAndEmptyNameNoObo_shouldCreateMIM() {
    // Given: Activity with users and empty name
    List<Long> userIds = Arrays.asList(111L, 222L);
    activity.setUserIds(userIds);
    activity.setRoomName("");
    activity.setRoomDescription(null);
    activity.setObo(null);

    Stream stream = new Stream();
    stream.setId("mimId456");
    when(streamService.create(userIds)).thenReturn(stream);

    // When: Execute is called
    executor.execute(context);

    // Then: Should create MIM (empty string is treated as no name)
    verify(streamService).create(userIds);
    verify(context).setOutputVariable("roomId", "mimId456");
  }

  // Tests for execute method - creating room with users, name, and description

  @Test
  void execute_withUsersNameAndDescriptionNoObo_shouldCreateRoomAndAddMembers() {
    // Given: Activity with users, name, and description
    List<Long> userIds = Arrays.asList(111L, 222L, 333L);
    String roomName = "Team Room";
    String description = "Team collaboration space";
    activity.setUserIds(userIds);
    activity.setRoomName(roomName);
    activity.setRoomDescription(description);
    activity.setObo(null);

    V3RoomDetail roomDetail = createV3RoomDetail("roomId999");
    when(streamService.create(any(V3RoomAttributes.class))).thenReturn(roomDetail);

    // When: Execute is called
    executor.execute(context);

    // Then: Should create room with attributes and add all members
    ArgumentCaptor<V3RoomAttributes> attributesCaptor = ArgumentCaptor.forClass(V3RoomAttributes.class);
    verify(streamService).create(attributesCaptor.capture());
    V3RoomAttributes capturedAttributes = attributesCaptor.getValue();
    assertThat(capturedAttributes.getName()).isEqualTo(roomName);
    assertThat(capturedAttributes.getDescription()).isEqualTo(description);

    verify(streamService).addMemberToRoom(111L, "roomId999");
    verify(streamService).addMemberToRoom(222L, "roomId999");
    verify(streamService).addMemberToRoom(333L, "roomId999");
    verify(context).setOutputVariable("roomId", "roomId999");
  }

  @Test
  void execute_withUsersNameAndDescriptionAndKeywordsNoObo_shouldCreateRoomWithAllDetails() {
    // Given: Activity with users, name, description, and keywords
    List<Long> userIds = Arrays.asList(111L, 222L);
    String roomName = "Project Room";
    String description = "Project planning";
    Map<String, String> keywords = new HashMap<>();
    keywords.put("project", "alpha");
    activity.setUserIds(userIds);
    activity.setRoomName(roomName);
    activity.setRoomDescription(description);
    activity.setKeywords(keywords);
    activity.setObo(null);

    V3RoomDetail roomDetail = createV3RoomDetail("roomId111");
    when(streamService.create(any(V3RoomAttributes.class))).thenReturn(roomDetail);

    // When: Execute is called
    executor.execute(context);

    // Then: Should create room with all details and add members
    ArgumentCaptor<V3RoomAttributes> attributesCaptor = ArgumentCaptor.forClass(V3RoomAttributes.class);
    verify(streamService).create(attributesCaptor.capture());
    V3RoomAttributes capturedAttributes = attributesCaptor.getValue();
    assertThat(capturedAttributes.getName()).isEqualTo(roomName);
    assertThat(capturedAttributes.getDescription()).isEqualTo(description);
    assertThat(capturedAttributes.getKeywords()).hasSize(1);

    verify(streamService).addMemberToRoom(111L, "roomId111");
    verify(streamService).addMemberToRoom(222L, "roomId111");
    verify(context).setOutputVariable("roomId", "roomId111");
  }

  // Tests for execute method - OBO scenarios

  @Test
  void execute_withObo_shouldCallDoOboWithCache() {
    // Given: Activity with OBO username
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setObo(obo);
    activity.setRoomName("Test Room");

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    V3RoomDetail roomDetail = createV3RoomDetail("oboRoomId123");
    when(oboStreamService.create(any(V3RoomAttributes.class))).thenReturn(roomDetail);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use OBO services
    verify(bdkGateway).obo(oboUsername);
    verify(oboStreamService).create(any(V3RoomAttributes.class));
    verify(streamService, never()).create(any(V3RoomAttributes.class));
    verify(context).setOutputVariable("roomId", "oboRoomId123");
  }

  @Test
  void execute_withOboUserId_shouldUseOboServices() {
    // Given: Activity with OBO user ID
    Long oboUserId = 987654321L;
    Obo obo = new Obo();
    obo.setUsername(null);
    obo.setUserId(oboUserId);
    activity.setObo(obo);
    activity.setRoomName("Test Room");

    when(bdkGateway.obo(oboUserId)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    V3RoomDetail roomDetail = createV3RoomDetail("oboRoomId456");
    when(oboStreamService.create(any(V3RoomAttributes.class))).thenReturn(roomDetail);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use OBO services with user ID
    verify(bdkGateway).obo(oboUserId);
    verify(oboStreamService).create(any(V3RoomAttributes.class));
    verify(context).setOutputVariable("roomId", "oboRoomId456");
  }

  @Test
  void execute_withEmptyOboObject_shouldUseNonOboPath() {
    // Given: Activity with empty OBO (no username or userId)
    Obo emptyObo = new Obo();
    emptyObo.setUsername(null);
    emptyObo.setUserId(null);
    activity.setObo(emptyObo);
    activity.setRoomName("Test Room");

    V3RoomDetail roomDetail = createV3RoomDetail("roomId000");
    when(streamService.create(any(V3RoomAttributes.class))).thenReturn(roomDetail);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use non-OBO path
    verify(streamService).create(any(V3RoomAttributes.class));
    verify(bdkGateway, never()).obo((String) null);
    verify(bdkGateway, never()).obo((Long) null);
    verify(context).setOutputVariable("roomId", "roomId000");
  }

  // Tests for execute method - edge cases

  @Test
  void execute_withEmptyUserList_shouldCreateRoomWithoutMembers() {
    // Given: Activity with empty user list but with name
    List<Long> emptyUserIds = Collections.emptyList();
    String roomName = "Empty Room";
    activity.setUserIds(emptyUserIds);
    activity.setRoomName(roomName);
    activity.setObo(null);

    V3RoomDetail roomDetail = createV3RoomDetail("emptyRoomId");
    when(streamService.create(any(V3RoomAttributes.class))).thenReturn(roomDetail);

    // When: Execute is called
    executor.execute(context);

    // Then: Should create room without adding members
    verify(streamService).create(any(V3RoomAttributes.class));
    verify(streamService, never()).addMemberToRoom(org.mockito.ArgumentMatchers.anyLong(),
        org.mockito.ArgumentMatchers.anyString());
    verify(context).setOutputVariable("roomId", "emptyRoomId");
  }

  @Test
  void execute_withNullUserList_shouldCreateRoomWithoutMembers() {
    // Given: Activity with null user list but with name
    String roomName = "Null User Room";
    activity.setUserIds(null);
    activity.setRoomName(roomName);
    activity.setObo(null);

    V3RoomDetail roomDetail = createV3RoomDetail("nullUserRoomId");
    when(streamService.create(any(V3RoomAttributes.class))).thenReturn(roomDetail);

    // When: Execute is called
    executor.execute(context);

    // Then: Should create room without adding members
    verify(streamService).create(any(V3RoomAttributes.class));
    verify(streamService, never()).addMemberToRoom(org.mockito.ArgumentMatchers.anyLong(),
        org.mockito.ArgumentMatchers.anyString());
    verify(context).setOutputVariable("roomId", "nullUserRoomId");
  }

  @Test
  void execute_withUsersAndNameButNoDescription_shouldCreateMIM() {
    // Given: Activity with users and name but no description
    List<Long> userIds = Arrays.asList(111L, 222L);
    String roomName = "Test Room";
    activity.setUserIds(userIds);
    activity.setRoomName(roomName);
    activity.setRoomDescription(null);
    activity.setObo(null);

    Stream stream = new Stream();
    stream.setId("mimIdNameOnly");
    when(streamService.create(userIds)).thenReturn(stream);

    // When: Execute is called
    executor.execute(context);

    // Then: Should create MIM because description is null
    verify(streamService).create(userIds);
    verify(streamService, never()).create(any(V3RoomAttributes.class));
    verify(context).setOutputVariable("roomId", "mimIdNameOnly");
  }

  @Test
  void execute_withUsersAndDescriptionButNoName_shouldCreateMIM() {
    // Given: Activity with users and description but no name
    List<Long> userIds = Arrays.asList(111L, 222L);
    String description = "Test Description";
    activity.setUserIds(userIds);
    activity.setRoomName(null);
    activity.setRoomDescription(description);
    activity.setObo(null);

    Stream stream = new Stream();
    stream.setId("mimIdDescOnly");
    when(streamService.create(userIds)).thenReturn(stream);

    // When: Execute is called
    executor.execute(context);

    // Then: Should create MIM because name is null
    verify(streamService).create(userIds);
    verify(streamService, never()).create(any(V3RoomAttributes.class));
    verify(context).setOutputVariable("roomId", "mimIdDescOnly");
  }

  @Test
  void execute_shouldNotThrowException() {
    // Given: Valid activity setup
    activity.setRoomName("Test Room");
    activity.setObo(null);

    V3RoomDetail roomDetail = createV3RoomDetail("roomIdNoThrow");
    when(streamService.create(any(V3RoomAttributes.class))).thenReturn(roomDetail);

    // When/Then: Execute should not throw exception
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();
  }

  @Test
  void execute_calledMultipleTimes_shouldWorkCorrectly() {
    // Given: Valid activity setup
    activity.setRoomName("Test Room");
    activity.setObo(null);

    V3RoomDetail roomDetail1 = createV3RoomDetail("roomId1");
    V3RoomDetail roomDetail2 = createV3RoomDetail("roomId2");
    when(streamService.create(any(V3RoomAttributes.class)))
        .thenReturn(roomDetail1)
        .thenReturn(roomDetail2);

    // When: Execute is called multiple times
    executor.execute(context);
    executor.execute(context);

    // Then: Should work correctly both times
    verify(streamService, times(2)).create(any(V3RoomAttributes.class));
    verify(context).setOutputVariable("roomId", "roomId1");
    verify(context).setOutputVariable("roomId", "roomId2");
  }

  // Tests for doOboWithCache method - room with attributes only

  @Test
  void doOboWithCache_withRoomAttributesOnly_shouldCreateRoomViaObo() {
    // Given: Activity with room name only
    String roomName = "OBO Room";
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setObo(obo);
    activity.setRoomName(roomName);
    activity.setUserIds(null);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    V3RoomDetail roomDetail = createV3RoomDetail("oboRoomAttrOnly");
    when(oboStreamService.create(any(V3RoomAttributes.class))).thenReturn(roomDetail);

    // When: doOboWithCache is called
    String result = executor.doOboWithCache(context);

    // Then: Should create room via OBO and return room ID
    assertThat(result).isEqualTo("oboRoomAttrOnly");
    ArgumentCaptor<V3RoomAttributes> attributesCaptor = ArgumentCaptor.forClass(V3RoomAttributes.class);
    verify(oboStreamService).create(attributesCaptor.capture());
    V3RoomAttributes capturedAttributes = attributesCaptor.getValue();
    assertThat(capturedAttributes.getName()).isEqualTo(roomName);
  }

  @Test
  void doOboWithCache_withFullRoomAttributes_shouldCreateRoomWithAllAttributes() {
    // Given: Activity with full room attributes
    String roomName = "OBO Full Room";
    String description = "OBO Description";
    Map<String, String> keywords = new HashMap<>();
    keywords.put("obo", "test");
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setObo(obo);
    activity.setRoomName(roomName);
    activity.setRoomDescription(description);
    activity.setKeywords(keywords);
    activity.setIsPublic(true);
    activity.setViewHistory(false);
    activity.setUserIds(null);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    V3RoomDetail roomDetail = createV3RoomDetail("oboRoomFull");
    when(oboStreamService.create(any(V3RoomAttributes.class))).thenReturn(roomDetail);

    // When: doOboWithCache is called
    String result = executor.doOboWithCache(context);

    // Then: Should create room with all attributes via OBO
    assertThat(result).isEqualTo("oboRoomFull");
    ArgumentCaptor<V3RoomAttributes> attributesCaptor = ArgumentCaptor.forClass(V3RoomAttributes.class);
    verify(oboStreamService).create(attributesCaptor.capture());
    V3RoomAttributes capturedAttributes = attributesCaptor.getValue();
    assertThat(capturedAttributes.getName()).isEqualTo(roomName);
    assertThat(capturedAttributes.getDescription()).isEqualTo(description);
    assertThat(capturedAttributes.getPublic()).isTrue();
    assertThat(capturedAttributes.getViewHistory()).isFalse();
    assertThat(capturedAttributes.getKeywords()).hasSize(1);
  }

  // Tests for doOboWithCache method - MIM with users only

  @Test
  void doOboWithCache_withUsersOnly_shouldCreateMIMViaObo() {
    // Given: Activity with users only (no name, no description)
    List<Long> userIds = Arrays.asList(111L, 222L, 333L);
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setObo(obo);
    activity.setUserIds(userIds);
    activity.setRoomName(null);
    activity.setRoomDescription(null);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    Stream stream = new Stream();
    stream.setId("oboMimId");
    when(oboStreamService.create(userIds)).thenReturn(stream);

    // When: doOboWithCache is called
    String result = executor.doOboWithCache(context);

    // Then: Should create MIM via OBO and return stream ID
    assertThat(result).isEqualTo("oboMimId");
    verify(oboStreamService).create(userIds);
    verify(oboStreamService, never()).create(any(V3RoomAttributes.class));
  }

  @Test
  void doOboWithCache_withUsersAndEmptyName_shouldCreateMIMViaObo() {
    // Given: Activity with users and empty name
    List<Long> userIds = Arrays.asList(111L, 222L);
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setObo(obo);
    activity.setUserIds(userIds);
    activity.setRoomName("");
    activity.setRoomDescription(null);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    Stream stream = new Stream();
    stream.setId("oboMimEmpty");
    when(oboStreamService.create(userIds)).thenReturn(stream);

    // When: doOboWithCache is called
    String result = executor.doOboWithCache(context);

    // Then: Should create MIM (empty string is treated as no name)
    assertThat(result).isEqualTo("oboMimEmpty");
    verify(oboStreamService).create(userIds);
  }

  // Tests for doOboWithCache method - room with users, name, and description

  @Test
  void doOboWithCache_withUsersNameAndDescription_shouldCreateRoomAndAddMembers() {
    // Given: Activity with users, name, and description
    List<Long> userIds = Arrays.asList(111L, 222L, 333L);
    String roomName = "OBO Team Room";
    String description = "OBO Team space";
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setObo(obo);
    activity.setUserIds(userIds);
    activity.setRoomName(roomName);
    activity.setRoomDescription(description);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    V3RoomDetail roomDetail = createV3RoomDetail("oboRoomWithMembers");
    when(oboStreamService.create(any(V3RoomAttributes.class))).thenReturn(roomDetail);

    // When: doOboWithCache is called
    String result = executor.doOboWithCache(context);

    // Then: Should create room via OBO and add all members
    assertThat(result).isEqualTo("oboRoomWithMembers");
    ArgumentCaptor<V3RoomAttributes> attributesCaptor = ArgumentCaptor.forClass(V3RoomAttributes.class);
    verify(oboStreamService).create(attributesCaptor.capture());
    V3RoomAttributes capturedAttributes = attributesCaptor.getValue();
    assertThat(capturedAttributes.getName()).isEqualTo(roomName);
    assertThat(capturedAttributes.getDescription()).isEqualTo(description);

    verify(oboStreamService).addMemberToRoom(111L, "oboRoomWithMembers");
    verify(oboStreamService).addMemberToRoom(222L, "oboRoomWithMembers");
    verify(oboStreamService).addMemberToRoom(333L, "oboRoomWithMembers");
  }

  @Test
  void doOboWithCache_withUsersNameDescriptionAndKeywords_shouldCreateRoomWithAllDetails() {
    // Given: Activity with all details
    List<Long> userIds = Arrays.asList(111L);
    String roomName = "OBO Complete Room";
    String description = "OBO Complete Description";
    Map<String, String> keywords = new HashMap<>();
    keywords.put("complete", "true");
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setObo(obo);
    activity.setUserIds(userIds);
    activity.setRoomName(roomName);
    activity.setRoomDescription(description);
    activity.setKeywords(keywords);
    activity.setIsPublic(false);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    V3RoomDetail roomDetail = createV3RoomDetail("oboCompleteRoom");
    when(oboStreamService.create(any(V3RoomAttributes.class))).thenReturn(roomDetail);

    // When: doOboWithCache is called
    String result = executor.doOboWithCache(context);

    // Then: Should create room with all details and add member
    assertThat(result).isEqualTo("oboCompleteRoom");
    ArgumentCaptor<V3RoomAttributes> attributesCaptor = ArgumentCaptor.forClass(V3RoomAttributes.class);
    verify(oboStreamService).create(attributesCaptor.capture());
    V3RoomAttributes capturedAttributes = attributesCaptor.getValue();
    assertThat(capturedAttributes.getName()).isEqualTo(roomName);
    assertThat(capturedAttributes.getDescription()).isEqualTo(description);
    assertThat(capturedAttributes.getKeywords()).hasSize(1);
    assertThat(capturedAttributes.getPublic()).isFalse();

    verify(oboStreamService).addMemberToRoom(111L, "oboCompleteRoom");
  }

  // Tests for doOboWithCache method - OBO with userId

  @Test
  void doOboWithCache_withOboUserId_shouldUseUserIdForAuth() {
    // Given: Activity with OBO user ID
    Long oboUserId = 987654321L;
    String roomName = "OBO UserID Room";
    Obo obo = new Obo();
    obo.setUsername(null);
    obo.setUserId(oboUserId);
    activity.setObo(obo);
    activity.setRoomName(roomName);
    activity.setUserIds(null);

    when(bdkGateway.obo(oboUserId)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    V3RoomDetail roomDetail = createV3RoomDetail("oboUserIdRoom");
    when(oboStreamService.create(any(V3RoomAttributes.class))).thenReturn(roomDetail);

    // When: doOboWithCache is called
    String result = executor.doOboWithCache(context);

    // Then: Should use user ID for OBO auth
    assertThat(result).isEqualTo("oboUserIdRoom");
    verify(bdkGateway).obo(oboUserId);
    verify(bdkGateway, never()).obo((String) null);
  }

  @Test
  void doOboWithCache_withBothOboUsernameAndUserId_shouldPreferUsername() {
    // Given: Activity with both OBO username and user ID
    String oboUsername = "obo.user";
    Long oboUserId = 987654321L;
    String roomName = "OBO Preference Room";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    obo.setUserId(oboUserId);
    activity.setObo(obo);
    activity.setRoomName(roomName);
    activity.setUserIds(null);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    V3RoomDetail roomDetail = createV3RoomDetail("oboPreferenceRoom");
    when(oboStreamService.create(any(V3RoomAttributes.class))).thenReturn(roomDetail);

    // When: doOboWithCache is called
    String result = executor.doOboWithCache(context);

    // Then: Should prefer username over user ID
    assertThat(result).isEqualTo("oboPreferenceRoom");
    verify(bdkGateway).obo(oboUsername);
    verify(bdkGateway, never()).obo(oboUserId);
  }

  // Tests for doOboWithCache method - edge cases

  @Test
  void doOboWithCache_withEmptyUserList_shouldCreateRoomWithoutMembers() {
    // Given: Activity with empty user list but with name and description
    List<Long> emptyUserIds = Collections.emptyList();
    String roomName = "OBO Empty Users Room";
    String description = "OBO Empty Description";
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setObo(obo);
    activity.setUserIds(emptyUserIds);
    activity.setRoomName(roomName);
    activity.setRoomDescription(description);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    V3RoomDetail roomDetail = createV3RoomDetail("oboEmptyUsers");
    when(oboStreamService.create(any(V3RoomAttributes.class))).thenReturn(roomDetail);

    // When: doOboWithCache is called
    String result = executor.doOboWithCache(context);

    // Then: Should create room without adding members
    assertThat(result).isEqualTo("oboEmptyUsers");
    verify(oboStreamService).create(any(V3RoomAttributes.class));
    verify(oboStreamService, never()).addMemberToRoom(org.mockito.ArgumentMatchers.anyLong(),
        org.mockito.ArgumentMatchers.anyString());
  }

  @Test
  void doOboWithCache_withNullUserList_shouldCreateRoomWithoutMembers() {
    // Given: Activity with null user list but with name
    String roomName = "OBO Null Users Room";
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setObo(obo);
    activity.setUserIds(null);
    activity.setRoomName(roomName);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    V3RoomDetail roomDetail = createV3RoomDetail("oboNullUsers");
    when(oboStreamService.create(any(V3RoomAttributes.class))).thenReturn(roomDetail);

    // When: doOboWithCache is called
    String result = executor.doOboWithCache(context);

    // Then: Should create room without adding members
    assertThat(result).isEqualTo("oboNullUsers");
    verify(oboStreamService).create(any(V3RoomAttributes.class));
    verify(oboStreamService, never()).addMemberToRoom(org.mockito.ArgumentMatchers.anyLong(),
        org.mockito.ArgumentMatchers.anyString());
  }

  @Test
  void doOboWithCache_withUsersAndNameButNoDescription_shouldCreateMIM() {
    // Given: Activity with users and name but no description
    List<Long> userIds = Arrays.asList(111L, 222L);
    String roomName = "OBO Name Only";
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setObo(obo);
    activity.setUserIds(userIds);
    activity.setRoomName(roomName);
    activity.setRoomDescription(null);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    Stream stream = new Stream();
    stream.setId("oboMimNameOnly");
    when(oboStreamService.create(userIds)).thenReturn(stream);

    // When: doOboWithCache is called
    String result = executor.doOboWithCache(context);

    // Then: Should create MIM because description is null
    assertThat(result).isEqualTo("oboMimNameOnly");
    verify(oboStreamService).create(userIds);
    verify(oboStreamService, never()).create(any(V3RoomAttributes.class));
  }

  @Test
  void doOboWithCache_withUsersAndDescriptionButNoName_shouldCreateMIM() {
    // Given: Activity with users and description but no name
    List<Long> userIds = Arrays.asList(111L, 222L);
    String description = "OBO Desc Only";
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setObo(obo);
    activity.setUserIds(userIds);
    activity.setRoomName(null);
    activity.setRoomDescription(description);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    Stream stream = new Stream();
    stream.setId("oboMimDescOnly");
    when(oboStreamService.create(userIds)).thenReturn(stream);

    // When: doOboWithCache is called
    String result = executor.doOboWithCache(context);

    // Then: Should create MIM because name is null
    assertThat(result).isEqualTo("oboMimDescOnly");
    verify(oboStreamService).create(userIds);
    verify(oboStreamService, never()).create(any(V3RoomAttributes.class));
  }

  @Test
  void doOboWithCache_shouldNotThrowException() {
    // Given: Valid activity with OBO
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setObo(obo);
    activity.setRoomName("Test Room");

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    V3RoomDetail roomDetail = createV3RoomDetail("oboNoThrow");
    when(oboStreamService.create(any(V3RoomAttributes.class))).thenReturn(roomDetail);

    // When/Then: doOboWithCache should not throw exception
    assertThatCode(() -> executor.doOboWithCache(context))
        .doesNotThrowAnyException();
  }

  @Test
  void doOboWithCache_calledMultipleTimes_shouldWorkCorrectly() {
    // Given: Valid activity with OBO
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setObo(obo);
    activity.setRoomName("Test Room");

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    V3RoomDetail roomDetail1 = createV3RoomDetail("oboRoom1");
    V3RoomDetail roomDetail2 = createV3RoomDetail("oboRoom2");
    when(oboStreamService.create(any(V3RoomAttributes.class)))
        .thenReturn(roomDetail1)
        .thenReturn(roomDetail2);

    // When: doOboWithCache is called multiple times
    String result1 = executor.doOboWithCache(context);
    String result2 = executor.doOboWithCache(context);

    // Then: Should work correctly both times
    assertThat(result1).isEqualTo("oboRoom1");
    assertThat(result2).isEqualTo("oboRoom2");
    verify(oboStreamService, times(2)).create(any(V3RoomAttributes.class));
  }

  // Helper method to create V3RoomDetail with room ID

  private V3RoomDetail createV3RoomDetail(String roomId) {
    V3RoomDetail roomDetail = new V3RoomDetail();
    RoomSystemInfo roomSystemInfo = new RoomSystemInfo();
    roomSystemInfo.setId(roomId);
    roomDetail.setRoomSystemInfo(roomSystemInfo);
    return roomDetail;
  }
}
