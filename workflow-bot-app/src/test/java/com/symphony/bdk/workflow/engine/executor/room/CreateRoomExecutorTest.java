package com.symphony.bdk.workflow.engine.executor.room;

import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.stream.OboStreamService;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.gen.api.model.RoomSystemInfo;
import com.symphony.bdk.gen.api.model.Stream;
import com.symphony.bdk.gen.api.model.V3RoomAttributes;
import com.symphony.bdk.gen.api.model.V3RoomDetail;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.room.CreateRoom;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateRoomExecutorTest {

    @Mock
    private ActivityExecutorContext<CreateRoom> context;

    @Mock
    private BdkGateway bdkGateway;

    @Mock
    private StreamService streamService;

    @Mock
    private OboServices oboServices;

    @Mock
    private OboStreamService oboStreamService;

    @Mock
    private AuthSession authSession;

    @InjectMocks
    private CreateRoomExecutor executor;

    @Test
    void executeShouldCreateRoomWithAttributesAndAddMembersWhenUidsNameAndDescriptionProvided() {
        // Arrange
        CreateRoom activity = new CreateRoom();
        activity.setUserIds(Arrays.asList(123L, 456L));
        activity.setRoomName("Test Room");
        activity.setRoomDescription("Test Description");
        activity.setIsPublic(true);

        V3RoomDetail v3RoomDetail = new V3RoomDetail();
        RoomSystemInfo roomSystemInfo = new RoomSystemInfo();
        roomSystemInfo.setId("room-id-123");
        v3RoomDetail.setRoomSystemInfo(roomSystemInfo);

        when(context.getActivity()).thenReturn(activity);
        when(context.bdk()).thenReturn(bdkGateway);
        when(bdkGateway.streams()).thenReturn(streamService);
        when(streamService.create(any(V3RoomAttributes.class))).thenReturn(v3RoomDetail);

        // Act
        executor.execute(context);

        // Assert
        verify(streamService).create(any(V3RoomAttributes.class));
        verify(streamService).addMemberToRoom(eq(123L), eq("room-id-123"));
        verify(streamService).addMemberToRoom(eq(456L), eq("room-id-123"));
        verify(context).setOutputVariable(eq("roomId"), eq("room-id-123"));
    }

    @Test
    void executeShouldCreateMimWhenOnlyUidsProvided() {
        // Arrange
        CreateRoom activity = new CreateRoom();
        activity.setUserIds(Arrays.asList(123L, 456L));

        Stream stream = new Stream();
        stream.setId("stream-id-789");

        when(context.getActivity()).thenReturn(activity);
        when(context.bdk()).thenReturn(bdkGateway);
        when(bdkGateway.streams()).thenReturn(streamService);
        when(streamService.create(any(List.class))).thenReturn(stream);

        // Act
        executor.execute(context);

        // Assert
        ArgumentCaptor<List<Long>> uidsCaptor = ArgumentCaptor.forClass(List.class);
        verify(streamService).create(uidsCaptor.capture());
        assertEquals(Arrays.asList(123L, 456L), uidsCaptor.getValue());
        verify(context).setOutputVariable(eq("roomId"), eq("stream-id-789"));
    }

    @Test
    void executeShouldCreateRoomWithAttributesWhenNameProvidedWithoutUids() {
        // Arrange
        CreateRoom activity = new CreateRoom();
        activity.setRoomName("Test Room");
        activity.setRoomDescription("Test Description");

        V3RoomDetail v3RoomDetail = new V3RoomDetail();
        RoomSystemInfo roomSystemInfo = new RoomSystemInfo();
        roomSystemInfo.setId("room-id-456");
        v3RoomDetail.setRoomSystemInfo(roomSystemInfo);

        when(context.getActivity()).thenReturn(activity);
        when(context.bdk()).thenReturn(bdkGateway);
        when(bdkGateway.streams()).thenReturn(streamService);
        when(streamService.create(any(V3RoomAttributes.class))).thenReturn(v3RoomDetail);

        // Act
        executor.execute(context);

        // Assert
        verify(streamService).create(any(V3RoomAttributes.class));
        verify(context).setOutputVariable(eq("roomId"), eq("room-id-456"));
    }

    @Test
    void executeShouldUseOboWhenOboIsProvided() {
        // Arrange
        CreateRoom activity = new CreateRoom();
        Obo obo = new Obo();
        obo.setUsername("test.user");
        activity.setObo(obo);
        activity.setRoomName("Test Room");
        activity.setRoomDescription("Test Description");

        V3RoomDetail v3RoomDetail = new V3RoomDetail();
        RoomSystemInfo roomSystemInfo = new RoomSystemInfo();
        roomSystemInfo.setId("obo-room-id");
        v3RoomDetail.setRoomSystemInfo(roomSystemInfo);

        when(context.getActivity()).thenReturn(activity);
        when(context.bdk()).thenReturn(bdkGateway);
        when(bdkGateway.obo("test.user")).thenReturn(authSession);
        when(bdkGateway.obo(authSession)).thenReturn(oboServices);
        when(oboServices.streams()).thenReturn(oboStreamService);
        when(oboStreamService.create(any(V3RoomAttributes.class))).thenReturn(v3RoomDetail);

        // Act
        executor.execute(context);

        // Assert
        verify(bdkGateway.obo(authSession).streams()).create(any(V3RoomAttributes.class));
        verify(context).setOutputVariable(eq("roomId"), eq("obo-room-id"));
    }

    @Test
    void toRoomAttributesShouldConvertAllFieldsCorrectly() {
        // Arrange
        CreateRoom activity = new CreateRoom();
        activity.setRoomName("Test Room");
        activity.setRoomDescription("Test Description");
        activity.setIsPublic(true);
        activity.setViewHistory(false);
        activity.setDiscoverable(true);
        activity.setReadOnly(false);
        activity.setCopyProtected(true);
        activity.setCrossPod(false);
        activity.setMultilateralRoom(true);
        activity.setMembersCanInvite(false);
        activity.setSubType("EXTERNAL");

        V3RoomDetail v3RoomDetail = new V3RoomDetail();
        RoomSystemInfo roomSystemInfo = new RoomSystemInfo();
        roomSystemInfo.setId("room-id");
        v3RoomDetail.setRoomSystemInfo(roomSystemInfo);

        when(context.getActivity()).thenReturn(activity);
        when(context.bdk()).thenReturn(bdkGateway);
        when(bdkGateway.streams()).thenReturn(streamService);
        when(streamService.create(any(V3RoomAttributes.class))).thenReturn(v3RoomDetail);

        // Act
        executor.execute(context);

        // Assert
        ArgumentCaptor<V3RoomAttributes> attributesCaptor = ArgumentCaptor.forClass(V3RoomAttributes.class);
        verify(streamService).create(attributesCaptor.capture());

        V3RoomAttributes capturedAttributes = attributesCaptor.getValue();
        assertNotNull(capturedAttributes);
        assertEquals("Test Room", capturedAttributes.getName());
        assertEquals("Test Description", capturedAttributes.getDescription());
        assertEquals(true, capturedAttributes.getPublic());
        assertEquals(false, capturedAttributes.getViewHistory());
        assertEquals(true, capturedAttributes.getDiscoverable());
        assertEquals(false, capturedAttributes.getReadOnly());
        assertEquals(true, capturedAttributes.getCopyProtected());
        assertEquals(false, capturedAttributes.getCrossPod());
        assertEquals(true, capturedAttributes.getMultiLateralRoom());
        assertEquals(false, capturedAttributes.getMembersCanInvite());
        assertEquals("EXTERNAL", capturedAttributes.getSubType());
    }

    @Test
    void toRoomAttributesShouldHandleKeywordsCorrectly() {
        // Arrange
        CreateRoom activity = new CreateRoom();
        activity.setRoomName("Test Room");
        Map<String, String> keywords = new HashMap<>();
        keywords.put("department", "Engineering");
        keywords.put("project", "Workflow");
        activity.setKeywords(keywords);

        V3RoomDetail v3RoomDetail = new V3RoomDetail();
        RoomSystemInfo roomSystemInfo = new RoomSystemInfo();
        roomSystemInfo.setId("room-id");
        v3RoomDetail.setRoomSystemInfo(roomSystemInfo);

        when(context.getActivity()).thenReturn(activity);
        when(context.bdk()).thenReturn(bdkGateway);
        when(bdkGateway.streams()).thenReturn(streamService);
        when(streamService.create(any(V3RoomAttributes.class))).thenReturn(v3RoomDetail);

        // Act
        executor.execute(context);

        // Assert
        ArgumentCaptor<V3RoomAttributes> attributesCaptor = ArgumentCaptor.forClass(V3RoomAttributes.class);
        verify(streamService).create(attributesCaptor.capture());

        V3RoomAttributes capturedAttributes = attributesCaptor.getValue();
        assertNotNull(capturedAttributes);
        assertNotNull(capturedAttributes.getKeywords());
        assertEquals(2, capturedAttributes.getKeywords().size());
    }

    @Test
    void doOboWithCacheShouldCreateRoomWithAttributesAndAddMembersWhenUidsNameAndDescriptionProvided() {
        // Arrange
        CreateRoom activity = new CreateRoom();
        Obo obo = new Obo();
        obo.setUserId(999L);
        activity.setObo(obo);
        activity.setUserIds(Arrays.asList(123L, 456L));
        activity.setRoomName("Test OBO Room");
        activity.setRoomDescription("Test OBO Description");

        V3RoomDetail v3RoomDetail = new V3RoomDetail();
        RoomSystemInfo roomSystemInfo = new RoomSystemInfo();
        roomSystemInfo.setId("obo-room-123");
        v3RoomDetail.setRoomSystemInfo(roomSystemInfo);

        when(context.getActivity()).thenReturn(activity);
        when(context.bdk()).thenReturn(bdkGateway);
        when(bdkGateway.obo(999L)).thenReturn(authSession);
        when(bdkGateway.obo(authSession)).thenReturn(oboServices);
        when(oboServices.streams()).thenReturn(oboStreamService);
        when(oboStreamService.create(any(V3RoomAttributes.class))).thenReturn(v3RoomDetail);

        // Act
        executor.execute(context);

        // Assert
        verify(oboStreamService).create(any(V3RoomAttributes.class));
        verify(oboStreamService).addMemberToRoom(eq(123L), eq("obo-room-123"));
        verify(oboStreamService).addMemberToRoom(eq(456L), eq("obo-room-123"));
        verify(context).setOutputVariable(eq("roomId"), eq("obo-room-123"));
    }

    @Test
    void doOboWithCacheShouldCreateMimWhenOnlyUidsProvided() {
        // Arrange
        CreateRoom activity = new CreateRoom();
        Obo obo = new Obo();
        obo.setUsername("test.user");
        activity.setObo(obo);
        activity.setUserIds(Arrays.asList(123L, 456L));

        Stream stream = new Stream();
        stream.setId("obo-mim-789");

        when(context.getActivity()).thenReturn(activity);
        when(context.bdk()).thenReturn(bdkGateway);
        when(bdkGateway.obo("test.user")).thenReturn(authSession);
        when(bdkGateway.obo(authSession)).thenReturn(oboServices);
        when(oboServices.streams()).thenReturn(oboStreamService);
        when(oboStreamService.create(any(List.class))).thenReturn(stream);

        // Act
        executor.execute(context);

        // Assert
        ArgumentCaptor<List<Long>> uidsCaptor = ArgumentCaptor.forClass(List.class);
        verify(oboStreamService).create(uidsCaptor.capture());
        assertEquals(Arrays.asList(123L, 456L), uidsCaptor.getValue());
        verify(context).setOutputVariable(eq("roomId"), eq("obo-mim-789"));
    }

    @Test
    void doOboWithCacheShouldCreateRoomWithAttributesWhenNameProvidedWithoutUids() {
        // Arrange
        CreateRoom activity = new CreateRoom();
        Obo obo = new Obo();
        obo.setUsername("test.user");
        activity.setObo(obo);
        activity.setRoomName("Test OBO Room");
        activity.setRoomDescription("Test OBO Description");

        V3RoomDetail v3RoomDetail = new V3RoomDetail();
        RoomSystemInfo roomSystemInfo = new RoomSystemInfo();
        roomSystemInfo.setId("obo-room-456");
        v3RoomDetail.setRoomSystemInfo(roomSystemInfo);

        when(context.getActivity()).thenReturn(activity);
        when(context.bdk()).thenReturn(bdkGateway);
        when(bdkGateway.obo("test.user")).thenReturn(authSession);
        when(bdkGateway.obo(authSession)).thenReturn(oboServices);
        when(oboServices.streams()).thenReturn(oboStreamService);
        when(oboStreamService.create(any(V3RoomAttributes.class))).thenReturn(v3RoomDetail);

        // Act
        executor.execute(context);

        // Assert
        verify(oboStreamService).create(any(V3RoomAttributes.class));
        verify(context).setOutputVariable(eq("roomId"), eq("obo-room-456"));
    }

    @Test
    void createRoomWithUidsAndAttributesShouldCallCreateAndAddMembers() {
        // Arrange
        CreateRoom activity = new CreateRoom();
        activity.setUserIds(Arrays.asList(111L, 222L, 333L));
        activity.setRoomName("Multi User Room");
        activity.setRoomDescription("Description");

        V3RoomDetail v3RoomDetail = new V3RoomDetail();
        RoomSystemInfo roomSystemInfo = new RoomSystemInfo();
        roomSystemInfo.setId("multi-room-id");
        v3RoomDetail.setRoomSystemInfo(roomSystemInfo);

        when(context.getActivity()).thenReturn(activity);
        when(context.bdk()).thenReturn(bdkGateway);
        when(bdkGateway.streams()).thenReturn(streamService);
        when(streamService.create(any(V3RoomAttributes.class))).thenReturn(v3RoomDetail);

        // Act
        executor.execute(context);

        // Assert
        verify(streamService).create(any(V3RoomAttributes.class));
        verify(streamService, times(3)).addMemberToRoom(any(Long.class), eq("multi-room-id"));
        verify(streamService).addMemberToRoom(eq(111L), eq("multi-room-id"));
        verify(streamService).addMemberToRoom(eq(222L), eq("multi-room-id"));
        verify(streamService).addMemberToRoom(eq(333L), eq("multi-room-id"));
    }

    @Test
    void executeShouldCreateRoomWithNullKeywordsWhenEmptyMapProvided() {
        // Arrange
        CreateRoom activity = new CreateRoom();
        activity.setRoomName("Test Room");
        activity.setKeywords(new HashMap<>());

        V3RoomDetail v3RoomDetail = new V3RoomDetail();
        RoomSystemInfo roomSystemInfo = new RoomSystemInfo();
        roomSystemInfo.setId("room-id");
        v3RoomDetail.setRoomSystemInfo(roomSystemInfo);

        when(context.getActivity()).thenReturn(activity);
        when(context.bdk()).thenReturn(bdkGateway);
        when(bdkGateway.streams()).thenReturn(streamService);
        when(streamService.create(any(V3RoomAttributes.class))).thenReturn(v3RoomDetail);

        // Act
        executor.execute(context);

        // Assert
        verify(streamService).create(any(V3RoomAttributes.class));
        verify(context).setOutputVariable(eq("roomId"), eq("room-id"));
    }
}
