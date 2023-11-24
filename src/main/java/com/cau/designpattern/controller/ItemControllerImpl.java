package com.cau.designpattern.controller;

import com.cau.designpattern.dto.ItemDto;
import com.cau.designpattern.service.ItemService;
import org.springframework.web.bind.annotation.*;

@RestController
public class ItemControllerImpl implements ItemController {
    private final ItemService itemService;

    public ItemControllerImpl(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    @PostMapping("/item/get")
    public ItemDto.ItemRes getItem(@RequestBody ItemDto.GetItemReq getItemReq) {
        return itemService.getItem(getItemReq);
    }

    @Override
    @DeleteMapping("/item/use")
    public ItemDto.ItemRes useItem(@RequestBody ItemDto.UseItemReq useItemReq) {
        return itemService.useItem(useItemReq);
    }

    @Override
    @PostMapping("item/rush")
    public ItemDto.ItemRes rushItem(@RequestBody ItemDto.RushItemReq rushItemReq) {
        return itemService.rushItem(rushItemReq);
    }
}
