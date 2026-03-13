package com.symphony.bdk.workflow.engine.camunda;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CamundaDataSourceConfigurationTest {

  @Mock
  private DataSource dataSource;

  @Test
  void shouldCreateCamundaDataSourceWhenMethodIsCalled() {
    // Arrange
    CamundaDataSourceConfiguration configuration = new CamundaDataSourceConfiguration();

    // Act
    DataSource result = configuration.camundaDataSource();

    // Assert
    assertThat(result).isNotNull();
  }

  @Test
  void shouldCreateCamundaTransactionManagerWhenMethodIsCalled() {
    // Arrange
    CamundaDataSourceConfiguration configuration = new CamundaDataSourceConfiguration();

    // Act
    PlatformTransactionManager result = configuration.camundaTransactionManager(dataSource);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result).isInstanceOf(DataSourceTransactionManager.class);
  }

  @Test
  void shouldCreateTransactionManagerWithCorrectDataSource() {
    // Arrange
    CamundaDataSourceConfiguration configuration = new CamundaDataSourceConfiguration();

    // Act
    PlatformTransactionManager result = configuration.camundaTransactionManager(dataSource);

    // Assert
    assertThat(result).isNotNull();
    DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) result;
    assertThat(transactionManager.getDataSource()).isEqualTo(dataSource);
  }
}
