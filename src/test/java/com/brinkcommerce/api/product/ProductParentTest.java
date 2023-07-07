package com.brinkcommerce.api.product;

import static com.brinkcommerce.api.util.Mocks.createProductParentResponse;
import static com.brinkcommerce.api.util.Mocks.createProductVariantResponse;
import static com.brinkcommerce.api.util.Mocks.mockProductParent;
import static com.brinkcommerce.api.util.Mocks.mockProductParentAddon;
import static com.brinkcommerce.api.util.Mocks.mockProductVariant;
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
import com.brinkcommerce.api.management.product.parent.BrinkProductParent;
import com.brinkcommerce.api.management.product.parent.BrinkProductParentAddon;
import com.brinkcommerce.api.management.product.parent.BrinkProductParentApi;
import com.brinkcommerce.api.management.product.parent.BrinkResponseWrapper;
import com.brinkcommerce.api.management.product.variant.BrinkProductVariant;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductParentTest {

  private BrinkProductParentApi productApi;

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

    productApi = BrinkProductParentApi.init(brinkConfiguration, authenticationHandler);
  }

  @Test
  void whenCreate_returnBrinkProductParent() throws IOException, InterruptedException {
    final BrinkProductParent productParent = mockProductParent();

    final BrinkProductParent responseProductParent = createProductParentResponse(productParent);

    final HttpResponse<String> httpResponse = mockHttpResponse(responseProductParent, 200);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThat(productApi.create(productParent)).isEqualTo(responseProductParent);

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo("http://mockserver.com/product/product-parents/123543");
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
    final BrinkProductParent productParent = mockProductParent();

    final BrinkHttpErrorMessage errorMessage = new BrinkHttpErrorMessage("0", "Error");
    final HttpResponse<String> httpResponse = mockHttpResponse(errorMessage, statusCode);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatThrownBy(() -> productApi.create(productParent))
        .isInstanceOf(BrinkProductException.class)
        .hasMessageContaining(String.format("Http code: %d", statusCode));
  }

  @Test
  void whenCreate_throwsRuntime() throws IOException, InterruptedException {
    final BrinkProductParent productParent = mockProductParent();

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class)))
        .thenThrow(RuntimeException.class);

    assertThatThrownBy(() -> productApi.create(productParent))
        .isInstanceOf(BrinkProductException.class);
  }

  @Test
  void whenCreateOnNullArgument_throwsNullPointer() {
    final BrinkProductParent productParent = null;

    assertThatThrownBy(() -> productApi.create(productParent))
        .isInstanceOf(NullPointerException.class);
  }
  // -----------------------------------------------------------------------------------------------

  @Test
  void whenUpdate_returnBrinkProductParent() throws IOException, InterruptedException {
    final BrinkProductParent updatedProductParent =
        BrinkProductParent.builder()
            .withId("123543")
            .withCustomAttributes(Map.of())
            .withDescription("The best bunny")
            .withDisplayDescriptions(Map.of())
            .withDisplayNames(Map.of())
            .withImageUrl("http://www.google.com")
            .withIsActive(true)
            .withName("Fluffy Bunny")
            .withSlug("/bunny")
            .withTags(Map.of())
            .withDescription("Babyshop for now.")
            .build();

    final BrinkProductParent responseProductParent =
        createProductParentResponse(updatedProductParent);

    final HttpResponse<String> httpResponse = mockHttpResponse(responseProductParent, 200);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThat(productApi.update(updatedProductParent)).isEqualTo(responseProductParent);

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo("http://mockserver.com/product/product-parents/123543");
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
    final BrinkProductParent productParent = mockProductParent();

    final BrinkHttpErrorMessage errorMessage = new BrinkHttpErrorMessage("0", "Error");
    final HttpResponse<String> httpResponse = mockHttpResponse(errorMessage, statusCode);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatThrownBy(() -> productApi.update(productParent))
        .isInstanceOf(BrinkProductException.class)
        .hasMessageContaining(String.format("Http code: %d", statusCode));
  }

  @Test
  void whenUpdate_throwsRuntime() throws IOException, InterruptedException {
    final BrinkProductParent productParent = mockProductParent();

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class)))
        .thenThrow(RuntimeException.class);

    assertThatThrownBy(() -> productApi.update(productParent))
        .isInstanceOf(BrinkProductException.class);
  }

  @Test
  void whenUpdateOnNullArgument_throwsNullPointer() {
    final BrinkProductParent productParent = null;

    assertThatThrownBy(() -> productApi.update(productParent))
        .isInstanceOf(NullPointerException.class);
  }
  // -----------------------------------------------------------------------------------------------

  @Test
  void whenGet_returnBrinkProductParent() throws IOException, InterruptedException {

    final String productParentId = "123543";
    final BrinkProductParent responseProductParent =
        createProductParentResponse(mockProductParent());

    final HttpResponse<String> httpResponse = mockHttpResponse(responseProductParent, 200);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThat(productApi.get(productParentId)).isEqualTo(responseProductParent);

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo("http://mockserver.com/product/product-parents/123543");
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
    final String productParentId = "123543";

    final BrinkHttpErrorMessage errorMessage = new BrinkHttpErrorMessage("0", "Error");
    final HttpResponse<String> httpResponse = mockHttpResponse(errorMessage, statusCode);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatThrownBy(() -> productApi.get(productParentId))
        .isInstanceOf(BrinkProductException.class)
        .hasMessageContaining(String.format("Http code: %d", statusCode));
  }

  @Test
  void whenGet_throwsRuntime() throws IOException, InterruptedException {
    final String productParentId = "123543";

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class)))
        .thenThrow(RuntimeException.class);

    assertThatThrownBy(() -> productApi.get(productParentId))
        .isInstanceOf(BrinkProductException.class);
  }

  @Test
  void whenGetOnNullArgument_throwsNullPointer() {
    final String productParentId = null;

    assertThatThrownBy(() -> productApi.get(productParentId))
        .isInstanceOf(NullPointerException.class);
  }

  // -----------------------------------------------------------------------------------------------

  @Test
  void whenDelete_returnNoContent() throws IOException, InterruptedException {

    final String productParentId = "123543";

    final HttpResponse<String> httpResponse = mockHttpResponse("", 204);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatCode(() -> productApi.delete(productParentId)).doesNotThrowAnyException();

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo("http://mockserver.com/product/product-parents/123543");
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
    final String productParentId = "123543";
    final BrinkHttpErrorMessage errorMessage = new BrinkHttpErrorMessage("0", "Error");
    final HttpResponse<String> httpResponse = mockHttpResponse(errorMessage, statusCode);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatThrownBy(() -> productApi.delete(productParentId))
        .isInstanceOf(BrinkProductException.class)
        .hasMessageContaining(String.format("Http code: %d", statusCode));
  }

  @Test
  void whenDelete_throwsRuntime() throws IOException, InterruptedException {
    final String productParentId = "123543";

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class)))
        .thenThrow(RuntimeException.class);

    assertThatThrownBy(() -> productApi.delete(productParentId))
        .isInstanceOf(BrinkProductException.class);
  }

  @Test
  void whenDeleteOnNullArgument_throwsNullPointer() {
    final String productParentId = null;

    assertThatThrownBy(() -> productApi.delete(productParentId))
        .isInstanceOf(NullPointerException.class);
  }

  // -----------------------------------------------------------------------------------------------

  @Test
  void whenGetProductVariants_returnProductVariantsList() throws IOException, InterruptedException {

    final String productParentId = "123654";
    final List<BrinkProductVariant> updatedProductVariant =
        List.of(mockProductVariant(), mockProductVariant());
    record ResponseWrapper(List<BrinkProductVariant> productVariants) {

    }

    final List<BrinkProductVariant> responseProductVariant =
        createProductVariantResponse(updatedProductVariant);
    final ResponseWrapper wrappedResponse = new ResponseWrapper(responseProductVariant);

    final HttpResponse<String> httpResponse = mockHttpResponse(wrappedResponse, 200);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThat(productApi.getProductVariants(productParentId)).isEqualTo(responseProductVariant);

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo("http://mockserver.com/product/product-parents/123654/variants");
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
  void whenGetAll_returnErrorStatusCode(final int statusCode)
      throws IOException, InterruptedException {
    final String productParentId = "123654";

    final BrinkHttpErrorMessage errorMessage = new BrinkHttpErrorMessage("0", "Error");
    final HttpResponse<String> httpResponse = mockHttpResponse(errorMessage, statusCode);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatThrownBy(() -> productApi.getProductVariants(productParentId))
        .isInstanceOf(BrinkProductException.class)
        .hasMessageContaining(String.format("Http code: %d", statusCode));
  }

  @Test
  void whenGetAll_throwsRuntime() throws IOException, InterruptedException {
    final String productParentId = "123654";

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class)))
        .thenThrow(RuntimeException.class);

    assertThatThrownBy(() -> productApi.getProductVariants(productParentId))
        .isInstanceOf(BrinkProductException.class);
  }

  @Test
  void whenGetAllOnNullArgument_throwsNullPointer() {
    final String productParentId = null;

    assertThatThrownBy(() -> productApi.getProductVariants(productParentId))
        .isInstanceOf(NullPointerException.class);
  }

  // -----------------------------------------------------------------------------------------------

  @Test
  void whenGetAddons_returnProductAddonsList() throws IOException, InterruptedException {

    final String productParentId = "parent-1";
    final List<BrinkProductParentAddon> updatedProductParentAddon =
        List.of(mockProductParentAddon(), mockProductParentAddon());
    record ResponseWrapper(List<BrinkProductParentAddon> productAddons) {

    }

    final ResponseWrapper wrappedResponse = new ResponseWrapper(updatedProductParentAddon);

    final HttpResponse<String> httpResponse = mockHttpResponse(wrappedResponse, 200);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThat(productApi.getAddons(productParentId)).isEqualTo(updatedProductParentAddon);

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo("http://mockserver.com/product/product-parents/parent-1/addons");
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
  void whenGetAddons_returnErrorStatusCode(final int statusCode)
      throws IOException, InterruptedException {
    final String productParentId = "parent-1";

    final BrinkHttpErrorMessage errorMessage = new BrinkHttpErrorMessage("0", "Error");
    final HttpResponse<String> httpResponse = mockHttpResponse(errorMessage, statusCode);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatThrownBy(() -> productApi.getAddons(productParentId))
        .isInstanceOf(BrinkProductException.class)
        .hasMessageContaining(String.format("Http code: %d", statusCode));
  }

  @Test
  void whenGetAddons_throwsRuntime() throws IOException, InterruptedException {
    final String productParentId = "parent-1";

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class)))
        .thenThrow(RuntimeException.class);

    assertThatThrownBy(() -> productApi.getAddons(productParentId))
        .isInstanceOf(BrinkProductException.class);
  }

  @Test
  void whenGetAddonsOnNullArgument_throwsNullPointer() {
    final String productParentId = null;

    assertThatThrownBy(() -> productApi.getAddons(productParentId))
        .isInstanceOf(NullPointerException.class);
  }

  // -----------------------------------------------------------------------------------------------

  @Test
  void whenAddAddon_returnBrinkResponseWrapper() throws IOException, InterruptedException {
    final String addonId = "addon-1";
    final String parentProductId = "parent-1";
    final BrinkResponseWrapper responseWrapper = new BrinkResponseWrapper(addonId, parentProductId);

    final HttpResponse<String> httpResponse = mockHttpResponse(responseWrapper, 200);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThat(productApi.addAddon(parentProductId, addonId)).isEqualTo(responseWrapper);

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo("http://mockserver.com/product/product-parents/parent-1/addons");
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
  void whenAddAddon_returnErrorStatusCode(final int statusCode)
      throws IOException, InterruptedException {
    final String addonId = "addon-1";
    final String parentProductId = "parent-1";

    final BrinkHttpErrorMessage errorMessage = new BrinkHttpErrorMessage("0", "Error");
    final HttpResponse<String> httpResponse = mockHttpResponse(errorMessage, statusCode);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatThrownBy(() -> productApi.addAddon(parentProductId, addonId))
        .isInstanceOf(BrinkProductException.class)
        .hasMessageContaining(String.format("Http code: %d", statusCode));
  }

  @Test
  void whenAddAddon_throwsRuntime() throws IOException, InterruptedException {
    final String addonId = "addon-1";
    final String parentProductId = "parent-1";

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class)))
        .thenThrow(RuntimeException.class);

    assertThatThrownBy(() -> productApi.addAddon(parentProductId, addonId))
        .isInstanceOf(BrinkProductException.class);
  }

  @Test
  void whenAddAddonOnNullArgument_throwsNullPointer() {
    final String addonId = "addon-1";
    final String parentProductId = null;

    assertThatThrownBy(() -> productApi.addAddon(parentProductId, addonId))
        .isInstanceOf(NullPointerException.class);
  }

  // -----------------------------------------------------------------------------------------------

  @Test
  void whenDeleteProductParentAddons_returnNoContent() throws IOException, InterruptedException {
    final String addonId = "addon-1";
    final String parentProductId = "parent-1";

    final HttpResponse<String> httpResponse = mockHttpResponse("", 204);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatCode(() -> productApi.deleteProductParentAddons(parentProductId, addonId))
        .doesNotThrowAnyException();

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo(
                          "http://mockserver.com/product/product-parents/parent-1/addons/addon-1");
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
  void whenDeleteProductParentAddons_returnErrorStatusCode(final int statusCode)
      throws IOException, InterruptedException {
    final String addonId = "addon-1";
    final String parentProductId = "parent-1";
    final BrinkHttpErrorMessage errorMessage = new BrinkHttpErrorMessage("0", "Error");
    final HttpResponse<String> httpResponse = mockHttpResponse(errorMessage, statusCode);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatThrownBy(() -> productApi.deleteProductParentAddons(parentProductId, addonId))
        .isInstanceOf(BrinkProductException.class)
        .hasMessageContaining(String.format("Http code: %d", statusCode));
  }

  @Test
  void whenDeleteProductParentAddons_throwsRuntime() throws IOException, InterruptedException {
    final String addonId = "addon-1";
    final String parentProductId = "parent-1";

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class)))
        .thenThrow(RuntimeException.class);

    assertThatThrownBy(() -> productApi.deleteProductParentAddons(parentProductId, addonId))
        .isInstanceOf(BrinkProductException.class);
  }

  @Test
  void whenDeleteProductParentAddonsOnNullArgument_throwsNullPointer() {
    final String addonId = "addon-1";
    final String parentProductId = null;

    assertThatThrownBy(() -> productApi.deleteProductParentAddons(parentProductId, addonId))
        .isInstanceOf(NullPointerException.class);
  }

  // -----------------------------------------------------------------------------------------------

  @Test
  void whenArchiveProductParent_returnNoContent() throws IOException, InterruptedException {
    final String productParentId = "parent-1";
    final Boolean includeProductVariants = true;

    final HttpResponse<String> httpResponse = mockHttpResponse("", 204);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatCode(
        () -> productApi.archiveBrinkProductParent(productParentId, includeProductVariants))
        .doesNotThrowAnyException();

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo("http://mockserver.com/product/product-parents/parent-1/archive");
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
  void whenArchiveProductParent_returnErrorStatusCode(final int statusCode)
      throws IOException, InterruptedException {
    final BrinkProductParent productParent = mockProductParent();

    final BrinkHttpErrorMessage errorMessage = new BrinkHttpErrorMessage("0", "Error");
    final HttpResponse<String> httpResponse = mockHttpResponse(errorMessage, statusCode);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatThrownBy(() -> productApi.create(productParent))
        .isInstanceOf(BrinkProductException.class)
        .hasMessageContaining(String.format("Http code: %d", statusCode));
  }

  @Test
  void whenArchiveProductParent_throwsRuntime() throws IOException, InterruptedException {
    final BrinkProductParent productParent = mockProductParent();

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class)))
        .thenThrow(RuntimeException.class);

    assertThatThrownBy(() -> productApi.create(productParent))
        .isInstanceOf(BrinkProductException.class);
  }

  @Test
  void whenArchiveProductParentOnNullArgument_throwsNullPointer() {
    final BrinkProductParent productParent = null;

    assertThatThrownBy(() -> productApi.create(productParent))
        .isInstanceOf(NullPointerException.class);
  }
}
