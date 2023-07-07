package com.brinkcommerce.api.store;

import static com.brinkcommerce.api.util.Mocks.createStoreGroupResponse;
import static com.brinkcommerce.api.util.Mocks.mockStoreGroup;
import static com.brinkcommerce.api.util.TestUtils.mockAuthenticationConfiguration;
import static com.brinkcommerce.api.util.TestUtils.mockBrinkConfiguration;
import static com.brinkcommerce.api.util.TestUtils.mockHttpResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.brinkcommerce.api.authentication.AuthenticationHandler;
import com.brinkcommerce.api.common.BrinkHttpErrorMessage;
import com.brinkcommerce.api.configuration.BrinkAuthentication;
import com.brinkcommerce.api.configuration.ManagementConfiguration;
import com.brinkcommerce.api.management.store.BrinkStoreException;
import com.brinkcommerce.api.management.store.storegroup.BrinkStoreGroup;
import com.brinkcommerce.api.management.store.storegroup.BrinkStoreGroupApi;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StoreGroupTest {

  private BrinkStoreGroupApi storeApi;

  @Mock HttpClient httpClient;

  @Mock AuthenticationHandler authenticationHandler;

  @BeforeEach
  void init() {
    final BrinkAuthentication authenticationConfiguration =
        mockAuthenticationConfiguration();

    final ManagementConfiguration brinkConfiguration =
        mockBrinkConfiguration(authenticationConfiguration, httpClient);

    storeApi = BrinkStoreGroupApi.init(brinkConfiguration, authenticationHandler);
  }

  @Test
  void whenCreate_returnBrinkStoreGroup() throws IOException, InterruptedException {
    final BrinkStoreGroup storeGroup = mockStoreGroup();

    final BrinkStoreGroup responseStoreGroup = createStoreGroupResponse(storeGroup);

    HttpResponse<String> httpResponse = mockHttpResponse(responseStoreGroup, 200);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThat(storeApi.create(storeGroup)).isEqualTo(responseStoreGroup);

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo("http://mockserver.com/store-groups/BABYSHOP");
                  return true;
                }),
            argThat(
                x -> {
                  assertThat(x).isNotNull();
                  return true;
                }));
  }

  @ParameterizedTest()
  @ValueSource(ints = {400, 500})
  void whenCreate_returnErrorStatusCode(int statusCode) throws IOException, InterruptedException {
    final BrinkStoreGroup storeGroup = mockStoreGroup();

    final BrinkHttpErrorMessage errorMessage = new BrinkHttpErrorMessage("0", "Error");
    HttpResponse<String> httpResponse = mockHttpResponse(errorMessage, statusCode);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatThrownBy(() -> storeApi.create(storeGroup))
        .isInstanceOf(BrinkStoreException.class)
        .hasMessageContaining(String.format("Http code: %d", statusCode));
  }

  @Test
  void whenCreate_throwsRuntime() throws IOException, InterruptedException {
    final BrinkStoreGroup storeGroup = mockStoreGroup();

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class)))
        .thenThrow(RuntimeException.class);

    assertThatThrownBy(() -> storeApi.create(storeGroup)).isInstanceOf(BrinkStoreException.class);
  }

  @Test
  void whenCreateOnNullArgument_throwsNullPointer() {
    final BrinkStoreGroup storeGroup = null;

    assertThatThrownBy(() -> storeApi.create(storeGroup)).isInstanceOf(NullPointerException.class);
  }
  // -----------------------------------------------------------------------------------------------

  @Test
  void whenUpdate_returnBrinkStoreGroup() throws IOException, InterruptedException {
    final BrinkStoreGroup updatedStoreGroup =
        BrinkStoreGroup.builder(mockStoreGroup()).withDescription("Babyshop for now.").build();

    final BrinkStoreGroup responseStoreGroup = createStoreGroupResponse(updatedStoreGroup);

    HttpResponse<String> httpResponse = mockHttpResponse(responseStoreGroup, 200);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThat(storeApi.update(updatedStoreGroup)).isEqualTo(responseStoreGroup);

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo("http://mockserver.com/store-groups/BABYSHOP");
                  return true;
                }),
            argThat(
                x -> {
                  assertThat(x).isNotNull();
                  return true;
                }));
  }

  @ParameterizedTest()
  @ValueSource(ints = {400, 500})
  void whenUpdate_returnErrorStatusCode(int statusCode) throws IOException, InterruptedException {
    final BrinkStoreGroup storeGroup = mockStoreGroup();

    final BrinkHttpErrorMessage errorMessage = new BrinkHttpErrorMessage("0", "Error");
    HttpResponse<String> httpResponse = mockHttpResponse(errorMessage, statusCode);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatThrownBy(() -> storeApi.update(storeGroup))
        .isInstanceOf(BrinkStoreException.class)
        .hasMessageContaining(String.format("Http code: %d", statusCode));
  }

  @Test
  void whenUpdate_throwsRuntime() throws IOException, InterruptedException {
    final BrinkStoreGroup storeGroup = mockStoreGroup();

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class)))
        .thenThrow(RuntimeException.class);

    assertThatThrownBy(() -> storeApi.update(storeGroup)).isInstanceOf(BrinkStoreException.class);
  }

  @Test
  void whenUpdateOnNullArgument_throwsNullPointer() {
    final BrinkStoreGroup storeGroup = null;

    assertThatThrownBy(() -> storeApi.update(storeGroup)).isInstanceOf(NullPointerException.class);
  }
  // -----------------------------------------------------------------------------------------------

  @Test
  void whenGet_returnBrinkStoreGroup() throws IOException, InterruptedException {

    final String storeGroupId = "BABYSHOP";
    final BrinkStoreGroup responseStoreGroup = createStoreGroupResponse(mockStoreGroup());

    HttpResponse<String> httpResponse = mockHttpResponse(responseStoreGroup, 200);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThat(storeApi.get(storeGroupId)).isEqualTo(responseStoreGroup);

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo("http://mockserver.com/store-groups/BABYSHOP");
                  return true;
                }),
            argThat(
                x -> {
                  assertThat(x).isNotNull();
                  return true;
                }));
  }

  @ParameterizedTest()
  @ValueSource(ints = {400, 500})
  void whenGet_returnErrorStatusCode(int statusCode) throws IOException, InterruptedException {
    final String storeGroupId = "BABYSHOP";

    final BrinkHttpErrorMessage errorMessage = new BrinkHttpErrorMessage("0", "Error");
    HttpResponse<String> httpResponse = mockHttpResponse(errorMessage, statusCode);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatThrownBy(() -> storeApi.get(storeGroupId))
        .isInstanceOf(BrinkStoreException.class)
        .hasMessageContaining(String.format("Http code: %d", statusCode));
  }

  @Test
  void whenGet_throwsRuntime() throws IOException, InterruptedException {
    final String storeGroupId = "BABYSHOP";

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class)))
        .thenThrow(RuntimeException.class);

    assertThatThrownBy(() -> storeApi.get(storeGroupId)).isInstanceOf(BrinkStoreException.class);
  }

  @Test
  void whenGetOnNullArgument_throwsNullPointer() {
    final String storeGroupId = null;

    assertThatThrownBy(() -> storeApi.get(storeGroupId)).isInstanceOf(NullPointerException.class);
  }

  // -----------------------------------------------------------------------------------------------

  @Test
  void whenDelete_returnNoContent() throws IOException, InterruptedException {

    final String storeGroupId = "BABYSHOP";

    HttpResponse<String> httpResponse = mockHttpResponse("", 204);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatCode(() -> storeApi.delete(storeGroupId)).doesNotThrowAnyException();

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo("http://mockserver.com/store-groups/BABYSHOP");
                  return true;
                }),
            argThat(
                x -> {
                  assertThat(x).isNotNull();
                  return true;
                }));
  }

  @ParameterizedTest()
  @ValueSource(ints = {400, 500})
  void whenDelete_returnErrorStatusCode(int statusCode) throws IOException, InterruptedException {
    final String storeGroupId = "BABYSHOP";
    final BrinkHttpErrorMessage errorMessage = new BrinkHttpErrorMessage("0", "Error");
    HttpResponse<String> httpResponse = mockHttpResponse(errorMessage, statusCode);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatThrownBy(() -> storeApi.delete(storeGroupId))
        .isInstanceOf(BrinkStoreException.class)
        .hasMessageContaining(String.format("Http code: %d", statusCode));
  }

  @Test
  void whenDelete_throwsRuntime() throws IOException, InterruptedException {
    final String storeGroupId = "BABYSHOP";

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class)))
        .thenThrow(RuntimeException.class);

    assertThatThrownBy(() -> storeApi.delete(storeGroupId)).isInstanceOf(BrinkStoreException.class);
  }

  @Test
  void whenDeleteOnNullArgument_throwsNullPointer() {
    final String storeGroupId = null;

    assertThatThrownBy(() -> storeApi.delete(storeGroupId))
        .isInstanceOf(NullPointerException.class);
  }
}
