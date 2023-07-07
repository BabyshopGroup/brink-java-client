package com.brinkcommerce.api.price;

import static com.brinkcommerce.api.util.Mocks.getPriceAddonRequest;
import static com.brinkcommerce.api.util.Mocks.mockPriceAddon;
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
import com.brinkcommerce.api.management.price.BrinkPriceException;
import com.brinkcommerce.api.management.price.addon.BrinkPriceAddon;
import com.brinkcommerce.api.management.price.addon.BrinkPriceAddonApi;
import com.brinkcommerce.api.management.price.addon.BrinkPriceAddonRequest;
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
public class PriceAddonTest {
  private BrinkPriceAddonApi priceApi;

  @Mock HttpClient httpClient;

  @Mock AuthenticationHandler authenticationHandler;

  @BeforeEach
  void init() {
    final BrinkAuthentication authenticationConfiguration =
        mockAuthenticationConfiguration();

    final ManagementConfiguration brinkConfiguration =
        mockBrinkConfiguration(authenticationConfiguration, httpClient);

    priceApi = BrinkPriceAddonApi.init(brinkConfiguration, authenticationHandler);
  }

  public static final String STORE_GROUP_ID = "BABYSHOP";

  @Test
  void whenCreate_returnBrinkPriceAddon() throws IOException, InterruptedException {
    final BrinkPriceAddon priceGroup = mockPriceAddon();

    final HttpResponse<String> httpResponse = mockHttpResponse(priceGroup, 200);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThat(priceApi.create(priceGroup)).isEqualTo(priceGroup);

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo(
                          "http://mockserver.com/price/store-groups/BABYSHOP/markets/SE/addons/123654_100/price");
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
  void whenCreate_returnErrorStatusCode(final int statusCode)
      throws IOException, InterruptedException {
    final BrinkPriceAddon priceGroup = mockPriceAddon();

    final BrinkHttpErrorMessage errorMessage = new BrinkHttpErrorMessage("0", "Error");
    final HttpResponse<String> httpResponse = mockHttpResponse(errorMessage, statusCode);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatThrownBy(() -> priceApi.create(priceGroup))
        .isInstanceOf(BrinkPriceException.class)
        .hasMessageContaining(String.format("Http code: %d", statusCode));
  }

  @Test
  void whenCreate_throwsRuntime() throws IOException, InterruptedException {
    final BrinkPriceAddon priceGroup = mockPriceAddon();

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class)))
        .thenThrow(RuntimeException.class);

    assertThatThrownBy(() -> priceApi.create(priceGroup)).isInstanceOf(BrinkPriceException.class);
  }

  @Test
  void whenCreateOnNullArgument_throwsNullPointer() {
    final BrinkPriceAddon priceGroup = null;

    assertThatThrownBy(() -> priceApi.create(priceGroup)).isInstanceOf(NullPointerException.class);
  }

  // -----------------------------------------------------------------------------------------------

  @Test
  void whenGet_returnBrinkPriceAddon() throws IOException, InterruptedException {

    final BrinkPriceAddonRequest request = getPriceAddonRequest();
    final BrinkPriceAddon responsePriceAddon = mockPriceAddon();
    final HttpResponse<String> httpResponse = mockHttpResponse(responsePriceAddon, 200);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThat(priceApi.get(request)).isEqualTo(responsePriceAddon);

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo(
                          "http://mockserver.com/price/store-groups/BABYSHOP/markets/SE/addons/123654_100/price");
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
  void whenGet_returnErrorStatusCode(final int statusCode)
      throws IOException, InterruptedException {

    final BrinkPriceAddonRequest request = getPriceAddonRequest();
    final BrinkHttpErrorMessage errorMessage = new BrinkHttpErrorMessage("0", "Error");
    final HttpResponse<String> httpResponse = mockHttpResponse(errorMessage, statusCode);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatThrownBy(() -> priceApi.get(request))
        .isInstanceOf(BrinkPriceException.class)
        .hasMessageContaining(String.format("Http code: %d", statusCode));
  }

  @Test
  void whenGet_throwsRuntime() throws IOException, InterruptedException {
    final BrinkPriceAddonRequest request = getPriceAddonRequest();

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class)))
        .thenThrow(RuntimeException.class);

    assertThatThrownBy(() -> priceApi.get(request)).isInstanceOf(BrinkPriceException.class);
  }

  @Test
  void whenGetOnNullArgument_throwsNullPointer() {
    final BrinkPriceAddonRequest request = null;

    assertThatThrownBy(() -> priceApi.get(request)).isInstanceOf(NullPointerException.class);
  }

  // -----------------------------------------------------------------------------------------------

  @Test
  void whenDelete_returnNoContent() throws IOException, InterruptedException {

    final BrinkPriceAddonRequest request = getPriceAddonRequest();
    final HttpResponse<String> httpResponse = mockHttpResponse("", 204);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatCode(() -> priceApi.delete(request)).doesNotThrowAnyException();

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo(
                          "http://mockserver.com/price/store-groups/BABYSHOP/markets/SE/addons/123654_100/price");
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
  void whenDelete_returnErrorStatusCode(final int statusCode)
      throws IOException, InterruptedException {
    final BrinkPriceAddonRequest request = getPriceAddonRequest();
    final BrinkHttpErrorMessage errorMessage = new BrinkHttpErrorMessage("0", "Error");
    final HttpResponse<String> httpResponse = mockHttpResponse(errorMessage, statusCode);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatThrownBy(() -> priceApi.delete(request))
        .isInstanceOf(BrinkPriceException.class)
        .hasMessageContaining(String.format("Http code: %d", statusCode));
  }

  @Test
  void whenDelete_throwsRuntime() throws IOException, InterruptedException {
    final BrinkPriceAddonRequest request = getPriceAddonRequest();

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class)))
        .thenThrow(RuntimeException.class);

    assertThatThrownBy(() -> priceApi.delete(request)).isInstanceOf(BrinkPriceException.class);
  }

  @Test
  void whenDeleteOnNullArgument_throwsNullPointer() {
    final BrinkPriceAddonRequest request = null;

    assertThatThrownBy(() -> priceApi.delete(request)).isInstanceOf(NullPointerException.class);
  }
}
