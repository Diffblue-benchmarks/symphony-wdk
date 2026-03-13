package com.symphony.bdk.workflow.engine.executor.stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.symphony.bdk.core.service.pagination.model.PaginationAttribute;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.gen.api.model.V2AdminStreamFilter;
import com.symphony.bdk.gen.api.model.V2AdminStreamList;
import com.symphony.bdk.gen.api.model.V2AdminStreamType;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.stream.GetStreams;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class GetStreamsExecutorTest {

  private GetStreamsExecutor executor;
  private ActivityExecutorContext<GetStreams> context;
  private BdkGateway bdkGateway;
  private StreamService streamService;
  private Method toFilterMethod;

  @BeforeEach
  void setUp() throws Exception {
    executor = new GetStreamsExecutor();
    context = mock(ActivityExecutorContext.class);
    bdkGateway = mock(BdkGateway.class);
    streamService = mock(StreamService.class);

    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.streams()).thenReturn(streamService);

    toFilterMethod = GetStreamsExecutor.class.getDeclaredMethod("toFilter", GetStreams.class);
    toFilterMethod.setAccessible(true);
  }

  @Test
  void shouldExecuteWithPaginationWhenBothLimitAndSkipAreSet() {
    GetStreams getStreams = new GetStreams();
    getStreams.setId("test-stream");
    getStreams.setLimit(50);
    getStreams.setSkip(10);
    V2AdminStreamList expectedStreams = new V2AdminStreamList();

    when(context.getActivity()).thenReturn(getStreams);
    when(streamService.listStreamsAdmin(any(V2AdminStreamFilter.class), any(PaginationAttribute.class)))
        .thenReturn(expectedStreams);

    executor.execute(context);

    verify(streamService).listStreamsAdmin(any(V2AdminStreamFilter.class),
        argThat(pagination -> pagination.getSkip() == 10 && pagination.getLimit() == 50));
    verify(context).setOutputVariable("streams", expectedStreams);
  }

  @Test
  void shouldExecuteWithoutPaginationWhenBothLimitAndSkipAreNull() {
    GetStreams getStreams = new GetStreams();
    getStreams.setId("test-stream");
    getStreams.setLimit(null);
    getStreams.setSkip(null);
    V2AdminStreamList expectedStreams = new V2AdminStreamList();

    when(context.getActivity()).thenReturn(getStreams);
    when(streamService.listStreamsAdmin(any(V2AdminStreamFilter.class)))
        .thenReturn(expectedStreams);

    executor.execute(context);

    verify(streamService).listStreamsAdmin(any(V2AdminStreamFilter.class));
    verify(context).setOutputVariable("streams", expectedStreams);
  }

  @Test
  void shouldThrowExceptionWhenOnlyLimitIsSet() {
    GetStreams getStreams = new GetStreams();
    getStreams.setId("test-stream");
    getStreams.setLimit(50);
    getStreams.setSkip(null);

    when(context.getActivity()).thenReturn(getStreams);

    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Skip and limit should both be set to get streams test-stream");
  }

  @Test
  void shouldThrowExceptionWhenOnlySkipIsSet() {
    GetStreams getStreams = new GetStreams();
    getStreams.setId("test-stream");
    getStreams.setLimit(null);
    getStreams.setSkip(10);

    when(context.getActivity()).thenReturn(getStreams);

    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Skip and limit should both be set to get streams test-stream");
  }

  @Test
  void shouldCreateFilterWithAllFieldsSet() throws Exception {
    GetStreams getStreams = new GetStreams();
    getStreams.setScope("EXTERNAL");
    getStreams.setOrigin("INTERNAL");
    getStreams.setStatus("ACTIVE");
    getStreams.setPrivacy("PUBLIC");
    getStreams.setStartDate("2024-01-01T00:00:00Z");
    getStreams.setEndDate("2024-12-31T23:59:59Z");
    getStreams.setTypes(Arrays.asList("IM", "ROOM"));

    V2AdminStreamFilter filter = (V2AdminStreamFilter) toFilterMethod.invoke(executor, getStreams);

    assertThat(filter).isNotNull();
    assertThat(filter.getScope()).isEqualTo("EXTERNAL");
    assertThat(filter.getOrigin()).isEqualTo("INTERNAL");
    assertThat(filter.getStatus()).isEqualTo("ACTIVE");
    assertThat(filter.getPrivacy()).isEqualTo("PUBLIC");
    assertThat(filter.getStartDate()).isNotNull();
    assertThat(filter.getEndDate()).isNotNull();
    assertThat(filter.getStreamTypes()).hasSize(2);
    assertThat(filter.getStreamTypes().get(0).getType()).isEqualTo("IM");
    assertThat(filter.getStreamTypes().get(1).getType()).isEqualTo("ROOM");
  }

  @Test
  void shouldCreateFilterWithNullTypes() throws Exception {
    GetStreams getStreams = new GetStreams();
    getStreams.setScope("INTERNAL");
    getStreams.setTypes(null);

    V2AdminStreamFilter filter = (V2AdminStreamFilter) toFilterMethod.invoke(executor, getStreams);

    assertThat(filter).isNotNull();
    assertThat(filter.getScope()).isEqualTo("INTERNAL");
    assertThat(filter.getStreamTypes()).isNull();
  }

  @Test
  void shouldCreateFilterWithEmptyTypes() throws Exception {
    GetStreams getStreams = new GetStreams();
    getStreams.setScope("INTERNAL");
    getStreams.setTypes(Collections.emptyList());

    V2AdminStreamFilter filter = (V2AdminStreamFilter) toFilterMethod.invoke(executor, getStreams);

    assertThat(filter).isNotNull();
    assertThat(filter.getScope()).isEqualTo("INTERNAL");
    assertThat(filter.getStreamTypes()).isEmpty();
  }

  @Test
  void shouldCreateFilterWithSingleType() throws Exception {
    GetStreams getStreams = new GetStreams();
    getStreams.setTypes(Collections.singletonList("POST"));

    V2AdminStreamFilter filter = (V2AdminStreamFilter) toFilterMethod.invoke(executor, getStreams);

    assertThat(filter).isNotNull();
    assertThat(filter.getStreamTypes()).hasSize(1);
    assertThat(filter.getStreamTypes().get(0).getType()).isEqualTo("POST");
  }

  @Test
  void shouldCreateFilterWithMinimalFields() throws Exception {
    GetStreams getStreams = new GetStreams();

    V2AdminStreamFilter filter = (V2AdminStreamFilter) toFilterMethod.invoke(executor, getStreams);

    assertThat(filter).isNotNull();
    assertThat(filter.getScope()).isNull();
    assertThat(filter.getOrigin()).isNull();
    assertThat(filter.getStatus()).isNull();
    assertThat(filter.getPrivacy()).isNull();
  }
}
