alter table paste
    drop constraint access_fk;

alter table paste
    drop constraint lifetime_fk;

alter table paste
    rename COLUMN access_id to access;

alter table paste
    alter column access type varchar(20);

alter table paste
    rename COLUMN lifetime_id to lifetime;

alter table paste
    alter column lifetime type varchar(20);

drop table access;
drop table lifetime;
