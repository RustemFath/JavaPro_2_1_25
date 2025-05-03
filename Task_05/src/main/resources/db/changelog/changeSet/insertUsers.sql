--liquibase formatted sql

--changeset rfatkhutdinov:insert-users
insert into users(username) values ('Fedor');
insert into users(username) values ('Roman');