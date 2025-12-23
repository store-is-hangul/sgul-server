package org.storeishangul.sgulserver.domain.dictionary.domain;

import jakarta.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Service;
import org.storeishangul.sgulserver.domain.dictionary.domain.vo.OpenDictSearchResult;
import org.storeishangul.sgulserver.domain.dictionary.infra.WordRepository;
import org.storeishangul.sgulserver.domain.dictionary.support.HangulComposer;
import org.storeishangul.sgulserver.domain.gameplay.domain.support.CardType;
import org.storeishangul.sgulserver.domain.gameplay.domain.support.Consonant;
import org.storeishangul.sgulserver.domain.gameplay.domain.support.Vowel;
import org.storeishangul.sgulserver.domain.gameplay.domain.vo.Card;

@Service
public class DictionaryService {

    private final WordRepository wordRepository;
    private final HashMap<String, Boolean> wordCache;


    public DictionaryService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
        this.wordCache = new HashMap<>();
    }

    @Nullable
    public String makeWordAndValidateOrElseNull(List<Card> cards) {

        List<Character> characters = cards.stream()
            .map(c -> findByTypeAndCode(c.getCardType(), c.getValue())).toList();

        String composedString = HangulComposer.compose(characters);

        // 단어 조합조차 되지 않는 경우
        if (composedString == null || composedString.isEmpty()) {

            return null;
        }

        if (wordCache.containsKey(composedString)) {

            return wordCache.get(composedString) ? composedString : null;
        }

        OpenDictSearchResult searchedResult = wordRepository.search(composedString);

        if (!searchedResult.contains(composedString)) {

            wordCache.put(composedString, false);
            return null;
        }

        wordCache.put(composedString, true);
        return composedString;
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
