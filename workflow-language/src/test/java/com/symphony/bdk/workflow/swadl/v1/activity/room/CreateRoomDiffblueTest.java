package com.symphony.bdk.workflow.swadl.v1.activity.room;

import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class CreateRoomDiffblueTest {
  /**
   * Test {@link CreateRoom#getUserIdsAsLongs()}.
   *
   * <p>Method under test: {@link CreateRoom#getUserIdsAsLongs()}
   */
  @Test
  @DisplayName("Test getUserIdsAsLongs()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List CreateRoom.getUserIdsAsLongs()"})
  void testGetUserIdsAsLongs() {
    // Arrange, Act and Assert
    assertNull(new CreateRoom().getUserIdsAsLongs());
  }
}
