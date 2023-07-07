package com.brinkcommerce.api.management.discount;

import com.brinkcommerce.api.authentication.AuthenticationHandler;
import com.brinkcommerce.api.configuration.ManagementConfiguration;
import com.brinkcommerce.api.exception.BrinkIntegrationException;
import com.brinkcommerce.api.utils.BrinkHttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

import static com.brinkcommerce.api.utils.BrinkHttpUtil.*;
import static com.brinkcommerce.api.utils.BrinkHttpUtil.APPLICATION_JSON;

public class BrinkDiscountCodeApi {

    private static final String PATH = "discount";

    private final ObjectMapper mapper;
    private final HttpClient httpClient;
    private final URI discountCodeUrl;
    private final AuthenticationHandler authenticationHandler;
    @Deprecated
    private final BrinkHttpUtil brinkHttpUtil;

    private BrinkDiscountCodeApi(ManagementConfiguration config, AuthenticationHandler authenticationHandler) {
        Objects.requireNonNull(config, "Configuration cannot be null.");
        Objects.requireNonNull(config.host(), "Management Host URL cannot be null.");
        this.mapper = Objects.requireNonNull(config.mapper(), "ObjectMapper cannot be null.");
        this.httpClient = Objects.requireNonNull(config.httpClient(), "HttpClient cannot be null.");
        this.discountCodeUrl = URI.create(String.format("%s/%s", config.host(), PATH));
        this.authenticationHandler = authenticationHandler;
        this.brinkHttpUtil = BrinkHttpUtil.create(this.mapper);
    }

    public static BrinkDiscountCodeApi init(final ManagementConfiguration config, final AuthenticationHandler authenticationHandler) {
        return new BrinkDiscountCodeApi(config, authenticationHandler);
    }


    public BrinkDiscount create(final BrinkDiscount discountRequest) {
        Objects.requireNonNull(discountRequest, "com.brinkcommerce.api.Brink Discount can not be null");

        try {
            final HttpRequest httpRequest = httpRequestBuilderWithAuthentication(
                            URI.create(buildURI(this.discountCodeUrl.toString())),
                            this.authenticationHandler.getToken(),
                            this.authenticationHandler.getApiKey())
                            .POST(HttpRequest.BodyPublishers.ofString(this.mapper.writeValueAsString(discountRequest)))
                            .header(ACCEPT, APPLICATION_JSON)
                            .header(CONTENT_TYPE, APPLICATION_JSON)
                            .build();

            final HttpResponse<String> response = makeRequest(httpRequest);
            return (BrinkDiscount) this.brinkHttpUtil.handleResponse(response, BrinkDiscount.class);
        } catch (final InterruptedException ie) {
            Thread.currentThread().interrupt();
            throw new BrinkDiscountException(
                    String.format("1 Failed to create discount with code %s.", discountRequest.code()),
                    ie,
                    null);
        } catch (final BrinkIntegrationException e) {
            throw new BrinkDiscountException(
                    String.format("2 Failed to create discount with code %s.", discountRequest.code()),
                    e,
                    e.brinkHttpCode(),
                    e.requestId());
        } catch (final Exception e) {
            throw new BrinkDiscountException(
                    String.format("3 Failed to create discount with code %s.", discountRequest.code()), e, null);
        }
    }

    private String buildURI(final String baseUrl) {
        return baseUrl + "/discounts/codes";
    }

    private HttpResponse<String> makeRequest(final HttpRequest request) throws IOException, InterruptedException {
        return this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
