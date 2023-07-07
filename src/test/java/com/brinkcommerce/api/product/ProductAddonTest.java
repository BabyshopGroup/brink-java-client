package com.brinkcommerce.api.product;

import static com.brinkcommerce.api.util.Mocks.createProductAddonResponse;
import static com.brinkcommerce.api.util.Mocks.mockProductAddon;
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
import com.brinkcommerce.api.management.product.BrinkProductException;
import com.brinkcommerce.api.management.product.addon.BrinkProductAddon;
import com.brinkcommerce.api.management.product.addon.BrinkProductAddonApi;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductAddonTest {

  private BrinkProductAddonApi productApi;

  @Mock
  HttpClient httpClient;

  @Mock
  AuthenticationHandler authenticationHandler;

  @BeforeEach
  void init() {
    final BrinkAuthentication authenticationConfiguration =
        mockAuthenticationConfiguration();

    final ManagementConfiguration brinkConfiguration =
        mockBrinkConfiguration(authenticationConfiguration, httpClient);

    productApi = BrinkProductAddonApi.init(brinkConfiguration, authenticationHandler);
  }

  @Test
  void whenCreate_returnBrinkProductAddon() throws IOException, InterruptedException {
    final BrinkProductAddon productAddon = mockProductAddon();

    final BrinkProductAddon responseProductAddon = createProductAddonResponse(productAddon);

    final HttpResponse<String> httpResponse = mockHttpResponse(responseProductAddon, 200);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThat(productApi.create(productAddon)).isEqualTo(responseProductAddon);

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo("http://mockserver.com/product/addons/addon-01");
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
    final BrinkProductAddon productAddon = mockProductAddon();

    final BrinkHttpErrorMessage errorMessage = new BrinkHttpErrorMessage("0", "Error");
    final HttpResponse<String> httpResponse = mockHttpResponse(errorMessage, statusCode);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatThrownBy(() -> productApi.create(productAddon))
        .isInstanceOf(BrinkProductException.class)
        .hasMessageContaining(String.format("Http code: %d", statusCode));
  }

  @Test
  void whenCreate_throwsRuntime() throws IOException, InterruptedException {
    final BrinkProductAddon productAddon = mockProductAddon();

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class)))
        .thenThrow(RuntimeException.class);

    assertThatThrownBy(() -> productApi.create(productAddon))
        .isInstanceOf(BrinkProductException.class);
  }

  @Test
  void whenCreateOnNullArgument_throwsNullPointer() {
    final BrinkProductAddon productAddon = null;

    assertThatThrownBy(() -> productApi.create(productAddon))
        .isInstanceOf(NullPointerException.class);
  }

  // -----------------------------------------------------------------------------------------------

  @Test
  void whenUpdate_returnBrinkProductAddon() throws IOException, InterruptedException {
    final BrinkProductAddon updatedProductAddon =
        BrinkProductAddon.builder()
            .withId("addon-01")
            .withName("Addon 1")
            .withImageUrl("http:www.google.se")
            .withTaxGroupId("BABYTAX")
            .withTags(Map.of())
            .withDisplayNames(Map.of())
            .withDisplayDescriptions(Map.of())
            .withDescription("Super Addon 1")
            .withCustomAttributes(Map.of())
            .withIsActive(true)
            .withDescription("Babyshop for now.")
            .build();

    final BrinkProductAddon responseProductAddon = createProductAddonResponse(updatedProductAddon);

    final HttpResponse<String> httpResponse = mockHttpResponse(responseProductAddon, 200);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThat(productApi.update(updatedProductAddon)).isEqualTo(responseProductAddon);

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo("http://mockserver.com/product/addons/addon-01");
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
    final BrinkProductAddon productAddon = mockProductAddon();

    final BrinkHttpErrorMessage errorMessage = new BrinkHttpErrorMessage("0", "Error");
    final HttpResponse<String> httpResponse = mockHttpResponse(errorMessage, statusCode);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatThrownBy(() -> productApi.update(productAddon))
        .isInstanceOf(BrinkProductException.class)
        .hasMessageContaining(String.format("Http code: %d", statusCode));
  }

  @Test
  void whenUpdate_throwsRuntime() throws IOException, InterruptedException {
    final BrinkProductAddon productAddon = mockProductAddon();

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class)))
        .thenThrow(RuntimeException.class);

    assertThatThrownBy(() -> productApi.update(productAddon))
        .isInstanceOf(BrinkProductException.class);
  }

  @Test
  void whenUpdateOnNullArgument_throwsNullPointer() {
    final BrinkProductAddon productAddon = null;

    assertThatThrownBy(() -> productApi.update(productAddon))
        .isInstanceOf(NullPointerException.class);
  }
  // -----------------------------------------------------------------------------------------------

  @Test
  void whenDelete_returnNoContent() throws IOException, InterruptedException {

    final String productAddonId = "addon-01";

    final HttpResponse<String> httpResponse = mockHttpResponse("", 204);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatCode(() -> productApi.delete(productAddonId)).doesNotThrowAnyException();

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo("http://mockserver.com/product/addons/addon-01");
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
    final String productAddonId = "addon-01";
    final BrinkHttpErrorMessage errorMessage = new BrinkHttpErrorMessage("0", "Error");
    final HttpResponse<String> httpResponse = mockHttpResponse(errorMessage, statusCode);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatThrownBy(() -> productApi.delete(productAddonId))
        .isInstanceOf(BrinkProductException.class)
        .hasMessageContaining(String.format("Http code: %d", statusCode));
  }

  @Test
  void whenDelete_throwsRuntime() throws IOException, InterruptedException {
    final String productAddonId = "addon-01";

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class)))
        .thenThrow(RuntimeException.class);

    assertThatThrownBy(() -> productApi.delete(productAddonId))
        .isInstanceOf(BrinkProductException.class);
  }

  @Test
  void whenDeleteOnNullArgument_throwsNullPointer() {
    final String productAddonId = null;

    assertThatThrownBy(() -> productApi.delete(productAddonId))
        .isInstanceOf(NullPointerException.class);
  }
}
