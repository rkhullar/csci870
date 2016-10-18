/*
 * @author  : Rajan Khullar
 * @created : 10/17/16
 * @updated : 10/17/16
 */

delete from dbo.actor where id in (select * from dbo.signup);
