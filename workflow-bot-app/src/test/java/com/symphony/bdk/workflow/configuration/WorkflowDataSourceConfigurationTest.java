package com.symphony.bdk.workflow.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class WorkflowDataSourceConfigurationTest {

  private WorkflowDataSourceConfiguration configuration;

  @BeforeEach
  void setUp() {
    configuration = new WorkflowDataSourceConfiguration();
  }

  @Test
  void wdkDataSourceShouldReturnDataSource() {
    DataSource dataSource = configuration.wdkDataSource();

    assertNotNull(dataSource);
  }

  @Test
  void transactionManagerShouldReturnJpaTransactionManager() {
    DataSource dataSource = mock(DataSource.class);

    PlatformTransactionManager transactionManager = configuration.transactionManager(dataSource);

    assertNotNull(transactionManager);
    assertTrue(transactionManager instanceof JpaTransactionManager);
    assertEquals(dataSource, ((JpaTransactionManager) transactionManager).getDataSource());
  }

  @Test
  void configStateCheckShouldThrowIllegalStateException() {
    IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
      configuration.configStateCheck();
    });

    assertTrue(exception.getMessage().contains("Workflow folder watcher must be disabled"));
    assertTrue(exception.getMessage().contains("wdk.workflows.path"));
  }
}
