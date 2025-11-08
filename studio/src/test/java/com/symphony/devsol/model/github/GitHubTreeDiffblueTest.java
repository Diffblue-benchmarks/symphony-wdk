package com.symphony.devsol.model.github;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class GitHubTreeDiffblueTest {
  /**
   * Test {@link GitHubTree#equals(Object)}, and {@link GitHubTree#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GitHubTree#equals(Object)}
   *   <li>{@link GitHubTree#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GitHubTree.equals(Object)", "int GitHubTree.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    GitHubTree gitHubTree = new GitHubTree();
    gitHubTree.setTree(new ArrayList<>());

    GitHubTree gitHubTree2 = new GitHubTree();
    gitHubTree2.setTree(new ArrayList<>());

    // Act and Assert
    assertEquals(gitHubTree, gitHubTree2);
    int expectedHashCodeResult = gitHubTree.hashCode();
    assertEquals(expectedHashCodeResult, gitHubTree2.hashCode());
  }

  /**
   * Test {@link GitHubTree#equals(Object)}, and {@link GitHubTree#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GitHubTree#equals(Object)}
   *   <li>{@link GitHubTree#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GitHubTree.equals(Object)", "int GitHubTree.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    GitHubTree gitHubTree = new GitHubTree();
    gitHubTree.setTree(new ArrayList<>());

    // Act and Assert
    assertEquals(gitHubTree, gitHubTree);
    int expectedHashCodeResult = gitHubTree.hashCode();
    assertEquals(expectedHashCodeResult, gitHubTree.hashCode());
  }

  /**
   * Test {@link GitHubTree#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GitHubTree#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GitHubTree.equals(Object)", "int GitHubTree.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    GitHubTreeNode gitHubTreeNode = new GitHubTreeNode();
    gitHubTreeNode.setPath("Path");

    ArrayList<GitHubTreeNode> tree = new ArrayList<>();
    tree.add(gitHubTreeNode);

    GitHubTree gitHubTree = new GitHubTree();
    gitHubTree.setTree(tree);

    GitHubTree gitHubTree2 = new GitHubTree();
    gitHubTree2.setTree(new ArrayList<>());

    // Act and Assert
    assertNotEquals(gitHubTree, gitHubTree2);
  }

  /**
   * Test {@link GitHubTree#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GitHubTree#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GitHubTree.equals(Object)", "int GitHubTree.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    GitHubTree gitHubTree = new GitHubTree();
    gitHubTree.setTree(new ArrayList<>());

    // Act and Assert
    assertNotEquals(gitHubTree, null);
  }

  /**
   * Test {@link GitHubTree#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GitHubTree#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GitHubTree.equals(Object)", "int GitHubTree.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    GitHubTree gitHubTree = new GitHubTree();
    gitHubTree.setTree(new ArrayList<>());

    // Act and Assert
    assertNotEquals(gitHubTree, "Different type to GitHubTree");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link GitHubTree}
   *   <li>{@link GitHubTree#setTree(List)}
   *   <li>{@link GitHubTree#toString()}
   *   <li>{@link GitHubTree#getTree()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void GitHubTree.<init>()", "List GitHubTree.getTree()", "void GitHubTree.setTree(List)",
      "String GitHubTree.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    GitHubTree actualGitHubTree = new GitHubTree();
    ArrayList<GitHubTreeNode> tree = new ArrayList<>();
    actualGitHubTree.setTree(tree);
    String actualToStringResult = actualGitHubTree.toString();
    List<GitHubTreeNode> actualTree = actualGitHubTree.getTree();

    // Assert
    assertEquals("GitHubTree(tree=[])", actualToStringResult);
    assertTrue(actualTree.isEmpty());
    assertSame(tree, actualTree);
  }
}
