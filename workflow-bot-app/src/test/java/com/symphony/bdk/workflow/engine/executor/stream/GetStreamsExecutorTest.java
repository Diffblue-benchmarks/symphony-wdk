package com.symphony.bdk.workflow.engine.executor.stream;

import com.symphony.bdk.core.service.pagination.model.PaginationAttribute;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.gen.api.model.V2AdminStreamFilter;
import com.symphony.bdk.gen.api.model.V2AdminStreamList;
import com.symphony.bdk.gen.api.model.V2AdminStreamType;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.stream.GetStreams;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetStreamsExecutorTest {

  @InjectMocks
  private GetStreamsExecutor executor;

  @Mock
  private ActivityExecutorContext<GetStreams> context;

  @Mock
  private BdkGateway bdk;

  @Mock
  private StreamService streamService;

  @Test
  void shouldGetStreamsWithoutPagination() {
    GetStreams activity = new GetStreams();
    activity.setId("get-streams");

    V2AdminStreamList streamList = new V2AdminStreamList();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);
    when(streamService.listStreamsAdmin(any(V2AdminStreamFilter.class))).thenReturn(streamList);

    executor.execute(context);

    verify(streamService).listStreamsAdmin(any(V2AdminStreamFilter.class));
    verify(context).setOutputVariable("streams", streamList);
  }

  @Test
  void shouldGetStreamsWithPagination() {
    GetStreams activity = new GetStreams();
    activity.setId("get-streams");
    activity.setLimit(10);
    activity.setSkip(5);

    V2AdminStreamList streamList = new V2AdminStreamList();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);
    when(streamService.listStreamsAdmin(any(V2AdminStreamFilter.class), any(PaginationAttribute.class)))
        .thenReturn(streamList);

    executor.execute(context);

    verify(streamService).listStreamsAdmin(any(V2AdminStreamFilter.class), any(PaginationAttribute.class));
    verify(context).setOutputVariable("streams", streamList);
  }

  @Test
  void shouldThrowWhenOnlyLimitIsSet() {
    GetStreams activity = new GetStreams();
    activity.setId("get-streams-only-limit");
    activity.setLimit(10);

    when(context.getActivity()).thenReturn(activity);

    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Skip and limit should both be set to get streams");
  }

  @Test
  void shouldThrowWhenOnlySkipIsSet() {
    GetStreams activity = new GetStreams();
    activity.setId("get-streams-only-skip");
    activity.setSkip(5);

    when(context.getActivity()).thenReturn(activity);

    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Skip and limit should both be set to get streams");
  }

  @Test
  void shouldBuildFilterWithAllFields() {
    GetStreams activity = new GetStreams();
    activity.setId("get-streams");
    activity.setScope("INTERNAL");
    activity.setOrigin("INTERNAL");
    activity.setStatus("ACTIVE");
    activity.setPrivacy("PRIVATE");
    activity.setTypes(List.of("ROOM", "IM"));

    V2AdminStreamList streamList = new V2AdminStreamList();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);
    when(streamService.listStreamsAdmin(any(V2AdminStreamFilter.class))).thenReturn(streamList);

    executor.execute(context);

    ArgumentCaptor<V2AdminStreamFilter> filterCaptor = ArgumentCaptor.forClass(V2AdminStreamFilter.class);
    verify(streamService).listStreamsAdmin(filterCaptor.capture());
    V2AdminStreamFilter filter = filterCaptor.getValue();
    assertThat(filter.getScope()).isEqualTo("INTERNAL");
    assertThat(filter.getOrigin()).isEqualTo("INTERNAL");
    assertThat(filter.getStatus()).isEqualTo("ACTIVE");
    assertThat(filter.getPrivacy()).isEqualTo("PRIVATE");
    assertThat(filter.getStreamTypes()).extracting(V2AdminStreamType::getType)
        .containsExactly("ROOM", "IM");
  }

  @Test
  void shouldBuildFilterWithNoTypes() {
    GetStreams activity = new GetStreams();
    activity.setId("get-streams");
    activity.setScope("EXTERNAL");

    V2AdminStreamList streamList = new V2AdminStreamList();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);
    when(streamService.listStreamsAdmin(any(V2AdminStreamFilter.class))).thenReturn(streamList);

    executor.execute(context);

    ArgumentCaptor<V2AdminStreamFilter> filterCaptor = ArgumentCaptor.forClass(V2AdminStreamFilter.class);
    verify(streamService).listStreamsAdmin(filterCaptor.capture());
    assertThat(filterCaptor.getValue().getStreamTypes()).isNull();
  }
}
