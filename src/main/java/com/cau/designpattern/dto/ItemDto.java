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
        private int itemId;
    }

    @Getter
    @Builder
    @ToString
    public static class GetItemRes {
        private List<Item> items;

        @Getter
        @Builder
        @ToString
        public static class Item {
            private int userGameItemId;
            private int itemId;
        }
    }
}
