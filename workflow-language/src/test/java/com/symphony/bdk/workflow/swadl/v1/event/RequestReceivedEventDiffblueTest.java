package com.symphony.bdk.workflow.swadl.v1.event;

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

class RequestReceivedEventDiffblueTest {
  /**
   * Test {@link RequestReceivedEvent#equals(Object)}, and {@link RequestReceivedEvent#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link RequestReceivedEvent#equals(Object)}
   *   <li>{@link RequestReceivedEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RequestReceivedEvent.equals(Object)", "int RequestReceivedEvent.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    RequestReceivedEvent requestReceivedEvent = new RequestReceivedEvent();
    requestReceivedEvent.setArguments(new HashMap<>());
    requestReceivedEvent.setId("42");
    requestReceivedEvent.setToken("ABC123");
    requestReceivedEvent.setWorkflowId("42");

    RequestReceivedEvent requestReceivedEvent2 = new RequestReceivedEvent();
    requestReceivedEvent2.setArguments(new HashMap<>());
    requestReceivedEvent2.setId("42");
    requestReceivedEvent2.setToken("ABC123");
    requestReceivedEvent2.setWorkflowId("42");

    // Act and Assert
    assertEquals(requestReceivedEvent, requestReceivedEvent2);
    int expectedHashCodeResult = requestReceivedEvent.hashCode();
    assertEquals(expectedHashCodeResult, requestReceivedEvent2.hashCode());
  }

  /**
   * Test {@link RequestReceivedEvent#equals(Object)}, and {@link RequestReceivedEvent#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link RequestReceivedEvent#equals(Object)}
   *   <li>{@link RequestReceivedEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RequestReceivedEvent.equals(Object)", "int RequestReceivedEvent.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    RequestReceivedEvent requestReceivedEvent = new RequestReceivedEvent();
    requestReceivedEvent.setArguments(new HashMap<>());
    requestReceivedEvent.setId("42");
    requestReceivedEvent.setToken("ABC123");
    requestReceivedEvent.setWorkflowId("42");

    // Act and Assert
    assertEquals(requestReceivedEvent, requestReceivedEvent);
    int expectedHashCodeResult = requestReceivedEvent.hashCode();
    assertEquals(expectedHashCodeResult, requestReceivedEvent.hashCode());
  }

  /**
   * Test {@link RequestReceivedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link RequestReceivedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RequestReceivedEvent.equals(Object)", "int RequestReceivedEvent.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    HashMap<String, Object> arguments = new HashMap<>();
    arguments.put("42", "42");

    RequestReceivedEvent requestReceivedEvent = new RequestReceivedEvent();
    requestReceivedEvent.setArguments(arguments);
    requestReceivedEvent.setId("42");
    requestReceivedEvent.setToken("ABC123");
    requestReceivedEvent.setWorkflowId("42");

    RequestReceivedEvent requestReceivedEvent2 = new RequestReceivedEvent();
    requestReceivedEvent2.setArguments(new HashMap<>());
    requestReceivedEvent2.setId("42");
    requestReceivedEvent2.setToken("ABC123");
    requestReceivedEvent2.setWorkflowId("42");

    // Act and Assert
    assertNotEquals(requestReceivedEvent, requestReceivedEvent2);
  }

  /**
   * Test {@link RequestReceivedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link RequestReceivedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RequestReceivedEvent.equals(Object)", "int RequestReceivedEvent.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    RequestReceivedEvent requestReceivedEvent = new RequestReceivedEvent();
    requestReceivedEvent.setArguments(new HashMap<>());
    requestReceivedEvent.setId("ABC123");
    requestReceivedEvent.setToken("ABC123");
    requestReceivedEvent.setWorkflowId("42");

    RequestReceivedEvent requestReceivedEvent2 = new RequestReceivedEvent();
    requestReceivedEvent2.setArguments(new HashMap<>());
    requestReceivedEvent2.setId("42");
    requestReceivedEvent2.setToken("ABC123");
    requestReceivedEvent2.setWorkflowId("42");

    // Act and Assert
    assertNotEquals(requestReceivedEvent, requestReceivedEvent2);
  }

  /**
   * Test {@link RequestReceivedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link RequestReceivedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RequestReceivedEvent.equals(Object)", "int RequestReceivedEvent.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    RequestReceivedEvent requestReceivedEvent = new RequestReceivedEvent();
    requestReceivedEvent.setArguments(new HashMap<>());
    requestReceivedEvent.setId("42");
    requestReceivedEvent.setToken("42");
    requestReceivedEvent.setWorkflowId("42");

    RequestReceivedEvent requestReceivedEvent2 = new RequestReceivedEvent();
    requestReceivedEvent2.setArguments(new HashMap<>());
    requestReceivedEvent2.setId("42");
    requestReceivedEvent2.setToken("ABC123");
    requestReceivedEvent2.setWorkflowId("42");

    // Act and Assert
    assertNotEquals(requestReceivedEvent, requestReceivedEvent2);
  }

  /**
   * Test {@link RequestReceivedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link RequestReceivedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RequestReceivedEvent.equals(Object)", "int RequestReceivedEvent.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    RequestReceivedEvent requestReceivedEvent = new RequestReceivedEvent();
    requestReceivedEvent.setArguments(new HashMap<>());
    requestReceivedEvent.setId("42");
    requestReceivedEvent.setToken(null);
    requestReceivedEvent.setWorkflowId("42");

    RequestReceivedEvent requestReceivedEvent2 = new RequestReceivedEvent();
    requestReceivedEvent2.setArguments(new HashMap<>());
    requestReceivedEvent2.setId("42");
    requestReceivedEvent2.setToken("ABC123");
    requestReceivedEvent2.setWorkflowId("42");

    // Act and Assert
    assertNotEquals(requestReceivedEvent, requestReceivedEvent2);
  }

  /**
   * Test {@link RequestReceivedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link RequestReceivedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RequestReceivedEvent.equals(Object)", "int RequestReceivedEvent.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    RequestReceivedEvent requestReceivedEvent = new RequestReceivedEvent();
    requestReceivedEvent.setArguments(new HashMap<>());
    requestReceivedEvent.setId("42");
    requestReceivedEvent.setToken("ABC123");
    requestReceivedEvent.setWorkflowId("ABC123");

    RequestReceivedEvent requestReceivedEvent2 = new RequestReceivedEvent();
    requestReceivedEvent2.setArguments(new HashMap<>());
    requestReceivedEvent2.setId("42");
    requestReceivedEvent2.setToken("ABC123");
    requestReceivedEvent2.setWorkflowId("42");

    // Act and Assert
    assertNotEquals(requestReceivedEvent, requestReceivedEvent2);
  }

  /**
   * Test {@link RequestReceivedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link RequestReceivedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RequestReceivedEvent.equals(Object)", "int RequestReceivedEvent.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    RequestReceivedEvent requestReceivedEvent = new RequestReceivedEvent();
    requestReceivedEvent.setArguments(new HashMap<>());
    requestReceivedEvent.setId("42");
    requestReceivedEvent.setToken("ABC123");
    requestReceivedEvent.setWorkflowId(null);

    RequestReceivedEvent requestReceivedEvent2 = new RequestReceivedEvent();
    requestReceivedEvent2.setArguments(new HashMap<>());
    requestReceivedEvent2.setId("42");
    requestReceivedEvent2.setToken("ABC123");
    requestReceivedEvent2.setWorkflowId("42");

    // Act and Assert
    assertNotEquals(requestReceivedEvent, requestReceivedEvent2);
  }

  /**
   * Test {@link RequestReceivedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link RequestReceivedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RequestReceivedEvent.equals(Object)", "int RequestReceivedEvent.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    RequestReceivedEvent requestReceivedEvent = new RequestReceivedEvent();
    requestReceivedEvent.setArguments(new HashMap<>());
    requestReceivedEvent.setId("42");
    requestReceivedEvent.setToken("ABC123");
    requestReceivedEvent.setWorkflowId("42");

    // Act and Assert
    assertNotEquals(requestReceivedEvent, null);
  }

  /**
   * Test {@link RequestReceivedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link RequestReceivedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RequestReceivedEvent.equals(Object)", "int RequestReceivedEvent.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    RequestReceivedEvent requestReceivedEvent = new RequestReceivedEvent();
    requestReceivedEvent.setArguments(new HashMap<>());
    requestReceivedEvent.setId("42");
    requestReceivedEvent.setToken("ABC123");
    requestReceivedEvent.setWorkflowId("42");

    // Act and Assert
    assertNotEquals(requestReceivedEvent, "Different type to RequestReceivedEvent");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link RequestReceivedEvent}
   *   <li>{@link RequestReceivedEvent#setArguments(Map)}
   *   <li>{@link RequestReceivedEvent#setToken(String)}
   *   <li>{@link RequestReceivedEvent#setWorkflowId(String)}
   *   <li>{@link RequestReceivedEvent#toString()}
   *   <li>{@link RequestReceivedEvent#getArguments()}
   *   <li>{@link RequestReceivedEvent#getToken()}
   *   <li>{@link RequestReceivedEvent#getWorkflowId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void RequestReceivedEvent.<init>()", "Map RequestReceivedEvent.getArguments()",
      "String RequestReceivedEvent.getToken()", "String RequestReceivedEvent.getWorkflowId()",
      "void RequestReceivedEvent.setArguments(Map)", "void RequestReceivedEvent.setToken(String)",
      "void RequestReceivedEvent.setWorkflowId(String)", "String RequestReceivedEvent.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    RequestReceivedEvent actualRequestReceivedEvent = new RequestReceivedEvent();
    HashMap<String, Object> arguments = new HashMap<>();
    actualRequestReceivedEvent.setArguments(arguments);
    actualRequestReceivedEvent.setToken("ABC123");
    actualRequestReceivedEvent.setWorkflowId("42");
    String actualToStringResult = actualRequestReceivedEvent.toString();
    Map<String, Object> actualArguments = actualRequestReceivedEvent.getArguments();
    String actualToken = actualRequestReceivedEvent.getToken();

    // Assert
    assertEquals("42", actualRequestReceivedEvent.getWorkflowId());
    assertEquals("ABC123", actualToken);
    assertEquals("RequestReceivedEvent(token=ABC123, arguments={}, workflowId=42)", actualToStringResult);
    assertNull(actualRequestReceivedEvent.getId());
    assertTrue(actualArguments.isEmpty());
    assertSame(arguments, actualArguments);
  }
}
