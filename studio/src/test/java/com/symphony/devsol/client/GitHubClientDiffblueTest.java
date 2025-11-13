package com.symphony.devsol.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.devsol.model.github.GitHubContent;
import com.symphony.devsol.model.github.GitHubTree;
import com.symphony.devsol.model.github.GitHubTreeNode;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

class GitHubClientDiffblueTest {
  /**
   * Test {@link GitHubClient#listGalleryCategories()}.
   *
   * <ul>
   *   <li>Given {@link GitHubTree} (default constructor) Tree is {@link ArrayList#ArrayList()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link GitHubClient#listGalleryCategories()}
   */
  @Test
  @DisplayName(
      "Test listGalleryCategories(); given GitHubTree (default constructor) Tree is ArrayList(); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List GitHubClient.listGalleryCategories()"})
  void testListGalleryCategories_givenGitHubTreeTreeIsArrayList_thenReturnEmpty()
      throws RestClientException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    GitHubTree gitHubTree = new GitHubTree();
    gitHubTree.setTree(new ArrayList<>());

    RestTemplate restTemplate = mock(RestTemplate.class);
    when(restTemplate.getForObject(
            Mockito.<String>any(), eq(GitHubTree.class), isA(Object[].class)))
        .thenReturn(gitHubTree);

    RestTemplateBuilder restTemplateBuilder = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder.build()).thenReturn(restTemplate);

    RestTemplateBuilder restTemplateBuilder2 = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder2.defaultHeader(Mockito.<String>any(), isA(String[].class)))
        .thenReturn(restTemplateBuilder);

    RestTemplateBuilder restTemplateBuilder3 = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder3.defaultHeader(Mockito.<String>any(), isA(String[].class)))
        .thenReturn(restTemplateBuilder2);
    GitHubClient gitHubClient = new GitHubClient("ABC123", restTemplateBuilder3);

    // Act
    List<String> actualListGalleryCategoriesResult = gitHubClient.listGalleryCategories();

    // Assert
    verify(restTemplateBuilder).build();
    verify(restTemplateBuilder2).defaultHeader(eq("Accept"), isA(String[].class));
    verify(restTemplateBuilder3).defaultHeader(eq("Authorization"), isA(String[].class));
    verify(restTemplate)
        .getForObject(
            eq(
                "https://api.github.com/repos/finos/symphony-wdk-gallery/git/trees/main?recursive=1"),
            isA(Class.class),
            isA(Object[].class));
    assertTrue(actualListGalleryCategoriesResult.isEmpty());
  }

  /**
   * Test {@link GitHubClient#listGalleryCategories()}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link GitHubClient#listGalleryCategories()}
   */
  @Test
  @DisplayName("Test listGalleryCategories(); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List GitHubClient.listGalleryCategories()"})
  void testListGalleryCategories_thenReturnEmpty() throws RestClientException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    GitHubTreeNode gitHubTreeNode = new GitHubTreeNode();
    gitHubTreeNode.setPath("/git/trees/main?recursive=1");

    ArrayList<GitHubTreeNode> tree = new ArrayList<>();
    tree.add(gitHubTreeNode);

    GitHubTree gitHubTree = new GitHubTree();
    gitHubTree.setTree(tree);

    RestTemplate restTemplate = mock(RestTemplate.class);
    when(restTemplate.getForObject(
            Mockito.<String>any(), eq(GitHubTree.class), isA(Object[].class)))
        .thenReturn(gitHubTree);

    RestTemplateBuilder restTemplateBuilder = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder.build()).thenReturn(restTemplate);

    RestTemplateBuilder restTemplateBuilder2 = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder2.defaultHeader(Mockito.<String>any(), isA(String[].class)))
        .thenReturn(restTemplateBuilder);

    RestTemplateBuilder restTemplateBuilder3 = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder3.defaultHeader(Mockito.<String>any(), isA(String[].class)))
        .thenReturn(restTemplateBuilder2);
    GitHubClient gitHubClient = new GitHubClient("ABC123", restTemplateBuilder3);

    // Act
    List<String> actualListGalleryCategoriesResult = gitHubClient.listGalleryCategories();

    // Assert
    verify(restTemplateBuilder).build();
    verify(restTemplateBuilder2).defaultHeader(eq("Accept"), isA(String[].class));
    verify(restTemplateBuilder3).defaultHeader(eq("Authorization"), isA(String[].class));
    verify(restTemplate)
        .getForObject(
            eq(
                "https://api.github.com/repos/finos/symphony-wdk-gallery/git/trees/main?recursive=1"),
            isA(Class.class),
            isA(Object[].class));
    assertTrue(actualListGalleryCategoriesResult.isEmpty());
  }

  /**
   * Test {@link GitHubClient#listGalleryCategories()}.
   *
   * <ul>
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link GitHubClient#listGalleryCategories()}
   */
  @Test
  @DisplayName("Test listGalleryCategories(); then return size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List GitHubClient.listGalleryCategories()"})
  void testListGalleryCategories_thenReturnSizeIsOne() throws RestClientException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    GitHubTreeNode gitHubTreeNode = new GitHubTreeNode();
    gitHubTreeNode.setPath("/git/trees/main?recursive=1");

    GitHubTreeNode gitHubTreeNode2 = new GitHubTreeNode();
    gitHubTreeNode2.setPath("categories/");

    ArrayList<GitHubTreeNode> tree = new ArrayList<>();
    tree.add(gitHubTreeNode2);
    tree.add(gitHubTreeNode);

    GitHubTree gitHubTree = new GitHubTree();
    gitHubTree.setTree(tree);

    RestTemplate restTemplate = mock(RestTemplate.class);
    when(restTemplate.getForObject(
            Mockito.<String>any(), eq(GitHubTree.class), isA(Object[].class)))
        .thenReturn(gitHubTree);

    RestTemplateBuilder restTemplateBuilder = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder.build()).thenReturn(restTemplate);

    RestTemplateBuilder restTemplateBuilder2 = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder2.defaultHeader(Mockito.<String>any(), isA(String[].class)))
        .thenReturn(restTemplateBuilder);

    RestTemplateBuilder restTemplateBuilder3 = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder3.defaultHeader(Mockito.<String>any(), isA(String[].class)))
        .thenReturn(restTemplateBuilder2);
    GitHubClient gitHubClient = new GitHubClient("ABC123", restTemplateBuilder3);

    // Act
    List<String> actualListGalleryCategoriesResult = gitHubClient.listGalleryCategories();

    // Assert
    verify(restTemplateBuilder).build();
    verify(restTemplateBuilder2).defaultHeader(eq("Accept"), isA(String[].class));
    verify(restTemplateBuilder3).defaultHeader(eq("Authorization"), isA(String[].class));
    verify(restTemplate)
        .getForObject(
            eq(
                "https://api.github.com/repos/finos/symphony-wdk-gallery/git/trees/main?recursive=1"),
            isA(Class.class),
            isA(Object[].class));
    assertEquals(1, actualListGalleryCategoriesResult.size());
    assertEquals("", actualListGalleryCategoriesResult.get(0));
  }

  /**
   * Test {@link GitHubClient#listGalleryWorkflows(String)}.
   *
   * <ul>
   *   <li>Given {@link GitHubTreeNode} (default constructor) Path is {@code
   *       /git/trees/main?recursive=1}.
   * </ul>
   *
   * <p>Method under test: {@link GitHubClient#listGalleryWorkflows(String)}
   */
  @Test
  @DisplayName(
      "Test listGalleryWorkflows(String); given GitHubTreeNode (default constructor) Path is '/git/trees/main?recursive=1'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List GitHubClient.listGalleryWorkflows(String)"})
  void testListGalleryWorkflows_givenGitHubTreeNodePathIsGitTreesMainRecursive1()
      throws RestClientException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    GitHubTreeNode gitHubTreeNode = new GitHubTreeNode();
    gitHubTreeNode.setPath("/git/trees/main?recursive=1");

    ArrayList<GitHubTreeNode> tree = new ArrayList<>();
    tree.add(gitHubTreeNode);

    GitHubTree gitHubTree = new GitHubTree();
    gitHubTree.setTree(tree);

    RestTemplate restTemplate = mock(RestTemplate.class);
    when(restTemplate.getForObject(
            Mockito.<String>any(), eq(GitHubTree.class), isA(Object[].class)))
        .thenReturn(gitHubTree);

    RestTemplateBuilder restTemplateBuilder = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder.build()).thenReturn(restTemplate);

    RestTemplateBuilder restTemplateBuilder2 = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder2.defaultHeader(Mockito.<String>any(), isA(String[].class)))
        .thenReturn(restTemplateBuilder);

    RestTemplateBuilder restTemplateBuilder3 = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder3.defaultHeader(Mockito.<String>any(), isA(String[].class)))
        .thenReturn(restTemplateBuilder2);
    GitHubClient gitHubClient = new GitHubClient("ABC123", restTemplateBuilder3);

    // Act
    List<String> actualListGalleryWorkflowsResult = gitHubClient.listGalleryWorkflows("Category");

    // Assert
    verify(restTemplateBuilder).build();
    verify(restTemplateBuilder2).defaultHeader(eq("Accept"), isA(String[].class));
    verify(restTemplateBuilder3).defaultHeader(eq("Authorization"), isA(String[].class));
    verify(restTemplate)
        .getForObject(
            eq(
                "https://api.github.com/repos/finos/symphony-wdk-gallery/git/trees/main?recursive=1"),
            isA(Class.class),
            isA(Object[].class));
    assertTrue(actualListGalleryWorkflowsResult.isEmpty());
  }

  /**
   * Test {@link GitHubClient#listGalleryWorkflows(String)}.
   *
   * <ul>
   *   <li>Given {@link GitHubTreeNode} (default constructor) Path is {@code Path}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link GitHubClient#listGalleryWorkflows(String)}
   */
  @Test
  @DisplayName(
      "Test listGalleryWorkflows(String); given GitHubTreeNode (default constructor) Path is 'Path'; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List GitHubClient.listGalleryWorkflows(String)"})
  void testListGalleryWorkflows_givenGitHubTreeNodePathIsPath_thenReturnEmpty()
      throws RestClientException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    GitHubTreeNode gitHubTreeNode = new GitHubTreeNode();
    gitHubTreeNode.setPath("/git/trees/main?recursive=1");

    GitHubTreeNode gitHubTreeNode2 = new GitHubTreeNode();
    gitHubTreeNode2.setPath("Path");

    ArrayList<GitHubTreeNode> tree = new ArrayList<>();
    tree.add(gitHubTreeNode2);
    tree.add(gitHubTreeNode);

    GitHubTree gitHubTree = new GitHubTree();
    gitHubTree.setTree(tree);

    RestTemplate restTemplate = mock(RestTemplate.class);
    when(restTemplate.getForObject(
            Mockito.<String>any(), eq(GitHubTree.class), isA(Object[].class)))
        .thenReturn(gitHubTree);

    RestTemplateBuilder restTemplateBuilder = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder.build()).thenReturn(restTemplate);

    RestTemplateBuilder restTemplateBuilder2 = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder2.defaultHeader(Mockito.<String>any(), isA(String[].class)))
        .thenReturn(restTemplateBuilder);

    RestTemplateBuilder restTemplateBuilder3 = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder3.defaultHeader(Mockito.<String>any(), isA(String[].class)))
        .thenReturn(restTemplateBuilder2);
    GitHubClient gitHubClient = new GitHubClient("ABC123", restTemplateBuilder3);

    // Act
    List<String> actualListGalleryWorkflowsResult = gitHubClient.listGalleryWorkflows("Category");

    // Assert
    verify(restTemplateBuilder).build();
    verify(restTemplateBuilder2).defaultHeader(eq("Accept"), isA(String[].class));
    verify(restTemplateBuilder3).defaultHeader(eq("Authorization"), isA(String[].class));
    verify(restTemplate)
        .getForObject(
            eq(
                "https://api.github.com/repos/finos/symphony-wdk-gallery/git/trees/main?recursive=1"),
            isA(Class.class),
            isA(Object[].class));
    assertTrue(actualListGalleryWorkflowsResult.isEmpty());
  }

  /**
   * Test {@link GitHubClient#listGalleryWorkflows(String)}.
   *
   * <ul>
   *   <li>Given {@link GitHubTree} (default constructor) Tree is {@link ArrayList#ArrayList()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link GitHubClient#listGalleryWorkflows(String)}
   */
  @Test
  @DisplayName(
      "Test listGalleryWorkflows(String); given GitHubTree (default constructor) Tree is ArrayList(); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List GitHubClient.listGalleryWorkflows(String)"})
  void testListGalleryWorkflows_givenGitHubTreeTreeIsArrayList_thenReturnEmpty()
      throws RestClientException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    GitHubTree gitHubTree = new GitHubTree();
    gitHubTree.setTree(new ArrayList<>());

    RestTemplate restTemplate = mock(RestTemplate.class);
    when(restTemplate.getForObject(
            Mockito.<String>any(), eq(GitHubTree.class), isA(Object[].class)))
        .thenReturn(gitHubTree);

    RestTemplateBuilder restTemplateBuilder = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder.build()).thenReturn(restTemplate);

    RestTemplateBuilder restTemplateBuilder2 = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder2.defaultHeader(Mockito.<String>any(), isA(String[].class)))
        .thenReturn(restTemplateBuilder);

    RestTemplateBuilder restTemplateBuilder3 = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder3.defaultHeader(Mockito.<String>any(), isA(String[].class)))
        .thenReturn(restTemplateBuilder2);
    GitHubClient gitHubClient = new GitHubClient("ABC123", restTemplateBuilder3);

    // Act
    List<String> actualListGalleryWorkflowsResult = gitHubClient.listGalleryWorkflows("Category");

    // Assert
    verify(restTemplateBuilder).build();
    verify(restTemplateBuilder2).defaultHeader(eq("Accept"), isA(String[].class));
    verify(restTemplateBuilder3).defaultHeader(eq("Authorization"), isA(String[].class));
    verify(restTemplate)
        .getForObject(
            eq(
                "https://api.github.com/repos/finos/symphony-wdk-gallery/git/trees/main?recursive=1"),
            isA(Class.class),
            isA(Object[].class));
    assertTrue(actualListGalleryWorkflowsResult.isEmpty());
  }

  /**
   * Test {@link GitHubClient#getWorkflow(String, String, String)}.
   *
   * <ul>
   *   <li>Given {@link GitHubContent} (default constructor) Content is lf.
   *   <li>Then return empty string.
   * </ul>
   *
   * <p>Method under test: {@link GitHubClient#getWorkflow(String, String, String)}
   */
  @Test
  @DisplayName(
      "Test getWorkflow(String, String, String); given GitHubContent (default constructor) Content is lf; then return empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String GitHubClient.getWorkflow(String, String, String)"})
  void testGetWorkflow_givenGitHubContentContentIsLf_thenReturnEmptyString()
      throws RestClientException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    GitHubContent gitHubContent = new GitHubContent();
    gitHubContent.setContent("\n");

    RestTemplate restTemplate = mock(RestTemplate.class);
    when(restTemplate.getForObject(
            Mockito.<String>any(), eq(GitHubContent.class), isA(Object[].class)))
        .thenReturn(gitHubContent);

    RestTemplateBuilder restTemplateBuilder = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder.build()).thenReturn(restTemplate);

    RestTemplateBuilder restTemplateBuilder2 = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder2.defaultHeader(Mockito.<String>any(), isA(String[].class)))
        .thenReturn(restTemplateBuilder);

    RestTemplateBuilder restTemplateBuilder3 = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder3.defaultHeader(Mockito.<String>any(), isA(String[].class)))
        .thenReturn(restTemplateBuilder2);
    GitHubClient gitHubClient = new GitHubClient("ABC123", restTemplateBuilder3);

    // Act
    String actualWorkflow = gitHubClient.getWorkflow("Category", "Workflow", "File");

    // Assert
    verify(restTemplateBuilder).build();
    verify(restTemplateBuilder2).defaultHeader(eq("Accept"), isA(String[].class));
    verify(restTemplateBuilder3).defaultHeader(eq("Authorization"), isA(String[].class));
    verify(restTemplate)
        .getForObject(
            eq(
                "https://api.github.com/repos/finos/symphony-wdk-gallery/contents/categories/Category/Workflow/File"),
            isA(Class.class),
            isA(Object[].class));
    assertEquals("", actualWorkflow);
  }

  /**
   * Test {@link GitHubClient#getWorkflow(String, String, String)}.
   *
   * <ul>
   *   <li>Then return replacement character replacement character z{.
   * </ul>
   *
   * <p>Method under test: {@link GitHubClient#getWorkflow(String, String, String)}
   */
  @Test
  @DisplayName(
      "Test getWorkflow(String, String, String); then return replacement character replacement character z{")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String GitHubClient.getWorkflow(String, String, String)"})
  void testGetWorkflow_thenReturnReplacementCharacterReplacementCharacterZ()
      throws RestClientException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    GitHubContent gitHubContent = new GitHubContent();
    gitHubContent.setContent("Content");

    RestTemplate restTemplate = mock(RestTemplate.class);
    when(restTemplate.getForObject(
            Mockito.<String>any(), eq(GitHubContent.class), isA(Object[].class)))
        .thenReturn(gitHubContent);

    RestTemplateBuilder restTemplateBuilder = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder.build()).thenReturn(restTemplate);

    RestTemplateBuilder restTemplateBuilder2 = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder2.defaultHeader(Mockito.<String>any(), isA(String[].class)))
        .thenReturn(restTemplateBuilder);

    RestTemplateBuilder restTemplateBuilder3 = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder3.defaultHeader(Mockito.<String>any(), isA(String[].class)))
        .thenReturn(restTemplateBuilder2);
    GitHubClient gitHubClient = new GitHubClient("ABC123", restTemplateBuilder3);

    // Act
    String actualWorkflow = gitHubClient.getWorkflow("Category", "Workflow", "File");

    // Assert
    verify(restTemplateBuilder).build();
    verify(restTemplateBuilder2).defaultHeader(eq("Accept"), isA(String[].class));
    verify(restTemplateBuilder3).defaultHeader(eq("Authorization"), isA(String[].class));
    verify(restTemplate)
        .getForObject(
            eq(
                "https://api.github.com/repos/finos/symphony-wdk-gallery/contents/categories/Category/Workflow/File"),
            isA(Class.class),
            isA(Object[].class));
    assertEquals("\n��z{", actualWorkflow);
  }

  /**
   * Test {@link GitHubClient#getReadme(String, String)} with {@code category}, {@code workflow}.
   *
   * <p>Method under test: {@link GitHubClient#getReadme(String, String)}
   */
  @Test
  @DisplayName("Test getReadme(String, String) with 'category', 'workflow'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String GitHubClient.getReadme(String, String)"})
  void testGetReadmeWithCategoryWorkflow() throws RestClientException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    GitHubContent gitHubContent = new GitHubContent();
    gitHubContent.setContent("Content");

    RestTemplate restTemplate = mock(RestTemplate.class);
    when(restTemplate.getForObject(
            Mockito.<String>any(), eq(GitHubContent.class), isA(Object[].class)))
        .thenReturn(gitHubContent);

    RestTemplateBuilder restTemplateBuilder = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder.build()).thenReturn(restTemplate);

    RestTemplateBuilder restTemplateBuilder2 = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder2.defaultHeader(Mockito.<String>any(), isA(String[].class)))
        .thenReturn(restTemplateBuilder);

    RestTemplateBuilder restTemplateBuilder3 = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder3.defaultHeader(Mockito.<String>any(), isA(String[].class)))
        .thenReturn(restTemplateBuilder2);
    GitHubClient gitHubClient = new GitHubClient("ABC123", restTemplateBuilder3);

    // Act
    String actualReadme = gitHubClient.getReadme("Category", "Workflow");

    // Assert
    verify(restTemplateBuilder).build();
    verify(restTemplateBuilder2).defaultHeader(eq("Accept"), isA(String[].class));
    verify(restTemplateBuilder3).defaultHeader(eq("Authorization"), isA(String[].class));
    verify(restTemplate)
        .getForObject(
            eq(
                "https://api.github.com/repos/finos/symphony-wdk-gallery/contents/categories/Category/Workflow/README.md"),
            isA(Class.class),
            isA(Object[].class));
    assertEquals("\n��z{", actualReadme);
  }

  /**
   * Test {@link GitHubClient#getReadme(String, String)} with {@code category}, {@code workflow}.
   *
   * <ul>
   *   <li>Then return empty string.
   * </ul>
   *
   * <p>Method under test: {@link GitHubClient#getReadme(String, String)}
   */
  @Test
  @DisplayName(
      "Test getReadme(String, String) with 'category', 'workflow'; then return empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String GitHubClient.getReadme(String, String)"})
  void testGetReadmeWithCategoryWorkflow_thenReturnEmptyString() throws RestClientException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    GitHubContent gitHubContent = new GitHubContent();
    gitHubContent.setContent("\n");

    RestTemplate restTemplate = mock(RestTemplate.class);
    when(restTemplate.getForObject(
            Mockito.<String>any(), eq(GitHubContent.class), isA(Object[].class)))
        .thenReturn(gitHubContent);

    RestTemplateBuilder restTemplateBuilder = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder.build()).thenReturn(restTemplate);

    RestTemplateBuilder restTemplateBuilder2 = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder2.defaultHeader(Mockito.<String>any(), isA(String[].class)))
        .thenReturn(restTemplateBuilder);

    RestTemplateBuilder restTemplateBuilder3 = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder3.defaultHeader(Mockito.<String>any(), isA(String[].class)))
        .thenReturn(restTemplateBuilder2);
    GitHubClient gitHubClient = new GitHubClient("ABC123", restTemplateBuilder3);

    // Act
    String actualReadme = gitHubClient.getReadme("Category", "Workflow");

    // Assert
    verify(restTemplateBuilder).build();
    verify(restTemplateBuilder2).defaultHeader(eq("Accept"), isA(String[].class));
    verify(restTemplateBuilder3).defaultHeader(eq("Authorization"), isA(String[].class));
    verify(restTemplate)
        .getForObject(
            eq(
                "https://api.github.com/repos/finos/symphony-wdk-gallery/contents/categories/Category/Workflow/README.md"),
            isA(Class.class),
            isA(Object[].class));
    assertEquals("", actualReadme);
  }

  /**
   * Test {@link GitHubClient#getReadme(String)} with {@code category}.
   *
   * <ul>
   *   <li>Given {@link GitHubContent} (default constructor) Content is lf.
   *   <li>Then return empty string.
   * </ul>
   *
   * <p>Method under test: {@link GitHubClient#getReadme(String)}
   */
  @Test
  @DisplayName(
      "Test getReadme(String) with 'category'; given GitHubContent (default constructor) Content is lf; then return empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String GitHubClient.getReadme(String)"})
  void testGetReadmeWithCategory_givenGitHubContentContentIsLf_thenReturnEmptyString()
      throws RestClientException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    GitHubContent gitHubContent = new GitHubContent();
    gitHubContent.setContent("\n");

    RestTemplate restTemplate = mock(RestTemplate.class);
    when(restTemplate.getForObject(
            Mockito.<String>any(), eq(GitHubContent.class), isA(Object[].class)))
        .thenReturn(gitHubContent);

    RestTemplateBuilder restTemplateBuilder = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder.build()).thenReturn(restTemplate);

    RestTemplateBuilder restTemplateBuilder2 = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder2.defaultHeader(Mockito.<String>any(), isA(String[].class)))
        .thenReturn(restTemplateBuilder);

    RestTemplateBuilder restTemplateBuilder3 = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder3.defaultHeader(Mockito.<String>any(), isA(String[].class)))
        .thenReturn(restTemplateBuilder2);
    GitHubClient gitHubClient = new GitHubClient("ABC123", restTemplateBuilder3);

    // Act
    String actualReadme = gitHubClient.getReadme("Category");

    // Assert
    verify(restTemplateBuilder).build();
    verify(restTemplateBuilder2).defaultHeader(eq("Accept"), isA(String[].class));
    verify(restTemplateBuilder3).defaultHeader(eq("Authorization"), isA(String[].class));
    verify(restTemplate)
        .getForObject(
            eq(
                "https://api.github.com/repos/finos/symphony-wdk-gallery/contents/categories/Category/README.md"),
            isA(Class.class),
            isA(Object[].class));
    assertEquals("", actualReadme);
  }

  /**
   * Test {@link GitHubClient#getReadme(String)} with {@code category}.
   *
   * <ul>
   *   <li>Then return replacement character replacement character z{.
   * </ul>
   *
   * <p>Method under test: {@link GitHubClient#getReadme(String)}
   */
  @Test
  @DisplayName(
      "Test getReadme(String) with 'category'; then return replacement character replacement character z{")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String GitHubClient.getReadme(String)"})
  void testGetReadmeWithCategory_thenReturnReplacementCharacterReplacementCharacterZ()
      throws RestClientException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    GitHubContent gitHubContent = new GitHubContent();
    gitHubContent.setContent("Content");

    RestTemplate restTemplate = mock(RestTemplate.class);
    when(restTemplate.getForObject(
            Mockito.<String>any(), eq(GitHubContent.class), isA(Object[].class)))
        .thenReturn(gitHubContent);

    RestTemplateBuilder restTemplateBuilder = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder.build()).thenReturn(restTemplate);

    RestTemplateBuilder restTemplateBuilder2 = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder2.defaultHeader(Mockito.<String>any(), isA(String[].class)))
        .thenReturn(restTemplateBuilder);

    RestTemplateBuilder restTemplateBuilder3 = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder3.defaultHeader(Mockito.<String>any(), isA(String[].class)))
        .thenReturn(restTemplateBuilder2);
    GitHubClient gitHubClient = new GitHubClient("ABC123", restTemplateBuilder3);

    // Act
    String actualReadme = gitHubClient.getReadme("Category");

    // Assert
    verify(restTemplateBuilder).build();
    verify(restTemplateBuilder2).defaultHeader(eq("Accept"), isA(String[].class));
    verify(restTemplateBuilder3).defaultHeader(eq("Authorization"), isA(String[].class));
    verify(restTemplate)
        .getForObject(
            eq(
                "https://api.github.com/repos/finos/symphony-wdk-gallery/contents/categories/Category/README.md"),
            isA(Class.class),
            isA(Object[].class));
    assertEquals("\n��z{", actualReadme);
  }
}
