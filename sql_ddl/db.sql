create type user_role as enum ('ADMIN', 'MANAGER', 'ENGINEER');
create type user_status as enum ('ACTIVE', 'INACTIVE');
create type action_status as enum ('ACTIVE' , 'INACTIVE');
create type compound_status as enum ('ACTIVE' , 'INACTIVE');
create type project_status as enum ('ACTIVE' , 'INACTIVE');
create type test_case_status as enum ('ACTIVE' , 'INACTIVE');
create type test_scenario_status as enum ('ACTIVE' , 'INACTIVE');
create type test_case_result as enum ('FAILED' , 'COMPLETE', 'CREATED');
create type user_project_status as enum ('WATCHER' , 'DEVELOPER');

create table users
(
    id           serial primary key  not null,
    email        varchar(100) UNIQUE not null,
    password     varchar(300)        not null,
    name         varchar(100)        not null,
    role         user_role           not null,
    status       user_status         not null,
    last_request timestamp                   ,
    photo_url     varchar(300)
);

create table reset_token
(
    id          serial primary key,
    token       varchar(100),
    expiry_date timestamp,
    user_id     integer UNIQUE NOT NULL REFERENCES users (id)
);

CREATE TABLE project
(
    id          serial PRIMARY KEY  NOT NULL,

    title       varchar(100) UNIQUE NOT NULL,
    link        varchar(200) UNIQUE NOT NULL,
    status      project_status      NOT NULL,
    create_date timestamp           NOT NULL
);

create table test_case
(
    id                  serial           not null
        constraint test_case_pkey
            primary key,
    title               varchar(100)     not null
        constraint test_case_title_key
            unique,
    project_id          integer
        constraint test_case_project_id_fkey
            references project,
    status              test_case_status not null,
    result              test_case_result not null,
    execution_cron_date varchar(50),
    repeatable          boolean
);

CREATE TABLE data_set
(
    id           serial PRIMARY KEY NOT NULL,
    title        varchar(100) UNIQUE,
    description  varchar(300),
    test_case_id integer            NOT NULL REFERENCES test_case (id)
        on update cascade on delete cascade

);

CREATE TABLE parameters
(
    id          serial PRIMARY KEY  NOT NULL,
    data_set_id integer             NOT NULL REFERENCES data_set (id)
        on update cascade on delete cascade,
    type        varchar(100) UNIQUE NOT NULL,
    value       varchar(100)        NOT NULL
);

CREATE TABLE user_project
(
    id          serial PRIMARY KEY  NOT NULL,

    project_id  integer      NOT NULL REFERENCES project (id),
    user_id     integer      NOT NULL REFERENCES users (id),
    user_status user_project_status NOT NULL
);

CREATE TABLE test_scenario
(
    id           serial PRIMARY KEY   NOT NULL,
    title        varchar(100) UNIQUE  NOT NULL,
    description  varchar(300)         NOT NULL,
    status       test_scenario_status NOT NULL,
    test_case_id integer              NOT NULL REFERENCES test_case (id)
);

create table action
(
    id          serial PRIMARY KEY  NOT NULL,
    title       varchar(100) UNIQUE NOT NULL,
    type        varchar(100)        NOT NULL,
    description varchar(300)
);

CREATE TABLE act_scenario
(
    id               serial PRIMARY KEY NOT NULL,
    action_id        integer            NOT NULL REFERENCES action (id),
    test_scenario_id integer            NOT NULL REFERENCES test_scenario (id),
    priority         integer            NOT NULL,
    action_status    action_status      NOT NULL,
    param_id         integer REFERENCES parameters (id)
        on update cascade on delete cascade


);

create table compound
(
    id          serial PRIMARY KEY  NOT NULL,
    title       varchar(100) UNIQUE NOT NULL,
    description varchar(300)
);

create table comp_act_prior
(
    id        serial PRIMARY KEY NOT NULL,
    action_id integer            NOT NULL REFERENCES action (id),
    comp_id   integer            NOT NULL REFERENCES compound (id),
    priority  integer            NOT NULL
);

CREATE TABLE history_test_case
(
    id              serial PRIMARY KEY  NOT NULL,
    result          test_case_result    NOT NULL,
    date_completed  timestamp           NOT NULL,
    id_test_case    integer             NOT NULL REFERENCES test_case(id)
);

CREATE TABLE action_result
(
  id              serial PRIMARY KEY  NOT NULL,
  compound_id     integer,
  result          varchar(100)        NOT NULL,
  screenshot_url  varchar(100)        NOT NULL,
  general_order    integer             NOT NULL,
  id_history_test_case    integer     NOT NULL REFERENCES history_test_case(id),
  action_element          varchar(100)        NOT NULL,
  argument         varchar(100)        NOT NULL,
  id_action       integer              NOT NULL REFERENCES action (id)
);

