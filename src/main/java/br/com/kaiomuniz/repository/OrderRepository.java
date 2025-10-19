package br.com.kaiomuniz.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.kaiomuniz.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByClientId(Long clientId, Pageable pageable);
}
