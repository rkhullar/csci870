/*
 * @author  : Rajan Khullar
 * @created : 11/27/16
 * @updated : 12/04/16
 */

drop schema if exists cnt cascade;
drop schema if exists get cascade;

create schema cnt;
create schema get;

/************************************************/
create view cnt.L as
    select building, floor, room, count(*)
    from dbv.scan
    where room <> 'any'
    group by building, floor, room
    having count(*) >= 1000
    order by count(*) desc;

create function get.L(dbo.building.abbr%type, dbo.location.floor%type, dbo.location.room%type)
  returns table(bssid macaddr, level smallint) as $$
  declare
    x integer;
  begin
    select fnd.location($1, $2, $3) into x;
    return query
    select w.bssid, s.level
    from dbo.wap w, dbo.scan s
    where s.locationID = x and s.wapID = w.id;
  end;
$$ language plpgsql;
/************************************************/

/************************************************/
create view cnt.W as
    select bssid, count(*)
    from dbv.scan
    where room <> 'any'
    group by bssid
    having count(*) >= 1000
    order by count(*) desc;
/************************************************/

/************************************************/
create view cnt.T as
    select
      to_char(time - interval '5 hours', 'HH24')::smallint as hour,
      count(*)
    from dbo.scan
    group by hour
    having count(*) >= 1000
    order by count(*) desc;

create view cnt.TT as
    select
      to_char(time - interval '5 hours', 'd')::smallint as dow,
      to_char(time - interval '5 hours', 'HH24')::smallint as hour,
      to_char(time - interval '5 hours', 'MI')::smallint/15 as quarter,
      count(*)
    from dbo.scan
    group by dow, hour, quarter
    having count(*) >= 1000
    order by count(*) desc;
/************************************************/

/************************************************/
create view cnt.LT as
    select
      l.building, l.floor, l.room,
      to_char(s.time - interval '5 hours', 'HH24')::smallint as hour,
      count(*)
    from dbo.scan s, dbv.location l
    where s.locationID = l.id and l.room <> 'any'
    group by building, floor, room, hour
    having count(*) >= 1000
    order by count(*) desc;

create function get.LT(dbo.building.abbr%type, dbo.location.floor%type, dbo.location.room%type, smallint)
  returns table(bssid macaddr, level smallint) as $$
  declare
    x integer;
  begin
    select fnd.location($1, $2, $3) into x;
    return query
    select w.bssid, s.level
    from dbo.wap w, dbo.scan s
    where s.locationID = x and s.wapID = w.id
      and to_char(s.time - interval '5 hours', 'HH24')::smallint = $4;
  end;
$$ language plpgsql;
/************************************************/
