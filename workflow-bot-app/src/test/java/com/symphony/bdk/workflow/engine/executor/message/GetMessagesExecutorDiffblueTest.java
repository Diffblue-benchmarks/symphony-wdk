package com.symphony.bdk.workflow.engine.executor.message;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.core.service.pagination.model.PaginationAttribute;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.message.GetMessages;
import java.time.Instant;
import java.util.Collections;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {GetMessagesExecutor.class})
@ExtendWith(SpringExtension.class)
class GetMessagesExecutorDiffblueTest {
  @Autowired private GetMessagesExecutor getMessagesExecutor;

  /**
   * Test {@link GetMessagesExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link GetMessages} (default constructor) StreamId is {@code Activity}.
   *   <li>Then calls {@link ActivityExecutorContext#getActivity()}.
   * </ul>
   *
   * <p>Method under test: {@link GetMessagesExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given GetMessages (default constructor) StreamId is 'Activity'; then calls getActivity()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void GetMessagesExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenGetMessagesStreamIdIsActivity_thenCallsGetActivity() {
    // Arrange
    GetMessages getMessages = new GetMessages();
    getMessages.setStreamId("Activity");

    ActivityExecutorContext<GetMessages> context = mock(ActivityExecutorContext.class);
    when(context.getActivity()).thenReturn(getMessages);

    // Act
    getMessagesExecutor.execute(context);

    // Assert
    verify(context).getActivity();
  }

  /**
   * Test {@link GetMessagesExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link GetMessages} (default constructor).
   *   <li>Then calls {@link ActivityExecutorContext#getActivity()}.
   * </ul>
   *
   * <p>Method under test: {@link GetMessagesExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given GetMessages (default constructor); then calls getActivity()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void GetMessagesExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenGetMessages_thenCallsGetActivity() {
    // Arrange
    ActivityExecutorContext<GetMessages> context = mock(ActivityExecutorContext.class);
    when(context.getActivity()).thenReturn(new GetMessages());

    // Act
    getMessagesExecutor.execute(context);

    // Assert
    verify(context).getActivity();
  }

  /**
   * Test {@link GetMessagesExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link GetMessages} with streamId and since set, skip and limit null.
   *   <li>Then calls {@link MessageService#listMessages(String, Instant)} without pagination.
   * </ul>
   *
   * <p>Method under test: {@link GetMessagesExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given GetMessages with streamId and since, no skip/limit; then calls listMessages without pagination")
  @Tag("ContributionFromDiffblue")
  @MethodsUnderTest({"void GetMessagesExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenGetMessagesWithStreamIdAndSinceNoPagination_thenCallsListMessagesWithoutPagination() {
    // Arrange
    GetMessages getMessages = new GetMessages();
    getMessages.setStreamId("STREAM_ID");
    getMessages.setSince("2023-01-01T00:00:00Z");

    MessageService messageService = mock(MessageService.class);
    when(messageService.listMessages(any(String.class), any(Instant.class))).thenReturn(
        Collections.emptyList());

    BdkGateway bdkGateway = mock(BdkGateway.class);
    when(bdkGateway.messages()).thenReturn(messageService);

    ActivityExecutorContext<GetMessages> context = mock(ActivityExecutorContext.class);
    when(context.getActivity()).thenReturn(getMessages);
    when(context.bdk()).thenReturn(bdkGateway);
    doNothing().when(context).setOutputVariable(any(String.class), any());

    // Act
    getMessagesExecutor.execute(context);

    // Assert
    verify(messageService).listMessages(eq("STREAM_ID"), any(Instant.class));
    verify(context).setOutputVariable(eq("messages"), any());
  }

  /**
   * Test {@link GetMessagesExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link GetMessages} with streamId, since, skip, and limit all set.
   *   <li>Then calls {@link MessageService#listMessages(String, Instant, PaginationAttribute)} with pagination.
   * </ul>
   *
   * <p>Method under test: {@link GetMessagesExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given GetMessages with streamId, since, skip, and limit; then calls listMessages with pagination")
  @Tag("ContributionFromDiffblue")
  @MethodsUnderTest({"void GetMessagesExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenGetMessagesWithPagination_thenCallsListMessagesWithPagination() {
    // Arrange
    GetMessages getMessages = new GetMessages();
    getMessages.setStreamId("STREAM_ID");
    getMessages.setSince("2023-01-01T00:00:00Z");
    getMessages.setSkip(0);
    getMessages.setLimit(50);

    MessageService messageService = mock(MessageService.class);
    when(messageService.listMessages(any(String.class), any(Instant.class),
        any(PaginationAttribute.class))).thenReturn(Collections.emptyList());

    BdkGateway bdkGateway = mock(BdkGateway.class);
    when(bdkGateway.messages()).thenReturn(messageService);

    ActivityExecutorContext<GetMessages> context = mock(ActivityExecutorContext.class);
    when(context.getActivity()).thenReturn(getMessages);
    when(context.bdk()).thenReturn(bdkGateway);
    doNothing().when(context).setOutputVariable(any(String.class), any());

    // Act
    getMessagesExecutor.execute(context);

    // Assert
    verify(messageService).listMessages(eq("STREAM_ID"), any(Instant.class),
        isA(PaginationAttribute.class));
    verify(context).setOutputVariable(eq("messages"), any());
  }
}
