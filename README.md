<div align="center">
  <h1>Order Management</h1>
  <p>Sistema de gerenciamento de pedidos para autoatendimento de fast food</p>

[![Kotlin Language](https://img.shields.io/static/v1?label=kotlin&message=language&color=purple&style=for-the-badge&logo=KOTLIN)](https://kotlinlang.org/)
[![Gradle Automation](https://img.shields.io/static/v1?label=gradle&message=Tool&color=darkgreen&style=for-the-badge&logo=GRADLE)](http://www.gnu.org/licenses/agpl-3.0)
[![SpringBoot Framework](https://img.shields.io/static/v1?label=springboot&message=framework&color=green&style=for-the-badge&logo=SPRINGBOOT)](https://opensource.org/licenses/)
[![Postgres Database](https://img.shields.io/static/v1?label=postgres&message=database&color=blue&style=for-the-badge&logo=POSTGRESQL)](http://www.gnu.org/licenses/agpl-3.0)
[![Flyway Migration](https://img.shields.io/static/v1?label=flyway&message=migration&color=red&style=for-the-badge&logo=FLYWAY)](http://www.gnu.org/licenses/agpl-3.0)
[![Docker Container](https://img.shields.io/static/v1?label=docker&message=container&color=lightblue&style=for-the-badge&logo=DOCKER)](http://www.gnu.org/licenses/agpl-3.0)
</div>

## Visão Geral

Projeto desenvolvido para o gerenciamento de autoatendimento de restaurantes, tendo como funcionalidades:

- Gerenciamento de pedidos
- Gerenciamento de clientes
- Gerenciamento de produtos
- Gerenciamento de pagamento

## Índice

- [Visão Geral](#visão-geral)
- [Requisitos de Negócio](#requisitos-de-negócio)
- [Arquitetura de Infraestrutura](#arquitetura-de-infraestrutura)
- [Documentação da API](#documentação-da-api)
- [Documentação da Banco de Dados](#documentação-da-banco-de-dados)
- [Desenvolvimento](#desenvolvimento)
- [Rodando Aplicação Local](#iniciando-com-docker-local)
- [Equipe](#desenvolvedores)

## Requisitos de Negócio

### Gerenciamento de Produtos
<details>
<summary>Clique para expandir</summary>

#### Cadastro de Produtos
![Cadastro de Produtos](files/cria-produto.png)

#### Atualização de Produtos
![Atualização de Produtos](files/atualiza-produto.png)

#### Consulta de Produtos
![Consulta de Produtos](files/consulta-produtos.png)

#### Produtos por Categoria
![Produtos por Categoria](files/consulta-produtos-categoria.png)

#### Consulta por ID
![Consulta por ID](files/consulta-produto.png)

#### Remoção de Produtos
![Remoção de Produtos](files/apaga-produto.png)
</details>

### Gerenciamento de Clientes
<details>
<summary>Clique para expandir</summary>

#### Cadastro de Cliente
![cria-cliente.png](files/cria-cliente.png)

#### Consulta Clientes
![Consulta de Clientes](files/consulta-usuarios.jpg)

#### Consulta cliente por identificador
![consulta-cliente.png](files/consulta-cliente.png)

#### Consulta cliente por cpf
![consulta-cliente-cpf.png](files/consulta-cliente-cpf.png)

#### Consulta cliente por email
![consulta-cliente-email.png](files/consulta-cliente-email.png)

#### Atualização de dados de Clientes
![atualiza.png](files/atualiza.png)

#### Deleta Clientes
![apaga-cliente.png](files/apaga-cliente.png)
</details>

### Gerenciamento de Pedidos
<details>
<summary>Clique para expandir</summary>

#### Cadastro de pedido
![cria-pedido.png](files/cria-pedido.png)

#### Atualização de pedido
![atualiza-pedido.png](files/atualiza-pedido.png)

#### Consulta de pedidos
![consulta-pedidos.png](files/consulta-pedidos.png)

#### Consulta pedidos por Status
![consulta-pedidos-por-status.png](files/consulta-pedidos-por-status.png)

#### Consulta por ID
![consulta-pedido.png](files/consulta-pedido.png)

#### Cancelamento de pedido
![deleta-pedido.png](files/deleta-pedido.png)
</details>

### Gerenciamento de Pagamentos
<details>
<summary>Clique para expandir</summary>

#### Consulta de pagamento por identificador
![consulta-pagamento.png](files/consulta-pagamento.png)

#### Criação de pagamento para histórico e geração de QRCode no provedor
![cria-pagamento.png](files/cria-pagamento.png)

#### Processamento de webhook do provedor
![processa-webhook.png](files/processa-webhook.png)
</details>

## Arquitetura de Infraestrutura

A arquitetura implementada segue os seguintes princípios:

- Uma **VPC** com três **subnets públicas**
- Um cluster **EKS** com dois nós
- O backend em **Kotlin** é containerizado e roda no Kubernetes
- O banco de dados **PostgreSQL** também é executado no cluster via StatefulSet
- Uso de **Secrets** e **ConfigMaps**
- Suporte a escalabilidade com **Horizontal Pod Autoscaler (HPA)**, usando o **metrics-server**
- Imagens Docker armazenadas no **ECR**
- Acesso externo ao backend via **LoadBalancer**

![consulta-pedidos.jpg](files/arquitetura-aws-v1.jpg)

---

## Documentação da API

A documentação interativa da API está disponível através do Swagger UI:
- **URL**: http://localhost:8080/swagger-ui.html

### Collection Postman

Para facilitar os testes, disponibilizamos uma collection do Postman com exemplos de requisições:

[![Run in Postman](https://run.pstmn.io/button.svg)](https://github.com/11SOAT-Tech-Challenge-Fast-Food/order-management/blob/main/postman/collections/order-management.postman_collection.json)

### Documentação Banco de Dados

**Banco de Dados escolhido**: Banco de Dados Relacional - AWS RDS

**Motivo da escolha**: Optamos por escolher um banco de relacional, devido aos fortes relacionamentos
de entidade que desenhamos em nossa regra de negócio... O Banco de dados relacional é o que trás um melhor
gerenciamento para esse fluxo.

![der.drawio.png](files/der.drawio.png)

## Desenvolvimento

### Estrutura do Projeto

```
src/
├── main/
│   ├── kotlin/
│   │   └── br/com/fiap/ordermanagement/
│   │       ├── customer/            # Separação por domínio
│   │       │  ├── common/             # Classes comuns ao mundo externo e interno
│   │       │  │  ├── dtos/               # Classes de transferência de dados
│   │       │  │  ├── interfaces/         # Contrato de implementação do mundo externo
│   │       │  │  └── mapper/             # Mapeamento de entidade para DTO e DTO para entidade
│   │       │  ├── core/               # Classes de domínio
│   │       │  │  ├── controllers/        # Entrada do mundo externo
│   │       │  │  ├── entities/           # Entidades do domínio
│   │       │  │  ├── gateways/           # Comunicação com sistemas externos
│   │       │  │  ├── presenters/         # Adaptador de dados para o mundo externo
│   │       │  │  └── usecases/           # Casos de uso da regra de negócio
│   │       │  ├── external/           # Classes de acesso externo
│   │       │  │  ├── api/                # Implementação de rest API
│   │       │  └──└── persistence/        # Implementação do banco de dados
│   │       ├── order/               # Separação por domínio de pedido - Seguindo a estrutura do customer
│   │       ├── payment/             # Separação por domínio de pagamento - Seguindo a estrutura do customer
│   │       ├── product/             # Separação por domínio de produto - Seguindo a estrutura do customer
│   │       └── OrderManagementApplication.kt
│   └── resources/
│       ├── db/migration/        # Scripts do Flyway
│       └── application.yml      # Configurações da aplicação
└── test/                        # Testes automatizados
```

### Padrões de Código

- Siga clean architecture
- Utilize nomes descritivos para variáveis e funções
- Documente funções e classes públicas
- Escreva testes unitários para novas funcionalidades


## Rodando aplicação

> **Lembrete**: Nosso repositório já está integrado com o Github Actions para realizar o deploy do kubernets, só abrir o pr para a branch Main e após o merge, será feito o deploy automáticamente

### Iniciando com Docker Local

#### Pré-requisitos

- Docker 20.10+
- Docker Compose 2.0+
- Git
- JDK 17+ (opcional, apenas para desenvolvimento)

1. **Clone o repositório**
   ```bash
   git clone https://github.com/11SOAT-Tech-Challenge-Fast-Food/order-management.git
   cd order-management
   ```

2. **Configure as variáveis de ambiente**
   ```bash
   Edite o arquivo variables-docker.env conforme necessário
   ```

3. **Inicie os containers**
   ```bash
   docker-compose -f docker/docker-compose.yml up -d
   ```

4. **Acesse a aplicação**
    - API: http://localhost:8080
    - Swagger UI: http://localhost:8080/swagger-ui.html
    - Banco de Dados: localhost:5432

5. **Parando a aplicação**
   ```bash
   docker-compose -f docker/docker-compose.yml down
   ```

---

## Desenvolvedores
| [<img loading="lazy" src="https://avatars.githubusercontent.com/u/79323910?v=4" width=115><br><sub>Bianca Vediner</sub>](https://github.com/BiaVediner) | [<img loading="lazy" src="https://avatars.githubusercontent.com/u/79324306?v=4" width=115><br><sub>Wesley Paternezi</sub>](https://github.com/WesleyPaternezi) | [<img loading="lazy" src="https://avatars.githubusercontent.com/u/61800458?v=4 " width=115><br><sub>Guilherme Paternezi</sub>](https://github.com/guilherme-paternezi) |
|:-----------------------------------------------------------------------------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------:|