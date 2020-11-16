create type developer_role as enum ('admin', 'manager', 'engineer');
create type developer_status as enum ('active', 'inactive');

create table user (
    id              serial primary key not null ,
    email           varchar(100) not null ,
    password        varchar(300) not null ,
    name            varchar(100) not null ,
    role            developer_role not null ,
    status          developer_status not null ,
    reset_token_id  integer
);

create table reset_token
(
    id           serial primary key,
    token        varchar(50),
    expiry_date  timestamp,
    developer_id integer
);