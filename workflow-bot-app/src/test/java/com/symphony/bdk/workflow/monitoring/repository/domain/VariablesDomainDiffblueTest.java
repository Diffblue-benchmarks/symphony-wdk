package com.symphony.bdk.workflow.monitoring.repository.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
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
   * Methods under test:
   * <ul>
   *   <li>{@link VariablesDomain#equals(Object)}
   *   <li>{@link VariablesDomain#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link VariablesDomain#equals(Object)}
   *   <li>{@link VariablesDomain#hashCode()}
   * </ul>
   */
  @Test
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
   * Method under test: {@link VariablesDomain#equals(Object)}
   */
  @Test
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
   * Method under test: {@link VariablesDomain#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    HashMap<String, Object> outputs = new HashMap<>();
    outputs.computeIfPresent("foo", mock(BiFunction.class));
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
   * Method under test: {@link VariablesDomain#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
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
   * Method under test: {@link VariablesDomain#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
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
   * Method under test: {@link VariablesDomain#equals(Object)}
   */
  @Test
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
   * Method under test: {@link VariablesDomain#equals(Object)}
   */
  @Test
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

    // Assert that nothing has changed
    assertEquals("VariablesDomain(outputs={}, revision=1, updateTime=1970-01-01T00:00:00Z)", actualToStringResult);
    assertEquals(1, actualRevision);
    assertTrue(actualOutputs.isEmpty());
    assertSame(outputs, actualOutputs);
    assertSame(actualUpdateTime.EPOCH, actualUpdateTime);
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link VariablesDomain}
   */
  @Test
  void testNewVariablesDomain() {
    // Arrange and Act
    VariablesDomain actualVariablesDomain = new VariablesDomain();

    // Assert
    assertNull(actualVariablesDomain.getUpdateTime());
    assertEquals(0, actualVariablesDomain.getRevision());
    assertTrue(actualVariablesDomain.getOutputs().isEmpty());
  }
}
