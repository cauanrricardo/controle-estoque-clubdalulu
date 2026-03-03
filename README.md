# Controle de Estoque — Club da Lulu

Sistema **web** desenvolvido para **automatizar o controle de estoque da empresa Club da Lulu**.

Antes, o controle era feito **no papel**, o que dificultava a organização, aumentava a chance de erro e atrasava o processo.  
Com este projeto, agora é possível **acessar pelo celular ou computador**, registrar e consultar o estoque de forma rápida e centralizada.

---

## Acesso online (Deploy)

A aplicação está publicada no Render e pode ser acessada aqui:

- **URL:** https://controle-estoque-clubdalulu.onrender.com/

---

## Tecnologias utilizadas

### Backend
- **Java 17**
- **Spring Boot**

### Frontend (Server-side rendering)
- **Thymeleaf**
- **Bootstrap**

### Banco de dados
- **PostgreSQL** (hospedado no Render)

### Testes
- **JUnit 5**
- **Mockito**

### Deploy
- **Render** (aplicação + banco)

---

## Motivação (por que esse projeto existe?)

Este sistema foi criado para resolver um problema real do dia a dia da **Club da Lulu**:  
o estoque era controlado manualmente em papel.

A ideia foi transformar isso em uma solução prática e acessível, permitindo que a equipe **consulte e atualize o estoque direto do celular**, sem depender de anotações físicas.

---

## Funcionalidades (visão geral)

- Cadastro e gerenciamento de produtos
- Atualização de quantidades (entrada/saída)
- Consulta do estoque atualizada em tempo real
- Interface simples e responsiva com Bootstrap

---

## Segurança / Acesso

O sistema possui uma camada de segurança com **Spring Security**, exigindo um **PIN** para acessar as funcionalidades.

- O **PIN é exclusivo da dona da empresa**
- Por segurança, o PIN **não é disponibilizado** no repositório nem neste README
  
---

## Como rodar localmente

### Pré-requisitos
- **Java 17**
- **Maven**
- **PostgreSQL** (local) ou usar as configurações apontando para um banco externo

### Passo a passo

1. Clone o repositório:
   ```bash
   git clone https://github.com/cauanrricardo/controle-estoque-clubdalulu.git
   ```

2. Entre na pasta do projeto:
   ```bash
   cd controle-estoque-clubdalulu
   ```

3. Configure as variáveis de ambiente / `application.properties` / `application.yml` com seu acesso ao PostgreSQL (URL, usuário e senha).

4. Rode a aplicação:
   ```bash
   mvn spring-boot:run
   ```

5. Acesse no navegador:
   - `http://localhost:8080`

---

## Rodando os testes

Para executar os testes automatizados:

```bash
mvn test
```

---

## Deploy no Render (resumo)

O projeto está hospedado no **Render**, com:
- aplicação Spring Boot
- banco **PostgreSQL** gerenciado/hospedado no Render

Assim, para usar em produção basta acessar o link (inclusive pelo celular):
https://controle-estoque-clubdalulu.onrender.com/

---

## Autor

**cauanrricardo**  
GitHub: https://github.com/cauanrricardo  
Deploy: https://controle-estoque-clubdalulu.onrender.com/  
Repositório: https://github.com/cauanrricardo/controle-estoque-clubdalulu
