drop table roles;

drop table user_roles;

alter table users
    add column role varchar(30);