package bro.repository;

import bro.entity.ItemEntity;
import bro.entity.OrderEntity;
import bro.entity.UserEntity;
import org.hibernate.query.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderEntityRepository extends JpaRepository<OrderEntity, Integer> {

    List<OrderEntity> findByBuyer(UserEntity user);

}
