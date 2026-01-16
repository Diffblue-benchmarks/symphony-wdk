package com.symphony.bdk.workflow.security;

import com.symphony.bdk.workflow.api.v1.WorkflowsApi;
import com.symphony.bdk.workflow.api.v1.WorkflowsMgtApi;
import com.symphony.bdk.workflow.configuration.WorkflowBotConfiguration;
import com.symphony.bdk.workflow.exception.UnauthorizedException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthorizationAspectClaudeTest {

  private WorkflowBotConfiguration mockConfiguration;
  private AuthorizationAspect authorizationAspect;
  private MockHttpServletRequest mockRequest;

  @BeforeEach
  void setUp() {
    mockConfiguration = mock(WorkflowBotConfiguration.class);
    authorizationAspect = new AuthorizationAspect(mockConfiguration);
    mockRequest = new MockHttpServletRequest();
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockRequest));
  }

  @AfterEach
  void tearDown() {
    RequestContextHolder.resetRequestAttributes();
  }

  @Test
  void testConstructor_initializesWithConfiguration() {
    // Test that constructor properly initializes the aspect with configuration
    WorkflowBotConfiguration config = mock(WorkflowBotConfiguration.class);
    AuthorizationAspect aspect = new AuthorizationAspect(config);

    assertThat(aspect).isNotNull();
  }

  @Test
  void testAuthorizationCheck_withValidManagementToken() {
    // Test that authorization passes with valid management token
    String validToken = "valid-management-token";
    when(mockConfiguration.getManagementToken()).thenReturn(validToken);
    mockRequest.addHeader(WorkflowsMgtApi.X_MANAGEMENT_TOKEN_KEY, validToken);

    Authorized authorized = createAuthorized(WorkflowsMgtApi.X_MANAGEMENT_TOKEN_KEY);

    // Should not throw exception
    authorizationAspect.authorizationCheck(authorized);
  }

  @Test
  void testAuthorizationCheck_withValidMonitoringToken() {
    // Test that authorization passes with valid monitoring token
    String validToken = "valid-monitoring-token";
    when(mockConfiguration.getMonitoringToken()).thenReturn(validToken);
    mockRequest.addHeader(WorkflowsApi.X_MONITORING_TOKEN_KEY, validToken);

    Authorized authorized = createAuthorized(WorkflowsApi.X_MONITORING_TOKEN_KEY);

    // Should not throw exception
    authorizationAspect.authorizationCheck(authorized);
  }

  @Test
  void testAuthorizationCheck_withInvalidManagementToken() {
    // Test that authorization fails with invalid management token
    String validToken = "valid-management-token";
    String invalidToken = "invalid-token";
    when(mockConfiguration.getManagementToken()).thenReturn(validToken);
    mockRequest.addHeader(WorkflowsMgtApi.X_MANAGEMENT_TOKEN_KEY, invalidToken);

    Authorized authorized = createAuthorized(WorkflowsMgtApi.X_MANAGEMENT_TOKEN_KEY);

    assertThatThrownBy(() -> authorizationAspect.authorizationCheck(authorized))
        .isInstanceOf(UnauthorizedException.class)
        .hasMessage("Request is not authorised");
  }

  @Test
  void testAuthorizationCheck_withInvalidMonitoringToken() {
    // Test that authorization fails with invalid monitoring token
    String validToken = "valid-monitoring-token";
    String invalidToken = "invalid-token";
    when(mockConfiguration.getMonitoringToken()).thenReturn(validToken);
    mockRequest.addHeader(WorkflowsApi.X_MONITORING_TOKEN_KEY, invalidToken);

    Authorized authorized = createAuthorized(WorkflowsApi.X_MONITORING_TOKEN_KEY);

    assertThatThrownBy(() -> authorizationAspect.authorizationCheck(authorized))
        .isInstanceOf(UnauthorizedException.class)
        .hasMessage("Request is not authorised");
  }

  @Test
  void testAuthorizationCheck_withBlankManagementTokenInConfiguration() {
    // Test that authorization fails when management token in configuration is blank
    when(mockConfiguration.getManagementToken()).thenReturn("");
    mockRequest.addHeader(WorkflowsMgtApi.X_MANAGEMENT_TOKEN_KEY, "some-token");

    Authorized authorized = createAuthorized(WorkflowsMgtApi.X_MANAGEMENT_TOKEN_KEY);

    assertThatThrownBy(() -> authorizationAspect.authorizationCheck(authorized))
        .isInstanceOf(UnauthorizedException.class)
        .hasMessage("Request is not authorised");
  }

  @Test
  void testAuthorizationCheck_withBlankMonitoringTokenInConfiguration() {
    // Test that authorization fails when monitoring token in configuration is blank
    when(mockConfiguration.getMonitoringToken()).thenReturn("");
    mockRequest.addHeader(WorkflowsApi.X_MONITORING_TOKEN_KEY, "some-token");

    Authorized authorized = createAuthorized(WorkflowsApi.X_MONITORING_TOKEN_KEY);

    assertThatThrownBy(() -> authorizationAspect.authorizationCheck(authorized))
        .isInstanceOf(UnauthorizedException.class)
        .hasMessage("Request is not authorised");
  }

  @Test
  void testAuthorizationCheck_withNullManagementTokenInConfiguration() {
    // Test that authorization fails when management token in configuration is null
    when(mockConfiguration.getManagementToken()).thenReturn(null);
    mockRequest.addHeader(WorkflowsMgtApi.X_MANAGEMENT_TOKEN_KEY, "some-token");

    Authorized authorized = createAuthorized(WorkflowsMgtApi.X_MANAGEMENT_TOKEN_KEY);

    assertThatThrownBy(() -> authorizationAspect.authorizationCheck(authorized))
        .isInstanceOf(UnauthorizedException.class)
        .hasMessage("Request is not authorised");
  }

  @Test
  void testAuthorizationCheck_withNullMonitoringTokenInConfiguration() {
    // Test that authorization fails when monitoring token in configuration is null
    when(mockConfiguration.getMonitoringToken()).thenReturn(null);
    mockRequest.addHeader(WorkflowsApi.X_MONITORING_TOKEN_KEY, "some-token");

    Authorized authorized = createAuthorized(WorkflowsApi.X_MONITORING_TOKEN_KEY);

    assertThatThrownBy(() -> authorizationAspect.authorizationCheck(authorized))
        .isInstanceOf(UnauthorizedException.class)
        .hasMessage("Request is not authorised");
  }

  @Test
  void testAuthorizationCheck_withMissingManagementTokenInRequest() {
    // Test that authorization fails when management token is missing in request header
    String validToken = "valid-management-token";
    when(mockConfiguration.getManagementToken()).thenReturn(validToken);
    // Not adding the header to request

    Authorized authorized = createAuthorized(WorkflowsMgtApi.X_MANAGEMENT_TOKEN_KEY);

    assertThatThrownBy(() -> authorizationAspect.authorizationCheck(authorized))
        .isInstanceOf(UnauthorizedException.class)
        .hasMessage("Request is not authorised");
  }

  @Test
  void testAuthorizationCheck_withMissingMonitoringTokenInRequest() {
    // Test that authorization fails when monitoring token is missing in request header
    String validToken = "valid-monitoring-token";
    when(mockConfiguration.getMonitoringToken()).thenReturn(validToken);
    // Not adding the header to request

    Authorized authorized = createAuthorized(WorkflowsApi.X_MONITORING_TOKEN_KEY);

    assertThatThrownBy(() -> authorizationAspect.authorizationCheck(authorized))
        .isInstanceOf(UnauthorizedException.class)
        .hasMessage("Request is not authorised");
  }

  @Test
  void testAuthorizationCheck_withUnknownHeaderKey() {
    // Test that authorization fails with an unknown header key
    Authorized authorized = createAuthorized("X-Unknown-Token");

    assertThatThrownBy(() -> authorizationAspect.authorizationCheck(authorized))
        .isInstanceOf(UnauthorizedException.class)
        .hasMessage("Request is not authorised");
  }

  @Test
  void testAuthorizationCheck_withEmptyHeaderKey() {
    // Test that authorization fails with an empty header key
    Authorized authorized = createAuthorized("");

    assertThatThrownBy(() -> authorizationAspect.authorizationCheck(authorized))
        .isInstanceOf(UnauthorizedException.class)
        .hasMessage("Request is not authorised");
  }

  @Test
  void testAuthorizationCheck_withWhitespaceManagementToken() {
    // Test that authorization fails when management token is whitespace
    when(mockConfiguration.getManagementToken()).thenReturn("   ");
    mockRequest.addHeader(WorkflowsMgtApi.X_MANAGEMENT_TOKEN_KEY, "some-token");

    Authorized authorized = createAuthorized(WorkflowsMgtApi.X_MANAGEMENT_TOKEN_KEY);

    assertThatThrownBy(() -> authorizationAspect.authorizationCheck(authorized))
        .isInstanceOf(UnauthorizedException.class)
        .hasMessage("Request is not authorised");
  }

  @Test
  void testAuthorizationCheck_withWhitespaceMonitoringToken() {
    // Test that authorization fails when monitoring token is whitespace
    when(mockConfiguration.getMonitoringToken()).thenReturn("   ");
    mockRequest.addHeader(WorkflowsApi.X_MONITORING_TOKEN_KEY, "some-token");

    Authorized authorized = createAuthorized(WorkflowsApi.X_MONITORING_TOKEN_KEY);

    assertThatThrownBy(() -> authorizationAspect.authorizationCheck(authorized))
        .isInstanceOf(UnauthorizedException.class)
        .hasMessage("Request is not authorised");
  }

  @Test
  void testAuthorizationCheck_withTokenContainingSpecialCharacters() {
    // Test that authorization works with tokens containing special characters
    String tokenWithSpecialChars = "token!@#$%^&*()_+-=[]{}|;':,.<>?";
    when(mockConfiguration.getManagementToken()).thenReturn(tokenWithSpecialChars);
    mockRequest.addHeader(WorkflowsMgtApi.X_MANAGEMENT_TOKEN_KEY, tokenWithSpecialChars);

    Authorized authorized = createAuthorized(WorkflowsMgtApi.X_MANAGEMENT_TOKEN_KEY);

    // Should not throw exception
    authorizationAspect.authorizationCheck(authorized);
  }

  @Test
  void testAuthorizationCheck_withVeryLongToken() {
    // Test that authorization works with very long tokens
    String longToken = "a".repeat(1000);
    when(mockConfiguration.getMonitoringToken()).thenReturn(longToken);
    mockRequest.addHeader(WorkflowsApi.X_MONITORING_TOKEN_KEY, longToken);

    Authorized authorized = createAuthorized(WorkflowsApi.X_MONITORING_TOKEN_KEY);

    // Should not throw exception
    authorizationAspect.authorizationCheck(authorized);
  }

  @Test
  void testAuthorizationCheck_caseSensitiveTokenComparison() {
    // Test that token comparison is case-sensitive
    String validToken = "ValidToken";
    String wrongCaseToken = "validtoken";
    when(mockConfiguration.getManagementToken()).thenReturn(validToken);
    mockRequest.addHeader(WorkflowsMgtApi.X_MANAGEMENT_TOKEN_KEY, wrongCaseToken);

    Authorized authorized = createAuthorized(WorkflowsMgtApi.X_MANAGEMENT_TOKEN_KEY);

    assertThatThrownBy(() -> authorizationAspect.authorizationCheck(authorized))
        .isInstanceOf(UnauthorizedException.class)
        .hasMessage("Request is not authorised");
  }

  @Test
  void testAuthorizationCheck_tokenWithLeadingWhitespace() {
    // Test that tokens with leading whitespace don't match
    String validToken = "valid-token";
    String tokenWithWhitespace = " valid-token";
    when(mockConfiguration.getManagementToken()).thenReturn(validToken);
    mockRequest.addHeader(WorkflowsMgtApi.X_MANAGEMENT_TOKEN_KEY, tokenWithWhitespace);

    Authorized authorized = createAuthorized(WorkflowsMgtApi.X_MANAGEMENT_TOKEN_KEY);

    assertThatThrownBy(() -> authorizationAspect.authorizationCheck(authorized))
        .isInstanceOf(UnauthorizedException.class)
        .hasMessage("Request is not authorised");
  }

  @Test
  void testAuthorizationCheck_tokenWithTrailingWhitespace() {
    // Test that tokens with trailing whitespace don't match
    String validToken = "valid-token";
    String tokenWithWhitespace = "valid-token ";
    when(mockConfiguration.getManagementToken()).thenReturn(validToken);
    mockRequest.addHeader(WorkflowsMgtApi.X_MANAGEMENT_TOKEN_KEY, tokenWithWhitespace);

    Authorized authorized = createAuthorized(WorkflowsMgtApi.X_MANAGEMENT_TOKEN_KEY);

    assertThatThrownBy(() -> authorizationAspect.authorizationCheck(authorized))
        .isInstanceOf(UnauthorizedException.class)
        .hasMessage("Request is not authorised");
  }

  @Test
  void testAuthorizationCheck_multipleCallsWithSameValidToken() {
    // Test that authorization can be called multiple times successfully
    String validToken = "valid-token";
    when(mockConfiguration.getManagementToken()).thenReturn(validToken);
    mockRequest.addHeader(WorkflowsMgtApi.X_MANAGEMENT_TOKEN_KEY, validToken);

    Authorized authorized = createAuthorized(WorkflowsMgtApi.X_MANAGEMENT_TOKEN_KEY);

    // Should not throw exception on multiple calls
    authorizationAspect.authorizationCheck(authorized);
    authorizationAspect.authorizationCheck(authorized);
    authorizationAspect.authorizationCheck(authorized);
  }

  @Test
  void testAuthorizationCheck_switchingBetweenManagementAndMonitoring() {
    // Test that switching between management and monitoring tokens works correctly
    String managementToken = "management-token";
    String monitoringToken = "monitoring-token";
    when(mockConfiguration.getManagementToken()).thenReturn(managementToken);
    when(mockConfiguration.getMonitoringToken()).thenReturn(monitoringToken);

    // First call with management token
    mockRequest = new MockHttpServletRequest();
    mockRequest.addHeader(WorkflowsMgtApi.X_MANAGEMENT_TOKEN_KEY, managementToken);
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockRequest));
    Authorized managementAuthorized = createAuthorized(WorkflowsMgtApi.X_MANAGEMENT_TOKEN_KEY);
    authorizationAspect.authorizationCheck(managementAuthorized);

    // Second call with monitoring token
    mockRequest = new MockHttpServletRequest();
    mockRequest.addHeader(WorkflowsApi.X_MONITORING_TOKEN_KEY, monitoringToken);
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockRequest));
    Authorized monitoringAuthorized = createAuthorized(WorkflowsApi.X_MONITORING_TOKEN_KEY);
    authorizationAspect.authorizationCheck(monitoringAuthorized);
  }

  @Test
  void testAuthorizationCheck_withNumericToken() {
    // Test that authorization works with numeric tokens
    String numericToken = "1234567890";
    when(mockConfiguration.getManagementToken()).thenReturn(numericToken);
    mockRequest.addHeader(WorkflowsMgtApi.X_MANAGEMENT_TOKEN_KEY, numericToken);

    Authorized authorized = createAuthorized(WorkflowsMgtApi.X_MANAGEMENT_TOKEN_KEY);

    // Should not throw exception
    authorizationAspect.authorizationCheck(authorized);
  }

  @Test
  void testAuthorizationCheck_withUuidToken() {
    // Test that authorization works with UUID-like tokens
    String uuidToken = "550e8400-e29b-41d4-a716-446655440000";
    when(mockConfiguration.getMonitoringToken()).thenReturn(uuidToken);
    mockRequest.addHeader(WorkflowsApi.X_MONITORING_TOKEN_KEY, uuidToken);

    Authorized authorized = createAuthorized(WorkflowsApi.X_MONITORING_TOKEN_KEY);

    // Should not throw exception
    authorizationAspect.authorizationCheck(authorized);
  }

  @Test
  void testAuthorizationCheck_managementTokenNotAffectedByMonitoringToken() {
    // Test that management token validation is independent of monitoring token
    String managementToken = "management-token";
    when(mockConfiguration.getManagementToken()).thenReturn(managementToken);
    when(mockConfiguration.getMonitoringToken()).thenReturn("different-monitoring-token");
    mockRequest.addHeader(WorkflowsMgtApi.X_MANAGEMENT_TOKEN_KEY, managementToken);

    Authorized authorized = createAuthorized(WorkflowsMgtApi.X_MANAGEMENT_TOKEN_KEY);

    // Should not throw exception, monitoring token should not interfere
    authorizationAspect.authorizationCheck(authorized);
  }

  @Test
  void testAuthorizationCheck_monitoringTokenNotAffectedByManagementToken() {
    // Test that monitoring token validation is independent of management token
    String monitoringToken = "monitoring-token";
    when(mockConfiguration.getMonitoringToken()).thenReturn(monitoringToken);
    when(mockConfiguration.getManagementToken()).thenReturn("different-management-token");
    mockRequest.addHeader(WorkflowsApi.X_MONITORING_TOKEN_KEY, monitoringToken);

    Authorized authorized = createAuthorized(WorkflowsApi.X_MONITORING_TOKEN_KEY);

    // Should not throw exception, management token should not interfere
    authorizationAspect.authorizationCheck(authorized);
  }

  /**
   * Helper method to create a mock Authorized annotation with the given header token key.
   * We use a concrete implementation approach rather than mocking the annotation directly.
   */
  private Authorized createAuthorized(String headerTokenKey) {
    return new Authorized() {
      @Override
      public String headerTokenKey() {
        return headerTokenKey;
      }

      @Override
      public Class<Authorized> annotationType() {
        return Authorized.class;
      }
    };
  }
}
