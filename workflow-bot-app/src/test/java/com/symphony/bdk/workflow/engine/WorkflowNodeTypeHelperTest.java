package com.symphony.bdk.workflow.engine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class WorkflowNodeTypeHelperTest {

  @Test
  void toType_shouldRemoveEventSuffix() {
    // Arrange
    String name = "MESSAGE_SENT_EVENT";

    // Act
    String result = WorkflowNodeTypeHelper.toType(name);

    // Assert
    assertThat(result).isEqualTo("MESSAGE_SENT");
  }

  @Test
  void toType_shouldRemoveGatewaySuffix() {
    // Arrange
    String name = "EXCLUSIVE_GATEWAY";

    // Act
    String result = WorkflowNodeTypeHelper.toType(name);

    // Assert
    assertThat(result).isEqualTo("EXCLUSIVE");
  }

  @Test
  void toType_shouldReturnSameNameWhenNoSuffix() {
    // Arrange
    String name = "SEND_MESSAGE";

    // Act
    String result = WorkflowNodeTypeHelper.toType(name);

    // Assert
    assertThat(result).isEqualTo("SEND_MESSAGE");
  }

  @Test
  void toGroup_shouldReturnEventForEventSuffix() {
    // Arrange
    String name = "MESSAGE_SENT_EVENT";

    // Act
    String result = WorkflowNodeTypeHelper.toGroup(name);

    // Assert
    assertThat(result).isEqualTo("EVENT");
  }

  @Test
  void toGroup_shouldReturnGatewayForGatewaySuffix() {
    // Arrange
    String name = "EXCLUSIVE_GATEWAY";

    // Act
    String result = WorkflowNodeTypeHelper.toGroup(name);

    // Assert
    assertThat(result).isEqualTo("GATEWAY");
  }

  @Test
  void toGroup_shouldReturnActivityForNoSuffix() {
    // Arrange
    String name = "SEND_MESSAGE";

    // Act
    String result = WorkflowNodeTypeHelper.toGroup(name);

    // Assert
    assertThat(result).isEqualTo("ACTIVITY");
  }

  @Test
  void toUpperUnderscore_shouldConvertCamelCaseToUpperUnderscore() {
    // Arrange
    String name = "sendMessage";

    // Act
    String result = WorkflowNodeTypeHelper.toUpperUnderscore(name);

    // Assert
    assertThat(result).isEqualTo("SEND_MESSAGE");
  }

  @Test
  void toUpperUnderscore_shouldHandleAlreadyUpperCase() {
    // Arrange
    String name = "SENDMESSAGE";

    // Act
    String result = WorkflowNodeTypeHelper.toUpperUnderscore(name);

    // Assert
    assertThat(result).isEqualTo("S_E_N_D_M_E_S_S_A_G_E");
  }

  @Test
  void toUpperUnderscore_shouldHandleMultipleCamelCaseWords() {
    // Arrange
    String name = "createConnectionActivity";

    // Act
    String result = WorkflowNodeTypeHelper.toUpperUnderscore(name);

    // Assert
    assertThat(result).isEqualTo("CREATE_CONNECTION_ACTIVITY");
  }

  @Test
  void toUpperUnderscore_shouldHandleSingleCharacter() {
    // Arrange
    String name = "a";

    // Act
    String result = WorkflowNodeTypeHelper.toUpperUnderscore(name);

    // Assert
    assertThat(result).isEqualTo("A");
  }

  @Test
  void toUpperUnderscore_shouldThrowNullPointerExceptionForNullInput() {
    // Act & Assert
    assertThatThrownBy(() -> WorkflowNodeTypeHelper.toUpperUnderscore(null))
        .isInstanceOf(NullPointerException.class);
  }
}
