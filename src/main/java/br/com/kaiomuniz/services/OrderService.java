package br.com.kaiomuniz.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.kaiomuniz.entities.Order;
import br.com.kaiomuniz.repository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    public Order createOrder(Order order) {
        return repository.save(order);
    }

    public Page<Order> getOrdersByClientId(Long clientId, int page, int size) {
        return repository.findByClientId(clientId, PageRequest.of(page - 1, size));
    }

    public Optional<Order> updateStatus(Long id, Order.Status status) {
        return repository.findById(id).map(order -> {
            order.setStatus(status);
            return repository.save(order);
        });
    }
}
