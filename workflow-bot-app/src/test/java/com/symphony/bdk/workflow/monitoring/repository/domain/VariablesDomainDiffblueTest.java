package com.symphony.bdk.workflow.monitoring.repository.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {VariablesDomain.class})
@ExtendWith(SpringExtension.class)
class VariablesDomainDiffblueTest {
  @Autowired
  private VariablesDomain variablesDomain;

  /**
   * Test {@link VariablesDomain#equals(Object)}, and
   * {@link VariablesDomain#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link VariablesDomain#equals(Object)}
   *   <li>{@link VariablesDomain#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    VariablesDomain variablesDomain = new VariablesDomain();
    variablesDomain.setOutputs(new HashMap<>());
    variablesDomain.setRevision(1);
    variablesDomain.setUpdateTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    VariablesDomain variablesDomain2 = new VariablesDomain();
    variablesDomain2.setOutputs(new HashMap<>());
    variablesDomain2.setRevision(1);
    variablesDomain2.setUpdateTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertEquals(variablesDomain, variablesDomain2);
    int expectedHashCodeResult = variablesDomain.hashCode();
    assertEquals(expectedHashCodeResult, variablesDomain2.hashCode());
  }

  /**
   * Test {@link VariablesDomain#equals(Object)}, and
   * {@link VariablesDomain#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link VariablesDomain#equals(Object)}
   *   <li>{@link VariablesDomain#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    VariablesDomain variablesDomain = new VariablesDomain();
    variablesDomain.setOutputs(new HashMap<>());
    variablesDomain.setRevision(1);
    variablesDomain.setUpdateTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertEquals(variablesDomain, variablesDomain);
    int expectedHashCodeResult = variablesDomain.hashCode();
    assertEquals(expectedHashCodeResult, variablesDomain.hashCode());
  }

  /**
   * Test {@link VariablesDomain#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariablesDomain#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    HashMap<String, Object> outputs = new HashMap<>();
    outputs.put("foo", "42");

    VariablesDomain variablesDomain = new VariablesDomain();
    variablesDomain.setOutputs(outputs);
    variablesDomain.setRevision(1);
    variablesDomain.setUpdateTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    VariablesDomain variablesDomain2 = new VariablesDomain();
    variablesDomain2.setOutputs(new HashMap<>());
    variablesDomain2.setRevision(1);
    variablesDomain2.setUpdateTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertNotEquals(variablesDomain, variablesDomain2);
  }

  /**
   * Test {@link VariablesDomain#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariablesDomain#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    VariablesDomain variablesDomain = new VariablesDomain();
    variablesDomain.setOutputs(new HashMap<>());
    variablesDomain.setRevision(3);
    variablesDomain.setUpdateTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    VariablesDomain variablesDomain2 = new VariablesDomain();
    variablesDomain2.setOutputs(new HashMap<>());
    variablesDomain2.setRevision(1);
    variablesDomain2.setUpdateTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertNotEquals(variablesDomain, variablesDomain2);
  }

  /**
   * Test {@link VariablesDomain#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariablesDomain#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    VariablesDomain variablesDomain = new VariablesDomain();
    variablesDomain.setOutputs(new HashMap<>());
    variablesDomain.setRevision(1);
    variablesDomain.setUpdateTime(LocalDate.now().atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    VariablesDomain variablesDomain2 = new VariablesDomain();
    variablesDomain2.setOutputs(new HashMap<>());
    variablesDomain2.setRevision(1);
    variablesDomain2.setUpdateTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertNotEquals(variablesDomain, variablesDomain2);
  }

  /**
   * Test {@link VariablesDomain#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariablesDomain#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    VariablesDomain variablesDomain = new VariablesDomain();
    variablesDomain.setOutputs(new HashMap<>());
    variablesDomain.setRevision(1);
    variablesDomain.setUpdateTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertNotEquals(variablesDomain, null);
  }

  /**
   * Test {@link VariablesDomain#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariablesDomain#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    VariablesDomain variablesDomain = new VariablesDomain();
    variablesDomain.setOutputs(new HashMap<>());
    variablesDomain.setRevision(1);
    variablesDomain.setUpdateTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertNotEquals(variablesDomain, "Different type to VariablesDomain");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link VariablesDomain#setOutputs(Map)}
   *   <li>{@link VariablesDomain#setRevision(int)}
   *   <li>{@link VariablesDomain#setUpdateTime(Instant)}
   *   <li>{@link VariablesDomain#toString()}
   *   <li>{@link VariablesDomain#getOutputs()}
   *   <li>{@link VariablesDomain#getRevision()}
   *   <li>{@link VariablesDomain#getUpdateTime()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    VariablesDomain variablesDomain = new VariablesDomain();
    HashMap<String, Object> outputs = new HashMap<>();

    // Act
    variablesDomain.setOutputs(outputs);
    variablesDomain.setRevision(1);
    variablesDomain.setUpdateTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    String actualToStringResult = variablesDomain.toString();
    Map<String, Object> actualOutputs = variablesDomain.getOutputs();
    int actualRevision = variablesDomain.getRevision();
    Instant actualUpdateTime = variablesDomain.getUpdateTime();

    // Assert
    assertEquals("VariablesDomain(outputs={}, revision=1, updateTime=1970-01-01T00:00:00Z)", actualToStringResult);
    assertEquals(1, actualRevision);
    assertTrue(actualOutputs.isEmpty());
    assertSame(outputs, actualOutputs);
    assertSame(actualUpdateTime.EPOCH, actualUpdateTime);
  }

  /**
   * Test new {@link VariablesDomain} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link VariablesDomain}
   */
  @Test
  @DisplayName("Test new VariablesDomain (default constructor)")
  void testNewVariablesDomain() {
    // Arrange and Act
    VariablesDomain actualVariablesDomain = new VariablesDomain();

    // Assert
    assertNull(actualVariablesDomain.getUpdateTime());
    assertEquals(0, actualVariablesDomain.getRevision());
    assertTrue(actualVariablesDomain.getOutputs().isEmpty());
  }
}
