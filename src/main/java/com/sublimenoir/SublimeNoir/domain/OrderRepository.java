package com.sublimenoir.SublimeNoir.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long>
{
    List<Order> findByUser(User user);

    List<Order> findByStatus(OrderStatus status);
}
