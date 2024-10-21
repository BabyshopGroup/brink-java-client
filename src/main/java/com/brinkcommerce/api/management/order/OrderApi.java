package com.brinkcommerce.api.management.order;

import com.brinkcommerce.api.authentication.AuthenticationHandler;
import com.brinkcommerce.api.configuration.ManagementConfiguration;
import com.brinkcommerce.api.exception.BrinkIntegrationException;
import com.brinkcommerce.api.management.order.delivery.BrinkOrderException;
import com.brinkcommerce.api.management.order.model.request.BrinkOrderCancellationPostRequest;
import com.brinkcommerce.api.management.order.model.request.BrinkOrderReleasePostRequest;
import com.brinkcommerce.api.management.order.model.request.BrinkOrderStartCancellationPostRequest;
import com.brinkcommerce.api.management.order.model.response.BrinkOrderReleasePostResponse;
import com.brinkcommerce.api.management.order.model.response.BrinkOrderCancellationPostResponse;
import com.brinkcommerce.api.management.order.model.response.BrinkOrderStartCancellationPostResponse;
import com.brinkcommerce.api.utils.BrinkHttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

import static com.brinkcommerce.api.utils.BrinkHttpUtil.*;

public class OrderApi {

    private static final String BASE_PATH = "order";
    private static final String ORDERS_PATH = "orders";
    private static final String CANCELLATIONS_PATH = "cancellations";

    private final ObjectMapper mapper;
    private final HttpClient httpClient;
    private final AuthenticationHandler authenticationHandler;
    private final URI orderPath;
    private final BrinkHttpUtil brinkHttpUtil;
    private final URI cancellationPath;

    public OrderApi(
            final ManagementConfiguration config,
            final AuthenticationHandler authenticationHandler
    ) {
        Objects.requireNonNull(config, "Configuration cannot be null.");
        Objects.requireNonNull(config.host(), "Management Host URL cannot be null.");
        this.mapper = Objects.requireNonNull(config.mapper(), "ObjectMapper cannot be null.");
        this.httpClient = Objects.requireNonNull(config.httpClient(), "HttpClient cannot be null.");
        this.orderPath = URI.create(String.format("%s/%s/%s", config.host(), BASE_PATH, ORDERS_PATH));
        this.cancellationPath = URI.create(String.format("%s/%s/%s", config.host(), BASE_PATH, CANCELLATIONS_PATH));
        this.authenticationHandler = authenticationHandler;
        this.brinkHttpUtil = BrinkHttpUtil.create(this.mapper);
    }

    public static OrderApi init(final ManagementConfiguration config, AuthenticationHandler authenticationHandler) {
        return new OrderApi(config, authenticationHandler);
    }

    public BrinkOrderCancellationPostResponse cancel(final BrinkOrderCancellationPostRequest request, final String orderId) {
        Objects.requireNonNull(request, "Request cannot be null");

        final String uri = new StringBuilder()
                .append(orderPath)
                .append("/")
                .append(orderId)
                .append("/cancellations")
                .toString();

        try {
            final HttpRequest httpRequest = httpRequestBuilderWithAuthentication(
                    URI.create(uri),
                    this.authenticationHandler.getToken(),
                    this.authenticationHandler.getApiKey())
                    .POST(HttpRequest.BodyPublishers.ofString(this.mapper.writeValueAsString(request)))
                    .header(ACCEPT, APPLICATION_JSON)
                    .header(CONTENT_TYPE, APPLICATION_JSON)
                    .build();

            final HttpResponse<String> response = makeRequest(httpRequest);
            return (BrinkOrderCancellationPostResponse) this.brinkHttpUtil.handleResponse(response, BrinkOrderCancellationPostResponse.class);
        } catch (final InterruptedException ie) {
            Thread.currentThread().interrupt();
            throw new BrinkOrderException(
                    String.format("Failed to create cancellation for order id %s", orderId),
                    ie,
                    null);
        } catch (final BrinkIntegrationException e) {
            throw new BrinkOrderException(
                    String.format("Failed to create cancellation for order id %s", orderId),
                    e,
                    e.brinkHttpCode(),
                    e.requestId());
        } catch (final Exception e) {
            throw new BrinkOrderException(
                    String.format("Failed to create cancellation for order id %s", orderId),
                    e,
                    null);
        }
    }

    public BrinkOrderStartCancellationPostResponse startCancel(final BrinkOrderStartCancellationPostRequest request, final String cancellationId) {
        Objects.requireNonNull(request, "Request cannot be null");
        final String uri = new StringBuilder()
                .append(this.cancellationPath)
                .append("/")
                .append(cancellationId)
                .append("/start")
                .toString();

        try {
            final HttpRequest httpRequest = httpRequestBuilderWithAuthentication(
                    URI.create(uri),
                    this.authenticationHandler.getToken(),
                    this.authenticationHandler.getApiKey())
                    .POST(HttpRequest.BodyPublishers.ofString(this.mapper.writeValueAsString(request)))
                    .header(ACCEPT, APPLICATION_JSON)
                    .header(CONTENT_TYPE, APPLICATION_JSON)
                    .build();

            final HttpResponse<String> response = makeRequest(httpRequest);
            return (BrinkOrderStartCancellationPostResponse) this.brinkHttpUtil.handleResponse(response, BrinkOrderStartCancellationPostResponse.class);
        } catch (final InterruptedException ie) {
            Thread.currentThread().interrupt();
            throw new BrinkOrderException(
                    String.format("Failed to start cancellation for cancellation id %s", cancellationId),
                    ie,
                    null);
        } catch (final BrinkIntegrationException e) {
            throw new BrinkOrderException(
                    String.format("Failed to start cancellation for cancellation id %s", cancellationId),
                    e,
                    e.brinkHttpCode(),
                    e.requestId());
        } catch (final Exception e) {
            throw new BrinkOrderException(
                    String.format("Failed to start cancellation for cancellation id %s", cancellationId),
                    e,
                    null);
        }
    }

    public BrinkOrderReleasePostResponse release(final BrinkOrderReleasePostRequest request, final String orderId) {
        Objects.requireNonNull(request, "Request cannot be null");
        final String uri = new StringBuilder()
                .append(orderPath)
                .append("/")
                .append(orderId)
                .append("/releases")
                .toString();

        try {
            final HttpRequest httpRequest = httpRequestBuilderWithAuthentication(
                    URI.create(uri),
                    this.authenticationHandler.getToken(),
                    this.authenticationHandler.getApiKey())
                    .POST(HttpRequest.BodyPublishers.ofString(this.mapper.writeValueAsString(request)))
                    .header(ACCEPT, APPLICATION_JSON)
                    .header(CONTENT_TYPE, APPLICATION_JSON)
                    .build();

            final HttpResponse<String> response = makeRequest(httpRequest);
            return (BrinkOrderReleasePostResponse) this.brinkHttpUtil.handleResponse(response, BrinkOrderReleasePostResponse.class);
        } catch (final InterruptedException ie) {
            Thread.currentThread().interrupt();
            throw new BrinkOrderException(
                    String.format("Failed to create release for order id %s", orderId),
                    ie,
                    null);
        } catch (final BrinkIntegrationException e) {
            throw new BrinkOrderException(
                    String.format("Failed to create release for order id %s", orderId),
                    e,
                    e.brinkHttpCode(),
                    e.requestId());
        } catch (final Exception e) {
            throw new BrinkOrderException(
                    String.format("Failed to create release for order id %s", orderId),
                    e,
                    null);
        }

    }

    private HttpResponse<String> makeRequest(final HttpRequest request) throws IOException, InterruptedException {
        return this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
