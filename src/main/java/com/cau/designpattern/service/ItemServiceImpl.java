package com.cau.designpattern.service;

import com.cau.designpattern.dto.ItemDto;
import com.cau.designpattern.repository.UserGameRepository;
import com.cau.designpattern.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {
    private final UserRepository userRepository;
    private final UserGameRepository userGameRepository;

    public ItemServiceImpl(UserRepository userRepository, UserGameRepository userGameRepository) {
        this.userRepository = userRepository;
        this.userGameRepository = userGameRepository;
    }

    @Override
    public ItemDto.GetItemRes getItem(ItemDto.GetItemReq getItemReq) {
        long userId = userRepository.getOneByName(getItemReq.getName())
                .orElseThrow(RuntimeException::new).getUserId();
        long userGameId = userGameRepository.findByUserIdAndGameBoardId(userId, getItemReq.getRound())
                .orElseThrow(RuntimeException::new).getUserGameId();

        return null;
    }
}
