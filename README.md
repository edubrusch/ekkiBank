# ekkiBank

Desafio Tecnológico Phi / 4All para desenvolvedores.

Com interesse de desempenhar o papel de desenvolvedor Java Backend no Squad Processamenteo da PHi, foi desenvolvido este programa, para demonstrar conhecimento em desenvolvimento Java e frameworks necessários para exercer a função.

Foi preparada a estrutura de pastas para criar todos os componentes (Backend, FrontEnd) porém o foco desta Demonstração será o backend, excluindo a construção do BFF.

## Tecnologias

O projeto do serviço foi criado Utilizando [SpringBoot Initializr] (https://start.spring.io/), Utilizando a versão do Springframework-starter-parent 2.2.0, e tendo todas dependências gerenciadas por este parent.

A construção do projeto utiliza Maven 3.3+
O código foi desenvolvido para Java 11
Testes unitários feitos com Junit 4+ (provido pelo pacote do spring boot) e Mockito
O banco utilizado é o H2, para poder ser executado sem a instalação de um banco de dados na máquina, e possui um script com dados para teste

## Execução e teste

requisitos: JDK 11, servidor Spring Boot ou IDE com plugn que execute Spring Boot, maven 3.3+

Para executar o projeto, pode ser feito pela IDE com Plugin do Spring Boot (no eclipse criar uma configuração de execução) ou fazer deploy em um servidor Spring Boot instalado na máquina.
Para executar os testes unitários, pode ser executado pela IDE (classe TransferValidationServiceImplTest.java) ou utilizando o maven com o comando mvn clean test

## Utilização da aplicação

Uma vez no ar, é possível exercitar os endpoints da aplicação.

Os endpoints são:

{{host}}:8080/creditCards/   (endpoint de cadastro de carões)

{{host}}:8080/balance/<account> (verificação do saldo de uma conta)

{{host}}:8080/transfer  (transferência entre contas)

{{host}}:8080/favoureds/ (cadastro de favorecido)


Para utilizar os endpoints o projeto dispõe de um projeto Postman.
para utilizar a aplicação via postman basta criar um ambiente no postman onde localhost é a máquina onde está sendo testado o projeto, ou trocar a variável {{localhost}} pelo nome da máquina. A porta padrão é 8080


## Implementação

O desafio de Full stack contém um app em React, um BFF e Serviço em Java. Tendo em vista a função pretendida dentro do squad (dev backend Java) foi construído apenas a parte do serviço Java.

As funcionalidades Implementadas:

* Home que liste o saldo atual da conta.
* CRUD de cartão de crédito.
* CRUD de contatos/favorecidos.
* Transferência de dinheiro entre usuários.

### Implementação dos serviços

Os CRUDS de favorecido e cartão de crédito foram criados com a ajuda da anotação _@RepositoryRestResource_ do pacote  _org.springframework.data_ Esta anotação permite criar de uma vez só todo o CRUD para uma determinada entity. O CRUD vem no formato HATEOAS, que possui links de referência. Neste momento foi tomada a decisão de manter o formato HATEOAS apenas para estes CRUDS, pois seria necessario mais tempo para personalizar os CRUDS removendo os links ou alterando os demais serviços para implementar o padrão. Desta forma, foi decidido que pelo menos todo objeto retornaria um objeto customizado de resposta, com informações relevantes.Assim ficaria mais fácil terminar implementação do HATEOAS no futuro. Esta decisão foi tomada tendo em vista a praticidade e diminuição do tempo de desenvolvimento.

### Escolha do banco de dados

Da mesma forma foi orientado a escolha do banco de dados. o H2 é um banco de dados que é carregado em memória, elimininando portanto a necessidade de criar um bando ce dados em um servidor para executar o projeto. Se fosse adotar outro banco de dados, bastaria alterar as configurações no arquivo _application.properties_

### Logs

Para ajudar na implementação foi configurado o logback com duas saidas: saída para o SYSOUT e para um arquivo na raiz do projeto. Foi utilizado durante o desenvolvimento porém no momento não há nenhum log sendo exibido.
