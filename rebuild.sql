/*
 * @author  : Rajan Khullar
 * @created : 09/08/16
 * @updated : 10/18/16
 */

create extension if not exists pgcrypto;

drop schema if exists dbo cascade;
drop schema if exists dbv cascade;
drop schema if exists new cascade;
drop schema if exists map cascade;
drop schema if exists fnd cascade;

create schema dbo;
create schema dbv;
create schema new;
create schema map;
create schema fnd;

/************************************************/
create table dbo.actor
(
    id serial primary key,
    fname varchar(20) not null,
    lname varchar(20) not null,
    email varchar(50) not null unique,
    token text,
    salt  text not null,
    pswd  text not null
);

create table dbo.signup
(
    id serial references dbo.actor(id) on delete cascade,
    primary key (id)
);

create view dbv.signup as
    select a.id, fname, lname, email, token, salt, pswd
    from dbo.actor a, dbo.signup s
    where a.id = s.id;

create view dbv.user as
    select * from dbo.actor where id not in (select id from dbo.signup);

create table dbo.admin
(
    id serial references dbo.actor(id) on delete cascade,
    primary key (id)
);

create view dbv.admin as
    select * from dbo.actor where id in (select id from dbo.admin);
/************************************************/

/************************************************/
create table dbo.wpa
(
    id serial primary key,
    bssid macaddr unique
);
/************************************************/

/************************************************/
create table dbo.building
(
    id serial primary key,
    abbr char(4) not null
);

create table dbo.location
(
    id serial primary key,
    buildingID serial references dbo.building(id),
    floor smallint not null,
    room varchar(20) not null,
    unique(buildingID, floor, room)
);

create view dbv.location as
    select l.id, b.abbr as building, l.floor, l.room
    from dbo.building b, dbo.location l
    where l.buildingID = b.id;
/************************************************/

/************************************************/
create table dbo.scan
(
    time timestamp not null default now(),
    actorID serial references dbo.actor(id) not null,
    wpaID serial references dbo.wpa(id) not null,
    level smallint not null,
    locationID serial references dbo.location(id) not null
);

create view dbv.scan as
    select extract(epoch from s.time) as uxt, a.email, w.bssid, s.level, l.building, l.floor, l.room
    from dbo.scan s, dbo.actor a, dbo.wpa w, dbv.location l
    where s.actorID = a.id and s.wpaID = w.id and s.locationID = l.id;
/************************************************/
