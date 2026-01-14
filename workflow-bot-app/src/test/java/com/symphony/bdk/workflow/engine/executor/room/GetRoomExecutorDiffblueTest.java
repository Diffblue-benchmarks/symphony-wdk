package com.symphony.bdk.workflow.engine.executor.room;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.core.auth.AuthenticatorFactory;
import com.symphony.bdk.core.config.model.BdkConfig;
import com.symphony.bdk.core.service.connection.ConnectionService;
import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.ext.group.SymphonyGroupService;
import com.symphony.bdk.gen.api.model.V3RoomDetail;
import com.symphony.bdk.workflow.engine.SpringBdkGateway;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.room.GetRoom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GetRoomExecutorDiffblueTest {
  /**
   * Test {@link GetRoomExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link StreamService} {@link StreamService#getRoomInfo(String)} return {@link
   *       V3RoomDetail} (default constructor).
   *   <li>Then calls {@link StreamService#getRoomInfo(String)}.
   * </ul>
   *
   * <p>Method under test: {@link GetRoomExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given StreamService getRoomInfo(String) return V3RoomDetail (default constructor); then calls getRoomInfo(String)")
  @Tag("MaintainedByDiffblue")
  void testExecute_givenStreamServiceGetRoomInfoReturnV3RoomDetail_thenCallsGetRoomInfo() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    GetRoomExecutor getRoomExecutor = new GetRoomExecutor();

    StreamService streamService = mock(StreamService.class);
    when(streamService.getRoomInfo(Mockito.<String>any())).thenReturn(new V3RoomDetail());
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

    ActivityExecutorContext<GetRoom> execution = mock(ActivityExecutorContext.class);
    doNothing().when(execution).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(execution.bdk()).thenReturn(springBdkGateway);
    when(execution.getActivity()).thenReturn(new GetRoom());

    // Act
    getRoomExecutor.execute(execution);

    // Assert
    verify(streamService).getRoomInfo(null);
    verify(execution).bdk();
    verify(execution, atLeast(1)).getActivity();
    verify(execution).setOutputVariable(eq("room"), isA(Object.class));
  }
}
