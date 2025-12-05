package com.ms.app.service;

import com.ms.app.dto.FollowDTO;
import com.ms.app.model.Follow;
import com.ms.app.repository.ArtistRepository;
import com.ms.app.repository.FollowRepository;
import com.ms.app.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FollowServiceImpl implements FollowService {
        private final FollowRepository followRepo;
        private final UsersRepository userRepo;
        private final ArtistRepository artistRepo;
        public FollowServiceImpl(FollowRepository followRepo, UsersRepository userRepo, ArtistRepository artistRepo) {
                this.followRepo = followRepo;
                this.userRepo = userRepo;
                this.artistRepo = artistRepo;
        }
        @Override
        public Follow create(FollowDTO follow) {
                Follow followEntity = new Follow();
                followEntity.setUser(userRepo.findById(follow.getUserId()).orElse(null));
                followEntity.setArtist(artistRepo.findById(follow.getArtistId()).orElse(null));
                return followRepo.save(followEntity);
        }
        @Override
        public void delete(Long id) {
                followRepo.deleteById(id);
        }
        @Override
        public List<Follow> findByArtistId(Long artistId) {
                return followRepo.findByArtistId(artistId);
        }
        @Override
        public List<Follow> findByUserId(Long userId) {
                return followRepo.findByUserId(userId);
        }
}
