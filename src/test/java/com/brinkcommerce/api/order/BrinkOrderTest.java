package com.brinkcommerce.api.order;

import com.brinkcommerce.api.authentication.AuthenticationHandler;
import com.brinkcommerce.api.common.BrinkHttpErrorMessage;
import com.brinkcommerce.api.configuration.BrinkAuthentication;
import com.brinkcommerce.api.configuration.ManagementConfiguration;
import com.brinkcommerce.api.management.order.delivery.BrinkDeliveryApi;
import com.brinkcommerce.api.management.order.delivery.BrinkDeliveryException;
import com.brinkcommerce.api.management.order.delivery.model.request.BrinkDeliveryGetRequest;
import com.brinkcommerce.api.management.order.delivery.model.request.BrinkDeliveryPostRequest;
import com.brinkcommerce.api.management.order.delivery.model.request.BrinkDeliveryStartRequest;
import com.brinkcommerce.api.management.order.delivery.model.response.BrinkDeliveryGetResponse;
import com.brinkcommerce.api.management.order.delivery.model.response.BrinkDeliveryPostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.brinkcommerce.api.util.Mocks.*;
import static com.brinkcommerce.api.util.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BrinkOrderTest {
    private BrinkDeliveryApi sut;

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
        sut = BrinkDeliveryApi.init(brinkConfiguration, authenticationHandler);
    }

    @Test
    void whenInit_returnBrinkOrderApi() {
        assertThat(sut).isNotNull();
    }

    @Test
    void whenCreate_returnBrinkDelivery() throws IOException, InterruptedException {
        final BrinkDeliveryPostRequest request = mockOrderDeliveryPostRequest();
        final BrinkDeliveryPostResponse response = mockOrderDeliveryPostResponse();

        HttpResponse<String> httpResponse = mockHttpResponse(response, 200);

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(httpResponse);

        assertThat(sut.create(request, "order-id-1")).isEqualTo(response);

        verify(httpClient)
                .send(
                        argThat(
                                x -> {
                                    assertThat(x.uri().toString())
                                            .isEqualTo("http://mockserver.com/order/orders/order-id-1/deliveries");
                                    return true;
                                }),
                        argThat(
                                x -> {
                                    assertThat(x).isNotNull();
                                    return true;
                                }));
    }

    @Test
    void whenCreate_throwsRuntime() throws IOException, InterruptedException {
        final BrinkDeliveryPostRequest request = mockOrderDeliveryPostRequest();

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> sut.create(request, "order-id-1")).isInstanceOf(BrinkDeliveryException.class);
    }

    @Test
    void whenCreateOnNullArgument_throwsNullPointer() {
    final BrinkDeliveryPostRequest request = null;

    assertThatThrownBy(() -> sut.create(request, "order-id-1")).isInstanceOf(NullPointerException.class);
  }

    @ParameterizedTest()
    @ValueSource(ints = {400, 500})
    void whenCreate_returnErrorStatusCode(final int statusCode)
            throws IOException, InterruptedException {
        final BrinkDeliveryPostRequest request = mockOrderDeliveryPostRequest();

        final BrinkHttpErrorMessage errorMessage = new BrinkHttpErrorMessage("0", "Error");
        final HttpResponse<String> httpResponse = mockHttpResponse(errorMessage, statusCode);

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(httpResponse);

        assertThatThrownBy(() -> sut.create(request, "order-id-1"))
                .isInstanceOf(BrinkDeliveryException.class)
                .hasMessageContaining(String.format("Http code: %d", statusCode));
    }

    /* --------------- get --------------- */
    @Test
    void whenGet_returnBrinkDelivery() throws IOException, InterruptedException {
        final BrinkDeliveryGetRequest request = mockOrderDeliveryGetRequest();
        final BrinkDeliveryGetResponse response = mockOrderDeliveryGetResponse();
        final HttpResponse<String> httpResponse = mockHttpResponse(response, 200);

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(httpResponse);

        assertThat(sut.get(request)).isEqualTo(response);

        verify(httpClient)
                .send(
                        argThat(
                                x -> {
                                    assertThat(x.uri().toString())
                                            .isEqualTo("http://mockserver.com/order/deliveries/delivery-id-1/");
                                    return true;
                                }),
                        argThat(
                                x -> {
                                    assertThat(x).isNotNull();
                                    return true;
                                }));
    }

    @Test
    void whenGet_throwsRuntime() throws IOException, InterruptedException {
        final BrinkDeliveryGetRequest request = mockOrderDeliveryGetRequest();

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> sut.get(request)).isInstanceOf(BrinkDeliveryException.class);
    }

    @Test
    void whenGetOnNullArgument_throwsNullPointer() {
        final BrinkDeliveryGetRequest request = null;

        assertThatThrownBy(() -> sut.get(request)).isInstanceOf(NullPointerException.class);
    }

    /* --------------- start --------------- */

    @Test
    void whenStart_returnBrinkStart() throws IOException, InterruptedException {
        final BrinkDeliveryStartRequest request = mockOrderDeliveryStartRequest();

        final HttpResponse<String> httpResponse = mock(HttpResponse.class);
        when(httpResponse.statusCode()).thenReturn(202);
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(httpResponse);

        sut.start(request, "delivery-id-1");

        verify(httpClient)
                .send(
                        argThat(
                                x -> {
                                    assertThat(x.uri().toString())
                                            .isEqualTo("http://mockserver.com/order/deliveries/delivery-id-1/start");
                                    return true;
                                }),
                        argThat(
                                x -> {
                                    assertThat(x).isNotNull();
                                    return true;
                                }));
    }

    @Test
    void whenStart_throwsRuntime() throws IOException, InterruptedException {
        final BrinkDeliveryStartRequest request = mockOrderDeliveryStartRequest();

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> sut.start(request, "delivery-id-1")).isInstanceOf(BrinkDeliveryException.class);
    }

    @Test
    void whenStartOnNullArgument_throwsNullPointer() {
        final BrinkDeliveryStartRequest request = null;

        assertThatThrownBy(() -> sut.start(request, "delivery-id-1")).isInstanceOf(NullPointerException.class);
    }

}
