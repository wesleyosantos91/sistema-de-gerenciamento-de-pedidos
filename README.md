<div align="center" width="100%">
    <img src="asserts/logo-wos.jpg" alt="logo" width="200" height="auto" />
</div>

</br>

<div align="center">

![](https://img.shields.io/badge/Status-Em%20Desenvolvimento-orange)
</div>

<div align="center">

![](https://img.shields.io/badge/Autor-Wesley%20Oliveira%20Santos-brightgreen)

<p>
  <a href="https://github.com/wesleyosantos91/sistema-de-gerenciamento-de-pedidos/graphs/contributors">
    <img src="https://img.shields.io/github/contributors/wesleyosantos91/sistema-de-gerenciamento-de-pedidos" alt="contributors" />
  </a>
  <a href="">
    <img src="https://img.shields.io/github/last-commit/wesleyosantos91/sistema-de-gerenciamento-de-pedidos" alt="last update" />
  </a>
  <a href="https://github.com/wesleyosantos91/sistema-de-gerenciamento-de-pedidos/network/members">
    <img src="https://img.shields.io/github/forks/wesleyosantos91/sistema-de-gerenciamento-de-pedidos" alt="forks" />
  </a>
  <a href="https://github.com/wesleyosantos91/sistema-de-gerenciamento-de-pedidos/stargazers">
    <img src="https://img.shields.io/github/stars/wesleyosantos91/sistema-de-gerenciamento-de-pedidos" alt="stars" />
  </a>
  <a href="https://github.com/wesleyosantos91/sistema-de-gerenciamento-de-pedidos/issues/">
    <img src="https://img.shields.io/github/issues/wesleyosantos91/sistema-de-gerenciamento-de-pedidos" alt="open issues" />
  </a>
  <a href="https://github.com/wesleyosantos91/sistema-de-gerenciamento-de-pedidos/pulls/">
    <img src="https://img.shields.io/github/issues-pr/wesleyosantos91/sistema-de-gerenciamento-de-pedidos" alt="pull requests" />
  </a>
  <a href="https://github.com/wesleyosantos91/sistema-de-gerenciamento-de-pedidos/blob/main/LICENSE">
    <img src="https://img.shields.io/github/license/wesleyosantos91/sistema-de-gerenciamento-de-pedidos" alt="license" />
  </a>
</p>

</div>


# 📦 Desafio Técnico - Sistema de Gerenciamento de Pedidos

## 📌 Descrição

Este desafio consiste na criação de um sistema de gerenciamento de pedidos para um pequeno e-commerce. O sistema deve permitir o cadastro de clientes, produtos e pedidos, garantindo a consistência dos dados.

O candidato pode escolher entre **Java com Spring Boot**, **Java com Quarkus** ou **Golang** para a implementação.

Desafio gerado por Chat GPT para estudo

---

## 📂 Modulos Implementados do Desafio

O repositório contém implementações separadas por módulos, permitindo a escolha entre diferentes stacks tecnológicas:

- **`modulo-go`** → Implementação em [Golang](./modulo-go)
- **`modulo-quarkus`** → Implementação em [Java com Quarkus](./modulo-quarkus)
- **`modulo-spring`** → Implementação em [Java com Spring Boot](./modulo-spring)

Cada módulo possui seu próprio `README.md` com instruções detalhadas para execução.

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

## 🛠️ Tecnologias Esperadas

O candidato pode escolher **Java (Spring Boot ou Quarkus)** ou **Golang**.

### **Opção 1: Java com Spring Boot**
- Java 21+
- Spring Boot 3
- Spring Web (API REST)
- Spring Data JPA (ORM)
- PostgreSQL ou H2 (para testes)
- Bean Validation
- JUnit 5 + Mockito + Testcontainers
- Springdoc OpenAPI (Swagger)

### **Opção 2: Java com Quarkus**
- Java 21+
- Quarkus RESTEasy
- Hibernate ORM Panache
- PostgreSQL ou H2 (para testes)
- JUnit 5 + Mockito + RestAssured + Testcontainers
- SmallRye OpenAPI

### **Opção 3: Golang**
- Go 1.24+
- Gin (API REST)
- GORM (ORM)
- PostgreSQL
- Testing package + Testcontainers-Go
- Swaggo (Swagger)

---

## ✅ Critérios de Avaliação
1. **Correção**: A aplicação deve atender aos requisitos descritos.
2. **Boas Práticas**: Código limpo, modular e bem estruturado.
3. **Uso adequado da stack escolhida**.
4. **Cobertura de testes** (unitários e de integração).
5. **Tratamento de erros** adequado.
6. **README bem documentado** explicando como rodar o projeto.

---

## 🚀 Como Entregar o Desafio
1. Faça um **fork** deste repositório.
2. Implemente a solução na linguagem e framework escolhidos.
3. Inclua um **README** explicando como rodar o projeto.
4. Envie o link do seu repositório para avaliação.

Boa sorte! 🚀
