package com.symphony.bdk.workflow.engine.executor.request.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpHeaders;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static org.assertj.core.api.Assertions.assertThat;

class HttpClientClaudeTest {

  private WireMockServer wireMockServer;
  private HttpClient httpClient;
  private String baseUrl;

  @BeforeEach
  void setUp() {
    wireMockServer = new WireMockServer(WireMockConfiguration.options().dynamicPort());
    wireMockServer.start();
    baseUrl = "http://localhost:" + wireMockServer.port();
    httpClient = new HttpClient();
  }

  @AfterEach
  void tearDown() {
    if (wireMockServer != null && wireMockServer.isRunning()) {
      wireMockServer.stop();
    }
  }

  @Test
  void testConstructor() {
    // Test that the constructor initializes the HttpClient properly
    HttpClient client = new HttpClient();
    assertThat(client).isNotNull();
  }

  @Test
  void testExecute_GetRequest_WithJsonResponse() throws IOException {
    // Arrange
    String jsonResponse = "{\"message\":\"success\",\"code\":200}";
    wireMockServer.stubFor(get(urlEqualTo("/test"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
            .withBody(jsonResponse)));

    Map<String, String> headers = new HashMap<>();

    // Act
    Response response = httpClient.execute("GET", baseUrl + "/test", null, headers);

    // Assert
    assertThat(response).isNotNull();
    assertThat(response.getCode()).isEqualTo(200);
    assertThat(response.getContent()).isInstanceOf(Map.class);

    @SuppressWarnings("unchecked")
    Map<String, Object> contentMap = (Map<String, Object>) response.getContent();
    assertThat(contentMap).containsEntry("message", "success");
    assertThat(contentMap).containsEntry("code", 200);
  }

  @Test
  void testExecute_PostRequest_WithJsonBody() throws IOException {
    // Arrange
    String responseBody = "{\"id\":1,\"status\":\"created\"}";
    wireMockServer.stubFor(post(urlEqualTo("/create"))
        .willReturn(aResponse()
            .withStatus(201)
            .withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
            .withBody(responseBody)));

    Map<String, String> headers = new HashMap<>();
    headers.put(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());

    Map<String, Object> body = new HashMap<>();
    body.put("name", "test");
    body.put("value", 123);

    // Act
    Response response = httpClient.execute("POST", baseUrl + "/create", body, headers);

    // Assert
    assertThat(response).isNotNull();
    assertThat(response.getCode()).isEqualTo(201);
    assertThat(response.getContent()).isInstanceOf(Map.class);

    @SuppressWarnings("unchecked")
    Map<String, Object> contentMap = (Map<String, Object>) response.getContent();
    assertThat(contentMap).containsEntry("id", 1);
    assertThat(contentMap).containsEntry("status", "created");

    // Verify request was sent with proper content
    wireMockServer.verify(postRequestedFor(urlEqualTo("/create"))
        .withHeader(HttpHeaders.CONTENT_TYPE, containing("application/json")));
  }

  @Test
  void testExecute_PostRequest_WithStringBody() throws IOException {
    // Arrange
    wireMockServer.stubFor(post(urlEqualTo("/text"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
            .withBody("{\"result\":\"ok\"}")));

    Map<String, String> headers = new HashMap<>();
    headers.put(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());

    String body = "{\"key\":\"value\"}";

    // Act
    Response response = httpClient.execute("POST", baseUrl + "/text", body, headers);

    // Assert
    assertThat(response).isNotNull();
    assertThat(response.getCode()).isEqualTo(200);
    assertThat(response.getContent()).isInstanceOf(Map.class);
  }

  @Test
  void testExecute_PostRequest_WithStringBodyNoContentType() throws IOException {
    // Arrange - When no content type is provided, it should default to application/json
    wireMockServer.stubFor(post(urlEqualTo("/default"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
            .withBody("{\"status\":\"ok\"}")));

    Map<String, String> headers = new HashMap<>();
    String body = "plain text body";

    // Act
    Response response = httpClient.execute("POST", baseUrl + "/default", body, headers);

    // Assert
    assertThat(response).isNotNull();
    assertThat(response.getCode()).isEqualTo(200);

    wireMockServer.verify(postRequestedFor(urlEqualTo("/default"))
        .withHeader(HttpHeaders.CONTENT_TYPE, containing("application/json")));
  }

  @Test
  void testExecute_PostRequest_WithObjectBodyNoContentType() throws IOException {
    // Arrange - When no content type is provided with an object, should serialize to JSON
    wireMockServer.stubFor(post(urlEqualTo("/object"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
            .withBody("{\"success\":true}")));

    Map<String, String> headers = new HashMap<>();
    Map<String, Object> body = new HashMap<>();
    body.put("field", "value");

    // Act
    Response response = httpClient.execute("POST", baseUrl + "/object", body, headers);

    // Assert
    assertThat(response).isNotNull();
    assertThat(response.getCode()).isEqualTo(200);

    wireMockServer.verify(postRequestedFor(urlEqualTo("/object"))
        .withHeader(HttpHeaders.CONTENT_TYPE, containing("application/json")));
  }

  @Test
  void testExecute_PostRequest_WithMultipartFormData() throws IOException {
    // Arrange
    wireMockServer.stubFor(post(urlEqualTo("/upload"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
            .withBody("{\"uploaded\":true}")));

    Map<String, String> headers = new HashMap<>();
    headers.put(HttpHeaders.CONTENT_TYPE, ContentType.MULTIPART_FORM_DATA.getMimeType());

    Map<String, Object> body = new LinkedHashMap<>();
    body.put("field1", "value1");
    body.put("field2", "value2");

    // Act
    Response response = httpClient.execute("POST", baseUrl + "/upload", body, headers);

    // Assert
    assertThat(response).isNotNull();
    assertThat(response.getCode()).isEqualTo(200);
    assertThat(response.getContent()).isInstanceOf(Map.class);

    // Verify multipart content was sent
    wireMockServer.verify(postRequestedFor(urlEqualTo("/upload"))
        .withHeader(HttpHeaders.CONTENT_TYPE, containing("multipart/form-data")));
  }

  @Test
  void testExecute_PutRequest() throws IOException {
    // Arrange
    wireMockServer.stubFor(put(urlEqualTo("/update"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
            .withBody("{\"updated\":true}")));

    Map<String, String> headers = new HashMap<>();
    headers.put(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());

    Map<String, Object> body = new HashMap<>();
    body.put("id", 1);

    // Act
    Response response = httpClient.execute("PUT", baseUrl + "/update", body, headers);

    // Assert
    assertThat(response).isNotNull();
    assertThat(response.getCode()).isEqualTo(200);
  }

  @Test
  void testExecute_DeleteRequest() throws IOException {
    // Arrange
    wireMockServer.stubFor(delete(urlEqualTo("/delete"))
        .willReturn(aResponse()
            .withStatus(204)));

    Map<String, String> headers = new HashMap<>();

    // Act
    Response response = httpClient.execute("DELETE", baseUrl + "/delete", null, headers);

    // Assert
    assertThat(response).isNotNull();
    assertThat(response.getCode()).isEqualTo(204);
    // When there's no response entity, content should be empty string
    assertThat(response.getContent()).isEqualTo("");
  }

  @Test
  void testExecute_WithCustomHeaders() throws IOException {
    // Arrange
    wireMockServer.stubFor(get(urlEqualTo("/headers"))
        .withHeader("X-Custom-Header", equalTo("custom-value"))
        .withHeader("Authorization", equalTo("Bearer token123"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
            .withBody("{\"status\":\"ok\"}")));

    Map<String, String> headers = new HashMap<>();
    headers.put("X-Custom-Header", "custom-value");
    headers.put("Authorization", "Bearer token123");

    // Act
    Response response = httpClient.execute("GET", baseUrl + "/headers", null, headers);

    // Assert
    assertThat(response).isNotNull();
    assertThat(response.getCode()).isEqualTo(200);
  }

  @Test
  void testExecute_WithNonJsonResponse() throws IOException {
    // Arrange
    wireMockServer.stubFor(get(urlEqualTo("/text"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader(HttpHeaders.CONTENT_TYPE, ContentType.TEXT_PLAIN.getMimeType())
            .withBody("plain text response")));

    Map<String, String> headers = new HashMap<>();

    // Act
    Response response = httpClient.execute("GET", baseUrl + "/text", null, headers);

    // Assert
    assertThat(response).isNotNull();
    assertThat(response.getCode()).isEqualTo(200);
    // Non-JSON content should be returned as string
    assertThat(response.getContent()).isEqualTo("plain text response");
  }

  @Test
  void testExecute_WithInvalidJsonResponse() throws IOException {
    // Arrange - Response claims to be JSON but is invalid
    wireMockServer.stubFor(get(urlEqualTo("/invalid-json"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
            .withBody("not valid json {")));

    Map<String, String> headers = new HashMap<>();

    // Act
    Response response = httpClient.execute("GET", baseUrl + "/invalid-json", null, headers);

    // Assert
    assertThat(response).isNotNull();
    assertThat(response.getCode()).isEqualTo(200);
    // When JSON parsing fails, content should be returned as string
    assertThat(response.getContent()).isEqualTo("not valid json {");
  }

  @Test
  void testExecute_WithNoContentTypeHeader() throws IOException {
    // Arrange - No content type header, should default to JSON parsing
    wireMockServer.stubFor(get(urlEqualTo("/no-content-type"))
        .willReturn(aResponse()
            .withStatus(200)
            .withBody("{\"key\":\"value\"}")));

    Map<String, String> headers = new HashMap<>();

    // Act
    Response response = httpClient.execute("GET", baseUrl + "/no-content-type", null, headers);

    // Assert
    assertThat(response).isNotNull();
    assertThat(response.getCode()).isEqualTo(200);
    // Null content type should default to JSON parsing
    assertThat(response.getContent()).isInstanceOf(Map.class);
  }

  @Test
  void testExecute_WithErrorStatusCode() throws IOException {
    // Arrange
    wireMockServer.stubFor(get(urlEqualTo("/error"))
        .willReturn(aResponse()
            .withStatus(500)
            .withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
            .withBody("{\"error\":\"Internal Server Error\"}")));

    Map<String, String> headers = new HashMap<>();

    // Act
    Response response = httpClient.execute("GET", baseUrl + "/error", null, headers);

    // Assert
    assertThat(response).isNotNull();
    assertThat(response.getCode()).isEqualTo(500);
    assertThat(response.getContent()).isInstanceOf(Map.class);

    @SuppressWarnings("unchecked")
    Map<String, Object> contentMap = (Map<String, Object>) response.getContent();
    assertThat(contentMap).containsEntry("error", "Internal Server Error");
  }

  @Test
  void testExecute_With404NotFound() throws IOException {
    // Arrange
    wireMockServer.stubFor(get(urlEqualTo("/not-found"))
        .willReturn(aResponse()
            .withStatus(404)
            .withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
            .withBody("{\"error\":\"Not Found\"}")));

    Map<String, String> headers = new HashMap<>();

    // Act
    Response response = httpClient.execute("GET", baseUrl + "/not-found", null, headers);

    // Assert
    assertThat(response).isNotNull();
    assertThat(response.getCode()).isEqualTo(404);
  }

  @Test
  void testExecute_PostWithTextPlainContentType() throws IOException {
    // Arrange
    wireMockServer.stubFor(post(urlEqualTo("/plain"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
            .withBody("{\"received\":true}")));

    Map<String, String> headers = new HashMap<>();
    headers.put(HttpHeaders.CONTENT_TYPE, ContentType.TEXT_PLAIN.getMimeType());

    String body = "plain text body";

    // Act
    Response response = httpClient.execute("POST", baseUrl + "/plain", body, headers);

    // Assert
    assertThat(response).isNotNull();
    assertThat(response.getCode()).isEqualTo(200);

    wireMockServer.verify(postRequestedFor(urlEqualTo("/plain"))
        .withHeader(HttpHeaders.CONTENT_TYPE, containing("text/plain")));
  }

  @Test
  void testExecute_PostWithXmlContentType() throws IOException {
    // Arrange
    wireMockServer.stubFor(post(urlEqualTo("/xml"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_XML.getMimeType())
            .withBody("<response><status>ok</status></response>")));

    Map<String, String> headers = new HashMap<>();
    headers.put(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_XML.getMimeType());

    String body = "<request><data>value</data></request>";

    // Act
    Response response = httpClient.execute("POST", baseUrl + "/xml", body, headers);

    // Assert
    assertThat(response).isNotNull();
    assertThat(response.getCode()).isEqualTo(200);
    // XML content type should not be parsed as JSON
    assertThat(response.getContent()).isEqualTo("<response><status>ok</status></response>");
  }

  @Test
  void testExecute_WithEmptyResponseBody() throws IOException {
    // Arrange
    wireMockServer.stubFor(post(urlEqualTo("/empty"))
        .willReturn(aResponse()
            .withStatus(201)
            .withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
            .withBody("")));

    Map<String, String> headers = new HashMap<>();
    headers.put(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());

    Map<String, Object> body = new HashMap<>();
    body.put("test", "data");

    // Act
    Response response = httpClient.execute("POST", baseUrl + "/empty", body, headers);

    // Assert
    assertThat(response).isNotNull();
    assertThat(response.getCode()).isEqualTo(201);
    // Empty JSON response should remain as empty string after failed parsing
    assertThat(response.getContent()).isEqualTo("");
  }

  @Test
  void testExecute_GetRequestNoBody() throws IOException {
    // Arrange
    wireMockServer.stubFor(get(urlEqualTo("/simple"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
            .withBody("{\"data\":\"value\"}")));

    Map<String, String> headers = new HashMap<>();

    // Act
    Response response = httpClient.execute("GET", baseUrl + "/simple", null, headers);

    // Assert
    assertThat(response).isNotNull();
    assertThat(response.getCode()).isEqualTo(200);
    assertThat(response.getContent()).isInstanceOf(Map.class);
  }

  @Test
  void testExecute_WithComplexJsonResponse() throws IOException {
    // Arrange
    String complexJson = "{\"user\":{\"name\":\"John\",\"age\":30},\"items\":[1,2,3],\"active\":true}";
    wireMockServer.stubFor(get(urlEqualTo("/complex"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
            .withBody(complexJson)));

    Map<String, String> headers = new HashMap<>();

    // Act
    Response response = httpClient.execute("GET", baseUrl + "/complex", null, headers);

    // Assert
    assertThat(response).isNotNull();
    assertThat(response.getCode()).isEqualTo(200);
    assertThat(response.getContent()).isInstanceOf(Map.class);

    @SuppressWarnings("unchecked")
    Map<String, Object> contentMap = (Map<String, Object>) response.getContent();
    assertThat(contentMap).containsKey("user");
    assertThat(contentMap).containsKey("items");
    assertThat(contentMap).containsEntry("active", true);
  }
}
