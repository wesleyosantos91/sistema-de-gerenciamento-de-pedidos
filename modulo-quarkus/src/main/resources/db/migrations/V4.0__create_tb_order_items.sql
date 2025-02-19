CREATE TABLE tb_order_items
(
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id    UUID           NOT NULL,
    product_id  UUID           NOT NULL,
    quantity    INTEGER        NOT NULL,
    unit_price  DECIMAL(10, 2) NOT NULL,
    total_price DECIMAL(10, 2) GENERATED ALWAYS AS (quantity * unit_price) STORED,
    CONSTRAINT fk_order_items_order FOREIGN KEY (order_id) REFERENCES tb_orders (id) ON DELETE CASCADE,
    CONSTRAINT fk_order_items_product FOREIGN KEY (product_id) REFERENCES tb_products (id) ON DELETE CASCADE
);