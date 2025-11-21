package com.ms.app.repository;
import com.ms.app.model.History;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {

    List<History> findByUserId(Long userId);



    List<History> findBySongId(Long songId);
}
