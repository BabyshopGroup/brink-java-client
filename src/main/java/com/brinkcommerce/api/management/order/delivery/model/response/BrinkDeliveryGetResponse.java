package com.brinkcommerce.api.management.order.delivery.model.response;

import java.time.Instant;
import java.util.List;

public record BrinkDeliveryGetResponse(
        String id,
        String orderId,
        String orderReference,
        List<WithStatus<ShippingProvider>> shippingProvider,
        List<WithStatus<PaymentProvider>> paymentProvider,
        List<WithStatus<VoucherProvider>> voucherProvider,
        List<WithStatus<GiftCardProvider>> giftCardProvider,
        List<WithStatus<GiftCardProductProvider>> giftCardProductProvider,
        List<WithStatus<PromotionProvider>> promotionProvider,
        List<WithStatus<BonusProvider>> bonusProvider,
        List<OrderLine> orderLines,
        List<ShippingFee> shippingFees,
        List<Gift> gifts,
        List<GiftCard> giftCards,
        List<GiftCardProduct> giftCardProducts,
        List<Voucher> vouchers,
        List<Bonus> bonus,
        List<Tracking> tracking,
        List<CapturedPayment> capturedPayment,
        List<Totals> totals,
        Instant created,
        Instant updated,
        Instant started,
        Instant restarted,
        Integer restarts,
        Instant completed,
        Instant failed,
        Integer revision
) {}
