package com.brinkcommerce.api.price;

import static com.brinkcommerce.api.util.Mocks.getPriceVariantMarket;
import static com.brinkcommerce.api.util.Mocks.getPriceVariantRequest;
import static com.brinkcommerce.api.util.Mocks.mockPriceVariantList;
import static com.brinkcommerce.api.util.Mocks.mockPriceVariantListResponse;
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
import com.brinkcommerce.api.management.price.variant.BrinkPriceVariantApi;
import com.brinkcommerce.api.management.price.variant.model.BrinkPriceVariantListRequest;
import com.brinkcommerce.api.management.price.variant.model.BrinkPriceVariantListResponse;
import com.brinkcommerce.api.management.price.variant.model.BrinkPriceVariantDeleteRequest;
import com.brinkcommerce.api.management.price.variant.model.BrinkPriceVariantRequest;
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
public class PriceVariantTest {

  private BrinkPriceVariantApi priceApi;

  @Mock HttpClient httpClient;

  @Mock AuthenticationHandler authenticationHandler;

  @BeforeEach
  void init() {
    final BrinkAuthentication authenticationConfiguration =
        mockAuthenticationConfiguration();

    final ManagementConfiguration brinkConfiguration =
        mockBrinkConfiguration(authenticationConfiguration, httpClient);

    priceApi = BrinkPriceVariantApi.init(brinkConfiguration, authenticationHandler);
  }

  public static final String STORE_GROUP_ID = "BABYSHOP";

  @Test
  void whenCreate_returnBrinkPriceVariant() throws IOException, InterruptedException {
    final BrinkPriceVariantListRequest priceGroup = mockPriceVariantList();

    final HttpResponse<String> httpResponse = mockHttpResponse(priceGroup, 200);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThat(priceApi.create(priceGroup)).isEqualTo(mockPriceVariantListResponse());

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo(
                          "http://mockserver.com/price/store-groups/BABYSHOP/product-variants/123654_100/prices");
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
    final BrinkPriceVariantListRequest priceGroup = mockPriceVariantList();

    final BrinkHttpErrorMessage errorMessage = new BrinkHttpErrorMessage("0", "Error");
    final HttpResponse<String> httpResponse = mockHttpResponse(errorMessage, statusCode);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatThrownBy(() -> priceApi.create(priceGroup))
        .isInstanceOf(BrinkPriceException.class)
        .hasMessageContaining(String.format("Http code: %d", statusCode));
  }

  @Test
  void whenCreate_throwsRuntime() throws IOException, InterruptedException {
    final BrinkPriceVariantListRequest priceGroup = mockPriceVariantList();

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class)))
        .thenThrow(RuntimeException.class);

    assertThatThrownBy(() -> priceApi.create(priceGroup)).isInstanceOf(BrinkPriceException.class);
  }

  @Test
  void whenCreateOnNullArgument_throwsNullPointer() {
    final BrinkPriceVariantListRequest priceGroup = null;

    assertThatThrownBy(() -> priceApi.create(priceGroup)).isInstanceOf(NullPointerException.class);
  }

  // -----------------------------------------------------------------------------------------------

  @Test
  void whenGet_returnBrinkPriceVariant() throws IOException, InterruptedException {

    final BrinkPriceVariantRequest request = getPriceVariantRequest();
    final BrinkPriceVariantListResponse responsePriceVariant = mockPriceVariantListResponse();
    final HttpResponse<String> httpResponse = mockHttpResponse(responsePriceVariant, 200);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThat(priceApi.get(request)).isEqualTo(responsePriceVariant);

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo(
                          "http://mockserver.com/price/store-groups/BABYSHOP/product-variants/123654_100/prices");
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

    final BrinkPriceVariantRequest request = getPriceVariantRequest();
    final BrinkHttpErrorMessage errorMessage = new BrinkHttpErrorMessage("0", "Error");
    final HttpResponse<String> httpResponse = mockHttpResponse(errorMessage, statusCode);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatThrownBy(() -> priceApi.get(request))
        .isInstanceOf(BrinkPriceException.class)
        .hasMessageContaining(String.format("Http code: %d", statusCode));
  }

  @Test
  void whenGet_throwsRuntime() throws IOException, InterruptedException {
    final BrinkPriceVariantRequest request = getPriceVariantRequest();

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class)))
        .thenThrow(RuntimeException.class);

    assertThatThrownBy(() -> priceApi.get(request)).isInstanceOf(BrinkPriceException.class);
  }

  @Test
  void whenGetOnNullArgument_throwsNullPointer() {
    final BrinkPriceVariantRequest request = null;

    assertThatThrownBy(() -> priceApi.get(request)).isInstanceOf(NullPointerException.class);
  }

  // -----------------------------------------------------------------------------------------------

  @Test
  void whenDelete_returnNoContent() throws IOException, InterruptedException {

    final BrinkPriceVariantDeleteRequest request = getPriceVariantMarket();
    final HttpResponse<String> httpResponse = mockHttpResponse("", 204);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatCode(() -> priceApi.delete(request)).doesNotThrowAnyException();

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo(
                          "http://mockserver.com/price/store-groups/BABYSHOP/markets/SE/product-variants/123654_100/price");
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
    final BrinkPriceVariantDeleteRequest request = getPriceVariantMarket();
    final BrinkHttpErrorMessage errorMessage = new BrinkHttpErrorMessage("0", "Error");
    final HttpResponse<String> httpResponse = mockHttpResponse(errorMessage, statusCode);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatThrownBy(() -> priceApi.delete(request))
        .isInstanceOf(BrinkPriceException.class)
        .hasMessageContaining(String.format("Http code: %d", statusCode));
  }

  @Test
  void whenDelete_throwsRuntime() throws IOException, InterruptedException {
    final BrinkPriceVariantDeleteRequest request = getPriceVariantMarket();

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class)))
        .thenThrow(RuntimeException.class);

    assertThatThrownBy(() -> priceApi.delete(request)).isInstanceOf(BrinkPriceException.class);
  }

  @Test
  void whenDeleteOnNullArgument_throwsNullPointer() {
    final BrinkPriceVariantDeleteRequest request = null;

    assertThatThrownBy(() -> priceApi.delete(request)).isInstanceOf(NullPointerException.class);
  }
}
