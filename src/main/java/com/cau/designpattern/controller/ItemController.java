package com.cau.designpattern.controller;

import com.cau.designpattern.dto.ItemDto;

public interface ItemController {
    public ItemDto.ItemRes getItem(ItemDto.GetItemReq getItemReq);
    public ItemDto.ItemRes useItem(ItemDto.UseItemReq useItemReq);
}
