package bro.repository;

import bro.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemEntityRepository extends JpaRepository<ItemEntity, Integer> {

    List<ItemEntity> findBySeller_UsernameAndActiveTrue(String username);

    List<ItemEntity> findByItemNameContainingIgnoreCase(String keyword);

    List<ItemEntity> findByTypeContainingIgnoreCase(String type);

    List<ItemEntity> findByActiveTrue();


}
