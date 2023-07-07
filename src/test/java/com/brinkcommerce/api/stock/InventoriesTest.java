package com.brinkcommerce.api.stock;

import static com.brinkcommerce.api.util.Mocks.createStockInventoryRequest;
import static com.brinkcommerce.api.util.Mocks.mockStockInventories;
import static com.brinkcommerce.api.util.Mocks.mockStockInventory;
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
import com.brinkcommerce.api.management.stock.inventories.BrinkInventoriesApi;
import com.brinkcommerce.api.management.stock.inventories.BrinkInventoriesInventories;
import com.brinkcommerce.api.management.stock.inventories.BrinkInventory;
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
public class InventoriesTest {
  private BrinkInventoriesApi inventoriesApi;

  @Mock HttpClient httpClient;

  @Mock AuthenticationHandler authenticationHandler;

  @BeforeEach
  void init() {
    final BrinkAuthentication authenticationConfiguration =
        mockAuthenticationConfiguration();

    final ManagementConfiguration brinkConfiguration =
        mockBrinkConfiguration(authenticationConfiguration, httpClient);

    this.inventoriesApi = BrinkInventoriesApi.init(brinkConfiguration, authenticationHandler);
  }

  @Test
  void whenGetInventories_returnInventories() throws IOException, InterruptedException {
    final BrinkInventoriesInventories inventoriesResponse = mockStockInventories();

    final HttpResponse<String> httpResponse = mockHttpResponse(inventoriesResponse, 200);

    when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
        .thenReturn(httpResponse);

    assertThat(this.inventoriesApi.get()).isEqualTo(inventoriesResponse);

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo("http://mockserver.com/stock/inventories");
                  return true;
                }),
            argThat(
                x -> {
                  assertThat(x).isNotNull();
                  return true;
                }));
  }

  @Test
  void whenGetInventory_returnInventory() throws IOException, InterruptedException {
    final String inventoryId = mockStockInventory().id();

    final BrinkInventory inventoryResponse = mockStockInventory();

    final HttpResponse<String> httpResponse = mockHttpResponse(inventoryResponse, 200);

    when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
        .thenReturn(httpResponse);

    assertThat(this.inventoriesApi.get(inventoryId)).isEqualTo(inventoryResponse);

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo(
                          String.format("http://mockserver.com/stock/inventories/%s", inventoryId));
                  return true;
                }),
            argThat(
                x -> {
                  assertThat(x).isNotNull();
                  return true;
                }));
  }

  @Test
  void whenCreateInventory_returnInventory() throws IOException, InterruptedException {
    final BrinkInventory inventoryRequest = createStockInventoryRequest();

    final HttpResponse<String> httpResponse =
        mockHttpResponse(inventoryRequest, 200);

    when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
        .thenReturn(httpResponse);

    final BrinkInventory actual = this.inventoriesApi.create(inventoryRequest);

    assertThat(actual.address()).isEqualTo(inventoryRequest.address());
    assertThat(actual.name()).isEqualTo(inventoryRequest.name());
    assertThat(actual.description()).isEqualTo(inventoryRequest.description());

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo(
                          String.format(
                              "http://mockserver.com/stock/inventories/%s", inventoryRequest.id()));
                  return true;
                }),
            argThat(
                x -> {
                  assertThat(x).isNotNull();
                  return true;
                }));
  }

  @Test
  void whenDeleteInventory_useCorrectUrl() throws IOException, InterruptedException {
    final String inventoryId = "tjohej";

    final HttpResponse<String> httpResponse = mockHttpResponse("", 204);

    when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
        .thenReturn(httpResponse);

    this.inventoriesApi.delete(inventoryId);

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo(
                          String.format("http://mockserver.com/stock/inventories/%s", inventoryId));
                  return true;
                }),
            argThat(
                x -> {
                  assertThat(x).isNotNull();
                  return true;
                }));
  }
}
