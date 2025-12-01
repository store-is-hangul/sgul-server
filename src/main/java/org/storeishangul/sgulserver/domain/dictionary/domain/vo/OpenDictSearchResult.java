package org.storeishangul.sgulserver.domain.dictionary.domain.vo;


import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OpenDictSearchResult {

    private Channel channel;

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

        private List<Item> item;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item {

        private String word;
        private Sense sense;
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
