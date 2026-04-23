package com.symphony.bdk.workflow.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

class WorkflowDataSourceConfigurationTest {

  private final WorkflowDataSourceConfiguration configuration = new WorkflowDataSourceConfiguration();

  @Test
  void shouldReturnJpaTransactionManagerWithDataSource() {
    DataSource dataSource = mock(DataSource.class);

    PlatformTransactionManager transactionManager = configuration.transactionManager(dataSource);

    assertThat(transactionManager).isInstanceOf(JpaTransactionManager.class);
    assertThat(((JpaTransactionManager) transactionManager).getDataSource()).isEqualTo(dataSource);
  }

  @Test
  void shouldThrowIllegalStateExceptionOnConfigStateCheck() {
    assertThatThrownBy(() -> configuration.configStateCheck())
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("wdk.workflows.path");
  }

  @Test
  void shouldReturnDataSourceFromWdkDataSource() {
    DataSource dataSource = configuration.wdkDataSource();

    assertThat(dataSource).isNotNull();
  }
}
