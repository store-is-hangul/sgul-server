package org.storeishangul.sgulserver.domain.dictionary.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HangulComposer {

    // Unicode bases
    private static final int SBase = 0xAC00;
    private static final int LCount = 19, VCount = 21, TCount = 28;
    private static final int NCount = VCount * TCount;

    // 초성(Leading consonants) in Unicode order
    private static final char[] L_TABLE = {
        'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ',
        'ㅎ'
    };

    // 중성(Vowels) in Unicode order
    private static final char[] V_TABLE = {
        'ㅏ', 'ㅐ', 'ㅑ', 'ㅒ', 'ㅓ', 'ㅔ', 'ㅕ', 'ㅖ', 'ㅗ', 'ㅘ', 'ㅙ', 'ㅚ', 'ㅛ',
        'ㅜ', 'ㅝ', 'ㅞ', 'ㅟ', 'ㅠ', 'ㅡ', 'ㅢ', 'ㅣ'
    };

    // 종성(Tails) in Unicode order (0 = none)
    private static final char[] T_TABLE = {
        '\0', 'ㄱ', 'ㄲ', 'ㄳ', 'ㄴ', 'ㄵ', 'ㄶ', 'ㄷ', 'ㄹ', 'ㄺ', 'ㄻ', 'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ', 'ㅀ',
        'ㅁ', 'ㅂ', 'ㅄ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    };

    private static final Map<Character, Integer> L_INDEX = indexMap(L_TABLE);
    private static final Map<Character, Integer> V_INDEX = indexMap(V_TABLE);
    private static final Map<Character, Integer> T_INDEX = indexMap(T_TABLE);

    // 복합 모음 규칙 (baseVowel, nextVowel) -> combinedVowel
    private static final Map<String, Character> COMPOSE_V = new HashMap<>();
    // 복합 받침 규칙 (c1, c2) -> combined 종성 문자
    private static final Map<String, Character> COMPOSE_T = new HashMap<>();

    static {
        // ㅗ + (ㅏ,ㅐ,ㅣ) -> (ㅘ,ㅙ,ㅚ)
        COMPOSE_V.put("ㅗㅏ", 'ㅘ');
        COMPOSE_V.put("ㅗㅐ", 'ㅙ');
        COMPOSE_V.put("ㅗㅣ", 'ㅚ');
        // ㅜ + (ㅓ,ㅔ,ㅣ) -> (ㅝ,ㅞ,ㅟ)
        COMPOSE_V.put("ㅜㅓ", 'ㅝ');
        COMPOSE_V.put("ㅜㅔ", 'ㅞ');
        COMPOSE_V.put("ㅜㅣ", 'ㅟ');
        // ㅡ + ㅣ -> ㅢ
        COMPOSE_V.put("ㅡㅣ", 'ㅢ');

        // 복합 받침
        COMPOSE_T.put("ㄱㅅ", 'ㄳ');
        COMPOSE_T.put("ㄴㅈ", 'ㄵ');
        COMPOSE_T.put("ㄴㅎ", 'ㄶ');
        COMPOSE_T.put("ㄹㄱ", 'ㄺ');
        COMPOSE_T.put("ㄹㅁ", 'ㄻ');
        COMPOSE_T.put("ㄹㅂ", 'ㄼ');
        COMPOSE_T.put("ㄹㅅ", 'ㄽ');
        COMPOSE_T.put("ㄹㅌ", 'ㄾ');
        COMPOSE_T.put("ㄹㅍ", 'ㄿ');
        COMPOSE_T.put("ㄹㅎ", 'ㅀ');
        COMPOSE_T.put("ㅂㅅ", 'ㅄ');
    }

    private static Map<Character, Integer> indexMap(char[] table) {
        Map<Character, Integer> m = new HashMap<>();
        for (int i = 0; i < table.length; i++) {
            m.put(table[i], i);
        }
        return m;
    }

    private static boolean isConsonant(char ch) {
        return L_INDEX.containsKey(ch);
    }

    private static boolean isVowel(char ch) {
        return V_INDEX.containsKey(ch);
    }

    private static Character tryComposeVowel(char v1, char v2) {
        Character c = COMPOSE_V.get("" + v1 + v2);
        return c != null ? c : null;
    }

    private static Character tryComposeFinal(char c1, char c2) {
        Character c = COMPOSE_T.get("" + c1 + c2);
        return c != null ? c : null;
    }

    public static String compose(List<Character> jamos) {

        if(jamos == null || jamos.isEmpty()) {
            return null;
        }

        StringBuilder out = new StringBuilder();

        int i = 0, n = jamos.size();
        while (i < n) {
            // 1) 초성
            char Lch, Vch, Tch = '\0';

            char cur = jamos.get(i);
            if (isVowel(cur)) {
                // 모음으로 시작하면
                return null;
            } else if (isConsonant(cur)) {
                Lch = cur;
                i++;
                if (i >= n || !isVowel(jamos.get(i))) {
                    // 모음이 없으면
                    return null;
                }
                Vch = jamos.get(i);
                i++;
            } else {
                // 한글 자모가 아니면 그대로 출력
                return null;
            }

            // 2) 복합 모음 결합 시도
            if (i < n && isVowel(jamos.get(i))) {
                Character comb = tryComposeVowel(Vch, jamos.get(i));
                if (comb != null) {
                    Vch = comb;
                    i++;
                }
            }

            // 3) 종성(받침) 후보
            if (i < n && isConsonant(jamos.get(i))) {
                char c1 = jamos.get(i);

                // 뒤가 모음이면, c1은 다음 음절 초성 → 종성 없음
                if (i + 1 < n && isVowel(jamos.get(i + 1))) {
                    Tch = '\0';
                } else {
                    Tch = c1; // 우선 단일 받침으로 가정
                    i++;

                    // 다음도 자음이면 복합 받침 시도 (단 그 다음이 모음이면 복합 받침 만들지 않음)
                    if (i < n && isConsonant(jamos.get(i))) {
                        char c2 = jamos.get(i);
                        boolean nextIsVowel = (i + 1 < n && isVowel(jamos.get(i + 1)));
                        Character combT = (!nextIsVowel) ? tryComposeFinal(c1, c2) : null;
                        if (combT != null) {
                            Tch = combT;
                            i++;
                        }
                    }
                }
            }

            // 4) 유니코드 조합
            Integer L = L_INDEX.get(Lch);
            Integer V = V_INDEX.get(Vch);
            Integer T = T_INDEX.getOrDefault(Tch, 0);

            if (L != null && V != null) {
                int codePoint = SBase + (L * VCount + V) * TCount + T;
                out.append((char) codePoint);
            } else {
                // 안전장치: 매핑 실패 시 개별 문자로 출력
                out.append(Lch).append(Vch);
                if (Tch != '\0') {
                    out.append(Tch);
                }
            }
        }
        return out.toString();
    }
}
