package com.symphony.bdk.workflow.engine.executor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;

class SecretKeeperDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SecretKeeper.SecretMetadata#equals(Object)}
   *   <li>{@link SecretKeeper.SecretMetadata#hashCode()}
   * </ul>
   */
  @Test
  void testSecretMetadataEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    SecretKeeper.SecretMetadata secretMetadata = new SecretKeeper.SecretMetadata();
    SecretKeeper.SecretMetadata secretMetadata2 = new SecretKeeper.SecretMetadata();

    // Act and Assert
    assertEquals(secretMetadata, secretMetadata2);
    int expectedHashCodeResult = secretMetadata.hashCode();
    assertEquals(expectedHashCodeResult, secretMetadata2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SecretKeeper.SecretMetadata#equals(Object)}
   *   <li>{@link SecretKeeper.SecretMetadata#hashCode()}
   * </ul>
   */
  @Test
  void testSecretMetadataEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    SecretKeeper.SecretMetadata secretMetadata = new SecretKeeper.SecretMetadata(
        "EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY",
        LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    SecretKeeper.SecretMetadata secretMetadata2 = new SecretKeeper.SecretMetadata(
        "EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY",
        LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertEquals(secretMetadata, secretMetadata2);
    int expectedHashCodeResult = secretMetadata.hashCode();
    assertEquals(expectedHashCodeResult, secretMetadata2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SecretKeeper.SecretMetadata#equals(Object)}
   *   <li>{@link SecretKeeper.SecretMetadata#hashCode()}
   * </ul>
   */
  @Test
  void testSecretMetadataEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    SecretKeeper.SecretMetadata secretMetadata = new SecretKeeper.SecretMetadata();

    // Act and Assert
    assertEquals(secretMetadata, secretMetadata);
    int expectedHashCodeResult = secretMetadata.hashCode();
    assertEquals(expectedHashCodeResult, secretMetadata.hashCode());
  }

  /**
   * Method under test: {@link SecretKeeper.SecretMetadata#equals(Object)}
   */
  @Test
  void testSecretMetadataEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    SecretKeeper.SecretMetadata secretMetadata = new SecretKeeper.SecretMetadata(
        "EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY",
        LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertNotEquals(secretMetadata, new SecretKeeper.SecretMetadata());
  }

  /**
   * Method under test: {@link SecretKeeper.SecretMetadata#equals(Object)}
   */
  @Test
  void testSecretMetadataEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    SecretKeeper.SecretMetadata secretMetadata = new SecretKeeper.SecretMetadata();

    // Act and Assert
    assertNotEquals(secretMetadata, new SecretKeeper.SecretMetadata("EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY",
        LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
  }

  /**
   * Method under test: {@link SecretKeeper.SecretMetadata#equals(Object)}
   */
  @Test
  void testSecretMetadataEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    SecretKeeper.SecretMetadata secretMetadata = new SecretKeeper.SecretMetadata();
    secretMetadata.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertNotEquals(secretMetadata, new SecretKeeper.SecretMetadata());
  }

  /**
   * Method under test: {@link SecretKeeper.SecretMetadata#equals(Object)}
   */
  @Test
  void testSecretMetadataEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    SecretKeeper.SecretMetadata secretMetadata = new SecretKeeper.SecretMetadata();

    SecretKeeper.SecretMetadata secretMetadata2 = new SecretKeeper.SecretMetadata();
    secretMetadata2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertNotEquals(secretMetadata, secretMetadata2);
  }

  /**
   * Method under test: {@link SecretKeeper.SecretMetadata#equals(Object)}
   */
  @Test
  void testSecretMetadataEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new SecretKeeper.SecretMetadata(), null);
  }

  /**
   * Method under test: {@link SecretKeeper.SecretMetadata#equals(Object)}
   */
  @Test
  void testSecretMetadataEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new SecretKeeper.SecretMetadata(), "Different type to SecretMetadata");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SecretKeeper.SecretMetadata#SecretMetadata()}
   *   <li>{@link SecretKeeper.SecretMetadata#setCreatedAt(Instant)}
   *   <li>{@link SecretKeeper.SecretMetadata#setSecretKey(String)}
   *   <li>{@link SecretKeeper.SecretMetadata#toString()}
   *   <li>{@link SecretKeeper.SecretMetadata#getCreatedAt()}
   *   <li>{@link SecretKeeper.SecretMetadata#getSecretKey()}
   * </ul>
   */
  @Test
  void testSecretMetadataGettersAndSetters() {
    // Arrange and Act
    SecretKeeper.SecretMetadata actualSecretMetadata = new SecretKeeper.SecretMetadata();
    actualSecretMetadata.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualSecretMetadata.setSecretKey("EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY");
    String actualToStringResult = actualSecretMetadata.toString();
    Instant actualCreatedAt = actualSecretMetadata.getCreatedAt();

    // Assert that nothing has changed
    assertEquals("EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY", actualSecretMetadata.getSecretKey());
    assertEquals(
        "SecretKeeper.SecretMetadata(secretKey=EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY, createdAt=1970-01-01T00"
            + ":00:00Z)",
        actualToStringResult);
    assertSame(actualCreatedAt.EPOCH, actualCreatedAt);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SecretKeeper.SecretMetadata#SecretMetadata(String, Instant)}
   *   <li>{@link SecretKeeper.SecretMetadata#setCreatedAt(Instant)}
   *   <li>{@link SecretKeeper.SecretMetadata#setSecretKey(String)}
   *   <li>{@link SecretKeeper.SecretMetadata#toString()}
   *   <li>{@link SecretKeeper.SecretMetadata#getCreatedAt()}
   *   <li>{@link SecretKeeper.SecretMetadata#getSecretKey()}
   * </ul>
   */
  @Test
  void testSecretMetadataGettersAndSetters2() {
    // Arrange and Act
    SecretKeeper.SecretMetadata actualSecretMetadata = new SecretKeeper.SecretMetadata(
        "EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY",
        LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualSecretMetadata.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualSecretMetadata.setSecretKey("EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY");
    String actualToStringResult = actualSecretMetadata.toString();
    Instant actualCreatedAt = actualSecretMetadata.getCreatedAt();

    // Assert that nothing has changed
    assertEquals("EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY", actualSecretMetadata.getSecretKey());
    assertEquals(
        "SecretKeeper.SecretMetadata(secretKey=EXAMPLEKEYwjalrXUtnFEMI/K7MDENG/bPxRfiCY, createdAt=1970-01-01T00"
            + ":00:00Z)",
        actualToStringResult);
    assertSame(actualCreatedAt.EPOCH, actualCreatedAt);
  }
}
