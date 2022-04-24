package com.waterfogsw.voucher.voucher.controller;

import com.waterfogsw.voucher.voucher.domain.Voucher;
import com.waterfogsw.voucher.voucher.dto.Response;
import com.waterfogsw.voucher.voucher.dto.ResponseStatus;
import com.waterfogsw.voucher.voucher.dto.VoucherDto;
import com.waterfogsw.voucher.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public Response<VoucherDto> voucherAdd(VoucherDto request) {
        try {
            validateVoucherRequest(request);
            Voucher voucher = VoucherDto.toDomain(request);
            Voucher savedVoucher = voucherService.saveVoucher(voucher);
            return Response.ok(VoucherDto.of(savedVoucher));
        } catch (IllegalArgumentException e) {
            return Response.error(ResponseStatus.BAD_REQUEST);
        }
    }

    private void validateVoucherRequest(VoucherDto request) {
        if (request.type() == null || request.value() == 0) {
            throw new IllegalArgumentException();
        }
    }
}
