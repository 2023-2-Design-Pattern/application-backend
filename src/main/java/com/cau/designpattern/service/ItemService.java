package com.cau.designpattern.service;

import com.cau.designpattern.dto.ItemDto;

public interface ItemService {
    public ItemDto.GetItemRes getItem(ItemDto.GetItemReq getItemReq);
}
