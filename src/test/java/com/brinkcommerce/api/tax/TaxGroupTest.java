package com.brinkcommerce.api.tax;

import static com.brinkcommerce.api.util.Mocks.createTaxGroupResponse;
import static com.brinkcommerce.api.util.Mocks.mockTaxGroup;
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
import com.brinkcommerce.api.management.tax.BrinkTaxException;
import com.brinkcommerce.api.management.tax.taxgroup.BrinkTaxGroup;
import com.brinkcommerce.api.management.tax.taxgroup.BrinkTaxGroupApi;
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
public class TaxGroupTest {

  private BrinkTaxGroupApi taxApi;

  @Mock HttpClient httpClient;

  @Mock AuthenticationHandler authenticationHandler;

  @BeforeEach
  void init() {
    final BrinkAuthentication authenticationConfiguration =
        mockAuthenticationConfiguration();

    final ManagementConfiguration brinkConfiguration =
        mockBrinkConfiguration(authenticationConfiguration, httpClient);

    taxApi = BrinkTaxGroupApi.init(brinkConfiguration, authenticationHandler);
  }

  @Test
  void whenCreate_returnBrinkTaxGroup() throws IOException, InterruptedException {
    final BrinkTaxGroup taxGroup = mockTaxGroup();

    final BrinkTaxGroup responseTaxGroup = createTaxGroupResponse(taxGroup);

    final HttpResponse<String> httpResponse = mockHttpResponse(responseTaxGroup, 200);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThat(taxApi.create(taxGroup)).isEqualTo(responseTaxGroup);

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo("http://mockserver.com/tax/tax-groups/BABYSHOP");
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
    final BrinkTaxGroup taxGroup = mockTaxGroup();

    final BrinkHttpErrorMessage errorMessage = new BrinkHttpErrorMessage("0", "Error");
    final HttpResponse<String> httpResponse = mockHttpResponse(errorMessage, statusCode);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatThrownBy(() -> taxApi.create(taxGroup))
        .isInstanceOf(BrinkTaxException.class)
        .hasMessageContaining(String.format("Http code: %d", statusCode));
  }

  @Test
  void whenCreate_throwsRuntime() throws IOException, InterruptedException {
    final BrinkTaxGroup taxGroup = mockTaxGroup();

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class)))
        .thenThrow(RuntimeException.class);

    assertThatThrownBy(() -> taxApi.create(taxGroup)).isInstanceOf(BrinkTaxException.class);
  }

  @Test
  void whenCreateOnNullArgument_throwsNullPointer() {
    final BrinkTaxGroup taxGroup = null;

    assertThatThrownBy(() -> taxApi.create(taxGroup)).isInstanceOf(NullPointerException.class);
  }
  // -----------------------------------------------------------------------------------------------

  @Test
  void whenGet_returnBrinkTaxGroup() throws IOException, InterruptedException {

    final String taxGroupId = "BABYSHOP";
    final BrinkTaxGroup responseTaxGroup = createTaxGroupResponse(mockTaxGroup());

    final HttpResponse<String> httpResponse = mockHttpResponse(responseTaxGroup, 200);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThat(taxApi.get(taxGroupId)).isEqualTo(responseTaxGroup);

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo("http://mockserver.com/tax/tax-groups/BABYSHOP");
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
    final String taxGroupId = "BABYSHOP";

    final BrinkHttpErrorMessage errorMessage = new BrinkHttpErrorMessage("0", "Error");
    final HttpResponse<String> httpResponse = mockHttpResponse(errorMessage, statusCode);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatThrownBy(() -> taxApi.get(taxGroupId))
        .isInstanceOf(BrinkTaxException.class)
        .hasMessageContaining(String.format("Http code: %d", statusCode));
  }

  @Test
  void whenGet_throwsRuntime() throws IOException, InterruptedException {
    final String taxGroupId = "BABYSHOP";

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class)))
        .thenThrow(RuntimeException.class);

    assertThatThrownBy(() -> taxApi.get(taxGroupId)).isInstanceOf(BrinkTaxException.class);
  }

  @Test
  void whenGetOnNullArgument_throwsNullPointer() {
    final String taxGroupId = null;

    assertThatThrownBy(() -> taxApi.get(taxGroupId)).isInstanceOf(NullPointerException.class);
  }

  // -----------------------------------------------------------------------------------------------

  @Test
  void whenDelete_returnNoContent() throws IOException, InterruptedException {

    final String taxGroupId = "BABYSHOP";

    final HttpResponse<String> httpResponse = mockHttpResponse("", 204);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatCode(() -> taxApi.delete(taxGroupId)).doesNotThrowAnyException();

    verify(httpClient)
        .send(
            argThat(
                x -> {
                  assertThat(x.uri().toString())
                      .isEqualTo("http://mockserver.com/tax/tax-groups/BABYSHOP");
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
    final BrinkHttpErrorMessage errorMessage = new BrinkHttpErrorMessage("0", "Error");
    final HttpResponse<String> httpResponse = mockHttpResponse(errorMessage, statusCode);

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class))).thenReturn(httpResponse);

    assertThatThrownBy(() -> taxApi.delete(taxGroupId))
        .isInstanceOf(BrinkTaxException.class)
        .hasMessageContaining(String.format("Http code: %d", statusCode));
  }

  @Test
  void whenDelete_throwsRuntime() throws IOException, InterruptedException {
    final String taxGroupId = "BABYSHOP";

    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class)))
        .thenThrow(RuntimeException.class);

    assertThatThrownBy(() -> taxApi.delete(taxGroupId)).isInstanceOf(BrinkTaxException.class);
  }

  @Test
  void whenDeleteOnNullArgument_throwsNullPointer() {
    final String taxGroupId = null;

    assertThatThrownBy(() -> taxApi.delete(taxGroupId)).isInstanceOf(NullPointerException.class);
  }
}
