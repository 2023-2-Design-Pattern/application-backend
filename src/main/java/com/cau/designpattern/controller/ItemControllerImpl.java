package com.cau.designpattern.controller;

import com.cau.designpattern.dto.ItemDto;
import com.cau.designpattern.service.ItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemControllerImpl implements ItemController {
    private final ItemService itemService;

    public ItemControllerImpl(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    @GetMapping("/item/get")
    public ItemDto.ItemRes getItem(@RequestBody ItemDto.GetItemReq getItemReq) {
        return itemService.getItem(getItemReq);
    }
}
