package com.symphony.bdk.workflow.swadl.v1.activity.message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class UpdateMessageDiffblueTest {
  /**
   * Test {@link UpdateMessage#setContent(Object)}.
   *
   * <ul>
   *   <li>Given {@code template}.
   *   <li>Then {@link UpdateMessage} (default constructor) Template is {@code Not all who wander
   *       are lost}.
   * </ul>
   *
   * <p>Method under test: {@link UpdateMessage#setContent(Object)}
   */
  @Test
  @DisplayName(
      "Test setContent(Object); given 'template'; then UpdateMessage (default constructor) Template is 'Not all who wander are lost'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UpdateMessage.setContent(Object)"})
  void testSetContent_givenTemplate_thenUpdateMessageTemplateIsNotAllWhoWanderAreLost() {
    // Arrange
    UpdateMessage updateMessage = new UpdateMessage();

    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put("template", "Not all who wander are lost");
    objectObjectMap.put("template-path", "Not all who wander are lost");

    // Act
    updateMessage.setContent(objectObjectMap);

    // Assert
    assertEquals("Not all who wander are lost", updateMessage.getTemplate());
    assertEquals("Not all who wander are lost", updateMessage.getTemplatePath());
    assertNull(updateMessage.getContent());
  }

  /**
   * Test {@link UpdateMessage#setContent(Object)}.
   *
   * <ul>
   *   <li>When {@code Content}.
   *   <li>Then {@link UpdateMessage} (default constructor) Content is {@code Content}.
   * </ul>
   *
   * <p>Method under test: {@link UpdateMessage#setContent(Object)}
   */
  @Test
  @DisplayName(
      "Test setContent(Object); when 'Content'; then UpdateMessage (default constructor) Content is 'Content'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UpdateMessage.setContent(Object)"})
  void testSetContent_whenContent_thenUpdateMessageContentIsContent() {
    // Arrange
    UpdateMessage updateMessage = new UpdateMessage();

    // Act
    updateMessage.setContent("Content");

    // Assert
    assertEquals("Content", updateMessage.getContent());
    assertNull(updateMessage.getTemplate());
    assertNull(updateMessage.getTemplatePath());
  }

  /**
   * Test {@link UpdateMessage#setContent(Object)}.
   *
   * <ul>
   *   <li>When one.
   *   <li>Then {@link UpdateMessage} (default constructor) Content is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link UpdateMessage#setContent(Object)}
   */
  @Test
  @DisplayName(
      "Test setContent(Object); when one; then UpdateMessage (default constructor) Content is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UpdateMessage.setContent(Object)"})
  void testSetContent_whenOne_thenUpdateMessageContentIsNull() {
    // Arrange
    UpdateMessage updateMessage = new UpdateMessage();

    // Act
    updateMessage.setContent(1);

    // Assert that nothing has changed
    assertNull(updateMessage.getContent());
    assertNull(updateMessage.getTemplate());
    assertNull(updateMessage.getTemplatePath());
  }
}
