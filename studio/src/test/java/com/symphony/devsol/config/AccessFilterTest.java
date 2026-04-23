package com.symphony.devsol.config;

import com.symphony.bdk.core.auth.jwt.UserClaim;
import com.symphony.bdk.workflow.api.v1.dto.VersionedWorkflowView;
import com.symphony.bdk.workflow.management.WorkflowManagementService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccessFilterTest {

    @Mock
    private WorkflowManagementService managementService;

    @InjectMocks
    private AccessFilter accessFilter;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain chain;

    @Mock
    private UserClaim user;

    @Mock
    private Part createdByPart;

    @Mock
    private Part swadlPart;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(accessFilter, "admins", List.of());
        when(request.getAttribute("user")).thenReturn(user);
    }

    @Test
    void doFilterInternal_whenGetRequest_shouldPassThrough() throws Exception {
        when(request.getMethod()).thenReturn("GET");

        accessFilter.doFilterInternal(request, response, chain);

        verify(chain).doFilter(request, response);
        verify(response, never()).sendError(eq(SC_UNAUTHORIZED), anyString());
    }

    @Test
    void doFilterInternal_whenOptionsRequest_shouldPassThrough() throws Exception {
        when(request.getMethod()).thenReturn("OPTIONS");

        accessFilter.doFilterInternal(request, response, chain);

        verify(chain).doFilter(request, response);
        verify(response, never()).sendError(eq(SC_UNAUTHORIZED), anyString());
    }

    @Test
    void doFilterInternal_whenMgmtTokenUser_shouldPassThrough() throws Exception {
        when(request.getMethod()).thenReturn("POST");
        when(user.getUsername()).thenReturn("mgmt-token");

        accessFilter.doFilterInternal(request, response, chain);

        verify(chain).doFilter(request, response);
        verify(response, never()).sendError(eq(SC_UNAUTHORIZED), anyString());
    }

    @Test
    void doFilterInternal_whenWebhookUser_shouldPassThrough() throws Exception {
        when(request.getMethod()).thenReturn("DELETE");
        when(user.getUsername()).thenReturn("webhook");

        accessFilter.doFilterInternal(request, response, chain);

        verify(chain).doFilter(request, response);
        verify(response, never()).sendError(eq(SC_UNAUTHORIZED), anyString());
    }

    @Test
    void doFilterInternal_whenWorkflowIdInPath_andUserIsOwner_shouldPassThrough() throws Exception {
        long userId = 100L;
        when(request.getMethod()).thenReturn("DELETE");
        when(user.getUsername()).thenReturn("user1");
        when(user.getId()).thenReturn(userId);
        when(request.getServletPath()).thenReturn("/v1/workflows/my-workflow");

        VersionedWorkflowView workflow = VersionedWorkflowView.builder()
                .workflowId("my-workflow")
                .createdBy(userId)
                .build();
        when(managementService.get("my-workflow")).thenReturn(Optional.of(workflow));

        accessFilter.doFilterInternal(request, response, chain);

        verify(chain).doFilter(request, response);
        verify(response, never()).sendError(eq(SC_UNAUTHORIZED), anyString());
    }

    @Test
    void doFilterInternal_whenWorkflowIdInPath_andUserIsNotOwnerAndNotAdmin_shouldReturnUnauthorized() throws Exception {
        long userId = 100L;
        long ownerId = 200L;
        when(request.getMethod()).thenReturn("DELETE");
        when(user.getUsername()).thenReturn("user1");
        when(user.getId()).thenReturn(userId);
        when(request.getServletPath()).thenReturn("/v1/workflows/my-workflow");

        VersionedWorkflowView workflow = VersionedWorkflowView.builder()
                .workflowId("my-workflow")
                .createdBy(ownerId)
                .build();
        when(managementService.get("my-workflow")).thenReturn(Optional.of(workflow));

        accessFilter.doFilterInternal(request, response, chain);

        verify(response).sendError(SC_UNAUTHORIZED, "You are not allowed to modify this workflow");
        verify(chain, never()).doFilter(request, response);
    }

    @Test
    void doFilterInternal_whenWorkflowIdInPath_andUserIsAdmin_shouldPassThrough() throws Exception {
        long userId = 100L;
        long ownerId = 200L;
        ReflectionTestUtils.setField(accessFilter, "admins", List.of(userId));
        when(request.getMethod()).thenReturn("DELETE");
        when(user.getUsername()).thenReturn("user1");
        when(user.getId()).thenReturn(userId);
        when(request.getServletPath()).thenReturn("/v1/workflows/my-workflow");

        VersionedWorkflowView workflow = VersionedWorkflowView.builder()
                .workflowId("my-workflow")
                .createdBy(ownerId)
                .build();
        when(managementService.get("my-workflow")).thenReturn(Optional.of(workflow));

        accessFilter.doFilterInternal(request, response, chain);

        verify(chain).doFilter(request, response);
        verify(response, never()).sendError(eq(SC_UNAUTHORIZED), anyString());
    }

    @Test
    void doFilterInternal_whenWorkflowNotFound_shouldPassThrough() throws Exception {
        long userId = 100L;
        when(request.getMethod()).thenReturn("DELETE");
        when(user.getUsername()).thenReturn("user1");
        when(user.getId()).thenReturn(userId);
        when(request.getServletPath()).thenReturn("/v1/workflows/my-workflow");
        when(managementService.get("my-workflow")).thenReturn(Optional.empty());

        accessFilter.doFilterInternal(request, response, chain);

        verify(chain).doFilter(request, response);
        verify(response, never()).sendError(eq(SC_UNAUTHORIZED), anyString());
    }

    @Test
    void doFilterInternal_whenPostWithNoPathWorkflowId_andAuthorMatchesUser_shouldPassThrough() throws Exception {
        long userId = 100L;
        when(request.getMethod()).thenReturn("POST");
        when(user.getUsername()).thenReturn("user1");
        when(user.getId()).thenReturn(userId);
        when(request.getServletPath()).thenReturn("/v1/workflows");

        when(createdByPart.getInputStream())
                .thenReturn(new ByteArrayInputStream(String.valueOf(userId).getBytes()));
        when(request.getPart("createdBy")).thenReturn(createdByPart);

        when(swadlPart.getInputStream())
                .thenReturn(new ByteArrayInputStream("id: my-workflow\nversion: 1".getBytes()));
        when(request.getPart("swadl")).thenReturn(swadlPart);

        when(managementService.get("my-workflow")).thenReturn(Optional.empty());

        accessFilter.doFilterInternal(request, response, chain);

        verify(chain).doFilter(request, response);
        verify(response, never()).sendError(eq(SC_UNAUTHORIZED), anyString());
    }

    @Test
    void doFilterInternal_whenPostWithNoPathWorkflowId_andAuthorDoesNotMatchUser_shouldReturnUnauthorized()
            throws Exception {
        long userId = 100L;
        long authorId = 200L;
        when(request.getMethod()).thenReturn("POST");
        when(user.getUsername()).thenReturn("user1");
        when(user.getId()).thenReturn(userId);
        when(request.getServletPath()).thenReturn("/v1/workflows");

        when(createdByPart.getInputStream())
                .thenReturn(new ByteArrayInputStream(String.valueOf(authorId).getBytes()));
        when(request.getPart("createdBy")).thenReturn(createdByPart);

        accessFilter.doFilterInternal(request, response, chain);

        verify(response).sendError(SC_UNAUTHORIZED, "Your identity does not match the provided author");
        verify(chain, never()).doFilter(request, response);
    }

    @Test
    void doFilterInternal_whenPostWithNoPathWorkflowId_andUserIsAdmin_shouldPassThrough() throws Exception {
        long userId = 100L;
        long authorId = 200L;
        ReflectionTestUtils.setField(accessFilter, "admins", List.of(userId));
        when(request.getMethod()).thenReturn("POST");
        when(user.getUsername()).thenReturn("user1");
        when(user.getId()).thenReturn(userId);
        when(request.getServletPath()).thenReturn("/v1/workflows");

        when(createdByPart.getInputStream())
                .thenReturn(new ByteArrayInputStream(String.valueOf(authorId).getBytes()));
        when(request.getPart("createdBy")).thenReturn(createdByPart);

        when(swadlPart.getInputStream())
                .thenReturn(new ByteArrayInputStream("id: my-workflow\nversion: 1".getBytes()));
        when(request.getPart("swadl")).thenReturn(swadlPart);

        when(managementService.get("my-workflow")).thenReturn(Optional.empty());

        accessFilter.doFilterInternal(request, response, chain);

        verify(chain).doFilter(request, response);
        verify(response, never()).sendError(eq(SC_UNAUTHORIZED), anyString());
    }

    @Test
    void doFilterInternal_whenPutWithNoPathWorkflowId_andSwadlHasWorkflowId_andOwnerDiffers_shouldReturnUnauthorized()
            throws Exception {
        long userId = 100L;
        long ownerId = 300L;
        when(request.getMethod()).thenReturn("PUT");
        when(user.getUsername()).thenReturn("user1");
        when(user.getId()).thenReturn(userId);
        when(request.getServletPath()).thenReturn("/v1/workflows");

        when(createdByPart.getInputStream())
                .thenReturn(new ByteArrayInputStream(String.valueOf(userId).getBytes()));
        when(request.getPart("createdBy")).thenReturn(createdByPart);

        when(swadlPart.getInputStream())
                .thenReturn(new ByteArrayInputStream("id: existing-workflow\nversion: 2".getBytes()));
        when(request.getPart("swadl")).thenReturn(swadlPart);

        VersionedWorkflowView workflow = VersionedWorkflowView.builder()
                .workflowId("existing-workflow")
                .createdBy(ownerId)
                .build();
        when(managementService.get("existing-workflow")).thenReturn(Optional.of(workflow));

        accessFilter.doFilterInternal(request, response, chain);

        verify(response).sendError(SC_UNAUTHORIZED, "You are not allowed to modify this workflow");
        verify(chain, never()).doFilter(request, response);
    }
}
