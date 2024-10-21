package com.brinkcommerce.api.management.order.delivery.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

// create dlievery model
public record BrinkDeliveryPostResponse(
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
) {}


