package com.waterfogsw.voucher.voucher.repository;

import com.waterfogsw.voucher.voucher.domain.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Profile({"local"})
@Repository
public class VoucherFileRepository implements VoucherRepository {

    public VoucherFileRepository() {
        FileUtils.initFilePath();
    }

    @Override
    public Voucher save(Voucher voucher) {
        if (voucher == null) {
            throw new IllegalArgumentException();
        }

        if (voucher.getId() == null) {
            Voucher newVoucher = createVoucherEntity(voucher);
            FileUtils.save(newVoucher);
            return newVoucher;
        }

        FileUtils.save(voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return FileUtils.findAll();
    }

    private Voucher createVoucherEntity(Voucher voucher) {
        Long id = KeyGenerator.keyGenerate();
        return Voucher.toEntity(id, voucher);
    }
}
