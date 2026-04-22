package com.symphony.bdk.workflow.engine.executor.group;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.core.auth.AuthenticatorFactory;
import com.symphony.bdk.core.config.model.BdkConfig;
import com.symphony.bdk.core.service.connection.ConnectionService;
import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.ext.group.SymphonyGroupService;
import com.symphony.bdk.ext.group.gen.api.model.ReadGroup;
import com.symphony.bdk.workflow.engine.SpringBdkGateway;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.group.CreateGroup;
import com.symphony.bdk.workflow.swadl.v1.activity.group.UpdateGroup;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UpdateGroupExecutor.class})
@ExtendWith(SpringExtension.class)
class UpdateGroupExecutorDiffblueTest {
  @Autowired private UpdateGroupExecutor updateGroupExecutor;

  /**
   * Test {@link UpdateGroupExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link UpdateGroup} (default constructor).
   *   <li>Then calls {@link ActivityExecutorContext#getActivity()}.
   * </ul>
   *
   * <p>Method under test: {@link UpdateGroupExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given UpdateGroup (default constructor); then calls getActivity()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UpdateGroupExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenUpdateGroup_thenCallsGetActivity() throws IOException {
    // Arrange
    ActivityExecutorContext<UpdateGroup> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenReturn(new UpdateGroup());

    // Act
    updateGroupExecutor.execute(execution);

    // Assert
    verify(execution, atLeast(1)).getActivity();
  }

  /**
   * Test {@link UpdateGroupExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link UpdateGroup} with status set to ACTIVE.
   *   <li>Then calls {@link SymphonyGroupService#updateGroup(String, String, Object)}.
   * </ul>
   *
   * <p>Method under test: {@link UpdateGroupExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given UpdateGroup with status ACTIVE; then calls updateGroup")
  @Tag("ContributionFromDiffblue")
  @MethodsUnderTest({"void UpdateGroupExecutor.execute(ActivityExecutorContext)"})
  void testExecute_withStatus_thenCallsUpdateGroup() throws IOException {
    // Arrange
    UpdateGroupExecutor executor = new UpdateGroupExecutor();

    UpdateGroup updateGroup = new UpdateGroup();
    updateGroup.setGroupId("group123");
    updateGroup.setEtag("etag1");
    updateGroup.setStatus("ACTIVE");
    updateGroup.setName("Test Group");

    ReadGroup readGroup = new ReadGroup();
    SymphonyGroupService groupService = mock(SymphonyGroupService.class);
    when(groupService.updateGroup(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<com.symphony.bdk.ext.group.gen.api.model.UpdateGroup>any()))
        .thenReturn(readGroup);

    SpringBdkGateway springBdkGateway =
        new SpringBdkGateway(
            mock(BdkConfig.class),
            mock(AuthenticatorFactory.class),
            mock(MessageService.class),
            mock(StreamService.class),
            mock(UserService.class),
            mock(ConnectionService.class),
            groupService,
            mock(SessionService.class));

    ActivityExecutorContext<UpdateGroup> execution = mock(ActivityExecutorContext.class);
    doNothing().when(execution).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(execution.bdk()).thenReturn(springBdkGateway);
    when(execution.getActivity()).thenReturn(updateGroup);

    // Act
    executor.execute(execution);

    // Assert
    verify(groupService)
        .updateGroup(
            eq("etag1"),
            eq("group123"),
            isA(com.symphony.bdk.ext.group.gen.api.model.UpdateGroup.class));
    verify(execution).setOutputVariable(eq("group"), isA(Object.class));
  }

  /**
   * Test {@link UpdateGroupExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link UpdateGroup} with status ACTIVE and owner set.
   *   <li>Then calls updateGroup with owner fields populated.
   * </ul>
   *
   * <p>Method under test: {@link UpdateGroupExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given UpdateGroup with status and owner; then calls updateGroup")
  @Tag("ContributionFromDiffblue")
  @MethodsUnderTest({"void UpdateGroupExecutor.execute(ActivityExecutorContext)"})
  void testExecute_withStatusAndOwner_thenCallsUpdateGroup() throws IOException {
    // Arrange
    UpdateGroupExecutor executor = new UpdateGroupExecutor();

    CreateGroup.Owner owner = new CreateGroup.Owner();
    owner.setId(123L);
    owner.setType("TENANT");

    UpdateGroup updateGroup = new UpdateGroup();
    updateGroup.setGroupId("group456");
    updateGroup.setEtag("etag2");
    updateGroup.setStatus("ACTIVE");
    updateGroup.setOwner(owner);

    ReadGroup readGroup = new ReadGroup();
    SymphonyGroupService groupService = mock(SymphonyGroupService.class);
    when(groupService.updateGroup(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<com.symphony.bdk.ext.group.gen.api.model.UpdateGroup>any()))
        .thenReturn(readGroup);

    SpringBdkGateway springBdkGateway =
        new SpringBdkGateway(
            mock(BdkConfig.class),
            mock(AuthenticatorFactory.class),
            mock(MessageService.class),
            mock(StreamService.class),
            mock(UserService.class),
            mock(ConnectionService.class),
            groupService,
            mock(SessionService.class));

    ActivityExecutorContext<UpdateGroup> execution = mock(ActivityExecutorContext.class);
    doNothing().when(execution).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(execution.bdk()).thenReturn(springBdkGateway);
    when(execution.getActivity()).thenReturn(updateGroup);

    // Act
    executor.execute(execution);

    // Assert
    verify(groupService)
        .updateGroup(
            eq("etag2"),
            eq("group456"),
            isA(com.symphony.bdk.ext.group.gen.api.model.UpdateGroup.class));
    verify(execution).setOutputVariable(eq("group"), isA(Object.class));
  }

  /**
   * Test {@link UpdateGroupExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link UpdateGroup} with imagePath set.
   *   <li>Then calls {@link SymphonyGroupService#updateAvatar(String, byte[])}.
   * </ul>
   *
   * <p>Method under test: {@link UpdateGroupExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given UpdateGroup with imagePath; then calls updateAvatar")
  @Tag("ContributionFromDiffblue")
  @MethodsUnderTest({"void UpdateGroupExecutor.execute(ActivityExecutorContext)"})
  void testExecute_withImagePath_thenCallsUpdateAvatar() throws IOException {
    // Arrange
    UpdateGroupExecutor executor = new UpdateGroupExecutor();

    UpdateGroup updateGroup = new UpdateGroup();
    updateGroup.setGroupId("group789");
    updateGroup.setEtag("etag3");
    updateGroup.setImagePath("/attachments/logo.png");

    ReadGroup readGroup = new ReadGroup();
    readGroup.seteTag("newEtag");

    SymphonyGroupService groupService = mock(SymphonyGroupService.class);
    when(groupService.updateAvatar(Mockito.<String>any(), Mockito.<byte[]>any()))
        .thenReturn(readGroup);

    SpringBdkGateway springBdkGateway =
        new SpringBdkGateway(
            mock(BdkConfig.class),
            mock(AuthenticatorFactory.class),
            mock(MessageService.class),
            mock(StreamService.class),
            mock(UserService.class),
            mock(ConnectionService.class),
            groupService,
            mock(SessionService.class));

    InputStream imageStream = new ByteArrayInputStream(new byte[]{1, 2, 3});
    ActivityExecutorContext<UpdateGroup> execution = mock(ActivityExecutorContext.class);
    doNothing().when(execution).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(execution.bdk()).thenReturn(springBdkGateway);
    when(execution.getActivity()).thenReturn(updateGroup);
    when(execution.getResource(any(Path.class))).thenReturn(imageStream);

    // Act
    executor.execute(execution);

    // Assert
    verify(groupService).updateAvatar(eq("group789"), any(byte[].class));
    verify(execution).setOutputVariable(eq("group"), isA(Object.class));
  }
}
