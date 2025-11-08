package com.symphony.devsol.model.github;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

class GitHubTreeNodeDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GitHubTreeNode#equals(Object)}
   *   <li>{@link GitHubTreeNode#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    GitHubTreeNode gitHubTreeNode = new GitHubTreeNode();
    gitHubTreeNode.setPath("Path");

    GitHubTreeNode gitHubTreeNode2 = new GitHubTreeNode();
    gitHubTreeNode2.setPath("Path");

    // Act and Assert
    assertEquals(gitHubTreeNode, gitHubTreeNode2);
    int expectedHashCodeResult = gitHubTreeNode.hashCode();
    assertEquals(expectedHashCodeResult, gitHubTreeNode2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GitHubTreeNode#equals(Object)}
   *   <li>{@link GitHubTreeNode#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    GitHubTreeNode gitHubTreeNode = new GitHubTreeNode();
    gitHubTreeNode.setPath(null);

    GitHubTreeNode gitHubTreeNode2 = new GitHubTreeNode();
    gitHubTreeNode2.setPath(null);

    // Act and Assert
    assertEquals(gitHubTreeNode, gitHubTreeNode2);
    int expectedHashCodeResult = gitHubTreeNode.hashCode();
    assertEquals(expectedHashCodeResult, gitHubTreeNode2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GitHubTreeNode#equals(Object)}
   *   <li>{@link GitHubTreeNode#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    GitHubTreeNode gitHubTreeNode = new GitHubTreeNode();
    gitHubTreeNode.setPath("Path");

    // Act and Assert
    assertEquals(gitHubTreeNode, gitHubTreeNode);
    int expectedHashCodeResult = gitHubTreeNode.hashCode();
    assertEquals(expectedHashCodeResult, gitHubTreeNode.hashCode());
  }

  /**
   * Method under test: {@link GitHubTreeNode#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    GitHubTreeNode gitHubTreeNode = new GitHubTreeNode();
    gitHubTreeNode.setPath(null);

    GitHubTreeNode gitHubTreeNode2 = new GitHubTreeNode();
    gitHubTreeNode2.setPath("Path");

    // Act and Assert
    assertNotEquals(gitHubTreeNode, gitHubTreeNode2);
  }

  /**
   * Method under test: {@link GitHubTreeNode#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    GitHubTreeNode gitHubTreeNode = new GitHubTreeNode();
    gitHubTreeNode.setPath("com.symphony.devsol.model.github.GitHubTreeNode");

    GitHubTreeNode gitHubTreeNode2 = new GitHubTreeNode();
    gitHubTreeNode2.setPath("Path");

    // Act and Assert
    assertNotEquals(gitHubTreeNode, gitHubTreeNode2);
  }

  /**
   * Method under test: {@link GitHubTreeNode#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    GitHubTreeNode gitHubTreeNode = new GitHubTreeNode();
    gitHubTreeNode.setPath("Path");

    // Act and Assert
    assertNotEquals(gitHubTreeNode, null);
  }

  /**
   * Method under test: {@link GitHubTreeNode#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    GitHubTreeNode gitHubTreeNode = new GitHubTreeNode();
    gitHubTreeNode.setPath("Path");

    // Act and Assert
    assertNotEquals(gitHubTreeNode, "Different type to GitHubTreeNode");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link GitHubTreeNode}
   *   <li>{@link GitHubTreeNode#setPath(String)}
   *   <li>{@link GitHubTreeNode#toString()}
   *   <li>{@link GitHubTreeNode#getPath()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    GitHubTreeNode actualGitHubTreeNode = new GitHubTreeNode();
    actualGitHubTreeNode.setPath("Path");
    String actualToStringResult = actualGitHubTreeNode.toString();

    // Assert that nothing has changed
    assertEquals("GitHubTreeNode(path=Path)", actualToStringResult);
    assertEquals("Path", actualGitHubTreeNode.getPath());
  }
}
