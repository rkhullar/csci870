/*
 * @author  : Rajan Khullar
 * @created : 09/08/16
 * @updated : 10/09/16
 */

create function new.actor(dbo.actor.fname%type, dbo.actor.lname%type, dbo.actor.email%type, ptxt varchar(72) default 'aaaaaa')
  returns integer as $$
  declare
    sval text;
    pval text;
    tval text;
    x integer;
  begin
    select gen_salt('bf') into sval;
    select crypt(ptxt::text, sval) into pval;
    select encode(digest($3 || gen_salt('bf'), 'sha256'), 'base64') into tval;
    insert into dbo.actor(fname, lname, email, salt, pswd, token) values ($1, $2, $3, sval, pval, tval) returning id into x;
    return x;
  end;
$$ language plpgsql;


create function new.wpa(dbo.wpa.bssid%type) returns integer as $$
  declare
    x integer;
  begin
    insert into dbo.wpa(bssid) values($1) returning id into x;
    return x;
  end;
$$ language plpgsql;

create function fnd.wpa(dbo.wpa.bssid%type) returns integer as $$
  declare
    x integer;
  begin
    select id from dbo.wpa where bssid = $1 into x;
    return x;
  end;
$$ language plpgsql;

create function new.scan(integer, dbo.wpa.bssid%type, dbo.scan.level%type)
  returns void as $$
  declare
    w integer;
  begin
    select fnd.wpa($2) into w;
    if w isnull then
        select new.wpa($2) into w;
    end if;
    insert into dbo.scan(time, wpaID, level) values (to_timestamp($1) at time zone 'UTC', w, $3);
  end;
$$ language plpgsql;
