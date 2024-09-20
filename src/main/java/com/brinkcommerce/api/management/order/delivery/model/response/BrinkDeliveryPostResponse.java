package com.brinkcommerce.api.management.order.delivery.model.response;

import java.time.Instant;
import java.util.List;

// create dlievery model
public record BrinkDeliveryPostResponse(
        String id,
        String orderId,
        String orderReference,
        PaymentProvider paymentProvider,
        ShippingProvider shippingProvider,
        PromotionProvider promotionProvider,
        GiftCardProvider giftCardProvider,
        GiftCardProductProvider giftCardProductProvider,
        VoucherProvider voucherProvider,
        BonusProvider bonusProvider,
        List<OrderLine> orderLines,
        List<ShippingFee> shippingFees,
        List<Gift> gifts,
        List<GiftCard> giftCards,
        List<GiftCardProduct> giftCardProducts,
        List<Voucher> vouchers,
        Bonus bonus,
        Tracking tracking,
        CapturedPayment capturedPayment,
        Totals totals,
        Instant created,
        Instant updated,
        Instant started,
        Instant restarted,
        Integer restarts,
        Instant completed,
        Instant failed,
        Integer revision
) {}


