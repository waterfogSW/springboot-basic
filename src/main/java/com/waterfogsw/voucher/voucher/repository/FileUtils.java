package com.waterfogsw.voucher.voucher.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waterfogsw.voucher.voucher.domain.Voucher;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    private static final String REPOSITORY_PATH = "./repository/voucher";
    private static final String FILE_FORMAT = ".json";

    private static final String FILE_EXIST = "File already exist";
    private static final String DIRECTORY_ERROR = "Error Occurred While Initialize Directory";

    private FileUtils() {
    }

    public static void save(Voucher voucher) {
        File voucherFile = new File(REPOSITORY_PATH + "/" + voucher.getId() + FILE_FORMAT);
        try {
            if (!voucherFile.createNewFile()) {
                throw new IllegalStateException(FILE_EXIST);
            }
            mapper.writeValue(voucherFile, voucher);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public static List<Voucher> findAll() {
        List<Voucher> fileVoucherList = new ArrayList<>();

        File folder = new File(REPOSITORY_PATH);
        try {
            for (var f : Objects.requireNonNull(folder.listFiles())) {
                fileVoucherList.add(mapper.readValue(f, Voucher.class));
            }
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage());
        }

        return fileVoucherList;
    }

    public static void initFilePath() {
        File repositoryFolder = new File(REPOSITORY_PATH);
        deleteDirectory(repositoryFolder);
        if (!repositoryFolder.mkdirs()) {
            throw new IllegalStateException(DIRECTORY_ERROR);
        }
    }

    private static void deleteDirectory(File rootFolder) {
        File[] allFiles = rootFolder.listFiles();
        if (allFiles != null) {
            for (File file : allFiles) {
                deleteDirectory(file);
            }
        }

        if (!rootFolder.delete()) {
            throw new IllegalStateException(DIRECTORY_ERROR);
        }
    }

}
