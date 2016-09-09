/*
 * @author  : Rajan Khullar
 * @created : 09/08/16
 * @updated : 09/08/16
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

/* Stage 1 */

create table dbo.data
(
    time timestamp,
    ssid char(17),
    level smallint
);

create function new.data(integer, dbo.data.ssid%type, dbo.data.level%type)
  returns void as $$
  insert into dbo.data values (to_timestamp($1) at time zone 'UTC', $2, $3);
$$ language sql;