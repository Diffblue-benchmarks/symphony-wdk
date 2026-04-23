package com.symphony.devsol.config;

import org.junit.jupiter.api.Test;

class AutoConfigTest {

  @Test
  void shouldLogOnInit() {
    AutoConfig autoConfig = new AutoConfig();

    autoConfig.init();
  }
}
