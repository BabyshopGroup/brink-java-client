package com.brinkcommerce.api.management.order.delivery.model.response;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public record BrinkDeliveryGetResponse(
        String id,
        String orderId,
        String orderReference,
        Optional<PaymentProviderWithStatus> paymentProvider,
        Optional<ShippingProviderWithStatus> shippingProvider,
        Optional<VoucherProviderWithStatus> voucherProvider,
        Optional<GiftCardProviderWithStatus> giftCardProvider,
        Optional<GiftCardProductProviderWithStatus> giftCardProductProvider,
        Optional<PromotionProviderWithStatus> promotionProvider,
        Optional<BonusProviderWithStatus> bonusProvider,
        List<OrderLine> orderLines,
        List<ShippingFee> shippingFees,
        List<Gift> gifts,
        List<GiftCard> giftCards,
        List<GiftCardProduct> giftCardProducts,
        @Deprecated
        List<Voucher> vouchers,
        Optional<Bonus> bonus,
        Optional<Tracking> tracking,
        Optional<CapturedPayment> capturedPayment,
        Totals totals,
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
