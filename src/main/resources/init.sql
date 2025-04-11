-- Criação do banco de dados
CREATE DATABASE IF NOT EXISTS AutoDealDB
defalt character set utf8mb4
defalt collate utf8mb4_general_ci;
-- Uso do banco de dados
USE AutoDealDB;

-- Criação da tabela usuario
CREATE TABLE IF NOT EXISTS usuario (
    id_usuario BINARY(16) PRIMARY KEY,
    nome_completo VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    cargo ENUM('PROPRIETARIO', 'ADMINISTRADOR', 'GERENTE', 'ASSISTENTE') NOT NULL
) DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;