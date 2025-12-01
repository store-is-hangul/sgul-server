package org.storeishangul.sgulserver.domain.gameplay.domain.support;

import lombok.Getter;

/**
 * 자음
 */
@Getter
public enum Consonant {
    ㄱ("1"),
    ㄴ("2"),
    ㄷ("3"),
    ㄹ("4"),
    ㅁ("5"),
    ㅂ("6"),
    ㅅ("7"),
    ㅇ("8"),
    ㅈ("9"),
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
}
