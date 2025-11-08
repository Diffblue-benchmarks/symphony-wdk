package com.symphony.devsol.config;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.core.auth.exception.AuthInitializationException;
import com.symphony.bdk.core.auth.jwt.UserClaim;
import com.symphony.devsol.client.ExtAppClient;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

@ContextConfiguration(classes = {AuthFilter.class})
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class AuthFilterDiffblueTest {
  @Autowired
  private AuthFilter authFilter;

  @MockBean
  private ExtAppClient extAppClient;

  /**
   * Method under test:
   * {@link AuthFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)}
   */
  @Test
  void testDoFilterInternal() throws AuthInitializationException, ServletException, IOException {
    // Arrange
    UserClaim userClaim = new UserClaim();
    userClaim.setAvatarSmallUrl("https://example.org/example");
    userClaim.setAvatarUrl("https://example.org/example");
    userClaim.setCompany("Company");
    userClaim.setCompanyId("42");
    userClaim.setDisplayName("Display Name");
    userClaim.setEmailAddress("42 Main St");
    userClaim.setFirstName("Jane");
    userClaim.setId(1L);
    userClaim.setLastName("Doe");
    userClaim.setLocation("Location");
    userClaim.setTitle("Dr");
    userClaim.setUsername("janedoe");
    when(extAppClient.validate(Mockito.<String>any())).thenReturn(userClaim);
    HttpServletRequestWrapper request = mock(HttpServletRequestWrapper.class);
    doNothing().when(request).setAttribute(Mockito.<String>any(), Mockito.<Object>any());
    when(request.getHeader(Mockito.<String>any())).thenReturn("https://example.org/example");
    when(request.getRequestURI()).thenReturn("https://example.org/example");
    when(request.getMethod()).thenReturn("https://example.org/example");
    Response response = new Response();
    FilterChain chain = mock(FilterChain.class);
    doNothing().when(chain).doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());

    // Act
    authFilter.doFilterInternal(request, response, chain);

    // Assert
    verify(extAppClient).validate(eq("/example.org/example"));
    verify(chain).doFilter(isA(ServletRequest.class), isA(ServletResponse.class));
    verify(request).setAttribute(eq("user"), isA(Object.class));
    verify(request, atLeast(1)).getHeader(Mockito.<String>any());
    verify(request).getMethod();
    verify(request).getRequestURI();
  }

  /**
   * Method under test:
   * {@link AuthFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)}
   */
  @Test
  void testDoFilterInternal2() throws AuthInitializationException, ServletException, IOException {
    // Arrange
    UserClaim userClaim = new UserClaim();
    userClaim.setAvatarSmallUrl("https://example.org/example");
    userClaim.setAvatarUrl("https://example.org/example");
    userClaim.setCompany("Company");
    userClaim.setCompanyId("42");
    userClaim.setDisplayName("Display Name");
    userClaim.setEmailAddress("42 Main St");
    userClaim.setFirstName("Jane");
    userClaim.setId(1L);
    userClaim.setLastName("Doe");
    userClaim.setLocation("Location");
    userClaim.setTitle("Dr");
    userClaim.setUsername("janedoe");
    when(extAppClient.validate(Mockito.<String>any())).thenReturn(userClaim);
    HttpServletRequestWrapper request = mock(HttpServletRequestWrapper.class);
    doNothing().when(request).setAttribute(Mockito.<String>any(), Mockito.<Object>any());
    when(request.getHeader(Mockito.<String>any())).thenReturn("https://example.org/example");
    when(request.getRequestURI()).thenReturn("https://example.org/example");
    when(request.getMethod()).thenReturn("https://example.org/example");
    Response response = new Response();
    FilterChain chain = mock(FilterChain.class);
    doThrow(new ServletException("An error occurred")).when(chain)
        .doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());

    // Act and Assert
    assertThrows(ServletException.class, () -> authFilter.doFilterInternal(request, response, chain));
    verify(extAppClient).validate(eq("/example.org/example"));
    verify(chain).doFilter(isA(ServletRequest.class), isA(ServletResponse.class));
    verify(request).setAttribute(eq("user"), isA(Object.class));
    verify(request, atLeast(1)).getHeader(Mockito.<String>any());
    verify(request).getMethod();
    verify(request).getRequestURI();
  }
}
