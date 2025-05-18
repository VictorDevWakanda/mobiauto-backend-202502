-- Criação do banco de dados
CREATE DATABASE IF NOT EXISTS AutoDealDB default character set utf8mb4 default collate utf8mb4_general_ci;
USE AutoDealDB;

-- Criação da tabela revenda
CREATE TABLE IF NOT EXISTS revenda (
    id_revenda CHAR(36) PRIMARY KEY,
    cnpj VARCHAR(18) NOT NULL UNIQUE,
    nome_social VARCHAR(100) NOT NULL
) DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

-- Criação da tabela usuario
CREATE TABLE IF NOT EXISTS usuario (
    id_usuario CHAR(36) PRIMARY KEY,
    nome_completo VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(100) NOT NULL,
    cargo ENUM(
        'PROPRIETARIO',
        'ADMINISTRADOR',
        'GERENTE',
        'ASSISTENTE'
    ) NOT NULL,
    id_revenda CHAR(36) NOT NULL,
    FOREIGN KEY (id_revenda) REFERENCES revenda (id_revenda)
) DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

-- Criação da tabela oportunidade
CREATE TABLE IF NOT EXISTS oportunidade (
    id_oportunidade CHAR(36) PRIMARY KEY,
    version BIGINT,
    status ENUM('NOVO', 'EM_ATENDIMENTO', 'CONCLUIDO') NOT NULL,
    motivo_conclusao VARCHAR(255),
    responsavel_id_usuario CHAR(36),
    revenda_id_revenda CHAR(36),
    veiculo_marca VARCHAR(50) NOT NULL,
    veiculo_modelo VARCHAR(50) NOT NULL,
    veiculo_versao VARCHAR(50) NOT NULL,
    veiculo_ano_modelo INT NOT NULL,
    cliente_nome VARCHAR(100) NOT NULL,
    cliente_email VARCHAR(20) NOT NULL,
    cliente_telefone VARCHAR(30) NOT NULL,
    data_atribuicao DATETIME,
    data_conclusao DATETIME,
    FOREIGN KEY (responsavel_id_usuario) REFERENCES usuario (id_usuario),
    FOREIGN KEY (revenda_id_revenda) REFERENCES revenda (id_revenda)
) DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;