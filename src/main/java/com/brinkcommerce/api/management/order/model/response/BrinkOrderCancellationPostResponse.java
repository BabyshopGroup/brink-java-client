package com.brinkcommerce.api.management.order.model.response;

import com.brinkcommerce.api.management.order.delivery.model.response.*;
import com.brinkcommerce.api.management.order.model.request.Reason;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public record BrinkOrderCancellationPostResponse(
        String id,
        String orderId,
        String orderReference,
        PaymentProvider paymentProvider,
        Optional<ShippingProvider> shippingProvider,
        Optional<PromotionProvider> promotionProvider,
        Optional<GiftCardProvider> giftCardProvider,
        Optional<GiftCardProductProvider> giftCardProductProvider,
        Optional<VoucherProvider> voucherProvider,
        Optional<BonusProvider> bonusProvider,
        Reason reason,
        List<GiftCard> giftCards,
        List<GiftCardProduct> giftCardProducts,
        CancelledPayment cancelledPayment,
        Optional<Bonus> bonus,
        Instant created,
        Instant updated,
        Optional<Instant> started,
        Optional<Instant> restarted,
        Optional<Long> restarts,
        Optional<Instant> completed,
        Optional<Instant> failed,
        Long revision
) {
}
