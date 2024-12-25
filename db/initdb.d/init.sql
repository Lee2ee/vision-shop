-- Databse 생성
CREATE DATABASE shopdb;

-- 시작 DB에 접근 가능한 유저 생성
CREATE USER 'shopuser'@'%' IDENTIFIED BY 'P@ssw0rd';
GRANT ALL PRIVILEGES ON shopdb.* TO 'shopuser'@'%';

FLUSH PRIVILEGES;