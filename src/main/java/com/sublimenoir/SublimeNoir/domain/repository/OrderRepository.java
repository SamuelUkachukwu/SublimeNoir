package com.sublimenoir.SublimeNoir.domain.repository;

import com.sublimenoir.SublimeNoir.domain.entity.OrderStatus;
import com.sublimenoir.SublimeNoir.domain.entity.Order;
import com.sublimenoir.SublimeNoir.domain.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long>
{
    List<Order> findByUser(User user);

    List<Order> findByStatus(OrderStatus status);
}
