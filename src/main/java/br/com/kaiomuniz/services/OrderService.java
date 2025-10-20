package br.com.kaiomuniz.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.kaiomuniz.dtos.CreateOrderRequest;
import br.com.kaiomuniz.dtos.OrderResponse;
import br.com.kaiomuniz.entities.Order;
import br.com.kaiomuniz.exceptions.BadRequestException;
import br.com.kaiomuniz.exceptions.ConflictException;
import br.com.kaiomuniz.exceptions.ResourceNotFoundException;
import br.com.kaiomuniz.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public OrderResponse createOrder(CreateOrderRequest request) {
        if(request.getClientId() == null || request.getTotalAmount() == null) {
            throw new BadRequestException("clientId e totalAmount são obrigatórios");
        }

        Order order = Order.builder()
                .clientId(request.getClientId())
                .totalAmount(request.getTotalAmount())
                .status(Order.Status.PENDING)
                .build();

        Order saved = repository.save(order);
        return mapToResponse(saved);
    }

    public List<OrderResponse> listOrders(Long clientId, int page, int size) {
        if(clientId == null) throw new BadRequestException("clientId é obrigatório");
        return repository.findByClientId(clientId, PageRequest.of(page, size))
                         .stream()
                         .map(this::mapToResponse)
                         .collect(Collectors.toList());
    }

    public OrderResponse payOrder(Long orderId) {
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

        if(order.getStatus() == Order.Status.PAID) {
            throw new ConflictException("Pedido já está pago");
        }

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
