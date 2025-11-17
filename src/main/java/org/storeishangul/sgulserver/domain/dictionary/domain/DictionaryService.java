package org.storeishangul.sgulserver.domain.dictionary.domain;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.storeishangul.sgulserver.domain.dictionary.support.HangulComposer;

@Service
@RequiredArgsConstructor
public class DictionaryService {

    public String makeWordAndValidate(List<Character> characters) {

        return HangulComposer.compose(characters);

        //TODO: 단어 유효성 체크
        // 외부 API
    }
}
