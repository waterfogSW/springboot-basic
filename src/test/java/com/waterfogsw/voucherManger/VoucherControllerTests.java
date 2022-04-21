package com.waterfogsw.voucherManger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class VoucherControllerTests {

    @Nested
    @DisplayName("voucherAdd 메서드는")
    class Describe_voucherAdd {

        @Nested
        @DisplayName("voucherDto 의 VoucherType 이 null 이면")
        class Context_with_voucherType_null {

            @Test
            @DisplayName("VoucherType 이 null 임에 대한 에러메시지를 가진 응답을 반환한다")
            void it_return_voucherType_error_response() {

            }
        }

        @Nested
        @DisplayName("voucherDto 의 Value 이 null 이면")
        class Context_with_value_null {

            @Test
            @DisplayName("Value 가 null 임에 대한 에러메시지를 가진 응답을 반환한다")
            void it_return_value_error_response() {

            }
        }

        @Nested
        @DisplayName("voucherDto 의 모든 필드값이 0이 아니면")
        class Context_with_not_null_voucherDto {

            @Test
            @DisplayName("생성한 바우처의 정보 가 있는 DTO 를 반환한다")
            void it_return_created_voucher_dto_response() {

            }
        }
    }
}
