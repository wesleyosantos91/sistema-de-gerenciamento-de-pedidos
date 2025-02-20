CREATE TABLE tb_products
(
    id             UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name           VARCHAR(255)   NOT NULL,
    price          DECIMAL(10, 2) NOT NULL,
    stock_quantity INTEGER        NOT NULL,
    created_at     TIMESTAMP        DEFAULT now(),
    updated_at     TIMESTAMP        DEFAULT now()
);