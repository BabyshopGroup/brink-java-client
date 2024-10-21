package com.brinkcommerce.api.order;

import com.brinkcommerce.api.authentication.AuthenticationHandler;
import com.brinkcommerce.api.common.BrinkHttpErrorMessage;
import com.brinkcommerce.api.configuration.BrinkAuthentication;
import com.brinkcommerce.api.configuration.ManagementConfiguration;
import com.brinkcommerce.api.management.order.OrderApi;
import com.brinkcommerce.api.management.order.delivery.BrinkDeliveryException;
import com.brinkcommerce.api.management.order.delivery.BrinkOrderException;
import com.brinkcommerce.api.management.order.delivery.model.request.BrinkDeliveryPostRequest;
import com.brinkcommerce.api.management.order.model.request.BrinkOrderCancellationPostRequest;
import com.brinkcommerce.api.management.order.model.request.BrinkOrderReleasePostRequest;
import com.brinkcommerce.api.management.order.model.request.BrinkOrderStartCancellationPostRequest;
import com.brinkcommerce.api.management.order.model.response.BrinkOrderCancellationPostResponse;
import com.brinkcommerce.api.management.order.model.response.BrinkOrderReleasePostResponse;
import com.brinkcommerce.api.management.order.model.response.BrinkOrderStartCancellationPostResponse;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BrinkOrderApiTest {
    private OrderApi sut;

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

        sut = OrderApi.init(brinkConfiguration, authenticationHandler);
    }

    @Test
    void whenInit_returnBrinkOrderApi() {
        assertThat(sut).isNotNull();
    }

    /* --------------------- cancel --------------------- */

    @Test
    void whenCancel_returnBrinkOrderCancellationPostResponse() throws IOException, InterruptedException {
        final BrinkOrderCancellationPostResponse response = mockOrderCancellationPostResponse();
        final BrinkOrderCancellationPostRequest request = mockOrderCancellationPostRequest();

        HttpResponse<String> httpResponse = mockHttpResponse(response, 200);

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(httpResponse);

        assertThat(sut.cancel(request, "order-id-1")).isEqualTo(response);

        verify(httpClient)
                .send(
                        argThat(x -> {
                            assertThat(x.uri().toString())
                                    .isEqualTo("http://mockserver.com/order/orders/order-id-1/cancellations");
                            return true;
                        }),
                        argThat(
                                x -> {
                                    assertThat(x).isNotNull();
                                    return true;
                                }
                        )
                );
    }

    @ParameterizedTest()
    @ValueSource(ints = {400, 500})
    void whenCancel_returnErrorStatusCode(final int statusCode) throws IOException, InterruptedException {
        final BrinkOrderCancellationPostRequest request = mockOrderCancellationPostRequest();

        final BrinkHttpErrorMessage errorMessage = new BrinkHttpErrorMessage("0", "Error");
        final HttpResponse<String> httpResponse = mockHttpResponse(errorMessage, statusCode);

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(httpResponse);

        assertThatThrownBy(() -> sut.cancel(request, "order-id-1")).isInstanceOf(BrinkOrderException.class);
    }

    @Test
    void whenCancelOnNullArgument_throwsNullPointer() {
        final BrinkOrderCancellationPostRequest request = null;
        assertThatThrownBy(() -> sut.cancel(request, "order-id-1")).isInstanceOf(NullPointerException.class);
    }

    @Test
    void whenCancel_throwsRuntime() throws IOException, InterruptedException {
        final BrinkOrderCancellationPostRequest request = mockOrderCancellationPostRequest();

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> sut.cancel(request, "order-id-1")).isInstanceOf(BrinkOrderException.class);
    }

    /* --------------------- release --------------------- */

    @Test
    void whenRelease_returnBrinkOrderReleasePostResponse() throws IOException, InterruptedException {
        final BrinkOrderReleasePostResponse response = mockOrderReleasePostResponse();
        final BrinkOrderReleasePostRequest request = mockOrderReleasePostRequest();

        HttpResponse<String> httpResponse = mockHttpResponse(response, 200);

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(httpResponse);

        assertThat(sut.release(request, "order-id-1")).isEqualTo(response);

        verify(httpClient)
                .send(
                        argThat(x -> {
                            assertThat(x.uri().toString())
                                    .isEqualTo("http://mockserver.com/order/orders/order-id-1/releases");
                            return true;
                        }),
                        argThat(
                                x -> {
                                    assertThat(x).isNotNull();
                                    return true;
                                }
                        )
                );
    }

    @ParameterizedTest()
    @ValueSource(ints = {400, 500})
    void whenRelease_returnErrorStatusCode(final int statusCode) throws IOException, InterruptedException {
        final BrinkOrderReleasePostRequest request = mockOrderReleasePostRequest();

        final BrinkHttpErrorMessage errorMessage = new BrinkHttpErrorMessage("0", "Error");
        final HttpResponse<String> httpResponse = mockHttpResponse(errorMessage, statusCode);

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(httpResponse);

        assertThatThrownBy(() -> sut.release(request, "order-id-1")).isInstanceOf(BrinkOrderException.class);
    }

    @Test
    void whenReleaseOnNullArgument_throwsNullPointer() {
        final BrinkOrderReleasePostRequest request = null;
        assertThatThrownBy(() -> sut.release(request, "order-id-1")).isInstanceOf(NullPointerException.class);
    }

    @Test
    void whenRelease_throwsRuntime() throws IOException, InterruptedException {
        final BrinkOrderReleasePostRequest request = mockOrderReleasePostRequest();

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> sut.release(request, "order-id-1")).isInstanceOf(BrinkOrderException.class);
    }

    /* --------------------- start cancel --------------------- */

    @Test
    void whenStartCancel_returnBrinkOrderStartCancellationPostResponse() throws IOException, InterruptedException {
        final String cancellationId = "cancellation-id-1";
        final BrinkOrderStartCancellationPostRequest request = mockBrinkOrderStartCancellationPostRequest();

        final BrinkOrderStartCancellationPostResponse response = mockBrinkOrderStartCancellationPostResponse();

        HttpResponse<String> httpResponse = mockHttpResponse(response, 200);

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(httpResponse);

        assertThat(sut.startCancel(request, cancellationId)).isEqualTo(response);

        verify(httpClient)
                .send(
                        argThat(x -> {
                            assertThat(x.uri().toString())
                                    .isEqualTo("http://mockserver.com/order/cancellations/cancellation-id-1/start");
                            return true;
                        }),
                        argThat(
                                x -> {
                                    assertThat(x).isNotNull();
                                    return true;
                                }
                        )
                );
    }

    @ParameterizedTest()
    @ValueSource(ints = {400, 500})
    void whenStartCancel_returnErrorStatusCode(final int statusCode) throws IOException, InterruptedException {
        final BrinkOrderStartCancellationPostRequest request = mockBrinkOrderStartCancellationPostRequest();
        final String cancellationId = "cancellation-id-1";

        final BrinkHttpErrorMessage errorMessage = new BrinkHttpErrorMessage("0", "Error");
        final HttpResponse<String> httpResponse = mockHttpResponse(errorMessage, statusCode);

        assertThatThrownBy(() -> sut.startCancel(request, cancellationId)).isInstanceOf(BrinkOrderException.class);
    }

    @Test
    void whenStartCancel_throwsRuntime() throws IOException, InterruptedException {
        final String cancellationId = "cancellation-id-1";
        final BrinkOrderStartCancellationPostRequest request = mockBrinkOrderStartCancellationPostRequest();

        assertThatThrownBy(() -> sut.startCancel(request, cancellationId)).isInstanceOf(BrinkOrderException.class);
    }

}
