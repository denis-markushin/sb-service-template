--liquibase formatted sql

--changeset <author>>:<id>
create table @snakeCaseName@s (
    id          uuid,
    -- System fields
    created_at  timestamp default now() not null,
    updated_at  timestamp default now() not null,
    archived_at timestamp,
    deleted_at  timestamp,
    primary key (id)
);

comment on table @snakeCaseName@s is '@pascalCaseName@';

comment on column @snakeCaseName@s.id is 'Primary id';

-- Системные поля
comment on column @snakeCaseName@s.created_at is 'Date and time of creating a record';
comment on column @snakeCaseName@s.updated_at is 'Date and time of recording renewal';
comment on column @snakeCaseName@s.archived_at is 'Date and time of record archiving';
comment on column @snakeCaseName@s.deleted_at is 'Date and time of removal time';
