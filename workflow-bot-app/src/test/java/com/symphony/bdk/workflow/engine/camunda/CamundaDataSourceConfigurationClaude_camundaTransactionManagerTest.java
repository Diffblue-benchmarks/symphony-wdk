package com.symphony.bdk.workflow.engine.camunda;

import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class CamundaDataSourceConfigurationClaude_camundaTransactionManagerTest {

  @Test
  void camundaTransactionManager_withValidDataSource_shouldReturnNonNullTransactionManager() {
    // Given: A CamundaDataSourceConfiguration instance and a valid DataSource
    CamundaDataSourceConfiguration config = new CamundaDataSourceConfiguration();
    DataSource dataSource = DataSourceBuilder.create().build();

    // When: camundaTransactionManager is called with a valid DataSource
    PlatformTransactionManager transactionManager = config.camundaTransactionManager(dataSource);

    // Then: The result should not be null
    assertThat(transactionManager).isNotNull();
  }

  @Test
  void camundaTransactionManager_withValidDataSource_shouldReturnPlatformTransactionManagerInstance() {
    // Given: A CamundaDataSourceConfiguration instance and a valid DataSource
    CamundaDataSourceConfiguration config = new CamundaDataSourceConfiguration();
    DataSource dataSource = DataSourceBuilder.create().build();

    // When: camundaTransactionManager is called
    PlatformTransactionManager transactionManager = config.camundaTransactionManager(dataSource);

    // Then: The result should be an instance of PlatformTransactionManager
    assertThat(transactionManager).isInstanceOf(PlatformTransactionManager.class);
  }

  @Test
  void camundaTransactionManager_withValidDataSource_shouldReturnDataSourceTransactionManagerInstance() {
    // Given: A CamundaDataSourceConfiguration instance and a valid DataSource
    CamundaDataSourceConfiguration config = new CamundaDataSourceConfiguration();
    DataSource dataSource = DataSourceBuilder.create().build();

    // When: camundaTransactionManager is called
    PlatformTransactionManager transactionManager = config.camundaTransactionManager(dataSource);

    // Then: The result should be an instance of DataSourceTransactionManager
    assertThat(transactionManager).isInstanceOf(DataSourceTransactionManager.class);
  }

  @Test
  void camundaTransactionManager_withValidDataSource_shouldConfigureDataSourceCorrectly() {
    // Given: A CamundaDataSourceConfiguration instance and a valid DataSource
    CamundaDataSourceConfiguration config = new CamundaDataSourceConfiguration();
    DataSource dataSource = DataSourceBuilder.create().build();

    // When: camundaTransactionManager is called
    PlatformTransactionManager transactionManager = config.camundaTransactionManager(dataSource);

    // Then: The transaction manager should be configured with the provided DataSource
    assertThat(transactionManager).isNotNull();
    DataSourceTransactionManager dsTransactionManager = (DataSourceTransactionManager) transactionManager;
    assertThat(dsTransactionManager.getDataSource()).isSameAs(dataSource);
  }

  @Test
  void camundaTransactionManager_calledMultipleTimes_shouldReturnDistinctInstances() {
    // Given: A CamundaDataSourceConfiguration instance and a valid DataSource
    CamundaDataSourceConfiguration config = new CamundaDataSourceConfiguration();
    DataSource dataSource = DataSourceBuilder.create().build();

    // When: camundaTransactionManager is called multiple times with the same DataSource
    PlatformTransactionManager transactionManager1 = config.camundaTransactionManager(dataSource);
    PlatformTransactionManager transactionManager2 = config.camundaTransactionManager(dataSource);

    // Then: Each call should return a distinct instance
    assertThat(transactionManager1).isNotNull();
    assertThat(transactionManager2).isNotNull();
    assertThat(transactionManager1).isNotSameAs(transactionManager2);
  }

  @Test
  void camundaTransactionManager_withDifferentDataSources_shouldReturnDistinctTransactionManagers() {
    // Given: A CamundaDataSourceConfiguration instance and two different DataSources
    CamundaDataSourceConfiguration config = new CamundaDataSourceConfiguration();
    DataSource dataSource1 = DataSourceBuilder.create().build();
    DataSource dataSource2 = DataSourceBuilder.create().build();

    // When: camundaTransactionManager is called with different DataSources
    PlatformTransactionManager transactionManager1 = config.camundaTransactionManager(dataSource1);
    PlatformTransactionManager transactionManager2 = config.camundaTransactionManager(dataSource2);

    // Then: Each call should return a distinct transaction manager with the correct DataSource
    assertThat(transactionManager1).isNotNull();
    assertThat(transactionManager2).isNotNull();
    assertThat(transactionManager1).isNotSameAs(transactionManager2);
    assertThat(((DataSourceTransactionManager) transactionManager1).getDataSource()).isSameAs(dataSource1);
    assertThat(((DataSourceTransactionManager) transactionManager2).getDataSource()).isSameAs(dataSource2);
  }

  @Test
  void camundaTransactionManager_withValidDataSource_shouldNotThrowException() {
    // Given: A CamundaDataSourceConfiguration instance and a valid DataSource
    CamundaDataSourceConfiguration config = new CamundaDataSourceConfiguration();
    DataSource dataSource = DataSourceBuilder.create().build();

    // When/Then: camundaTransactionManager should not throw any exception
    assertThatCode(() -> config.camundaTransactionManager(dataSource))
        .doesNotThrowAnyException();
  }

  @Test
  void camundaTransactionManager_fromDifferentConfigInstances_shouldReturnDistinctManagers() {
    // Given: Two CamundaDataSourceConfiguration instances and a DataSource
    CamundaDataSourceConfiguration config1 = new CamundaDataSourceConfiguration();
    CamundaDataSourceConfiguration config2 = new CamundaDataSourceConfiguration();
    DataSource dataSource = DataSourceBuilder.create().build();

    // When: camundaTransactionManager is called on both config instances
    PlatformTransactionManager transactionManager1 = config1.camundaTransactionManager(dataSource);
    PlatformTransactionManager transactionManager2 = config2.camundaTransactionManager(dataSource);

    // Then: Each config instance should return distinct transaction managers
    assertThat(transactionManager1).isNotNull();
    assertThat(transactionManager2).isNotNull();
    assertThat(transactionManager1).isNotSameAs(transactionManager2);
  }

  @Test
  void camundaTransactionManager_shouldConfigureTransactionManagerAsDataSourceType() {
    // Given: A CamundaDataSourceConfiguration instance and a valid DataSource
    CamundaDataSourceConfiguration config = new CamundaDataSourceConfiguration();
    DataSource dataSource = DataSourceBuilder.create().build();

    // When: camundaTransactionManager is called
    PlatformTransactionManager transactionManager = config.camundaTransactionManager(dataSource);

    // Then: The transaction manager should be specifically a DataSourceTransactionManager
    assertThat(transactionManager.getClass()).isEqualTo(DataSourceTransactionManager.class);
  }

  @Test
  void camundaTransactionManager_shouldPreserveDataSourceReference() {
    // Given: A CamundaDataSourceConfiguration instance and a valid DataSource
    CamundaDataSourceConfiguration config = new CamundaDataSourceConfiguration();
    DataSource dataSource = DataSourceBuilder.create().build();

    // When: camundaTransactionManager is called
    PlatformTransactionManager transactionManager = config.camundaTransactionManager(dataSource);

    // Then: The same DataSource reference should be maintained in the transaction manager
    DataSourceTransactionManager dsTransactionManager = (DataSourceTransactionManager) transactionManager;
    assertThat(dsTransactionManager.getDataSource()).isNotNull();
    assertThat(dsTransactionManager.getDataSource()).isSameAs(dataSource);
  }
}
