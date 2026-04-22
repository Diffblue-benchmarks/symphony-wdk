package com.symphony.bdk.workflow.engine.executor.attachment;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.gen.api.model.V4AttachmentInfo;
import com.symphony.bdk.gen.api.model.V4Message;
import com.symphony.bdk.gen.api.model.V4Stream;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.attachment.GetAttachment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Collections;

@ContextConfiguration(classes = {GetAttachmentExecutor.class})
@ExtendWith(SpringExtension.class)
class GetAttachmentExecutorDiffblueTest {
  @Autowired private GetAttachmentExecutor getAttachmentExecutor;

  /**
   * Test {@link GetAttachmentExecutor#execute(ActivityExecutorContext)}.
   *
   * <p>Method under test: {@link GetAttachmentExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void GetAttachmentExecutor.execute(ActivityExecutorContext)"})
  void testExecute() throws IOException {
    // Arrange
    ActivityExecutorContext<GetAttachment> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenThrow(new IllegalArgumentException());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> getAttachmentExecutor.execute(execution));
    verify(execution).getActivity();
  }

  /**
   * Test {@link GetAttachmentExecutor#execute(ActivityExecutorContext)} when getMessage returns null.
   *
   * <p>Method under test: {@link GetAttachmentExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext); when message is null; then throws IllegalArgumentException")
  @Tag("ContributionFromDiffblue")
  @MethodsUnderTest({"void GetAttachmentExecutor.execute(ActivityExecutorContext)"})
  void testExecute_whenMessageIsNull_throwsIllegalArgumentException() throws IOException {
    // Arrange
    GetAttachmentExecutor executor = new GetAttachmentExecutor();

    GetAttachment activity = new GetAttachment();
    activity.setMessageId("MSG_ID");

    MessageService messageService = mock(MessageService.class);
    when(messageService.getMessage("MSG_ID")).thenReturn(null);

    BdkGateway bdkGateway = mock(BdkGateway.class);
    when(bdkGateway.messages()).thenReturn(messageService);

    ActivityExecutorContext<GetAttachment> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenReturn(activity);
    when(execution.bdk()).thenReturn(bdkGateway);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> executor.execute(execution));
    verify(messageService).getMessage("MSG_ID");
  }

  /**
   * Test {@link GetAttachmentExecutor#execute(ActivityExecutorContext)} when attachments is null.
   *
   * <p>Method under test: {@link GetAttachmentExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext); when attachments is null; then throws IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @MethodsUnderTest({"void GetAttachmentExecutor.execute(ActivityExecutorContext)"})
  void testExecute_whenAttachmentsIsNull_throwsIllegalStateException() throws IOException {
    // Arrange
    GetAttachmentExecutor executor = new GetAttachmentExecutor();

    GetAttachment activity = new GetAttachment();
    activity.setMessageId("MSG_ID");

    V4Message message = new V4Message().messageId("MSG_ID");

    MessageService messageService = mock(MessageService.class);
    when(messageService.getMessage("MSG_ID")).thenReturn(message);

    BdkGateway bdkGateway = mock(BdkGateway.class);
    when(bdkGateway.messages()).thenReturn(messageService);

    ActivityExecutorContext<GetAttachment> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenReturn(activity);
    when(execution.bdk()).thenReturn(bdkGateway);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> executor.execute(execution));
    verify(messageService).getMessage("MSG_ID");
  }

  /**
   * Test {@link GetAttachmentExecutor#execute(ActivityExecutorContext)} when attachment ID not found.
   *
   * <p>Method under test: {@link GetAttachmentExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext); when attachment ID not found; then throws IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @MethodsUnderTest({"void GetAttachmentExecutor.execute(ActivityExecutorContext)"})
  void testExecute_whenAttachmentIdNotFound_throwsIllegalStateException() throws IOException {
    // Arrange
    GetAttachmentExecutor executor = new GetAttachmentExecutor();

    GetAttachment activity = new GetAttachment();
    activity.setMessageId("MSG_ID");
    activity.setAttachmentId("WANTED_ID");

    V4AttachmentInfo attachmentInfo = new V4AttachmentInfo().id("OTHER_ID").name("file.txt");
    V4Stream v4Stream = new V4Stream().streamId("STREAM_ID");
    V4Message message = new V4Message().messageId("MSG_ID").stream(v4Stream)
        .attachments(Collections.singletonList(attachmentInfo));

    MessageService messageService = mock(MessageService.class);
    when(messageService.getMessage("MSG_ID")).thenReturn(message);

    BdkGateway bdkGateway = mock(BdkGateway.class);
    when(bdkGateway.messages()).thenReturn(messageService);

    ActivityExecutorContext<GetAttachment> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenReturn(activity);
    when(execution.bdk()).thenReturn(bdkGateway);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> executor.execute(execution));
    verify(messageService).getMessage("MSG_ID");
  }

  /**
   * Test {@link GetAttachmentExecutor#execute(ActivityExecutorContext)} happy path.
   *
   * <p>Method under test: {@link GetAttachmentExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext); happy path; then sets output variable with attachment path")
  @Tag("ContributionFromDiffblue")
  @MethodsUnderTest({"void GetAttachmentExecutor.execute(ActivityExecutorContext)",
      "Path GetAttachmentExecutor.storeAttachment(byte[], String, ActivityExecutorContext)"})
  void testExecute_happyPath_setsOutputVariable() throws IOException {
    // Arrange
    GetAttachmentExecutor executor = new GetAttachmentExecutor();

    GetAttachment activity = new GetAttachment();
    activity.setMessageId("MSG_ID");
    activity.setAttachmentId("ATTACHMENT_ID");

    V4AttachmentInfo attachmentInfo = new V4AttachmentInfo().id("ATTACHMENT_ID").name("file.txt");
    V4Stream v4Stream = new V4Stream().streamId("STREAM_ID");
    V4Message message = new V4Message().messageId("MSG_ID").stream(v4Stream)
        .attachments(Collections.singletonList(attachmentInfo));

    byte[] encodedContent = Base64.getEncoder().encode("hello".getBytes());
    Path expectedPath = Path.of("process123", "activityA-file.txt");

    MessageService messageService = mock(MessageService.class);
    when(messageService.getMessage("MSG_ID")).thenReturn(message);
    when(messageService.getAttachment("STREAM_ID", "MSG_ID", "ATTACHMENT_ID")).thenReturn(encodedContent);

    BdkGateway bdkGateway = mock(BdkGateway.class);
    when(bdkGateway.messages()).thenReturn(messageService);

    ActivityExecutorContext<GetAttachment> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenReturn(activity);
    when(execution.bdk()).thenReturn(bdkGateway);
    when(execution.getCurrentActivityId()).thenReturn("activityA");
    when(execution.getProcessInstanceId()).thenReturn("process123");
    when(execution.saveResource(any(Path.class), any(byte[].class))).thenReturn(expectedPath);
    doNothing().when(execution).setOutputVariable(any(String.class), any(Object.class));

    // Act
    executor.execute(execution);

    // Assert
    verify(messageService).getMessage("MSG_ID");
    verify(messageService).getAttachment("STREAM_ID", "MSG_ID", "ATTACHMENT_ID");
    verify(execution).saveResource(eq(Path.of("process123", "activityA-file.txt")), any(byte[].class));
    verify(execution).setOutputVariable(eq("attachmentPath"), eq(expectedPath.toString()));
  }
}
