package com.cau.designpattern.controller;

import com.cau.designpattern.service.ItemService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemControllerImpl implements ItemController {
    private final ItemService itemService;

    public ItemControllerImpl(ItemService itemService) {
        this.itemService = itemService;
    }
}
