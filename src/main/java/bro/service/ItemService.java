package bro.service;

import bro.entity.ItemEntity;

import java.util.List;

public interface ItemService {

    List<ItemEntity> findAll();

    ItemEntity findById(int theId);

    ItemEntity save(ItemEntity itemEntity);

    void delete(int theId);

    List<ItemEntity> findBySeller_Username(String username);

    List<ItemEntity> findByKeyword(String keyword);

    List<ItemEntity> findByType(String type);

    List<ItemEntity> findItemsByActiveTrue();
}
