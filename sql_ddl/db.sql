create type user_role as enum ('ADMIN', 'MANAGER', 'ENGINEER');
create type user_status as enum ('ACTIVE', 'INACTIVE');
create type action_status as enum ('ACTIVE' , 'INACTIVE');
create type compound_status as enum ('ACTIVE' , 'INACTIVE');
create type project_status as enum ('ACTIVE' , 'INACTIVE');
create type test_case_status as enum ('ACTIVE' , 'INACTIVE');
create type test_scenario_status as enum ('ACTIVE' , 'INACTIVE');
create type test_case_result as enum ('FAILED' , 'COMPLETE', 'CREATED');
create type user_project_status as enum ('WATCHER' , 'DEVELOPER');

create table users (
                       id              serial primary key not null ,
                       email           varchar(100) UNIQUE not null ,
                       password        varchar(300) not null ,
                       name            varchar(100) not null ,
                       role            user_role not null ,
                       status          user_status not null ,
                       last_request  timestamp
);

create table reset_token
(
    id           serial primary key,
    token        varchar(100),
    expiry_date  timestamp,
    user_id integer UNIQUE NOT NULL REFERENCES users(id)
);

CREATE TABLE project
(
    id serial PRIMARY KEY NOT NULL,
    name varchar(100) UNIQUE NOT NULL,
    link varchar(200) UNIQUE NOT NULL,
    status project_status NOT NULL,
    create_data timestamp NOT NULL
);

create table test_case
(
    id serial PRIMARY KEY NOT NULL,
    title varchar(100) UNIQUE NOT NULL,
    project_id integer REFERENCES project(id),
    status test_case_status NOT NULL,
    result test_case_result
);

CREATE TABLE data_set
(
    id serial PRIMARY KEY NOT NULL,
    title varchar(100) UNIQUE,
    description varchar(300),
    test_case_id integer NOT NULL REFERENCES test_case(id)
);

CREATE TABLE parameters
(
    id serial PRIMARY KEY NOT NULL,
    data_set_id integer NOT NULL REFERENCES data_set(id),
    type varchar(100) NOT NULL,
    value varchar(100) NOT NULL
);

CREATE TABLE action_parameters
(
    id serial PRIMARY KEY NOT NULL,
    parameters_id integer NOT NULL REFERENCES parameters(id)
);

CREATE TABLE user_project
(
    id serial PRIMARY KEY NOT NULL,
    project_id integer UNIQUE NOT NULL REFERENCES project(id),
    user_id integer UNIQUE NOT NULL REFERENCES users(id),
    user_status user_project_status NOT NULL
);

CREATE TABLE test_scenario
(
    id serial PRIMARY KEY NOT NULL,
    title varchar(100) UNIQUE NOT NULL,
    description varchar(300) NOT NULL,
    status test_scenario_status NOT NULL,
    test_case_id integer NOT NULL REFERENCES test_case(id)
);

create table library
(
    id serial PRIMARY KEY NOT NULL,
    description varchar(300),
    create_date timestamp not null
);

create table action
(
    id             serial PRIMARY KEY NOT NULL,
    title          varchar(100) UNIQUE NOT NULL,
    type          varchar(100) NOT NULL,
    library_id     integer NOT NULL REFERENCES library(id)
);

CREATE TABLE act_scenario
(
    id serial PRIMARY KEY NOT NULL,
    action_id integer NOT NULL REFERENCES action(id),
    test_scenario_id integer NOT NULL REFERENCES test_scenario(id),
    priority integer UNIQUE NOT NULL,
    action_status action_status NOT NULL,
    act_param_id integer UNIQUE NOT NULL REFERENCES action_parameters(id)
);

create table compound
(
    id serial PRIMARY KEY NOT NULL,
    title varchar(100) UNIQUE NOT NULL,
    library_id integer NOT NULL REFERENCES library(id)
);

create table comp_act_prior
(
    id serial PRIMARY KEY NOT NULL,
    action_id integer NOT NULL REFERENCES action(id),
    comp_id integer NOT NULL REFERENCES compound(id),
    priority integer UNIQUE NOT NULL
);

CREATE TABLE comp_scenario
(
    id serial PRIMARY KEY NOT NULL,
    compound_id integer NOT NULL REFERENCES compound(id),
    test_scenario_id integer NOT NULL REFERENCES test_scenario(id),
    priority integer UNIQUE NOT NULL,
    comp_status compound_status NOT NULL
);