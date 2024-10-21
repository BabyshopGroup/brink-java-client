package com.brinkcommerce.api.management.order.model.response;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

public record BrinkOrderStartCancellationPostResponse(
        @JsonTypeInfo(
                use = JsonTypeInfo.Id.NAME,
                property = "actionType"
        )
        @JsonSubTypes({
                @JsonSubTypes.Type(value = CancellationActionBonusAuto.class, name = "AUTO"),
                @JsonSubTypes.Type(value = CancellationActionBonusManual.class, name = "MANUAL"),
                @JsonSubTypes.Type(value = CancellationActionSkip.class, name = "SKIP")
        })
        CancellationActionBonus bonus,
        @JsonTypeInfo(
                use = JsonTypeInfo.Id.NAME,
                property = "actionType"
        )
        @JsonSubTypes({
                @JsonSubTypes.Type(value = CancellationActionGiftCardAuto.class, name = "AUTO"),
                @JsonSubTypes.Type(value = CancellationActionGiftCardManual.class, name = "MANUAL"),
                @JsonSubTypes.Type(value = CancellationActionSkip.class, name = "SKIP")
        })
        CancellationActionGiftCard giftCard,
        @JsonTypeInfo(
                use = JsonTypeInfo.Id.NAME,
                property = "actionType"
        )
        @JsonSubTypes({
                @JsonSubTypes.Type(value = CancellationActionGiftCardProductAuto.class, name = "AUTO"),
                @JsonSubTypes.Type(value = CancellationActionGiftCardProductManual.class, name = "MANUAL"),
                @JsonSubTypes.Type(value = CancellationActionSkip.class, name = "SKIP")
        })
        CancellationActionGiftCardProduct giftCardProduct,
        @JsonTypeInfo(
                use = JsonTypeInfo.Id.NAME,
                property = "actionType"
        )
        @JsonSubTypes({
                @JsonSubTypes.Type(value = CancellationActionPaymentAuto.class, name = "AUTO"),
                @JsonSubTypes.Type(value = CancellationActionPaymentManual.class, name = "MANUAL"),
                @JsonSubTypes.Type(value = CancellationActionSkip.class, name = "SKIP")
        })
        CancellationActionPayment payment

) {
}
