--liquibase formatted sql

--changeset <author>>:<id>
create table users (
    id          uuid,
    full_name   text                    not null,
    phone       text,
    email       text                    not null,
    -- System fields
    created_at  timestamp default now() not null,
    updated_at  timestamp default now() not null,
    archived_at timestamp,
    deleted_at  timestamp,
    primary key (id)
);

comment on table users is 'Users';

comment on column users.id is 'Primary id';
comment on column users.full_name is 'Full name';
comment on column users.phone is 'Mobile number';
comment on column users.email is 'E-mail';

-- Системные поля
comment on column users.created_at is 'Date and time of creating a record';
comment on column users.updated_at is 'Date and time of recording renewal';
comment on column users.archived_at is 'Date and time of record archiving';
comment on column users.deleted_at is 'Date and time of removal time';
