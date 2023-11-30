package com.cau.designpattern.entity;

import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
@Builder
public class ItemEntity {
    private long itemId;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ItemEntity that = (ItemEntity)o;
        return itemId == that.itemId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId);
    }
}
