package com.symphony.bdk.workflow.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WorkflowDataSourceConfigurationClaudeTest {

  // Tests for <init>() constructor

  @Test
  void constructor_shouldCreateNonNullInstance() {
    // When: Creating a new instance of WorkflowDataSourceConfiguration
    WorkflowDataSourceConfiguration config = new WorkflowDataSourceConfiguration();

    // Then: The instance should not be null
    assertThat(config).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance of WorkflowDataSourceConfiguration
    WorkflowDataSourceConfiguration config = new WorkflowDataSourceConfiguration();

    // Then: The instance should be of type WorkflowDataSourceConfiguration
    assertThat(config).isInstanceOf(WorkflowDataSourceConfiguration.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating two instances of WorkflowDataSourceConfiguration
    WorkflowDataSourceConfiguration config1 = new WorkflowDataSourceConfiguration();
    WorkflowDataSourceConfiguration config2 = new WorkflowDataSourceConfiguration();

    // Then: Each call should create a distinct instance
    assertThat(config1).isNotSameAs(config2);
  }

  @Test
  void constructor_shouldAllowMultipleInstantiations() {
    // When: Creating multiple instances of WorkflowDataSourceConfiguration
    WorkflowDataSourceConfiguration config1 = new WorkflowDataSourceConfiguration();
    WorkflowDataSourceConfiguration config2 = new WorkflowDataSourceConfiguration();
    WorkflowDataSourceConfiguration config3 = new WorkflowDataSourceConfiguration();

    // Then: All instances should be non-null and distinct
    assertThat(config1).isNotNull();
    assertThat(config2).isNotNull();
    assertThat(config3).isNotNull();
    assertThat(config1).isNotSameAs(config2);
    assertThat(config2).isNotSameAs(config3);
    assertThat(config1).isNotSameAs(config3);
  }

  @Test
  void constructor_shouldCreateInstanceWithNoException() {
    // When/Then: Creating a new instance should not throw any exception
    assertThatCode(() -> new WorkflowDataSourceConfiguration())
        .doesNotThrowAnyException();
  }

  // Tests for wdkDataSource() method

  @Test
  void wdkDataSource_shouldReturnNonNullDataSource() {
    // Given: A WorkflowDataSourceConfiguration instance
    WorkflowDataSourceConfiguration config = new WorkflowDataSourceConfiguration();

    // When: wdkDataSource is called
    DataSource dataSource = config.wdkDataSource();

    // Then: The result should not be null
    assertThat(dataSource).isNotNull();
  }

  @Test
  void wdkDataSource_shouldReturnDataSourceInstance() {
    // Given: A WorkflowDataSourceConfiguration instance
    WorkflowDataSourceConfiguration config = new WorkflowDataSourceConfiguration();

    // When: wdkDataSource is called
    DataSource dataSource = config.wdkDataSource();

    // Then: The result should be an instance of DataSource
    assertThat(dataSource).isInstanceOf(DataSource.class);
  }

  @Test
  void wdkDataSource_calledMultipleTimes_shouldReturnDistinctInstances() {
    // Given: A WorkflowDataSourceConfiguration instance
    WorkflowDataSourceConfiguration config = new WorkflowDataSourceConfiguration();

    // When: wdkDataSource is called multiple times
    DataSource dataSource1 = config.wdkDataSource();
    DataSource dataSource2 = config.wdkDataSource();

    // Then: Each call should return a distinct instance
    assertThat(dataSource1).isNotNull();
    assertThat(dataSource2).isNotNull();
    assertThat(dataSource1).isNotSameAs(dataSource2);
  }

  @Test
  void wdkDataSource_shouldNotThrowException() {
    // Given: A WorkflowDataSourceConfiguration instance
    WorkflowDataSourceConfiguration config = new WorkflowDataSourceConfiguration();

    // When/Then: wdkDataSource should not throw any exception
    assertThatCode(() -> config.wdkDataSource())
        .doesNotThrowAnyException();
  }

  @Test
  void wdkDataSource_calledFromDifferentInstances_shouldReturnDistinctDataSources() {
    // Given: Two WorkflowDataSourceConfiguration instances
    WorkflowDataSourceConfiguration config1 = new WorkflowDataSourceConfiguration();
    WorkflowDataSourceConfiguration config2 = new WorkflowDataSourceConfiguration();

    // When: wdkDataSource is called on both instances
    DataSource dataSource1 = config1.wdkDataSource();
    DataSource dataSource2 = config2.wdkDataSource();

    // Then: Each instance should return distinct DataSource instances
    assertThat(dataSource1).isNotNull();
    assertThat(dataSource2).isNotNull();
    assertThat(dataSource1).isNotSameAs(dataSource2);
  }

  // Tests for transactionManager(DataSource) method

  @Test
  void transactionManager_withValidDataSource_shouldReturnNonNullTransactionManager() {
    // Given: A WorkflowDataSourceConfiguration instance and a valid DataSource
    WorkflowDataSourceConfiguration config = new WorkflowDataSourceConfiguration();
    DataSource dataSource = DataSourceBuilder.create().build();

    // When: transactionManager is called with a valid DataSource
    PlatformTransactionManager transactionManager = config.transactionManager(dataSource);

    // Then: The result should not be null
    assertThat(transactionManager).isNotNull();
  }

  @Test
  void transactionManager_withValidDataSource_shouldReturnPlatformTransactionManagerInstance() {
    // Given: A WorkflowDataSourceConfiguration instance and a valid DataSource
    WorkflowDataSourceConfiguration config = new WorkflowDataSourceConfiguration();
    DataSource dataSource = DataSourceBuilder.create().build();

    // When: transactionManager is called
    PlatformTransactionManager transactionManager = config.transactionManager(dataSource);

    // Then: The result should be an instance of PlatformTransactionManager
    assertThat(transactionManager).isInstanceOf(PlatformTransactionManager.class);
  }

  @Test
  void transactionManager_withValidDataSource_shouldReturnJpaTransactionManagerInstance() {
    // Given: A WorkflowDataSourceConfiguration instance and a valid DataSource
    WorkflowDataSourceConfiguration config = new WorkflowDataSourceConfiguration();
    DataSource dataSource = DataSourceBuilder.create().build();

    // When: transactionManager is called
    PlatformTransactionManager transactionManager = config.transactionManager(dataSource);

    // Then: The result should be an instance of JpaTransactionManager
    assertThat(transactionManager).isInstanceOf(JpaTransactionManager.class);
  }

  @Test
  void transactionManager_withValidDataSource_shouldConfigureDataSourceCorrectly() {
    // Given: A WorkflowDataSourceConfiguration instance and a valid DataSource
    WorkflowDataSourceConfiguration config = new WorkflowDataSourceConfiguration();
    DataSource dataSource = DataSourceBuilder.create().build();

    // When: transactionManager is called
    PlatformTransactionManager transactionManager = config.transactionManager(dataSource);

    // Then: The transaction manager should be configured with the provided DataSource
    assertThat(transactionManager).isNotNull();
    JpaTransactionManager jpaTransactionManager = (JpaTransactionManager) transactionManager;
    assertThat(jpaTransactionManager.getDataSource()).isSameAs(dataSource);
  }

  @Test
  void transactionManager_calledMultipleTimes_shouldReturnDistinctInstances() {
    // Given: A WorkflowDataSourceConfiguration instance and a valid DataSource
    WorkflowDataSourceConfiguration config = new WorkflowDataSourceConfiguration();
    DataSource dataSource = DataSourceBuilder.create().build();

    // When: transactionManager is called multiple times with the same DataSource
    PlatformTransactionManager transactionManager1 = config.transactionManager(dataSource);
    PlatformTransactionManager transactionManager2 = config.transactionManager(dataSource);

    // Then: Each call should return a distinct instance
    assertThat(transactionManager1).isNotNull();
    assertThat(transactionManager2).isNotNull();
    assertThat(transactionManager1).isNotSameAs(transactionManager2);
  }

  @Test
  void transactionManager_withDifferentDataSources_shouldReturnDistinctTransactionManagers() {
    // Given: A WorkflowDataSourceConfiguration instance and two different DataSources
    WorkflowDataSourceConfiguration config = new WorkflowDataSourceConfiguration();
    DataSource dataSource1 = DataSourceBuilder.create().build();
    DataSource dataSource2 = DataSourceBuilder.create().build();

    // When: transactionManager is called with different DataSources
    PlatformTransactionManager transactionManager1 = config.transactionManager(dataSource1);
    PlatformTransactionManager transactionManager2 = config.transactionManager(dataSource2);

    // Then: Each call should return a distinct transaction manager with the correct DataSource
    assertThat(transactionManager1).isNotNull();
    assertThat(transactionManager2).isNotNull();
    assertThat(transactionManager1).isNotSameAs(transactionManager2);
    assertThat(((JpaTransactionManager) transactionManager1).getDataSource()).isSameAs(dataSource1);
    assertThat(((JpaTransactionManager) transactionManager2).getDataSource()).isSameAs(dataSource2);
  }

  @Test
  void transactionManager_withValidDataSource_shouldNotThrowException() {
    // Given: A WorkflowDataSourceConfiguration instance and a valid DataSource
    WorkflowDataSourceConfiguration config = new WorkflowDataSourceConfiguration();
    DataSource dataSource = DataSourceBuilder.create().build();

    // When/Then: transactionManager should not throw any exception
    assertThatCode(() -> config.transactionManager(dataSource))
        .doesNotThrowAnyException();
  }

  @Test
  void transactionManager_fromDifferentConfigInstances_shouldReturnDistinctManagers() {
    // Given: Two WorkflowDataSourceConfiguration instances and a DataSource
    WorkflowDataSourceConfiguration config1 = new WorkflowDataSourceConfiguration();
    WorkflowDataSourceConfiguration config2 = new WorkflowDataSourceConfiguration();
    DataSource dataSource = DataSourceBuilder.create().build();

    // When: transactionManager is called on both config instances
    PlatformTransactionManager transactionManager1 = config1.transactionManager(dataSource);
    PlatformTransactionManager transactionManager2 = config2.transactionManager(dataSource);

    // Then: Each config instance should return distinct transaction managers
    assertThat(transactionManager1).isNotNull();
    assertThat(transactionManager2).isNotNull();
    assertThat(transactionManager1).isNotSameAs(transactionManager2);
  }

  @Test
  void transactionManager_shouldConfigureTransactionManagerAsJpaType() {
    // Given: A WorkflowDataSourceConfiguration instance and a valid DataSource
    WorkflowDataSourceConfiguration config = new WorkflowDataSourceConfiguration();
    DataSource dataSource = DataSourceBuilder.create().build();

    // When: transactionManager is called
    PlatformTransactionManager transactionManager = config.transactionManager(dataSource);

    // Then: The transaction manager should be specifically a JpaTransactionManager
    assertThat(transactionManager.getClass()).isEqualTo(JpaTransactionManager.class);
  }

  // Tests for configStateCheck() method

  @Test
  void configStateCheck_shouldThrowIllegalStateException() {
    // Given: A WorkflowDataSourceConfiguration instance
    WorkflowDataSourceConfiguration config = new WorkflowDataSourceConfiguration();

    // When/Then: configStateCheck should throw IllegalStateException
    assertThatThrownBy(() -> config.configStateCheck())
        .isInstanceOf(IllegalStateException.class);
  }

  @Test
  void configStateCheck_shouldThrowExceptionWithCorrectMessage() {
    // Given: A WorkflowDataSourceConfiguration instance
    WorkflowDataSourceConfiguration config = new WorkflowDataSourceConfiguration();

    // When/Then: configStateCheck should throw IllegalStateException with specific message
    assertThatThrownBy(() -> config.configStateCheck())
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("Workflow folder watcher must be disabled")
        .hasMessageContaining("workflow management API")
        .hasMessageContaining("wdk.workflows.path");
  }

  @Test
  void configStateCheck_shouldAlwaysThrowException() {
    // Given: A WorkflowDataSourceConfiguration instance
    WorkflowDataSourceConfiguration config = new WorkflowDataSourceConfiguration();

    // When/Then: configStateCheck should throw exception every time it's called
    assertThatThrownBy(() -> config.configStateCheck())
        .isInstanceOf(IllegalStateException.class);
    assertThatThrownBy(() -> config.configStateCheck())
        .isInstanceOf(IllegalStateException.class);
    assertThatThrownBy(() -> config.configStateCheck())
        .isInstanceOf(IllegalStateException.class);
  }

  @Test
  void configStateCheck_fromDifferentInstances_shouldThrowException() {
    // Given: Two WorkflowDataSourceConfiguration instances
    WorkflowDataSourceConfiguration config1 = new WorkflowDataSourceConfiguration();
    WorkflowDataSourceConfiguration config2 = new WorkflowDataSourceConfiguration();

    // When/Then: configStateCheck should throw exception from both instances
    assertThatThrownBy(() -> config1.configStateCheck())
        .isInstanceOf(IllegalStateException.class);
    assertThatThrownBy(() -> config2.configStateCheck())
        .isInstanceOf(IllegalStateException.class);
  }

  @Test
  void configStateCheck_shouldNeverReturnNormally() {
    // Given: A WorkflowDataSourceConfiguration instance
    WorkflowDataSourceConfiguration config = new WorkflowDataSourceConfiguration();

    // When/Then: configStateCheck should always throw an exception and never return a value
    boolean exceptionThrown = false;
    try {
      config.configStateCheck();
    } catch (IllegalStateException e) {
      exceptionThrown = true;
      assertThat(e.getMessage()).contains("Workflow folder watcher must be disabled");
    }
    assertThat(exceptionThrown).isTrue();
  }

  @Test
  void configStateCheck_shouldThrowExceptionWithCompleteErrorMessage() {
    // Given: A WorkflowDataSourceConfiguration instance
    WorkflowDataSourceConfiguration config = new WorkflowDataSourceConfiguration();

    // When/Then: configStateCheck should throw exception with the complete error message
    assertThatThrownBy(() -> config.configStateCheck())
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("Workflow folder watcher must be disabled while using workflow management API. "
            + "Please remove 'wdk.workflows.path' property from the configuration file.");
  }
}
