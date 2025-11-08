package com.symphony.bdk.workflow.api.v1.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;

class SecretViewDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SecretView#equals(Object)}
   *   <li>{@link SecretView#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link SecretView#equals(Object)}
   *   <li>{@link SecretView#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link SecretView#equals(Object)}
   *   <li>{@link SecretView#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    SecretView secretView = new SecretView();

    // Act and Assert
    assertEquals(secretView, secretView);
    int expectedHashCodeResult = secretView.hashCode();
    assertEquals(expectedHashCodeResult, secretView.hashCode());
  }

  /**
   * Method under test: {@link SecretView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    SecretView secretView = new SecretView("Key", "AZAZ".toCharArray());

    // Act and Assert
    assertNotEquals(secretView, new SecretView());
  }

  /**
   * Method under test: {@link SecretView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    SecretView secretView = new SecretView();

    // Act and Assert
    assertNotEquals(secretView, new SecretView("Key", "AZAZ".toCharArray()));
  }

  /**
   * Method under test: {@link SecretView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    SecretView secretView = new SecretView();
    secretView.setSecret("\u0001\u0003\u0001\u0003".toCharArray());

    // Act and Assert
    assertNotEquals(secretView, new SecretView());
  }

  /**
   * Method under test: {@link SecretView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new SecretView(), null);
  }

  /**
   * Method under test: {@link SecretView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new SecretView(), "Different type to SecretView");
  }

  /**
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
  void testGettersAndSetters() {
    // Arrange and Act
    SecretView actualSecretView = new SecretView();
    actualSecretView.setKey("Key");
    char[] secret = "AZAZ".toCharArray();
    actualSecretView.setSecret(secret);
    String actualToStringResult = actualSecretView.toString();
    String actualKey = actualSecretView.getKey();

    // Assert that nothing has changed
    assertEquals("Key", actualKey);
    assertEquals("SecretView(key=Key, secret=[A, Z, A, Z])", actualToStringResult);
    assertSame(secret, actualSecretView.getSecret());
  }

  /**
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
  void testGettersAndSetters2() {
    // Arrange and Act
    SecretView actualSecretView = new SecretView("Key", "AZAZ".toCharArray());
    actualSecretView.setKey("Key");
    char[] secret = "AZAZ".toCharArray();
    actualSecretView.setSecret(secret);
    String actualToStringResult = actualSecretView.toString();
    String actualKey = actualSecretView.getKey();

    // Assert that nothing has changed
    assertEquals("Key", actualKey);
    assertEquals("SecretView(key=Key, secret=[A, Z, A, Z])", actualToStringResult);
    assertSame(secret, actualSecretView.getSecret());
  }
}
