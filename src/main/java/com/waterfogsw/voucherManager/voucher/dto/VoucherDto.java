package com.waterfogsw.voucherManager.voucher.dto;

import com.waterfogsw.voucherManager.voucher.domain.Voucher;
import com.waterfogsw.voucherManager.voucher.domain.VoucherType;

public class VoucherDto {
    public static record Request(
            VoucherType type,
            Integer value
    ) {
        public static Voucher to(Request request) {
            return new Voucher(
                    request.type(),
                    request.value()
            );
        }
    }

    public static record Response(
            Long id,
            VoucherType type,
            Integer value
    ) {
        public static Response of(Voucher voucher) {
            return new Response(
                    voucher.getId(),
                    voucher.getType(),
                    voucher.getValue()
            );
        }
    }
}
