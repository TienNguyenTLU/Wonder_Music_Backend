package com.ms.app.service;

import com.ms.app.model.History;

import java.util.List;

public interface HistoryService {
    History create(History history);
    List<History> findByUser(Long id);
}
