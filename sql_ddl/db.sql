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
    reset_token_id  integer
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
create table data_set
(
  id serial primary key not null ,
  title varchar(10) unique not null ,
  description varchar(50),
  id_test_case integer not null
);
-- need to add : "FOREIGN KEY (id_test_case) REFERENCES test_case(id)" when table test_case will be created
create table parameters
(
  id serial primary key not null ,
  id_data_set integer not null,
  type varchar(10) not null,
  value varchar(10) not null,
  FOREIGN KEY (id_data_set) REFERENCES data_set(id)
);
