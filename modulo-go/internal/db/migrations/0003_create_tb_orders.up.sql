CREATE TABLE tb_orders
(
    id          UUID PRIMARY KEY        DEFAULT gen_random_uuid(),
    customer_id UUID           NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    created_at  TIMESTAMP               DEFAULT now(),
    CONSTRAINT fk_orders_customer FOREIGN KEY (customer_id) REFERENCES tb_customers (id) ON DELETE CASCADE
);