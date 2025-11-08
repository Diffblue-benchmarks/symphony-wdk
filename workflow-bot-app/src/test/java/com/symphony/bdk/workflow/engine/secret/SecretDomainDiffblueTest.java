package com.symphony.bdk.workflow.engine.secret;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

class SecretDomainDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SecretDomain#equals(Object)}
   *   <li>{@link SecretDomain#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    SecretDomain secretDomain = new SecretDomain();
    secretDomain.setCreatedAt(1L);
    secretDomain.setId("42");
    secretDomain.setRef("Ref");
    secretDomain.setSecret("Secret");

    SecretDomain secretDomain2 = new SecretDomain();
    secretDomain2.setCreatedAt(1L);
    secretDomain2.setId("42");
    secretDomain2.setRef("Ref");
    secretDomain2.setSecret("Secret");

    // Act and Assert
    assertEquals(secretDomain, secretDomain2);
    int expectedHashCodeResult = secretDomain.hashCode();
    assertEquals(expectedHashCodeResult, secretDomain2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SecretDomain#equals(Object)}
   *   <li>{@link SecretDomain#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    SecretDomain secretDomain = new SecretDomain();
    secretDomain.setCreatedAt(null);
    secretDomain.setId("42");
    secretDomain.setRef("Ref");
    secretDomain.setSecret("Secret");

    SecretDomain secretDomain2 = new SecretDomain();
    secretDomain2.setCreatedAt(null);
    secretDomain2.setId("42");
    secretDomain2.setRef("Ref");
    secretDomain2.setSecret("Secret");

    // Act and Assert
    assertEquals(secretDomain, secretDomain2);
    int expectedHashCodeResult = secretDomain.hashCode();
    assertEquals(expectedHashCodeResult, secretDomain2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SecretDomain#equals(Object)}
   *   <li>{@link SecretDomain#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    SecretDomain secretDomain = new SecretDomain();
    secretDomain.setCreatedAt(1L);
    secretDomain.setId(null);
    secretDomain.setRef("Ref");
    secretDomain.setSecret("Secret");

    SecretDomain secretDomain2 = new SecretDomain();
    secretDomain2.setCreatedAt(1L);
    secretDomain2.setId(null);
    secretDomain2.setRef("Ref");
    secretDomain2.setSecret("Secret");

    // Act and Assert
    assertEquals(secretDomain, secretDomain2);
    int expectedHashCodeResult = secretDomain.hashCode();
    assertEquals(expectedHashCodeResult, secretDomain2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SecretDomain#equals(Object)}
   *   <li>{@link SecretDomain#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual4() {
    // Arrange
    SecretDomain secretDomain = new SecretDomain();
    secretDomain.setCreatedAt(1L);
    secretDomain.setId("42");
    secretDomain.setRef(null);
    secretDomain.setSecret("Secret");

    SecretDomain secretDomain2 = new SecretDomain();
    secretDomain2.setCreatedAt(1L);
    secretDomain2.setId("42");
    secretDomain2.setRef(null);
    secretDomain2.setSecret("Secret");

    // Act and Assert
    assertEquals(secretDomain, secretDomain2);
    int expectedHashCodeResult = secretDomain.hashCode();
    assertEquals(expectedHashCodeResult, secretDomain2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SecretDomain#equals(Object)}
   *   <li>{@link SecretDomain#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual5() {
    // Arrange
    SecretDomain secretDomain = new SecretDomain();
    secretDomain.setCreatedAt(1L);
    secretDomain.setId("42");
    secretDomain.setRef("Ref");
    secretDomain.setSecret(null);

    SecretDomain secretDomain2 = new SecretDomain();
    secretDomain2.setCreatedAt(1L);
    secretDomain2.setId("42");
    secretDomain2.setRef("Ref");
    secretDomain2.setSecret(null);

    // Act and Assert
    assertEquals(secretDomain, secretDomain2);
    int expectedHashCodeResult = secretDomain.hashCode();
    assertEquals(expectedHashCodeResult, secretDomain2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SecretDomain#equals(Object)}
   *   <li>{@link SecretDomain#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    SecretDomain secretDomain = new SecretDomain();
    secretDomain.setCreatedAt(1L);
    secretDomain.setId("42");
    secretDomain.setRef("Ref");
    secretDomain.setSecret("Secret");

    // Act and Assert
    assertEquals(secretDomain, secretDomain);
    int expectedHashCodeResult = secretDomain.hashCode();
    assertEquals(expectedHashCodeResult, secretDomain.hashCode());
  }

  /**
   * Method under test: {@link SecretDomain#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    SecretDomain secretDomain = new SecretDomain();
    secretDomain.setCreatedAt(3L);
    secretDomain.setId("42");
    secretDomain.setRef("Ref");
    secretDomain.setSecret("Secret");

    SecretDomain secretDomain2 = new SecretDomain();
    secretDomain2.setCreatedAt(1L);
    secretDomain2.setId("42");
    secretDomain2.setRef("Ref");
    secretDomain2.setSecret("Secret");

    // Act and Assert
    assertNotEquals(secretDomain, secretDomain2);
  }

  /**
   * Method under test: {@link SecretDomain#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    SecretDomain secretDomain = new SecretDomain();
    secretDomain.setCreatedAt(null);
    secretDomain.setId("42");
    secretDomain.setRef("Ref");
    secretDomain.setSecret("Secret");

    SecretDomain secretDomain2 = new SecretDomain();
    secretDomain2.setCreatedAt(1L);
    secretDomain2.setId("42");
    secretDomain2.setRef("Ref");
    secretDomain2.setSecret("Secret");

    // Act and Assert
    assertNotEquals(secretDomain, secretDomain2);
  }

  /**
   * Method under test: {@link SecretDomain#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    SecretDomain secretDomain = new SecretDomain();
    secretDomain.setCreatedAt(1L);
    secretDomain.setId("Ref");
    secretDomain.setRef("Ref");
    secretDomain.setSecret("Secret");

    SecretDomain secretDomain2 = new SecretDomain();
    secretDomain2.setCreatedAt(1L);
    secretDomain2.setId("42");
    secretDomain2.setRef("Ref");
    secretDomain2.setSecret("Secret");

    // Act and Assert
    assertNotEquals(secretDomain, secretDomain2);
  }

  /**
   * Method under test: {@link SecretDomain#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    SecretDomain secretDomain = new SecretDomain();
    secretDomain.setCreatedAt(1L);
    secretDomain.setId(null);
    secretDomain.setRef("Ref");
    secretDomain.setSecret("Secret");

    SecretDomain secretDomain2 = new SecretDomain();
    secretDomain2.setCreatedAt(1L);
    secretDomain2.setId("42");
    secretDomain2.setRef("Ref");
    secretDomain2.setSecret("Secret");

    // Act and Assert
    assertNotEquals(secretDomain, secretDomain2);
  }

  /**
   * Method under test: {@link SecretDomain#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    SecretDomain secretDomain = new SecretDomain();
    secretDomain.setCreatedAt(1L);
    secretDomain.setId("42");
    secretDomain.setRef("42");
    secretDomain.setSecret("Secret");

    SecretDomain secretDomain2 = new SecretDomain();
    secretDomain2.setCreatedAt(1L);
    secretDomain2.setId("42");
    secretDomain2.setRef("Ref");
    secretDomain2.setSecret("Secret");

    // Act and Assert
    assertNotEquals(secretDomain, secretDomain2);
  }

  /**
   * Method under test: {@link SecretDomain#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    SecretDomain secretDomain = new SecretDomain();
    secretDomain.setCreatedAt(1L);
    secretDomain.setId("42");
    secretDomain.setRef(null);
    secretDomain.setSecret("Secret");

    SecretDomain secretDomain2 = new SecretDomain();
    secretDomain2.setCreatedAt(1L);
    secretDomain2.setId("42");
    secretDomain2.setRef("Ref");
    secretDomain2.setSecret("Secret");

    // Act and Assert
    assertNotEquals(secretDomain, secretDomain2);
  }

  /**
   * Method under test: {@link SecretDomain#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    SecretDomain secretDomain = new SecretDomain();
    secretDomain.setCreatedAt(1L);
    secretDomain.setId("42");
    secretDomain.setRef("Ref");
    secretDomain.setSecret("42");

    SecretDomain secretDomain2 = new SecretDomain();
    secretDomain2.setCreatedAt(1L);
    secretDomain2.setId("42");
    secretDomain2.setRef("Ref");
    secretDomain2.setSecret("Secret");

    // Act and Assert
    assertNotEquals(secretDomain, secretDomain2);
  }

  /**
   * Method under test: {@link SecretDomain#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    SecretDomain secretDomain = new SecretDomain();
    secretDomain.setCreatedAt(1L);
    secretDomain.setId("42");
    secretDomain.setRef("Ref");
    secretDomain.setSecret(null);

    SecretDomain secretDomain2 = new SecretDomain();
    secretDomain2.setCreatedAt(1L);
    secretDomain2.setId("42");
    secretDomain2.setRef("Ref");
    secretDomain2.setSecret("Secret");

    // Act and Assert
    assertNotEquals(secretDomain, secretDomain2);
  }

  /**
   * Method under test: {@link SecretDomain#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    SecretDomain secretDomain = new SecretDomain();
    secretDomain.setCreatedAt(1L);
    secretDomain.setId("42");
    secretDomain.setRef("Ref");
    secretDomain.setSecret("Secret");

    // Act and Assert
    assertNotEquals(secretDomain, null);
  }

  /**
   * Method under test: {@link SecretDomain#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    SecretDomain secretDomain = new SecretDomain();
    secretDomain.setCreatedAt(1L);
    secretDomain.setId("42");
    secretDomain.setRef("Ref");
    secretDomain.setSecret("Secret");

    // Act and Assert
    assertNotEquals(secretDomain, "Different type to SecretDomain");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SecretDomain#SecretDomain()}
   *   <li>{@link SecretDomain#setCreatedAt(Long)}
   *   <li>{@link SecretDomain#setId(String)}
   *   <li>{@link SecretDomain#setRef(String)}
   *   <li>{@link SecretDomain#setSecret(String)}
   *   <li>{@link SecretDomain#toString()}
   *   <li>{@link SecretDomain#getCreatedAt()}
   *   <li>{@link SecretDomain#getId()}
   *   <li>{@link SecretDomain#getRef()}
   *   <li>{@link SecretDomain#getSecret()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    SecretDomain actualSecretDomain = new SecretDomain();
    actualSecretDomain.setCreatedAt(1L);
    actualSecretDomain.setId("42");
    actualSecretDomain.setRef("Ref");
    actualSecretDomain.setSecret("Secret");
    String actualToStringResult = actualSecretDomain.toString();
    Long actualCreatedAt = actualSecretDomain.getCreatedAt();
    String actualId = actualSecretDomain.getId();
    String actualRef = actualSecretDomain.getRef();

    // Assert that nothing has changed
    assertEquals("42", actualId);
    assertEquals("Ref", actualRef);
    assertEquals("Secret", actualSecretDomain.getSecret());
    assertEquals("SecretDomain(id=42, ref=Ref, secret=Secret, createdAt=1)", actualToStringResult);
    assertEquals(1L, actualCreatedAt.longValue());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SecretDomain#SecretDomain(String, String)}
   *   <li>{@link SecretDomain#setCreatedAt(Long)}
   *   <li>{@link SecretDomain#setId(String)}
   *   <li>{@link SecretDomain#setRef(String)}
   *   <li>{@link SecretDomain#setSecret(String)}
   *   <li>{@link SecretDomain#toString()}
   *   <li>{@link SecretDomain#getCreatedAt()}
   *   <li>{@link SecretDomain#getId()}
   *   <li>{@link SecretDomain#getRef()}
   *   <li>{@link SecretDomain#getSecret()}
   * </ul>
   */
  @Test
  void testGettersAndSetters2() {
    // Arrange and Act
    SecretDomain actualSecretDomain = new SecretDomain("Ref", "Secret");
    actualSecretDomain.setCreatedAt(1L);
    actualSecretDomain.setId("42");
    actualSecretDomain.setRef("Ref");
    actualSecretDomain.setSecret("Secret");
    String actualToStringResult = actualSecretDomain.toString();
    Long actualCreatedAt = actualSecretDomain.getCreatedAt();
    String actualId = actualSecretDomain.getId();
    String actualRef = actualSecretDomain.getRef();

    // Assert that nothing has changed
    assertEquals("42", actualId);
    assertEquals("Ref", actualRef);
    assertEquals("Secret", actualSecretDomain.getSecret());
    assertEquals("SecretDomain(id=42, ref=Ref, secret=Secret, createdAt=1)", actualToStringResult);
    assertEquals(1L, actualCreatedAt.longValue());
  }
}
