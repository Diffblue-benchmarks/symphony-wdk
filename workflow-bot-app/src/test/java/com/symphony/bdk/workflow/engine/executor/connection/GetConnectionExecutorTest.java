package com.symphony.bdk.workflow.engine.executor.connection;

import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.connection.ConnectionService;
import com.symphony.bdk.gen.api.model.UserConnection;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.connection.GetConnection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetConnectionExecutorTest {

  @InjectMocks
  private GetConnectionExecutor executor;

  @Mock
  private ActivityExecutorContext<GetConnection> context;

  @Mock
  private BdkGateway bdk;

  @Test
  void shouldGetConnectionWhenNotObo() {
    GetConnection activity = new GetConnection();
    activity.setUserId("123456");

    ConnectionService connectionService = mock(ConnectionService.class);
    UserConnection userConnection = new UserConnection();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.connections()).thenReturn(connectionService);
    when(connectionService.getConnection(123456L)).thenReturn(userConnection);

    executor.execute(context);

    verify(context).setOutputVariable("connection", userConnection);
  }

  @Test
  void shouldGetConnectionOboWhenOboUsernameIsSet() {
    GetConnection activity = new GetConnection();
    activity.setUserId("789");
    Obo obo = new Obo();
    obo.setUsername("oboUser");
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    ConnectionService connectionService = mock(ConnectionService.class);
    UserConnection userConnection = new UserConnection();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.obo("oboUser")).thenReturn(authSession);
    when(bdk.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(connectionService);
    when(connectionService.getConnection(789L)).thenReturn(userConnection);

    executor.execute(context);

    verify(context).setOutputVariable("connection", userConnection);
  }

  @Test
  void shouldGetConnectionOboWhenOboUserIdIsSet() {
    GetConnection activity = new GetConnection();
    activity.setUserId("101");
    Obo obo = new Obo();
    obo.setUserId(42L);
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    ConnectionService connectionService = mock(ConnectionService.class);
    UserConnection userConnection = new UserConnection();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.obo(42L)).thenReturn(authSession);
    when(bdk.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(connectionService);
    when(connectionService.getConnection(101L)).thenReturn(userConnection);

    executor.execute(context);

    verify(context).setOutputVariable("connection", userConnection);
  }

  @Test
  void shouldDoOboWithCacheWhenOboUsernameIsSet() {
    GetConnection activity = new GetConnection();
    activity.setUserId("555");
    Obo obo = new Obo();
    obo.setUsername("oboUser2");
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    ConnectionService connectionService = mock(ConnectionService.class);
    UserConnection userConnection = new UserConnection();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.obo("oboUser2")).thenReturn(authSession);
    when(bdk.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(connectionService);
    when(connectionService.getConnection(555L)).thenReturn(userConnection);

    UserConnection result = executor.doOboWithCache(context);

    org.assertj.core.api.Assertions.assertThat(result).isEqualTo(userConnection);
  }
}
