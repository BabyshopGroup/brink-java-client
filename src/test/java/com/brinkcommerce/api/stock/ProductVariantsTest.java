package com.brinkcommerce.api.stock;

import static com.brinkcommerce.api.util.Mocks.mockStockProduct;
import static com.brinkcommerce.api.util.Mocks.mockStockProductInventory;
import static com.brinkcommerce.api.util.Mocks.updateStockProductInventoryResponse;
import static com.brinkcommerce.api.util.Mocks.updateStockProductResponse;
import static com.brinkcommerce.api.util.TestUtils.mockAuthenticationConfiguration;
import static com.brinkcommerce.api.util.TestUtils.mockBrinkConfiguration;
import static com.brinkcommerce.api.util.TestUtils.mockHttpResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.brinkcommerce.api.authentication.AuthenticationHandler;
import com.brinkcommerce.api.configuration.BrinkAuthentication;
import com.brinkcommerce.api.configuration.ManagementConfiguration;
import com.brinkcommerce.api.management.stock.productvariants.BrinkVariantInventory;
import com.brinkcommerce.api.management.stock.productvariants.BrinkProductVariantStock;
import com.brinkcommerce.api.management.stock.productvariants.BrinkProductVariantsApi;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductVariantsTest {
  private BrinkProductVariantsApi productsApi;

  @Mock HttpClient httpClient;

  @Mock AuthenticationHandler authenticationHandler;

  @BeforeEach
  void init() {
    final BrinkAuthentication authenticationConfiguration =
        mockAuthenticationConfiguration();

    final ManagementConfiguration brinkConfiguration =
        mockBrinkConfiguration(authenticationConfiguration, httpClient);

    this.productsApi = BrinkProductVariantsApi.init(brinkConfiguration, authenticationHandler);
  }

  @Test
  void whenUpdateProduct_returnProduct() throws IOException, InterruptedException {
    final BrinkProductVariantStock product = mockStockProduct();

    final BrinkProductVariantStock responseProduct = updateStockProductResponse(product);

    final HttpResponse<String> httpResponse = mockHttpResponse(responseProduct, 200);

    when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
        .thenReturn(httpResponse);

    assertThat(this.productsApi.update(product)).isEqualTo(responseProduct);

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo(
                          String.format(
                              "http://mockserver.com/stock/product-variants/%s",
                              product.productVariantId()));
                  return true;
                }),
            argThat(
                x -> {
                  assertThat(x).isNotNull();
                  return true;
                }));
  }

  @Test
  void whenUpdateProductInventory_returnProductInventory()
      throws IOException, InterruptedException {
    final BrinkVariantInventory productInventory = mockStockProductInventory();

    final BrinkVariantInventory responseProductInventory =
        updateStockProductInventoryResponse(productInventory);

    final HttpResponse<String> httpResponse = mockHttpResponse(responseProductInventory, 200);

    when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
        .thenReturn(httpResponse);

    assertThat(this.productsApi.update(productInventory)).isEqualTo(responseProductInventory);

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo(
                          String.format(
                              "http://mockserver.com/stock/product-variants/%s/inventories/%s",
                              productInventory.variantId(), productInventory.inventoryId()));
                  return true;
                }),
            argThat(
                x -> {
                  assertThat(x).isNotNull();
                  return true;
                }));
  }
}
