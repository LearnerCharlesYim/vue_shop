package com.charles.service;

import com.charles.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> findByConditionAndPage(Object obj);

    void save(Order order);
}
