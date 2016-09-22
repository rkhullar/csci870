/*
 * @author  : Rajan Khullar
 * @created : 09/08/16
 * @updated : 09/15/16
 */

create extension if not exists pgcrypto;

drop schema if exists dbo cascade;
drop schema if exists dbv cascade;
drop schema if exists new cascade;
drop schema if exists map cascade;

create schema dbo;
create schema dbv;
create schema new;
create schema map;

/* Stage 0 */
create table dbo.item
(
  id serial primary key,
  name varchar(50) not null
);

/* Stage 1 */

create table dbo.wpa
(
    id serial primary key,
    bssid macaddr
);

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

create table dbo.data
(
    time timestamp,
    wpaID serial references dbo.wpa(id),
    level smallint,
    locationID serial references dbo.location(id)
);

create function new.data(integer, dbo.wpa.id%type, dbo.data.level%type)
  returns void as $$
  insert into dbo.data values (to_timestamp($1) at time zone 'UTC', $2, $3);
$$ language sql;
