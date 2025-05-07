--liquibase formatted sql

--changeset rfatkhutdinov:create-products
create table IF NOT EXISTS products
(
    id bigserial primary key,
    account varchar(25),
    balance numeric,
    type varchar(10),
    user_id bigint references users(id)
);
