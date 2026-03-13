package com.symphony.bdk.workflow.engine.executor.request;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExecuteRequestUtilsTest {

  @Test
  void encodeQueryParametersShouldEncodeSpecialCharacters() {
    String url = "http://example.com/api?param=hello world&key=value+test";
    String result = ExecuteRequestUtils.encodeQueryParameters(url);

    assertThat(result).contains("hello+world");
    assertThat(result).contains("value%2Btest");
  }

  @Test
  void encodeQueryParametersShouldHandleMultipleQueryParameters() {
    String url = "http://example.com/api?param1=value1&param2=value2&param3=value3";
    String result = ExecuteRequestUtils.encodeQueryParameters(url);

    assertThat(result).contains("param1=value1");
    assertThat(result).contains("param2=value2");
    assertThat(result).contains("param3=value3");
  }

  @Test
  void encodeQueryParametersShouldEncodeMultipleValuesForSameKey() {
    String url = "http://example.com/api?tags=java&tags=spring&tags=test framework";
    String result = ExecuteRequestUtils.encodeQueryParameters(url);

    assertThat(result).contains("tags=java");
    assertThat(result).contains("tags=spring");
    assertThat(result).contains("tags=test+framework");
  }

  @Test
  void encodeQueryParametersShouldHandleUrlWithoutQueryParameters() {
    String url = "http://example.com/api";
    String result = ExecuteRequestUtils.encodeQueryParameters(url);

    assertThat(result).isEqualTo("http://example.com/api");
  }

  @Test
  void encodeQueryParametersShouldEncodeSpecialCharactersInParameterValues() {
    String url = "http://example.com/api?email=user@example.com&path=/home/user";
    String result = ExecuteRequestUtils.encodeQueryParameters(url);

    assertThat(result).contains("email=user%40example.com");
    assertThat(result).contains("path=%2Fhome%2Fuser");
  }

  @Test
  void encodeQueryParametersShouldHandleEmptyParameterValue() {
    String url = "http://example.com/api?empty=&hasValue=test";
    String result = ExecuteRequestUtils.encodeQueryParameters(url);

    assertThat(result).contains("empty=");
    assertThat(result).contains("hasValue=test");
  }

  @Test
  void encodeQueryParametersShouldEncodePercentSign() {
    String url = "http://example.com/api?discount=50%&tax=10%";
    String result = ExecuteRequestUtils.encodeQueryParameters(url);

    assertThat(result).contains("discount=50%25");
    assertThat(result).contains("tax=10%25");
  }

  @Test
  void encodeQueryParametersShouldEncodeUnicodeCharacters() {
    String url = "http://example.com/api?text=Hello\u00A0World&emoji=\uD83D\uDE00";
    String result = ExecuteRequestUtils.encodeQueryParameters(url);

    assertThat(result).contains("text=Hello");
    assertThat(result).contains("emoji=%F0%9F%98%80");
  }

  @Test
  void encodeQueryParametersShouldPreserveUrlPathAndScheme() {
    String url = "https://api.example.com:8080/v1/users?name=John Doe";
    String result = ExecuteRequestUtils.encodeQueryParameters(url);

    assertThat(result).startsWith("https://api.example.com:8080/v1/users");
    assertThat(result).contains("name=John+Doe");
  }
}
