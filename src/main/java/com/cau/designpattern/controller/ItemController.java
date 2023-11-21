package com.cau.designpattern.controller;

import com.cau.designpattern.dto.ItemDto;

public interface ItemController {
    public ItemDto.GetItemRes getItem(ItemDto.GetItemReq getItemReq);
}
