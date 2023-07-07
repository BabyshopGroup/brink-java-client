package com.brinkcommerce.api.tax;

import static com.brinkcommerce.api.util.Mocks.createTaxMarketResponse;
import static com.brinkcommerce.api.util.Mocks.mockTaxMarket;
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
import com.brinkcommerce.api.management.store.BrinkCountryCode;
import com.brinkcommerce.api.management.tax.BrinkTaxException;
import com.brinkcommerce.api.management.tax.market.BrinkTaxMarket;
import com.brinkcommerce.api.management.tax.market.BrinkTaxMarketApi;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TaxMarketTest {

  private BrinkTaxMarketApi taxApi;

  @Mock HttpClient httpClient;

  @Mock AuthenticationHandler authenticationHandler;

  @BeforeEach
  void init() {
    final BrinkAuthentication authenticationConfiguration =
        mockAuthenticationConfiguration();

    final ManagementConfiguration brinkConfiguration =
        mockBrinkConfiguration(authenticationConfiguration, httpClient);

    taxApi = BrinkTaxMarketApi.init(brinkConfiguration, authenticationHandler);
  }

  public static final String TAX_GROUP_ID = "BABYSHOP";

  @Test
  void whenCreate_returnBrinkTaxMarket() throws IOException, InterruptedException {
    final BrinkTaxMarket taxMarket = mockTaxMarket();
    final BrinkTaxMarket responseTaxMarket = createTaxMarketResponse(taxMarket);

    final HttpResponse<String> httpResponse = mockHttpResponse(responseTaxMarket, 200);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThat(taxApi.create(TAX_GROUP_ID, taxMarket)).isEqualTo(responseTaxMarket);

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo("http://mockserver.com/tax/tax-groups/BABYSHOP/markets/SE/");
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
    final BrinkTaxMarket taxMarket = mockTaxMarket();

    final BrinkHttpErrorMessage errorMessage = new BrinkHttpErrorMessage("0", "Error");
    final HttpResponse<String> httpResponse = mockHttpResponse(errorMessage, statusCode);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatThrownBy(() -> taxApi.create(TAX_GROUP_ID, taxMarket))
        .isInstanceOf(BrinkTaxException.class)
        .hasMessageContaining(String.format("Http code: %d", statusCode));
  }

  @Test
  void whenCreate_throwsRuntime() throws IOException, InterruptedException {
    final BrinkTaxMarket taxMarket = mockTaxMarket();

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class)))
        .thenThrow(RuntimeException.class);

    assertThatThrownBy(() -> taxApi.create(TAX_GROUP_ID, taxMarket))
        .isInstanceOf(BrinkTaxException.class);
  }

  @Test
  void whenCreateOnNullArgument_throwsNullPointer() {
    final BrinkTaxMarket taxMarket = null;

    assertThatThrownBy(() -> taxApi.create(TAX_GROUP_ID, taxMarket))
        .isInstanceOf(NullPointerException.class);
  }

  // -----------------------------------------------------------------------------------------------

  @Test
  void whenGetAll_returnBrinkTaxMarketList() throws IOException, InterruptedException {

    final String taxGroupId = "BABYSHOP";
    final List<BrinkTaxMarket> updatedTaxMarket = List.of(mockTaxMarket(), mockTaxMarket());
    record ResponseWrapper(List<BrinkTaxMarket> taxMarkets) {}
    final List<BrinkTaxMarket> responseTaxMarket = createTaxMarketResponse(updatedTaxMarket);
    final ResponseWrapper wrappedResponse = new ResponseWrapper(responseTaxMarket);

    final HttpResponse<String> httpResponse = mockHttpResponse(wrappedResponse, 200);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThat(taxApi.getAll(taxGroupId)).isEqualTo(responseTaxMarket);

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo("http://mockserver.com/tax/tax-groups/BABYSHOP/markets");
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
    final String taxGroupId = "BABYSHOP";

    final BrinkHttpErrorMessage errorMessage = new BrinkHttpErrorMessage("0", "Error");
    final HttpResponse<String> httpResponse = mockHttpResponse(errorMessage, statusCode);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatThrownBy(() -> taxApi.getAll(taxGroupId))
        .isInstanceOf(BrinkTaxException.class)
        .hasMessageContaining(String.format("Http code: %d", statusCode));
  }

  @Test
  void whenGetAll_throwsRuntime() throws IOException, InterruptedException {
    final String taxGroupId = "BABYSHOP";

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class)))
        .thenThrow(RuntimeException.class);

    assertThatThrownBy(() -> taxApi.getAll(taxGroupId)).isInstanceOf(BrinkTaxException.class);
  }

  @Test
  void whenGetAllOnNullArgument_throwsNullPointer() {
    final String taxGroupId = null;

    assertThatThrownBy(() -> taxApi.getAll(taxGroupId)).isInstanceOf(NullPointerException.class);
  }

  // -----------------------------------------------------------------------------------------------

  @Test
  void whenDelete_returnNoContent() throws IOException, InterruptedException {

    final String taxGroupId = "BABYSHOP";
    final BrinkCountryCode countryCode = BrinkCountryCode.SE;

    final HttpResponse<String> httpResponse = mockHttpResponse("", 204);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatCode(() -> taxApi.delete(taxGroupId, BrinkCountryCode.SE)).doesNotThrowAnyException();

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo("http://mockserver.com/tax/tax-groups/BABYSHOP/markets/SE/");
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
    final String taxGroupId = "BABYSHOP";
    final BrinkCountryCode countryCode = BrinkCountryCode.SE;
    final BrinkHttpErrorMessage errorMessage = new BrinkHttpErrorMessage("0", "Error");
    final HttpResponse<String> httpResponse = mockHttpResponse(errorMessage, statusCode);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatThrownBy(() -> taxApi.delete(taxGroupId, BrinkCountryCode.SE))
        .isInstanceOf(BrinkTaxException.class)
        .hasMessageContaining(String.format("Http code: %d", statusCode));
  }

  @Test
  void whenDelete_throwsRuntime() throws IOException, InterruptedException {
    final String taxGroupId = "BABYSHOP";

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class)))
        .thenThrow(RuntimeException.class);

    assertThatThrownBy(() -> taxApi.delete(taxGroupId, BrinkCountryCode.SE))
        .isInstanceOf(BrinkTaxException.class);
  }

  @Test
  void whenDeleteOnNullArgument_throwsNullPointer() {
    final String taxGroupId = null;

    assertThatThrownBy(() -> taxApi.delete(taxGroupId, BrinkCountryCode.SE))
        .isInstanceOf(NullPointerException.class);
  }
}
