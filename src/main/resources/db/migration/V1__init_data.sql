create sequence hibernate_sequence start 2 increment 1;

create table access
(
    id   int8 not null,
    name varchar(255),
    primary key (id)
);

create table lifetime
(
    id      int8 not null,
    minutes int8,
    name    varchar(255),
    primary key (id)
);

create table paste
(
    id          int8 not null,
    user_id     int8 null,
    date_create timestamp,
    dead_time   timestamp,
    description varchar(512),
    hash_code   varchar(255),
    name        varchar(255),
    access_id   int8 not null,
    lifetime_id int8 not null,
    primary key (id)
);

create table user_roles
(
    user_id int8 not null,
    role_id int8 not null
);

create table roles
(
    id   int8 not null,
    name varchar(20)
);

create table users
(
    id         int8 not null,
    user_name  varchar(20) unique,
    password   varchar(512),
    first_name varchar(30),
    last_name  varchar(30),
    email      varchar(30),
    primary key (id)
);

create table comment
(
    id       int8 not null,
    user_id  int8,
    paste_id int8 not null,
    text     varchar(512),
    primary key (id)
);

alter table
    if exists paste
    add
    constraint access_fk foreign key (access_id) references access;

alter table
    if exists paste
    add
    constraint lifetime_fk foreign key (lifetime_id) references lifetime;

alter table
    if exists comment
    add
    constraint paste_id_fk foreign key (paste_id) references paste;

insert into access(id, name)
values (1, 'public');

insert into access(id, name)
values (2, 'unlisted');

insert into access(id, name)
values (3, 'private');

insert into lifetime(id, name, minutes)
values (1, '10 мин.', 10);

insert into lifetime(id, name, minutes)
values (2, '1 час', 60);

insert into lifetime(id, name, minutes)
values (3, '3 часа', 180);

insert into lifetime(id, name, minutes)
values (4, '1 день', 1440);

insert into lifetime(id, name, minutes)
values (5, '1 неделя', 10080);

insert into lifetime(id, name, minutes)
values (6, '1 месяц', 43200);

insert into lifetime(id, name, minutes)
values (7, 'без ограничения', null);

insert into roles(id, name)
values (1, 'USER');

insert into roles(id, name)
values (2, 'ADMIN');

--password for ADMIN is admin

insert into users(id, user_name, password, first_name, last_name, email)
values (1,
        'ADMIN',
        '$2a$10$QimV3h4/2/oJ6HxHu.yO7OyUwSf42G6KjOkwCPFm1XuBEvJntgayq',
        'Vlad',
        'Markov',
        'vlad@mail.ru');

insert into user_roles(user_id, role_id)
values (1, 1);

insert into user_roles(user_id, role_id)
values (1, 2);
