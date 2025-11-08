package com.symphony.devsol.model.github;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class GitHubTreeNodeDiffblueTest {
  /**
   * Test {@link GitHubTreeNode#equals(Object)}, and {@link GitHubTreeNode#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GitHubTreeNode#equals(Object)}
   *   <li>{@link GitHubTreeNode#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GitHubTreeNode.equals(Object)", "int GitHubTreeNode.hashCode()"})
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
   * Test {@link GitHubTreeNode#equals(Object)}, and {@link GitHubTreeNode#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GitHubTreeNode#equals(Object)}
   *   <li>{@link GitHubTreeNode#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GitHubTreeNode.equals(Object)", "int GitHubTreeNode.hashCode()"})
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
   * Test {@link GitHubTreeNode#equals(Object)}, and {@link GitHubTreeNode#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GitHubTreeNode#equals(Object)}
   *   <li>{@link GitHubTreeNode#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GitHubTreeNode.equals(Object)", "int GitHubTreeNode.hashCode()"})
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
   * Test {@link GitHubTreeNode#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GitHubTreeNode#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GitHubTreeNode.equals(Object)", "int GitHubTreeNode.hashCode()"})
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
   * Test {@link GitHubTreeNode#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GitHubTreeNode#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GitHubTreeNode.equals(Object)", "int GitHubTreeNode.hashCode()"})
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
   * Test {@link GitHubTreeNode#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GitHubTreeNode#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GitHubTreeNode.equals(Object)", "int GitHubTreeNode.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    GitHubTreeNode gitHubTreeNode = new GitHubTreeNode();
    gitHubTreeNode.setPath("Path");

    // Act and Assert
    assertNotEquals(gitHubTreeNode, null);
  }

  /**
   * Test {@link GitHubTreeNode#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GitHubTreeNode#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GitHubTreeNode.equals(Object)", "int GitHubTreeNode.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    GitHubTreeNode gitHubTreeNode = new GitHubTreeNode();
    gitHubTreeNode.setPath("Path");

    // Act and Assert
    assertNotEquals(gitHubTreeNode, "Different type to GitHubTreeNode");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link GitHubTreeNode}
   *   <li>{@link GitHubTreeNode#setPath(String)}
   *   <li>{@link GitHubTreeNode#toString()}
   *   <li>{@link GitHubTreeNode#getPath()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void GitHubTreeNode.<init>()", "String GitHubTreeNode.getPath()",
      "void GitHubTreeNode.setPath(String)", "String GitHubTreeNode.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    GitHubTreeNode actualGitHubTreeNode = new GitHubTreeNode();
    actualGitHubTreeNode.setPath("Path");
    String actualToStringResult = actualGitHubTreeNode.toString();

    // Assert
    assertEquals("GitHubTreeNode(path=Path)", actualToStringResult);
    assertEquals("Path", actualGitHubTreeNode.getPath());
  }
}
