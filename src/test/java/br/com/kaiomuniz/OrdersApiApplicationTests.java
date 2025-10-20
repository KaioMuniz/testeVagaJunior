package br.com.kaiomuniz;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import br.com.kaiomuniz.dtos.CreateOrderRequest;
import br.com.kaiomuniz.dtos.OrderResponse;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrdersApiApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private static final Faker faker = new Faker(new Locale("pt-BR"));

    private static Long createdOrderId;

    @Test
    @Order(1)
    void deveCriarUmPedidoComSucesso() throws Exception {
        var request = new CreateOrderRequest();
        request.setClientId(faker.number().numberBetween(1L, 100L));
        request.setTotalAmount(BigDecimal.valueOf(faker.number().randomDouble(2, 50, 500)));

        mockMvc.perform(
                post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request))
        )
        .andExpect(status().isCreated())
        .andDo(result -> {
            String json = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
            var response = mapper.readValue(json, OrderResponse.class);

            assertNotNull(response);
            assertNotNull(response.getId());
            assertEquals(response.getClientId(), request.getClientId());
            assertEquals(response.getTotalAmount(), request.getTotalAmount());
            assertEquals(response.getStatus(), "PENDING");

            createdOrderId = response.getId();
        });
    }


    @Test
    @Order(2)
    void deveAtualizarStatusParaPaid() throws Exception {
        mockMvc.perform(
                patch("/orders/" + createdOrderId)
                        .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andDo(result -> {
            String json = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
            var updated = mapper.readValue(json, OrderResponse.class);

            assertNotNull(updated);
            assertEquals(updated.getId(), createdOrderId);
            assertEquals(updated.getStatus(), "PAID");
        });
    }

    @Test
    @Order(3)
    void deveRetornar404AoAtualizarPedidoInexistente() throws Exception {
        mockMvc.perform(
                patch("/orders/999999")
                        .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isNotFound())
        .andDo(result -> {
            String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
            assertTrue(content.contains("não encontrado"));
        });
    }

    @Test
    @Order(4)
    void deveRetornar400ParaClienteNaoInformadoNoGet() throws Exception {
        mockMvc.perform(
                get("/orders")
                        .param("page", "0")
                        .param("size", "5")
                        .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isBadRequest());
    }
}
