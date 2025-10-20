package br.com.kaiomuniz.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.kaiomuniz.dtos.CreateOrderRequest;
import br.com.kaiomuniz.dtos.OrderResponse;
import br.com.kaiomuniz.services.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody CreateOrderRequest request) {
        OrderResponse response = service.createOrder(request);
        return ResponseEntity.status(201).body(response); // 201 Created
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> listOrders(
            @RequestParam(required = false) Long clientId,
            @RequestParam int page,
            @RequestParam int size) {
        List<OrderResponse> orders = service.listOrders(clientId, page, size);
        return ResponseEntity.ok(orders); // 200 OK
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrderResponse> payOrder(@PathVariable Long id) {
        OrderResponse response = service.payOrder(id);
        return ResponseEntity.ok(response); // 200 OK
    }
}
