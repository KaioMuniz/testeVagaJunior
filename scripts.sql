-- Cria a tabela orders caso não exista
CREATE TABLE IF NOT EXISTS orders (
    id SERIAL PRIMARY KEY,            -- ID auto-increment
    client_id BIGINT NOT NULL,        -- ID do cliente
    total_amount NUMERIC(10,2) NOT NULL, -- Valor total do pedido
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING' -- Status do pedido, padrão PENDING
);

-- Insere dados de exemplo
INSERT INTO orders (client_id, total_amount, status) VALUES
(42, 150.75, 'PENDING'),
(42, 200.50, 'PAID'),
(42, 99.99, 'PENDING'),
(43, 300.00, 'PENDING'),
(43, 450.25, 'PAID'),
(44, 120.00, 'PENDING'),
(44, 220.10, 'PAID'),
(45, 500.00, 'PENDING'),
(42, 75.50, 'PAID'),
(42, 180.00, 'PENDING');

-- Lista todos os pedidos ordenados pelo ID
SELECT * FROM orders ORDER BY id;

-- Lista pedidos de um cliente específico
SELECT * FROM orders WHERE client_id = 42 ORDER BY id;

-- Conta quantos pedidos existem por status
SELECT status, COUNT(*) AS total
FROM orders
GROUP BY status;

-- Atualiza o status de um pedido específico (simula PATCH)
UPDATE orders
SET status = 'PAID'
WHERE id = 3;

-- Consulta paginada: cliente 42, página 2, tamanho 3
SELECT * FROM orders
WHERE client_id = 42
ORDER BY id
OFFSET 3 LIMIT 3;  -- pula os 3 primeiros registros e retorna os próximos 3
