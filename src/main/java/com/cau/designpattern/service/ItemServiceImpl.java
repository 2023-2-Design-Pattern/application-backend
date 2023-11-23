package com.cau.designpattern.service;

import com.cau.designpattern.dto.ItemDto;
import com.cau.designpattern.entity.UserGameItemEntity;
import com.cau.designpattern.repository.UserGameItemRepository;
import com.cau.designpattern.repository.UserGameRepository;
import com.cau.designpattern.repository.UserRepository;
import org.springframework.stereotype.Service;

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

    private ItemDto.GetItemRes getAllUserGameItemsResponse(long userGameId) {
        List<ItemDto.GetItemRes.Item> items = null;
        List<UserGameItemEntity> list = userGameItemRepository.getAllUserGameItems(userGameId);

        for(UserGameItemEntity userGameItem : list) {
            ItemDto.GetItemRes.Item item = ItemDto.GetItemRes.Item.builder()
                    .userGameItemId(userGameItem.getUserGameItemId())
                    .itemId(userGameItem.getItemId())
                    .build();
            items.add(item);
        }
        ItemDto.GetItemRes res = ItemDto.GetItemRes.builder()
                .items(items)
                .build();

        return res;
    }

    @Override
    public ItemDto.GetItemRes getItem(ItemDto.GetItemReq getItemReq) {
        long userId = userRepository.getOneByName(getItemReq.getName())
                .orElseThrow(RuntimeException::new).getUserId();
        long userGameId = userGameRepository.findByUserIdAndGameBoardId(userId, getItemReq.getRound())
                .orElseThrow(RuntimeException::new).getUserGameId();

        userGameItemRepository.getItem(userGameId, getItemReq.getItemId());

        return getAllUserGameItemsResponse(userGameId);
    }
}
