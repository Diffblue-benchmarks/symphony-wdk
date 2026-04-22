package com.symphony.devsol.config;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

class CorsConfigDiffblueTest {
  /**
   * Test {@link CorsConfig#addCorsMappings(CorsRegistry)}.
   *
   * <ul>
   *   <li>Then calls {@link CorsRegistry#addMapping(String)} with {@code /**}.
   * </ul>
   *
   * <p>Method under test: {@link CorsConfig#addCorsMappings(CorsRegistry)}
   */
  @Test
  @DisplayName("Test addCorsMappings(CorsRegistry); then calls addMapping with '/**'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CorsConfig.addCorsMappings(CorsRegistry)"})
  void testAddCorsMappings_thenCallsAddMapping() {
    // Arrange
    CorsConfig corsConfig = new CorsConfig();
    CorsRegistry registry = Mockito.mock(CorsRegistry.class);
    CorsRegistration corsRegistration = Mockito.mock(CorsRegistration.class);
    when(registry.addMapping(any())).thenReturn(corsRegistration);
    when(corsRegistration.allowedMethods(any(String[].class))).thenReturn(corsRegistration);

    // Act
    corsConfig.addCorsMappings(registry);

    // Assert
    verify(registry).addMapping("/**");
    verify(corsRegistration).allowedMethods("GET", "PUT", "POST", "DELETE");
  }
}
