package com.symphony.bdk.workflow.engine.executor.stream;

import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.pagination.model.PaginationAttribute;
import com.symphony.bdk.core.service.stream.OboStreamService;
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

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetUserStreamsExecutorTest {

    @Mock
    private ActivityExecutorContext<GetUserStreams> context;

    @Mock
    private BdkGateway bdkGateway;

    @Mock
    private OboServices oboServices;

    @Mock
    private OboStreamService oboStreamService;

    @Mock
    private AuthSession authSession;

    @InjectMocks
    private GetUserStreamsExecutor executor;

    @Test
    void executeWithOboAndPaginationShouldCallOboStreamServiceWithPagination() {
        // Arrange
        GetUserStreams activity = new GetUserStreams();
        Obo obo = new Obo();
        obo.setUsername("test.user");
        activity.setObo(obo);
        activity.setLimit(10);
        activity.setSkip(5);
        activity.setIncludeInactiveStreams(true);

        StreamAttributes stream1 = new StreamAttributes();
        StreamAttributes stream2 = new StreamAttributes();
        List<StreamAttributes> expectedStreams = Arrays.asList(stream1, stream2);

        when(context.getActivity()).thenReturn(activity);
        when(context.bdk()).thenReturn(bdkGateway);
        when(bdkGateway.obo("test.user")).thenReturn(authSession);
        when(bdkGateway.obo(authSession)).thenReturn(oboServices);
        when(oboServices.streams()).thenReturn(oboStreamService);
        when(oboStreamService.listStreams(any(StreamFilter.class), any(PaginationAttribute.class)))
            .thenReturn(expectedStreams);

        // Act
        executor.execute(context);

        // Assert
        ArgumentCaptor<StreamFilter> filterCaptor = ArgumentCaptor.forClass(StreamFilter.class);
        ArgumentCaptor<PaginationAttribute> paginationCaptor = ArgumentCaptor.forClass(PaginationAttribute.class);
        verify(oboStreamService).listStreams(filterCaptor.capture(), paginationCaptor.capture());

        StreamFilter capturedFilter = filterCaptor.getValue();
        assertNotNull(capturedFilter);
        assertEquals(true, capturedFilter.getIncludeInactiveStreams());

        PaginationAttribute capturedPagination = paginationCaptor.getValue();
        assertNotNull(capturedPagination);
        assertEquals(5, capturedPagination.getSkip());
        assertEquals(10, capturedPagination.getLimit());

        verify(context).setOutputVariable(eq("streams"), eq(expectedStreams));
    }

    @Test
    void executeWithOboAndNoPaginationShouldCallOboStreamServiceWithoutPagination() {
        // Arrange
        GetUserStreams activity = new GetUserStreams();
        Obo obo = new Obo();
        obo.setUserId(12345L);
        activity.setObo(obo);
        activity.setIncludeInactiveStreams(false);

        StreamAttributes stream1 = new StreamAttributes();
        List<StreamAttributes> expectedStreams = Arrays.asList(stream1);

        when(context.getActivity()).thenReturn(activity);
        when(context.bdk()).thenReturn(bdkGateway);
        when(bdkGateway.obo(12345L)).thenReturn(authSession);
        when(bdkGateway.obo(authSession)).thenReturn(oboServices);
        when(oboServices.streams()).thenReturn(oboStreamService);
        when(oboStreamService.listStreams(any(StreamFilter.class))).thenReturn(expectedStreams);

        // Act
        executor.execute(context);

        // Assert
        ArgumentCaptor<StreamFilter> filterCaptor = ArgumentCaptor.forClass(StreamFilter.class);
        verify(oboStreamService).listStreams(filterCaptor.capture());

        StreamFilter capturedFilter = filterCaptor.getValue();
        assertNotNull(capturedFilter);
        assertEquals(false, capturedFilter.getIncludeInactiveStreams());

        verify(context).setOutputVariable(eq("streams"), eq(expectedStreams));
    }

    @Test
    void executeWithOboAndOnlyLimitSetShouldCallOboStreamServiceWithoutPagination() {
        // Arrange
        GetUserStreams activity = new GetUserStreams();
        Obo obo = new Obo();
        obo.setUsername("test.user");
        activity.setObo(obo);
        activity.setLimit(10);
        // skip is null

        StreamAttributes stream1 = new StreamAttributes();
        List<StreamAttributes> expectedStreams = Arrays.asList(stream1);

        when(context.getActivity()).thenReturn(activity);
        when(context.bdk()).thenReturn(bdkGateway);
        when(bdkGateway.obo("test.user")).thenReturn(authSession);
        when(bdkGateway.obo(authSession)).thenReturn(oboServices);
        when(oboServices.streams()).thenReturn(oboStreamService);
        when(oboStreamService.listStreams(any(StreamFilter.class))).thenReturn(expectedStreams);

        // Act
        executor.execute(context);

        // Assert
        verify(oboStreamService).listStreams(any(StreamFilter.class));
        verify(context).setOutputVariable(eq("streams"), eq(expectedStreams));
    }

    @Test
    void executeWithOboAndOnlySkipSetShouldCallOboStreamServiceWithoutPagination() {
        // Arrange
        GetUserStreams activity = new GetUserStreams();
        Obo obo = new Obo();
        obo.setUsername("test.user");
        activity.setObo(obo);
        activity.setSkip(5);
        // limit is null

        StreamAttributes stream1 = new StreamAttributes();
        List<StreamAttributes> expectedStreams = Arrays.asList(stream1);

        when(context.getActivity()).thenReturn(activity);
        when(context.bdk()).thenReturn(bdkGateway);
        when(bdkGateway.obo("test.user")).thenReturn(authSession);
        when(bdkGateway.obo(authSession)).thenReturn(oboServices);
        when(oboServices.streams()).thenReturn(oboStreamService);
        when(oboStreamService.listStreams(any(StreamFilter.class))).thenReturn(expectedStreams);

        // Act
        executor.execute(context);

        // Assert
        verify(oboStreamService).listStreams(any(StreamFilter.class));
        verify(context).setOutputVariable(eq("streams"), eq(expectedStreams));
    }
}
