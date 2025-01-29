package com.symphony.bdk.workflow.api.v1.dto;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SecretViewDiffblueTest {
  /**
   * Test {@link SecretView#equals(Object)}, and {@link SecretView#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link SecretView#equals(Object)}
   *   <li>{@link SecretView#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    SecretView secretView = new SecretView();
    SecretView secretView2 = new SecretView();

    // Act and Assert
    assertEquals(secretView, secretView2);
    int expectedHashCodeResult = secretView.hashCode();
    assertEquals(expectedHashCodeResult, secretView2.hashCode());
  }

  /**
   * Test {@link SecretView#equals(Object)}, and {@link SecretView#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link SecretView#equals(Object)}
   *   <li>{@link SecretView#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    SecretView secretView = new SecretView("Key", "AZAZ".toCharArray());
    SecretView secretView2 = new SecretView("Key", "AZAZ".toCharArray());

    // Act and Assert
    assertEquals(secretView, secretView2);
    int expectedHashCodeResult = secretView.hashCode();
    assertEquals(expectedHashCodeResult, secretView2.hashCode());
  }

  /**
   * Test {@link SecretView#equals(Object)}, and {@link SecretView#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link SecretView#equals(Object)}
   *   <li>{@link SecretView#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    SecretView secretView = new SecretView();

    // Act and Assert
    assertEquals(secretView, secretView);
    int expectedHashCodeResult = secretView.hashCode();
    assertEquals(expectedHashCodeResult, secretView.hashCode());
  }

  /**
   * Test {@link SecretView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SecretView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    SecretView secretView = new SecretView("Key", "AZAZ".toCharArray());

    // Act and Assert
    assertNotEquals(secretView, new SecretView());
  }

  /**
   * Test {@link SecretView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SecretView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    SecretView secretView = new SecretView();

    // Act and Assert
    assertNotEquals(secretView, new SecretView("Key", "AZAZ".toCharArray()));
  }

  /**
   * Test {@link SecretView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SecretView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    SecretView secretView = new SecretView();
    secretView.setSecret("\u0001\u0003\u0001\u0003".toCharArray());

    // Act and Assert
    assertNotEquals(secretView, new SecretView());
  }

  /**
   * Test {@link SecretView#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SecretView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new SecretView(), null);
  }

  /**
   * Test {@link SecretView#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SecretView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new SecretView(), "Different type to SecretView");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link SecretView#SecretView()}
   *   <li>{@link SecretView#setKey(String)}
   *   <li>{@link SecretView#setSecret(char[])}
   *   <li>{@link SecretView#toString()}
   *   <li>{@link SecretView#getKey()}
   *   <li>{@link SecretView#getSecret()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    SecretView actualSecretView = new SecretView();
    actualSecretView.setKey("Key");
    char[] secret = "AZAZ".toCharArray();
    actualSecretView.setSecret(secret);
    String actualToStringResult = actualSecretView.toString();
    String actualKey = actualSecretView.getKey();
    char[] actualSecret = actualSecretView.getSecret();

    // Assert
    assertEquals("Key", actualKey);
    assertEquals("SecretView(key=Key, secret=[A, Z, A, Z])", actualToStringResult);
    assertSame(secret, actualSecret);
    assertArrayEquals("AZAZ".toCharArray(), actualSecret);
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@code Key}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link SecretView#SecretView(String, char[])}
   *   <li>{@link SecretView#setKey(String)}
   *   <li>{@link SecretView#setSecret(char[])}
   *   <li>{@link SecretView#toString()}
   *   <li>{@link SecretView#getKey()}
   *   <li>{@link SecretView#getSecret()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters; when 'Key'")
  void testGettersAndSetters_whenKey() {
    // Arrange and Act
    SecretView actualSecretView = new SecretView("Key", "AZAZ".toCharArray());
    actualSecretView.setKey("Key");
    char[] secret = "AZAZ".toCharArray();
    actualSecretView.setSecret(secret);
    String actualToStringResult = actualSecretView.toString();
    String actualKey = actualSecretView.getKey();
    char[] actualSecret = actualSecretView.getSecret();

    // Assert
    assertEquals("Key", actualKey);
    assertEquals("SecretView(key=Key, secret=[A, Z, A, Z])", actualToStringResult);
    assertSame(secret, actualSecret);
    assertArrayEquals("AZAZ".toCharArray(), actualSecret);
  }
}
