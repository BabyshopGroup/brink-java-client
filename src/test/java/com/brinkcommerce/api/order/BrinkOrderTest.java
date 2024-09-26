package com.brinkcommerce.api.order;

import com.brinkcommerce.api.authentication.AuthenticationHandler;
import com.brinkcommerce.api.configuration.BrinkAuthentication;
import com.brinkcommerce.api.configuration.ManagementConfiguration;
import com.brinkcommerce.api.management.order.BrinkOrderApi;
import com.brinkcommerce.api.management.order.delivery.BrinkDeliveryApi;
import com.brinkcommerce.api.management.order.delivery.model.request.BrinkDeliveryPostRequest;
import com.brinkcommerce.api.management.order.delivery.model.response.BrinkDeliveryPostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.brinkcommerce.api.util.Mocks.mockOrderDeliveryPostRequest;
import static com.brinkcommerce.api.util.Mocks.mockOrderDeliveryPostResponse;
import static com.brinkcommerce.api.util.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

        assertThat(sut.create(request)).isEqualTo(response);

        verify(httpClient)
                .send(
                        argThat(
                                x -> {
                                    assertThat(x.uri().toString())
                                            .isEqualTo("http://mockserver.com/orders/order-id-1/deliveries");
                                    return true;
                                }),
                        argThat(
                                x -> {
                                    assertThat(x).isNotNull();
                                    return true;
                                }));
    }

}
