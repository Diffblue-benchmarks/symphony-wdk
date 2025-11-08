package com.symphony.bdk.workflow.management.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.api.v1.dto.SwadlView;
import com.symphony.bdk.workflow.management.repository.domain.VersionedWorkflow;
import com.symphony.bdk.workflow.swadl.v1.Activity;
import com.symphony.bdk.workflow.swadl.v1.Properties;
import com.symphony.bdk.workflow.swadl.v1.Workflow;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {VersionedWorkflowBiConverter.class, String.class})
@ExtendWith(SpringExtension.class)
class VersionedWorkflowBiConverterDiffblueTest {
  @Autowired
  private VersionedWorkflowBiConverter versionedWorkflowBiConverter;

  /**
   * Test {@link VersionedWorkflowBiConverter#apply(Workflow, SwadlView)} with {@code Workflow}, {@code SwadlView}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link Workflow} (default constructor) Properties is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VersionedWorkflowBiConverter#apply(Workflow, SwadlView)}
   */
  @Test
  @DisplayName("Test apply(Workflow, SwadlView) with 'Workflow', 'SwadlView'; given 'null'; when Workflow (default constructor) Properties is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"VersionedWorkflow VersionedWorkflowBiConverter.apply(Workflow, SwadlView)"})
  void testApplyWithWorkflowSwadlView_givenNull_whenWorkflowPropertiesIsNull() {
    // Arrange
    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);
    workflow.setProperties(null);
    SwadlView swadlView = SwadlView.builder()
        .createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();

    // Act
    VersionedWorkflow actualApplyResult = versionedWorkflowBiConverter.apply(workflow, swadlView);

    // Assert
    assertEquals("", actualApplyResult.getDeploymentId());
    assertEquals("42", actualApplyResult.getWorkflowId());
    assertEquals("Swadl", actualApplyResult.getSwadl());
    assertEquals("The characteristics of someone or something", actualApplyResult.getDescription());
    assertNull(actualApplyResult.getEtag());
    assertNull(actualApplyResult.getId());
    assertEquals(1L, actualApplyResult.getCreatedBy().longValue());
    assertEquals(1L, actualApplyResult.getVersion().longValue());
    assertTrue(actualApplyResult.getActive());
    assertTrue(actualApplyResult.getPublished());
  }

  /**
   * Test {@link VersionedWorkflowBiConverter#apply(Workflow, SwadlView)} with {@code Workflow}, {@code SwadlView}.
   * <ul>
   *   <li>Given {@code true}.</li>
   *   <li>Then calls {@link Workflow#getId()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VersionedWorkflowBiConverter#apply(Workflow, SwadlView)}
   */
  @Test
  @DisplayName("Test apply(Workflow, SwadlView) with 'Workflow', 'SwadlView'; given 'true'; then calls getId()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"VersionedWorkflow VersionedWorkflowBiConverter.apply(Workflow, SwadlView)"})
  void testApplyWithWorkflowSwadlView_givenTrue_thenCallsGetId() {
    // Arrange
    Properties properties = new Properties();
    properties.setPublish(true);
    Workflow workflow = mock(Workflow.class);
    when(workflow.isToPublish()).thenReturn(true);
    when(workflow.getVersion()).thenReturn(1L);
    when(workflow.getId()).thenReturn("42");
    doNothing().when(workflow).setActivities(Mockito.<List<Activity>>any());
    doNothing().when(workflow).setId(Mockito.<String>any());
    doNothing().when(workflow).setProperties(Mockito.<Properties>any());
    doNothing().when(workflow).setVariables(Mockito.<Map<String, Object>>any());
    doNothing().when(workflow).setVersion(Mockito.<Long>any());
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);
    SwadlView swadlView = SwadlView.builder()
        .createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();

    // Act
    VersionedWorkflow actualApplyResult = versionedWorkflowBiConverter.apply(workflow, swadlView);

    // Assert
    verify(workflow).getId();
    verify(workflow).getVersion();
    verify(workflow).isToPublish();
    verify(workflow).setActivities(isA(List.class));
    verify(workflow).setId(eq("42"));
    verify(workflow).setProperties(isA(Properties.class));
    verify(workflow).setVariables(isA(Map.class));
    verify(workflow).setVersion(eq(1L));
    assertEquals("", actualApplyResult.getDeploymentId());
    assertEquals("42", actualApplyResult.getWorkflowId());
    assertEquals("Swadl", actualApplyResult.getSwadl());
    assertEquals("The characteristics of someone or something", actualApplyResult.getDescription());
    assertNull(actualApplyResult.getEtag());
    assertNull(actualApplyResult.getId());
    assertEquals(1L, actualApplyResult.getCreatedBy().longValue());
    assertEquals(1L, actualApplyResult.getVersion().longValue());
    assertTrue(actualApplyResult.getActive());
    assertTrue(actualApplyResult.getPublished());
  }

