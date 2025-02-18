# 📦 Desafio Técnico - Sistema de Gerenciamento de Pedidos

## 📌 Descrição

Este desafio consiste na criação de um sistema de gerenciamento de pedidos para um pequeno e-commerce. O sistema deve permitir o cadastro de clientes, produtos e pedidos, garantindo a consistência dos dados.

O candidato pode escolher entre **Java com Spring Boot**, **Java com Quarkus** ou **Golang** para a implementação.

Desafio Técnico gerado por Chat GPT para estudo.

---

## ⚙️ Requisitos

### 1️⃣ **Funcionalidades**

✅ **Clientes**
- Criar um cliente
- Buscar cliente por ID
- Listar todos os clientes
- Atualizar cliente
- Deletar cliente (apenas se não houver pedidos)

✅ **Produtos**
- Criar um produto
- Buscar produto por ID
- Listar todos os produtos
- Atualizar produto
- Deletar produto

✅ **Pedidos**
- Criar um pedido
    - Deve validar se o cliente existe
    - Deve verificar se há estoque suficiente dos produtos
    - O estoque dos produtos deve ser atualizado após o pedido
- Buscar pedido por ID (incluindo itens do pedido)
- Listar todos os pedidos
- Atualizar pedido (somente se ainda não estiver finalizado)
- Deletar pedido

---

### 2️⃣ **Modelagem de Dados**

📌 **Clientes (`customers`)**
- `id` (UUID, chave primária)
- `name` (String, não nulo)
- `email` (String, único e não nulo)
- `created_at` (Timestamp)

📌 **Produtos (`products`)**
- `id` (UUID, chave primária)
- `name` (String, não nulo)
- `price` (BigDecimal, não nulo)
- `stock_quantity` (Integer, não nulo)
- `created_at` (Timestamp)

📌 **Pedidos (`orders`)**
- `id` (UUID, chave primária)
- `customer_id` (UUID, chave estrangeira para `customers`)
- `total_price` (BigDecimal, calculado automaticamente)
- `created_at` (Timestamp)

📌 **Itens do Pedido (`order_items`)**
- `id` (UUID, chave primária)
- `order_id` (UUID, chave estrangeira para `orders`)
- `product_id` (UUID, chave estrangeira para `products`)
- `quantity` (Integer, não nulo)
- `unit_price` (BigDecimal, registrado no momento da compra)
- `total_price` (BigDecimal, `quantity * unit_price`)

---

### 3️⃣ **Regras de Negócio**
- O **total do pedido** deve ser **calculado automaticamente**.
- Um pedido **só pode ser criado se houver estoque suficiente** para os produtos.
- Ao criar um pedido, o **estoque dos produtos deve ser atualizado** corretamente.
- **Não é possível excluir um cliente que já tenha pedidos.**

---

## ✅ Critérios de Avaliação

1. **Correção**: A aplicação deve atender aos requisitos descritos.
2. **Boas Práticas**: Código limpo, modular e bem estruturado.
3. **Uso adequado da stack escolhida**.
4. **Cobertura de testes** (unitários e de integração).
5. **Tratamento de erros** adequado.
6. **README bem documentado** explicando como rodar o projeto.
