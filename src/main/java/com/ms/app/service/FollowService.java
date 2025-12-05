package com.ms.app.service;

import com.ms.app.dto.FollowDTO;
import com.ms.app.model.Follow;
import org.springframework.stereotype.Service;

import java.util.List;

public interface FollowService {
        Follow create(FollowDTO follow);
        void delete(Long id);
        List<Follow> findByArtistId(Long artistId);
        List<Follow> findByUserId(Long userId);
}
