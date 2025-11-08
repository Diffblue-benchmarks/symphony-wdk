package com.symphony.bdk.workflow.api.v1.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.api.v1.dto.SwadlView.SwadlViewBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {SwadlViewBuilder.class})
@ExtendWith(SpringExtension.class)
class SwadlViewDiffblueTest {
  @Autowired
  private SwadlViewBuilder swadlViewBuilder;

  /**
   * Test {@link SwadlView#equals(Object)}, and {@link SwadlView#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link SwadlView#equals(Object)}
   *   <li>{@link SwadlView#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SwadlView.equals(Object)", "int SwadlView.hashCode()"})
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
   * Test {@link SwadlView#equals(Object)}, and {@link SwadlView#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link SwadlView#equals(Object)}
   *   <li>{@link SwadlView#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SwadlView.equals(Object)", "int SwadlView.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    SwadlViewBuilder swadlViewBuilder = mock(SwadlViewBuilder.class);
    when(swadlViewBuilder.createdBy(Mockito.<Long>any())).thenReturn(SwadlView.builder());
    SwadlView buildResult = swadlViewBuilder.createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();
    SwadlViewBuilder swadlViewBuilder2 = mock(SwadlViewBuilder.class);
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
   * Test {@link SwadlView#equals(Object)}, and {@link SwadlView#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link SwadlView#equals(Object)}
   *   <li>{@link SwadlView#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SwadlView.equals(Object)", "int SwadlView.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    SwadlViewBuilder swadlViewBuilder = mock(SwadlViewBuilder.class);
    when(swadlViewBuilder.description(Mockito.<String>any())).thenReturn(SwadlView.builder());
    SwadlViewBuilder swadlViewBuilder2 = mock(SwadlViewBuilder.class);
    when(swadlViewBuilder2.createdBy(Mockito.<Long>any())).thenReturn(swadlViewBuilder);
    SwadlView buildResult = swadlViewBuilder2.createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();
    SwadlViewBuilder swadlViewBuilder3 = mock(SwadlViewBuilder.class);
    when(swadlViewBuilder3.description(Mockito.<String>any())).thenReturn(SwadlView.builder());
    SwadlViewBuilder swadlViewBuilder4 = mock(SwadlViewBuilder.class);
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
   * Test {@link SwadlView#equals(Object)}, and {@link SwadlView#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link SwadlView#equals(Object)}
   *   <li>{@link SwadlView#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SwadlView.equals(Object)", "int SwadlView.hashCode()"})
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
   * Test {@link SwadlView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SwadlView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SwadlView.equals(Object)", "int SwadlView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    SwadlViewBuilder swadlViewBuilder = mock(SwadlViewBuilder.class);
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
   * Test {@link SwadlView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SwadlView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SwadlView.equals(Object)", "int SwadlView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    SwadlViewBuilder swadlViewBuilder = mock(SwadlViewBuilder.class);
    when(swadlViewBuilder.description(Mockito.<String>any())).thenReturn(SwadlView.builder());
    SwadlViewBuilder swadlViewBuilder2 = mock(SwadlViewBuilder.class);
    when(swadlViewBuilder2.createdBy(Mockito.<Long>any())).thenReturn(swadlViewBuilder);
    SwadlView buildResult = swadlViewBuilder2.createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();
    SwadlViewBuilder swadlViewBuilder3 = mock(SwadlViewBuilder.class);
    when(swadlViewBuilder3.createdBy(Mockito.<Long>any())).thenReturn(SwadlView.builder());
    SwadlView buildResult2 = swadlViewBuilder3.createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link SwadlView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SwadlView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SwadlView.equals(Object)", "int SwadlView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    SwadlViewBuilder swadlViewBuilder = mock(SwadlViewBuilder.class);
    when(swadlViewBuilder.swadl(Mockito.<String>any())).thenReturn(SwadlView.builder());
    SwadlViewBuilder swadlViewBuilder2 = mock(SwadlViewBuilder.class);
    when(swadlViewBuilder2.description(Mockito.<String>any())).thenReturn(swadlViewBuilder);
    SwadlViewBuilder swadlViewBuilder3 = mock(SwadlViewBuilder.class);
    when(swadlViewBuilder3.createdBy(Mockito.<Long>any())).thenReturn(swadlViewBuilder2);
    SwadlView buildResult = swadlViewBuilder3.createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();
    SwadlViewBuilder swadlViewBuilder4 = mock(SwadlViewBuilder.class);
    when(swadlViewBuilder4.description(Mockito.<String>any())).thenReturn(SwadlView.builder());
    SwadlViewBuilder swadlViewBuilder5 = mock(SwadlViewBuilder.class);
    when(swadlViewBuilder5.createdBy(Mockito.<Long>any())).thenReturn(swadlViewBuilder4);
    SwadlView buildResult2 = swadlViewBuilder5.createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link SwadlView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SwadlView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SwadlView.equals(Object)", "int SwadlView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    SwadlViewBuilder swadlViewBuilder = mock(SwadlViewBuilder.class);
    SwadlView buildResult = SwadlView.builder()
        .createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();
    when(swadlViewBuilder.build()).thenReturn(buildResult);
    SwadlViewBuilder swadlViewBuilder2 = mock(SwadlViewBuilder.class);
    when(swadlViewBuilder2.swadl(Mockito.<String>any())).thenReturn(swadlViewBuilder);
    SwadlViewBuilder swadlViewBuilder3 = mock(SwadlViewBuilder.class);
    when(swadlViewBuilder3.description(Mockito.<String>any())).thenReturn(swadlViewBuilder2);
    SwadlViewBuilder swadlViewBuilder4 = mock(SwadlViewBuilder.class);
    when(swadlViewBuilder4.createdBy(Mockito.<Long>any())).thenReturn(swadlViewBuilder3);
    SwadlView buildResult2 = swadlViewBuilder4.createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();
    SwadlViewBuilder swadlViewBuilder5 = mock(SwadlViewBuilder.class);
    when(swadlViewBuilder5.description(Mockito.<String>any())).thenReturn(SwadlView.builder());
    SwadlViewBuilder swadlViewBuilder6 = mock(SwadlViewBuilder.class);
    when(swadlViewBuilder6.createdBy(Mockito.<Long>any())).thenReturn(swadlViewBuilder5);
    SwadlView buildResult3 = swadlViewBuilder6.createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();

    // Act and Assert
    assertNotEquals(buildResult2, buildResult3);
  }

  /**
   * Test {@link SwadlView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SwadlView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SwadlView.equals(Object)", "int SwadlView.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    SwadlViewBuilder swadlViewBuilder = mock(SwadlViewBuilder.class);
    when(swadlViewBuilder.createdBy(Mockito.<Long>any())).thenReturn(SwadlView.builder());
    SwadlView buildResult = swadlViewBuilder.createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();
    SwadlViewBuilder swadlViewBuilder2 = mock(SwadlViewBuilder.class);
    when(swadlViewBuilder2.build()).thenReturn(buildResult);
    SwadlViewBuilder swadlViewBuilder3 = mock(SwadlViewBuilder.class);
    when(swadlViewBuilder3.swadl(Mockito.<String>any())).thenReturn(swadlViewBuilder2);
    SwadlViewBuilder swadlViewBuilder4 = mock(SwadlViewBuilder.class);
    when(swadlViewBuilder4.description(Mockito.<String>any())).thenReturn(swadlViewBuilder3);
    SwadlViewBuilder swadlViewBuilder5 = mock(SwadlViewBuilder.class);
    when(swadlViewBuilder5.createdBy(Mockito.<Long>any())).thenReturn(swadlViewBuilder4);
    SwadlView buildResult2 = swadlViewBuilder5.createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();
    SwadlViewBuilder swadlViewBuilder6 = mock(SwadlViewBuilder.class);
    when(swadlViewBuilder6.description(Mockito.<String>any())).thenReturn(SwadlView.builder());
    SwadlViewBuilder swadlViewBuilder7 = mock(SwadlViewBuilder.class);
    when(swadlViewBuilder7.createdBy(Mockito.<Long>any())).thenReturn(swadlViewBuilder6);
    SwadlView buildResult3 = swadlViewBuilder7.createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();

    // Act and Assert
    assertNotEquals(buildResult2, buildResult3);
  }

  /**
   * Test {@link SwadlView#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SwadlView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SwadlView.equals(Object)", "int SwadlView.hashCode()"})
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
   * Test {@link SwadlView#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SwadlView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SwadlView.equals(Object)", "int SwadlView.hashCode()"})
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
   * Test getters and setters.
   * <p>
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
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SwadlView.<init>(String, String, Long)", "Long SwadlView.getCreatedBy()",
      "String SwadlView.getDescription()", "String SwadlView.getSwadl()", "void SwadlView.setCreatedBy(Long)",
      "void SwadlView.setDescription(String)", "void SwadlView.setSwadl(String)", "String SwadlView.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    SwadlView actualSwadlView = new SwadlView("The characteristics of someone or something", "Swadl", 1L);
    actualSwadlView.setCreatedBy(1L);
    actualSwadlView.setDescription("The characteristics of someone or something");
    actualSwadlView.setSwadl("Swadl");
    String actualToStringResult = actualSwadlView.toString();
    Long actualCreatedBy = actualSwadlView.getCreatedBy();
    String actualDescription = actualSwadlView.getDescription();

    // Assert
    assertEquals("Swadl", actualSwadlView.getSwadl());
    assertEquals("SwadlView(description=The characteristics of someone or something, swadl=Swadl, createdBy=1)",
        actualToStringResult);
    assertEquals("The characteristics of someone or something", actualDescription);
    assertEquals(1L, actualCreatedBy.longValue());
  }

  /**
   * Test SwadlViewBuilder {@link SwadlViewBuilder#build()}.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link SwadlViewBuilder#build()}
   *   <li>{@link SwadlViewBuilder#createdBy(Long)}
   *   <li>{@link SwadlViewBuilder#description(String)}
   *   <li>{@link SwadlViewBuilder#swadl(String)}
   * </ul>
   */
  @Test
  @DisplayName("Test SwadlViewBuilder build()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SwadlViewBuilder.<init>()", "SwadlView SwadlViewBuilder.build()",
      "SwadlViewBuilder SwadlViewBuilder.createdBy(Long)", "SwadlViewBuilder SwadlViewBuilder.description(String)",
      "SwadlViewBuilder SwadlViewBuilder.swadl(String)", "String SwadlViewBuilder.toString()"})
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
