/*
 * @author  : Rajan Khullar
 * @created : 09/08/16
 * @updated : 09/28/16
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

/************************************************/
create table dbo.item
(
  id serial primary key,
  name varchar(50) not null
);
/************************************************/

/************************************************/
create table dbo.actor
(
  id serial primary key,
  fname varchar(20) not null,
  lname varchar(20) not null,
  email varchar(50) not null unique,
  salt  text not null,
  pswd  text not null
);

create table dbo.register
(
  id serial references dbo.actor(id),
  primary key (id)
);
/************************************************/

/************************************************/
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
/************************************************/

/************************************************/
create table dbo.data
(
  time timestamp,
  wpaID serial references dbo.wpa(id),
  level smallint,
  locationID serial references dbo.location(id)
);
/************************************************/
