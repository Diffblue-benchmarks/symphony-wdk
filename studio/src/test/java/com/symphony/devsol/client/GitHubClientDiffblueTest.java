package com.symphony.devsol.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.devsol.model.github.GitHubContent;
import com.symphony.devsol.model.github.GitHubTree;
import com.symphony.devsol.model.github.GitHubTreeNode;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@ContextConfiguration(classes = {GitHubClient.class, String.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class GitHubClientDiffblueTest {
  @MockBean
  private GitHubClient gitHubClient;

  @MockBean
  private RestTemplateBuilder restTemplateBuilder;

  /**
   * Method under test: {@link GitHubClient#listGalleryCategories()}
   */
  @Test
  void testListGalleryCategories() throws RestClientException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    GitHubTree gitHubTree = new GitHubTree();
    gitHubTree.setTree(new ArrayList<>());
    RestTemplate restTemplate = mock(RestTemplate.class);
    when(restTemplate.getForObject(Mockito.<String>any(), Mockito.<Class<GitHubTree>>any(), isA(Object[].class)))
        .thenReturn(gitHubTree);
    RestTemplateBuilder restTemplateBuilder = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder.build()).thenReturn(restTemplate);
    RestTemplateBuilder restTemplateBuilder2 = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder2.defaultHeader(Mockito.<String>any(), isA(String[].class)))
        .thenReturn(restTemplateBuilder);
    RestTemplateBuilder restTemplateBuilder3 = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder3.defaultHeader(Mockito.<String>any(), isA(String[].class)))
        .thenReturn(restTemplateBuilder2);

    // Act
    List<String> actualListGalleryCategoriesResult = (new GitHubClient("ABC123", restTemplateBuilder3))
        .listGalleryCategories();

    // Assert
    verify(restTemplateBuilder).build();
    verify(restTemplateBuilder2).defaultHeader(eq("Accept"), isA(String[].class));
    verify(restTemplateBuilder3).defaultHeader(eq("Authorization"), isA(String[].class));
    verify(restTemplate).getForObject(
        eq("https://api.github.com/repos/finos/symphony-wdk-gallery/git/trees/main?recursive=1"), isA(Class.class),
        isA(Object[].class));
    assertTrue(actualListGalleryCategoriesResult.isEmpty());
  }

  /**
   * Method under test: {@link GitHubClient#listGalleryCategories()}
   */
  @Test
  void testListGalleryCategories2() throws RestClientException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    GitHubTreeNode gitHubTreeNode = new GitHubTreeNode();
    gitHubTreeNode.setPath("/git/trees/main?recursive=1");

    ArrayList<GitHubTreeNode> tree = new ArrayList<>();
    tree.add(gitHubTreeNode);

    GitHubTree gitHubTree = new GitHubTree();
    gitHubTree.setTree(tree);
    RestTemplate restTemplate = mock(RestTemplate.class);
    when(restTemplate.getForObject(Mockito.<String>any(), Mockito.<Class<GitHubTree>>any(), isA(Object[].class)))
        .thenReturn(gitHubTree);
    RestTemplateBuilder restTemplateBuilder = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder.build()).thenReturn(restTemplate);
    RestTemplateBuilder restTemplateBuilder2 = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder2.defaultHeader(Mockito.<String>any(), isA(String[].class)))
        .thenReturn(restTemplateBuilder);
    RestTemplateBuilder restTemplateBuilder3 = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder3.defaultHeader(Mockito.<String>any(), isA(String[].class)))
        .thenReturn(restTemplateBuilder2);

    // Act
    List<String> actualListGalleryCategoriesResult = (new GitHubClient("ABC123", restTemplateBuilder3))
        .listGalleryCategories();

    // Assert
    verify(restTemplateBuilder).build();
    verify(restTemplateBuilder2).defaultHeader(eq("Accept"), isA(String[].class));
    verify(restTemplateBuilder3).defaultHeader(eq("Authorization"), isA(String[].class));
    verify(restTemplate).getForObject(
        eq("https://api.github.com/repos/finos/symphony-wdk-gallery/git/trees/main?recursive=1"), isA(Class.class),
        isA(Object[].class));
    assertTrue(actualListGalleryCategoriesResult.isEmpty());
  }

  /**
   * Method under test: {@link GitHubClient#listGalleryCategories()}
   */
  @Test
  void testListGalleryCategories3() throws RestClientException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
    when(restTemplate.getForObject(Mockito.<String>any(), Mockito.<Class<GitHubTree>>any(), isA(Object[].class)))
        .thenReturn(gitHubTree);
    RestTemplateBuilder restTemplateBuilder = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder.build()).thenReturn(restTemplate);
    RestTemplateBuilder restTemplateBuilder2 = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder2.defaultHeader(Mockito.<String>any(), isA(String[].class)))
        .thenReturn(restTemplateBuilder);
    RestTemplateBuilder restTemplateBuilder3 = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder3.defaultHeader(Mockito.<String>any(), isA(String[].class)))
        .thenReturn(restTemplateBuilder2);

    // Act
    List<String> actualListGalleryCategoriesResult = (new GitHubClient("ABC123", restTemplateBuilder3))
        .listGalleryCategories();

    // Assert
    verify(restTemplateBuilder).build();
    verify(restTemplateBuilder2).defaultHeader(eq("Accept"), isA(String[].class));
    verify(restTemplateBuilder3).defaultHeader(eq("Authorization"), isA(String[].class));
    verify(restTemplate).getForObject(
        eq("https://api.github.com/repos/finos/symphony-wdk-gallery/git/trees/main?recursive=1"), isA(Class.class),
        isA(Object[].class));
    assertEquals(1, actualListGalleryCategoriesResult.size());
    assertEquals("", actualListGalleryCategoriesResult.get(0));
  }

  /**
   * Method under test: {@link GitHubClient#listGalleryWorkflows(String)}
   */
  @Test
  void testListGalleryWorkflows() throws RestClientException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    GitHubTree gitHubTree = new GitHubTree();
    gitHubTree.setTree(new ArrayList<>());
    RestTemplate restTemplate = mock(RestTemplate.class);
    when(restTemplate.getForObject(Mockito.<String>any(), Mockito.<Class<GitHubTree>>any(), isA(Object[].class)))
        .thenReturn(gitHubTree);
    RestTemplateBuilder restTemplateBuilder = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder.build()).thenReturn(restTemplate);
    RestTemplateBuilder restTemplateBuilder2 = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder2.defaultHeader(Mockito.<String>any(), isA(String[].class)))
        .thenReturn(restTemplateBuilder);
    RestTemplateBuilder restTemplateBuilder3 = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder3.defaultHeader(Mockito.<String>any(), isA(String[].class)))
        .thenReturn(restTemplateBuilder2);

    // Act
    List<String> actualListGalleryWorkflowsResult = (new GitHubClient("ABC123", restTemplateBuilder3))
        .listGalleryWorkflows("Category");

    // Assert
    verify(restTemplateBuilder).build();
    verify(restTemplateBuilder2).defaultHeader(eq("Accept"), isA(String[].class));
    verify(restTemplateBuilder3).defaultHeader(eq("Authorization"), isA(String[].class));
    verify(restTemplate).getForObject(
        eq("https://api.github.com/repos/finos/symphony-wdk-gallery/git/trees/main?recursive=1"), isA(Class.class),
        isA(Object[].class));
    assertTrue(actualListGalleryWorkflowsResult.isEmpty());
  }

  /**
   * Method under test: {@link GitHubClient#listGalleryWorkflows(String)}
   */
  @Test
  void testListGalleryWorkflows2() throws RestClientException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    GitHubTreeNode gitHubTreeNode = new GitHubTreeNode();
    gitHubTreeNode.setPath("/git/trees/main?recursive=1");

    ArrayList<GitHubTreeNode> tree = new ArrayList<>();
    tree.add(gitHubTreeNode);

    GitHubTree gitHubTree = new GitHubTree();
    gitHubTree.setTree(tree);
    RestTemplate restTemplate = mock(RestTemplate.class);
    when(restTemplate.getForObject(Mockito.<String>any(), Mockito.<Class<GitHubTree>>any(), isA(Object[].class)))
        .thenReturn(gitHubTree);
    RestTemplateBuilder restTemplateBuilder = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder.build()).thenReturn(restTemplate);
    RestTemplateBuilder restTemplateBuilder2 = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder2.defaultHeader(Mockito.<String>any(), isA(String[].class)))
        .thenReturn(restTemplateBuilder);
    RestTemplateBuilder restTemplateBuilder3 = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder3.defaultHeader(Mockito.<String>any(), isA(String[].class)))
        .thenReturn(restTemplateBuilder2);

    // Act
    List<String> actualListGalleryWorkflowsResult = (new GitHubClient("ABC123", restTemplateBuilder3))
        .listGalleryWorkflows("Category");

    // Assert
    verify(restTemplateBuilder).build();
    verify(restTemplateBuilder2).defaultHeader(eq("Accept"), isA(String[].class));
    verify(restTemplateBuilder3).defaultHeader(eq("Authorization"), isA(String[].class));
    verify(restTemplate).getForObject(
        eq("https://api.github.com/repos/finos/symphony-wdk-gallery/git/trees/main?recursive=1"), isA(Class.class),
        isA(Object[].class));
    assertTrue(actualListGalleryWorkflowsResult.isEmpty());
  }

  /**
   * Method under test: {@link GitHubClient#listGalleryWorkflows(String)}
   */
  @Test
  void testListGalleryWorkflows3() throws RestClientException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
    when(restTemplate.getForObject(Mockito.<String>any(), Mockito.<Class<GitHubTree>>any(), isA(Object[].class)))
        .thenReturn(gitHubTree);
    RestTemplateBuilder restTemplateBuilder = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder.build()).thenReturn(restTemplate);
    RestTemplateBuilder restTemplateBuilder2 = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder2.defaultHeader(Mockito.<String>any(), isA(String[].class)))
        .thenReturn(restTemplateBuilder);
    RestTemplateBuilder restTemplateBuilder3 = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder3.defaultHeader(Mockito.<String>any(), isA(String[].class)))
        .thenReturn(restTemplateBuilder2);

    // Act
    List<String> actualListGalleryWorkflowsResult = (new GitHubClient("ABC123", restTemplateBuilder3))
        .listGalleryWorkflows("Category");

    // Assert
    verify(restTemplateBuilder).build();
    verify(restTemplateBuilder2).defaultHeader(eq("Accept"), isA(String[].class));
    verify(restTemplateBuilder3).defaultHeader(eq("Authorization"), isA(String[].class));
    verify(restTemplate).getForObject(
        eq("https://api.github.com/repos/finos/symphony-wdk-gallery/git/trees/main?recursive=1"), isA(Class.class),
        isA(Object[].class));
    assertTrue(actualListGalleryWorkflowsResult.isEmpty());
  }

  /**
   * Method under test: {@link GitHubClient#getWorkflow(String, String, String)}
   */
  @Test
  void testGetWorkflow() throws RestClientException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    GitHubContent gitHubContent = mock(GitHubContent.class);
    when(gitHubContent.getContent()).thenReturn("foo");
    doNothing().when(gitHubContent).setContent(Mockito.<String>any());
    gitHubContent.setContent("Not all who wander are lost");
    RestTemplate restTemplate = mock(RestTemplate.class);
    when(restTemplate.getForObject(Mockito.<String>any(), Mockito.<Class<GitHubContent>>any(), isA(Object[].class)))
        .thenReturn(gitHubContent);
    RestTemplateBuilder restTemplateBuilder = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder.build()).thenReturn(restTemplate);
    RestTemplateBuilder restTemplateBuilder2 = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder2.defaultHeader(Mockito.<String>any(), isA(String[].class)))
        .thenReturn(restTemplateBuilder);
    RestTemplateBuilder restTemplateBuilder3 = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder3.defaultHeader(Mockito.<String>any(), isA(String[].class)))
        .thenReturn(restTemplateBuilder2);

    // Act
    String actualWorkflow = (new GitHubClient("ABC123", restTemplateBuilder3)).getWorkflow("Category", "Workflow",
        "File");

    // Assert
    verify(gitHubContent).getContent();
    verify(gitHubContent).setContent(eq("Not all who wander are lost"));
    verify(restTemplateBuilder).build();
    verify(restTemplateBuilder2).defaultHeader(eq("Accept"), isA(String[].class));
    verify(restTemplateBuilder3).defaultHeader(eq("Authorization"), isA(String[].class));
    verify(restTemplate).getForObject(
        eq("https://api.github.com/repos/finos/symphony-wdk-gallery/contents/categories/Category/Workflow/File"),
        isA(Class.class), isA(Object[].class));
    assertEquals("~�", actualWorkflow);
  }

  /**
   * Method under test: {@link GitHubClient#getReadme(String)}
   */
  @Test
  void testGetReadme() throws Exception {
    // Arrange
    when(gitHubClient.getReadme(Mockito.<String>any())).thenReturn("Readme");
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/gallery/readme/{category}", "Category");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(gitHubClient)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
        .andExpect(MockMvcResultMatchers.content().string("Readme"));
  }

  /**
   * Method under test: {@link GitHubClient#getReadme(String, String)}
   */
  @Test
  void testGetReadme2() throws RestClientException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    GitHubContent gitHubContent = mock(GitHubContent.class);
    when(gitHubContent.getContent()).thenReturn("foo");
    doNothing().when(gitHubContent).setContent(Mockito.<String>any());
    gitHubContent.setContent("Not all who wander are lost");
    RestTemplate restTemplate = mock(RestTemplate.class);
    when(restTemplate.getForObject(Mockito.<String>any(), Mockito.<Class<GitHubContent>>any(), isA(Object[].class)))
        .thenReturn(gitHubContent);
    RestTemplateBuilder restTemplateBuilder = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder.build()).thenReturn(restTemplate);
    RestTemplateBuilder restTemplateBuilder2 = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder2.defaultHeader(Mockito.<String>any(), isA(String[].class)))
        .thenReturn(restTemplateBuilder);
    RestTemplateBuilder restTemplateBuilder3 = mock(RestTemplateBuilder.class);
    when(restTemplateBuilder3.defaultHeader(Mockito.<String>any(), isA(String[].class)))
        .thenReturn(restTemplateBuilder2);

    // Act
    String actualReadme = (new GitHubClient("ABC123", restTemplateBuilder3)).getReadme("Category", "Workflow");

    // Assert
    verify(gitHubContent).getContent();
    verify(gitHubContent).setContent(eq("Not all who wander are lost"));
    verify(restTemplateBuilder).build();
    verify(restTemplateBuilder2).defaultHeader(eq("Accept"), isA(String[].class));
    verify(restTemplateBuilder3).defaultHeader(eq("Authorization"), isA(String[].class));
    verify(restTemplate).getForObject(
        eq("https://api.github.com/repos/finos/symphony-wdk-gallery/contents/categories/Category/Workflow/README.md"),
        isA(Class.class), isA(Object[].class));
    assertEquals("~�", actualReadme);
  }
}
