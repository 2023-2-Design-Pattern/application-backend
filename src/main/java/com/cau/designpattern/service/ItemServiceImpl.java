package com.cau.designpattern.service;

import com.cau.designpattern.dto.ItemDto;
import com.cau.designpattern.entity.UserGameItemEntity;
import com.cau.designpattern.repository.UserGameItemRepository;
import com.cau.designpattern.repository.UserGameRepository;
import com.cau.designpattern.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    private final UserGameItemRepository userGameItemRepository;
    private final UserRepository userRepository;
    private final UserGameRepository userGameRepository;

    public ItemServiceImpl(UserGameItemRepository userGameItemRepository,
                           UserRepository userRepository,
                           UserGameRepository userGameRepository) {
        this.userGameItemRepository = userGameItemRepository;
        this.userRepository = userRepository;
        this.userGameRepository = userGameRepository;
    }

    private ItemDto.ItemRes getAllUserGameItemsResponse(long userGameId) {
        List<ItemDto.ItemRes.Item> items = new ArrayList<>();
        List<UserGameItemEntity> list = userGameItemRepository.getAllUserGameItems(userGameId);

        for(UserGameItemEntity userGameItem : list) {
            ItemDto.ItemRes.Item item = ItemDto.ItemRes.Item.builder()
                    .userGameItemId(userGameItem.getUserGameItemId())
                    .itemId(userGameItem.getItemId())
                    .build();
            items.add(item);
        }
        ItemDto.ItemRes res = ItemDto.ItemRes.builder()
                .items(items)
                .build();

        return res;
    }

    @Override
    public ItemDto.ItemRes getItem(ItemDto.GetItemReq getItemReq) {
        long userId = userRepository.getOneByName(getItemReq.getName())
                .orElseThrow(RuntimeException::new).getUserId();
        long userGameId = userGameRepository.findByUserIdAndGameBoardId(userId, getItemReq.getRound())
                .orElseThrow(RuntimeException::new).getUserGameId();

        userGameItemRepository.getItem(userGameId, getItemReq.getItemId());

        return getAllUserGameItemsResponse(userGameId);
    }

    @Override
    public ItemDto.ItemRes useItem(ItemDto.UseItemReq useItemReq) {
        long userId = userRepository.getOneByName(useItemReq.getName())
                .orElseThrow(RuntimeException::new).getUserId();
        long userGameId = userGameRepository.findByUserIdAndGameBoardId(userId, useItemReq.getRound())
                .orElseThrow(RuntimeException::new).getUserGameId();

        userGameItemRepository.useItem(useItemReq.getUserGameItemId(), userGameId);

        return getAllUserGameItemsResponse(userGameId);
    }
}
