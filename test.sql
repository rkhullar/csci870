/*
 * @author  : Rajan Khullar
 * @created : 10/10/16
 * @updated : 10/10/16
 */

/*
select fnd.user('rkhullar@nyit.edu', 'aaaaaa', true, false);
select fnd.location('ANY', 0::smallint, 'any');
*/

/*
select new.scan(1473328800, 1, '00:00:00:00:00:00'::macaddr, -100::smallint, 1);
select new.scan((select extract(epoch from now())::integer), 1, '00:00:00:00:00:01'::macaddr, -100::smallint, 1);
*/

/*select * from dbv.scan;*/


/*insert into dbo.scan(select now()::timestamp, '00:00:00:00:00:00'::macaddr, -100);*/
/*select new.scan(1473328800, '00:00:00:00:00:01'::macaddr, -100::smallint);*/

/*
select '00:00:00:00:00:01'::macaddr;
*/