package com.symphony.devsol.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AutoConfigTest {

  private AutoConfig autoConfig;

  @BeforeEach
  void setUp() {
    autoConfig = new AutoConfig();
  }

  @Test
  void shouldInitializeWithoutException() {
    autoConfig.init();
  }
}
