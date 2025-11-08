package com.symphony.devsol.model.github;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GitHubTreeDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GitHubTree#equals(Object)}
   *   <li>{@link GitHubTree#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link GitHubTree#equals(Object)}
   *   <li>{@link GitHubTree#hashCode()}
   * </ul>
   */
  @Test
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
   * Method under test: {@link GitHubTree#equals(Object)}
   */
  @Test
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
   * Method under test: {@link GitHubTree#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    GitHubTreeNode gitHubTreeNode = mock(GitHubTreeNode.class);
    doNothing().when(gitHubTreeNode).setPath(Mockito.<String>any());
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
   * Method under test: {@link GitHubTree#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    GitHubTree gitHubTree = new GitHubTree();
    gitHubTree.setTree(new ArrayList<>());

    // Act and Assert
    assertNotEquals(gitHubTree, null);
  }

  /**
   * Method under test: {@link GitHubTree#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    GitHubTree gitHubTree = new GitHubTree();
    gitHubTree.setTree(new ArrayList<>());

    // Act and Assert
    assertNotEquals(gitHubTree, "Different type to GitHubTree");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link GitHubTree}
   *   <li>{@link GitHubTree#setTree(List)}
   *   <li>{@link GitHubTree#toString()}
   *   <li>{@link GitHubTree#getTree()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    GitHubTree actualGitHubTree = new GitHubTree();
    ArrayList<GitHubTreeNode> tree = new ArrayList<>();
    actualGitHubTree.setTree(tree);
    String actualToStringResult = actualGitHubTree.toString();
    List<GitHubTreeNode> actualTree = actualGitHubTree.getTree();

    // Assert that nothing has changed
    assertEquals("GitHubTree(tree=[])", actualToStringResult);
    assertTrue(actualTree.isEmpty());
    assertSame(tree, actualTree);
  }
}