  /**
   * Test {@link VersionedWorkflowBiConverter#apply(Workflow, SwadlView)} with {@code Workflow}, {@code SwadlView}.
   * <ul>
   *   <li>Then return not Published.</li>
   * </ul>
   * <p>
   * Method under test: {@link VersionedWorkflowBiConverter#apply(Workflow, SwadlView)}
   */
  @Test
  @DisplayName("Test apply(Workflow, SwadlView) with 'Workflow', 'SwadlView'; then return not Published")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"VersionedWorkflow VersionedWorkflowBiConverter.apply(Workflow, SwadlView)"})
  void testApplyWithWorkflowSwadlView_thenReturnNotPublished() {
    // Arrange
    Properties properties = mock(Properties.class);
    when(properties.getPublish()).thenReturn(false);
    doNothing().when(properties).setPublish(Mockito.<Boolean>any());
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);
    SwadlView swadlView = SwadlView.builder()
        .createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();

    // Act
    VersionedWorkflow actualApplyResult = versionedWorkflowBiConverter.apply(workflow, swadlView);

    // Assert
    verify(properties).getPublish();
    verify(properties).setPublish(eq(true));
    assertEquals("", actualApplyResult.getDeploymentId());
    assertEquals("42", actualApplyResult.getWorkflowId());
    assertEquals("Swadl", actualApplyResult.getSwadl());
    assertEquals("The characteristics of someone or something", actualApplyResult.getDescription());
    assertNull(actualApplyResult.getEtag());
    assertNull(actualApplyResult.getId());
    assertEquals(1L, actualApplyResult.getCreatedBy().longValue());
    assertEquals(1L, actualApplyResult.getVersion().longValue());
    assertFalse(actualApplyResult.getPublished());
    assertTrue(actualApplyResult.getActive());
  }

  /**
   * Test {@link VersionedWorkflowBiConverter#apply(Workflow, SwadlView)} with {@code Workflow}, {@code SwadlView}.
   * <ul>
   *   <li>When {@link Workflow} (default constructor) Properties is {@link Properties} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link VersionedWorkflowBiConverter#apply(Workflow, SwadlView)}
   */
  @Test
  @DisplayName("Test apply(Workflow, SwadlView) with 'Workflow', 'SwadlView'; when Workflow (default constructor) Properties is Properties (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"VersionedWorkflow VersionedWorkflowBiConverter.apply(Workflow, SwadlView)"})
  void testApplyWithWorkflowSwadlView_whenWorkflowPropertiesIsProperties() {
    // Arrange
    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);
    SwadlView swadlView = SwadlView.builder()
        .createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();

    // Act
    VersionedWorkflow actualApplyResult = versionedWorkflowBiConverter.apply(workflow, swadlView);

    // Assert
    assertEquals("", actualApplyResult.getDeploymentId());
    assertEquals("42", actualApplyResult.getWorkflowId());
    assertEquals("Swadl", actualApplyResult.getSwadl());
    assertEquals("The characteristics of someone or something", actualApplyResult.getDescription());
    assertNull(actualApplyResult.getEtag());
    assertNull(actualApplyResult.getId());
    assertEquals(1L, actualApplyResult.getCreatedBy().longValue());
    assertEquals(1L, actualApplyResult.getVersion().longValue());
    assertTrue(actualApplyResult.getActive());
    assertTrue(actualApplyResult.getPublished());
  }

  /**
   * Test {@link VersionedWorkflowBiConverter#VersionedWorkflowBiConverter(String)}.
   * <p>
   * Method under test: {@link VersionedWorkflowBiConverter#VersionedWorkflowBiConverter(String)}
   */
  @Test
  @DisplayName("Test new VersionedWorkflowBiConverter(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void VersionedWorkflowBiConverter.<init>(String)"})
  void testNewVersionedWorkflowBiConverter() {
    // Arrange and Act
    VersionedWorkflowBiConverter actualVersionedWorkflowBiConverter = new VersionedWorkflowBiConverter("42");

    // Assert
    Class<VersionedWorkflow> expectedTargetClass = VersionedWorkflow.class;
    assertEquals(expectedTargetClass, actualVersionedWorkflowBiConverter.getTargetClass());
    Class<Workflow> expectedSourceClass = Workflow.class;
    assertEquals(expectedSourceClass, actualVersionedWorkflowBiConverter.getSourceClass());
  }
}
