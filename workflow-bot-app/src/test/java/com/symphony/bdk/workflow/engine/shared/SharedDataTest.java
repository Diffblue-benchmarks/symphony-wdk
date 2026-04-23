package com.symphony.bdk.workflow.engine.shared;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SharedDataTest {

  @Test
  void shouldSetNamespaceAndReturnSelf() {
    SharedData sharedData = new SharedData();

    SharedData result = sharedData.namespace("testNamespace");

    assertThat(result).isSameAs(sharedData);
    assertThat(result.getNamespace()).isEqualTo("testNamespace");
  }
}
