package com.symphony.bdk.workflow.engine.executor.group;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.symphony.bdk.ext.group.SymphonyGroupService;
import com.symphony.bdk.ext.group.gen.api.model.ReadGroup;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.group.CreateGroup;
import com.symphony.bdk.workflow.swadl.v1.activity.group.UpdateGroup;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

class UpdateGroupExecutorTest {

  private final UpdateGroupExecutor underTest = new UpdateGroupExecutor();

  @Test
  @SuppressWarnings("unchecked")
  void shouldUpdateAvatarWhenImagePathIsSet() throws IOException {
    ActivityExecutorContext<UpdateGroup> context = mock(ActivityExecutorContext.class);
    BdkGateway bdk = mock(BdkGateway.class);
    SymphonyGroupService groupService = mock(SymphonyGroupService.class);

    UpdateGroup activity = new UpdateGroup();
    activity.setGroupId("group123");
    activity.setImagePath("/path/to/image.png");

    ReadGroup updatedGroup = new ReadGroup();
    updatedGroup.seteTag("newEtag");

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.groups()).thenReturn(groupService);
    when(context.getResource(any(Path.class))).thenReturn(new ByteArrayInputStream(new byte[]{1, 2, 3}));
    when(groupService.updateAvatar(eq("group123"), any(byte[].class))).thenReturn(updatedGroup);

    underTest.execute(context);

    verify(groupService).updateAvatar(eq("group123"), any(byte[].class));
    verify(context).setOutputVariable("group", updatedGroup);
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldUpdateGroupWhenStatusIsSet() throws IOException {
    ActivityExecutorContext<UpdateGroup> context = mock(ActivityExecutorContext.class);
    BdkGateway bdk = mock(BdkGateway.class);
    SymphonyGroupService groupService = mock(SymphonyGroupService.class);

    UpdateGroup activity = new UpdateGroup();
    activity.setGroupId("group456");
    activity.setEtag("etag123");
    activity.setStatus("ACTIVE");

    ReadGroup updatedGroup = new ReadGroup();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.groups()).thenReturn(groupService);
    when(groupService.updateGroup(eq("etag123"), eq("group456"), any())).thenReturn(updatedGroup);

    underTest.execute(context);

    verify(groupService).updateGroup(eq("etag123"), eq("group456"), any());
    verify(context).setOutputVariable("group", updatedGroup);
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldUpdateBothAvatarAndGroupWhenImagePathAndStatusAreSet() throws IOException {
    ActivityExecutorContext<UpdateGroup> context = mock(ActivityExecutorContext.class);
    BdkGateway bdk = mock(BdkGateway.class);
    SymphonyGroupService groupService = mock(SymphonyGroupService.class);

    UpdateGroup activity = new UpdateGroup();
    activity.setGroupId("group789");
    activity.setImagePath("/path/to/image.png");
    activity.setStatus("ACTIVE");

    ReadGroup avatarGroup = new ReadGroup();
    avatarGroup.seteTag("etagAfterAvatar");
    ReadGroup updatedGroup = new ReadGroup();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.groups()).thenReturn(groupService);
    when(context.getResource(any(Path.class))).thenReturn(new ByteArrayInputStream(new byte[]{1, 2, 3}));
    when(groupService.updateAvatar(eq("group789"), any(byte[].class))).thenReturn(avatarGroup);
    when(groupService.updateGroup(eq("etagAfterAvatar"), eq("group789"), any())).thenReturn(updatedGroup);

    underTest.execute(context);

    verify(groupService).updateAvatar(eq("group789"), any(byte[].class));
    verify(groupService).updateGroup(eq("etagAfterAvatar"), eq("group789"), any());
    verify(context).setOutputVariable("group", updatedGroup);
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldNotSetOutputWhenNeitherImagePathNorStatusIsSet() throws IOException {
    ActivityExecutorContext<UpdateGroup> context = mock(ActivityExecutorContext.class);
    BdkGateway bdk = mock(BdkGateway.class);
    SymphonyGroupService groupService = mock(SymphonyGroupService.class);

    UpdateGroup activity = new UpdateGroup();
    activity.setGroupId("group000");

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.groups()).thenReturn(groupService);

    underTest.execute(context);

    verify(context, never()).setOutputVariable(any(), any());
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldUpdateGroupWithOwnerWhenOwnerIsSet() throws IOException {
    ActivityExecutorContext<UpdateGroup> context = mock(ActivityExecutorContext.class);
    BdkGateway bdk = mock(BdkGateway.class);
    SymphonyGroupService groupService = mock(SymphonyGroupService.class);

    UpdateGroup activity = new UpdateGroup();
    activity.setGroupId("groupWithOwner");
    activity.setEtag("etagOwner");
    activity.setStatus("ACTIVE");
    activity.setName("My Group");

    CreateGroup.Owner owner = new CreateGroup.Owner();
    owner.setId(123L);
    owner.setType("TENANT");
    activity.setOwner(owner);

    ReadGroup updatedGroup = new ReadGroup();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.groups()).thenReturn(groupService);
    when(groupService.updateGroup(eq("etagOwner"), eq("groupWithOwner"), any())).thenReturn(updatedGroup);

    underTest.execute(context);

    ArgumentCaptor<com.symphony.bdk.ext.group.gen.api.model.UpdateGroup> captor =
        ArgumentCaptor.forClass(com.symphony.bdk.ext.group.gen.api.model.UpdateGroup.class);
    verify(groupService).updateGroup(eq("etagOwner"), eq("groupWithOwner"), captor.capture());
    assertThat(captor.getValue().getOwnerId()).isEqualTo(123L);
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldUpdateGroupWithNullSubTypeWhenSubTypeNotSet() throws IOException {
    ActivityExecutorContext<UpdateGroup> context = mock(ActivityExecutorContext.class);
    BdkGateway bdk = mock(BdkGateway.class);
    SymphonyGroupService groupService = mock(SymphonyGroupService.class);

    UpdateGroup activity = new UpdateGroup();
    activity.setGroupId("groupNoSubType");
    activity.setEtag("etag");
    activity.setStatus("ACTIVE");
    // subType is null by default

    ReadGroup updatedGroup = new ReadGroup();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.groups()).thenReturn(groupService);
    when(groupService.updateGroup(any(), any(), any())).thenReturn(updatedGroup);

    underTest.execute(context);

    ArgumentCaptor<com.symphony.bdk.ext.group.gen.api.model.UpdateGroup> captor =
        ArgumentCaptor.forClass(com.symphony.bdk.ext.group.gen.api.model.UpdateGroup.class);
    verify(groupService).updateGroup(any(), any(), captor.capture());
    assertThat(captor.getValue().getSubType()).isNull();
  }
}
