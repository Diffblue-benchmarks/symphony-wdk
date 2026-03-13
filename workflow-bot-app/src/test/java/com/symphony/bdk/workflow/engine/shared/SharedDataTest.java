package com.symphony.bdk.workflow.engine.shared;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SharedDataTest {

    @Test
    void namespaceWithValidValueShouldSetNamespaceAndReturnThis() {
        // Arrange
        SharedData sharedData = new SharedData();
        String expectedNamespace = "testNamespace";

        // Act
        SharedData result = sharedData.namespace(expectedNamespace);

        // Assert
        assertThat(result).isSameAs(sharedData);
        assertThat(sharedData.getNamespace()).isEqualTo(expectedNamespace);
    }

    @Test
    void namespaceWithNullShouldSetNamespaceToNullAndReturnThis() {
        // Arrange
        SharedData sharedData = new SharedData();

        // Act
        SharedData result = sharedData.namespace(null);

        // Assert
        assertThat(result).isSameAs(sharedData);
        assertThat(sharedData.getNamespace()).isNull();
    }

    @Test
    void namespaceWithEmptyStringShouldSetNamespaceToEmptyStringAndReturnThis() {
        // Arrange
        SharedData sharedData = new SharedData();
        String expectedNamespace = "";

        // Act
        SharedData result = sharedData.namespace(expectedNamespace);

        // Assert
        assertThat(result).isSameAs(sharedData);
        assertThat(sharedData.getNamespace()).isEqualTo(expectedNamespace);
    }

    @Test
    void namespaceShouldAllowMethodChaining() {
        // Arrange
        SharedData sharedData = new SharedData();
        String expectedNamespace = "chainedNamespace";

        // Act
        SharedData result = sharedData.namespace(expectedNamespace).namespace("anotherNamespace");

        // Assert
        assertThat(result).isSameAs(sharedData);
        assertThat(sharedData.getNamespace()).isEqualTo("anotherNamespace");
    }
}
