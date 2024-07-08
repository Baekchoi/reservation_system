package com.example.reservation_system.controller;

import com.example.reservation_system.entity.Store;
import com.example.reservation_system.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
public class StoreController {

    @Autowired
    private StoreService storeService;

    // 매장 정보 등록
    @PostMapping
    public Store createStore(@RequestBody Store store) {
        return storeService.createStore(store);
    }

    // 매장 정보 수정
    @PutMapping("/update")
    public Store updateStore(@RequestParam String storeName, @RequestBody Store storeDetails) {
        return storeService.updateStore(storeName, storeDetails);
    }

    // 매장 정보 삭제
    @DeleteMapping("/delete")
    public void deleteStore(@RequestParam String storeName) {
        storeService.deleteStore(storeName);
    }

    // 모든 매장 조회
    @GetMapping
    public List<Store> getAllStores() {
        return storeService.getAllStores();
    }

    // 매장 이름으로 조회
    @GetMapping("/search")
    public Store getStoreByStoreName(@RequestParam String storeName) {
        return storeService.getStoreByStoreName(storeName);
    }

}
