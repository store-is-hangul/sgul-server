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
    // 초성 쌍자음
    private static final Map<String, Character> COMPOSE_L = new HashMap<>();

    static {
        // ㅗ, ㅜ, ㅡ 결합
        COMPOSE_V.put("ㅗㅏ", 'ㅘ');
        COMPOSE_V.put("ㅗㅐ", 'ㅙ');
        COMPOSE_V.put("ㅗㅣ", 'ㅚ');
        COMPOSE_V.put("ㅜㅓ", 'ㅝ');
        COMPOSE_V.put("ㅜㅔ", 'ㅞ');
        COMPOSE_V.put("ㅜㅣ", 'ㅟ');
        COMPOSE_V.put("ㅡㅣ", 'ㅢ');
        COMPOSE_V.put("ㅏㅣ", 'ㅐ');
        COMPOSE_V.put("ㅓㅣ", 'ㅔ');

        // 복합 받침 (기본들)
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
        // 종성 쌍자음
        COMPOSE_T.put("ㄱㄱ", 'ㄲ');
        COMPOSE_T.put("ㅅㅅ", 'ㅆ');

        // 초성 쌍자음
        COMPOSE_L.put("ㄱㄱ", 'ㄲ');
        COMPOSE_L.put("ㄷㄷ", 'ㄸ');
        COMPOSE_L.put("ㅂㅂ", 'ㅃ');
        COMPOSE_L.put("ㅅㅅ", 'ㅆ');
        COMPOSE_L.put("ㅈㅈ", 'ㅉ');
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
        return COMPOSE_V.get("" + v1 + v2);
    }

    private static Character tryComposeFinal(char c1, char c2) {
        return COMPOSE_T.get("" + c1 + c2);
    }

    private static Character tryComposeLeading(char c1, char c2) {
        return COMPOSE_L.get("" + c1 + c2);
    }

    public static String compose(List<Character> jamos) {

        if (jamos == null || jamos.isEmpty()) {
            return null;
        }

        StringBuilder out = new StringBuilder();
        int i = 0, n = jamos.size();

        while (i < n) {
            char Lch, Vch, Tch = '\0';

            // --- 초성 결정 ---
            if (i >= n) {
                break;
            }
            char cur = jamos.get(i);

            if (isVowel(cur)) {
                // 모음으로 시작
                return null;

            } else if (isConsonant(cur)) {
                // 초성 쌍자음 결합 시도 (다음이 같은 자음이고 그 다음이 모음이면 더 자연스러움)
                if (i + 1 < n && isConsonant(jamos.get(i + 1))) {
                    Character combL = tryComposeLeading(cur, jamos.get(i + 1));
                    // 다음 다음이 모음이면 쌍자음 확정
                    boolean afterIsVowel = (i + 2 < n && isVowel(jamos.get(i + 2)));
                    if (combL != null && afterIsVowel) {
                        Lch = combL;
                        i += 2;
                    } else {
                        Lch = cur;
                        i++;
                    }
                } else {
                    Lch = cur;
                    i++;
                }

                if (i >= n || !isVowel(jamos.get(i))) {
                    // 모음이 바로 안오면
                    return null;
                }
                Vch = jamos.get(i);
                i++;
            } else {
                // 한글 자모 외 문자 그대로 출력
                out.append(cur);
                i++;
                continue;
            }

            // --- 중성(복합 모음) 결합 ---
            if (i < n && isVowel(jamos.get(i))) {
                Character comb = tryComposeVowel(Vch, jamos.get(i));
                if (comb != null) {
                    Vch = comb;
                    i++;
                }
            }

            // --- 종성(받침) 후보 ---
            if (i < n && isConsonant(jamos.get(i))) {
                char c1 = jamos.get(i);

                // 뒤가 모음이면 종성으로 쓰지 않고 다음 음절 초성으로 넘김
                if (i + 1 < n && isVowel(jamos.get(i + 1))) {
                    Tch = '\0';
                } else {
                    // 우선 단일 받침
                    Tch = c1;
                    i++;

                    // 다음도 자음이면 복합 받침/쌍받침 결합 시도
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

            // --- 완성형 조합 ---
            Integer L = L_INDEX.get(Lch);
            Integer V = V_INDEX.get(Vch);
            Integer T = T_INDEX.getOrDefault(Tch, 0);

            if (L != null && V != null) {
                int codePoint = SBase + (L * VCount + V) * TCount + T;
                out.append((char) codePoint);
            } else {
                // 실패 시 안전하게 분해 출력
                if (Lch != '\0') {
                    out.append(Lch);
                }
                    out.append(Vch);
                if (Tch != '\0') {
                    out.append(Tch);
                }
            }
        }
        return out.toString();
    }
}
