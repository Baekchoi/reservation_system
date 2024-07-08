package com.example.reservation_system.repository;

import com.example.reservation_system.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    Optional<Store> findByStoreNameContaining(String storeName);
}
