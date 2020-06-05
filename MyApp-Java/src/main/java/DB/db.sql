create database my_app;
use my_app;

create table players
(
    id            int auto_increment primary key,
    first_name    varchar(30)             not null,
    last_name     varchar(30)             not null,
    user_nick     varchar(30) unique      not null,
    creation_date timestamp default now() not null
);

create table games
(
    id            int auto_increment primary key,
    name          varchar(100) unique not null,
    creation_date timestamp default now()
);

create table matches
(
    id         int auto_increment primary key,
    game_id    int references games (id),
    player_id  int references players (id),
    score      int                     not null,
    event_date timestamp default now() not null
);
