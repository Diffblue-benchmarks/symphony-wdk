package com.symphony.bdk.workflow.swadl.v1.activity.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ExecuteRequestDiffblueTest {
  /**
   * Test {@link ExecuteRequest#equals(Object)}, and {@link ExecuteRequest#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ExecuteRequest#equals(Object)}
   *   <li>{@link ExecuteRequest#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ExecuteRequest.equals(Object)", "int ExecuteRequest.hashCode()"})
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
   * Test {@link ExecuteRequest#equals(Object)}, and {@link ExecuteRequest#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ExecuteRequest#equals(Object)}
   *   <li>{@link ExecuteRequest#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ExecuteRequest.equals(Object)", "int ExecuteRequest.hashCode()"})
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
   * Test {@link ExecuteRequest#equals(Object)}, and {@link ExecuteRequest#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ExecuteRequest#equals(Object)}
   *   <li>{@link ExecuteRequest#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ExecuteRequest.equals(Object)", "int ExecuteRequest.hashCode()"})
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
   * Test {@link ExecuteRequest#equals(Object)}, and {@link ExecuteRequest#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ExecuteRequest#equals(Object)}
   *   <li>{@link ExecuteRequest#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ExecuteRequest.equals(Object)", "int ExecuteRequest.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ExecuteRequest executeRequest = new ExecuteRequest();

    // Act and Assert
    assertEquals(executeRequest, executeRequest);
    int expectedHashCodeResult = executeRequest.hashCode();
    assertEquals(expectedHashCodeResult, executeRequest.hashCode());
  }

  /**
   * Test {@link ExecuteRequest#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecuteRequest#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ExecuteRequest.equals(Object)", "int ExecuteRequest.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ExecuteRequest executeRequest = new ExecuteRequest();
    executeRequest.add("GET", "Value");

    // Act and Assert
    assertNotEquals(executeRequest, new ExecuteRequest());
  }

  /**
   * Test {@link ExecuteRequest#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecuteRequest#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ExecuteRequest.equals(Object)", "int ExecuteRequest.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ExecuteRequest executeRequest = new ExecuteRequest();
    executeRequest.setUrl("https://example.org/example");

    // Act and Assert
    assertNotEquals(executeRequest, new ExecuteRequest());
  }

  /**
   * Test {@link ExecuteRequest#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecuteRequest#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ExecuteRequest.equals(Object)", "int ExecuteRequest.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    ExecuteRequest executeRequest = new ExecuteRequest();
    executeRequest.setBody("Body");

    // Act and Assert
    assertNotEquals(executeRequest, new ExecuteRequest());
  }

  /**
   * Test {@link ExecuteRequest#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecuteRequest#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ExecuteRequest.equals(Object)", "int ExecuteRequest.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    ExecuteRequest executeRequest = new ExecuteRequest();

    ExecuteRequest executeRequest2 = new ExecuteRequest();
    executeRequest2.setUrl("https://example.org/example");

    // Act and Assert
    assertNotEquals(executeRequest, executeRequest2);
  }

  /**
   * Test {@link ExecuteRequest#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecuteRequest#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ExecuteRequest.equals(Object)", "int ExecuteRequest.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    ExecuteRequest executeRequest = new ExecuteRequest();

    ExecuteRequest executeRequest2 = new ExecuteRequest();
    executeRequest2.setBody("Body");

    // Act and Assert
    assertNotEquals(executeRequest, executeRequest2);
  }

  /**
   * Test {@link ExecuteRequest#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecuteRequest#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ExecuteRequest.equals(Object)", "int ExecuteRequest.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    ExecuteRequest executeRequest = new ExecuteRequest();
    executeRequest.setBody(new ExecuteRequest());

    // Act and Assert
    assertNotEquals(executeRequest, new ExecuteRequest());
  }

  /**
   * Test {@link ExecuteRequest#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecuteRequest#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ExecuteRequest.equals(Object)", "int ExecuteRequest.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
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
   * Test {@link ExecuteRequest#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecuteRequest#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ExecuteRequest.equals(Object)", "int ExecuteRequest.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
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
   * Test {@link ExecuteRequest#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecuteRequest#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ExecuteRequest.equals(Object)", "int ExecuteRequest.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ExecuteRequest(), null);
  }

  /**
   * Test {@link ExecuteRequest#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecuteRequest#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ExecuteRequest.equals(Object)", "int ExecuteRequest.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ExecuteRequest(), "Different type to ExecuteRequest");
  }

  /**
   * Test getters and setters.
   * <p>
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
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Object ExecuteRequest.getBody()", "Map ExecuteRequest.getHeaders()",
      "String ExecuteRequest.getMethod()", "String ExecuteRequest.getUrl()",
      "boolean ExecuteRequest.isEncodeQueryParams()", "void ExecuteRequest.setBody(Object)",
      "void ExecuteRequest.setEncodeQueryParams(boolean)", "void ExecuteRequest.setHeaders(Map)",
      "void ExecuteRequest.setMethod(String)", "void ExecuteRequest.setUrl(String)",
      "String ExecuteRequest.toString()"})
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

    // Assert
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
   * Test new {@link ExecuteRequest} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link ExecuteRequest}
   */
  @Test
  @DisplayName("Test new ExecuteRequest (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ExecuteRequest.<init>()"})
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
    assertNull(actualExecuteRequest.getUrl());
    assertTrue(actualExecuteRequest.isEncodeQueryParams());
    assertTrue(actualExecuteRequest.getVariableProperties().isEmpty());
    assertTrue(actualExecuteRequest.getHeaders().isEmpty());
  }
}
