package com.backend.productservice.repository;

import com.backend.productservice.entity.Invention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface InventionRepository extends JpaRepository<Invention, Long> {
    Optional<Invention> findByInventionId(Long inventionId);
    List<Invention> findByInventorId(Long inventorId);
    
    @Modifying
    @Transactional
    @Query("UPDATE Invention i SET i.investorId = :investorId, i.isLive = :isLive WHERE i.inventionId = :inventionId")
    int updateInvestorAndLiveStatus(
        @Param("inventionId") Long inventionId, 
        @Param("investorId") Long investorId, 
        @Param("isLive") Boolean isLive
    );
}
