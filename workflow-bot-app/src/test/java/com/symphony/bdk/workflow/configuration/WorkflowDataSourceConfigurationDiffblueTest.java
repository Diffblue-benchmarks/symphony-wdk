package com.symphony.bdk.workflow.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.orm.jpa.DefaultJpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionExecutionListener;

@ExtendWith(MockitoExtension.class)
class WorkflowDataSourceConfigurationDiffblueTest {
  @InjectMocks
  private WorkflowDataSourceConfiguration workflowDataSourceConfiguration;

  /**
   * Test {@link WorkflowDataSourceConfiguration#wdkDataSource()}.
   * <p>
   * Method under test: {@link WorkflowDataSourceConfiguration#wdkDataSource()}
   */
  @Test
  @DisplayName("Test wdkDataSource()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"DataSource WorkflowDataSourceConfiguration.wdkDataSource()"})
  void testWdkDataSource() throws SQLException {
    // Arrange and Act
    DataSource actualWdkDataSourceResult = workflowDataSourceConfiguration.wdkDataSource();

    // Assert
    assertTrue(actualWdkDataSourceResult instanceof HikariDataSource);
    assertNull(((HikariDataSource) actualWdkDataSourceResult).getHikariPoolMXBean());
    assertNull(((HikariDataSource) actualWdkDataSourceResult).getMetricsTrackerFactory());
    assertNull(actualWdkDataSourceResult.getLogWriter());
    assertNull(((HikariDataSource) actualWdkDataSourceResult).getHealthCheckRegistry());
    assertNull(((HikariDataSource) actualWdkDataSourceResult).getMetricRegistry());
    assertNull(((HikariDataSource) actualWdkDataSourceResult).getCatalog());
    assertNull(((HikariDataSource) actualWdkDataSourceResult).getConnectionInitSql());
    assertNull(((HikariDataSource) actualWdkDataSourceResult).getConnectionTestQuery());
    assertNull(((HikariDataSource) actualWdkDataSourceResult).getDataSourceClassName());
    assertNull(((HikariDataSource) actualWdkDataSourceResult).getDataSourceJNDI());
    assertNull(((HikariDataSource) actualWdkDataSourceResult).getDriverClassName());
    assertNull(((HikariDataSource) actualWdkDataSourceResult).getExceptionOverrideClassName());
    assertNull(((HikariDataSource) actualWdkDataSourceResult).getJdbcUrl());
    assertNull(((HikariDataSource) actualWdkDataSourceResult).getPassword());
    assertNull(((HikariDataSource) actualWdkDataSourceResult).getPoolName());
    assertNull(((HikariDataSource) actualWdkDataSourceResult).getSchema());
    assertNull(((HikariDataSource) actualWdkDataSourceResult).getTransactionIsolation());
    assertNull(((HikariDataSource) actualWdkDataSourceResult).getUsername());
    assertNull(((HikariDataSource) actualWdkDataSourceResult).getScheduledExecutor());
    assertNull(((HikariDataSource) actualWdkDataSourceResult).getThreadFactory());
    assertNull(((HikariDataSource) actualWdkDataSourceResult).getDataSource());
    assertEquals(-1, ((HikariDataSource) actualWdkDataSourceResult).getMaximumPoolSize());
    assertEquals(-1, ((HikariDataSource) actualWdkDataSourceResult).getMinimumIdle());
    assertEquals(0, actualWdkDataSourceResult.getLoginTimeout());
    assertEquals(0L, ((HikariDataSource) actualWdkDataSourceResult).getKeepaliveTime());
    assertEquals(0L, ((HikariDataSource) actualWdkDataSourceResult).getLeakDetectionThreshold());
    assertEquals(1800000L, ((HikariDataSource) actualWdkDataSourceResult).getMaxLifetime());
    assertEquals(1L, ((HikariDataSource) actualWdkDataSourceResult).getInitializationFailTimeout());
    assertEquals(30000L, ((HikariDataSource) actualWdkDataSourceResult).getConnectionTimeout());
    assertEquals(5000L, ((HikariDataSource) actualWdkDataSourceResult).getValidationTimeout());
    assertEquals(600000L, ((HikariDataSource) actualWdkDataSourceResult).getIdleTimeout());
    assertFalse(((HikariDataSource) actualWdkDataSourceResult).isAllowPoolSuspension());
    assertFalse(((HikariDataSource) actualWdkDataSourceResult).isIsolateInternalQueries());
    assertFalse(((HikariDataSource) actualWdkDataSourceResult).isReadOnly());
    assertFalse(((HikariDataSource) actualWdkDataSourceResult).isRegisterMbeans());
    assertFalse(((HikariDataSource) actualWdkDataSourceResult).isClosed());
    assertFalse(((HikariDataSource) actualWdkDataSourceResult).isRunning());
    assertTrue(((HikariDataSource) actualWdkDataSourceResult).isAutoCommit());
    Properties dataSourceProperties = ((HikariDataSource) actualWdkDataSourceResult).getDataSourceProperties();
    assertTrue(dataSourceProperties.isEmpty());
    assertEquals(dataSourceProperties, ((HikariDataSource) actualWdkDataSourceResult).getHealthCheckProperties());
  }

  /**
   * Test {@link WorkflowDataSourceConfiguration#transactionManager(DataSource)}.
   * <p>
   * Method under test: {@link WorkflowDataSourceConfiguration#transactionManager(DataSource)}
   */
  @Test
  @DisplayName("Test transactionManager(DataSource)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"PlatformTransactionManager WorkflowDataSourceConfiguration.transactionManager(DataSource)"})
  void testTransactionManager() {
    // Arrange
    DataSource dataSource = mock(DataSource.class);

    // Act
    PlatformTransactionManager actualTransactionManagerResult = workflowDataSourceConfiguration
        .transactionManager(dataSource);

    // Assert
    Collection<TransactionExecutionListener> transactionExecutionListeners = ((JpaTransactionManager) actualTransactionManagerResult)
        .getTransactionExecutionListeners();
    assertTrue(transactionExecutionListeners instanceof List);
    assertTrue(((JpaTransactionManager) actualTransactionManagerResult).getJpaDialect() instanceof DefaultJpaDialect);
    assertTrue(actualTransactionManagerResult instanceof JpaTransactionManager);
    assertNull(((JpaTransactionManager) actualTransactionManagerResult).getEntityManagerFactory());
    assertNull(((JpaTransactionManager) actualTransactionManagerResult).getPersistenceUnitName());
    assertEquals(-1, ((JpaTransactionManager) actualTransactionManagerResult).getDefaultTimeout());
    assertEquals(0, ((JpaTransactionManager) actualTransactionManagerResult).getTransactionSynchronization());
    assertFalse(((JpaTransactionManager) actualTransactionManagerResult).isFailEarlyOnGlobalRollbackOnly());
    assertFalse(((JpaTransactionManager) actualTransactionManagerResult).isRollbackOnCommitFailure());
    assertFalse(((JpaTransactionManager) actualTransactionManagerResult).isValidateExistingTransaction());
    assertTrue(transactionExecutionListeners.isEmpty());
    assertTrue(((JpaTransactionManager) actualTransactionManagerResult).getJpaPropertyMap().isEmpty());
    assertTrue(((JpaTransactionManager) actualTransactionManagerResult).isGlobalRollbackOnParticipationFailure());
    assertTrue(((JpaTransactionManager) actualTransactionManagerResult).isNestedTransactionAllowed());
    assertSame(dataSource, ((JpaTransactionManager) actualTransactionManagerResult).getDataSource());
  }

  /**
   * Test {@link WorkflowDataSourceConfiguration#configStateCheck()}.
   * <p>
   * Method under test: {@link WorkflowDataSourceConfiguration#configStateCheck()}
   */
  @Test
  @DisplayName("Test configStateCheck()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.lang.Object WorkflowDataSourceConfiguration.configStateCheck()"})
  void testConfigStateCheck() {
    // Arrange, Act and Assert
    assertThrows(IllegalStateException.class, () -> workflowDataSourceConfiguration.configStateCheck());
  }
}
