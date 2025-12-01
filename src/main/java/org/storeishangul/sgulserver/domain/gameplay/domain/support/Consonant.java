package org.storeishangul.sgulserver.domain.gameplay.domain.support;

import java.util.Arrays;
import lombok.Getter;

/**
 * 자음
 */
@Getter
public enum Consonant {
    ㄱ("01"),
    ㄴ("02"),
    ㄷ("03"),
    ㄹ("04"),
    ㅁ("05"),
    ㅂ("06"),
    ㅅ("07"),
    ㅇ("08"),
    ㅈ("09"),
    ㅊ("10"),
    ㅋ("11"),
    ㅌ("12"),
    ㅍ("13"),
    ㅎ("14"),
    ;

    private String code;

    Consonant(String code) {
        this.code = code;
    }


    public static Consonant findByCode(String code) {

        return Arrays.stream(values())
            .filter(consonant -> consonant.code.equals(code))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Invalid consonant code: " + code));
    }
}
