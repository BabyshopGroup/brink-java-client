package com.brinkcommerce.api.price;

import static com.brinkcommerce.api.util.Mocks.*;
import static com.brinkcommerce.api.util.TestUtils.mockAuthenticationConfiguration;
import static com.brinkcommerce.api.util.TestUtils.mockBrinkConfiguration;
import static com.brinkcommerce.api.util.TestUtils.mockHttpResponse;
import static org.assertj.core.api.Assertions.assertThat;
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
import com.brinkcommerce.api.management.price.variant.model.BrinkPriceVariantPatchRequest;
import com.brinkcommerce.api.management.price.variant.model.BrinkPriceVariantPutRequest;
import com.brinkcommerce.api.management.price.variant.model.BrinkPriceVariantListResponse;
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
    final BrinkPriceVariantPutRequest priceGroup = mockPriceVariantPutRequest();

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
    final BrinkPriceVariantPutRequest priceGroup = mockPriceVariantPutRequest();

    final BrinkHttpErrorMessage errorMessage = new BrinkHttpErrorMessage("0", "Error");
    final HttpResponse<String> httpResponse = mockHttpResponse(errorMessage, statusCode);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatThrownBy(() -> priceApi.create(priceGroup))
        .isInstanceOf(BrinkPriceException.class)
        .hasMessageContaining(String.format("Http code: %d", statusCode));
  }

  @Test
  void whenCreate_throwsRuntime() throws IOException, InterruptedException {
    final BrinkPriceVariantPutRequest priceGroup = mockPriceVariantPutRequest();

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class)))
        .thenThrow(RuntimeException.class);

    assertThatThrownBy(() -> priceApi.create(priceGroup)).isInstanceOf(BrinkPriceException.class);
  }

  @Test
  void whenCreateOnNullArgument_throwsNullPointer() {
    final BrinkPriceVariantPutRequest priceGroup = null;

    assertThatThrownBy(() -> priceApi.create(priceGroup)).isInstanceOf(NullPointerException.class);
  }

  // -----------------------------------------------------------------------------------------------
  @Test
  void whenUpdate_returnBrinkPriceVariant() throws IOException, InterruptedException {
    final BrinkPriceVariantPatchRequest priceGroup = mockPriceVariantPatchRequest();

    final HttpResponse<String> httpResponse = mockHttpResponse(mockPriceVariantListResponse(), 200);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThat(priceApi.update(priceGroup)).isEqualTo(mockPriceVariantListResponse());

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

  @Test
  void whenUpdate_returnEmptyBrinkPriceVariant() throws IOException, InterruptedException {
    final BrinkPriceVariantPatchRequest priceGroup = mockEmptyPriceVariantPatchRequest();

    final HttpResponse<String> httpResponse = mockHttpResponse(mockEmptyPriceVariantListResponse(), 200);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThat(priceApi.update(priceGroup)).isEqualTo(mockEmptyPriceVariantListResponse());

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
  void whenUpdate_returnErrorStatusCode(final int statusCode)
      throws IOException, InterruptedException {
    final BrinkPriceVariantPatchRequest priceGroup = mockPriceVariantPatchRequest();

    final BrinkHttpErrorMessage errorMessage = new BrinkHttpErrorMessage("0", "Error");
    final HttpResponse<String> httpResponse = mockHttpResponse(errorMessage, statusCode);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatThrownBy(() -> priceApi.update(priceGroup))
        .isInstanceOf(BrinkPriceException.class)
        .hasMessageContaining(String.format("Http code: %d", statusCode));
  }

  @Test
  void whenUpdate_throwsRuntime() throws IOException, InterruptedException {
    final BrinkPriceVariantPatchRequest priceGroup = mockPriceVariantPatchRequest();

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class)))
        .thenThrow(RuntimeException.class);

    assertThatThrownBy(() -> priceApi.update(priceGroup)).isInstanceOf(BrinkPriceException.class);
  }

  @Test
  void whenUpdateOnNullArgument_throwsNullPointer() {
    final BrinkPriceVariantPatchRequest priceGroup = null;

    assertThatThrownBy(() -> priceApi.update(priceGroup)).isInstanceOf(NullPointerException.class);
  }

  // ----------------------------------------------------------------------------------------------

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






}
