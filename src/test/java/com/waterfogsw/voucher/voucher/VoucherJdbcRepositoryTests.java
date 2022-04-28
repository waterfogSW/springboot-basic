package com.waterfogsw.voucher.voucher;

import com.waterfogsw.voucher.voucher.domain.FixedAmountVoucher;
import com.waterfogsw.voucher.voucher.domain.PercentDiscountVoucher;
import com.waterfogsw.voucher.voucher.domain.Voucher;
import com.waterfogsw.voucher.voucher.repository.VoucherJdbcRepository;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles({"jdbc"})
@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VoucherJdbcRepositoryTests {

    @Autowired
    VoucherJdbcRepository voucherRepository;
    @Autowired
    DataSource dataSource;

    @Test
    @Order(1)
    @DisplayName("datasource 확인")
    public void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @Configuration
    @ComponentScan(
            basePackages = {"com.waterfogsw.voucher.voucher"}
    )
    static class Config {
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:3306/voucher_mgmt")
                    .username("root")
                    .password("02709580")
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }

        @Bean
        public PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }

        @Bean
        public TransactionTemplate transactionTemplate(PlatformTransactionManager platformTransactionManager) {
            return new TransactionTemplate(platformTransactionManager);
        }
    }

    @Nested
    @DisplayName("save 메서드는")
    class Describe_save {

        @Nested
        @DisplayName("인자로 전달받은 Voucher 가 null 이면")
        class Context_with_null_argument {

            @Test
            @Order(2)
            @DisplayName("IllegalArgumentException 예외를 발생시킨다")
            void it_throw_error() {
                assertThrows(IllegalArgumentException.class, () -> voucherRepository.save(null));
            }
        }

        @Nested
        @DisplayName("Voucher 가 정상적으로 저장되면")
        class Context_with_voucher_saved {

            @Test
            @Order(3)
            @Transactional
            @DisplayName("저장한 바우처를 리턴한다")
            void it_return_saved_voucher() {
                Voucher voucher1 = new FixedAmountVoucher(1000);
                Voucher voucher2 = new PercentDiscountVoucher(50);

                Voucher savedVoucher1 = voucherRepository.save(voucher1);
                Voucher savedVoucher2 = voucherRepository.save(voucher2);

                assertThat(savedVoucher1.getType(), is(voucher1.getType()));
                assertThat(savedVoucher1.getValue(), is(voucher1.getValue()));
                assertThat(savedVoucher2.getType(), is(voucher2.getType()));
                assertThat(savedVoucher2.getValue(), is(voucher2.getValue()));
            }
        }
    }

    @Nested
    @DisplayName("findAll 메소드는")
    class Describe_findAll {

        @Nested
        @DisplayName("호출되면")
        class Context_with_call {

            @Test
            @Order(4)
            @Transactional
            @DisplayName("저장된 모든 Voucher 에 대한 List 를 리턴한다")
            void it_return_saved_voucherList() {
                final var existingVoucherList = voucherRepository.findAll();

                final Voucher voucher1 = new FixedAmountVoucher(1000);
                final Voucher voucher2 = new PercentDiscountVoucher(50);
                final Voucher voucher3 = new PercentDiscountVoucher(30);

                voucherRepository.save(voucher1);
                voucherRepository.save(voucher2);
                voucherRepository.save(voucher3);

                final var addedVoucherList = voucherRepository.findAll();

                assertThat(existingVoucherList.size() + 3, is(addedVoucherList.size()));
            }
        }
    }
}
