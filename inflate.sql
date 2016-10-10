/*
 * @author  : Rajan Khullar
 * @created : 09/08/16
 * @updated : 10/10/16
 */

insert into dbo.item(name) values ('apple'), ('banana');

select new.actor('Rajan', 'Khullar', 'rkhullar@nyit.edu');

select new.scan(1473328800, '00:00:00:00:00:00'::macaddr, -100::smallint);

/*insert into dbo.scan(select now()::timestamp, '00:00:00:00:00:00'::macaddr, -100);*/
/*select new.scan(1473328800, '00:00:00:00:00:01'::macaddr, -100::smallint);*/

/*
select '00:00:00:00:00:01'::macaddr;
*/
