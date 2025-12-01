package org.storeishangul.sgulserver.domain.gameplay.domain.support;

import lombok.Getter;

/**
 * 모음
 */

@Getter
public enum Vowel {
    ㅏ("1"),
    ㅑ("2"),
    ㅓ("3"),
    ㅕ("4"),
    ㅗ("5"),
    ㅛ("6"),
    ㅜ("7"),
    ㅠ("8"),
    ㅡ("9"),
    ㅣ("10"),
    ㅐ("11"),
    ㅔ("12"),
    ;

    private String code;

    Vowel(String code) {
        this.code = code;
    }
}
