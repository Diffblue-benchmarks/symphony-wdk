package com.symphony.devsol.client;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.devsol.model.github.GitHubContent;
import com.symphony.devsol.model.github.GitHubTree;
import com.symphony.devsol.model.github.GitHubTreeNode;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

class GitHubClientDiffblueTest {
  /**
   * Test {@link GitHubClient#listGalleryCategories()}.
   *
   * <p>Method under test: {@link GitHubClient#listGalleryCategories()}
   */
  @Test
  @DisplayName("Test listGalleryCategories()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List GitHubClient.listGalleryCategories()"})
  void testListGalleryCategories() throws Exception {
    // Arrange
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/gallery/categories");

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

    // Act and Assert
    MockMvcBuilders.standaloneSetup(gitHubClient)
        .build()
        .perform(requestBuilder)
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(content().string("[\"\"]"));
  }

  /**
   * Test {@link GitHubClient#listGalleryCategories()}.
   *
   * <ul>
   *   <li>Then content string {@code []}.
   * </ul>
   *
   * <p>Method under test: {@link GitHubClient#listGalleryCategories()}
   */
  @Test
  @DisplayName("Test listGalleryCategories(); then content string '[]'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List GitHubClient.listGalleryCategories()"})
  void testListGalleryCategories_thenContentStringLeftSquareBracketRightSquareBracket()
      throws Exception {
    // Arrange
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/gallery/categories");

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

    // Act and Assert
    MockMvcBuilders.standaloneSetup(gitHubClient)
        .build()
        .perform(requestBuilder)
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(content().string("[]"));
  }

  /**
   * Test {@link GitHubClient#listGalleryCategories()}.
   *
   * <ul>
   *   <li>Then content string {@code []}.
   * </ul>
   *
   * <p>Method under test: {@link GitHubClient#listGalleryCategories()}
   */
  @Test
  @DisplayName("Test listGalleryCategories(); then content string '[]'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List GitHubClient.listGalleryCategories()"})
  void testListGalleryCategories_thenContentStringLeftSquareBracketRightSquareBracket2()
      throws Exception {
    // Arrange
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/gallery/categories");

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

    // Act and Assert
    MockMvcBuilders.standaloneSetup(gitHubClient)
        .build()
        .perform(requestBuilder)
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(content().string("[]"));
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
  @MethodsUnderTest({"java.util.List GitHubClient.listGalleryWorkflows(String)"})
  void testListGalleryWorkflows_givenGitHubTreeNodePathIsGitTreesMainRecursive1() throws Exception {
    // Arrange
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/gallery/{category}/workflows", "Category");

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

    // Act and Assert
    MockMvcBuilders.standaloneSetup(gitHubClient)
        .build()
        .perform(requestBuilder)
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(content().string("[]"));
  }

  /**
   * Test {@link GitHubClient#listGalleryWorkflows(String)}.
   *
   * <ul>
   *   <li>Given {@link GitHubTreeNode} (default constructor) Path is {@code Path}.
   *   <li>Then status {@link StatusResultMatchers#isOk()}.
   * </ul>
   *
   * <p>Method under test: {@link GitHubClient#listGalleryWorkflows(String)}
   */
  @Test
  @DisplayName(
      "Test listGalleryWorkflows(String); given GitHubTreeNode (default constructor) Path is 'Path'; then status isOk()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List GitHubClient.listGalleryWorkflows(String)"})
  void testListGalleryWorkflows_givenGitHubTreeNodePathIsPath_thenStatusIsOk() throws Exception {
    // Arrange
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/gallery/{category}/workflows", "Category");

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

    // Act and Assert
    MockMvcBuilders.standaloneSetup(gitHubClient)
        .build()
        .perform(requestBuilder)
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(content().string("[]"));
  }

  /**
   * Test {@link GitHubClient#listGalleryWorkflows(String)}.
   *
   * <ul>
   *   <li>Given {@link GitHubTree} (default constructor) Tree is {@link ArrayList#ArrayList()}.
   *   <li>Then status {@link StatusResultMatchers#isOk()}.
   * </ul>
   *
   * <p>Method under test: {@link GitHubClient#listGalleryWorkflows(String)}
   */
  @Test
  @DisplayName(
      "Test listGalleryWorkflows(String); given GitHubTree (default constructor) Tree is ArrayList(); then status isOk()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List GitHubClient.listGalleryWorkflows(String)"})
  void testListGalleryWorkflows_givenGitHubTreeTreeIsArrayList_thenStatusIsOk() throws Exception {
    // Arrange
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/gallery/{category}/workflows", "Category");

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

    // Act and Assert
    MockMvcBuilders.standaloneSetup(gitHubClient)
        .build()
        .perform(requestBuilder)
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(content().string("[]"));
  }

  /**
   * Test {@link GitHubClient#getWorkflow(String, String, String)}.
   * <ul>
   *   <li>Given {@link GitHubContent} (default constructor) Content is {@code Content}.</li>
   *   <li>Then content string {@code ??z{}.</li>
   * </ul>
   * <p>
   * Method under test: {@link GitHubClient#getWorkflow(String, String, String)}
   */
  @Test
  @DisplayName(
      "Test getWorkflow(String, String, String); given GitHubContent (default constructor) Content is 'Content'; then content string '??z{'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String GitHubClient.getWorkflow(String, String, String)"})
  void testGetWorkflow_givenGitHubContentContentIsContent_thenContentStringZ() throws Exception {
    // Arrange
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.get(
            "/gallery/{category}/workflows/{workflow}/{file}", "Category", "Workflow", "File");

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

    // Act and Assert
    MockMvcBuilders.standaloneSetup(gitHubClient)
        .build()
        .perform(requestBuilder)
        .andExpect(status().isOk())
        .andExpect(content().contentType("text/plain;charset=ISO-8859-1"))
        .andExpect(content().string("\n??z{"));
  }

  /**
   * Test {@link GitHubClient#getWorkflow(String, String, String)}.
   *
   * <ul>
   *   <li>Given {@link GitHubContent} (default constructor) Content is lf.
   *   <li>Then content string empty string.
   * </ul>
   *
   * <p>Method under test: {@link GitHubClient#getWorkflow(String, String, String)}
   */
  @Test
  @DisplayName(
      "Test getWorkflow(String, String, String); given GitHubContent (default constructor) Content is lf; then content string empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String GitHubClient.getWorkflow(String, String, String)"})
  void testGetWorkflow_givenGitHubContentContentIsLf_thenContentStringEmptyString()
      throws Exception {
    // Arrange
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.get(
            "/gallery/{category}/workflows/{workflow}/{file}", "Category", "Workflow", "File");

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

    // Act and Assert
    MockMvcBuilders.standaloneSetup(gitHubClient)
        .build()
        .perform(requestBuilder)
        .andExpect(status().isOk())
        .andExpect(content().contentType("text/plain;charset=ISO-8859-1"))
        .andExpect(content().string(""));
  }

  /**
   * Test {@link GitHubClient#getReadme(String, String)} with {@code category}, {@code workflow}.
   *
   * <ul>
   *   <li>Then content string empty string.
   * </ul>
   *
   * <p>Method under test: {@link GitHubClient#getReadme(String, String)}
   */
  @Test
  @DisplayName(
      "Test getReadme(String, String) with 'category', 'workflow'; then content string empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String GitHubClient.getReadme(String, String)"})
  void testGetReadmeWithCategoryWorkflow_thenContentStringEmptyString() throws Exception {
    // Arrange
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/gallery/readme/{category}/{workflow}", "Category", "Workflow");

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

    // Act and Assert
    MockMvcBuilders.standaloneSetup(gitHubClient)
        .build()
        .perform(requestBuilder)
        .andExpect(status().isOk())
        .andExpect(content().contentType("text/plain;charset=ISO-8859-1"))
        .andExpect(content().string(""));
  }

  /**
   * Test {@link GitHubClient#getReadme(String, String)} with {@code category}, {@code workflow}.
   * <ul>
   *   <li>Then content string {@code ??z{}.</li>
   * </ul>
   * <p>
   * Method under test: {@link GitHubClient#getReadme(String, String)}
   */
  @Test
  @DisplayName(
      "Test getReadme(String, String) with 'category', 'workflow'; then content string '??z{'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String GitHubClient.getReadme(String, String)"})
  void testGetReadmeWithCategoryWorkflow_thenContentStringZ() throws Exception {
    // Arrange
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/gallery/readme/{category}/{workflow}", "Category", "Workflow");

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

    // Act and Assert
    MockMvcBuilders.standaloneSetup(gitHubClient)
        .build()
        .perform(requestBuilder)
        .andExpect(status().isOk())
        .andExpect(content().contentType("text/plain;charset=ISO-8859-1"))
        .andExpect(content().string("\n??z{"));
  }

  /**
   * Test {@link GitHubClient#getReadme(String)} with {@code category}.
   * <ul>
   *   <li>Given {@link GitHubContent} (default constructor) Content is {@code Content}.</li>
   *   <li>Then content string {@code ??z{}.</li>
   * </ul>
   * <p>
   * Method under test: {@link GitHubClient#getReadme(String)}
   */
  @Test
  @DisplayName(
      "Test getReadme(String) with 'category'; given GitHubContent (default constructor) Content is 'Content'; then content string '??z{'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String GitHubClient.getReadme(String)"})
  void testGetReadmeWithCategory_givenGitHubContentContentIsContent_thenContentStringZ()
      throws Exception {
    // Arrange
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/gallery/readme/{category}", "Category");

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

    // Act and Assert
    MockMvcBuilders.standaloneSetup(gitHubClient)
        .build()
        .perform(requestBuilder)
        .andExpect(status().isOk())
        .andExpect(content().contentType("text/plain;charset=ISO-8859-1"))
        .andExpect(content().string("\n??z{"));
  }

  /**
   * Test {@link GitHubClient#getReadme(String)} with {@code category}.
   *
   * <ul>
   *   <li>Then content string empty string.
   * </ul>
   *
   * <p>Method under test: {@link GitHubClient#getReadme(String)}
   */
  @Test
  @DisplayName("Test getReadme(String) with 'category'; then content string empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String GitHubClient.getReadme(String)"})
  void testGetReadmeWithCategory_thenContentStringEmptyString() throws Exception {
    // Arrange
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/gallery/readme/{category}", "Category");

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

    // Act and Assert
    MockMvcBuilders.standaloneSetup(gitHubClient)
        .build()
        .perform(requestBuilder)
        .andExpect(status().isOk())
        .andExpect(content().contentType("text/plain;charset=ISO-8859-1"))
        .andExpect(content().string(""));
  }
}
