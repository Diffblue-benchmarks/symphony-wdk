package com.symphony.bdk.workflow.api.v1.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.symphony.bdk.workflow.monitoring.repository.domain.VariablesDomain;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {VariableView.class})
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class VariableViewDiffblueTest {
  @Autowired
  private VariableView variableView;

  /**
   * Test {@link VariableView#equals(Object)}, and
   * {@link VariableView#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link VariableView#equals(Object)}
   *   <li>{@link VariableView#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    VariableView variableView = new VariableView();
    VariableView variableView2 = new VariableView();

    // Act and Assert
    assertEquals(variableView, variableView2);
    int expectedHashCodeResult = variableView.hashCode();
    assertEquals(expectedHashCodeResult, variableView2.hashCode());
  }

  /**
   * Test {@link VariableView#equals(Object)}, and
   * {@link VariableView#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link VariableView#equals(Object)}
   *   <li>{@link VariableView#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    VariableView variableView = new VariableView(new VariablesDomain());
    VariableView variableView2 = new VariableView(new VariablesDomain());

    // Act and Assert
    assertEquals(variableView, variableView2);
    int expectedHashCodeResult = variableView.hashCode();
    assertEquals(expectedHashCodeResult, variableView2.hashCode());
  }

  /**
   * Test {@link VariableView#equals(Object)}, and
   * {@link VariableView#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link VariableView#equals(Object)}
   *   <li>{@link VariableView#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    VariableView variableView = new VariableView();
    variableView.setUpdateTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    VariableView variableView2 = new VariableView();
    variableView2.setUpdateTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertEquals(variableView, variableView2);
    int expectedHashCodeResult = variableView.hashCode();
    assertEquals(expectedHashCodeResult, variableView2.hashCode());
  }

  /**
   * Test {@link VariableView#equals(Object)}, and
   * {@link VariableView#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link VariableView#equals(Object)}
   *   <li>{@link VariableView#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    VariableView variableView = new VariableView();

    // Act and Assert
    assertEquals(variableView, variableView);
    int expectedHashCodeResult = variableView.hashCode();
    assertEquals(expectedHashCodeResult, variableView.hashCode());
  }

  /**
   * Test {@link VariableView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    VariableView variableView = new VariableView(new VariablesDomain());

    // Act and Assert
    assertNotEquals(variableView, new VariableView());
  }

  /**
   * Test {@link VariableView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    VariableView variableView = new VariableView();

    // Act and Assert
    assertNotEquals(variableView, new VariableView(new VariablesDomain()));
  }

  /**
   * Test {@link VariableView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    VariableView variableView = new VariableView();
    variableView.setRevision(1);

    // Act and Assert
    assertNotEquals(variableView, new VariableView());
  }

  /**
   * Test {@link VariableView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    VariableView variableView = new VariableView();
    variableView.setUpdateTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertNotEquals(variableView, new VariableView());
  }

  /**
   * Test {@link VariableView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    VariableView variableView = new VariableView();

    VariableView variableView2 = new VariableView();
    variableView2.setUpdateTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertNotEquals(variableView, variableView2);
  }

  /**
   * Test {@link VariableView#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new VariableView(), null);
  }

  /**
   * Test {@link VariableView#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new VariableView(), "Different type to VariableView");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link VariableView#VariableView()}
   *   <li>{@link VariableView#setOutputs(Map)}
   *   <li>{@link VariableView#setRevision(int)}
   *   <li>{@link VariableView#setUpdateTime(Instant)}
   *   <li>{@link VariableView#toString()}
   *   <li>{@link VariableView#getOutputs()}
   *   <li>{@link VariableView#getRevision()}
   *   <li>{@link VariableView#getUpdateTime()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    VariableView actualVariableView = new VariableView();
    HashMap<String, Object> outputs = new HashMap<>();
    actualVariableView.setOutputs(outputs);
    actualVariableView.setRevision(1);
    actualVariableView.setUpdateTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    String actualToStringResult = actualVariableView.toString();
    Map<String, Object> actualOutputs = actualVariableView.getOutputs();
    int actualRevision = actualVariableView.getRevision();
    Instant actualUpdateTime = actualVariableView.getUpdateTime();

    // Assert
    assertEquals("VariableView(outputs={}, revision=1, updateTime=1970-01-01T00:00:00Z)", actualToStringResult);
    assertEquals(1, actualRevision);
    assertTrue(actualOutputs.isEmpty());
    assertSame(outputs, actualOutputs);
    assertSame(actualUpdateTime.EPOCH, actualUpdateTime);
  }

  /**
   * Test {@link VariableView#VariableView(VariablesDomain)}.
   * <p>
   * Method under test: {@link VariableView#VariableView(VariablesDomain)}
   */
  @Test
  @DisplayName("Test new VariableView(VariablesDomain)")
  void testNewVariableView() {
    // Arrange
    VariablesDomain domain = new VariablesDomain();
    domain.setOutputs(new HashMap<>());
    domain.setRevision(1);
    domain.setUpdateTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    VariableView actualVariableView = new VariableView(domain);

    // Assert
    Instant updateTime = actualVariableView.getUpdateTime();
    assertEquals(0, updateTime.getNano());
    assertEquals(0L, updateTime.getEpochSecond());
    assertEquals(1, actualVariableView.getRevision());
    assertTrue(actualVariableView.getOutputs().isEmpty());
  }
}
