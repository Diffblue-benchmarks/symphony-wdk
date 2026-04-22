package com.symphony.devsol.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.core.auth.jwt.UserClaim;
import com.symphony.bdk.workflow.api.v1.dto.VersionedWorkflowView;
import com.symphony.bdk.workflow.management.WorkflowManagementService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.mock.web.MockPart;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

@ContextConfiguration(classes = {AccessFilter.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
class AccessFilterDiffblueTest {
  @Autowired private AccessFilter accessFilter;

  @MockBean private WorkflowManagementService managementService;

  /**
   * Test {@link AccessFilter#doFilterInternal(jakarta.servlet.http.HttpServletRequest,
   * jakarta.servlet.http.HttpServletResponse, FilterChain)}.
   *
   * <ul>
   *   <li>Given GET method.
   *   <li>Then chain doFilter is called and response status is two hundred.
   * </ul>
   *
   * <p>Method under test: {@link AccessFilter#doFilterInternal(jakarta.servlet.http.HttpServletRequest,
   * jakarta.servlet.http.HttpServletResponse, FilterChain)}
   */
  @Test
  @DisplayName(
      "Test doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain); given GET method; then chain doFilter is called")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AccessFilter.doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)"
  })
  void testDoFilterInternal_givenGetMethod_thenChainDoFilterCalled()
      throws ServletException, IOException {
    // Arrange
    MockMultipartHttpServletRequest request =
        new MockMultipartHttpServletRequest(new MockServletContext());
    request.setMethod("GET");
    UserClaim user = new UserClaim();
    user.setId(42L);
    user.setUsername("testuser");
    request.setAttribute("user", user);
    MockHttpServletResponse response = new MockHttpServletResponse();

    FilterChain chain = mock(FilterChain.class);
    doNothing().when(chain).doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());

    // Act
    accessFilter.doFilterInternal(request, response, chain);

    // Assert
    verify(chain).doFilter(isA(ServletRequest.class), isA(ServletResponse.class));
    assertEquals(200, response.getStatus());
    assertFalse(response.isCommitted());
  }

  /**
   * Test {@link AccessFilter#doFilterInternal(jakarta.servlet.http.HttpServletRequest,
   * jakarta.servlet.http.HttpServletResponse, FilterChain)}.
   *
   * <ul>
   *   <li>Given POST method with username "mgmt-token".
   *   <li>Then chain doFilter is called and response status is two hundred.
   * </ul>
   *
   * <p>Method under test: {@link AccessFilter#doFilterInternal(jakarta.servlet.http.HttpServletRequest,
   * jakarta.servlet.http.HttpServletResponse, FilterChain)}
   */
  @Test
  @DisplayName(
      "Test doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain); given POST method with username 'mgmt-token'; then chain doFilter is called")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AccessFilter.doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)"
  })
  void testDoFilterInternal_givenMgmtTokenUsername_thenChainDoFilterCalled()
      throws ServletException, IOException {
    // Arrange
    MockMultipartHttpServletRequest request =
        new MockMultipartHttpServletRequest(new MockServletContext());
    request.setMethod("POST");
    UserClaim user = new UserClaim();
    user.setId(42L);
    user.setUsername("mgmt-token");
    request.setAttribute("user", user);
    MockHttpServletResponse response = new MockHttpServletResponse();

    FilterChain chain = mock(FilterChain.class);
    doNothing().when(chain).doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());

    // Act
    accessFilter.doFilterInternal(request, response, chain);

    // Assert
    verify(chain).doFilter(isA(ServletRequest.class), isA(ServletResponse.class));
    assertEquals(200, response.getStatus());
    assertFalse(response.isCommitted());
  }

  /**
   * Test {@link AccessFilter#doFilterInternal(jakarta.servlet.http.HttpServletRequest,
   * jakarta.servlet.http.HttpServletResponse, FilterChain)}.
   *
   * <ul>
   *   <li>Given POST method with username "webhook".
   *   <li>Then chain doFilter is called and response status is two hundred.
   * </ul>
   *
   * <p>Method under test: {@link AccessFilter#doFilterInternal(jakarta.servlet.http.HttpServletRequest,
   * jakarta.servlet.http.HttpServletResponse, FilterChain)}
   */
  @Test
  @DisplayName(
      "Test doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain); given POST method with username 'webhook'; then chain doFilter is called")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AccessFilter.doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)"
  })
  void testDoFilterInternal_givenWebhookUsername_thenChainDoFilterCalled()
      throws ServletException, IOException {
    // Arrange
    MockMultipartHttpServletRequest request =
        new MockMultipartHttpServletRequest(new MockServletContext());
    request.setMethod("POST");
    UserClaim user = new UserClaim();
    user.setId(42L);
    user.setUsername("webhook");
    request.setAttribute("user", user);
    MockHttpServletResponse response = new MockHttpServletResponse();

    FilterChain chain = mock(FilterChain.class);
    doNothing().when(chain).doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());

    // Act
    accessFilter.doFilterInternal(request, response, chain);

    // Assert
    verify(chain).doFilter(isA(ServletRequest.class), isA(ServletResponse.class));
    assertEquals(200, response.getStatus());
    assertFalse(response.isCommitted());
  }

  /**
   * Test {@link AccessFilter#doFilterInternal(jakarta.servlet.http.HttpServletRequest,
   * jakarta.servlet.http.HttpServletResponse, FilterChain)}.
   *
   * <ul>
   *   <li>Given DELETE request with URL matching workflow pattern.
   *   <li>Given workflow not found (empty Optional).
   *   <li>Then chain doFilter is called.
   * </ul>
   *
   * <p>Method under test: {@link AccessFilter#doFilterInternal(jakarta.servlet.http.HttpServletRequest,
   * jakarta.servlet.http.HttpServletResponse, FilterChain)}
   */
  @Test
  @DisplayName(
      "Test doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain); given DELETE with URL matching workflow pattern and workflow not found; then chain doFilter is called")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AccessFilter.doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)"
  })
  void testDoFilterInternal_givenDeleteWithWorkflowUrlAndWorkflowNotFound_thenChainDoFilterCalled()
      throws ServletException, IOException {
    // Arrange
    MockMultipartHttpServletRequest request =
        new MockMultipartHttpServletRequest(new MockServletContext());
    request.setMethod("DELETE");
    request.setServletPath("/v1/workflows/my-workflow");
    UserClaim user = new UserClaim();
    user.setId(42L);
    user.setUsername("testuser");
    request.setAttribute("user", user);
    MockHttpServletResponse response = new MockHttpServletResponse();

    when(managementService.get("my-workflow")).thenReturn(Optional.empty());

    FilterChain chain = mock(FilterChain.class);
    doNothing().when(chain).doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());

    // Act
    accessFilter.doFilterInternal(request, response, chain);

    // Assert
    verify(chain).doFilter(isA(ServletRequest.class), isA(ServletResponse.class));
    assertEquals(200, response.getStatus());
    assertFalse(response.isCommitted());
  }

  /**
   * Test {@link AccessFilter#doFilterInternal(jakarta.servlet.http.HttpServletRequest,
   * jakarta.servlet.http.HttpServletResponse, FilterChain)}.
   *
   * <ul>
   *   <li>Given DELETE request with URL matching workflow pattern.
   *   <li>Given user is the owner of the workflow.
   *   <li>Then chain doFilter is called.
   * </ul>
   *
   * <p>Method under test: {@link AccessFilter#doFilterInternal(jakarta.servlet.http.HttpServletRequest,
   * jakarta.servlet.http.HttpServletResponse, FilterChain)}
   */
  @Test
  @DisplayName(
      "Test doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain); given DELETE with URL matching workflow pattern and user is owner; then chain doFilter is called")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AccessFilter.doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)"
  })
  void testDoFilterInternal_givenDeleteWithWorkflowUrlAndUserIsOwner_thenChainDoFilterCalled()
      throws ServletException, IOException {
    // Arrange
    long userId = 42L;
    MockMultipartHttpServletRequest request =
        new MockMultipartHttpServletRequest(new MockServletContext());
    request.setMethod("DELETE");
    request.setServletPath("/v1/workflows/my-workflow");
    UserClaim user = new UserClaim();
    user.setId(userId);
    user.setUsername("testuser");
    request.setAttribute("user", user);
    MockHttpServletResponse response = new MockHttpServletResponse();

    VersionedWorkflowView workflow =
        VersionedWorkflowView.builder().workflowId("my-workflow").createdBy(userId).build();
    when(managementService.get("my-workflow")).thenReturn(Optional.of(workflow));

    FilterChain chain = mock(FilterChain.class);
    doNothing().when(chain).doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());

    // Act
    accessFilter.doFilterInternal(request, response, chain);

    // Assert
    verify(chain).doFilter(isA(ServletRequest.class), isA(ServletResponse.class));
    assertEquals(200, response.getStatus());
    assertFalse(response.isCommitted());
  }

  /**
   * Test {@link AccessFilter#doFilterInternal(jakarta.servlet.http.HttpServletRequest,
   * jakarta.servlet.http.HttpServletResponse, FilterChain)}.
   *
   * <ul>
   *   <li>Given DELETE request with URL matching workflow pattern.
   *   <li>Given user does not own the workflow and is not an admin.
   *   <li>Then response sends 401 Unauthorized error.
   * </ul>
   *
   * <p>Method under test: {@link AccessFilter#doFilterInternal(jakarta.servlet.http.HttpServletRequest,
   * jakarta.servlet.http.HttpServletResponse, FilterChain)}
   */
  @Test
  @DisplayName(
      "Test doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain); given DELETE with URL matching workflow pattern and user does not own workflow; then response is Unauthorized")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AccessFilter.doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)"
  })
  void testDoFilterInternal_givenDeleteWithWorkflowUrlAndUserNotOwner_thenUnauthorized()
      throws ServletException, IOException {
    // Arrange
    MockMultipartHttpServletRequest request =
        new MockMultipartHttpServletRequest(new MockServletContext());
    request.setMethod("DELETE");
    request.setServletPath("/v1/workflows/my-workflow");
    UserClaim user = new UserClaim();
    user.setId(42L);
    user.setUsername("testuser");
    request.setAttribute("user", user);
    MockHttpServletResponse response = new MockHttpServletResponse();

    VersionedWorkflowView workflow =
        VersionedWorkflowView.builder().workflowId("my-workflow").createdBy(999L).build();
    when(managementService.get("my-workflow")).thenReturn(Optional.of(workflow));

    FilterChain chain = mock(FilterChain.class);

    // Act
    accessFilter.doFilterInternal(request, response, chain);

    // Assert
    assertEquals(401, response.getStatus());
    assertEquals("You are not allowed to modify this workflow", response.getErrorMessage());
    assertTrue(response.isCommitted());
  }

  /**
   * Test {@link AccessFilter#doFilterInternal(jakarta.servlet.http.HttpServletRequest,
   * jakarta.servlet.http.HttpServletResponse, FilterChain)}.
   *
   * <ul>
   *   <li>Given POST request without URL matching workflow pattern.
   *   <li>Given user ID matches createdBy part.
   *   <li>Given swadl part has matching id.
   *   <li>Given workflow not found.
   *   <li>Then chain doFilter is called.
   * </ul>
   *
   * <p>Method under test: {@link AccessFilter#doFilterInternal(jakarta.servlet.http.HttpServletRequest,
   * jakarta.servlet.http.HttpServletResponse, FilterChain)}
   */
  @Test
  @DisplayName(
      "Test doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain); given POST with no URL match user matches createdBy and workflow not found; then chain doFilter is called")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AccessFilter.doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)"
  })
  void testDoFilterInternal_givenPostWithNoUrlMatchAndUserMatchesCreatedBy_thenChainDoFilterCalled()
      throws ServletException, IOException {
    // Arrange
    long userId = 42L;
    MockMultipartHttpServletRequest request =
        new MockMultipartHttpServletRequest(new MockServletContext());
    request.setMethod("POST");
    request.setServletPath("/v1/workflows");
    UserClaim user = new UserClaim();
    user.setId(userId);
    user.setUsername("testuser");
    request.setAttribute("user", user);
    request.addPart(new MockPart("createdBy", String.valueOf(userId).getBytes()));
    request.addPart(new MockPart("swadl", "id: my-workflow\nother: content".getBytes()));
    MockHttpServletResponse response = new MockHttpServletResponse();

    when(managementService.get("my-workflow")).thenReturn(Optional.empty());

    FilterChain chain = mock(FilterChain.class);
    doNothing().when(chain).doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());

    // Act
    accessFilter.doFilterInternal(request, response, chain);

    // Assert
    verify(chain).doFilter(isA(ServletRequest.class), isA(ServletResponse.class));
    assertEquals(200, response.getStatus());
    assertFalse(response.isCommitted());
  }

  /**
   * Test {@link AccessFilter#doFilterInternal(jakarta.servlet.http.HttpServletRequest,
   * jakarta.servlet.http.HttpServletResponse, FilterChain)}.
   *
   * <ul>
   *   <li>Given POST request without URL matching workflow pattern.
   *   <li>Given user ID does not match createdBy part and is not an admin.
   *   <li>Then response sends 401 Unauthorized error.
   * </ul>
   *
   * <p>Method under test: {@link AccessFilter#doFilterInternal(jakarta.servlet.http.HttpServletRequest,
   * jakarta.servlet.http.HttpServletResponse, FilterChain)}
   */
  @Test
  @DisplayName(
      "Test doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain); given POST with no URL match and user does not match createdBy; then response is Unauthorized")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AccessFilter.doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)"
  })
  void testDoFilterInternal_givenPostWithNoUrlMatchAndUserNotMatchCreatedBy_thenUnauthorized()
      throws ServletException, IOException {
    // Arrange
    MockMultipartHttpServletRequest request =
        new MockMultipartHttpServletRequest(new MockServletContext());
    request.setMethod("POST");
    request.setServletPath("/v1/workflows");
    UserClaim user = new UserClaim();
    user.setId(42L);
    user.setUsername("testuser");
    request.setAttribute("user", user);
    request.addPart(new MockPart("createdBy", "999".getBytes()));
    MockHttpServletResponse response = new MockHttpServletResponse();

    FilterChain chain = mock(FilterChain.class);

    // Act
    accessFilter.doFilterInternal(request, response, chain);

    // Assert
    assertEquals(401, response.getStatus());
    assertEquals(
        "Your identity does not match the provided author", response.getErrorMessage());
    assertTrue(response.isCommitted());
  }
}
