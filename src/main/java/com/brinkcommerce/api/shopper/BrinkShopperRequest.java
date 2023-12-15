package com.brinkcommerce.api.shopper;

import java.util.*;

public record BrinkShopperRequest(String productParentId, String storeGroupId, String countryCode, Map<String, String> headers) {

    public BrinkShopperRequest {
        Objects.requireNonNull(productParentId, "Product parent id cannot be null.");
        Objects.requireNonNull(storeGroupId, "Store group id cannot be null.");
        Objects.requireNonNull(countryCode, "Country code variant cannot be null.");
        if(Objects.isNull(headers)) {
            headers = new HashMap<>();
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String productParentId;
        private String storeGroupId;
        private String countryCode;
        private Map<String, String> headers;

        private Builder() {
        }

        public Builder withProductParentId(String productParentId) {
            this.productParentId = productParentId;
            return this;
        }

        public Builder withStoreGroupId(String storeGroupId) {
            this.storeGroupId = storeGroupId;
            return this;
        }

        public Builder withCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        public Builder withHeaders(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public Builder withHeader(final String name, final String value) {
            if (Objects.isNull(headers)) {
                headers = new HashMap<>();
            }
            headers.put(name, value);
            return this;
        }

        public BrinkShopperRequest build() {
            return new BrinkShopperRequest(productParentId, storeGroupId, countryCode, headers);
        }
    }
}
