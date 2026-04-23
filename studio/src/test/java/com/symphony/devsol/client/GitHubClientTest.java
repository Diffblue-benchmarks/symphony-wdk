package com.symphony.devsol.client;

import com.symphony.devsol.model.github.GitHubContent;
import com.symphony.devsol.model.github.GitHubTree;
import com.symphony.devsol.model.github.GitHubTreeNode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GitHubClientTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private RestTemplateBuilder restTemplateBuilder;

    private GitHubClient gitHubClient;

    @BeforeEach
    void setUp() {
        when(restTemplateBuilder.defaultHeader(anyString(), anyString())).thenReturn(restTemplateBuilder);
        when(restTemplateBuilder.build()).thenReturn(restTemplate);
        gitHubClient = new GitHubClient("test-token", restTemplateBuilder);
    }

    @Test
    void listGalleryCategories_returnsCategoryNames() {
        GitHubTreeNode node1 = new GitHubTreeNode();
        node1.setPath("categories/automation");
        GitHubTreeNode node2 = new GitHubTreeNode();
        node2.setPath("categories/automation/workflow1.swadl.yaml");
        GitHubTreeNode node3 = new GitHubTreeNode();
        node3.setPath("other/path");

        GitHubTree tree = new GitHubTree();
        tree.setTree(Arrays.asList(node1, node2, node3));

        when(restTemplate.getForObject(anyString(), eq(GitHubTree.class))).thenReturn(tree);

        List<String> result = gitHubClient.listGalleryCategories();

        assertThat(result, contains("automation"));
    }

    @Test
    void listGalleryWorkflows_returnsWorkflowFiles() {
        GitHubTreeNode node1 = new GitHubTreeNode();
        node1.setPath("categories/automation/workflow1.swadl.yaml");
        GitHubTreeNode node2 = new GitHubTreeNode();
        node2.setPath("categories/automation/README.md");
        GitHubTreeNode node3 = new GitHubTreeNode();
        node3.setPath("categories/other/workflow2.swadl.yaml");

        GitHubTree tree = new GitHubTree();
        tree.setTree(Arrays.asList(node1, node2, node3));

        when(restTemplate.getForObject(anyString(), eq(GitHubTree.class))).thenReturn(tree);

        List<String> result = gitHubClient.listGalleryWorkflows("automation");

        assertThat(result, contains("workflow1.swadl.yaml"));
    }

    @Test
    void getWorkflow_returnsDecodedContent() {
        String content = "workflow content";
        String encoded = Base64.getEncoder().encodeToString(content.getBytes(StandardCharsets.UTF_8));

        GitHubContent githubContent = new GitHubContent();
        githubContent.setContent(encoded);

        when(restTemplate.getForObject(anyString(), eq(GitHubContent.class))).thenReturn(githubContent);

        String result = gitHubClient.getWorkflow("automation", "workflow1", "workflow1.swadl.yaml");

        assertThat(result, is(content));
    }

    @Test
    void getReadme_withCategory_returnsDecodedContent() {
        String content = "# README";
        String encoded = Base64.getEncoder().encodeToString(content.getBytes(StandardCharsets.UTF_8));

        GitHubContent githubContent = new GitHubContent();
        githubContent.setContent(encoded);

        when(restTemplate.getForObject(anyString(), eq(GitHubContent.class))).thenReturn(githubContent);

        String result = gitHubClient.getReadme("automation");

        assertThat(result, is(content));
    }

    @Test
    void getReadme_withCategoryAndWorkflow_returnsDecodedContent() {
        String content = "# Workflow README";
        String encoded = Base64.getEncoder().encodeToString(content.getBytes(StandardCharsets.UTF_8));

        GitHubContent githubContent = new GitHubContent();
        githubContent.setContent(encoded);

        when(restTemplate.getForObject(anyString(), eq(GitHubContent.class))).thenReturn(githubContent);

        String result = gitHubClient.getReadme("automation", "workflow1");

        assertThat(result, is(content));
    }
}
