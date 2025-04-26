package bro.service;

import bro.entity.ItemEntity;
import bro.entity.OrderEntity;
import bro.entity.UserEntity;
import bro.repository.ItemEntityRepository;
import bro.repository.OrderEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{

    private final OrderEntityRepository orderEntityRepository;

    public OrderServiceImpl(OrderEntityRepository orderEntityRepository){
        this.orderEntityRepository = orderEntityRepository;
    }

    @Override
    public List<OrderEntity> findByBuyer(UserEntity user) {
        return orderEntityRepository.findByBuyer(user);
    }

    @Override
    public OrderEntity save(OrderEntity orderEntity) {
        return orderEntityRepository.save(orderEntity);
    }
}
