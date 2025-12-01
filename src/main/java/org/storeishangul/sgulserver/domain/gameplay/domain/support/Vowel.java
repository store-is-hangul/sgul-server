package org.storeishangul.sgulserver.domain.gameplay.domain.support;

import java.util.Arrays;
import lombok.Getter;

/**
 * 모음
 */

@Getter
public enum Vowel {
    ㅏ("01"),
    ㅑ("02"),
    ㅓ("03"),
    ㅕ("04"),
    ㅗ("05"),
    ㅛ("06"),
    ㅜ("07"),
    ㅠ("08"),
    ㅡ("09"),
    ㅣ("10"),
//    ㅐ("11"),
//    ㅔ("12"),
    ;

    private String code;

    Vowel(String code) {
        this.code = code;
    }

    public static Vowel findByCode(String code) {

        return Arrays.stream(values())
            .filter(vowel -> vowel.code.equals(code))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Invalid vowel code: " + code));
    }
}
