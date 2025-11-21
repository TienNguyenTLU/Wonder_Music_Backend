package com.ms.app.controller;

import com.ms.app.model.History;
import com.ms.app.service.HistoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history")
public class HistoryController {

    private final HistoryService service;

    public HistoryController(HistoryService service) {
        this.service = service;
    }

    @PostMapping
    public History create(@RequestBody History history) {
        return service.create(history);
    }

    @GetMapping("/user/{id}")
    public List<History> getByUser(@PathVariable Long id) {
        return service.findByUser(id);
    }
}
