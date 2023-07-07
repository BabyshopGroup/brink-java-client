package com.brinkcommerce.api.stock;

import static com.brinkcommerce.api.util.Mocks.mockStockMarket;
import static com.brinkcommerce.api.util.Mocks.mockStockMarketInventoryIds;
import static com.brinkcommerce.api.util.Mocks.updateMarketInventoryIdsResponse;
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
import com.brinkcommerce.api.management.stock.storegroups.BrinkStoreGroupMarketInventories;
import com.brinkcommerce.api.management.stock.storegroups.BrinkStoreGroupMarketInventoryIds;
import com.brinkcommerce.api.management.stock.storegroups.BrinkStoreGroupsApi;
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
public class StoreGroupsTest {
  private BrinkStoreGroupsApi marketsApi;

  @Mock HttpClient httpClient;

  @Mock AuthenticationHandler authenticationHandler;

  @BeforeEach
  void init() {
    final BrinkAuthentication authenticationConfiguration =
        mockAuthenticationConfiguration();

    final ManagementConfiguration brinkConfiguration =
        mockBrinkConfiguration(authenticationConfiguration, httpClient);

    this.marketsApi = BrinkStoreGroupsApi.init(brinkConfiguration, authenticationHandler);
  }

  @Test
  void whenUpdateMarketInventoryIds_returnMarketInventoryIds()
      throws IOException, InterruptedException {
    final var request = mockStockMarketInventoryIds();

    final BrinkStoreGroupMarketInventoryIds response = updateMarketInventoryIdsResponse(request);

    final HttpResponse<String> httpResponse = mockHttpResponse(response, 200);

    when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
        .thenReturn(httpResponse);

    assertThat(this.marketsApi.update(request)).isEqualTo(response);

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo(
                          String.format(
                              "http://mockserver.com/stock/store-groups/%s/markets/%s/inventories",
                              request.storeGroupId(), request.countryCode()));
                  return true;
                }),
            argThat(
                x -> {
                  assertThat(x).isNotNull();
                  return true;
                }));
  }

  @Test
  void whenGetMarketInventories_returnMarketInventories() throws IOException, InterruptedException {
    final String storeGroupId = mockStockMarket().storeGroupId();
    final String countryCode = mockStockMarket().countryCode();

    final BrinkStoreGroupMarketInventories marketInventoriesResponse = mockStockMarket();

    final HttpResponse<String> httpResponse = mockHttpResponse(marketInventoriesResponse, 200);

    when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
        .thenReturn(httpResponse);

    assertThat(this.marketsApi.get(storeGroupId, countryCode)).isEqualTo(marketInventoriesResponse);

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo(
                          String.format(
                              "http://mockserver.com/stock/store-groups/%s/markets/%s",
                              storeGroupId, countryCode));
                  return true;
                }),
            argThat(
                x -> {
                  assertThat(x).isNotNull();
                  return true;
                }));
  }
}
