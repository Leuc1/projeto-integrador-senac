CREATE DATABASE PI;
USE PI;

CREATE TABLE Usuario (
    usu_id_pk INTEGER PRIMARY KEY AUTO_INCREMENT,
    usu_nome VARCHAR(100) NOT NULL,
    usu_email VARCHAR(100) UNIQUE,
    usu_cpf VARCHAR(14) UNIQUE,
    usu_senha VARCHAR(100) NOT NULL,
    usu_palavra_chave VARCHAR(100) NOT NULL,
    usu_tipo VARCHAR(20) DEFAULT 'comum' 
    );
    
CREATE TABLE Endereco (
    end_id_pk INTEGER PRIMARY KEY AUTO_INCREMENT,
    end_bairro VARCHAR(100) NOT NULL,
    end_rua VARCHAR(150) NOT NULL,
    end_cep VARCHAR(8) NOT NULL,
    end_estado VARCHAR(2) NOT NULL DEFAULT 'BA'
);

CREATE TABLE Problema (
    pro_id_pk INTEGER PRIMARY KEY AUTO_INCREMENT,
    pro_descricao VARCHAR(400) NOT NULL,
    pro_hora_envio DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    pro_local VARCHAR(255),
    pro_classificacao VARCHAR(100) NOT NULL,
    pro_risco BOOLEAN NOT NULL DEFAULT FALSE,
    pro_status VARCHAR(50) NOT NULL DEFAULT 'Em análise'
);

CREATE TABLE Anexos (
    ane_id_pk INTEGER PRIMARY KEY AUTO_INCREMENT,
    ane_tipo VARCHAR(100),
    ane_data DATE
    );

CREATE TABLE Equipe_admin (
    Admin_id_pk INTEGER PRIMARY KEY AUTO_INCREMENT
    );
    
ALTER TABLE Problema
ADD usu_id_fk INTEGER,
ADD FOREIGN KEY(usu_id_fk) REFERENCES Usuario (usu_id_pk);

ALTER TABLE Problema
ADD end_id_fk INTEGER,
ADD FOREIGN KEY(end_id_fk) REFERENCES Endereco (end_id_pk);

ALTER TABLE Anexos
ADD  pro_id_fk INTEGER,
ADD FOREIGN KEY(pro_id_fk) REFERENCES Problema(pro_id_pk);

 

CREATE VIEW usuario_relatorio AS
SELECT
    Usuario.usu_id_pk AS "id do usuario",
    Usuario.usu_nome AS "nome do usuario",
    Usuario.usu_email AS "email do usuario",
    Usuario.usu_cpf AS "cpf do usuario",
    Usuario.usu_tipo AS "tipo de usuario",
    Usuario.usu_palavra_chave AS "Palavra chave",
    Problema.pro_id_pk AS "id do problema",
    Problema.pro_descricao AS "descricao do problema",
    Problema.pro_hora_envio AS "data de envio do problema",
    Problema.pro_local AS "local do relatorio",
    Problema.pro_classificacao AS "classificacao",
    Problema.pro_risco AS "risco",
    Problema.pro_status AS "status do problema",
    Endereco.end_bairro AS "bairro",
    Endereco.end_rua AS "rua",
    Endereco.end_cep AS "cep",
    Endereco.end_estado AS "estado"
FROM Usuario 
LEFT JOIN Problema ON Usuario.usu_id_pk = Problema.usu_id_fk
LEFT JOIN Endereco ON Problema.end_id_fk = Endereco.end_id_pk;

SELECT * FROM usuario_relatorio;
