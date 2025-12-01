package org.storeishangul.sgulserver.domain.dictionary.domain;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.storeishangul.sgulserver.domain.dictionary.support.HangulComposer;
import org.storeishangul.sgulserver.domain.gameplay.domain.support.CardType;
import org.storeishangul.sgulserver.domain.gameplay.domain.support.Consonant;
import org.storeishangul.sgulserver.domain.gameplay.domain.support.Vowel;
import org.storeishangul.sgulserver.domain.gameplay.domain.vo.Card;

@Service
@RequiredArgsConstructor
public class DictionaryService {

    public String makeWordAndValidate(List<Card> cards) {

        List<Character> characters = cards.stream()
            .map(c -> findByTypeAndCode(c.getCardType(), c.getValue())).toList();

        //TODO: 단어 유효성 체크
        // 외부 API

        return HangulComposer.compose(characters);
    }

    private Character findByTypeAndCode(CardType type, String code) {

        if (type == CardType.VOWEL) {
            Vowel vowel = Vowel.findByCode(code);
            return vowel.name().charAt(0);
        } else {
            Consonant consonant = Consonant.findByCode(code);
            return consonant.name().charAt(0);
        }
    }
}
