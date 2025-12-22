package org.storeishangul.sgulserver.domain.dictionary.domain;

import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.storeishangul.sgulserver.domain.dictionary.domain.exception.WordNotFoundException;
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
    private final HashSet<String> wordCache;


    public DictionaryService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
        this.wordCache = new HashSet<>();
    }

    public String makeWordAndValidate(List<Card> cards) {

        List<Character> characters = cards.stream()
            .map(c -> findByTypeAndCode(c.getCardType(), c.getValue())).toList();

        String composedString = HangulComposer.compose(characters);

        if (wordCache.contains(composedString)) {

            return composedString;
        }

        OpenDictSearchResult searchedResult = wordRepository.search(composedString);

        if (!searchedResult.contains(composedString)) {

            throw new WordNotFoundException();
        }

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
