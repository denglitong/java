create database test;

create table employee
(
    id    int auto_increment
        primary key,
    name  varchar(128) not null,
    age   int          null,
    email varchar(128) null,
    constraint id
        unique (id)
);

create table user
(
    id         int auto_increment
        primary key,
    name  varchar(128) not null,
    email      varchar(128) not null,
    age        int          null,
    grade      int          null,
    is_deleted tinyint(1)   not null,
    constraint id
        unique (id)
);