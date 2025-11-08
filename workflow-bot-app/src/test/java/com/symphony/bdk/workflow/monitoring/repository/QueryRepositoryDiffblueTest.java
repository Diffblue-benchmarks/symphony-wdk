package com.symphony.bdk.workflow.monitoring.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.symphony.bdk.workflow.converter.ObjectConverter;
import com.symphony.bdk.workflow.engine.camunda.monitoring.repository.ActivityCmdaApiQueryRepository;
import com.symphony.bdk.workflow.monitoring.repository.domain.ActivityInstanceDomain;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ActivityCmdaApiQueryRepository.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class QueryRepositoryDiffblueTest {
  @MockBean
  private HistoryService historyService;

  @MockBean
  private ObjectConverter objectConverter;

  @Autowired
  private QueryRepository<ActivityInstanceDomain, String> queryRepository;

  @MockBean
  private RepositoryService repositoryService;

  @MockBean
  private RuntimeService runtimeService;

  /**
   * Method under test: {@link QueryRepository#findById(Object)}
   */
  @Test
  void testFindById() {
    // Arrange, Act and Assert
    assertFalse(queryRepository.findById("Var1").isPresent());
  }

  /**
   * Method under test: {@link QueryRepository#findAll()}
   */
  @Test
  void testFindAll() {
    // Arrange, Act and Assert
    assertTrue(queryRepository.findAll().isEmpty());
  }
}
