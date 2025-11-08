package com.symphony.bdk.workflow.swadl.v1.activity.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import com.symphony.bdk.workflow.swadl.v1.activity.Debug;
import com.symphony.bdk.workflow.swadl.v1.activity.RelationalEvents;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ExecuteRequestDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ExecuteRequest#equals(Object)}
   *   <li>{@link ExecuteRequest#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    ExecuteRequest executeRequest = new ExecuteRequest();
    ExecuteRequest executeRequest2 = new ExecuteRequest();

    // Act and Assert
    assertEquals(executeRequest, executeRequest2);
    int expectedHashCodeResult = executeRequest.hashCode();
    assertEquals(expectedHashCodeResult, executeRequest2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ExecuteRequest#equals(Object)}
   *   <li>{@link ExecuteRequest#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    ExecuteRequest executeRequest = new ExecuteRequest();
    executeRequest.setUrl("https://example.org/example");

    ExecuteRequest executeRequest2 = new ExecuteRequest();
    executeRequest2.setUrl("https://example.org/example");

    // Act and Assert
    assertEquals(executeRequest, executeRequest2);
    int expectedHashCodeResult = executeRequest.hashCode();
    assertEquals(expectedHashCodeResult, executeRequest2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ExecuteRequest#equals(Object)}
   *   <li>{@link ExecuteRequest#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    ExecuteRequest executeRequest = new ExecuteRequest();
    executeRequest.setBody("Body");

    ExecuteRequest executeRequest2 = new ExecuteRequest();
    executeRequest2.setBody("Body");

    // Act and Assert
    assertEquals(executeRequest, executeRequest2);
    int expectedHashCodeResult = executeRequest.hashCode();
    assertEquals(expectedHashCodeResult, executeRequest2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ExecuteRequest#equals(Object)}
   *   <li>{@link ExecuteRequest#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ExecuteRequest executeRequest = new ExecuteRequest();

    // Act and Assert
    assertEquals(executeRequest, executeRequest);
    int expectedHashCodeResult = executeRequest.hashCode();
    assertEquals(expectedHashCodeResult, executeRequest.hashCode());
  }

  /**
   * Method under test: {@link ExecuteRequest#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ExecuteRequest executeRequest = new ExecuteRequest();
    executeRequest.add("GET", "Value");

    // Act and Assert
    assertNotEquals(executeRequest, new ExecuteRequest());
  }

  /**
   * Method under test: {@link ExecuteRequest#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ExecuteRequest executeRequest = new ExecuteRequest();
    executeRequest.add("GET", mock(Debug.class));

    // Act and Assert
    assertNotEquals(executeRequest, new ExecuteRequest());
  }

  /**
   * Method under test: {@link ExecuteRequest#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    ExecuteRequest executeRequest = new ExecuteRequest();
    executeRequest.setUrl("https://example.org/example");

    // Act and Assert
    assertNotEquals(executeRequest, new ExecuteRequest());
  }

  /**
   * Method under test: {@link ExecuteRequest#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    ExecuteRequest executeRequest = new ExecuteRequest();
    executeRequest.setBody("Body");

    // Act and Assert
    assertNotEquals(executeRequest, new ExecuteRequest());
  }

  /**
   * Method under test: {@link ExecuteRequest#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    ExecuteRequest executeRequest = new ExecuteRequest();

    ExecuteRequest executeRequest2 = new ExecuteRequest();
    executeRequest2.setUrl("https://example.org/example");

    // Act and Assert
    assertNotEquals(executeRequest, executeRequest2);
  }

  /**
   * Method under test: {@link ExecuteRequest#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    ExecuteRequest executeRequest = new ExecuteRequest();

    ExecuteRequest executeRequest2 = new ExecuteRequest();
    executeRequest2.setBody("Body");

    // Act and Assert
    assertNotEquals(executeRequest, executeRequest2);
  }

  /**
   * Method under test: {@link ExecuteRequest#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    ExecuteRequest executeRequest = new ExecuteRequest();
    executeRequest.setBody(new ExecuteRequest());

    // Act and Assert
    assertNotEquals(executeRequest, new ExecuteRequest());
  }

  /**
   * Method under test: {@link ExecuteRequest#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    ExecuteRequest executeRequest = new ExecuteRequest();
    executeRequest.setMethod("https://example.org/example");
    executeRequest.setUrl("https://example.org/example");

    ExecuteRequest executeRequest2 = new ExecuteRequest();
    executeRequest2.setUrl("https://example.org/example");

    // Act and Assert
    assertNotEquals(executeRequest, executeRequest2);
  }

  /**
   * Method under test: {@link ExecuteRequest#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    ExecuteRequest executeRequest = new ExecuteRequest();
    executeRequest.setMethod(null);
    executeRequest.setUrl("https://example.org/example");

    ExecuteRequest executeRequest2 = new ExecuteRequest();
    executeRequest2.setUrl("https://example.org/example");

    // Act and Assert
    assertNotEquals(executeRequest, executeRequest2);
  }

  /**
   * Method under test: {@link ExecuteRequest#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ExecuteRequest(), null);
  }

  /**
   * Method under test: {@link ExecuteRequest#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ExecuteRequest(), "Different type to ExecuteRequest");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ExecuteRequest#setBody(Object)}
   *   <li>{@link ExecuteRequest#setEncodeQueryParams(boolean)}
   *   <li>{@link ExecuteRequest#setHeaders(Map)}
   *   <li>{@link ExecuteRequest#setMethod(String)}
   *   <li>{@link ExecuteRequest#setUrl(String)}
   *   <li>{@link ExecuteRequest#toString()}
   *   <li>{@link ExecuteRequest#getBody()}
   *   <li>{@link ExecuteRequest#getHeaders()}
   *   <li>{@link ExecuteRequest#getMethod()}
   *   <li>{@link ExecuteRequest#getUrl()}
   *   <li>{@link ExecuteRequest#isEncodeQueryParams()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    ExecuteRequest executeRequest = new ExecuteRequest();

    // Act
    executeRequest.setBody("Body");
    executeRequest.setEncodeQueryParams(true);
    HashMap<String, Object> headers = new HashMap<>();
    executeRequest.setHeaders(headers);
    executeRequest.setMethod("Method");
    executeRequest.setUrl("https://example.org/example");
    String actualToStringResult = executeRequest.toString();
    Object actualBody = executeRequest.getBody();
    Map<String, Object> actualHeaders = executeRequest.getHeaders();
    String actualMethod = executeRequest.getMethod();
    String actualUrl = executeRequest.getUrl();

    // Assert that nothing has changed
    assertEquals("Body", actualBody);
    assertEquals(
        "ExecuteRequest(url=https://example.org/example, method=Method, body=Body, headers={}, encodeQueryParams"
            + "=true)",
        actualToStringResult);
    assertEquals("Method", actualMethod);
    assertEquals("https://example.org/example", actualUrl);
    assertTrue(executeRequest.isEncodeQueryParams());
    assertTrue(actualHeaders.isEmpty());
    assertSame(headers, actualHeaders);
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link ExecuteRequest}
   */
  @Test
  void testNewExecuteRequest() {
    // Arrange and Act
    ExecuteRequest actualExecuteRequest = new ExecuteRequest();

    // Assert
    assertEquals("GET", actualExecuteRequest.getMethod());
    assertNull(actualExecuteRequest.getOn());
    assertNull(actualExecuteRequest.getElseCondition());
    assertNull(actualExecuteRequest.getBody());
    assertNull(actualExecuteRequest.getId());
    assertNull(actualExecuteRequest.getIfCondition());
    RelationalEvents events = actualExecuteRequest.getEvents();
    assertNull(events.getParentId());
    assertNull(actualExecuteRequest.getUrl());
    assertFalse(events.isParallel());
    assertTrue(events.isEmpty());
    assertTrue(actualExecuteRequest.isEncodeQueryParams());
    assertTrue(events.getEvents().isEmpty());
    assertTrue(actualExecuteRequest.getVariableProperties().isEmpty());
    assertTrue(actualExecuteRequest.getHeaders().isEmpty());
  }
}
