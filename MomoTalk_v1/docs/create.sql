create schema if not exists momotalk ;
use momotalk;
create table if not exists file
(
    hash     varchar(60) not null
        primary key,
    filename text        not null
);

create table if not exists `group`
(
    group_name   varchar(100)                                  not null,
    group_id     varchar(30)                                   not null
        primary key,
    avatar       varchar(30)                                   null,
    introduction varchar(200) default '快来写点什么介绍一下吧' not null,
    created_time datetime                                      not null,
    is_deleted   tinyint      default 0                        null
);

create table if not exists group_member
(
    group_id  varchar(30)              not null,
    username  varchar(45)              not null,
    privilege varchar(10) default 'no' not null,
    primary key (group_id, username)
);

create table if not exists message
(
    uid        bigint                      not null
        primary key,
    group_id   varchar(60)                 not null,
    type       varchar(20)                 not null,
    sender     varchar(60) charset utf8mb3 not null,
    content    text                        not null,
    is_deleted tinyint default 0           not null
);

create table if not exists user
(
    username      varchar(50)              not null
        primary key,
    password      varchar(32)              not null,
    privilege     varchar(10) default 'no' not null,
    register_time datetime                 null
);

