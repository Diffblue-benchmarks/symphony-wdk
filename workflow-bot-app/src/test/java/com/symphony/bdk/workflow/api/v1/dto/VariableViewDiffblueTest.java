package com.symphony.bdk.workflow.api.v1.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.monitoring.repository.domain.VariablesDomain;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
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
   * Methods under test:
   * <ul>
   *   <li>{@link VariableView#equals(Object)}
   *   <li>{@link VariableView#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link VariableView#equals(Object)}
   *   <li>{@link VariableView#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    VariableView variableView = new VariableView();

    // Act and Assert
    assertEquals(variableView, variableView);
    int expectedHashCodeResult = variableView.hashCode();
    assertEquals(expectedHashCodeResult, variableView.hashCode());
  }

  /**
   * Method under test: {@link VariableView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    VariableView variableView = new VariableView(new VariablesDomain());

    // Act and Assert
    assertNotEquals(variableView, new VariableView());
  }

  /**
   * Method under test: {@link VariableView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    VariableView variableView = new VariableView();

    // Act and Assert
    assertNotEquals(variableView, new VariableView(new VariablesDomain()));
  }

  /**
   * Method under test: {@link VariableView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    VariableView variableView = new VariableView();
    variableView.setRevision(1);

    // Act and Assert
    assertNotEquals(variableView, new VariableView());
  }

  /**
   * Method under test: {@link VariableView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    VariableView variableView = new VariableView();
    variableView.setUpdateTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertNotEquals(variableView, new VariableView());
  }

  /**
   * Method under test: {@link VariableView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    VariablesDomain domain = mock(VariablesDomain.class);
    when(domain.getRevision()).thenReturn(1);
    when(domain.getUpdateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    when(domain.getOutputs()).thenReturn(new HashMap<>());
    VariableView variableView = new VariableView(domain);

    // Act and Assert
    assertNotEquals(variableView, new VariableView());
  }

  /**
   * Method under test: {@link VariableView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new VariableView(), null);
  }

  /**
   * Method under test: {@link VariableView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new VariableView(), "Different type to VariableView");
  }

  /**
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

    // Assert that nothing has changed
    assertEquals("VariableView(outputs={}, revision=1, updateTime=1970-01-01T00:00:00Z)", actualToStringResult);
    assertEquals(1, actualRevision);
    assertTrue(actualOutputs.isEmpty());
    assertSame(outputs, actualOutputs);
    assertSame(actualUpdateTime.EPOCH, actualUpdateTime);
  }

  /**
   * Method under test: {@link VariableView#VariableView(VariablesDomain)}
   */
  @Test
  void testNewVariableView() {
    // Arrange
    VariablesDomain domain = new VariablesDomain();
    HashMap<String, Object> outputs = new HashMap<>();
    domain.setOutputs(outputs);
    domain.setRevision(1);
    domain.setUpdateTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    VariableView actualVariableView = new VariableView(domain);

    // Assert
    Instant updateTime = actualVariableView.getUpdateTime();
    assertEquals(0, updateTime.getNano());
    assertEquals(0L, updateTime.getEpochSecond());
    assertEquals(1, actualVariableView.getRevision());
    Map<String, Object> outputs2 = actualVariableView.getOutputs();
    assertTrue(outputs2.isEmpty());
    assertSame(outputs, outputs2);
  }

  /**
   * Method under test: {@link VariableView#VariableView(VariablesDomain)}
   */
  @Test
  void testNewVariableView2() {
    // Arrange
    VariablesDomain domain = mock(VariablesDomain.class);
    when(domain.getRevision()).thenReturn(1);
    when(domain.getUpdateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    HashMap<String, Object> stringObjectMap = new HashMap<>();
    when(domain.getOutputs()).thenReturn(stringObjectMap);
    doNothing().when(domain).setOutputs(Mockito.<Map<String, Object>>any());
    doNothing().when(domain).setRevision(anyInt());
    doNothing().when(domain).setUpdateTime(Mockito.<Instant>any());
    domain.setOutputs(new HashMap<>());
    domain.setRevision(1);
    domain.setUpdateTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    VariableView actualVariableView = new VariableView(domain);

    // Assert
    verify(domain).getOutputs();
    verify(domain).getRevision();
    verify(domain).getUpdateTime();
    verify(domain).setOutputs(isA(Map.class));
    verify(domain).setRevision(eq(1));
    verify(domain).setUpdateTime(isA(Instant.class));
    Instant updateTime = actualVariableView.getUpdateTime();
    assertEquals(0, updateTime.getNano());
    assertEquals(0L, updateTime.getEpochSecond());
    assertEquals(1, actualVariableView.getRevision());
    Map<String, Object> outputs = actualVariableView.getOutputs();
    assertTrue(outputs.isEmpty());
    assertSame(stringObjectMap, outputs);
  }
}
