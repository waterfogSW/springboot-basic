package com.waterfogsw.voucher.console;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class ConsoleInputTests {

    @Nested
    @DisplayName("inputMenu 메서드는")
    class Describe_input_menu {
    
        @Nested
        @DisplayName("메뉴가 입력되면")
        class Context_with_valid_menu {
            
            @Test
            @DisplayName("입력된 메뉴에 해당하는 Enum 타입을 반환한다")
            void it_return_enum() {

            }
        }

        @Nested
        @DisplayName("존재하지 않는 메뉴가 입력되면")
        class Context_with_invalid_menu {

            @Test
            @DisplayName("잘못된 입력임을 출력한다")
            void it_return_enum() {

            }
        }
    }
}
