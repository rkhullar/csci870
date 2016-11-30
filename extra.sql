/*
 * @author  : Rajan Khullar
 * @created : 11/27/16
 * @updated : 11/30/16
 */

/*location vs count*/
select building, floor, room, count(*)
from dbv.scan
where room<>'any'
group by building, floor, room
having count(*) >= 1000
order by count(*) desc;

/*bssid vs count*/
select bssid, count(*)
from dbv.scan
where room <> 'any'
group by bssid
having count(*) >= 1000
order by count(*) desc;

/*time vs count*/
select
  to_char(time - interval '5 hours', 'd') as day,
  to_char(time - interval '5 hours', 'HH24') as hour,
  to_char(time - interval '5 hours', 'MI')::smallint/15 as quarter,
  count(*)
from dbo.scan
group by day, hour, quarter
having count(*) >= 1000
order by count(*) desc;

/*time vs count*/
select
  to_char(time - interval '5 hours', 'HH24') as hour,
  count(*)
from dbo.scan
group by hour
having count(*) >= 1000
order by count(*) desc;

/*location and time vs count*/
select
  l.building, l.floor, l.room,
  to_char(s.time - interval '5 hours', 'HH24') as hour,
  count(*)
from dbo.scan s, dbv.location l
where s.locationID = l.id and l.room <> 'any'
group by building, floor, room, hour
having count(*) >= 1000
order by count(*) desc;
