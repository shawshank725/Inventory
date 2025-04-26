package bro.service;

import bro.entity.ItemEntity;
import bro.repository.ItemEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService{

    @Autowired
    private final ItemEntityRepository itemEntityRepository;

    public ItemServiceImpl(ItemEntityRepository itemEntityRepository){
        this.itemEntityRepository = itemEntityRepository;
    }

    @Override
    public List<ItemEntity> findAll() {
        return itemEntityRepository.findAll();
    }

    @Override
    public ItemEntity findById(int theId) {
        Optional<ItemEntity> result = itemEntityRepository.findById(theId);
        ItemEntity itemEntity = null;

        if (result.isPresent()){
            itemEntity = result.get();
        }
        else {
            System.out.println("could not find the ITEM");
        }
        return itemEntity;
    }

    @Override
    @Transactional
    public ItemEntity save(ItemEntity itemEntity) {
        return itemEntityRepository.save(itemEntity);
    }

    @Override
    @Transactional
    public void delete(int theId) {
        //itemEntityRepository.deleteById(theId);
        Optional<ItemEntity> result = itemEntityRepository.findById(theId);
        if (result.isPresent()){
            ItemEntity itemEntity = result.get();
            itemEntity.setActive(false);
            itemEntityRepository.save(itemEntity);
        }
        else {
            throw new RuntimeException("Item not found");
        }
    }

    @Override
    public List<ItemEntity> findBySeller_Username(String username) {
        return itemEntityRepository.findBySeller_UsernameAndActiveTrue(username);
    }

    @Override
    public List<ItemEntity> findByKeyword(String keyword) {
        return itemEntityRepository.findByItemNameContainingIgnoreCase(keyword);
    }

    @Override
    public List<ItemEntity> findByType(String type) {
        return itemEntityRepository.findByTypeContainingIgnoreCase(type);
    }

    @Override
    public List<ItemEntity> findItemsByActiveTrue() {
        return itemEntityRepository.findByActiveTrue();
    }
}
