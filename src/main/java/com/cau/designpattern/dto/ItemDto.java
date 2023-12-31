package com.cau.designpattern.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

public class ItemDto {
    @Getter
    @ToString
    public static class GetItemReq {
        private String name;
        private long round;
        private long itemId;
    }

    @Getter
    @ToString
    public static class UseItemReq {
        private String name;
        private long round;
        private long userGameItemId;
    }

    @Getter
    @ToString
    public static class RushItemReq {
        private String name;
        private long round;
        private long userGameItemId1;
        private long userGameItemId2;
    }

    @Getter
    @Builder
    @ToString
    public static class ItemRes {
        private List<Item> items;

        @Getter
        @Builder
        @ToString
        public static class Item {
            private long userGameItemId;
            private long itemId;
        }
    }
}
