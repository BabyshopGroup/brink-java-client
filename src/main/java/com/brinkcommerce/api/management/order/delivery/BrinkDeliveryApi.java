package com.brinkcommerce.api.management.order.delivery;

import com.brinkcommerce.api.authentication.AuthenticationHandler;
import com.brinkcommerce.api.configuration.ManagementConfiguration;
import com.brinkcommerce.api.exception.BrinkIntegrationException;
import com.brinkcommerce.api.management.order.delivery.model.request.BrinkDeliveryGetRequest;
import com.brinkcommerce.api.management.order.delivery.model.request.BrinkDeliveryPostRequest;
import com.brinkcommerce.api.management.order.delivery.model.request.BrinkDeliveryStartRequest;
import com.brinkcommerce.api.management.order.delivery.model.response.BrinkDeliveryGetResponse;
import com.brinkcommerce.api.management.order.delivery.model.response.BrinkDeliveryPostResponse;
import com.brinkcommerce.api.utils.BrinkHttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

import static com.brinkcommerce.api.utils.BrinkHttpUtil.*;

public class BrinkDeliveryApi {

    private static final String PATH = "orders";

    private final HttpClient httpClient;
    private final ObjectMapper mapper;
    private final AuthenticationHandler authenticationHandler;
    private final BrinkHttpUtil brinkHttpUtil;
    private final URI orderUrl;

    public BrinkDeliveryApi(
            final ManagementConfiguration config,
            final AuthenticationHandler authenticationHandler
    ) {
        Objects.requireNonNull(config, "Configuration cannot be null.");
        Objects.requireNonNull(config.host(), "Management Host URL cannot be null.");
        this.mapper = Objects.requireNonNull(config.mapper(), "ObjectMapper cannot be null.");
        this.httpClient = Objects.requireNonNull(config.httpClient(), "HttpClient cannot be null.");
        this.orderUrl = URI.create(String.format("%s/%s", config.host(), PATH));
        this.authenticationHandler = authenticationHandler;
        this.brinkHttpUtil = BrinkHttpUtil.create(this.mapper);
    }

    public static BrinkDeliveryApi init(
            final ManagementConfiguration config, AuthenticationHandler authenticationHandler) {
        return new BrinkDeliveryApi(config, authenticationHandler);
    }

    public BrinkDeliveryPostResponse create(
            final BrinkDeliveryPostRequest request
    ) {
        Objects.requireNonNull(request, "com.brinkcommerce.api.Brink Brink delivery post request cannot be null");
        final String uri = new StringBuilder()
                .append(String.format("%s/", this.orderUrl.toString()))
                .append(String.format("%s/", request.orderId()))
                .append("deliveries")
                .toString();

        try {
            final HttpRequest httpRequest =
                    httpRequestBuilderWithAuthentication(
                            URI.create(uri),
                            this.authenticationHandler.getToken(),
                            this.authenticationHandler.getApiKey())
                            .POST(HttpRequest.BodyPublishers.ofString(this.mapper.writeValueAsString(request)))
                            .header(ACCEPT, APPLICATION_JSON)
                            .header(CONTENT_TYPE, APPLICATION_JSON)
                            .build();

            final HttpResponse<String> response = makeRequest(httpRequest);

            return (BrinkDeliveryPostResponse) this.brinkHttpUtil.handleResponse(response, BrinkDeliveryPostResponse.class);
        } catch (final InterruptedException ie) {
            Thread.currentThread().interrupt();
            throw new BrinkDeliveryException(
                    String.format("Failed to create delivery with for order id %s.", request.orderId()), ie, null);
        } catch (final BrinkIntegrationException e) {
            throw new BrinkDeliveryException(
                    String.format("Failed to create delivery with for order id %s.", request.orderId()),
                    e,
                    e.brinkHttpCode(),
                    e.requestId());
        } catch (final Exception e) {
            throw new BrinkDeliveryException(
                    String.format("Failed to create delivery with for order id %s.", request.orderId()), e, null);
        }
    }

    public BrinkDeliveryGetResponse get(
            final BrinkDeliveryGetRequest request
    ) {
        final String uri = new StringBuilder()
                .append(String.format("%s/", this.orderUrl.toString()))
                .append(String.format("%s/", request.deliveryId()))
                .append("deliveries")
                .toString();

        final HttpRequest httpRequest =
                httpRequestBuilderWithAuthentication(
                        URI.create(uri),
                        this.authenticationHandler.getToken(),
                        this.authenticationHandler.getApiKey())
                        .GET()
                        .build();

        try {
            final HttpResponse<String> response = makeRequest(httpRequest);
            return (BrinkDeliveryGetResponse) this.brinkHttpUtil.handleResponse(response, BrinkDeliveryGetResponse.class);
        } catch (final InterruptedException ie) {
            Thread.currentThread().interrupt();
            throw new BrinkDeliveryException(
                    String.format("Failed to get delivery with delivery-id %s.", request.deliveryId()),
                    ie,
                    null);
        } catch (final BrinkIntegrationException e) {
            throw new BrinkDeliveryException(
                    String.format("Failed to get delivery with delivery-id %s.", request.deliveryId()),
                    e,
                    e.brinkHttpCode(),
                    e.requestId());
        } catch (final Exception e) {
            throw new BrinkDeliveryException(
                    String.format("Failed to get delivery with delivery-id %s.", request.deliveryId()),
                    e,
                    null);
        }
    }

    public void start(
            final BrinkDeliveryStartRequest request
            ) {
        final String uri = new StringBuilder()
                .append("deliveries")
                .append(String.format("%s/", request.deliveryId()))
                .append("start")
                .toString();
        final HttpRequest httpRequest = httpRequestBuilderWithAuthentication(
                URI.create(uri),
                this.authenticationHandler.getToken(),
                this.authenticationHandler.getApiKey())
                .POST(HttpRequest.BodyPublishers.noBody())
                .header(ACCEPT, APPLICATION_JSON)
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .build();
        try {
            final HttpResponse<String> response = makeRequest(httpRequest);
            this.brinkHttpUtil.handleResponse(response, null);
        } catch (final InterruptedException ie) {
            Thread.currentThread().interrupt();
            throw new BrinkDeliveryException(
                    String.format("Failed to start delivery with delivery-id %s.", request.deliveryId()),
                    ie,
                    null);
        } catch (final BrinkIntegrationException e) {
            throw new BrinkDeliveryException(
                    String.format("Failed to start delivery with delivery-id %s.", request.deliveryId()),
                    e,
                    e.brinkHttpCode(),
                    e.requestId());
        } catch (final Exception e) {
            throw new BrinkDeliveryException(
                    String.format("Failed to start delivery with delivery-id %s.", request.deliveryId()),
                    e,
                    null);
        }
    }

    private HttpResponse<String> makeRequest(final HttpRequest request) throws IOException, InterruptedException {
        return this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
