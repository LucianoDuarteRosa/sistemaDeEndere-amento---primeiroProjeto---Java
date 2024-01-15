/* Executar cada bloco separado*/

CREATE TABLE produtos (
  CodInt INT(5) NOT NULL ,
  Descricao VARCHAR(60) NOT NULL,
  QuantMinVenda DOUBLE NOT NULL,
  CodBarras VARCHAR(20),
  Fornecedor VARCHAR(60) NOT NULL,
  Estoque DOUBLE NOT NULL,
  Custo DOUBLE,
  CodFab VARCHAR(60),
  QuantCx INT(5),
  PRIMARY KEY (CodInt)
);

CREATE TABLE enderecamento (
  Id int(20) NOT NULL AUTO_INCREMENT,
  CodInt INT(5) NOT NULL ,
  Descricao VARCHAR(60) NOT NULL,
  QuantMinVenda DOUBLE NOT NULL,
  CodBarras VARCHAR(20),
  CodFab VARCHAR(60),
  Ton VARCHAR(10) NOT NULL,
  Bitola VARCHAR(10)NOT NULL,
  Rua INT(11) NOT NULL,
  Predio INT(11) NOT NULL,
  Observacao VARCHAR(60),
  Obs2 VARCHAR(60),
  Disponivel BOOLEAN NOT NULL,
  Lista INT(11),
  Fornecedor VARCHAR(60),
  Inventario INT(1),
  PRIMARY KEY (Id)
);

CREATE TABLE lista (
Id int(20) NOT NULL AUTO_INCREMENT,
Nome varchar(20) NOT NULL,
Disponivel BOOLEAN NOT NULL,
PRIMARY KEY (Id)
);

CREATE TABLE predio (
  Id int(20) NOT NULL AUTO_INCREMENT,
  Rua INT(11) NOT NULL,
  Predio INT(11) NOT NULL,
  Vagas INT(2),
  PRIMARY KEY (ID),
  FOREIGN KEY (Rua) REFERENCES rua (Rua)
);

CREATE TABLE rua(
  Rua INT(11) NOT NULL,
  PRIMARY KEY (Rua)
);

CREATE TABLE Fornecedor(
Id int(20) NOT NULL AUTO_INCREMENT,
RazaoSocial varchar(60) NOT NULL,
CNPJ varchar(60) NOT NULL,
PRIMARY KEY (Id)
);

CREATE TABLE Caminhos(
Tela varchar(10) NOT NULL,
Caminho varchar(300),
PRIMARY KEY (Tela)
);

INSERT INTO produtos.caminhos (Tela, Caminho) VALUES ('nome', '');
INSERT INTO produtos.caminhos (Tela, Caminho) VALUES ('exp', '');
INSERT INTO produtos.caminhos (Tela, Caminho) VALUES ('config', '');