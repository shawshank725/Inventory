package bro.service;

import bro.entity.ItemEntity;
import bro.entity.OrderEntity;
import bro.entity.UserEntity;

import java.util.List;

public interface OrderService {

    List<OrderEntity> findByBuyer(UserEntity user);

    OrderEntity save(OrderEntity orderEntity);
}
