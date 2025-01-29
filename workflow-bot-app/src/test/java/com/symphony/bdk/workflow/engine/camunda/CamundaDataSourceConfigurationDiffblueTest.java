package com.symphony.bdk.workflow.engine.camunda;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionExecutionListener;

class CamundaDataSourceConfigurationDiffblueTest {
  /**
   * Test {@link CamundaDataSourceConfiguration#camundaDataSource()}.
   * <p>
   * Method under test: {@link CamundaDataSourceConfiguration#camundaDataSource()}
   */
  @Test
  @DisplayName("Test camundaDataSource()")
  void testCamundaDataSource() throws SQLException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    DataSource actualCamundaDataSourceResult = (new CamundaDataSourceConfiguration()).camundaDataSource();

    // Assert
    assertTrue(actualCamundaDataSourceResult instanceof HikariDataSource);
    assertNull(((HikariDataSource) actualCamundaDataSourceResult).getHikariPoolMXBean());
    assertNull(((HikariDataSource) actualCamundaDataSourceResult).getMetricsTrackerFactory());
    assertNull(actualCamundaDataSourceResult.getLogWriter());
    assertNull(((HikariDataSource) actualCamundaDataSourceResult).getHealthCheckRegistry());
    assertNull(((HikariDataSource) actualCamundaDataSourceResult).getMetricRegistry());
    assertNull(((HikariDataSource) actualCamundaDataSourceResult).getCatalog());
    assertNull(((HikariDataSource) actualCamundaDataSourceResult).getConnectionInitSql());
    assertNull(((HikariDataSource) actualCamundaDataSourceResult).getConnectionTestQuery());
    assertNull(((HikariDataSource) actualCamundaDataSourceResult).getDataSourceClassName());
    assertNull(((HikariDataSource) actualCamundaDataSourceResult).getDataSourceJNDI());
    assertNull(((HikariDataSource) actualCamundaDataSourceResult).getDriverClassName());
    assertNull(((HikariDataSource) actualCamundaDataSourceResult).getExceptionOverrideClassName());
    assertNull(((HikariDataSource) actualCamundaDataSourceResult).getJdbcUrl());
    assertNull(((HikariDataSource) actualCamundaDataSourceResult).getPassword());
    assertNull(((HikariDataSource) actualCamundaDataSourceResult).getPoolName());
    assertNull(((HikariDataSource) actualCamundaDataSourceResult).getSchema());
    assertNull(((HikariDataSource) actualCamundaDataSourceResult).getTransactionIsolation());
    assertNull(((HikariDataSource) actualCamundaDataSourceResult).getUsername());
    assertNull(((HikariDataSource) actualCamundaDataSourceResult).getScheduledExecutor());
    assertNull(((HikariDataSource) actualCamundaDataSourceResult).getThreadFactory());
    assertNull(((HikariDataSource) actualCamundaDataSourceResult).getDataSource());
    assertEquals(-1, ((HikariDataSource) actualCamundaDataSourceResult).getMaximumPoolSize());
    assertEquals(-1, ((HikariDataSource) actualCamundaDataSourceResult).getMinimumIdle());
    assertEquals(0, actualCamundaDataSourceResult.getLoginTimeout());
    assertEquals(0L, ((HikariDataSource) actualCamundaDataSourceResult).getKeepaliveTime());
    assertEquals(0L, ((HikariDataSource) actualCamundaDataSourceResult).getLeakDetectionThreshold());
    assertEquals(1800000L, ((HikariDataSource) actualCamundaDataSourceResult).getMaxLifetime());
    assertEquals(1L, ((HikariDataSource) actualCamundaDataSourceResult).getInitializationFailTimeout());
    assertEquals(30000L, ((HikariDataSource) actualCamundaDataSourceResult).getConnectionTimeout());
    assertEquals(5000L, ((HikariDataSource) actualCamundaDataSourceResult).getValidationTimeout());
    assertEquals(600000L, ((HikariDataSource) actualCamundaDataSourceResult).getIdleTimeout());
    assertFalse(((HikariDataSource) actualCamundaDataSourceResult).isAllowPoolSuspension());
    assertFalse(((HikariDataSource) actualCamundaDataSourceResult).isIsolateInternalQueries());
    assertFalse(((HikariDataSource) actualCamundaDataSourceResult).isReadOnly());
    assertFalse(((HikariDataSource) actualCamundaDataSourceResult).isRegisterMbeans());
    assertFalse(((HikariDataSource) actualCamundaDataSourceResult).isClosed());
    assertFalse(((HikariDataSource) actualCamundaDataSourceResult).isRunning());
    assertTrue(((HikariDataSource) actualCamundaDataSourceResult).isAutoCommit());
    Properties dataSourceProperties = ((HikariDataSource) actualCamundaDataSourceResult).getDataSourceProperties();
    assertTrue(dataSourceProperties.isEmpty());
    assertEquals(dataSourceProperties, ((HikariDataSource) actualCamundaDataSourceResult).getHealthCheckProperties());
  }

  /**
   * Test
   * {@link CamundaDataSourceConfiguration#camundaTransactionManager(DataSource)}.
   * <ul>
   *   <li>Then TransactionExecutionListeners return {@link List}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CamundaDataSourceConfiguration#camundaTransactionManager(DataSource)}
   */
  @Test
  @DisplayName("Test camundaTransactionManager(DataSource); then TransactionExecutionListeners return List")
  void testCamundaTransactionManager_thenTransactionExecutionListenersReturnList() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    DataSource dataSource = mock(DataSource.class);

    // Act
    PlatformTransactionManager actualCamundaTransactionManagerResult = (new CamundaDataSourceConfiguration())
        .camundaTransactionManager(dataSource);

    // Assert
    Collection<TransactionExecutionListener> transactionExecutionListeners = ((DataSourceTransactionManager) actualCamundaTransactionManagerResult)
        .getTransactionExecutionListeners();
    assertTrue(transactionExecutionListeners instanceof List);
    assertTrue(actualCamundaTransactionManagerResult instanceof DataSourceTransactionManager);
    assertEquals(-1, ((DataSourceTransactionManager) actualCamundaTransactionManagerResult).getDefaultTimeout());
    assertEquals(0,
        ((DataSourceTransactionManager) actualCamundaTransactionManagerResult).getTransactionSynchronization());
    assertFalse(((DataSourceTransactionManager) actualCamundaTransactionManagerResult).isEnforceReadOnly());
    assertFalse(
        ((DataSourceTransactionManager) actualCamundaTransactionManagerResult).isFailEarlyOnGlobalRollbackOnly());
    assertFalse(((DataSourceTransactionManager) actualCamundaTransactionManagerResult).isRollbackOnCommitFailure());
    assertFalse(((DataSourceTransactionManager) actualCamundaTransactionManagerResult).isValidateExistingTransaction());
    assertTrue(transactionExecutionListeners.isEmpty());
    assertTrue(((DataSourceTransactionManager) actualCamundaTransactionManagerResult)
        .isGlobalRollbackOnParticipationFailure());
    assertTrue(((DataSourceTransactionManager) actualCamundaTransactionManagerResult).isNestedTransactionAllowed());
    assertSame(dataSource, ((DataSourceTransactionManager) actualCamundaTransactionManagerResult).getDataSource());
    assertSame(dataSource, ((DataSourceTransactionManager) actualCamundaTransactionManagerResult).getResourceFactory());
  }
}
