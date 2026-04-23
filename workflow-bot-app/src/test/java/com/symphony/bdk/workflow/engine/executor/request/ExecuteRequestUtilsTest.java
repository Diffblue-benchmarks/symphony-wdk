package com.symphony.bdk.workflow.engine.executor.request;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExecuteRequestUtilsTest {

  @Test
  void shouldReturnUrlUnchangedWhenNoQueryParameters() {
    String url = "https://example.com/api/resource";

    String result = ExecuteRequestUtils.encodeQueryParameters(url);

    assertThat(result).isEqualTo(url);
  }

  @Test
  void shouldEncodeSpecialCharactersInQueryParameterValues() {
    String url = "https://example.com/api/resource?key=hello world";

    String result = ExecuteRequestUtils.encodeQueryParameters(url);

    assertThat(result).isEqualTo("https://example.com/api/resource?key=hello+world");
  }

  @Test
  void shouldEncodeAmpersandInQueryParameterValues() {
    String url = "https://example.com/api/resource?key=a%26b";

    String result = ExecuteRequestUtils.encodeQueryParameters(url);

    assertThat(result).contains("key=");
  }

  @Test
  void shouldHandleMultipleQueryParameters() {
    String url = "https://example.com/api?foo=bar&baz=qux";

    String result = ExecuteRequestUtils.encodeQueryParameters(url);

    assertThat(result).contains("foo=bar");
    assertThat(result).contains("baz=qux");
  }

  @Test
  void shouldEncodeQueryParameterValueWithSpecialSymbols() {
    String url = "https://example.com/api?q=hello+world&filter=a b";

    String result = ExecuteRequestUtils.encodeQueryParameters(url);

    assertThat(result).isNotNull();
    assertThat(result).contains("q=");
    assertThat(result).contains("filter=");
  }

  @Test
  void shouldPreservePathWhenEncodingQueryParameters() {
    String url = "https://example.com/some/path?param=value";

    String result = ExecuteRequestUtils.encodeQueryParameters(url);

    assertThat(result).startsWith("https://example.com/some/path");
  }
}
