/*
 * @author  : Rajan Khullar
 * @created : 09/08/16
 * @updated : 09/28/16
 */

create function new.actor(dbo.actor.fname%type, dbo.actor.lname%type, dbo.actor.email%type, ptxt text default 'aaaaaa')
  returns integer as $$
  declare
    sval text;
    pval text;
    x integer;
  begin
    select gen_salt('bf') into sval;
    select crypt(ptxt, sval) into pval;
    insert into dbo.actor(fname, lname, email, salt, pswd) values ($1, $2, $3, sval, pval) returning id into x;
      return x;
  end;
$$ language plpgsql;

create function new.data(integer, dbo.wpa.id%type, dbo.data.level%type)
  returns void as $$
  insert into dbo.data values (to_timestamp($1) at time zone 'UTC', $2, $3);
$$ language sql;
