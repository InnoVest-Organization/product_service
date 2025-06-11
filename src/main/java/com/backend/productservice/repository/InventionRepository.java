package com.backend.productservice.repository;

import com.backend.productservice.entity.Invention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface InventionRepository extends JpaRepository<Invention, Long> {
    Optional<Invention> findByInventionId(Long inventionId);
    List<Invention> findByInventorId(Long inventorId);
}
