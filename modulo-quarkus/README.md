<div align="center" width="100%">
    <img src="../asserts/logo-wos.jpg" alt="logo" width="200" height="auto" />
</div>

<br>

<div align="center">

![](https://img.shields.io/badge/Status-Em%20Desenvolvimento-orange)
</div>

<div align="center">

![](https://img.shields.io/badge/Autor-Wesley%20Oliveira%20Santos-brightgreen)
![](https://img.shields.io/badge/Language-java-brightgreen)
![](https://img.shields.io/badge/Framework-quarkus-brightgreen)

</div>

<div align="center">

# 📦 Desafio Técnico - Sistema de Gerenciamento de Pedidos

Confira o enunciado completo, [clicando aqui](../problem.md).

</div>

##  Pré - requisitos

- [ `Java 21+` ](https://www.oracle.com/java/technologies/downloads/#java21)
- [ `Apache Maven`](https://maven.apache.org/download.cgi)
- [ `Docker` ](https://www.docker.com/)
- [ `Docker-Compose` ](https://docs.docker.com/compose/install/)
- [ `Datadog` ](https://docs.datadoghq.com/)
- [ `Grafana k6` ](https://grafana.com/docs/k6/latest/set-up/install-k6/)

## Stack Utilizada
- **Java 21**
- **Quarkus**
- **Panache**
- **Smallrye Openapi**
- **Flyway**
- **Postgres**
- **Datadog**
- **Grafana k6 (Load testing for engineering teams)**

## Aplicações

### Modulo Spring
| Nome           | Descrição                                                                                                |
|----------------|----------------------------------------------------------------------------------------------------------|
| modulo Quarkus | Esse microserviço tem a reposnsabilidade de tratar todo core do contexto do desafio feito em quarkus. |

#### Portas
| Aplicação      | Porta |
|----------------|-------|
| modulo-quarkus | 8080  |

#### Setup

- ##### Variáveis de ambiente

| Variável de Ambiente  | Descrição                                                                      |
|-----------------------|--------------------------------------------------------------------------------|
| `DATABASE_HOST`       | Especifique o host do banco de dados `MySQL` a ser usado (padrão `localhost` ) |
| `DATABASE_PORT`       | Especifique a porta do banco de dados `MySQL` a ser usada (padrão `3306` )     |
| `DATABASE_USERNAME`   | Especifique o user do `MySQL` a ser usado (padrão `app` )                      |
| `DATABASE_PASSWORD`   | Especifique a password do `MySQL` a ser usado (padrão `app` )                  |
| `DATADOG_API_KEY`     | Especifique o api key do `DATADOG`  a ser usado (obtido no portal do datadog)  |
| `DATADOG_APP_KEY`     | Especifique o app key do `DATADOG`  a ser usado (obtido no portal do datadog)  |
| `DATADOG_URI`         | Especifique o uri do `DATADOG`  a ser usado (obtido no portal do datadog)      |


</br>
<a href="https://www.linkedin.com/in/wesleyosantos91/" target="_blank">
  <img src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white" target="_blank" />
</a>

</br>
<b>Developed by Wesley Oliveira Santos</b>