package br.com.kaiomuniz.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.kaiomuniz.dtos.CreateOrderRequest;
import br.com.kaiomuniz.dtos.OrderResponse;
import br.com.kaiomuniz.entities.Order;
import br.com.kaiomuniz.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public OrderResponse createOrder(CreateOrderRequest request) {
        Order order = Order.builder()
                .clientId(request.getClientId())
                .totalAmount(request.getTotalAmount())
                .status(Order.Status.PENDING)
                .build();

        Order saved = repository.save(order);
        return mapToResponse(saved);
    }

    public List<OrderResponse> listOrders(Long clientId, int page, int size) {
        return repository.findByClientId(clientId, PageRequest.of(page, size))
                         .stream()
                         .map(this::mapToResponse)
                         .collect(Collectors.toList());
    }

    public OrderResponse payOrder(Long orderId) throws Exception {
        Optional<Order> optional = repository.findById(orderId);
        if(optional.isEmpty()) {
            throw new Exception("Pedido não encontrado");
        }
        Order order = optional.get();
        order.setStatus(Order.Status.PAID);
        return mapToResponse(repository.save(order));
    }

    private OrderResponse mapToResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setClientId(order.getClientId());
        response.setTotalAmount(order.getTotalAmount());
        response.setStatus(order.getStatus().name());
        return response;
    }
}
