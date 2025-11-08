package com.symphony.devsol.config;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

class CorsConfigDiffblueTest {
  /**
   * Method under test: {@link CorsConfig#addCorsMappings(CorsRegistry)}
   */
  @Test
  void testAddCorsMappings() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CorsConfig corsConfig = new CorsConfig();
    CorsRegistry registry = mock(CorsRegistry.class);
    when(registry.addMapping(Mockito.<String>any())).thenReturn(new CorsRegistration("Path Pattern"));

    // Act
    corsConfig.addCorsMappings(registry);

    // Assert
    verify(registry).addMapping(eq("/**"));
  }
}
