package org.storeishangul.sgulserver.domain.dictionary.infra;

import org.storeishangul.sgulserver.domain.dictionary.domain.vo.OpenDictSearchResult;

public interface WordRepository {

    OpenDictSearchResult search(String word);
}
