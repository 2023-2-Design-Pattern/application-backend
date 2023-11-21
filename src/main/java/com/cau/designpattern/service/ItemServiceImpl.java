package com.cau.designpattern.service;

import com.cau.designpattern.dto.ItemDto;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {
    public ItemServiceImpl() {

    }

    @Override
    public ItemDto.GetItemRes getItem(ItemDto.GetItemReq getItemReq) {
        return null;
    }
}
