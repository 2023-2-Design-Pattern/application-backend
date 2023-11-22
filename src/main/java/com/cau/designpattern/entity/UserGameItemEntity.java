package com.cau.designpattern.entity;

import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
@Builder
public class UserGameItemEntity {
    private long userGameItemId;
    private long userGameId;
    private long itemId;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserGameItemEntity that = (UserGameItemEntity)o;
        return userGameItemId == that.userGameItemId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userGameItemId);
    }
}
