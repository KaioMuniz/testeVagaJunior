package br.com.kaiomuniz.dtos;

import java.math.BigDecimal;

public class CreateOrderRequest {
    private Long clientId;
    private BigDecimal totalAmount;

    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
}
