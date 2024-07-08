package com.example.reservation_system.service;

import com.example.reservation_system.entity.Store;
import com.example.reservation_system.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    // 매장 등록
    public Store createStore(Store store) {
        return storeRepository.save(store);
    }

    // 매장 수정 (업데이트)
    public Store updateStore(String storeName, Store storeDetails) {
        Store store = storeRepository.findByStoreNameContaining(storeName)
                .orElseThrow(() -> new RuntimeException("Store not found with name: " + storeName));
        store.setStoreName(storeDetails.getStoreName());
        store.setAddress(storeDetails.getAddress());
        store.setDescription(storeDetails.getDescription());
        return storeRepository.save(store);
    }

    // 매장 삭제
    public void deleteStore(String storeName) {
        Store store = storeRepository.findByStoreNameContaining(storeName)
                .orElseThrow(() -> new RuntimeException("Store not found with name: " + storeName));
        storeRepository.delete(store);
    }

    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    public Store getStoreByStoreName(String storeName) {
        return storeRepository.findByStoreNameContaining(storeName)
                .orElseThrow(() -> new RuntimeException("Store not found with storeName: " + storeName));
    }
}
