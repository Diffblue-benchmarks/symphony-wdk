package com.symphony.devsol.model.github;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class GitHubContentDiffblueTest {
  /**
   * Test {@link GitHubContent#equals(Object)}, and {@link GitHubContent#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GitHubContent#equals(Object)}
   *   <li>{@link GitHubContent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GitHubContent.equals(Object)", "int GitHubContent.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    GitHubContent gitHubContent = new GitHubContent();
    gitHubContent.setContent("Not all who wander are lost");

    GitHubContent gitHubContent2 = new GitHubContent();
    gitHubContent2.setContent("Not all who wander are lost");

    // Act and Assert
    assertEquals(gitHubContent, gitHubContent2);
    int expectedHashCodeResult = gitHubContent.hashCode();
    assertEquals(expectedHashCodeResult, gitHubContent2.hashCode());
  }

  /**
   * Test {@link GitHubContent#equals(Object)}, and {@link GitHubContent#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GitHubContent#equals(Object)}
   *   <li>{@link GitHubContent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GitHubContent.equals(Object)", "int GitHubContent.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    GitHubContent gitHubContent = new GitHubContent();
    gitHubContent.setContent(null);

    GitHubContent gitHubContent2 = new GitHubContent();
    gitHubContent2.setContent(null);

    // Act and Assert
    assertEquals(gitHubContent, gitHubContent2);
    int expectedHashCodeResult = gitHubContent.hashCode();
    assertEquals(expectedHashCodeResult, gitHubContent2.hashCode());
  }

  /**
   * Test {@link GitHubContent#equals(Object)}, and {@link GitHubContent#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GitHubContent#equals(Object)}
   *   <li>{@link GitHubContent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GitHubContent.equals(Object)", "int GitHubContent.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    GitHubContent gitHubContent = new GitHubContent();
    gitHubContent.setContent("Not all who wander are lost");

    // Act and Assert
    assertEquals(gitHubContent, gitHubContent);
    int expectedHashCodeResult = gitHubContent.hashCode();
    assertEquals(expectedHashCodeResult, gitHubContent.hashCode());
  }

  /**
   * Test {@link GitHubContent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GitHubContent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GitHubContent.equals(Object)", "int GitHubContent.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    GitHubContent gitHubContent = new GitHubContent();
    gitHubContent.setContent("Content");

    GitHubContent gitHubContent2 = new GitHubContent();
    gitHubContent2.setContent("Not all who wander are lost");

    // Act and Assert
    assertNotEquals(gitHubContent, gitHubContent2);
  }

  /**
   * Test {@link GitHubContent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GitHubContent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GitHubContent.equals(Object)", "int GitHubContent.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    GitHubContent gitHubContent = new GitHubContent();
    gitHubContent.setContent(null);

    GitHubContent gitHubContent2 = new GitHubContent();
    gitHubContent2.setContent("Not all who wander are lost");

    // Act and Assert
    assertNotEquals(gitHubContent, gitHubContent2);
  }

  /**
   * Test {@link GitHubContent#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GitHubContent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GitHubContent.equals(Object)", "int GitHubContent.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    GitHubContent gitHubContent = new GitHubContent();
    gitHubContent.setContent("Not all who wander are lost");

    // Act and Assert
    assertNotEquals(gitHubContent, null);
  }

  /**
   * Test {@link GitHubContent#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GitHubContent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GitHubContent.equals(Object)", "int GitHubContent.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    GitHubContent gitHubContent = new GitHubContent();
    gitHubContent.setContent("Not all who wander are lost");

    // Act and Assert
    assertNotEquals(gitHubContent, "Different type to GitHubContent");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link GitHubContent}
   *   <li>{@link GitHubContent#setContent(String)}
   *   <li>{@link GitHubContent#toString()}
   *   <li>{@link GitHubContent#getContent()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void GitHubContent.<init>()", "String GitHubContent.getContent()",
      "void GitHubContent.setContent(String)", "String GitHubContent.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    GitHubContent actualGitHubContent = new GitHubContent();
    actualGitHubContent.setContent("Not all who wander are lost");
    String actualToStringResult = actualGitHubContent.toString();

    // Assert
    assertEquals("GitHubContent(content=Not all who wander are lost)", actualToStringResult);
    assertEquals("Not all who wander are lost", actualGitHubContent.getContent());
  }
}
