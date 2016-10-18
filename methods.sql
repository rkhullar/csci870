/*
 * @author  : Rajan Khullar
 * @created : 09/08/16
 * @updated : 10/15/16
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

create function fnd.user(dbo.actor.email%type, secret text, modeP boolean, modeT boolean)
  returns integer as $$
  declare
    x integer;
    sval text;
    pval text;
  begin
    select id from dbv.user where email = $1 into x;
    if x isnull then
        return null;
    end if;
    if modeT then
        select id from dbv.user where email = $1 and token = $2 into x;
        return x;
    end if;
    if modeP then
        select salt from dbv.user where email = $1 into sval;
        select crypt(secret, sval) into pval;
        select id from dbv.user where email = $1 and pswd = pval into x;
        return x;
    end if;
    return null;
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

create function new.location(dbo.building.abbr%type, dbo.location.floor%type, dbo.location.room%type)
  returns integer as $$
  declare
    t integer;
    x integer;
  begin
    select id from dbo.building where abbr = $1 into t;
    if t notnull then
        insert into dbo.location(buildingID, floor, room) values(t, $2, $3) returning id into x;
        return x;
    end if;
    return null;
  end;
$$ language plpgsql;

create function fnd.location(dbo.building.abbr%type, dbo.location.floor%type, dbo.location.room%type)
  returns integer as $$
  declare
    x integer;
  begin
    select id from dbv.location where building=$1 and floor=$2 and room=$3 into x;
    return x;
  end;
$$ language plpgsql;

create function new.scan(integer, integer, dbo.wpa.bssid%type, dbo.scan.level%type, integer)
  returns void as $$
  declare
    w integer;
  begin
    select fnd.wpa($3) into w;
    if w isnull then
        select new.wpa($3) into w;
    end if;
    insert into dbo.scan(time, actorID, wpaID, level, locationID)
        values (to_timestamp($1) at time zone 'UTC', $2, w, $4, $5);
  end;
$$ language plpgsql;
