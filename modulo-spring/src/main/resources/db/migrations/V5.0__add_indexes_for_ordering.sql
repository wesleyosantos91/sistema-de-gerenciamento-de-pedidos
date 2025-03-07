-- Criação da extensão pg_trgm (se necessário)
CREATE EXTENSION IF NOT EXISTS pg_trgm;

-- Índice GIN para buscas parciais em `name`
CREATE INDEX IF NOT EXISTS idx_customers_name_trgm ON tb_customers USING gin (name gin_trgm_ops);

-- Índices individuais para ordenação eficiente
CREATE INDEX IF NOT EXISTS idx_customers_id ON tb_customers (id);
CREATE INDEX IF NOT EXISTS idx_customers_name ON tb_customers (name);
CREATE INDEX IF NOT EXISTS idx_customers_email ON tb_customers (email);
CREATE INDEX IF NOT EXISTS idx_customers_created_at ON tb_customers (created_at);
CREATE INDEX IF NOT EXISTS idx_customers_updated_at ON tb_customers (updated_at);
