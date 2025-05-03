--liquibase formatted sql

--changeset rfatkhutdinov:init-db
create table IF NOT EXISTS users
(
    id bigserial primary key,
    username varchar(255) unique
);
