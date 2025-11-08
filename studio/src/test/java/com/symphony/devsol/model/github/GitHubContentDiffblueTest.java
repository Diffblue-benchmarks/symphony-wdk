package com.symphony.devsol.model.github;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

class GitHubContentDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GitHubContent#equals(Object)}
   *   <li>{@link GitHubContent#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link GitHubContent#equals(Object)}
   *   <li>{@link GitHubContent#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link GitHubContent#equals(Object)}
   *   <li>{@link GitHubContent#hashCode()}
   * </ul>
   */
  @Test
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
   * Method under test: {@link GitHubContent#equals(Object)}
   */
  @Test
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
   * Method under test: {@link GitHubContent#equals(Object)}
   */
  @Test
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
   * Method under test: {@link GitHubContent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    GitHubContent gitHubContent = new GitHubContent();
    gitHubContent.setContent("Not all who wander are lost");

    // Act and Assert
    assertNotEquals(gitHubContent, null);
  }

  /**
   * Method under test: {@link GitHubContent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    GitHubContent gitHubContent = new GitHubContent();
    gitHubContent.setContent("Not all who wander are lost");

    // Act and Assert
    assertNotEquals(gitHubContent, "Different type to GitHubContent");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link GitHubContent}
   *   <li>{@link GitHubContent#setContent(String)}
   *   <li>{@link GitHubContent#toString()}
   *   <li>{@link GitHubContent#getContent()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    GitHubContent actualGitHubContent = new GitHubContent();
    actualGitHubContent.setContent("Not all who wander are lost");
    String actualToStringResult = actualGitHubContent.toString();

    // Assert that nothing has changed
    assertEquals("GitHubContent(content=Not all who wander are lost)", actualToStringResult);
    assertEquals("Not all who wander are lost", actualGitHubContent.getContent());
  }
}
