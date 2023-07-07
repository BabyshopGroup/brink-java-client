package com.brinkcommerce.api.product;

import static com.brinkcommerce.api.util.Mocks.createProductVariantResponse;
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
import com.brinkcommerce.api.management.product.variant.BrinkDimensions;
import com.brinkcommerce.api.management.product.variant.BrinkProductVariant;
import com.brinkcommerce.api.management.product.variant.BrinkProductVariantApi;
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
public class ProductVariantTest {

  private BrinkProductVariantApi productApi;

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

    productApi = BrinkProductVariantApi.init(brinkConfiguration, authenticationHandler);
  }

  @Test
  void whenCreate_returnBrinkProductVariant() throws IOException, InterruptedException {
    final BrinkProductVariant productVariant = mockProductVariant();

    final BrinkProductVariant responseProductVariant = createProductVariantResponse(productVariant);

    final HttpResponse<String> httpResponse = mockHttpResponse(responseProductVariant, 200);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThat(productApi.create(productVariant)).isEqualTo(responseProductVariant);

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo("http://mockserver.com/product/product-variants/123543_100");
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
    final BrinkProductVariant productVariant = mockProductVariant();

    final BrinkHttpErrorMessage errorMessage = new BrinkHttpErrorMessage("0", "Error");
    final HttpResponse<String> httpResponse = mockHttpResponse(errorMessage, statusCode);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatThrownBy(() -> productApi.create(productVariant))
        .isInstanceOf(BrinkProductException.class)
        .hasMessageContaining(String.format("Http code: %d", statusCode));
  }

  @Test
  void whenCreate_throwsRuntime() throws IOException, InterruptedException {
    final BrinkProductVariant productVariant = mockProductVariant();

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class)))
        .thenThrow(RuntimeException.class);

    assertThatThrownBy(() -> productApi.create(productVariant))
        .isInstanceOf(BrinkProductException.class);
  }

  @Test
  void whenCreateOnNullArgument_throwsNullPointer() {
    final BrinkProductVariant productVariant = null;

    assertThatThrownBy(() -> productApi.create(productVariant))
        .isInstanceOf(NullPointerException.class);
  }
  // -----------------------------------------------------------------------------------------------

  @Test
  void whenUpdate_returnBrinkProductVariant() throws IOException, InterruptedException {
    final BrinkProductVariant updatedProductVariant =
        BrinkProductVariant.builder()
            .withId("123543_100")
            .withCustomAttributes(Map.of())
            .withDescription("Size 100")
            .withProductParentId("123543")
            .withDisplayDescriptions(Map.of())
            .withDisplayNames(Map.of())
            .withImageUrl("http://www.google.com")
            .withIsActive(true)
            .withName("FluffyBunny 100")
            .withSlug("/Fluffy100")
            .withTags(Map.of())
            .withTaxGroupId("BABYTAX")
            .withWeight(55)
            .withDimensions(new BrinkDimensions(55, 55, 55))
            .withDescription("Babyshop for now.")
            .build();

    final BrinkProductVariant responseProductVariant =
        createProductVariantResponse(updatedProductVariant);

    final HttpResponse<String> httpResponse = mockHttpResponse(responseProductVariant, 200);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThat(productApi.update(updatedProductVariant)).isEqualTo(responseProductVariant);

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo("http://mockserver.com/product/product-variants/123543_100");
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
    final BrinkProductVariant productVariant = mockProductVariant();

    final BrinkHttpErrorMessage errorMessage = new BrinkHttpErrorMessage("0", "Error");
    final HttpResponse<String> httpResponse = mockHttpResponse(errorMessage, statusCode);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatThrownBy(() -> productApi.update(productVariant))
        .isInstanceOf(BrinkProductException.class)
        .hasMessageContaining(String.format("Http code: %d", statusCode));
  }

  @Test
  void whenUpdate_throwsRuntime() throws IOException, InterruptedException {
    final BrinkProductVariant productVariant = mockProductVariant();

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class)))
        .thenThrow(RuntimeException.class);

    assertThatThrownBy(() -> productApi.update(productVariant))
        .isInstanceOf(BrinkProductException.class);
  }

  @Test
  void whenUpdateOnNullArgument_throwsNullPointer() {
    final BrinkProductVariant productVariant = null;

    assertThatThrownBy(() -> productApi.update(productVariant))
        .isInstanceOf(NullPointerException.class);
  }
  // -----------------------------------------------------------------------------------------------

  @Test
  void whenDelete_returnNoContent() throws IOException, InterruptedException {

    final String productVariantId = "123543_100";

    final HttpResponse<String> httpResponse = mockHttpResponse("", 204);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatCode(() -> productApi.delete(productVariantId)).doesNotThrowAnyException();

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo("http://mockserver.com/product/product-variants/123543_100");
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
    final String productVariantId = "123543_100";
    final BrinkHttpErrorMessage errorMessage = new BrinkHttpErrorMessage("0", "Error");
    final HttpResponse<String> httpResponse = mockHttpResponse(errorMessage, statusCode);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatThrownBy(() -> productApi.delete(productVariantId))
        .isInstanceOf(BrinkProductException.class)
        .hasMessageContaining(String.format("Http code: %d", statusCode));
  }

  @Test
  void whenDelete_throwsRuntime() throws IOException, InterruptedException {
    final String productVariantId = "123543_100";

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class)))
        .thenThrow(RuntimeException.class);

    assertThatThrownBy(() -> productApi.delete(productVariantId))
        .isInstanceOf(BrinkProductException.class);
  }

  @Test
  void whenDeleteOnNullArgument_throwsNullPointer() {
    final String productVariantId = null;

    assertThatThrownBy(() -> productApi.delete(productVariantId))
        .isInstanceOf(NullPointerException.class);
  }
}
