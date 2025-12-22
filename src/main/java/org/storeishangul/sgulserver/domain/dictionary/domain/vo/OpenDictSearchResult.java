package org.storeishangul.sgulserver.domain.dictionary.domain.vo;


import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OpenDictSearchResult {

    private Channel channel;

    public boolean validateEmpty() {

        return channel == null || channel.item == null || channel.item.isEmpty();
    }

    public boolean contains(String word) {

        if (validateEmpty() || word == null || word.isBlank()) {
            return false;
        }

        return channel.item.stream().anyMatch(item -> item.word.equals(word));
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Channel {

        private String title;
        private String link;
        private String description;
        private int total;
        private int start;
        private int num;
        private Long lastbuilddate;

        private List<Item> item;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item {

        private String word;
        private List<Sense> sense;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Sense {

        @JsonProperty("target_code")
        private long targetCode;

        @JsonProperty("sense_no")
        private int senseNo;

        private String definition;
        private String pos;
        private String link;

        /**
         * JSON의 "type" 필드에 매핑.
         */
        private String type;
    }
}
