-- Criação do banco de dados
CREATE DATABASE IF NOT EXISTS AutoDealDB default character set utf8mb4 default collate utf8mb4_general_ci;
-- Uso do banco de dados
USE AutoDealDB;

-- Criação da tabela revenda
CREATE TABLE IF NOT EXISTS revenda (
    id_revenda BINARY(16) PRIMARY KEY,
    cnpj VARCHAR(14) NOT NULL UNIQUE,
    nome_social VARCHAR(255) NOT NULL
) DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

-- Criação da tabela usuario
CREATE TABLE IF NOT EXISTS usuario (
    id_usuario BINARY(16) PRIMARY KEY,
    nome_completo VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    cargo ENUM(
        'PROPRIETARIO',
        'ADMINISTRADOR',
        'GERENTE',
        'ASSISTENTE'
    ) NOT NULL,
    id_revenda BINARY(16) NOT NULL,
    FOREIGN KEY (id_revenda) REFERENCES revenda (id_revenda)
) DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;