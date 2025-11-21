package com.ms.app.service;

import com.ms.app.model.History;
import com.ms.app.repository.HistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository repo;

    public HistoryServiceImpl(HistoryRepository repo) {
        this.repo = repo;
    }

    @Override
    public History create(History history) {
        return repo.save(history);
    }

    @Override
    public List<History> findByUser(Long id) {
        return repo.findByUserId(id);
    }
}
