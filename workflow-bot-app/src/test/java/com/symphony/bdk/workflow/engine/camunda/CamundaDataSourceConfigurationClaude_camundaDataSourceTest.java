package com.symphony.bdk.workflow.engine.camunda;

import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class CamundaDataSourceConfigurationClaude_camundaDataSourceTest {

  @Test
  void camundaDataSource_shouldReturnNonNullDataSource() {
    // Given: A CamundaDataSourceConfiguration instance
    CamundaDataSourceConfiguration config = new CamundaDataSourceConfiguration();

    // When: camundaDataSource is called
    DataSource dataSource = config.camundaDataSource();

    // Then: The result should not be null
    assertThat(dataSource).isNotNull();
  }

  @Test
  void camundaDataSource_shouldReturnDataSourceInstance() {
    // Given: A CamundaDataSourceConfiguration instance
    CamundaDataSourceConfiguration config = new CamundaDataSourceConfiguration();

    // When: camundaDataSource is called
    DataSource dataSource = config.camundaDataSource();

    // Then: The result should be an instance of DataSource
    assertThat(dataSource).isInstanceOf(DataSource.class);
  }

  @Test
  void camundaDataSource_calledMultipleTimes_shouldReturnDistinctInstances() {
    // Given: A CamundaDataSourceConfiguration instance
    CamundaDataSourceConfiguration config = new CamundaDataSourceConfiguration();

    // When: camundaDataSource is called multiple times
    DataSource dataSource1 = config.camundaDataSource();
    DataSource dataSource2 = config.camundaDataSource();

    // Then: Each call should return a distinct instance
    assertThat(dataSource1).isNotNull();
    assertThat(dataSource2).isNotNull();
    assertThat(dataSource1).isNotSameAs(dataSource2);
  }

  @Test
  void camundaDataSource_shouldNotThrowException() {
    // Given: A CamundaDataSourceConfiguration instance
    CamundaDataSourceConfiguration config = new CamundaDataSourceConfiguration();

    // When/Then: camundaDataSource should not throw any exception
    assertThatCode(() -> config.camundaDataSource())
        .doesNotThrowAnyException();
  }

  @Test
  void camundaDataSource_calledFromDifferentInstances_shouldReturnDistinctDataSources() {
    // Given: Two CamundaDataSourceConfiguration instances
    CamundaDataSourceConfiguration config1 = new CamundaDataSourceConfiguration();
    CamundaDataSourceConfiguration config2 = new CamundaDataSourceConfiguration();

    // When: camundaDataSource is called on both instances
    DataSource dataSource1 = config1.camundaDataSource();
    DataSource dataSource2 = config2.camundaDataSource();

    // Then: Each instance should return distinct DataSource instances
    assertThat(dataSource1).isNotNull();
    assertThat(dataSource2).isNotNull();
    assertThat(dataSource1).isNotSameAs(dataSource2);
  }
}
