package com.symphony.devsol.client;

import com.symphony.devsol.model.github.GitHubContent;
import com.symphony.devsol.model.github.GitHubTree;
import com.symphony.devsol.model.github.GitHubTreeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GitHubClientTest {
    private static final String BASE_URI = "https://api.github.com/repos/finos/symphony-wdk-gallery";

    @Mock
    private RestTemplateBuilder restTemplateBuilder;

    @Mock
    private RestTemplate restTemplate;

    private GitHubClient gitHubClient;

    @BeforeEach
    void setUp() {
        when(restTemplateBuilder.defaultHeader(any(), any())).thenReturn(restTemplateBuilder);
        when(restTemplateBuilder.build()).thenReturn(restTemplate);
    }

    @Test
    void shouldCreateClientWithAuthorizationHeader() {
        // Arrange
        String token = "test-token";
        ArgumentCaptor<String> headerNameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> headerValueCaptor = ArgumentCaptor.forClass(String.class);

        // Act
        gitHubClient = new GitHubClient(token, restTemplateBuilder);

        // Assert
        verify(restTemplateBuilder, times(2)).defaultHeader(headerNameCaptor.capture(), headerValueCaptor.capture());
        List<String> headerNames = headerNameCaptor.getAllValues();
        List<String> headerValues = headerValueCaptor.getAllValues();

        assertThat(headerNames, hasItem("Authorization"));
        assertThat(headerValues, hasItem("Bearer " + token));
        assertThat(headerNames, hasItem("Accept"));
        assertThat(headerValues, hasItem(MediaType.APPLICATION_JSON.toString()));
        verify(restTemplateBuilder).build();
    }

    @Test
    void shouldListGalleryCategoriesFromTree() {
        // Arrange
        gitHubClient = new GitHubClient("token", restTemplateBuilder);

        GitHubTreeNode node1 = new GitHubTreeNode();
        node1.setPath("categories/category1");
        GitHubTreeNode node2 = new GitHubTreeNode();
        node2.setPath("categories/category2");
        GitHubTreeNode node3 = new GitHubTreeNode();
        node3.setPath("categories/category1/workflow.swadl.yaml");
        GitHubTreeNode node4 = new GitHubTreeNode();
        node4.setPath("other/path");

        GitHubTree tree = new GitHubTree();
        tree.setTree(Arrays.asList(node1, node2, node3, node4));

        when(restTemplate.getForObject(eq(BASE_URI + "/git/trees/main?recursive=1"), eq(GitHubTree.class)))
            .thenReturn(tree);

        // Act
        List<String> categories = gitHubClient.listGalleryCategories();

        // Assert
        assertThat(categories, hasSize(2));
        assertThat(categories, containsInAnyOrder("category1", "category2"));
    }

    @Test
    void shouldListGalleryWorkflowsForCategory() {
        // Arrange
        gitHubClient = new GitHubClient("token", restTemplateBuilder);
        String category = "test-category";

        GitHubTreeNode node1 = new GitHubTreeNode();
        node1.setPath("categories/test-category/workflow1.swadl.yaml");
        GitHubTreeNode node2 = new GitHubTreeNode();
        node2.setPath("categories/test-category/workflow2.swadl.yaml");
        GitHubTreeNode node3 = new GitHubTreeNode();
        node3.setPath("categories/test-category/README.md");
        GitHubTreeNode node4 = new GitHubTreeNode();
        node4.setPath("categories/other-category/workflow3.swadl.yaml");

        GitHubTree tree = new GitHubTree();
        tree.setTree(Arrays.asList(node1, node2, node3, node4));

        when(restTemplate.getForObject(eq(BASE_URI + "/git/trees/main?recursive=1"), eq(GitHubTree.class)))
            .thenReturn(tree);

        // Act
        List<String> workflows = gitHubClient.listGalleryWorkflows(category);

        // Assert
        assertThat(workflows, hasSize(2));
        assertThat(workflows, containsInAnyOrder("workflow1.swadl.yaml", "workflow2.swadl.yaml"));
    }

    @Test
    void shouldGetWorkflowContent() {
        // Arrange
        gitHubClient = new GitHubClient("token", restTemplateBuilder);
        String category = "test-category";
        String workflow = "test-workflow";
        String file = "workflow.swadl.yaml";
        String expectedContent = "workflow: test content";
        String base64Content = Base64.getEncoder().encodeToString(expectedContent.getBytes(StandardCharsets.UTF_8));
        String base64WithNewlines = base64Content.substring(0, 10) + "\n" + base64Content.substring(10);

        GitHubContent content = new GitHubContent();
        content.setContent(base64WithNewlines);

        String expectedUri = BASE_URI + "/contents/categories/" + category + "/" + workflow + "/" + file;
        when(restTemplate.getForObject(eq(expectedUri), eq(GitHubContent.class)))
            .thenReturn(content);

        // Act
        String result = gitHubClient.getWorkflow(category, workflow, file);

        // Assert
        assertThat(result, is(expectedContent));
    }

    @Test
    void shouldGetReadmeForCategory() {
        // Arrange
        gitHubClient = new GitHubClient("token", restTemplateBuilder);
        String category = "test-category";
        String expectedContent = "# README content";
        String base64Content = Base64.getEncoder().encodeToString(expectedContent.getBytes(StandardCharsets.UTF_8));
        String base64WithNewlines = base64Content.substring(0, 8) + "\n" + base64Content.substring(8);

        GitHubContent content = new GitHubContent();
        content.setContent(base64WithNewlines);

        String expectedUri = BASE_URI + "/contents/categories/" + category + "/README.md";
        when(restTemplate.getForObject(eq(expectedUri), eq(GitHubContent.class)))
            .thenReturn(content);

        // Act
        String result = gitHubClient.getReadme(category);

        // Assert
        assertThat(result, is(expectedContent));
    }

    @Test
    void shouldGetReadmeForCategoryAndWorkflow() {
        // Arrange
        gitHubClient = new GitHubClient("token", restTemplateBuilder);
        String category = "test-category";
        String workflow = "test-workflow";
        String expectedContent = "# Workflow README";
        String base64Content = Base64.getEncoder().encodeToString(expectedContent.getBytes(StandardCharsets.UTF_8));
        String base64WithNewlines = base64Content.substring(0, 10) + "\n" + base64Content.substring(10);

        GitHubContent content = new GitHubContent();
        content.setContent(base64WithNewlines);

        String expectedUri = BASE_URI + "/contents/categories/" + category + "/" + workflow + "/README.md";
        when(restTemplate.getForObject(eq(expectedUri), eq(GitHubContent.class)))
            .thenReturn(content);

        // Act
        String result = gitHubClient.getReadme(category, workflow);

        // Assert
        assertThat(result, is(expectedContent));
    }

    @Test
    void shouldHandleEmptyTreeList() {
        // Arrange
        gitHubClient = new GitHubClient("token", restTemplateBuilder);

        GitHubTree tree = new GitHubTree();
        tree.setTree(Arrays.asList());

        when(restTemplate.getForObject(eq(BASE_URI + "/git/trees/main?recursive=1"), eq(GitHubTree.class)))
            .thenReturn(tree);

        // Act
        List<String> categories = gitHubClient.listGalleryCategories();

        // Assert
        assertThat(categories, is(empty()));
    }

    @Test
    void shouldDecodeBase64ContentWithoutNewlines() {
        // Arrange
        gitHubClient = new GitHubClient("token", restTemplateBuilder);
        String category = "simple";
        String workflow = "test";
        String file = "file.txt";
        String expectedContent = "simple content";
        String base64Content = Base64.getEncoder().encodeToString(expectedContent.getBytes(StandardCharsets.UTF_8));

        GitHubContent content = new GitHubContent();
        content.setContent(base64Content);

        String expectedUri = BASE_URI + "/contents/categories/" + category + "/" + workflow + "/" + file;
        when(restTemplate.getForObject(eq(expectedUri), eq(GitHubContent.class)))
            .thenReturn(content);

        // Act
        String result = gitHubClient.getWorkflow(category, workflow, file);

        // Assert
        assertThat(result, is(expectedContent));
    }
}
