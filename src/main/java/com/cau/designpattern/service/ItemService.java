package com.cau.designpattern.service;

import com.cau.designpattern.dto.ItemDto;

public interface ItemService {
    public ItemDto.ItemRes getItem(ItemDto.GetItemReq getItemReq);
    public ItemDto.ItemRes useItem(ItemDto.UseItemReq useItemReq);
    public ItemDto.ItemRes rushItem(ItemDto.RushItemReq rushItemReq);
}
