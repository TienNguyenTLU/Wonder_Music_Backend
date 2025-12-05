package com.ms.app.repository;

import com.ms.app.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface FollowRepository extends JpaRepository<Follow, Long> {
        List<Follow> findByArtistId(Long artistId);
        List<Follow> findByUserId(Long userId);
}
