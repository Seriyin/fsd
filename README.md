# Fundamentos de Sistemas Distribuídos #

## Sistema de Gestão de uma livraria ##

-----------------------------------------

### Entidades ###

O sistema deve incluir as seguintes entidades:

   - Livro - Dá acesso à informação do livro (título, autor, ...).
   - Livraria - Permite pesquisar livros e consultar o histórico de compras.
   - Carrinho - Permite organizar vários livros para serem comprados.
   - Banco - Permite registar um pagamento e consulta o histórico de pagamentos.

--------------------------------------------

### Operações ###

1. A operação típica a efetuar por um cliente é:  procura de livros numa livraria, adição de livros a
um carrinho e confirmação da compra indicando um banco para pagamento.

2. O sistema tem que permitir que o código cliente,  a livraria e o banco executem em processos
diferentes.
   Poderá permitir também que o armazenamento da informação dos livros e das contas bancárias estejam distribuidas por mais do que um processo.

3. O sistema proposto tem que garantir que a operação de compra é atómica, mesmo com vários clientes concorrentes e re-início de qualquer dos processos. Poderá também garantir que o cliente
pode fazer progresso sem que o processo do banco esteja disponível.

4.  A resolução proposta deve ser modular, maximizando o código que é independente desta aplicação em concreto, e adequado à utilização em grande escala, sem gargalos ou limitações.
