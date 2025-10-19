package br.com.kaiomuniz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.kaiomuniz.entities.Order;
import br.com.kaiomuniz.services.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order created = service.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<Page<Order>> getOrders(
            @RequestParam Long clientId,
            @RequestParam int page,
            @RequestParam int size) {
        return ResponseEntity.ok(service.getOrdersByClientId(clientId, page, size));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Order> markAsPaid(@PathVariable Long id) {
        return service.updateStatus(id, Order.Status.PAID)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
