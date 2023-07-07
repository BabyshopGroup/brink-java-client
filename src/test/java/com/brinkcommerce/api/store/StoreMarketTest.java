package com.brinkcommerce.api.store;

import static com.brinkcommerce.api.util.Mocks.createStoreMarketResponse;
import static com.brinkcommerce.api.util.Mocks.mockStoreMarket;
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
import com.brinkcommerce.api.management.store.BrinkCountryCode;
import com.brinkcommerce.api.management.store.BrinkCurrencyCode;
import com.brinkcommerce.api.management.store.BrinkStoreException;
import com.brinkcommerce.api.management.store.market.BrinkStoreMarket;
import com.brinkcommerce.api.management.store.market.BrinkStoreMarketApi;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StoreMarketTest {

  private BrinkStoreMarketApi storeApi;

  @Mock HttpClient httpClient;

  @Mock AuthenticationHandler authenticationHandler;

  @BeforeEach
  void init() {
    final BrinkAuthentication authenticationConfiguration =
        mockAuthenticationConfiguration();

    final ManagementConfiguration brinkConfiguration =
        mockBrinkConfiguration(authenticationConfiguration, httpClient);

    storeApi = BrinkStoreMarketApi.init(brinkConfiguration, authenticationHandler);
  }

  @Test
  void whenCreate_returnBrinkStoreMarket() throws IOException, InterruptedException {
    final BrinkStoreMarket storeMarket = mockStoreMarket();

    final BrinkStoreMarket responseStoreMarket = createStoreMarketResponse(storeMarket);

    HttpResponse<String> httpResponse = mockHttpResponse(responseStoreMarket, 200);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThat(storeApi.create(storeMarket)).isEqualTo(responseStoreMarket);

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo("http://mockserver.com/store-groups/BABYSHOP/markets/SE/");
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
    final BrinkStoreMarket storeMarket = mockStoreMarket();

    final BrinkHttpErrorMessage errorMessage = new BrinkHttpErrorMessage("0", "Error");
    HttpResponse<String> httpResponse = mockHttpResponse(errorMessage, statusCode);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatThrownBy(() -> storeApi.create(storeMarket))
        .isInstanceOf(BrinkStoreException.class)
        .hasMessageContaining(String.format("Http code: %d", statusCode));
  }

  @Test
  void whenCreate_throwsRuntime() throws IOException, InterruptedException {
    final BrinkStoreMarket storeMarket = mockStoreMarket();

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class)))
        .thenThrow(RuntimeException.class);

    assertThatThrownBy(() -> storeApi.create(storeMarket)).isInstanceOf(BrinkStoreException.class);
  }

  @Test
  void whenCreateOnNullArgument_throwsNullPointer() {
    final BrinkStoreMarket storeMarket = null;

    assertThatThrownBy(() -> storeApi.create(storeMarket)).isInstanceOf(NullPointerException.class);
  }

  // -----------------------------------------------------------------------------------------------

  @Test
  void whenUpdate_returnBrinkStoreMarket() throws IOException, InterruptedException {
    final BrinkStoreMarket updatedStoreMarket =
        BrinkStoreMarket.builder(mockStoreMarket()).withCurrencyCode(BrinkCurrencyCode.EUR).build();

    final BrinkStoreMarket responseStoreMarket = createStoreMarketResponse(updatedStoreMarket);

    HttpResponse<String> httpResponse = mockHttpResponse(responseStoreMarket, 200);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThat(storeApi.update(updatedStoreMarket)).isEqualTo(responseStoreMarket);

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo("http://mockserver.com/store-groups/BABYSHOP/markets/SE/");
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
    final BrinkStoreMarket storeMarket = mockStoreMarket();

    final BrinkHttpErrorMessage errorMessage = new BrinkHttpErrorMessage("0", "Error");
    HttpResponse<String> httpResponse = mockHttpResponse(errorMessage, statusCode);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatThrownBy(() -> storeApi.update(storeMarket))
        .isInstanceOf(BrinkStoreException.class)
        .hasMessageContaining(String.format("Http code: %d", statusCode));
  }

  @Test
  void whenUpdate_throwsRuntime() throws IOException, InterruptedException {
    final BrinkStoreMarket storeMarket = mockStoreMarket();

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class)))
        .thenThrow(RuntimeException.class);

    assertThatThrownBy(() -> storeApi.update(storeMarket)).isInstanceOf(BrinkStoreException.class);
  }

  @Test
  void whenUpdateOnNullArgument_throwsNullPointer() {
    final BrinkStoreMarket storeMarket = null;

    assertThatThrownBy(() -> storeApi.update(storeMarket)).isInstanceOf(NullPointerException.class);
  }

  // -----------------------------------------------------------------------------------------------

  @Test
  void whenGetAll_returnBrinkStoreMarketList() throws IOException, InterruptedException {

    final String storeGroupId = "BABYSHOP";
    final List<BrinkStoreMarket> updatedStoreMarket = List.of(mockStoreMarket(), mockStoreMarket());
    record ResponseWrapper(List<BrinkStoreMarket> storeMarkets) {}

    final List<BrinkStoreMarket> responseStoreMarket =
        createStoreMarketResponse(updatedStoreMarket);
    final ResponseWrapper wrappedResponse = new ResponseWrapper(responseStoreMarket);

    HttpResponse<String> httpResponse = mockHttpResponse(wrappedResponse, 200);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThat(storeApi.get(storeGroupId)).isEqualTo(responseStoreMarket);

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo("http://mockserver.com/store-groups/BABYSHOP/markets");
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
  void whenGetAll_returnErrorStatusCode(int statusCode) throws IOException, InterruptedException {
    final String storeGroupId = "BABYSHOP";

    final BrinkHttpErrorMessage errorMessage = new BrinkHttpErrorMessage("0", "Error");
    HttpResponse<String> httpResponse = mockHttpResponse(errorMessage, statusCode);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatThrownBy(() -> storeApi.get(storeGroupId))
        .isInstanceOf(BrinkStoreException.class)
        .hasMessageContaining(String.format("Http code: %d", statusCode));
  }

  @Test
  void whenGetAll_throwsRuntime() throws IOException, InterruptedException {
    final String storeGroupId = "BABYSHOP";

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class)))
        .thenThrow(RuntimeException.class);

    assertThatThrownBy(() -> storeApi.get(storeGroupId)).isInstanceOf(BrinkStoreException.class);
  }

  @Test
  void whenGetAllOnNullArgument_throwsNullPointer() {
    final String storeGroupId = null;

    assertThatThrownBy(() -> storeApi.get(storeGroupId))
        .isInstanceOf(NullPointerException.class);
  }

  // -----------------------------------------------------------------------------------------------

  @Test
  void whenDelete_returnNoContent() throws IOException, InterruptedException {

    final String storeGroupId = "BABYSHOP";
    final BrinkCountryCode countryCode = BrinkCountryCode.SE;

    HttpResponse<String> httpResponse = mockHttpResponse("", 204);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatCode(() -> storeApi.delete(storeGroupId, BrinkCountryCode.SE))
        .doesNotThrowAnyException();

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo("http://mockserver.com/store-groups/BABYSHOP/markets/SE/");
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
    final BrinkCountryCode countryCode = BrinkCountryCode.SE;
    final BrinkHttpErrorMessage errorMessage = new BrinkHttpErrorMessage("0", "Error");
    HttpResponse<String> httpResponse = mockHttpResponse(errorMessage, statusCode);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatThrownBy(() -> storeApi.delete(storeGroupId, BrinkCountryCode.SE))
        .isInstanceOf(BrinkStoreException.class)
        .hasMessageContaining(String.format("Http code: %d", statusCode));
  }

  @Test
  void whenDelete_throwsRuntime() throws IOException, InterruptedException {
    final String storeGroupId = "BABYSHOP";

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class)))
        .thenThrow(RuntimeException.class);

    assertThatThrownBy(() -> storeApi.delete(storeGroupId, BrinkCountryCode.SE))
        .isInstanceOf(BrinkStoreException.class);
  }

  @Test
  void whenDeleteOnNullArgument_throwsNullPointer() {
    final String storeGroupId = null;

    assertThatThrownBy(() -> storeApi.delete(storeGroupId, BrinkCountryCode.SE))
        .isInstanceOf(NullPointerException.class);
  }
}
