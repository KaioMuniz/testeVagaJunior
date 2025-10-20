package br.com.kaiomuniz.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return new ResponseEntity<>(service.createOrder(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> listOrders(
            @RequestParam(required = false) Long clientId,
            @RequestParam int page,
            @RequestParam int size) {

        if(clientId == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(service.listOrders(clientId, page, size));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> payOrder(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.payOrder(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}
