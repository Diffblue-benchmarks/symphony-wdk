package com.symphony.bdk.workflow.configuration;

import com.symphony.bdk.ext.group.SymphonyGroupBdkExtension;
import com.symphony.bdk.workflow.engine.ResourceProvider;
import com.symphony.bdk.workflow.engine.secret.SecretCryptVault;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WorkflowBotConfigurationTest {

    private WorkflowBotConfiguration configuration;

    @BeforeEach
    void setUp() {
        configuration = new WorkflowBotConfiguration();
    }

    @Test
    void workflowResourcesProviderShouldReturnProviderWithConfiguredPath() {
        // Arrange
        String expectedPath = "./test-workflows";
        ReflectionTestUtils.setField(configuration, "workflowsFolderPath", expectedPath);

        // Act
        ResourceProvider provider = configuration.workflowResourcesProvider();

        // Assert
        assertNotNull(provider);
        assertTrue(provider instanceof WorkflowResourcesProvider);
    }

    @Test
    void groupExtensionShouldReturnSymphonyGroupBdkExtension() {
        // Arrange
        // No setup needed

        // Act
        SymphonyGroupBdkExtension extension = configuration.groupExtension();

        // Assert
        assertNotNull(extension);
        assertTrue(extension instanceof SymphonyGroupBdkExtension);
    }

    @Test
    void cryptJpaVaultShouldCreateVaultWithValidPassphrase() throws InvalidKeySpecException, NoSuchAlgorithmException {
        // Arrange
        String validPassphrase = "validPassphrase123456";

        // Act
        SecretCryptVault vault = configuration.cryptJpaVault(validPassphrase);

        // Assert
        assertNotNull(vault);
    }

    @Test
    void cryptJpaVaultShouldEncryptAndDecryptSuccessfully() throws InvalidKeySpecException, NoSuchAlgorithmException {
        // Arrange
        String validPassphrase = "validPassphrase123456";
        byte[] plaintext = "test data".getBytes(StandardCharsets.UTF_8);

        // Act
        SecretCryptVault vault = configuration.cryptJpaVault(validPassphrase);
        byte[] encrypted = vault.encrypt(plaintext);
        byte[] decrypted = vault.decrypt(encrypted);

        // Assert
        assertNotNull(encrypted);
        assertNotNull(decrypted);
        assertArrayEquals(plaintext, decrypted);
    }

    @Test
    void cryptJpaVaultShouldThrowExceptionWhenPassphraseTooShort() {
        // Arrange
        String shortPassphrase = "short";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            configuration.cryptJpaVault(shortPassphrase);
        });

        assertEquals("The encryption passphrase length must have at least 16 characters.", exception.getMessage());
    }

    @Test
    void cryptJpaVaultShouldThrowExceptionWhenPassphraseExactly15Characters() {
        // Arrange
        String passphrase15 = "123456789012345";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            configuration.cryptJpaVault(passphrase15);
        });

        assertEquals("The encryption passphrase length must have at least 16 characters.", exception.getMessage());
    }

    @Test
    void cryptJpaVaultShouldSucceedWhenPassphraseExactly16Characters() throws InvalidKeySpecException, NoSuchAlgorithmException {
        // Arrange
        String passphrase16 = "1234567890123456";

        // Act
        SecretCryptVault vault = configuration.cryptJpaVault(passphrase16);

        // Assert
        assertNotNull(vault);
    }
}
