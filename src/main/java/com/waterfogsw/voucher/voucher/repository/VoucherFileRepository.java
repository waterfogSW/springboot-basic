package com.waterfogsw.voucher.voucher.repository;

import com.waterfogsw.voucher.voucher.domain.Voucher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Profile({"default"})
@Repository
public class VoucherFileRepository implements VoucherRepository {

    private final String repositoryPath;

    public VoucherFileRepository(@Value("${file-repository-path}") String path) {
        repositoryPath = path;
        VoucherFileUtils.initFilePath(path);
    }

    @Override
    public Voucher save(Voucher voucher) {
        if (voucher == null) {
            throw new IllegalArgumentException();
        }

        if (voucher.getId() == null) {
            Voucher newVoucher = createVoucherEntity(voucher);
            VoucherFileUtils.save(newVoucher, repositoryPath);
            return newVoucher;
        }

        VoucherFileUtils.save(voucher, repositoryPath);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return VoucherFileUtils.findAll(repositoryPath);
    }

    private Voucher createVoucherEntity(Voucher voucher) {
        Long id = KeyGenerator.keyGenerate();
        return Voucher.toEntity(id, voucher);
    }
}
