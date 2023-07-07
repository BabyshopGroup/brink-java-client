package com.brinkcommerce.api.shopper;

import static com.brinkcommerce.api.util.TestUtils.mockAuthenticationConfiguration;
import static com.brinkcommerce.api.util.TestUtils.mockBrinkShopperConfiguration;
import static com.brinkcommerce.api.util.TestUtils.mockHttpResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.brinkcommerce.api.configuration.BrinkAuthentication;
import com.brinkcommerce.api.shopper.prices.BrinkPricesException;
import com.brinkcommerce.api.shopper.prices.BrinkProductParentPrices;
import com.brinkcommerce.api.shopper.prices.BrinkProductParentPricesProductParent;
import com.brinkcommerce.api.shopper.prices.BrinkProductVariantPrice;
import com.brinkcommerce.api.shopper.stocks.BrinkProductParentStock;
import com.brinkcommerce.api.shopper.stocks.BrinkProductParentStockProductParent;
import com.brinkcommerce.api.shopper.stocks.BrinkStocksException;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BrinkCommerceShopperApiTest {

  private static final String PRODUCT_ID = "p1";
  private static final String MARKET = "SE";
  private static final String STORE_GROUP_ID = "BABYSHOP";

  @Mock private HttpClient httpClient;

  private BrinkShopper api;

  @BeforeEach
  void init() {
    final BrinkAuthentication authenticationConfiguration =
        mockAuthenticationConfiguration();

    final ShopperConfiguration brinkConfiguration = mockBrinkShopperConfiguration(this.httpClient);

    this.api = BrinkShopper.init(brinkConfiguration);
  }



  @Test
  void stockLevelRequest_shouldReturnStockLevel() throws IOException, InterruptedException {
    final HttpResponse<String> httpResponse =
        mockHttpResponse(new BrinkProductParentStock(new BrinkProductParentStockProductParent(List.of(), "p1")), 200);

    when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
        .thenReturn(httpResponse);

    final BrinkProductParentStock response =
        this.api.stocks().get(PRODUCT_ID, STORE_GROUP_ID, MARKET);

    verify(this.httpClient, times(1))
        .send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo(
                          String.format(
                              "http://mockserver.com/stocks/product-parents/%s/store-groups/%s/markets/%s",
                              PRODUCT_ID, STORE_GROUP_ID, MARKET));
                  return true;
                }),
            argThat(
                x -> {
                  assertThat(x).isNotNull();
                  return true;
                }));

    assertEquals("p1", response.productParent().id());
  }

  @Test
  void priceRequest_shouldReturnPrices() throws IOException, InterruptedException {
    final HttpResponse<String> httpResponse =
        mockHttpResponse(
            new BrinkProductParentPrices(
                new BrinkProductParentPricesProductParent(
                    true,
                    "BABYSHOP",
                    "SE",
                    List.of(new BrinkProductVariantPrice(1100L, 1000L, 100L, 200L, 2500L, 2L, "id")),
                    "id",
                    "SEK"
                )
            ),
            200);

    when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
        .thenReturn(httpResponse);

    final BrinkProductParentPrices response =
        this.api.prices().get(PRODUCT_ID, STORE_GROUP_ID, MARKET);

    verify(this.httpClient, times(1))
        .send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo(
                          String.format(
                              "http://mockserver.com/prices/product-parents/%s/store-groups/%s/markets/%s",
                              PRODUCT_ID, STORE_GROUP_ID, MARKET));
                  return true;
                }),
            argThat(
                x -> {
                  assertThat(x).isNotNull();
                  return true;
                }));

    assertEquals("id", response.productParent().id());
  }

  @Test
  void stockLevelRequest_shouldThrowException() throws IOException, InterruptedException {
    when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
        .thenThrow(new RuntimeException());

    assertThrows(
        BrinkStocksException.class,
        () -> this.api.stocks().get(PRODUCT_ID, STORE_GROUP_ID, MARKET));

    verify(this.httpClient, times(1))
        .send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo(
                          String.format(
                              "http://mockserver.com/stocks/product-parents/%s/store-groups/%s/markets/%s",
                              PRODUCT_ID, STORE_GROUP_ID, MARKET));
                  return true;
                }),
            argThat(
                x -> {
                  assertThat(x).isNotNull();
                  return true;
                }));
  }

  @Test
  void priceRequest_shouldThrowException() throws IOException, InterruptedException {
    when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
        .thenThrow(new RuntimeException());

    assertThrows(
        BrinkPricesException.class,
        () -> this.api.prices().get(PRODUCT_ID, STORE_GROUP_ID, MARKET));

    verify(this.httpClient, times(1))
        .send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo(
                          String.format(
                              "http://mockserver.com/prices/product-parents/%s/store-groups/%s/markets/%s",
                              PRODUCT_ID, STORE_GROUP_ID, MARKET));
                  return true;
                }),
            argThat(
                x -> {
                  assertThat(x).isNotNull();
                  return true;
                }));
  }
}
