package com.symphony.bdk.workflow.engine.executor.stream;

import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.pagination.model.PaginationAttribute;
import com.symphony.bdk.core.service.stream.OboStreamService;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.gen.api.model.StreamAttributes;
import com.symphony.bdk.gen.api.model.StreamFilter;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.stream.GetUserStreams;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetUserStreamsExecutorTest {

  @InjectMocks
  private GetUserStreamsExecutor executor;

  @Mock
  private ActivityExecutorContext<GetUserStreams> context;

  @Mock
  private BdkGateway bdk;

  @Mock
  private StreamService streamService;

  @Mock
  private OboServices oboServices;

  @Mock
  private OboStreamService oboStreamService;

  @Mock
  private AuthSession authSession;

  @Test
  void shouldExecuteNoPaginationWhenLimitAndSkipAreNull() throws Exception {
    // given
    GetUserStreams activity = new GetUserStreams();
    activity.setLimit(null);
    activity.setSkip(null);

    List<StreamAttributes> streams = Collections.emptyList();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);
    when(streamService.listStreams(any(StreamFilter.class))).thenReturn(streams);

    // when
    executor.execute(context);

    // then
    verify(context).setOutputVariable("streams", streams);
  }

  @Test
  void shouldExecuteWithPaginationWhenLimitAndSkipAreSet() throws Exception {
    // given
    GetUserStreams activity = new GetUserStreams();
    activity.setLimit(10);
    activity.setSkip(5);

    List<StreamAttributes> streams = Collections.singletonList(new StreamAttributes());

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);
    when(streamService.listStreams(any(StreamFilter.class), any(PaginationAttribute.class))).thenReturn(streams);

    // when
    executor.execute(context);

    // then
    verify(context).setOutputVariable("streams", streams);
  }

  @Test
  void shouldExecuteOboPathWhenOboUsernameConfigured() throws Exception {
    // given
    Obo obo = new Obo();
    obo.setUsername("obo-user");

    GetUserStreams activity = new GetUserStreams();
    activity.setObo(obo);

    List<StreamAttributes> streams = Collections.emptyList();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.obo("obo-user")).thenReturn(authSession);
    when(bdk.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.listStreams(any(StreamFilter.class))).thenReturn(streams);

    // when
    executor.execute(context);

    // then
    verify(context).setOutputVariable("streams", streams);
  }

  @Test
  void shouldExecuteOboPathWhenOboUserIdConfigured() throws Exception {
    // given
    Obo obo = new Obo();
    obo.setUserId(12345L);

    GetUserStreams activity = new GetUserStreams();
    activity.setObo(obo);

    List<StreamAttributes> streams = Collections.singletonList(new StreamAttributes());

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.obo(12345L)).thenReturn(authSession);
    when(bdk.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.listStreams(any(StreamFilter.class))).thenReturn(streams);

    // when
    executor.execute(context);

    // then
    verify(context).setOutputVariable("streams", streams);
  }

  @Test
  void shouldExecuteOboPathWithPaginationWhenOboUsernameAndLimitAndSkipConfigured() throws Exception {
    // given
    Obo obo = new Obo();
    obo.setUsername("obo-user");

    GetUserStreams activity = new GetUserStreams();
    activity.setObo(obo);
    activity.setLimit(10);
    activity.setSkip(0);

    List<StreamAttributes> streams = Collections.emptyList();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.obo("obo-user")).thenReturn(authSession);
    when(bdk.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.listStreams(any(StreamFilter.class), any(PaginationAttribute.class))).thenReturn(streams);

    // when
    executor.execute(context);

    // then
    verify(context).setOutputVariable("streams", streams);
  }

  @Test
  void shouldIncludeStreamTypesInFilterWhenTypesProvided() throws Exception {
    // given
    GetUserStreams activity = new GetUserStreams();
    activity.setTypes(List.of("IM", "MIM"));
    activity.setIncludeInactiveStreams(true);

    List<StreamAttributes> streams = Collections.emptyList();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);
    ArgumentCaptor<StreamFilter> filterCaptor = ArgumentCaptor.forClass(StreamFilter.class);
    when(streamService.listStreams(filterCaptor.capture())).thenReturn(streams);

    // when
    executor.execute(context);

    // then
    StreamFilter capturedFilter = filterCaptor.getValue();
    assertThat(capturedFilter.getStreamTypes()).hasSize(2);
    assertThat(capturedFilter.getIncludeInactiveStreams()).isTrue();
    verify(context).setOutputVariable("streams", streams);
  }

  @Test
  void shouldUseNullStreamTypesInFilterWhenTypesNotProvided() throws Exception {
    // given
    GetUserStreams activity = new GetUserStreams();
    activity.setTypes(null);
    activity.setIncludeInactiveStreams(false);

    List<StreamAttributes> streams = Collections.emptyList();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);
    ArgumentCaptor<StreamFilter> filterCaptor = ArgumentCaptor.forClass(StreamFilter.class);
    when(streamService.listStreams(filterCaptor.capture())).thenReturn(streams);

    // when
    executor.execute(context);

    // then
    StreamFilter capturedFilter = filterCaptor.getValue();
    assertThat(capturedFilter.getStreamTypes()).isNull();
    assertThat(capturedFilter.getIncludeInactiveStreams()).isFalse();
  }
}
