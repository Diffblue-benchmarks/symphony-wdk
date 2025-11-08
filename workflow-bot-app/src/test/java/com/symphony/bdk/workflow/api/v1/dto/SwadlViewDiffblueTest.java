package com.symphony.bdk.workflow.api.v1.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SwadlViewDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SwadlView#equals(Object)}
   *   <li>{@link SwadlView#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    SwadlView buildResult = SwadlView.builder()
        .createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();
    SwadlView buildResult2 = SwadlView.builder()
        .createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SwadlView#equals(Object)}
   *   <li>{@link SwadlView#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    SwadlView.SwadlViewBuilder swadlViewBuilder = mock(SwadlView.SwadlViewBuilder.class);
    when(swadlViewBuilder.createdBy(Mockito.<Long>any())).thenReturn(SwadlView.builder());
    SwadlView buildResult = swadlViewBuilder.createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();
    SwadlView.SwadlViewBuilder swadlViewBuilder2 = mock(SwadlView.SwadlViewBuilder.class);
    when(swadlViewBuilder2.createdBy(Mockito.<Long>any())).thenReturn(SwadlView.builder());
    SwadlView buildResult2 = swadlViewBuilder2.createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SwadlView#equals(Object)}
   *   <li>{@link SwadlView#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    SwadlView.SwadlViewBuilder swadlViewBuilder = mock(SwadlView.SwadlViewBuilder.class);
    when(swadlViewBuilder.description(Mockito.<String>any())).thenReturn(SwadlView.builder());
    SwadlView.SwadlViewBuilder swadlViewBuilder2 = mock(SwadlView.SwadlViewBuilder.class);
    when(swadlViewBuilder2.createdBy(Mockito.<Long>any())).thenReturn(swadlViewBuilder);
    SwadlView buildResult = swadlViewBuilder2.createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();
    SwadlView.SwadlViewBuilder swadlViewBuilder3 = mock(SwadlView.SwadlViewBuilder.class);
    when(swadlViewBuilder3.description(Mockito.<String>any())).thenReturn(SwadlView.builder());
    SwadlView.SwadlViewBuilder swadlViewBuilder4 = mock(SwadlView.SwadlViewBuilder.class);
    when(swadlViewBuilder4.createdBy(Mockito.<Long>any())).thenReturn(swadlViewBuilder3);
    SwadlView buildResult2 = swadlViewBuilder4.createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SwadlView#equals(Object)}
   *   <li>{@link SwadlView#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    SwadlView buildResult = SwadlView.builder()
        .createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult.hashCode());
  }

  /**
   * Method under test: {@link SwadlView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    SwadlView.SwadlViewBuilder swadlViewBuilder = mock(SwadlView.SwadlViewBuilder.class);
    when(swadlViewBuilder.createdBy(Mockito.<Long>any())).thenReturn(SwadlView.builder());
    SwadlView buildResult = swadlViewBuilder.createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();
    SwadlView buildResult2 = SwadlView.builder()
        .createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link SwadlView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    SwadlView.SwadlViewBuilder swadlViewBuilder = mock(SwadlView.SwadlViewBuilder.class);
    when(swadlViewBuilder.description(Mockito.<String>any())).thenReturn(SwadlView.builder());
    SwadlView.SwadlViewBuilder swadlViewBuilder2 = mock(SwadlView.SwadlViewBuilder.class);
    when(swadlViewBuilder2.createdBy(Mockito.<Long>any())).thenReturn(swadlViewBuilder);
    SwadlView buildResult = swadlViewBuilder2.createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();
    SwadlView.SwadlViewBuilder swadlViewBuilder3 = mock(SwadlView.SwadlViewBuilder.class);
    when(swadlViewBuilder3.createdBy(Mockito.<Long>any())).thenReturn(SwadlView.builder());
    SwadlView buildResult2 = swadlViewBuilder3.createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link SwadlView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    SwadlView.SwadlViewBuilder swadlViewBuilder = mock(SwadlView.SwadlViewBuilder.class);
    when(swadlViewBuilder.swadl(Mockito.<String>any())).thenReturn(SwadlView.builder());
    SwadlView.SwadlViewBuilder swadlViewBuilder2 = mock(SwadlView.SwadlViewBuilder.class);
    when(swadlViewBuilder2.description(Mockito.<String>any())).thenReturn(swadlViewBuilder);
    SwadlView.SwadlViewBuilder swadlViewBuilder3 = mock(SwadlView.SwadlViewBuilder.class);
    when(swadlViewBuilder3.createdBy(Mockito.<Long>any())).thenReturn(swadlViewBuilder2);
    SwadlView buildResult = swadlViewBuilder3.createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();
    SwadlView.SwadlViewBuilder swadlViewBuilder4 = mock(SwadlView.SwadlViewBuilder.class);
    when(swadlViewBuilder4.description(Mockito.<String>any())).thenReturn(SwadlView.builder());
    SwadlView.SwadlViewBuilder swadlViewBuilder5 = mock(SwadlView.SwadlViewBuilder.class);
    when(swadlViewBuilder5.createdBy(Mockito.<Long>any())).thenReturn(swadlViewBuilder4);
    SwadlView buildResult2 = swadlViewBuilder5.createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link SwadlView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    SwadlView.SwadlViewBuilder swadlViewBuilder = mock(SwadlView.SwadlViewBuilder.class);
    SwadlView buildResult = SwadlView.builder()
        .createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();
    when(swadlViewBuilder.build()).thenReturn(buildResult);
    SwadlView.SwadlViewBuilder swadlViewBuilder2 = mock(SwadlView.SwadlViewBuilder.class);
    when(swadlViewBuilder2.swadl(Mockito.<String>any())).thenReturn(swadlViewBuilder);
    SwadlView.SwadlViewBuilder swadlViewBuilder3 = mock(SwadlView.SwadlViewBuilder.class);
    when(swadlViewBuilder3.description(Mockito.<String>any())).thenReturn(swadlViewBuilder2);
    SwadlView.SwadlViewBuilder swadlViewBuilder4 = mock(SwadlView.SwadlViewBuilder.class);
    when(swadlViewBuilder4.createdBy(Mockito.<Long>any())).thenReturn(swadlViewBuilder3);
    SwadlView buildResult2 = swadlViewBuilder4.createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();
    SwadlView.SwadlViewBuilder swadlViewBuilder5 = mock(SwadlView.SwadlViewBuilder.class);
    when(swadlViewBuilder5.description(Mockito.<String>any())).thenReturn(SwadlView.builder());
    SwadlView.SwadlViewBuilder swadlViewBuilder6 = mock(SwadlView.SwadlViewBuilder.class);
    when(swadlViewBuilder6.createdBy(Mockito.<Long>any())).thenReturn(swadlViewBuilder5);
    SwadlView buildResult3 = swadlViewBuilder6.createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();

    // Act and Assert
    assertNotEquals(buildResult2, buildResult3);
  }

  /**
   * Method under test: {@link SwadlView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    SwadlView.SwadlViewBuilder swadlViewBuilder = mock(SwadlView.SwadlViewBuilder.class);
    when(swadlViewBuilder.createdBy(Mockito.<Long>any())).thenReturn(SwadlView.builder());
    SwadlView buildResult = swadlViewBuilder.createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();
    SwadlView.SwadlViewBuilder swadlViewBuilder2 = mock(SwadlView.SwadlViewBuilder.class);
    when(swadlViewBuilder2.build()).thenReturn(buildResult);
    SwadlView.SwadlViewBuilder swadlViewBuilder3 = mock(SwadlView.SwadlViewBuilder.class);
    when(swadlViewBuilder3.swadl(Mockito.<String>any())).thenReturn(swadlViewBuilder2);
    SwadlView.SwadlViewBuilder swadlViewBuilder4 = mock(SwadlView.SwadlViewBuilder.class);
    when(swadlViewBuilder4.description(Mockito.<String>any())).thenReturn(swadlViewBuilder3);
    SwadlView.SwadlViewBuilder swadlViewBuilder5 = mock(SwadlView.SwadlViewBuilder.class);
    when(swadlViewBuilder5.createdBy(Mockito.<Long>any())).thenReturn(swadlViewBuilder4);
    SwadlView buildResult2 = swadlViewBuilder5.createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();
    SwadlView.SwadlViewBuilder swadlViewBuilder6 = mock(SwadlView.SwadlViewBuilder.class);
    when(swadlViewBuilder6.description(Mockito.<String>any())).thenReturn(SwadlView.builder());
    SwadlView.SwadlViewBuilder swadlViewBuilder7 = mock(SwadlView.SwadlViewBuilder.class);
    when(swadlViewBuilder7.createdBy(Mockito.<Long>any())).thenReturn(swadlViewBuilder6);
    SwadlView buildResult3 = swadlViewBuilder7.createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();

    // Act and Assert
    assertNotEquals(buildResult2, buildResult3);
  }

  /**
   * Method under test: {@link SwadlView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    SwadlView buildResult = SwadlView.builder()
        .createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, null);
  }

  /**
   * Method under test: {@link SwadlView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    SwadlView buildResult = SwadlView.builder()
        .createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, "Different type to SwadlView");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SwadlView#SwadlView(String, String, Long)}
   *   <li>{@link SwadlView#setCreatedBy(Long)}
   *   <li>{@link SwadlView#setDescription(String)}
   *   <li>{@link SwadlView#setSwadl(String)}
   *   <li>{@link SwadlView#toString()}
   *   <li>{@link SwadlView#getCreatedBy()}
   *   <li>{@link SwadlView#getDescription()}
   *   <li>{@link SwadlView#getSwadl()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    SwadlView actualSwadlView = new SwadlView("The characteristics of someone or something", "Swadl", 1L);
    actualSwadlView.setCreatedBy(1L);
    actualSwadlView.setDescription("The characteristics of someone or something");
    actualSwadlView.setSwadl("Swadl");
    String actualToStringResult = actualSwadlView.toString();
    Long actualCreatedBy = actualSwadlView.getCreatedBy();
    String actualDescription = actualSwadlView.getDescription();

    // Assert that nothing has changed
    assertEquals("Swadl", actualSwadlView.getSwadl());
    assertEquals("SwadlView(description=The characteristics of someone or something, swadl=Swadl, createdBy=1)",
        actualToStringResult);
    assertEquals("The characteristics of someone or something", actualDescription);
    assertEquals(1L, actualCreatedBy.longValue());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SwadlView.SwadlViewBuilder#build()}
   *   <li>{@link SwadlView.SwadlViewBuilder#createdBy(Long)}
   *   <li>{@link SwadlView.SwadlViewBuilder#description(String)}
   *   <li>{@link SwadlView.SwadlViewBuilder#swadl(String)}
   * </ul>
   */
  @Test
  void testSwadlViewBuilderBuild() {
    // Arrange and Act
    SwadlView actualBuildResult = SwadlView.builder()
        .createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();

    // Assert
    assertEquals("Swadl", actualBuildResult.getSwadl());
    assertEquals("The characteristics of someone or something", actualBuildResult.getDescription());
    assertEquals(1L, actualBuildResult.getCreatedBy().longValue());
  }
}
