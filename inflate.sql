/*
 * @author  : Rajan Khullar
 * @created : 09/08/16
 * @updated : 09/28/16
 */

insert into dbo.item(name) values ('apple'), ('banana');

select new.actor('Rajan', 'Khullar', 'rkhullar@nyit.edu');

/*
insert into dbo.data (select now()::timestamp, '00:00:00:00:00:00'::macaddr, -100);
select new.data(1473328800, '00:00:00:00:00:01'::macaddr, -100::smallint);
*/

/*
select '00:00:00:00:00:01'::macaddr;
*/