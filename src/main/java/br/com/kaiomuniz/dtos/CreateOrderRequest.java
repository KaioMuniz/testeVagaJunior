package br.com.kaiomuniz.dtos;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;

public class CreateOrderRequest {

    @NotNull(message = "clientId é obrigatório")
    private Long clientId;

    @NotNull(message = "totalAmount é obrigatório")
    private BigDecimal totalAmount;

    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
}
