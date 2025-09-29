package com.symphony.bdk.workflow.engine.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {SharedData.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
class SharedDataDiffblueTest {
  @Autowired private SharedData sharedData;

  /**
   * Test {@link SharedData#namespace(String)}.
   *
   * <p>Method under test: {@link SharedData#namespace(String)}
   */
  @Test
  @DisplayName("Test namespace(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"SharedData SharedData.namespace(String)"})
  void testNamespace() {
    // Arrange and Act
    SharedData actualNamespaceResult = sharedData.namespace("Namespace");

    // Assert
    assertEquals("Namespace", sharedData.getNamespace());
    assertSame(sharedData, actualNamespaceResult);
  }
}
