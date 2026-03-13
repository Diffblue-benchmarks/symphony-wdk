package com.symphony.bdk.workflow.engine.executor.group;

import com.symphony.bdk.ext.group.SymphonyGroupService;
import com.symphony.bdk.ext.group.gen.api.model.Owner;
import com.symphony.bdk.ext.group.gen.api.model.ReadGroup;
import com.symphony.bdk.ext.group.gen.api.model.Status;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.group.CreateGroup;
import com.symphony.bdk.workflow.swadl.v1.activity.group.UpdateGroup;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateGroupExecutorTest {

    @Mock
    private ActivityExecutorContext<UpdateGroup> context;

    @Mock
    private BdkGateway bdkGateway;

    @Mock
    private SymphonyGroupService groupService;

    @InjectMocks
    private UpdateGroupExecutor executor;

    @Test
    void executeShouldUpdateAvatarWhenImagePathIsProvided() throws IOException {
        // Arrange
        UpdateGroup activity = new UpdateGroup();
        activity.setGroupId("test-group-id");
        activity.setImagePath("avatar.png");
        activity.setEtag("etag-1");

        byte[] imageBytes = new byte[]{1, 2, 3, 4};
        InputStream imageStream = new ByteArrayInputStream(imageBytes);

        ReadGroup updatedGroup = new ReadGroup();
        updatedGroup.seteTag("etag-2");

        when(context.getActivity()).thenReturn(activity);
        when(context.getResource(Path.of("avatar.png"))).thenReturn(imageStream);
        when(context.bdk()).thenReturn(bdkGateway);
        when(bdkGateway.groups()).thenReturn(groupService);
        when(groupService.updateAvatar(eq("test-group-id"), any(byte[].class))).thenReturn(updatedGroup);

        // Act
        executor.execute(context);

        // Assert
        verify(groupService).updateAvatar(eq("test-group-id"), any(byte[].class));
        assertEquals("etag-2", activity.getEtag());
        verify(context).setOutputVariable(eq("group"), eq(updatedGroup));
    }

    @Test
    void executeShouldUpdateGroupWhenStatusIsProvided() throws IOException {
        // Arrange
        UpdateGroup activity = new UpdateGroup();
        activity.setGroupId("test-group-id");
        activity.setStatus("ACTIVE");
        activity.setEtag("etag-1");
        activity.setName("Test Group");
        activity.setType("SDL");

        ReadGroup updatedGroup = new ReadGroup();
        updatedGroup.setId("test-group-id");

        when(context.getActivity()).thenReturn(activity);
        when(context.bdk()).thenReturn(bdkGateway);
        when(bdkGateway.groups()).thenReturn(groupService);
        when(groupService.updateGroup(eq("etag-1"), eq("test-group-id"), any(com.symphony.bdk.ext.group.gen.api.model.UpdateGroup.class)))
            .thenReturn(updatedGroup);

        // Act
        executor.execute(context);

        // Assert
        ArgumentCaptor<com.symphony.bdk.ext.group.gen.api.model.UpdateGroup> updateGroupCaptor =
            ArgumentCaptor.forClass(com.symphony.bdk.ext.group.gen.api.model.UpdateGroup.class);
        verify(groupService).updateGroup(eq("etag-1"), eq("test-group-id"), updateGroupCaptor.capture());

        com.symphony.bdk.ext.group.gen.api.model.UpdateGroup capturedGroup = updateGroupCaptor.getValue();
        assertNotNull(capturedGroup);
        assertEquals("test-group-id", capturedGroup.getId());
        assertEquals("etag-1", capturedGroup.geteTag());
        assertEquals("Test Group", capturedGroup.getName());
        assertEquals(Status.ACTIVE, capturedGroup.getStatus());

        verify(context).setOutputVariable(eq("group"), eq(updatedGroup));
    }

    @Test
    void executeShouldUpdateBothAvatarAndGroupWhenBothAreProvided() throws IOException {
        // Arrange
        UpdateGroup activity = new UpdateGroup();
        activity.setGroupId("test-group-id");
        activity.setImagePath("avatar.png");
        activity.setStatus("ACTIVE");
        activity.setEtag("etag-1");
        activity.setName("Test Group");
        activity.setType("SDL");

        byte[] imageBytes = new byte[]{1, 2, 3, 4};
        InputStream imageStream = new ByteArrayInputStream(imageBytes);

        ReadGroup avatarUpdatedGroup = new ReadGroup();
        avatarUpdatedGroup.seteTag("etag-2");

        ReadGroup fullyUpdatedGroup = new ReadGroup();
        fullyUpdatedGroup.setId("test-group-id");

        when(context.getActivity()).thenReturn(activity);
        when(context.getResource(Path.of("avatar.png"))).thenReturn(imageStream);
        when(context.bdk()).thenReturn(bdkGateway);
        when(bdkGateway.groups()).thenReturn(groupService);
        when(groupService.updateAvatar(eq("test-group-id"), any(byte[].class))).thenReturn(avatarUpdatedGroup);
        when(groupService.updateGroup(eq("etag-2"), eq("test-group-id"), any(com.symphony.bdk.ext.group.gen.api.model.UpdateGroup.class)))
            .thenReturn(fullyUpdatedGroup);

        // Act
        executor.execute(context);

        // Assert
        verify(groupService).updateAvatar(eq("test-group-id"), any(byte[].class));
        verify(groupService).updateGroup(eq("etag-2"), eq("test-group-id"), any(com.symphony.bdk.ext.group.gen.api.model.UpdateGroup.class));
        assertEquals("etag-2", activity.getEtag());
        verify(context).setOutputVariable(eq("group"), eq(fullyUpdatedGroup));
    }

    @Test
    void executeShouldNotSetOutputVariableWhenNoUpdateIsPerformed() throws IOException {
        // Arrange
        UpdateGroup activity = new UpdateGroup();
        activity.setGroupId("test-group-id");
        // No imagePath and no status

        when(context.getActivity()).thenReturn(activity);

        // Act
        executor.execute(context);

        // Assert
        verify(context, never()).setOutputVariable(any(), any());
    }

    @Test
    void toUpdateGroupShouldConvertAllFieldsCorrectly() throws IOException {
        // Arrange
        UpdateGroup activity = new UpdateGroup();
        activity.setGroupId("test-group-id");
        activity.setEtag("etag-1");
        activity.setName("Test Group");
        activity.setStatus("ACTIVE");
        activity.setType("SDL");
        activity.setReferrer("test-referrer");

        CreateGroup.Owner owner = new CreateGroup.Owner();
        owner.setId(123L);
        owner.setType("USER");
        activity.setOwner(owner);

        CreateGroup.GroupMember member = new CreateGroup.GroupMember();
        member.setUserId(456L);
        member.setTenantId(789);
        activity.setMembers(List.of(member));

        CreateGroup.Profile profile = new CreateGroup.Profile();
        profile.setDisplayName("Display Name");
        profile.setCompanyName("Company");
        profile.setEmail("test@example.com");
        activity.setProfile(profile);

        CreateGroup.VisibilityRestriction visibilityRestriction = new CreateGroup.VisibilityRestriction();
        visibilityRestriction.setTenantIds(List.of(1, 2, 3));
        activity.setVisibilityRestriction(visibilityRestriction);

        CreateGroup.ImplicitConnection implicitConnection = new CreateGroup.ImplicitConnection();
        implicitConnection.setUserIds(List.of(111L, 222L));
        activity.setImplicitConnection(implicitConnection);

        CreateGroup.InteractionTransfer interactionTransfer = new CreateGroup.InteractionTransfer();
        interactionTransfer.setTenantIds(List.of(4, 5));
        activity.setInteractionTransfer(interactionTransfer);

        when(context.getActivity()).thenReturn(activity);
        when(context.bdk()).thenReturn(bdkGateway);
        when(bdkGateway.groups()).thenReturn(groupService);
        when(groupService.updateGroup(any(), any(), any())).thenReturn(new ReadGroup());

        // Act
        executor.execute(context);

        // Assert
        ArgumentCaptor<com.symphony.bdk.ext.group.gen.api.model.UpdateGroup> captor =
            ArgumentCaptor.forClass(com.symphony.bdk.ext.group.gen.api.model.UpdateGroup.class);
        verify(groupService).updateGroup(any(), any(), captor.capture());

        com.symphony.bdk.ext.group.gen.api.model.UpdateGroup result = captor.getValue();
        assertNotNull(result);
        assertEquals("SDL", result.getType());
        assertEquals("test-group-id", result.getId());
        assertEquals("etag-1", result.geteTag());
        assertEquals("Test Group", result.getName());
        assertEquals(Status.ACTIVE, result.getStatus());
        assertEquals("test-referrer", result.getReferrer());
        assertEquals(123L, result.getOwnerId());
        assertEquals(Owner.USER, result.getOwnerType());
        assertNotNull(result.getMembers());
        assertEquals(1, result.getMembers().size());
        assertEquals(456L, result.getMembers().get(0).getMemberId());
        assertEquals(789, result.getMembers().get(0).getMemberTenant());
        assertNotNull(result.getProfile());
        assertEquals("Display Name", result.getProfile().getDisplayName());
        assertNotNull(result.getVisibilityRestriction());
        assertEquals(3, result.getVisibilityRestriction().getRestrictedTenantsList().size());
        assertNotNull(result.getImplicitConnection());
        assertEquals(2, result.getImplicitConnection().getConnectedUsersList().size());
        assertNotNull(result.getInteractionTransfer());
        assertEquals(2, result.getInteractionTransfer().getRestrictedTenantsList().size());
    }

    @Test
    void toUpdateGroupShouldHandleNullOwnerCorrectly() throws IOException {
        // Arrange
        UpdateGroup activity = new UpdateGroup();
        activity.setGroupId("test-group-id");
        activity.setStatus("ACTIVE");
        activity.setEtag("etag-1");
        activity.setName("Test Group");
        activity.setType("SDL");
        activity.setOwner(null);

        when(context.getActivity()).thenReturn(activity);
        when(context.bdk()).thenReturn(bdkGateway);
        when(bdkGateway.groups()).thenReturn(groupService);
        when(groupService.updateGroup(any(), any(), any())).thenReturn(new ReadGroup());

        // Act
        executor.execute(context);

        // Assert
        ArgumentCaptor<com.symphony.bdk.ext.group.gen.api.model.UpdateGroup> captor =
            ArgumentCaptor.forClass(com.symphony.bdk.ext.group.gen.api.model.UpdateGroup.class);
        verify(groupService).updateGroup(any(), any(), captor.capture());

        com.symphony.bdk.ext.group.gen.api.model.UpdateGroup result = captor.getValue();
        assertNotNull(result);
        assertNull(result.getOwnerId());
        assertNull(result.getOwnerType());
    }

    @Test
    void toSubTypeShouldReturnNullWhenSubTypeIsNull() throws IOException {
        // Arrange
        UpdateGroup activity = new UpdateGroup();
        activity.setGroupId("test-group-id");
        activity.setStatus("ACTIVE");
        activity.setEtag("etag-1");
        activity.setName("Test Group");
        activity.setType("SDL");
        activity.setSubType(null);

        when(context.getActivity()).thenReturn(activity);
        when(context.bdk()).thenReturn(bdkGateway);
        when(bdkGateway.groups()).thenReturn(groupService);
        when(groupService.updateGroup(any(), any(), any())).thenReturn(new ReadGroup());

        // Act
        executor.execute(context);

        // Assert
        ArgumentCaptor<com.symphony.bdk.ext.group.gen.api.model.UpdateGroup> captor =
            ArgumentCaptor.forClass(com.symphony.bdk.ext.group.gen.api.model.UpdateGroup.class);
        verify(groupService).updateGroup(any(), any(), captor.capture());

        com.symphony.bdk.ext.group.gen.api.model.UpdateGroup result = captor.getValue();
        assertNotNull(result);
        assertNull(result.getSubType());
    }

    @Test
    void toUpdateGroupShouldHandleNullFieldsCorrectly() throws IOException {
        // Arrange
        UpdateGroup activity = new UpdateGroup();
        activity.setGroupId("test-group-id");
        activity.setStatus("ACTIVE");
        activity.setEtag("etag-1");
        activity.setName("Test Group");
        activity.setType("SDL");
        activity.setMembers(null);
        activity.setProfile(null);
        activity.setVisibilityRestriction(null);
        activity.setImplicitConnection(null);
        activity.setInteractionTransfer(null);

        when(context.getActivity()).thenReturn(activity);
        when(context.bdk()).thenReturn(bdkGateway);
        when(bdkGateway.groups()).thenReturn(groupService);
        when(groupService.updateGroup(any(), any(), any())).thenReturn(new ReadGroup());

        // Act
        executor.execute(context);

        // Assert
        ArgumentCaptor<com.symphony.bdk.ext.group.gen.api.model.UpdateGroup> captor =
            ArgumentCaptor.forClass(com.symphony.bdk.ext.group.gen.api.model.UpdateGroup.class);
        verify(groupService).updateGroup(any(), any(), captor.capture());

        com.symphony.bdk.ext.group.gen.api.model.UpdateGroup result = captor.getValue();
        assertNotNull(result);
        assertNull(result.getMembers());
        assertNull(result.getProfile());
        assertNull(result.getVisibilityRestriction());
        assertNull(result.getImplicitConnection());
        assertNull(result.getInteractionTransfer());
    }
}
