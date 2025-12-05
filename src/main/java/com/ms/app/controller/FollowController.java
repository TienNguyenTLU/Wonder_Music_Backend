package com.ms.app.controller;

import com.ms.app.dto.FollowDTO;
import com.ms.app.model.Follow;
import com.ms.app.service.FollowService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/follows")
public class FollowController {
        private final FollowService service;
        public FollowController(FollowService service) {
                this.service = service;
        }
        @PostMapping
        public Follow create(@RequestBody FollowDTO follow) {
                return service.create(follow);
        }
        @DeleteMapping("/{id}")
        public void delete(Long id) {
                service.delete(id);
        }
        @GetMapping("/artist/{artistId}")
        public List<Follow> findByArtistId(@PathVariable Long artistId) {
                return service.findByArtistId(artistId);
        }
        @GetMapping("/user/{userId}")
        public List<Follow> findByUserId(@PathVariable Long userId) {
                return service.findByUserId(userId);
        }
}
