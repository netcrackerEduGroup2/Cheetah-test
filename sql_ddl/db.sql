create type user_role as enum ('admin', 'manager', 'engineer');
create type user_status as enum ('active', 'inactive');
create type action_status as enum ('active' , 'inactive');
create type compound_status as enum ('active' , 'inactive');

create table users (
                       id              serial primary key not null ,
                       email           varchar(100) not null ,
                       password        varchar(300) not null ,
                       name            varchar(100) not null ,
                       role            user_role not null ,
                       status          user_status not null ,
                       reset_token_id  integer,
                       last_request  timestamp
);

create table reset_token
(
    id           serial primary key,
    token        varchar(50),
    expiry_date  timestamp,
    user_id integer
);

create table actions
(
    id             serial primary key not null,
    title          varchar(10)        not null,
    description    varchar(50),
    id_compound     integer,
    id_test_scenario integer,
    status         action_status not null

);

create table compounds
(
    id serial primary key  not null ,
    title varchar(10),
    description    varchar(50),
    id_test_scenario integer,
    status         action_status not null
);

create table lib_act_compound
(
    id_library integer,
    id_compound integer,
    id_action integer
);

create table library
(
    id serial primary key  not null,
    description varchar(50),
    name varchar(10) not null,
    create_date timestamp not null
);

create table project
(
    id          serial         not null
        constraint project_id_pk
            primary key,
    name        varchar(50)    not null,
    link        varchar(150)   not null,
    status      project_status not null,
    create_date timestamp      not null,
    owner_id    integer
        constraint user_id_fk
            references users
);

create table user_project
(
    id          serial      not null
        constraint user_project_pk
            primary key,
    project_id  integer
        constraint user_project_id_fk
            references project,
    user_id     integer
        constraint user_id_fk
            references users,
    user_status user_status not null
);