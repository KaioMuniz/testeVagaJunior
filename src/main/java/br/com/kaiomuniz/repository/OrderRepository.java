package br.com.kaiomuniz.repository;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.kaiomuniz.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByClientId(Long clientId, Pageable pageable);
}
